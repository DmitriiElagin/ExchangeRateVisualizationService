package elagin.dmitrii.ExchangeRateVisualizationService.service.exchange_rate_tracking;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Service
public class OpenExchangeRatesService implements ExchangeRateTrackingService {

    private final OpenExchangeRatesClient client;

    @Value("${service.openexchangerates.app-id}")
    private String appId;

    public OpenExchangeRatesService(OpenExchangeRatesClient client) {
        this.client = client;
    }


    @Override
    public Map<String, String> getCurrencies() {
        return client.getCurrencies();
    }

    @Override
    public Map<String, BigDecimal> getLatestRates() {
        return client.getLatestRates(appId).getRates();
    }

    @Override
    public Map<String, BigDecimal> getHistoricalRates(LocalDate date) {
        return client.getHistoricalRates(date, appId).getRates();
    }
}
