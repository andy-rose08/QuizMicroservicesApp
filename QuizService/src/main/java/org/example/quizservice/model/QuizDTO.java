package org.example.quizservice.model;

import lombok.Data;

@Data
public class QuizDTO {

  String categoryName;
  Integer numQuestions;
  String title;
}
