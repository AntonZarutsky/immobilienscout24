package de.immobilienscout.devtest.service.analyzer;


import com.google.common.collect.ImmutableMap;
import de.immobilienscout.ResourceLoader;
import de.immobilienscout.devtest.domain.Tag;
import de.immobilienscout.devtest.utils.exception.ProcessingException;
import java.util.List;
import java.util.StringJoiner;
import org.jsoup.Jsoup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static com.google.common.collect.ImmutableList.of;
import static de.immobilienscout.devtest.Utils.link;
import static de.immobilienscout.devtest.domain.Tag.EXTERNAL_LINK;
import static de.immobilienscout.devtest.domain.Tag.INTERNAL_LINK;
import static org.mockito.Mockito.times;

@RunWith( MockitoJUnitRunner.class )
public class LinkAnalyzerTest {

    String baseUrl = "https://www.immobilienscout24.de";

    PageAnalyzer analyzer = new LinkAnalyzer();

    @Mock
    ImmutableMap.Builder builder;

    List<Tag> links = of(INTERNAL_LINK, EXTERNAL_LINK);

    @Test
    public void testPreparedHtml() {

        String html = ResourceLoader.resource("test.html");

        analyzer.analyze(Jsoup.parse(html, baseUrl), builder);

        checkResults(of("186", "20"));

    }

    @Test
    public void testHtmlWithSomeLinks() {

        String html = "<html>" +
                      link(baseUrl + "/testing?p=1") +
                      link("#top") +
                      link("https://google.com") +
                      "</html>";

        analyzer.analyze(Jsoup.parse(html, baseUrl), builder);

        checkResults(of("2", "1"));

    }

    @Test
    public void testLinksWithoutHref() {

        String html = "<html>" +
                "<a>to</a>" +
                "<a>to2</a>" +
                "</html>";


        analyzer.analyze(Jsoup.parse(html, baseUrl), builder);

        checkResults(of("0", "0"));
    }

    @Test
    public void testNoLinks() {

        String html = "<html><body></body></html>";

        analyzer.analyze(Jsoup.parse(html, baseUrl), builder);

        checkResults(of("0", "0"));
    }

    @Test
    public void testEmptyHtml() {

        String html = "";

        analyzer.analyze(Jsoup.parse(html, baseUrl), builder);

        checkResults(of("0", "0"));

    }


    private void checkResults(List<String> counts) {
        if (links.size() != 2) {
            throw new ProcessingException("wrong parameters");
        }
        for (int i = 0; i < links.size(); i++) {
            Mockito.verify(builder, times(1)).put(links.get(i), counts.get(i));
        }
    }

}