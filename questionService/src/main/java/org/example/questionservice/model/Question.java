package org.example.questionservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Question {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  private String questionTitle;
  private String option1;
  private String option2;
  private String option3;
  private String option4;
  private String rightAnswer;
  private String difficultyLevel;
  private String category;

}
