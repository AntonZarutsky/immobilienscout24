package de.immobilienscout.devtest.service;

import de.immobilienscout.devtest.utils.exception.ProcessingException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import lombok.Cleanup;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UrlFetcher {

    @Autowired
    private HttpClient httpClient;

    @NonNull
    @Retryable(maxAttempts = 10)
    public Pair<String, String> fetch(@NonNull String url){

        try {
            HttpClientContext context = HttpClientContext.create();
            val response = httpClient.execute(new HttpGet(url), context);

            if (!HttpStatus.valueOf(response.getStatusLine().getStatusCode()).is2xxSuccessful()){
                throw new ProcessingException("Not successfull response code " + response.getStatusLine().getStatusCode() +
                        "for url " + url.toString());
            }

//          closing stream in order to prevent memory leaks
            @Cleanup InputStream bodyStream = response.getEntity().getContent();


//          JSoup has methods to fetch document from URL by them own.
//          but unfortunatelly it is quite limited to customize connections/connectionPool behavior.
            return Pair.of(
                        context.getTargetHost().toURI(),
                        IOUtils.toString(bodyStream, Charset.defaultCharset())
                    );

        } catch (Exception e) {
            throw new ProcessingException(e.getMessage());
        }
    }
    @NonNull
    @Retryable(maxAttempts = 10)
    public Pair<String, String> fetch(@NonNull URL url){

        return fetch(url.toExternalForm());
    }
}
