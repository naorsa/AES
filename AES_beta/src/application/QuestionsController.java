package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.controlsfx.control.textfield.TextFields;

import ocsf.client.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.IntegerStringConverter;

public class QuestionsController implements Observer{

    @FXML
    private TextField txtFieldQuestion;

    @FXML
    private TableColumn<Question, String> tbcAns1;

    @FXML
    private TableColumn<Question, Integer> tbcCorr;

    @FXML
    private TableColumn<Question, String> tbcId;

    @FXML
    private TableColumn<Question, String> tbcAns3;
    
    @FXML
    private TableColumn<Question, String> tbcAns2;

    @FXML
    private TextField txtFieldId;

    @FXML
    private TableColumn<Question, String> tbcAns4;

    @FXML
    private Button btnSearch;

    @FXML
    private TableColumn<Question, String> tbcIdText;

    @FXML
    private TableColumn<Question, String> tbcName;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField txtFieldName;

    @FXML
    private Button update;

    @FXML
    private TableView<Question> tblQuestions;

    @FXML
    private Label lblUpdateError;
    
    @FXML
    private TableColumn<Question, Integer> tbcIdNum;

    private ObservableClient client;
    private Map<String,Integer> newValues;
	private ArrayList<Question> questions;
	
	

	@FXML
 	void searchQuestion(ActionEvent event) {
		tblQuestions.getItems().clear();
		String questionId = txtFieldId.getText();
		String questionName = txtFieldName.getText();
		String questionIns = txtFieldQuestion.getText();
		ObservableList<Question> queryQuestions = FXCollections.observableArrayList();
		if (questionId != null) {
			for (int i = 0; i < questions.size(); i++) {
				Question q = questions.get(i);
				if (q.getId().equals(questionId)) {
					queryQuestions.add(q);
				}
			}

		}

		if (questionName != null) {
			for (int i = 0; i < questions.size(); i++) {
				Question q = questions.get(i);
				if (q.getTeacherName().equals(questionName) && (!queryQuestions.contains(q))) {
					queryQuestions.add(q);
				}
			}

		}
		if (questionIns != null) {
			for (int i = 0; i < questions.size(); i++) {
				Question q = questions.get(i);
				if (q.getQuestionIns().equals(questionIns) && (!queryQuestions.contains(q))) {
					queryQuestions.add(q);
				}
			}

		}
		tbcId.setCellValueFactory(new PropertyValueFactory<Question, String>("id"));
		tbcName.setCellValueFactory(new PropertyValueFactory<Question, String>("teacherName"));
		tbcIdText.setCellValueFactory(new PropertyValueFactory<Question, String>("questionIns"));
		tbcCorr.setCellValueFactory(new PropertyValueFactory<Question, Integer>("correctAns"));
		tbcAns1.setCellValueFactory(new PropertyValueFactory<Question, String>("ans1"));
		tbcAns2.setCellValueFactory(new PropertyValueFactory<Question, String>("ans2"));
		tbcAns3.setCellValueFactory(new PropertyValueFactory<Question, String>("ans3"));
		tbcAns4.setCellValueFactory(new PropertyValueFactory<Question, String>("ans4"));
		tbcIdNum.setCellValueFactory(new PropertyValueFactory<Question, Integer>("questionNum"));
		tblQuestions.setItems(queryQuestions);
		

		txtFieldId.clear();
		txtFieldName.clear();
		txtFieldQuestion.clear();

	}



    
	
	@FXML
	public void initialize() throws IOException {
		Platform.runLater(() -> rootPane.requestFocus());
		tblQuestions.getItems().clear();
		tblQuestions.setEditable(true);
		tbcCorr.setCellFactory(TextFieldTableCell.<Question, Integer>forTableColumn(new IntegerStringConverter()));
		newValues = new HashMap<String,Integer>();
		client = new ObservableClient("77.138.5.122",8000);
		btnSearch.setDisable(true);
		lblUpdateError.setVisible(false);
		client.addObserver(this);
		client.openConnection();
		Message send = new Message("get-questions");
		client.sendToServer(send);

	}
	
    @FXML
    void updateQuestion(ActionEvent event) {
    	try {
    		if(newValues.size() == 0)
    		{
    			lblUpdateError.setText("Please update the row!");
    			lblUpdateError.setVisible(true);
    		}
    		else
    			lblUpdateError.setVisible(false);
    		Message send = new Message("set-questions-map",newValues);
			client.sendToServer(send);
			newValues.clear();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
	@Override
	public void update(Observable o, Object arg) {

		if(arg instanceof String)
		{
			String s = (String)arg;
			System.out.println(s);
		}
		if(arg instanceof Message)
		{
			Message handleMsg = (Message) arg;
			String[] recivedMSG = handleMsg.getMsg().split("-");
			if (recivedMSG[0].equals("ok") &&  recivedMSG[1].equals("arraylist"))
			{
				questions = handleMsg.getQuestions();
				String[] possibleIDs = new String [questions.size()];
				String[] possibleNames = new String [questions.size()];
				String[] possibleQuestion = new String [questions.size()];
				int i=0;
				
				for(Question q : questions)
				{
					
					possibleIDs[i] = q.getId();
					possibleNames[i] = q.getTeacherName();
					possibleQuestion[i] = q.getQuestionIns();
					i++;
				}		
				TextFields.bindAutoCompletion(txtFieldId, possibleIDs);
				TextFields.bindAutoCompletion(txtFieldName, possibleNames);
				TextFields.bindAutoCompletion(txtFieldQuestion, possibleQuestion);
				btnSearch.setDisable(false);
			}
			
			if(recivedMSG[0].equals("ok") &&  recivedMSG[1].equals("map"))
			{
				Message send = new Message("get-questions");
				try {
					client.sendToServer(send);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			
		}
	}





	@FXML public void updateCorrect(TableColumn.CellEditEvent<Question, Integer> correctEditEvent) {
		Question question = tblQuestions.getSelectionModel().getSelectedItem();
		Integer newValue = correctEditEvent.getNewValue();
		if(newValue>=1 && newValue <= 4)
			newValues.put(question.getId(),newValue);
		else
		{
			lblUpdateError.setText("Please enter valid input!");
			lblUpdateError.setVisible(true);
		}
		
	}





	@FXML public void updateCorrect() {}



}