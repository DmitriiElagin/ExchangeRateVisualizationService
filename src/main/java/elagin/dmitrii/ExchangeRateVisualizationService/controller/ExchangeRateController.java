package elagin.dmitrii.ExchangeRateVisualizationService.controller;

import elagin.dmitrii.ExchangeRateVisualizationService.service.exchange_rate_tracking.ExchangeRateTrackingService;
import elagin.dmitrii.ExchangeRateVisualizationService.service.gif.GifService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Controller
@Validated
public class ExchangeRateController {
    public final static String TAG_RICH = "rich";
    public final static String TAG_BROKE = "broke";

    @Value("${service.giphy.tag.broke}")
    private String tagBroke;

    @Value("${service.giphy.tag.rich}")
    private String tagRich;

    private final ExchangeRateTrackingService exchangeRateTrackingService;
    private final GifService gifService;

    public ExchangeRateController(ExchangeRateTrackingService exchangeRateTrackingService, GifService gifService) {
        this.exchangeRateTrackingService = exchangeRateTrackingService;
        this.gifService = gifService;
    }

    @GetMapping("latestRate")
    public String compareLatestRateAndShowGif(@RequestParam @NotBlank @Size(min = 3, max = 3) String code, Model model) {
        String url;

        if (exchangeRateTrackingService.compareLatestRateWithYesterday(code) <= 0) {
            url = gifService.getRandomGifUrlByTag(tagBroke);
        } else {
            url = gifService.getRandomGifUrlByTag(tagRich);
        }
        model.addAttribute("url", url);

        return "gif";
    }
}