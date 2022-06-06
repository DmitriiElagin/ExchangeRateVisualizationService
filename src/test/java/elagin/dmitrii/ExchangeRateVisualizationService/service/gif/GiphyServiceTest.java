package elagin.dmitrii.ExchangeRateVisualizationService.service.gif;

import elagin.dmitrii.ExchangeRateVisualizationService.model.giphy.Data;
import elagin.dmitrii.ExchangeRateVisualizationService.model.giphy.GiphyResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@WebMvcTest(GiphyService.class)
class GiphyServiceTest {

    @Value("${service.giphy.api-key}")
    private String apiKey;

    @MockBean
    private GiphyClient client;

    @Autowired
    @Qualifier("giphyService")
    private GifService service;

    @Test
    public void getRandomGifUrlByTagShouldWorkCorrectly() {
        String tag = "test";

        var response = new GiphyResponse();
        response.data = new Data();
        response.data.embed_url = tag;

        Mockito.when(client.getRandomGifByTag(apiKey, tag)).thenReturn(response);

        var url = service.getRandomGifUrlByTag(tag);
        assertNotNull(url);
        assertEquals(url, tag);
    }
}