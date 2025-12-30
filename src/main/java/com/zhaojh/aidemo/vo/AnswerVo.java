package com.zhaojh.aidemo.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnswerVo {

    String answer;
    String language;
    String rules;
}
