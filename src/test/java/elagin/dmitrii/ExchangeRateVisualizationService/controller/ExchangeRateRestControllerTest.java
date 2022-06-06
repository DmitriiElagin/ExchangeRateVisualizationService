package elagin.dmitrii.ExchangeRateVisualizationService.controller;

import elagin.dmitrii.ExchangeRateVisualizationService.error.InvalidCurrencyCodeException;
import elagin.dmitrii.ExchangeRateVisualizationService.service.exchange_rate_tracking.ExchangeRateTrackingService;
import elagin.dmitrii.ExchangeRateVisualizationService.service.gif.GifService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExchangeRateRestController.class)
class ExchangeRateRestControllerTest {

    @Value("${service.giphy.tag.broke}")
    private String tagBroke;

    @Value("${service.giphy.tag.rich}")
    private String tagRich;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ExchangeRateTrackingService exchangeRateTrackingService;

    @MockBean
    GifService gifService;

    @Test
    public void compareLatestRateAndGetGifUrlShouldReturnRich() throws Exception {
        Mockito.when(exchangeRateTrackingService.compareLatestRateWithYesterday("EUR")).thenReturn(1);
        Mockito.when(gifService.getRandomGifUrlByTag(tagRich))
                .thenReturn(tagRich);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/latestRate?code=EUR"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.url", is(tagRich)));

    }

    @Test
    public void compareLatestRateAndGetGifUrlShouldReturnBroke() throws Exception {
        Mockito.when(exchangeRateTrackingService.compareLatestRateWithYesterday("EUR")).thenReturn(-1);
        Mockito.when(gifService.getRandomGifUrlByTag(tagBroke))
                .thenReturn(tagBroke);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/latestRate?code=EUR"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.url", is(tagBroke)));
    }

    @Test
    public void compareLatestRateAndGetGifUrlShouldReturn400() throws Exception {
        final var message = "message";

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/latestRate?code="))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error", is(ErrorController.CONSTRAINT_VIOLATION_ERROR)))
                .andExpect(jsonPath("$.errors", notNullValue()));

        Mockito.when(exchangeRateTrackingService.compareLatestRateWithYesterday("..."))
                .thenThrow(new InvalidCurrencyCodeException(message));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/latestRate?code=..."))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.message", is(message)));
    }

    @Test
    public void compareLatestRateAndGetGifUrlShouldReturn500() throws Exception {
        final var message = "message";

        Mockito.when(exchangeRateTrackingService.compareLatestRateWithYesterday("USD"))
                .thenThrow(new RuntimeException(message));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/latestRate?code=USD"))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.status", is(500)))
                .andExpect(jsonPath("$.message", is(message)));
    }

    @Test
    public void getCurrenciesShouldWorkSuccessfully() throws Exception {
        final var currencies = Map.of("USD", "Dollar", "EUR", "Euro", "RUB", "Ruble");

        Mockito.when(exchangeRateTrackingService.getCurrencies()).thenReturn(currencies);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/currencies"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.USD", is(currencies.get("USD"))))
                .andExpect(jsonPath("$.RUB", is(currencies.get("RUB"))));
    }
}