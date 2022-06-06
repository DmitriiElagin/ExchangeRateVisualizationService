package elagin.dmitrii.ExchangeRateVisualizationService.controller;

import elagin.dmitrii.ExchangeRateVisualizationService.model.Response;
import elagin.dmitrii.ExchangeRateVisualizationService.service.exchange_rate_tracking.ExchangeRateTrackingService;
import elagin.dmitrii.ExchangeRateVisualizationService.service.gif.GifService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@Validated
public class ExchangeRateRestController {
    public final static String TAG_RICH = "rich";
    public final static String TAG_BROKE = "broke";

    @Value("${service.giphy.tag.broke}")
    private String tagBroke;

    @Value("${service.giphy.tag.rich}")
    private String tagRich;

    private final ExchangeRateTrackingService exchangeRateTrackingService;
    private final GifService gifService;

    public ExchangeRateRestController(ExchangeRateTrackingService exchangeRateTrackingService, GifService gifService) {
        this.exchangeRateTrackingService = exchangeRateTrackingService;
        this.gifService = gifService;
    }

    @GetMapping("latestRate")
    public Response compareLatestRateAndGetGifUrl(@RequestParam @NotBlank @Size(min = 3, max = 3) String code) {
        String url;

        if (exchangeRateTrackingService.compareLatestRateWithYesterday(code) <= 0) {
            url = gifService.getRandomGifUrlByTag(tagBroke);
        } else {
            url = gifService.getRandomGifUrlByTag(tagRich);
        }

        return new Response(url);
    }

    @GetMapping("currencies")
    @ResponseBody
    public Map<String, String> getCurrencies() {
        return exchangeRateTrackingService.getCurrencies();
    }
}
