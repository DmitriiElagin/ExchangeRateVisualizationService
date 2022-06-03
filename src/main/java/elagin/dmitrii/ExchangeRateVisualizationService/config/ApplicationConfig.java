package elagin.dmitrii.ExchangeRateVisualizationService.config;

import elagin.dmitrii.ExchangeRateVisualizationService.service.exchange_rate_tracking.OpenExchangeRatesClient;
import elagin.dmitrii.ExchangeRateVisualizationService.service.gif.GiphyClient;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Value("${service.openexchangerates.url}")
    private String openExchangeRatesServiceUrl;

    @Value("${service.giphy.url}")
    private String giphyServiceUrl;

    @Bean
    public OpenExchangeRatesClient openExchangeRatesClient() {

        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Slf4jLogger(OpenExchangeRatesClient.class))
                .target(OpenExchangeRatesClient.class, openExchangeRatesServiceUrl);
    }

    @Bean
    public GiphyClient giphyClient() {

        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Slf4jLogger(GiphyClient.class))
                .target(GiphyClient.class, giphyServiceUrl);
    }
}
