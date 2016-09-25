package de.immobilienscout.devtest.service.analyzer;

import com.google.common.collect.ImmutableMap;
import de.immobilienscout.ResourceLoader;
import de.immobilienscout.devtest.domain.Tag;
import lombok.Value;
import org.jsoup.Jsoup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.verification.VerificationMode;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;

@RunWith( MockitoJUnitRunner.class)
public class VersionAnalyzerTest {

    String baseUrl = "https://www.immobilienscout24.de";

    PageAnalyzer analyzer = new VersionAnalyzer();

    @Mock
    ImmutableMap.Builder builder;

    @Test
    public void testPreparedHtml(){

        String html = ResourceLoader.resource("test.html");

        analyzer.analyze(Jsoup.parse(html, baseUrl), builder);

        Mockito.verify(builder, times(1))
               .put(Tag.VERSION, "<!doctype html>");


    }
    @Test
    public void testEmptyHtml(){

        String html = "<html></html>";

        analyzer.analyze(Jsoup.parse(html, baseUrl), builder);

        Mockito.verify(builder, times(1))
                .put(Tag.VERSION, "");


    }

    @Test
    public void testVersionedHtml(){

        String html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\"><html></html>";

        analyzer.analyze(Jsoup.parse(html, baseUrl), builder);

        Mockito.verify(builder, times(1))
                .put(Tag.VERSION, "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">");


    }

    @Test
    public void testVersioned2Html(){

        String html = "<!doctype html><html></html>";

        analyzer.analyze(Jsoup.parse(html, baseUrl), builder);

        Mockito.verify(builder, times(1))
                .put(Tag.VERSION, "<!doctype html>");

    }

}















