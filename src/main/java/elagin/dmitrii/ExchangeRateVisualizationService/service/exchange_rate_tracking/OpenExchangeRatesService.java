package elagin.dmitrii.ExchangeRateVisualizationService.service.exchange_rate_tracking;

import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class OpenExchangeRatesService implements ExchangeRateTrackingService {

    private final GsonEncoder encoder;
    private final GsonDecoder decoder;
    private final OkHttpClient okHttpClient;

    @Value("${service.openexchangerates.app-id}")
    private String appId;

    @Value("${service.openexchangerates.currencies-endpoint}")
    private String currenciesEndpoint;

    @Value("${service.openexchangerates.latest-endpoint}")
    private String latestEndpoint;

    public OpenExchangeRatesService(OkHttpClient okHttpClient, GsonEncoder encoder, GsonDecoder decoder) {
        this.okHttpClient = okHttpClient;
        this.encoder = encoder;
        this.decoder = decoder;
    }


    @Override
    public Map<String, String> getCurrencies() {
        final var client = Feign.builder()
                .client(okHttpClient)
                .encoder(encoder)
                .decoder(decoder)
                .logger(new Slf4jLogger(CurrencyClient.class))
                .target(CurrencyClient.class, currenciesEndpoint);

        return client.getCurrencies();
    }

    @Override
    public Map<String, BigDecimal> getLatestRates() {
        final var client = Feign.builder()
                .client(okHttpClient)
                .encoder(encoder)
                .decoder(decoder)
                .logger(new Slf4jLogger(LatestRateClient.class))
                .target(LatestRateClient.class, latestEndpoint);

        return client.getLatestRates(appId).getRates();
    }
}
