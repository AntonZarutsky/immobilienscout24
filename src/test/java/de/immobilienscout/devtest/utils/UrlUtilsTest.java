package de.immobilienscout.devtest.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
@Slf4j
public class UrlUtilsTest {

    private String baseUrl = "https://www.immobilienscout24.de";

    private Pattern pattern = UrlUtils.internalLinksPattern(baseUrl);

    @Parameterized.Parameter
    public String url;

    @Parameterized.Parameter(value = 1)
    public boolean isInternal;


    @Parameterized.Parameters
    public static Collection parameters() {
        return Arrays.asList(new Object[][] {
                // internal
                { "https://www.immobilienscout24.de", true},
                { "http://www.immobilienscout24.de", true},
                { "https://www.immobilienscout24.de/some-resource/", true},
                { "http://www.immobilienscout24.de/some-resource/", true},
                { "http://www.immobilienscout24.de/some-resource#loginOverlay", true},
                { "http://www.immobilienscout24.de/some-resource?param1=value1", true},
                { "http://subdomain.www.immobilienscout24.de/some-resource?param1=value1", true},
                { "https://subdomain.www.immobilienscout24.de/some-resource?param1=value1", true},

                // external
                { "https://www.static-immobilienscout24.de", false},
                { "http://www.immobilienscout24.fr", false},
                { "https://news.immobilienscout24.de", false},
                { "https://appartments.immobilienscout24.de/", false},
                { "https://news.immobilienscout24.de/", false},
                { "https://www.immobilienscout24.deparam=value", false},

        });
    }

    @Test
    public void testPattern() throws MalformedURLException {

        log.info("value {}", url);
        assertThat(pattern.matcher(url).matches(), equalTo(isInternal));
    }

}