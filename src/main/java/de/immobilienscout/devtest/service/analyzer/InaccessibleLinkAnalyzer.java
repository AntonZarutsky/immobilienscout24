package de.immobilienscout.devtest.service.analyzer;

import com.google.common.collect.ImmutableMap;
import de.immobilienscout.devtest.domain.Tag;
import de.immobilienscout.devtest.service.UrlFetcher;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static de.immobilienscout.devtest.domain.Tag.INACCESSIBLE_LINK;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.stream.Collectors.toList;


@Component
@Slf4j
public class InaccessibleLinkAnalyzer implements PageAnalyzer{

    @Autowired
    private UrlFetcher urlFetcher;

    @Autowired
    private Executor executor;

    @Override
    public void analyze(@NonNull Document doc, @NonNull ImmutableMap.Builder<Tag,String> builder) {

//        in order to check accessibility for all links fast, it looks like a good idea to do that in parallel.
        List<CompletableFuture<Boolean>> inaccessableLinksFutures =
            doc.select("a[href]")
               .stream()
               .map(this::toUrl)
               .map(this::isInaccessableFuture)
               .collect(toList());

// Joining futures and calcuating amount of not inaccessible links
        Long inaccessableLinksCount =
            inaccessableLinksFutures.stream()
                .map(CompletableFuture::join)
                .filter(result -> result)
                .count();

        builder.put(INACCESSIBLE_LINK, String.valueOf(inaccessableLinksCount));
    }

    private CompletableFuture<Boolean> isInaccessableFuture(String url) {

        return CompletableFuture.supplyAsync(() -> {
            try {
                urlFetcher.fetch(new URL(url));
                log.info("Fetching {}", new URL(url).toString());
                return FALSE;
            }catch (Exception e) {
                log.error("Unable to fetch url {}", url, e);
                return TRUE;
            }
        }, executor);
    }


    private String toUrl(Element element) {
        return element.absUrl("href");
    }
}

