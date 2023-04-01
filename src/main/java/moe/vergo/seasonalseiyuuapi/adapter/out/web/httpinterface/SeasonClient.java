package moe.vergo.seasonalseiyuuapi.adapter.out.web.httpinterface;

import moe.vergo.seasonalseiyuuapi.adapter.out.web.dto.SeasonalAnimeResponseDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.Map;

@HttpExchange(url = "/seasons", accept = "application/json", contentType = "application/json")
public interface SeasonClient {
    @GetExchange("/{year}/{season}")
    SeasonalAnimeResponseDto getByYearAndSeason(@PathVariable("year") int year, @PathVariable("season") String season, @RequestParam Map<String, Integer> queryParams);
}
