package elagin.dmitrii.ExchangeRateVisualizationService.service.exchange_rate_tracking;

import elagin.dmitrii.ExchangeRateVisualizationService.error.InvalidCurrencyCodeException;
import elagin.dmitrii.ExchangeRateVisualizationService.error.InvalidDateException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OpenExchangeRatesServiceIntegrationTest {

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

    @Test
    void getHistoricalRatesShouldNotBeNullOrEmpty() {
        final var rates = service.getHistoricalRates(LocalDate.now().minus(1, ChronoUnit.YEARS));

        assertNotNull(rates);
        assertFalse(rates.isEmpty());
    }

    @Test
    void getHistoricalRatesShouldReturnCorrectRates() {
        final var rates = service.getHistoricalRates(LocalDate.of(2002, 1, 1));

        assertEquals(0, rates.get("USD").compareTo(BigDecimal.ONE));
        assertEquals(0, rates.get("EUR").compareTo(BigDecimal.valueOf(1.125715)));
        assertEquals(0, rates.get("RUB").compareTo(BigDecimal.valueOf(30.345058)));
    }

    @Test
    void getHistoricalRatesShouldTrowException() {
        try {
            service.getHistoricalRates(LocalDate.of(2122, 1, 1));
            fail("Метод не выбросил исключение");
        } catch (InvalidDateException ignored) {
        }


    }

    @Test
    void compareLatestRateWithYesterdayShouldWorkCorrectly() {
        assertEquals(0, service.compareLatestRateWithYesterday("USD"));
    }

    @Test
    void compareLatestRateWithYesterdayShouldThrowException() {
        try {
            service.compareLatestRateWithYesterday("");
            fail("Метод не выбросил исключение");
        } catch (InvalidCurrencyCodeException ignored) {
        }
    }
}