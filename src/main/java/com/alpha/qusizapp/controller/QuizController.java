package com.alpha.qusizapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.qusizapp.dto.QuizDTO;
import com.alpha.qusizapp.dto.QuizResponce;
import com.alpha.qusizapp.dto.ResponceStructure;
import com.alpha.qusizapp.entity.Quiz;
import com.alpha.qusizapp.service.QuizService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/quiz")
@Tag(name = "QuizController", description = "Controller for handling quiz-related operations")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("/create")
    @Operation(summary = "Create a quiz", description = "Creates a new quiz with the specified category, number of questions, and title")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Quiz created successfully",
            content = { @Content(mediaType = "application/json",
            schema = @Schema(implementation = ResponceStructure.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid input parameters",
            content = @Content)
    })
    public ResponseEntity<ResponceStructure<Quiz>> createQuiz(@RequestParam String category, @RequestParam int numQ, @RequestParam String title) {
        return quizService.createQuiz(category, numQ, title);
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "Get a quiz", description = "Fetches a quiz by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Quiz retrieved successfully",
            content = { @Content(mediaType = "application/json",
            schema = @Schema(implementation = ResponceStructure.class)) }),
        @ApiResponse(responseCode = "404", description = "Quiz not found",
            content = @Content)
    })
    public ResponseEntity<ResponceStructure<QuizDTO>> getQuiz(@PathVariable int id) {
        return quizService.getQuiz(id);
    }

    @PostMapping("/submit/{id}")
    @Operation(summary = "Submit a quiz", description = "Submits the answers for a quiz and returns the score")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Quiz submitted successfully",
            content = { @Content(mediaType = "application/json",
            schema = @Schema(implementation = ResponceStructure.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid quiz submission",
            content = @Content)
    })
    public ResponseEntity<ResponceStructure<Integer>> sublitQuiz(@RequestBody List<QuizResponce> quizres, @PathVariable int id) {
        return quizService.submitQuiz(quizres, id);
    }
}
