package application;

public class Question {

	private String teacherName;
	private String questionIns;
	private String id;

	public Question(String teacherName, String questionIns, String id, String courseName) {
		super();

		this.teacherName = teacherName;
		this.questionIns = questionIns;
		this.id = id;

		this.courseName = courseName;
	}

	public String getQuestionIns() {
		return questionIns;
	}

	public void setQuestionIns(String questionIns) {
		this.questionIns = questionIns;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	private String courseName;

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

}
