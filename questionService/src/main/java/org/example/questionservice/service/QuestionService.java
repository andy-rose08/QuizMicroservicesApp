package org.example.questionservice.service;


import org.example.questionservice.model.Question;
import org.example.questionservice.model.QuestionWrapper;
import org.example.questionservice.model.Response;
import org.example.questionservice.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
  @Autowired
  QuestionRepository repo;

  public ResponseEntity<List<Question>> getAllQuestions() {
    try {
      return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }


  public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
    try {
      return new ResponseEntity<>(repo.findAllByCategory(category), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<Question> addQuestion(Question question) {
    try {

      return new ResponseEntity<>(repo.save(question), HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<Question> deleteQuestion(Long id) {
    try {
      if (repo.existsById(id)) {
        repo.deleteById(id);
        if (! repo.existsById(id)) {
          return new ResponseEntity<>(null, HttpStatus.OK);
        } else {
          return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
      } else {
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }


  public ResponseEntity<Question> updateQuestion(Question question) {

    try {
      if (repo.existsById(question.getId())) {
        return new ResponseEntity<>(repo.save(question), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<List<Integer>> getQuestionsForQuiz(String category, int numQuestions) {
    List<Integer> questions = repo.findRandomQuestionsByCategory(category, numQuestions);
    return new ResponseEntity<>(questions, HttpStatus.OK);
  }

  public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
    List<QuestionWrapper> wrappers = new ArrayList<>();
    List<Question> questions = new ArrayList<>();
    for (Integer id : questionIds) {
      questions.add(repo.findById(Long.valueOf(id)).get());
    }

    for (Question question : questions) {
      QuestionWrapper wrapper = new QuestionWrapper();
      wrapper.setId(question.getId());
      wrapper.setQuestionTitle(question.getQuestionTitle());
      wrapper.setOption1(question.getOption1());
      wrapper.setOption2(question.getOption2());
      wrapper.setOption3(question.getOption3());
      wrapper.setOption4(question.getOption4());
      wrappers.add(wrapper);

    }

    return new ResponseEntity<>(wrappers, HttpStatus.OK);

  }

  public ResponseEntity<Integer> getScore(List<Response> responses) {
    int right = 0;
    for (Response response : responses) {
      Question question = repo.findById(response.getId()).get();
      if (response.getResponse().equals(question.getRightAnswer())) {
        right++;
      }
    }
    return new ResponseEntity<>(right, HttpStatus.OK);
  }
}
