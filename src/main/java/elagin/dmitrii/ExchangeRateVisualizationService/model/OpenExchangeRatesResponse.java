package elagin.dmitrii.ExchangeRateVisualizationService.model;

import java.math.BigDecimal;
import java.util.Map;

public class OpenExchangeRatesResponse {
    public String disclaimer;
    public String license;
    public long timestamp;
    public String base;
    public Map<String, BigDecimal> rates;
}
