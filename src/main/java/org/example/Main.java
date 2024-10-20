package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        String url = "https://api.nasa.gov/planetary/apod" +
                "?api_key=LpomD27fhW1cKho325YJzUtk5dCsJDcSiK4Dk3uE" +
                "&date=2024-10-20";

        CloseableHttpClient httpClient = HttpClients.createDefault();
        ClassicHttpRequest httpGet = ClassicRequestBuilder.get(url).build();

        CloseableHttpResponse response = httpClient.execute(httpGet);

//        Scanner sc = new Scanner(response.getEntity().getContent());
//        String answer = sc.nextLine();
//        System.out.println(answer);

        ObjectMapper mapper = new ObjectMapper();
        NasaAnswer answer = mapper.readValue(response.getEntity().getContent(), NasaAnswer.class);

//        System.out.println(answer.copyright);
//        System.out.println(answer.title);
//        System.out.println(answer.url);
        String imageUrl = answer.url;
        String[] splittedAnswer = imageUrl.split("/");
        String fileName = splittedAnswer[splittedAnswer.length - 1];

        HttpGet imageRequest = new HttpGet(imageUrl);
        CloseableHttpResponse image = httpClient.execute(imageRequest);

        FileOutputStream fos = new FileOutputStream("images/" + fileName);
        image.getEntity().writeTo(fos);


    }
}