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

    @Value("${service.openexchangerates.app-id}")
    private String appId;

    public OpenExchangeRatesService(OpenExchangeRatesClient client) {
        this.client = client;
    }

    @Override
    public Map<String, String> getCurrencies() {
        return Collections.unmodifiableMap(client.getCurrencies());
    }

    @Override
    public Map<String, BigDecimal> getLatestRates() {
        return client.getLatestRates(appId).rates;
    }

    @Override
    public Map<String, BigDecimal> getHistoricalRates(LocalDate date) {
        if (date.isAfter(LocalDate.now())) {
            logger.error("Неверная дата - {}", date);
            throw new InvalidDateException("The date '" + date + "' is invalid");
        }

        return client.getHistoricalRates(date, appId).rates;
    }

    @Override
    public int compareLatestRateWithYesterday(String code) {
        final var currencies = getCurrencies();

        if (!currencies.containsKey(code)) {
            logger.error("Неверный код валюты - {}", code);
            throw new InvalidCurrencyCodeException("The currency code '" + code + "' is invalid or unsupported");
        }

        var yesterday = LocalDate.now().minusDays(1);

        final var yesterdayRates = getHistoricalRates(yesterday);

        final var latest = getLatestRates().get(code);
        logger.info("Последний курс {} = {}", code, latest);

        final var yesterdayRate = yesterdayRates.get(code);
        logger.info("Курс {} за {} = {}", code, yesterday, yesterdayRate);

        return latest.compareTo(yesterdayRates.get(code));
    }


}
