package elagin.dmitrii.ExchangeRateVisualizationService.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CurrencyRateController {

    @GetMapping("/api/v1/latestRate")
    public String displayLatestRateAsGIF(@RequestParam String code) {
        return "redirect://media3.giphy.com/media/dsWqtCdX0CLP2jmuxJ/200w_s.gif?cid=2100d7dd8005bb474858d16315cca72e3526acb38efb229a&rid=200w_s.gif&ct=g";
    }
}
