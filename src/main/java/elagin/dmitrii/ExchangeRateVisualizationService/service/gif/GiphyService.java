package elagin.dmitrii.ExchangeRateVisualizationService.service.gif;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GiphyService implements GifService {

    @Value("${service.giphy.api-key}")
    private String apiKey;

    private final GiphyClient client;

    public GiphyService(GiphyClient client) {
        this.client = client;
    }

    @Override
    public String getRandomGifUrlByTag(String tag) {
        return client
                .getRandomGifByTag(apiKey, tag)
                .data
                .images
                .original
                .url;
    }
}
