package com.alpha.qusizapp.dao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alpha.qusizapp.Repo.QuestionRepo;
import com.alpha.qusizapp.entity.Question;

@Repository
public class QuestionDao {

    @Autowired
    private QuestionRepo questionRepo;

    public Question saveQuestion(Question question) {
        return questionRepo.save(question);
    }

    public List<Question> findAllQuestions() {
        return questionRepo.findAll();
    }

    public List<Question> getQuestionsByCategory(String category) {
        return questionRepo.findByCategory(category);
    }

    
    public List<Question> findRandomQuestonsBycategory(String category, int numQ){
    	return questionRepo.findRandomQuestionsByCategory(category, numQ);
    }
	
}
