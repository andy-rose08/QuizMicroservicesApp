package org.example.questionservice.repository;


import org.example.questionservice.model.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepositoryImplementation<Question, Long> {
  List<Question> findAllByCategory(String category);


  @Query(value = "Select id from question q where q.category=:category ORDER BY RANDOM() LIMIT :numQ", nativeQuery = true)
  List<Integer> findRandomQuestionsByCategory(String category, int numQ);
}
