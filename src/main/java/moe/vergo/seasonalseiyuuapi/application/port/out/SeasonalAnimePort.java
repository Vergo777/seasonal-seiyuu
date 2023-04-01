package moe.vergo.seasonalseiyuuapi.application.port.out;

import moe.vergo.seasonalseiyuuapi.domain.Anime;
import moe.vergo.seasonalseiyuuapi.domain.Season;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface SeasonalAnimePort {
    List<Anime> getSeasonalAnime(int year, Season season);
}
