package com.zhaojh.aidemo.controller;

import com.zhaojh.aidemo.dto.QuestionDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AiController {

    private final OllamaChatModel ollamaChatModel;
    private ChatClient chatClient;
    private final ChatClient.Builder chatClientBuilder;

    @Value("classpath:/templates/prompt.txt")
    private Resource promptResource;

    @PostConstruct
    public void init() {
        chatClient = chatClientBuilder.build();
    }

    /*@PostMapping("/analyze")
    public String analyzeImage(@RequestBody @Valid QuestionDto questionDto) {
        ClassPathResource resource = new ClassPathResource("test.jpeg");
        String message = questionDto.getQuestion() + "Answer this with language : " + questionDto.getLanguage();
        Message userMessage = new UserMessage(message, new Media(MimeTypeUtils.IMAGE_JPEG, resource));
        String result = ollamaChatModel.call(new Prompt(userMessage).getContents());
        return result;
    }*/

    @PostMapping("/ask")
    public String ask(@RequestBody @Valid QuestionDto questionDto) {
        String answer = chatClient.prompt()
                .user(spec -> spec.text(promptResource)
                        .param("question", questionDto.getQuestion())
                        .param("language", questionDto.getLanguage())
                        .param("rules", questionDto.getRules())
                        .param("answer", questionDto.getAnswer())
                )
                .call()
                .content();
        return answer;
    }
}
