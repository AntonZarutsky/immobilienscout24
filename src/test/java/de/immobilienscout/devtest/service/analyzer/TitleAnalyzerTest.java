package de.immobilienscout.devtest.service.analyzer;

import com.google.common.collect.ImmutableMap;
import de.immobilienscout.ResourceLoader;
import de.immobilienscout.devtest.domain.Tag;
import org.jsoup.Jsoup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;

@RunWith( MockitoJUnitRunner.class)
public class TitleAnalyzerTest {

    String baseUrl = "https://www.immobilienscout24.de";

    PageAnalyzer analyzer = new TitleAnalyzer();

    @Mock
    ImmutableMap.Builder builder;

    @Test
    public void testPreparedHtml(){

        String html = ResourceLoader.resource("test.html");

        analyzer.analyze(Jsoup.parse(html, baseUrl), builder);

        Mockito.verify(builder, times(1))
               .put(Tag.TITLE, "Immobilien, Wohnungen und Häuser bei ImmobilienScout24");


    }
    @Test
    public void testMissingTitle(){

        String html = "<html></html>";

        analyzer.analyze(Jsoup.parse(html, baseUrl), builder);

        Mockito.verify(builder, times(1))
                .put(Tag.TITLE, "");

    }

    @Test
    public void testEmptyTitle(){

        String html = "<html><head><title></title></head></html>";

        analyzer.analyze(Jsoup.parse(html, baseUrl), builder);

        Mockito.verify(builder, times(1))
                .put(Tag.TITLE, "");


    }

}















