package com.zhaojh.aidemo.controller;

import com.zhaojh.aidemo.dto.QuestionDto;
import com.zhaojh.aidemo.vo.AnswerVo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AiController {

    private final OllamaChatModel ollamaChatModel;
    private ChatClient chatClient;
    private final ChatClient.Builder chatClientBuilder;

    @Value("classpath:/templates/user_prompt.txt")
    private Resource userPromptTemplate;

    @Value("classpath:/templates/system_prompt.txt")
    private Resource systemPromptTemplate;

    @Value("classpath:/templates/song_prompt.txt")
    private Resource songPromptTemplate;

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
    public AnswerVo ask(@RequestBody @Valid QuestionDto questionDto) {
        AnswerVo answer = chatClient.prompt()
                .system(spec -> spec.text(systemPromptTemplate)
                        .param("question", questionDto.getQuestion())
                )
                .user(spec -> spec.text(userPromptTemplate)
                        .param("language", questionDto.getLanguage())
                        .param("rules", questionDto.getRules())
                )
                .call()
                .entity(AnswerVo.class);
        return answer;
    }

    @GetMapping("/songs")
    public List<String> getSongList(@RequestParam String year, @RequestParam String language,@RequestParam String country) {
        List<String> answers = chatClient.prompt()
                .user(spec -> spec.text(songPromptTemplate)
                        .param("year", year)
                        .param("language", language)
                        .param("country", country)
                )
                .call()
                .entity(new ParameterizedTypeReference<List<String>>() {});
        return answers;
    }
}
