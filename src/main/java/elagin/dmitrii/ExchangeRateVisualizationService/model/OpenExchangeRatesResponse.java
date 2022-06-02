package elagin.dmitrii.ExchangeRateVisualizationService.model;

import java.math.BigDecimal;
import java.util.Map;

public class OpenExchangeRatesResponse {
    private final String disclaimer;
    private final String license;
    private final long timestamp;
    private final String base;
    private final Map<String, BigDecimal> rates;

    public OpenExchangeRatesResponse(String disclaimer, String license, long timestamp, String base, Map<String, BigDecimal> rates) {
        this.disclaimer = disclaimer;
        this.license = license;
        this.timestamp = timestamp;
        this.base = base;
        this.rates = rates;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public String getLicense() {
        return license;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getBase() {
        return base;
    }

    public Map<String, BigDecimal> getRates() {
        return rates;
    }
}
