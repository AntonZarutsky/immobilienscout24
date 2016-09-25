package de.immobilienscout.devtest.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.immobilienscout.devtest.api.dto.AnalysisResult;
import de.immobilienscout.devtest.domain.Tag;
import de.immobilienscout.devtest.service.analyzer.PageAnalyzer;
import de.immobilienscout.devtest.service.analyzer.VersionAnalyzer;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class AnalysisService {

    @Autowired
    public List<? extends PageAnalyzer> analyzers;

    @Autowired
    public UrlFetcher      urlFetcher;

    public AnalysisResult analyze(URL pageUrl) {
        try {
            ImmutableMap.Builder<Tag, String> infoBuilder = ImmutableMap.builder();

            Document document = getDocument(pageUrl);

            analyzers.forEach(analyzer -> {
                analyzer.analyze(document, infoBuilder);
            });

            return new AnalysisResult(infoBuilder.build());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private Document getDocument(URL pageUrl) {
        Pair<String, String> response = urlFetcher.fetch(pageUrl);

        return Jsoup.parse(
//                document body
                response.getRight(),
//                baseUrl after redirects
                response.getLeft()
            );
    }
}











