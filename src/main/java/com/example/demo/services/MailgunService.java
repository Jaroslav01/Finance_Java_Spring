package com.example.demo.services;
import java.io.File;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Service;

@Service
public class MailgunService {
    public void sendSimpleMessage(String email) throws UnirestException { // JsonNode
        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/yushchenko.software/messages")
			.basicAuth("api", "e15cd2cd49c8ab4b8f88fba868dbeb0f-c3d1d1eb-1d366d8e")
                .queryString("from", "no-replay@yushchenko.software")
                .queryString("to", email)
                .queryString("subject", "Welcome to finance")
                .queryString("text", "testing")
                .asJson();
        request.getBody();
    }
}
