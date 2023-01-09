package com.huihui.memo.controller;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;

import com.huihui.memo.MemoApplication;
import com.huihui.memo.dao.CurrentUserDao;
import com.huihui.memo.dao.NoteDao;
import com.huihui.memo.pojo.CurrentUser;
import com.huihui.memo.pojo.Note;
import com.huihui.memo.pojo.User;
import com.huihui.memo.view.LoginView;

import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;

@FXMLController
public class MemoController implements Initializable{

	//javafx控件
	
	//添加备忘按钮
    @FXML
    private Button btnAddMemo;
    
    //所有备忘按钮
    @FXML
    private Button btnAllMemo;

    //已完成备忘按钮
    @FXML
    private Button btnFinishedMemo;

    //退出按钮
    @FXML
    private Button btnLogout;

    //查询按钮
    @FXML
    private Button btnSearch;
    
    //替换按钮
    @FXML
    private Button btnReplace;

    //未完成备忘按钮
    @FXML
    private Button btnUnfinishedMemo;

    //备忘表格视图
    @FXML
    private TableView<Note>noteView;

    //详情栏
    @FXML
    private TableColumn<Note, Boolean> detailCol;
    
    //删除栏
    @FXML
    private TableColumn<Note, Boolean> deleteCol;

    //标题栏
    @FXML
    private TableColumn<Note, String> titleCol;
    
    //状态 栏
    @FXML
    private TableColumn<Note, String> statusCol;

    //查询输入框
    @FXML
    private TextField txtSearch;
    
    //替换输入框
    @FXML
    private TextField txtReplace;
    
    //欢迎词标签
    @FXML
    private Label labelWelcome;

    //用于存储视图中的每个项
    private ObservableList<Note>noteList = FXCollections.observableArrayList();
    
    //数据库访问对象
    
    //备忘表
    @Autowired
    NoteDao noteDao;
    
    //当前用户表
    @Autowired
    CurrentUserDao currentUserDao;
    
    //当前用户
    User curUser = new User();
     
    //添加备忘
    @FXML
    void AddMemo(ActionEvent event) throws IOException {
    	
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("adddialog.fxml"));
		DialogPane addPane = loader.load();
		AddMemoController addMemoController = loader.getController();
		
		Dialog<ButtonType>dialog = new Dialog<>();
		dialog.setDialogPane(addPane);
		dialog.setTitle("添加备忘");
		dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
		dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
		addMemoController.setProperty(addPane);
    	
		//设置一个不可见的关闭Dialog按钮，使得单击Dialog右上角的"X"能够关闭Dialog窗口
        Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);
		
		Optional<ButtonType>clickedButton = dialog.showAndWait();
		
		if(clickedButton.get().equals(ButtonType.OK)) {
			//把临时文件中的修改内容读取进来，并且进行分词。
			String modifyString = new String(Files.readAllBytes(Paths.get("temp.txt")));
			String modifyItems[] = modifyString.split("█");
			//删除临时文件
			Files.delete(Paths.get("temp.txt"));
			
			Note note = new Note();
			//设置当前备忘的信息
			if(modifyItems.length==2) {
				note.setTitle(modifyItems[0]);
				note.setContent(modifyItems[1]);
			}else {
				note.setTitle(modifyItems[0]);
			}
			note.setStatus("未完成");
			
			//获取创建记录的时间
		    Calendar calendar= Calendar.getInstance();
		    SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
		    java.sql.Date createdDate = java.sql.Date.valueOf(dateFormat.format(calendar.getTime()));
		    //设置创建时间
		    note.setCreatedDate(createdDate);
		    //设置创建者为当前用户
			note.setUser(curUser);
			noteDao.save(note);
			
			//弹出备忘已创建的提示
			Alert alert = new Alert(Alert.AlertType.INFORMATION,"备忘已保存。");
	        alert.initOwner(MemoApplication.getStage());
	        alert.showAndWait();
			
			dialog.close();
			
			//添加备忘后刷新列表
			noteView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			loadNoteDetails();
			setColumnProperties();
		}
    }

    //退出
    @FXML
    void Logout(ActionEvent event) {
		//删除之前的用户
		currentUserDao.deleteAll();
        MemoApplication.showView(LoginView.class);
        MemoApplication.getStage().sizeToScene();
        
        //关闭应用
        Platform.exit();
    }

    //搜索
    @FXML
    void Search(ActionEvent event) {
    	String search = txtSearch.getText();
    	Integer uid = curUser.getId();
    	List<Note>searchNotes = noteDao.findBySearch(search,uid);
    	noteList.clear();
    	noteList.addAll(searchNotes);
    	noteView.setItems(noteList);
    	setColumnProperties();
    }

    //替换
    @FXML
    void Replace(ActionEvent event) {
    	String search = txtSearch.getText();
    	
    	if(search.equals("")) {
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("错误");
            alertError.setContentText("不能够替换空内容。");
            alertError.showAndWait();
    		throw new RuntimeException("不能够替换空内容！");
    	}
    	
    	String replace = txtReplace.getText();
    	Integer uid = curUser.getId();
    	List<Note>searchNotes = noteDao.findBySearch(search,uid);
    	Integer numOfNotes = searchNotes.size();
    	
		//弹出警告，询问是否要替换
        Alert alertComfirm = new Alert(Alert.AlertType.CONFIRMATION);
        alertComfirm.setTitle("警告");
        alertComfirm.setContentText("找到符合条件的备忘共 "+numOfNotes+" 条，确认进行替换吗？" );
        
        Optional<ButtonType>result = alertComfirm.showAndWait();

        //确认替换，成功则弹出提示
		if(result.get()==ButtonType.OK) {
			noteDao.replace(search,replace,uid);
			
			Alert alert = new Alert(Alert.AlertType.INFORMATION,"替换成功。");
	        alert.initOwner(MemoApplication.getStage());
	        alert.showAndWait();
	        
	        //替换后刷新列表
			noteView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			loadNoteDetails();
			setColumnProperties();
		}
    }
    
    //获得所有备忘
    @FXML
    void getAllMemo(ActionEvent event) {
        //获取所有备忘
		noteView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		loadNoteDetails();
		setColumnProperties();
    }

    //获得已完成备忘
    @FXML
    void getFinishedMemo(ActionEvent event) {
    	Integer uid = curUser.getId();
    	List<Note>finishedNotes = noteDao.findFinished(uid);
    	noteList.clear();
    	noteList.addAll(finishedNotes);
    	noteView.setItems(noteList);
    	setColumnProperties();
    }

    //获得未完成备忘
    @FXML
    void getUnfinishedMemo(ActionEvent event) {
    	Integer uid = curUser.getId();
    	List<Note>unfinishedNotes = noteDao.findUnFinished(uid);
    	noteList.clear();
    	noteList.addAll(unfinishedNotes);
    	noteView.setItems(noteList);
    	setColumnProperties();
    }

    //设置当前用户
    //从备忘表中把所有备忘取出来，设置为表格视图的Item
    private void loadNoteDetails() {
    	noteList.clear();

    	String username = curUser.getUsername(); 
    	Integer uid = curUser.getId();
    	//设置欢迎词
    	labelWelcome.setText("欢迎， "+username+" 。");
    	
    	List<Note>notes=noteDao.findByUserId(uid);
    	noteList.addAll(notes);
    	noteView.setItems(noteList);
    }

    //设置表格视图中，每一栏的内容
    private void setColumnProperties() {
    	
    	//编辑或者查看备忘详情
    	Callback<TableColumn<Note, Boolean>, TableCell<Note, Boolean>> detailCell = 
    			new Callback<TableColumn<Note, Boolean>, TableCell<Note, Boolean>>()
    	{
    		@Override
    		public TableCell<Note, Boolean> call( final TableColumn<Note, Boolean> param)
    		{
    			final TableCell<Note, Boolean> cell = new TableCell<Note, Boolean>()
    			{
    				Image imgEdit = new Image(getClass().getResourceAsStream("/images/detail.png"));
    				final Button btnEdit = new Button();
    				
    				@Override
    				public void updateItem(Boolean check, boolean empty)
    				{
    					super.updateItem(check, empty);
    					if(empty)
    					{
    						setGraphic(null);
    						setText(null);
    					}
    					else{
    						btnEdit.setOnAction(e ->{
    							Note note = getTableView().getItems().get(getIndex());

    					        try {
        					        FXMLLoader loader = new FXMLLoader();
        					        loader.setLocation(getClass().getResource("editdialog.fxml"));
									DialogPane editPane = loader.load();
									EditMemoController editMemoController = loader.getController();
									
									/*把这行所对应的备忘和dialogPane一同传给EditMemoController，不能直接在controller中
									用@FXML dialogPane, 否则会出现null exception*/ 
									
									Dialog<ButtonType>dialog = new Dialog<>();
									dialog.setDialogPane(editPane);
									dialog.setTitle("详细信息");
									dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
									dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
									editMemoController.setNote(note,editPane);
									
									//设置一个不可见的关闭Dialog按钮，使得单击Dialog右上角的"X"能够关闭Dialog窗口
						            Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
						            closeButton.managedProperty().bind(closeButton.visibleProperty());
						            closeButton.setVisible(false);
										
									Optional<ButtonType>clickedButton = dialog.showAndWait();
									
									if(clickedButton.get().equals(ButtonType.OK)) {
										//把临时文件中的修改内容读取进来，并且进行分词。
										String modifyString = new String(Files.readAllBytes(Paths.get("temp.txt")));
										String modifyItems[] = modifyString.split("█");
										//删除临时文件
										Files.delete(Paths.get("temp.txt"));
										
										//更新当前备忘的信息
										note.setTitle(modifyItems[0]);
										note.setContent(modifyItems[1]);
										note.setStatus(modifyItems[2]);
										
										//如果状态修改为已完成，则添加完成时间
										if(note.getStatus().equals("已完成")) {
											//获取完成备忘的时间
										    Calendar calendar= Calendar.getInstance();
										    SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
										    java.sql.Date finishedDate = java.sql.Date.valueOf(dateFormat.format(calendar.getTime()));
											note.setFinishedDate(finishedDate);
										}
										
										noteDao.save(note);
										
										dialog.close();
										
										//更新后刷新列表
	        							noteView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	        							loadNoteDetails();
	        							setColumnProperties();
									}
									

									
								} catch (IOException e1) {
									e1.printStackTrace();
								}
    					        

    						});
    						
    						btnEdit.setStyle("-fx-background-color: transparent;");
    						ImageView iv = new ImageView();
    				        iv.setImage(imgEdit);
    				        iv.setPreserveRatio(true);
    				        iv.setSmooth(true);
    				        iv.setCache(true);
    						btnEdit.setGraphic(iv);
    						
    						setGraphic(btnEdit);
    						setAlignment(Pos.CENTER);
    						setText(null);
    					}
    				}

    			};
    			return cell;
    		}
    	};
    	
    	//删除备忘
    	Callback<TableColumn<Note, Boolean>, TableCell<Note, Boolean>> deleteCell = 
    			new Callback<TableColumn<Note, Boolean>, TableCell<Note, Boolean>>()
    	{
    		@Override
    		public TableCell<Note, Boolean> call( final TableColumn<Note, Boolean> param)
    		{
    			final TableCell<Note, Boolean> cell = new TableCell<Note, Boolean>()
    			{
    				Image imgEdit = new Image(getClass().getResourceAsStream("/images/delete.png"));
    				final Button btnEdit = new Button();
    				
    				@Override
    				public void updateItem(Boolean check, boolean empty)
    				{
    					super.updateItem(check, empty);
    					if(empty)
    					{
    						setGraphic(null);
    						setText(null);
    					}
    					else{
    						btnEdit.setOnAction(e ->{
    							
    							//获取本行对应的备忘
    							Note note = getTableView().getItems().get(getIndex());
    							String title = note.getTitle();
    							
    							//弹出警告，询问是否要删除
    				            Alert alertComfirm = new Alert(Alert.AlertType.CONFIRMATION);
    				            alertComfirm.setTitle(title);
    				            alertComfirm.setContentText("确认要删除备忘“"+title+"”吗？");
    				            
    				            Optional<ButtonType>result = alertComfirm.showAndWait();

    				            //确认删除，成功则弹出提示
    							if(result.get()==ButtonType.OK) {
        							noteDao.delete(note);
        							Alert alert = new Alert(Alert.AlertType.INFORMATION,"备忘“"+title+"”已删除。");
        					        alert.initOwner(MemoApplication.getStage());
        					        alert.showAndWait();
        					        
        					        //删除后刷新列表
        							noteView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        							loadNoteDetails();
        							setColumnProperties();
    							}
    						});
    						
    						btnEdit.setStyle("-fx-background-color: transparent;");
    						ImageView iv = new ImageView();
    				        iv.setImage(imgEdit);
    				        iv.setPreserveRatio(true);
    				        iv.setSmooth(true);
    				        iv.setCache(true);
    						btnEdit.setGraphic(iv);
    						
    						setGraphic(btnEdit);
    						setAlignment(Pos.CENTER);
    						setText(null);
    					}
    				}

    			};
    			return cell;
    		}
    	};
    	
    	titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
    	statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
    	detailCol.setCellFactory(detailCell);
    	deleteCol.setCellFactory(deleteCell);
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	    //获取当前用户
    	List<CurrentUser> currentUser = currentUserDao.findAll();
    	curUser = currentUser.get(0).getUser();
		
		noteView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		loadNoteDetails();
		setColumnProperties();
	}
}

