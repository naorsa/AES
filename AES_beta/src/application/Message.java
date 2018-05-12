package application;

import java.util.ArrayList;
import java.util.Map;

public class Message {
	private String msg;
	private ArrayList<Question> questions;
	private Map<String, Integer> correctAns;
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public ArrayList<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}
	public Map<String, Integer> getCorrectAns() {
		return correctAns;
	}
	public void setCorrectAns(Map<String, Integer> correctAns) {
		this.correctAns = correctAns;
	}
	public Message(String str)
	{
		msg=str;
	}
	public Message(String str,ArrayList<Question> questions)
	{
		msg=str;
		this.questions=questions;
	}
	public Message(String str,Map<String, Integer> correctAns)
	{
		msg=str;
		this.correctAns=correctAns;
	}	
}
