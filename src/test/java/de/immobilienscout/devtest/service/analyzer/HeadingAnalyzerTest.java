package de.immobilienscout.devtest.service.analyzer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.immobilienscout.ResourceLoader;
import de.immobilienscout.devtest.domain.Tag;
import de.immobilienscout.devtest.utils.exception.ProcessingException;
import java.util.List;
import org.jsoup.Jsoup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static com.google.common.collect.ImmutableList.of;
import static de.immobilienscout.devtest.domain.Tag.*;
import static org.mockito.Mockito.times;

@RunWith( MockitoJUnitRunner.class)
public class HeadingAnalyzerTest {

    String baseUrl = "https://www.immobilienscout24.de";

    PageAnalyzer analyzer = new HeadingAnalyzer();

    @Mock
    ImmutableMap.Builder builder;

    List<Tag> headings = of(H1, H2, H3, H4, H5, H6);

    @Test
    public void testPreparedHtml() {

        String html = ResourceLoader.resource("test.html");

        analyzer.analyze(Jsoup.parse(html, baseUrl), builder);

        checkResults(of("0", "3", "15", "1", "0", "0"));


    }

    @Test
    public void testNoHeadingsHtml() {

        String html = "<html></html>";

        analyzer.analyze(Jsoup.parse(html, baseUrl), builder);

        checkResults(of("0", "0", "0", "0", "0", "0"));

    }

    @Test
    public void testNestedHtml() {

        String html = "<html><h1><h2><h3></h3><h3></h3></h2></h1></html>";

        analyzer.analyze(Jsoup.parse(html, baseUrl), builder);

        checkResults(of("1", "1", "2", "0", "0", "0"));

    }


    private void checkResults(List<String> headingsCounts) {
        if (headings.size() != 6) {
            throw new ProcessingException("wrong parameters");
        }
        for (int i = 0; i < headings.size(); i++) {
            Mockito.verify(builder, times(1)).put(headings.get(i), headingsCounts.get(i));
        }
    }

}


















