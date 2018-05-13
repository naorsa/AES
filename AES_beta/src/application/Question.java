package application;

public class Question implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String teacherName;
	private String questionIns;
	private String id;
	private String ans1;
	private String ans2;
	private String ans3;
	private String ans4;
	private int correctAns;
	private int questionNum;
	
	
	public Question(String id,int questionNum,String teacherName, String questionIns,String ans1,String ans2,String ans3,String ans4,int correctAns) {
		super();
		this.ans1 = ans1;
		this.ans2 = ans2;
		this.ans3 = ans3;
		this.ans4 = ans4;
		this.questionNum = questionNum;
		this.correctAns = correctAns;
		this.teacherName = teacherName;
		this.questionIns = questionIns;
		this.id = id;
	}
	
	
	public int getQuestionNum() {
		return questionNum;
	}


	public void setQuestionNum(int questionNum) {
		this.questionNum = questionNum;
	}





	public String getAns1() {
		return ans1;
	}


	public void setAns1(String ans1) {
		this.ans1 = ans1;
	}


	


	public void setAns2(String ans2) {
		this.ans2 = ans2;
	}

	public String getAns2() {
		return ans2;
	}
	public String getAns3() {
		return ans3;
	}


	public void setAns3(String ans3) {
		this.ans3 = ans3;
	}


	public String getAns4() {
		return ans4;
	}


	public void setAns4(String ans4) {
		this.ans4 = ans4;
	}


	public int getCorrectAns() {
		return correctAns;
	}

	public void setCorrectAns(int correctAns) {
		this.correctAns = correctAns;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
