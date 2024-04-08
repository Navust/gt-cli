package com.github.navust.gtcli.subcommand;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;


@Command(
        name = "redeem",
        description = "Redeems a coupon code."
)
public class Redeem implements Callable<Integer> {

    @Option(
            names = {"-r", "--region"},
            defaultValue = "NA",
            completionCandidates = RegionCandidates.class,
            description = "The server region. (Default region: ${DEFAULT-VALUE})\n" +
                    "(Valid regions: ${COMPLETION-CANDIDATES})"
    )
    String region;
    @Option(names = {"-u", "--user-id"}, description = "The user account ID.", required = true)
    long userId;
    @Option(names = {"-c", "--code"}, description = "The coupon code.", required = true)
    String code;

    @Override
    public Integer call() throws Exception {
        String formData = Map.of(
                        "region", region,
                        "userId", Long.toString(userId),
                        "code", code
                )
                .entrySet().stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("&"));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://guardiantales.com/coupon/redeem"))
                .setHeader("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(formData))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String content = response.body();

        // Parse content with jsoup
        Document document = Jsoup.parse(content);
        Element textDescription = document.selectFirst(".txt_desc");
        if (textDescription != null && textDescription.hasText()) {
            System.out.println(textDescription.text());
        }

        return 0;
    }

    static private class RegionCandidates extends ArrayList<String> {
        RegionCandidates() {
            super(List.of("NA", "LA", "EU", "OC", "SEA"));
        }
    }
}
