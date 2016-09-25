package de.immobilienscout.devtest.service.analyzer;

import com.google.common.collect.ImmutableMap;
import de.immobilienscout.ResourceLoader;
import de.immobilienscout.devtest.domain.Tag;
import de.immobilienscout.devtest.service.UrlFetcher;
import de.immobilienscout.devtest.utils.exception.ProcessingException;
import java.net.URL;
import java.util.StringJoiner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.Jsoup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import static de.immobilienscout.devtest.Utils.link;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;

@RunWith( MockitoJUnitRunner.class)
public class InaccessibleLinkAnalyzerTest {

    String baseUrl = "https://www.immobilienscout24.de";

    @InjectMocks
    PageAnalyzer analyzer = new InaccessibleLinkAnalyzer();

    @Mock
    public UrlFetcher urlFetcher;

    @Mock
    ImmutableMap.Builder builder;

    @Before
    public void setUp() throws Exception {
        ReflectionTestUtils.setField(analyzer, "executor", Executors.newSingleThreadExecutor());
    }

    @Test
    public void testPreparedHtmlWhereAllLinksAccessible(){

        // assume, that all links are accessible.
        given(urlFetcher.fetch(Matchers.any(URL.class))).willReturn(Pair.<String,String>of("",""));

        String html = ResourceLoader.resource("test.html");

        analyzer.analyze(Jsoup.parse(html, baseUrl), builder);

        Mockito.verify(builder, times(1))
                .put(Tag.INACCESSIBLE_LINK, "0");


    }
    @Test
    public void testPreparedHtmlWhereAllLinksNotAccessable(){

        given(urlFetcher.fetch(Matchers.any(URL.class))).willThrow(new ProcessingException("unable to fetch"));

        String html = ResourceLoader.resource("test.html");

        analyzer.analyze(Jsoup.parse(html, baseUrl), builder);

        Mockito.verify(builder, times(1))
                .put(Tag.INACCESSIBLE_LINK, "206");

    }

    @Test
    public void testMixedLinksAccessibility(){

        given(urlFetcher.fetch(Matchers.any(URL.class)))
            .willAnswer(invocation -> {
                URL url = (URL) invocation.getArguments()[0];
                if (url.toString().contains("not-acessible")){
                    throw new RuntimeException("something happens");
                }
                return Pair.<String, String>of("","");
            });

        String html = "<html>" +
                link(baseUrl + "/testing?p=1") +
                link("not-acessible-1") +
                link("not-acessible-2") +
                link("#top") +
                link("not-acessible-3") +
                link("https://google.com") +
                "</html>";

        analyzer.analyze(Jsoup.parse(html, baseUrl), builder);

        Mockito.verify(builder, times(1))
                .put(Tag.INACCESSIBLE_LINK, "3");


    }

}