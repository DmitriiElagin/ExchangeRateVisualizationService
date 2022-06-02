package elagin.dmitrii.ExchangeRateVisualizationService.service.exchange_rate_tracking;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public interface ExchangeRateTrackingService {
    Map<String, String> getCurrencies();

    Map<String, BigDecimal> getLatestRates();

    Map<String, BigDecimal> getHistoricalRates(LocalDate date);
}