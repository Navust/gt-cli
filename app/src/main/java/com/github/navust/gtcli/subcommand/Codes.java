package com.github.navust.gtcli.subcommand;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import picocli.CommandLine;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@CommandLine.Command(
        name = "codes",
        description = "Lists active coupon codes."
)
public class Codes implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://kg.games/ActiveCodes"))
                .GET()
                .build();

        HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String content = response.body();

        // Parse content with jsoup
        Document document = Jsoup.parse(content);
        Element faqBody = document.selectFirst(".faq-body");
        if (faqBody != null && faqBody.hasText()) {
            String codes = faqBody.children().stream()
                    .filter(Element::hasText)
                    .map(Element::text)
                    .filter(text -> text.contains("expires"))
                    .collect(Collectors.joining("\n"));

            System.out.println(codes);
        }

        return 0;
    }
}
