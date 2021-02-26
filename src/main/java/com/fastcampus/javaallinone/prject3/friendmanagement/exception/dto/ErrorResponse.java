package com.fastcampus.javaallinone.prject3.friendmanagement.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class ErrorResponse {

    private int code;

    private String message;
}
