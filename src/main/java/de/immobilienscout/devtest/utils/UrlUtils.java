package de.immobilienscout.devtest.utils;

import de.immobilienscout.devtest.utils.exception.ProcessingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringJoiner;
import java.util.regex.Pattern;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;


public final class UrlUtils {

    private UrlUtils() {}

    @NonNull
    public static Pattern internalLinksPattern(@NonNull String urlString){

        URL url = null;
        try {
            url = new URL(urlString);
        }catch (MalformedURLException e) {
            throw new ProcessingException("Unable to parse URL from " + urlString);
        }

        return Pattern.compile(
            new StringJoiner("")
//            allow all schemas
            .add("^(http|https)://")
//            for subdomain inclusion
            .add("(.*\\.)?")
//            unclude host
            .add(url.getHost().replace(".","\\."))
//            taking inco account navigation, params, path
            .add("((/|\\?|#).*)?")
            .toString()
       );
    }
}
