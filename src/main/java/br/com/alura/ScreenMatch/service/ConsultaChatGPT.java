package br.com.alura.ScreenMatch.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ConsultaChatGPT {
    public static String traduzir(String texto) {
        OpenAiService openAiService = new OpenAiService(System.getenv("OPENAI_APIKEY"));

        CompletionRequest completionRequest = CompletionRequest.builder()
                .model("gpt-4o")
                .prompt("traduza para o português o texto: " + texto)
                .maxTokens(1000)
                .temperature(0.7)
                .build();

        var resposta = openAiService.createCompletion(completionRequest);
        return resposta.getChoices().get(0).getText();
    }
}
