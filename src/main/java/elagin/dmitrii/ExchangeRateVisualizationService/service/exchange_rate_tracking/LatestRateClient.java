package elagin.dmitrii.ExchangeRateVisualizationService.service.exchange_rate_tracking;

import elagin.dmitrii.ExchangeRateVisualizationService.model.OpenExchangeRatesResponse;
import feign.Param;
import feign.RequestLine;

public interface LatestRateClient {

    @RequestLine("GET?app_id={app_id}")
    OpenExchangeRatesResponse getLatestRates(@Param("app_id") String appId);
}
