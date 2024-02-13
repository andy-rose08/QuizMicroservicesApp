package org.example.quizservice.service;

import org.example.quizservice.feign.QuizInterface;
import org.example.quizservice.model.QuestionWrapper;
import org.example.quizservice.model.Quiz;
import org.example.quizservice.model.Response;
import org.example.quizservice.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

  @Autowired
  QuizRepository repository;

  @Autowired
  QuizInterface quizInterface;


  public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
   List<Integer> questions = quizInterface.getQuestionsForQuiz(category, numQ).getBody();
   Quiz quiz = new Quiz();
   quiz.setTitle(title);
   quiz.setQuestions(questions);
   repository.save(quiz);
   return new ResponseEntity<>("Quiz Successfully Created", HttpStatus.CREATED);
  }

  public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(long id){
    Quiz quiz = repository.findById(id).get();
    List<Integer> questions = quiz.getQuestions();

    ResponseEntity<List<QuestionWrapper>> wrapper =  quizInterface.getQuestionFromId(questions);
    return wrapper;
  }

  public ResponseEntity<Integer> submitQuiz(long id, List<Response> responses) {
    ResponseEntity<Integer> score = quizInterface.getScore(responses);
    return  score;
  }
}
