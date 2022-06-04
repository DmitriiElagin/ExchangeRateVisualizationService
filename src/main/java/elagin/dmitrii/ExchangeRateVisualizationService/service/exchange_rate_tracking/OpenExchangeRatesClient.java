package elagin.dmitrii.ExchangeRateVisualizationService.service.exchange_rate_tracking;

import elagin.dmitrii.ExchangeRateVisualizationService.model.OpenExchangeRatesResponse;
import feign.Param;
import feign.RequestLine;

import java.time.LocalDate;
import java.util.Map;

public interface OpenExchangeRatesClient {

    @RequestLine("GET/currencies.json")
    Map<String, String> getCurrencies();


    @RequestLine("GET/latest.json?app_id={app_id}")
    OpenExchangeRatesResponse getLatestRates(@Param("app_id") String appId);

    @RequestLine("GET/historical/{date}.json?app_id={app_id}")
    OpenExchangeRatesResponse getHistoricalRates(@Param("date") LocalDate date, @Param("app_id") String appId);
}
