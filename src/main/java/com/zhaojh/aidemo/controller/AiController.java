package com.zhaojh.aidemo.controller;

import com.zhaojh.aidemo.dto.QuestionDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AiController {

    private final OllamaChatModel ollamaChatModel;

    @PostConstruct
    public void init() {

    }

    @PostMapping("/analyze")
    public String analyzeImage(@RequestBody @Valid QuestionDto questionDto) {
        ClassPathResource resource = new ClassPathResource("test.jpeg");
        String message = questionDto.getQuestion() + "Answer this with language : " + questionDto.getLanguage();
        Message userMessage = new UserMessage(message, new Media(MimeTypeUtils.IMAGE_JPEG, resource));
        String result = ollamaChatModel.call(new Prompt(userMessage).getContents());
        return result;
    }

    @PostMapping("/ask")
    public String ask(@RequestBody @Valid QuestionDto questionDto) {
        String message = questionDto.getQuestion() + "Answer this with language : " + questionDto.getLanguage();
        Message userMessage = new UserMessage(message);
        String result = ollamaChatModel.call(new Prompt(userMessage).getContents());
        return result;
    }
}
