package de.immobilienscout.devtest.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableRetry
public class GlobalConfiguration {

    @Value("${app.httpClient.connections.total}")
    private int maxTotalConnections;

    @Value("${app.httpClient.socket.timeout}")
    private int socketTimeout;

    @Value("${app.httpClient.connections.timeout}")
    private int connectionTimeout;

    @Value("${app.httpClient.connections.requestTimeout}")
    private int connectionRequestTimeout;

    @Bean
    public Executor defautExecutor(){
        return Executors.newFixedThreadPool(100);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(clientHttpRequestFactory());
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory(httpClient());
    }

    @Bean
    public CloseableHttpClient httpClient() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(maxTotalConnections);

        RequestConfig config = RequestConfig.custom()
            .setConnectionRequestTimeout(connectionRequestTimeout)
            .setSocketTimeout(socketTimeout)
            .setConnectTimeout(connectionTimeout).build();

        CloseableHttpClient defaultHttpClient = HttpClientBuilder.create()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(config).build();

        return defaultHttpClient;
    }


}
