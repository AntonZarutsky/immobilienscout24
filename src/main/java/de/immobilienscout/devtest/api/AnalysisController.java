package de.immobilienscout.devtest.api;

import de.immobilienscout.devtest.api.dto.AnalysisResult;
import de.immobilienscout.devtest.service.AnalysisService;
import de.immobilienscout.devtest.utils.exception.BadRequestException;
import java.net.MalformedURLException;
import java.net.URL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AnalysisController {

    @Autowired
    private AnalysisService analyzerService;

    @RequestMapping(value = "/analysis", method = RequestMethod.POST)
    public AnalysisResult analyze(@ModelAttribute URL pageUrl) {
        log.info("Analyzing {} web page", pageUrl);
        return analyzerService.analyze(pageUrl);
    }

    @ModelAttribute
    public URL pageUrl(@RequestParam String pageUrl)  {
        try {
            return new URL(pageUrl);
        }catch (MalformedURLException e) {
            throw new BadRequestException("Bad request parameter","Page url for analysis is incorrect. url=" + pageUrl);
        }
    }
}









