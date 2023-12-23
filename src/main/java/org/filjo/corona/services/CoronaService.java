package org.filjo.corona.services;


import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class CoronaService extends BaseService {

    private static final String CORONA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchCoronaData() throws IOException, InterruptedException {
        super.fetchCoronaData();
    }

    @Override
    public HttpResponse<String> getStringHttpResponse(String link) throws IOException, InterruptedException {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(CORONA_URL))
                    .build();

            return client.send(request, HttpResponse.BodyHandlers.ofString());
        }
    }
}
