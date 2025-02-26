package com.trial.openai.testOpenAI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yaml.snakeyaml.Yaml;

@SpringBootApplication
public class TestOpenAiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestOpenAiApplication.class, args);

		try{

			TestOpenAiApplication app = new TestOpenAiApplication();

        //parsing request
        var fileName = "request.json";

        System.out.println("getResourceAsStream : " + fileName);
        InputStream is = app.getFileFromResourceAsStream(fileName);
        
        //parsing yaml config
        Yaml yaml = new Yaml();
        var configFile = "config.yaml";
        InputStream yamlIS = app.getFileFromResourceAsStream(configFile);

        HashMap yamlMap = yaml.load(yamlIS);
        var apiKey = yamlMap.get("openAI_api_key");

		HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("https://api.openai.com/v1/chat/completions"))
        .header("Content-Type", "application/json")
        .header("Authorization", "Bearer " + apiKey)
		.POST(HttpRequest.BodyPublishers.ofInputStream(() -> is))
        .build();

		var client = HttpClient.newHttpClient();
		var response = client.send(request, HttpResponse.BodyHandlers.ofString());
		System.out.println(response.body());

		}catch(Exception e){
			e.printStackTrace();
		}

	}

	private InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }

	 // print input stream
    private static void printInputStream(InputStream is) {

        try (InputStreamReader streamReader =
                    new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
