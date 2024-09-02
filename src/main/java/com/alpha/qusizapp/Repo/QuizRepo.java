package com.alpha.qusizapp.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.qusizapp.entity.Quiz;

public interface QuizRepo extends JpaRepository<Quiz, Integer>{

}
