package com.example.commons;

import lombok.Data;

import java.util.List;

/**
 * Created by kodonghyeon on 2016. 6. 6..
 */
@Data
public class ErrorResponse {
  private String message;
  private String code;

  private List<FieldError> errors;

  public static class FieldError {
    private String field;
    private String value;
    private String reason;
  }
}
