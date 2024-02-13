package org.example.questionservice.controller;


import org.example.questionservice.model.Question;
import org.example.questionservice.model.QuestionWrapper;
import org.example.questionservice.model.Response;
import org.example.questionservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {
  @Autowired
  QuestionService questionService;
  @Autowired
  Environment environment;

  @GetMapping("allQuestions")
  public ResponseEntity<List<Question>> getAllQuestions(){
    return questionService.getAllQuestions();
  }
  @GetMapping("category/{category}")
  public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category){
    return questionService.getQuestionsByCategory(category);
  }

  @PostMapping("addQuestion")
  public ResponseEntity<Question> addQuestion(@RequestBody Question question){
    return questionService.addQuestion(question);
  }
  @DeleteMapping("deleteQuestion/{id}")
  public ResponseEntity<Question> deleteQuestion(@PathVariable Long id){
    return  questionService.deleteQuestion(id);
  }
  @PutMapping("updateQuestion/{id}")
  public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody Question question){
    return questionService.updateQuestion(question);
  }


  //generate
  @GetMapping("generate")
  public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category, @RequestParam int numQuestions){
    return questionService.getQuestionsForQuiz(category,numQuestions);
  }
  //getQuestions(question id)
 @PostMapping("getQuestions")
  public ResponseEntity<List<QuestionWrapper>> getQuestionFromId(@RequestBody List<Integer> questionIds){
    System.out.println(environment.getProperty("local.server.port"));
    return questionService.getQuestionsFromId(questionIds);
  }
  //getScore
  @PostMapping("getScore")
  public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
    return questionService.getScore(responses);
  }
}
