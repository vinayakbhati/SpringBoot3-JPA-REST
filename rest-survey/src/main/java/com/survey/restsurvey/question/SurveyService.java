package com.survey.restsurvey.question;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class SurveyService {
	private static List<Survey> surveys= new ArrayList<Survey>();
	
	static {
		Question q1 = new Question("Question1","Most popular cloud platform","AWS", Arrays.asList("AWS","Azure","Google Cloud","Oracle Cloud"));
		Question q2 = new Question("Question2","Fastest growing cloud platform","Google Cloud", Arrays.asList("AWS","Azure","Google Cloud","Oracle Cloud"));
		Question q3 = new Question("Question3","Most popular DevOps Tools", "Kubernetes", Arrays.asList("Kubernetes","Docker","Terraform","Azure Devops"));
		List<Question> listQuestion = new ArrayList<Question>(Arrays.asList(q1,q2,q3));
		Survey s1 = new Survey("Survey1","Cloud Survey","Easy Cloud Survey Question", listQuestion);
		surveys.add(s1);
		
	}

	public List<Survey> retrieveAllSurvey() {
		return surveys;
	}


	public Survey retrieveSurveyById(String surveyId) {
		Optional<Survey> optionalSurvey= surveys.stream().filter(sur -> sur.getId().equalsIgnoreCase(surveyId))
				.findFirst();
		if(optionalSurvey.isEmpty()) return null;
		return optionalSurvey.get();
	}


	public List<Question> retrieveAllSurveyQuestions(String surveyId) {
		Survey sur= retrieveSurveyById(surveyId);
		return sur.getQuestions();		
	}


	public Question retrieveSurveyQuestionById(String surveyId, String questionId) {
		List<Question> listQues= retrieveAllSurveyQuestions(surveyId);
	Optional<Question> OpQues= listQues.stream().filter(q -> q.getId().equalsIgnoreCase(questionId)).findFirst();
	if(OpQues.isEmpty())
		return null;
	return OpQues.get();
	}

	//add survey question and return response
	public String addSurveyQuestion(String surveyId, Question ques) {
	List<Question> listQuestion= retrieveAllSurveyQuestions(surveyId);
		listQuestion.add(ques);	
		String questionId= randomNo();
		ques.setId(questionId);
		return questionId;
	}

	public String randomNo() {
		SecureRandom secure = new SecureRandom();
		String st = new BigInteger(32,secure).toString();
		return st;
	}

	public void deleteSurveyQuestion(String surveyId, String questionId) {
	List<Question> listQuestion= retrieveAllSurveyQuestions(surveyId);
	listQuestion.removeIf(q -> q.getId().equalsIgnoreCase(questionId));
	}


	public void updateSurveyQuestion(String surveyId, String questionId, Question ques) {
		List<Question> listQues =	retrieveAllSurveyQuestions(surveyId);
		listQues.removeIf(q -> q.getId().equalsIgnoreCase(questionId));
		listQues.add(ques);		
	}


	
	
}
