package application;

import org.controlsfx.control.textfield.TextFields;

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

public class QuestionsController {

	@FXML
	private TextField txtFieldQuestion;

	@FXML
	private TableColumn<Question, String> tbcId;

	@FXML
	private TableView<Question> tblQuestions;

	@FXML
	private TextField txtFieldCourse;

	@FXML
	private TextField txtFieldId;

	@FXML
	private Button btnSearch;

	@FXML
	private TableColumn<Question, String> tbcName;

	@FXML
	private AnchorPane rootPane;

	@FXML
	private TextField txtFieldName;

	@FXML
	private Button update;

	private ObservableList<Question> questions;

	@FXML
	void searchQuestion(ActionEvent event) {
		System.out.println("d");
		tblQuestions.getItems().clear();
		String questionId = txtFieldId.getText();
		String questionCourse = txtFieldCourse.getText();
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
		if (questionCourse != null) {
			for (int i = 0; i < questions.size(); i++) {
				Question q = questions.get(i);
				if (q.getCourseName().equals(questionCourse) && (!queryQuestions.contains(q))) {
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
		tblQuestions.setItems(queryQuestions);

		txtFieldCourse.clear();
		txtFieldId.clear();
		txtFieldName.clear();
		txtFieldQuestion.clear();

	}

	@FXML
	public void initialize() {
		Platform.runLater(() -> rootPane.requestFocus());
		String[] possibleIDs = { "204403257", "308023381", "16498549", "213446654", "464889123" };
		String[] possibleNames = { "Gal", "Alon", "Malloc", "Omer", "Naor" };
		String[] possibleQuestion = { "What?", "Why?", "Who?", "Enter", "Press" };
		String[] possibleCourses = { "Shitot", "Tests", "Stam" };
		TextFields.bindAutoCompletion(txtFieldId, possibleIDs);
		TextFields.bindAutoCompletion(txtFieldName, possibleNames);
		TextFields.bindAutoCompletion(txtFieldQuestion, possibleQuestion);
		TextFields.bindAutoCompletion(txtFieldCourse, possibleCourses);
		questions = FXCollections.observableArrayList(new Question("Omer", "Enter", "204403257", "Stam"),
				new Question("Naor", "Press", "308023381", "Stam"), new Question("Gal", "Press", "213446654", "Stam"),
				new Question("Alon", "Press", "16498549", "Stam"),
				new Question("Malloc", "Press", "464889123", "Stam"));
		tblQuestions.getItems().clear();

	}

}