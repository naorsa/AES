package application;

import java.io.IOException;
import java.util.ArrayList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

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
    private TableColumn<Question, String> tbcIdNum;

	private ArrayList<Question> questions;
//s
	@FXML
	void searchQuestion(ActionEvent event) {
		String ans1;
		String ans2;
		String ans3;
		String ans4;
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
		tbcAns3.setCellValueFactory(new PropertyValueFactory<Question, String>("ans2"));
		tbcAns4.setCellValueFactory(new PropertyValueFactory<Question, String>("ans3"));
		tblQuestions.setItems(queryQuestions);
		

		txtFieldId.clear();
		txtFieldName.clear();
		txtFieldQuestion.clear();

	}

	@FXML
	public void initialize() throws IOException {
		Platform.runLater(() -> rootPane.requestFocus());
		tblQuestions.getItems().clear();
		ObservableClient client = new ObservableClient("localhost",8000);
		client.addObserver(this);
		client.openConnection();

		client.sendToServer("Data base");

	}

	@Override
	public void update(Observable o, Object arg) {

		if(arg instanceof String)
		{
			String s = (String)arg;
			System.out.println(s);
		}
		if(arg instanceof ArrayList<?>)
		{
			questions = (ArrayList<Question>)arg;
			String[] possibleIDs = new String [questions.size()];
			String[] possibleNames = new String [questions.size()];
			String[] possibleQuestion = new String [questions.size()];
			int i=0;
			for(Question q:questions)
			{
				
				possibleIDs[i] = q.getId();
				possibleNames[i] = q.getTeacherName();
				possibleQuestion[i] = q.getQuestionIns();
				i++;
			}
			
			TextFields.bindAutoCompletion(txtFieldId, possibleIDs);
			TextFields.bindAutoCompletion(txtFieldName, possibleNames);
			TextFields.bindAutoCompletion(txtFieldQuestion, possibleQuestion);
		}
	}

	
}