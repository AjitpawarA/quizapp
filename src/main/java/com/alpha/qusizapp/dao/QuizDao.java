package com.alpha.qusizapp.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alpha.qusizapp.Repo.QuizRepo;
import com.alpha.qusizapp.entity.Question;
import com.alpha.qusizapp.entity.Quiz;
import com.alpha.qusizapp.exception.QuestionNotFoundException;

@Repository
public class QuizDao {

    @Autowired
    private QuestionDao questionDao;
    
    @Autowired
    private QuizRepo quizRepo;

    public List<Question> createQuiz(String category, int numQ, String title) {
        List<Question> questions = questionDao.findRandomQuestonsBycategory(category, numQ);
        
        if (questions.size() < numQ) {
            throw new QuestionNotFoundException("Not enough questions available in category: " + category);
        }
        return questions;
    }
    
    public Optional<Quiz> getQuiz(int id) {
        return quizRepo.findById(id);  // Return Optional from repository
    }
    
    
    
}