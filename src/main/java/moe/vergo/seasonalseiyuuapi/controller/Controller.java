package moe.vergo.seasonalseiyuuapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import moe.vergo.seasonalseiyuuapi.anime.model.Anime;
import moe.vergo.seasonalseiyuuapi.anime.model.AnimeCharacters;
import moe.vergo.seasonalseiyuuapi.anime.model.FullAnimeDetails;
import moe.vergo.seasonalseiyuuapi.anime.service.AnimeService;
import moe.vergo.seasonalseiyuuapi.seiyuu.model.Seiyuu;
import moe.vergo.seasonalseiyuuapi.seiyuu.service.SeiyuuService;
import moe.vergo.seasonalseiyuuapi.summary.model.SeasonalSeiyuu;
import moe.vergo.seasonalseiyuuapi.summary.service.SummaryService;
import moe.vergo.seasonalseiyuuapi.summary.views.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
public class Controller {

    @Autowired
    private AnimeService animeService;

    @Autowired
    private SeiyuuService seiyuuService;

    @Autowired
    private SummaryService summaryService;

    @GetMapping("/anime/{ID}")
    public Anime getAnimeInfo(@PathVariable int ID) {
        return animeService.getAnimeInfo(ID);
    }

    @GetMapping("/animeCharacters/{ID}")
    public AnimeCharacters getAnimeCharactersInfo(@PathVariable int ID) {
        return animeService.getAnimeCharactersInfo(ID);
    }

    @GetMapping("/currentlyAiringDetails")
    public List<FullAnimeDetails> getCurrentlyAiringDetails() {
        return animeService.getDetailsForCurrentlyAiringAnime();
    }

    @GetMapping("/seiyuuDetails/{ID}")
    public SeasonalSeiyuu getSeiyuuInfo(@PathVariable int ID) {
        return summaryService.getSeiyuuDetailsMap().get(ID);
    }

    @GetMapping("/seasonalSeiyuus")
    public List<Seiyuu> getSeasonalSeiyuus() {
        return seiyuuService.getSeiyuusForCurrentlyAiringAnime();
    }

    @GetMapping("/currentSeasonSummary")
    @JsonView(Views.Simple.class)
    public List<SeasonalSeiyuu> getCurrentSeasonSummary() {
        return summaryService.getCurrentSeasonSummary();
    }
}
