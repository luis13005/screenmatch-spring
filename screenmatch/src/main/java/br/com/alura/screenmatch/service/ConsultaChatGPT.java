package br.com.alura.screenmatch.service;


import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ConsultaChatGPT {
    private static String API_KEY = "*****ColoqueSuaApiAqui****";

    public static String Consultar(String txt){
        System.out.println("API: "+API_KEY);
        OpenAiService chat = new OpenAiService(API_KEY);

        CompletionRequest request = CompletionRequest.builder()
                .model("gpt-3.5-turbo-instruct")
                .prompt("Traduza o texto para português: "+txt)
                .maxTokens(1000)
                .temperature(0.7)
                .build();

        var resposta = chat.createCompletion(request);
        return resposta.getChoices().get(0).getText();
    }
}
