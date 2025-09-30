package com.quest.exception;

public class FileMappingException extends RuntimeException {
  public FileMappingException(Exception e) {
    super(e);
  }
}
