package elagin.dmitrii.ExchangeRateVisualizationService.service.gif;

import elagin.dmitrii.ExchangeRateVisualizationService.model.giphy.GiphyResponse;
import feign.Param;
import feign.RequestLine;

public interface GiphyClient {

    @RequestLine("GET/random?api_key={api_key}&tag={tag}")
    GiphyResponse getRandomGifByTag(@Param("api_key") String apiKey, @Param("tag") String tag);
}
