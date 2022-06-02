package elagin.dmitrii.ExchangeRateVisualizationService.service;

import elagin.dmitrii.ExchangeRateVisualizationService.service.exchange_rate_tracking.ExchangeRateTrackingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OpenExchangeRatesServiceTest {

    @Autowired
    @Qualifier("openExchangeRatesService")
    private ExchangeRateTrackingService service;

    @Test
    void getCurrenciesShouldNotBeNullOrEmpty() {
       final var currencies = service.getCurrencies();

       assertNotNull(currencies);
        assertFalse(currencies.isEmpty());
    }

    @Test
    void getCurrenciesShouldContainUsdAndEur() {
        final var currencies = service.getCurrencies();

        assertTrue(currencies.containsKey("USD"));
        assertTrue(currencies.containsKey("EUR"));
    }

    @Test
    void getLatestRatesShouldNotBeNullOrEmpty() {
        final var rates = service.getLatestRates();

        assertNotNull(rates);
        assertFalse(rates.isEmpty());
    }

    @Test
    void getLatestRatesShouldReturnUsdRateEqualOne() {
        final var rates = service.getLatestRates();

        assertEquals(0, rates.get("USD").compareTo(BigDecimal.ONE));
    }


}