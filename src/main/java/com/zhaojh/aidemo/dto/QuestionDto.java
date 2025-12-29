package com.zhaojh.aidemo.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionDto {

    @NotEmpty(message = "Question cannot be empty")
    String question;
    @NotEmpty(message = "Language cannot be empty")
    String language;
    @NotEmpty(message = "Rules cannot be empty")
    String rules;
//    @NotEmpty(message = "Answer cannot be empty")
    String answer;
}
