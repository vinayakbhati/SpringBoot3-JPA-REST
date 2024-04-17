package com.survey.restsurvey.question;

import java.util.List;

public class Question {

	private String id;
	private String description;
	private String correctAnswer;
	private List<String> option;
	
	public Question() {
		
	}
	
	
	public Question(String id, String description, String correctAnswer, List<String> option) {
		super();
		this.id = id;
		this.description = description;
		this.correctAnswer = correctAnswer;
		this.option = option;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCorrectAnswer() {
		return correctAnswer;
	}
	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	public List<String> getOption() {
		return option;
	}
	public void setOption(List<String> option) {
		this.option = option;
	}


	@Override
	public String toString() {
		return "Question [id=" + id + ", description=" + description + ", correctAnswer=" + correctAnswer + ", option="
				+ option + "]";
	}
	
	
}
