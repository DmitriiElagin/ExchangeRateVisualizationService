package elagin.dmitrii.ExchangeRateVisualizationService.service.exchange_rate_tracking;

import elagin.dmitrii.ExchangeRateVisualizationService.error.InvalidCurrencyCodeException;
import elagin.dmitrii.ExchangeRateVisualizationService.error.InvalidDateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;

@Service
public class OpenExchangeRatesService implements ExchangeRateTrackingService {

    private static final Logger logger = LoggerFactory.getLogger(OpenExchangeRatesService.class);

    private final OpenExchangeRatesClient client;
    private Map<String, String> currencies;
    private Map<String, BigDecimal> yesterdayRates;
    private LocalDate lastUpdate;

    @Value("${service.openexchangerates.app-id}")
    private String appId;

    public OpenExchangeRatesService(OpenExchangeRatesClient client) {
        this.client = client;
    }

    @Override
    public Map<String, String> getCurrencies() {
        if (lastUpdate == null || lastUpdate.isBefore(LocalDate.now())) {
            logger.debug("Запрос списка валют у сервиса");
            currencies = Collections.unmodifiableMap(client.getCurrencies());
        }

        return currencies;
    }

    @Override
    public Map<String, BigDecimal> getLatestRates() {
        return client.getLatestRates(appId).getRates();
    }

    @Override
    public Map<String, BigDecimal> getHistoricalRates(LocalDate date) {
        if (date.isAfter(LocalDate.now())) {
            throw new InvalidDateException("The date '" + date + "' is invalid");
        }

        return client.getHistoricalRates(date, appId).getRates();
    }

    @Override
    public int compareLatestRateWithYesterday(String code) {
        if (lastUpdate == null || lastUpdate.isBefore(LocalDate.now())) {
            logger.debug("Список и курсы валют устарели. Запрос у сервиса");
            getCurrencies();

            yesterdayRates = getHistoricalRates(LocalDate.now().minusDays(1));
            lastUpdate = LocalDate.now();
        }

        if (!currencies.containsKey(code)) {
            logger.error("Неверный код валюты - {}", code);
            throw new InvalidCurrencyCodeException("The currency code '" + code + "' is invalid or unsupported");
        }

        final var latest = getLatestRates().get(code);

        return latest.compareTo(yesterdayRates.get(code));
    }


}
