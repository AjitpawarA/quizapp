package com.alpha.qusizapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.qusizapp.dto.ResponceStructure;
import com.alpha.qusizapp.entity.Question;
import com.alpha.qusizapp.service.QuestionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/save")
    @Operation(summary = "Save a question", description = "Saves a question to the database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Question saved successfully",
            content = { @Content(mediaType = "application/json",
            schema = @Schema(implementation = ResponceStructure.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid input",
            content = @Content)
    })
    public ResponseEntity<ResponceStructure<Question>> save(@RequestBody Question question) {
        ResponseEntity<ResponceStructure<Question>> savedQuestion = questionService.saveQuestion(question);
        return savedQuestion;
    }

    @GetMapping("/allquestions")
    @Operation(summary = "Get all questions", description = "Fetches all questions from the database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
            content = { @Content(mediaType = "application/json",
            schema = @Schema(implementation = ResponceStructure.class)) }),
        @ApiResponse(responseCode = "404", description = "No questions found",
            content = @Content)
    })
    public ResponseEntity<ResponceStructure<List<Question>>> findAllque() {
        return questionService.findAllQuestions();
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Get questions by category", description = "Fetches questions based on the category provided")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
            content = { @Content(mediaType = "application/json",
            schema = @Schema(implementation = ResponceStructure.class)) }),
        @ApiResponse(responseCode = "404", description = "No questions found in this category",
            content = @Content)
    })
    public ResponseEntity<ResponceStructure<List<Question>>> getQuebyCat(@PathVariable String category) {
        return questionService.getQuestionsByCategory(category);
    }
}
