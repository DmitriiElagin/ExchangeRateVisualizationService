package elagin.dmitrii.ExchangeRateVisualizationService.service.gif;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GiphyServiceIntegrationTest {

    @Autowired
    @Qualifier("giphyService")
    private GifService service;

    @Test
    public void getRandomGifUrlByTagShouldWorkCorrectly() {
        String startWith = "https://giphy.com/embed/";

        var url = service.getRandomGifUrlByTag("rich");
        assertNotNull(url);
        assertFalse(url.isBlank());
        assertTrue(url.startsWith(startWith));

        var url2 = service.getRandomGifUrlByTag("rich");
        assertNotEquals(url, url2);
    }

}