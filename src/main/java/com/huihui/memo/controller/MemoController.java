package com.huihui.memo.controller;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;

import com.huihui.memo.MemoApplication;
import com.huihui.memo.dao.CurrentUserDao;
import com.huihui.memo.dao.NoteDao;
import com.huihui.memo.pojo.CurrentUser;
import com.huihui.memo.pojo.Note;
import com.huihui.memo.pojo.User;
import com.huihui.memo.view.AddMemoView;

import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private Button btnUnfinishedMemo;

    @FXML
    private TableView<Note>noteView;

    @FXML
    private TableColumn<Note, Boolean> detailCol;
    
    @FXML
    private TableColumn<Note, Boolean> editCol;
    
    @FXML
    private TableColumn<Note, Boolean> deleteCol;

    @FXML
    private TableColumn<Note, String> titleCol;

    @FXML
    private TextField txtSearch;

    //用于存储视图中的每个项
    private ObservableList<Note>noteList = FXCollections.observableArrayList();
    
    //其他
    @Autowired
    NoteDao noteDao;
    
    @Autowired
    CurrentUserDao currentUserDao;
    
    @FXML
    void AddMemo(ActionEvent event) {
    	MemoApplication.showView(AddMemoView.class);
    	MemoApplication.getStage().sizeToScene();
    }

    @FXML
    void Logout(ActionEvent event) {

    }

    @FXML
    void Search(ActionEvent event) {

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
	    //获取当前用户
    	List<CurrentUser> currentUser = currentUserDao.findAll();
    	User user = currentUser.get(0).getUser();
    	Integer uid = user.getId();
    	List<Note>notes=noteDao.findByUserId(uid);
    	noteList.addAll(notes);
    	noteView.setItems(noteList);
    }

    private void setColumnProperties() {
    	
    	Callback<TableColumn<Note, Boolean>, TableCell<Note, Boolean>> editCell = 
    			new Callback<TableColumn<Note, Boolean>, TableCell<Note, Boolean>>()
    	{
    		@Override
    		public TableCell<Note, Boolean> call( final TableColumn<Note, Boolean> param)
    		{
    			final TableCell<Note, Boolean> cell = new TableCell<Note, Boolean>()
    			{
    				Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
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
//    							User user = getTableView().getItems().get(getIndex());
//    							updateUser(user);
    							System.out.println("btnEdit triggered!");
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
    	
    	Callback<TableColumn<Note, Boolean>, TableCell<Note, Boolean>> detailCell = 
    			new Callback<TableColumn<Note, Boolean>, TableCell<Note, Boolean>>()
    	{
    		@Override
    		public TableCell<Note, Boolean> call( final TableColumn<Note, Boolean> param)
    		{
    			final TableCell<Note, Boolean> cell = new TableCell<Note, Boolean>()
    			{
    				Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
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
//    							User user = getTableView().getItems().get(getIndex());
//    							updateUser(user);
    							System.out.println("btnEdit triggered!");
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
    				Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
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
//    							User user = getTableView().getItems().get(getIndex());
//    							updateUser(user);
    							System.out.println("btnEdit triggered!");
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
    	
    	titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
    	editCol.setCellFactory(editCell);
    	detailCol.setCellFactory(detailCell);
    	deleteCol.setCellFactory(deleteCell);
    	

    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		noteView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		loadNoteDetails();
		setColumnProperties();
	}
}

