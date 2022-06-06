package elagin.dmitrii.ExchangeRateVisualizationService.service.exchange_rate_tracking;

import elagin.dmitrii.ExchangeRateVisualizationService.error.InvalidDateException;
import elagin.dmitrii.ExchangeRateVisualizationService.model.OpenExchangeRatesResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(OpenExchangeRatesService.class)
class OpenExchangeRatesServiceTest {
    @Value("${service.openexchangerates.app-id}")
    private String appId;

    @Autowired
    @Qualifier("openExchangeRatesService")
    private ExchangeRateTrackingService service;

    @MockBean
    private OpenExchangeRatesClient client;

    @Test
    void getCurrenciesShouldNotBeNullOrEmpty() {
        var currencies = Map.of("RUB", "Ruble");

        Mockito.when(client.getCurrencies()).thenReturn(currencies);

        currencies = service.getCurrencies();

        assertNotNull(currencies);
        assertFalse(currencies.isEmpty());
    }

    @Test
    void getCurrenciesShouldContainUsdAndEur() {
        var currencies = Map.of("USD", "Dollar", "EUR", "Euro");

        Mockito.when(client.getCurrencies()).thenReturn(currencies);

        currencies = service.getCurrencies();

        assertTrue(currencies.containsKey("USD"));
        assertEquals("Euro", currencies.get("EUR"));
    }

    @Test
    void getLatestRatesShouldNotBeNullOrEmpty() {
        OpenExchangeRatesResponse response = new OpenExchangeRatesResponse();
        response.rates = Map.of("USD", BigDecimal.ONE);


        Mockito.when(client.getLatestRates(Mockito.anyString())).thenReturn(response);

        final var rates = service.getLatestRates();

        assertNotNull(rates);
        assertFalse(rates.isEmpty());
    }

    @Test
    void getLatestRatesShouldReturnUsdRateEqualOne() {
        OpenExchangeRatesResponse response = new OpenExchangeRatesResponse();
        response.rates = Map.of("USD", BigDecimal.ONE);

        Mockito.when(client.getLatestRates(appId)).thenReturn(response);

        final var rates = service.getLatestRates();

        assertEquals(0, rates.get("USD").compareTo(BigDecimal.ONE));
    }


    @Test
    void getHistoricalRatesShouldReturnCorrectRates() {
        OpenExchangeRatesResponse response = new OpenExchangeRatesResponse();
        response.rates = Map.of("USD", BigDecimal.ONE, "EUR",
                BigDecimal.valueOf(1.125715), "RUB", BigDecimal.valueOf(0.1));

        Mockito.when(client.getHistoricalRates(LocalDate.EPOCH, appId)).thenReturn(response);

        final var rates = service.getHistoricalRates(LocalDate.EPOCH);

        assertEquals(0, rates.get("USD").compareTo(BigDecimal.ONE));
        assertEquals(0, rates.get("EUR").compareTo(BigDecimal.valueOf(1.125715)));
        assertEquals(0, rates.get("RUB").compareTo(BigDecimal.valueOf(0.1)));
    }

    @Test
    void getHistoricalRatesShouldTrowException() {
        OpenExchangeRatesResponse response = new OpenExchangeRatesResponse();
        response.rates = Map.of("USD", BigDecimal.ONE);
        LocalDate date = LocalDate.of(2122, 1, 1);

        Mockito.when(client.getHistoricalRates(date, appId)).thenReturn(response);

        try {
            service.getHistoricalRates(date);
            fail("Метод не выбросил исключение");
        } catch (InvalidDateException ignored) {
        }
    }

    @Test
    void compareLatestRateWithYesterdayShouldReturnOne() {
        var currencies = Map.of("RUB", "");
        var historical = new OpenExchangeRatesResponse();
        var latest = new OpenExchangeRatesResponse();

        historical.rates = Map.of("RUB", BigDecimal.valueOf(1.5));
        latest.rates = Map.of("RUB", BigDecimal.valueOf(7.77));

        Mockito.when(client.getCurrencies()).thenReturn(currencies);
        Mockito.when(client.getHistoricalRates(LocalDate.now().minusDays(1), appId)).thenReturn(historical);
        Mockito.when(client.getLatestRates(appId)).thenReturn(latest);
        assertEquals(1, service.compareLatestRateWithYesterday("RUB"));
    }

    @Test
    void compareLatestRateWithYesterdayShouldReturnMinus1() {
        var currencies = Map.of("RUB", "");
        var historical = new OpenExchangeRatesResponse();
        var latest = new OpenExchangeRatesResponse();

        historical.rates = Map.of("RUB", BigDecimal.valueOf(61));
        latest.rates = Map.of("RUB", BigDecimal.valueOf(60));

        Mockito.when(client.getCurrencies()).thenReturn(currencies);
        Mockito.when(client.getHistoricalRates(LocalDate.now().minusDays(1), appId)).thenReturn(historical);
        Mockito.when(client.getLatestRates(appId)).thenReturn(latest);
        assertEquals(-1, service.compareLatestRateWithYesterday("RUB"));
    }
}