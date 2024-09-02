package com.alpha.qusizapp.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alpha.qusizapp.dao.QuestionDao;
import com.alpha.qusizapp.dto.ResponceStructure;
import com.alpha.qusizapp.entity.Question;
import com.alpha.qusizapp.exception.QuestionNotFoundException;
import com.alpha.qusizapp.exception.QuestionSaveException;

@Service
public class QuestionService {

	@Autowired
	private QuestionDao questionDao;

	public ResponseEntity<ResponceStructure<Question>> saveQuestion(Question question) {
		try {
			Question savedQuestion = questionDao.saveQuestion(question);
			if (savedQuestion == null) {
				throw new QuestionSaveException("Failed to save the question");
			}

			ResponceStructure<Question> response = new ResponceStructure<>();
			response.setStatuscode(HttpStatus.CREATED.value());
			response.setMessage("Question saved successfully");
			response.setData(savedQuestion);

			return new ResponseEntity<>(response, HttpStatus.CREATED);

		} catch (QuestionSaveException e) {
			ResponceStructure<Question> response = new ResponceStructure<>();
			response.setStatuscode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setMessage(e.getMessage());
			response.setData(null);

			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<ResponceStructure<List<Question>>> findAllQuestions() {
		try {
			List<Question> questions = questionDao.findAllQuestions();
			if (questions.isEmpty()) {
				throw new QuestionNotFoundException("No questions found");
			}

			ResponceStructure<List<Question>> response = new ResponceStructure<>();
			response.setStatuscode(HttpStatus.OK.value());
			response.setMessage("List of all questions");
			response.setData(questions);

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (QuestionNotFoundException e) {
			ResponceStructure<List<Question>> response = new ResponceStructure<>();
			response.setStatuscode(HttpStatus.NOT_FOUND.value());
			response.setMessage(e.getMessage());
			response.setData(null);

			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<ResponceStructure<List<Question>>> getQuestionsByCategory(String category) {
		try {
			List<Question> questions = questionDao.getQuestionsByCategory(category);
			if (questions.isEmpty()) {
				throw new QuestionNotFoundException("No questions found for category: " + category);
			}

			ResponceStructure<List<Question>> response = new ResponceStructure<>();
			response.setStatuscode(HttpStatus.OK.value());
			response.setMessage("Questions for category: " + category);
			response.setData(questions);

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (QuestionNotFoundException e) {
			ResponceStructure<List<Question>> response = new ResponceStructure<>();
			response.setStatuscode(HttpStatus.NOT_FOUND.value());
			response.setMessage(e.getMessage());
			response.setData(null);

			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
}
