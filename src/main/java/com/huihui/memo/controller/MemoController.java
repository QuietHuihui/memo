package com.huihui.memo.controller;

import java.io.IOException;
import java.net.URL;
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
import com.huihui.memo.view.AddMemoView;
import com.huihui.memo.view.LoginView;

import de.felixroske.jfxsupport.FXMLController;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

@FXMLController
public class MemoController implements Initializable{

	//javafx控件
    @FXML
    private Button btnAddMemo;

    @FXML
    private Button btnAllMemo;

    @FXML
    private Button btnFinishedMemo;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnSearch;
    
    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnUnfinishedMemo;

    @FXML
    private TableView<Note>noteView;

    @FXML
    private TableColumn<Note, Boolean> detailCol;
    
    @FXML
    private TableColumn<Note, Boolean> deleteCol;

    @FXML
    private TableColumn<Note, String> titleCol;
    
    @FXML
    private TableColumn<Note, String> statusCol;

    @FXML
    private TextField txtSearch;
    
    @FXML
    private Label labelWelcome;

    //用于存储视图中的每个项
    private ObservableList<Note>noteList = FXCollections.observableArrayList();
    
    Parent root;
    
    Stage primaryStage;
    
    //其他
    @Autowired
    NoteDao noteDao;
    
    @Autowired
    CurrentUserDao currentUserDao;
    
    User curUser = new User();
     
    @FXML
    void AddMemo(ActionEvent event) {
    	MemoApplication.showView(AddMemoView.class);
    	MemoApplication.getStage().sizeToScene();
    }

    @FXML
    void Logout(ActionEvent event) {
		//删除之前的用户
		currentUserDao.deleteAll();
        MemoApplication.showView(LoginView.class);
        MemoApplication.getStage().sizeToScene();
    }

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

    @FXML
    void getAllMemo(ActionEvent event) {

    }

    @FXML
    void getFinishedMemo(ActionEvent event) {

    }

    @FXML
    void getUnfinishedMemo(ActionEvent event) {

    }

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
									editMemoController.setNote(note);
									Dialog<ButtonType>dialog = new Dialog<>();
									dialog.setDialogPane(editPane);
									dialog.setTitle("详细信息");
									dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
						            Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
						            closeButton.managedProperty().bind(closeButton.visibleProperty());
						            closeButton.setVisible(false);
									dialog.show();
									
								} catch (IOException e1) {
									// TODO Auto-generated catch block
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

//    				private void updateUser(User user) {
//    					userId.setText(Long.toString(user.getId()));
//    					firstName.setText(user.getFirstName());
//    					lastName.setText(user.getLastName());
//    					dob.setValue(user.getDob());
//    					if(user.getGender().equals("Male")) rbMale.setSelected(true);
//    					else rbFemale.setSelected(true);
//    					cbRole.getSelectionModel().select(user.getRole());
//    				}
    			};
    			return cell;
    		}
    	};
    	
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
    
    @FXML
    void Refresh(ActionEvent event) {
		noteView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		loadNoteDetails();
		setColumnProperties();
    }
    
    //编辑备忘使用
    
    @FXML
    void editBack(ActionEvent event) {

    }

    @FXML
    void editSubmit(ActionEvent event) {

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

