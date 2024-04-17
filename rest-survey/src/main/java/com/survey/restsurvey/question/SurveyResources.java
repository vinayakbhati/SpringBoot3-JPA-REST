package com.survey.restsurvey.question;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.survey.restsurvey.user.UserDetails;
import com.survey.restsurvey.user.UserDetailsPagingSorting;
import com.survey.restsurvey.user.UserDetailsRepository;

@RestController
public class SurveyResources {

	@Autowired
	private SurveyService surveyService;
	
	@Autowired
	private UserDetailsRepository userDetailsRepo;
	
	@Autowired
	private UserDetailsPagingSorting userDetailsPS;
	
	
	// get all survey
	@RequestMapping(value="/surveys", method= RequestMethod.GET)
	public List<Survey> retrieveAllSurvey(){		
		return surveyService.retrieveAllSurvey();
	}
	
	//get specific survey with surveyId
	@RequestMapping(value="/surveys/{surveyId}", method= RequestMethod.GET)
	public Survey retrieveSurveyById(@PathVariable String surveyId) {
		Survey sur= surveyService.retrieveSurveyById(surveyId);
		if(sur == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);			
	return sur;
	}
	
	//retrieve survey question
	@RequestMapping(value="/surveys/{surveyId}/questions")
	public List<Question> retrieveAllSurveyQuestions(@PathVariable String surveyId){
		List<Question> listQues= surveyService.retrieveAllSurveyQuestions(surveyId);
		return listQues;
	}
	
	//retriece specific survey question
	@RequestMapping(value="/surveys/{surveyId}/questions/{questionId}")
	public Question retrieveSurveyQuestionById(@PathVariable String surveyId, @PathVariable String questionId) {
		Question ques = surveyService.retrieveSurveyQuestionById(surveyId,questionId);
		return ques;
	}
	
	//Add specific survey question
		@RequestMapping(value="/surveys/{surveyId}/questions", method=RequestMethod.POST)
		public ResponseEntity addNewSurveyQuestion(@PathVariable String surveyId,
													@RequestBody Question ques){
		String questionId = surveyService.addSurveyQuestion(surveyId,ques);
		URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
					.path("/{questionId}")
					.buildAndExpand(questionId)
					.toUri();
			return ResponseEntity.created(location).build();
		}
		
		//delete specific survey question
		@RequestMapping(value="/surveys/{surveyId}/questions/{questionId}", method=RequestMethod.DELETE)
		public ResponseEntity deleteSurveyQuestion(@PathVariable String surveyId, @PathVariable String questionId) {
			surveyService.deleteSurveyQuestion(surveyId, questionId);
			return ResponseEntity.noContent().build();
		}
		
		//update specific survey question
				@RequestMapping(value="/surveys/{surveyId}/questions/{questionId}", method=RequestMethod.PUT)
				public ResponseEntity<Object> updateSurveyQuestion(@PathVariable String surveyId,
						@PathVariable String questionId,
						@RequestBody Question ques) {
					surveyService.updateSurveyQuestion(surveyId, questionId, ques);
					return ResponseEntity.noContent().build();
				}
				
		@RequestMapping(value= "/userdetails")
		public List<UserDetails> findAll(){
			List<UserDetails> list = userDetailsRepo.findAll();
			return list;
		}
		
		//http://localhost:8080/paging-sorting?sortBy=name&sortDirection=desc
		//http://localhost:8080/paging-sortingkl?page=0&size=10&sortBy=id&sortDirection=asc
		
		@RequestMapping(value="/paging-sorting",  method=RequestMethod.GET)
		public List<UserDetails> sortByRole( @RequestParam(defaultValue = "id") String sortBy,
	            @RequestParam(defaultValue = "asc") String sortDirection){
			Sort.Direction direction = Sort.Direction.fromString(sortDirection);
	        Sort sort = Sort.by(direction, sortBy);      
			
			List<UserDetails> userList = (List<UserDetails>) userDetailsPS.findAll(sort);
			
			return userList;
			
		}
}
