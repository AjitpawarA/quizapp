package com.alpha.qusizapp.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alpha.qusizapp.Repo.QuizRepo;
import com.alpha.qusizapp.dao.QuizDao;
import com.alpha.qusizapp.dto.QuestionDTO;
import com.alpha.qusizapp.dto.QuizDTO;
import com.alpha.qusizapp.dto.QuizResponce;
import com.alpha.qusizapp.dto.ResponceStructure;
import com.alpha.qusizapp.entity.Question;
import com.alpha.qusizapp.entity.Quiz;
import com.alpha.qusizapp.exception.QuestionNotFoundException;

@Service
public class QuizService {

	@Autowired
	private QuizDao quizDao;

	@Autowired
	private QuizRepo quizRepo;

	public ResponseEntity<ResponceStructure<Quiz>> createQuiz(String category, int numQ, String title) {
		try {
			List<Question> quizQuestions = quizDao.createQuiz(category, numQ, title);

			Quiz quiz = new Quiz();
			quiz.setQuestions(quizQuestions);
			quiz.setTitle(title);

			ResponceStructure<Quiz> response = new ResponceStructure<>();
			response.setStatuscode(HttpStatus.OK.value());
			response.setMessage("Quiz created successfully");
			response.setData(quiz);
			quizRepo.save(quiz);

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (QuestionNotFoundException e) {
			ResponceStructure<Quiz> response = new ResponceStructure<>();
			response.setStatuscode(HttpStatus.NOT_FOUND.value());
			response.setMessage(e.getMessage());
			response.setData(null);

			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<ResponceStructure<QuizDTO>> getQuiz(int id) {
		try {
			Optional<Quiz> quizOpt = quizDao.getQuiz(id);

			if (quizOpt.isPresent()) {
				Quiz quiz = quizOpt.get();

				QuizDTO quizDTO = new QuizDTO();
				quizDTO.setId(quiz.getId());
				quizDTO.setTitle(quiz.getTitle());
				List<QuestionDTO> questionDTOs = quiz.getQuestions().stream().map(q -> {
					QuestionDTO dto = new QuestionDTO();
					dto.setId(q.getId());
					dto.setTitle(q.getTitle());
					dto.setOp1(q.getOp1());
					dto.setOp2(q.getOp2());
					dto.setOp3(q.getOp3());
					dto.setOp4(q.getOp4());
					return dto;
				}).collect(Collectors.toList());
				quizDTO.setQuestions(questionDTOs);

				ResponceStructure<QuizDTO> response = new ResponceStructure<>();
				response.setStatuscode(HttpStatus.OK.value());
				response.setMessage("Quiz retrieved successfully");
				response.setData(quizDTO);

				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				ResponceStructure<QuizDTO> response = new ResponceStructure<>();
				response.setStatuscode(HttpStatus.NOT_FOUND.value());
				response.setMessage("Quiz not found");
				response.setData(null);

				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
		} catch (QuestionNotFoundException e) {
			ResponceStructure<QuizDTO> response = new ResponceStructure<>();
			response.setStatuscode(HttpStatus.NOT_FOUND.value());
			response.setMessage(e.getMessage());
			response.setData(null);

			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<ResponceStructure<Integer>> submitQuiz(List<QuizResponce> quizResponses, int id) {
	   
	    Optional<Quiz> optionalQuiz = quizRepo.findById(id);

	    if (!optionalQuiz.isPresent()) {
	        ResponceStructure<Integer> response = new ResponceStructure<>();
	        response.setStatuscode(HttpStatus.NOT_FOUND.value());
	        response.setMessage("Quiz not found");
	        response.setData(null);
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }

	    Quiz quiz = optionalQuiz.get();
	    List<Question> questions = quiz.getQuestions();

	    if (quizResponses.size() != questions.size()) {
	        ResponceStructure<Integer> response = new ResponceStructure<>();
	        response.setStatuscode(HttpStatus.BAD_REQUEST.value());
	        response.setMessage("Number of responses does not match number of questions");
	        response.setData(null);
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }

	    int right = 0;

	    for (int i = 0; i < quizResponses.size(); i++) {
	        QuizResponce response = quizResponses.get(i);
	        Question question = questions.get(i);
	        if (response.getRes().equals(question.getCorrectop())) {
	            right++;
	        }
	    }

	    ResponceStructure<Integer> response = new ResponceStructure<>();
	    response.setStatuscode(HttpStatus.OK.value());
	    response.setMessage("Quiz submitted successfully");
	    response.setData(right);

	    return new ResponseEntity<>(response, HttpStatus.OK);
	}

}