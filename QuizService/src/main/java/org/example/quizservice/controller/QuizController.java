package org.example.quizservice.controller;


import org.example.quizservice.model.QuestionWrapper;
import org.example.quizservice.model.QuizDTO;
import org.example.quizservice.model.Response;

import org.example.quizservice.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {
  @Autowired
  QuizService service;

  @PostMapping("create")
  public ResponseEntity<String> createQuiz(@RequestBody QuizDTO quizDto){
    return service.createQuiz(quizDto.getCategoryName(), quizDto.getNumQuestions(), quizDto.getTitle());

  }

  @GetMapping("get/{id}")
  public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable long id){
    return service.getQuizQuestions(id);
  }
  @PostMapping("submit/{id}")
  public ResponseEntity<Integer> submitQuiz(@PathVariable long id, @RequestBody List<Response> responses ){
    return service.submitQuiz(id, responses);
  }
}
