package com.quest.exception.files;

public class FileMappingException extends RuntimeException {
  public FileMappingException(Exception e) {
    super(e);
  }
}
