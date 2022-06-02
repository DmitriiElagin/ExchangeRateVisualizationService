package elagin.dmitrii.ExchangeRateVisualizationService.config;

import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public OkHttpClient okHttpClient(){
        return new OkHttpClient();
    }

    @Bean
    public GsonEncoder gsonEncoder() {
        return new GsonEncoder();
    }

    @Bean
    public GsonDecoder gsonDecoder() {
        return new GsonDecoder();
    }
}
