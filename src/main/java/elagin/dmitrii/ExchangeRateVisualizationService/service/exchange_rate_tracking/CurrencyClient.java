package elagin.dmitrii.ExchangeRateVisualizationService.service.exchange_rate_tracking;

import feign.RequestLine;

import java.util.Map;

public interface CurrencyClient {

    @RequestLine("GET")
    Map<String, String> getCurrencies();
}
