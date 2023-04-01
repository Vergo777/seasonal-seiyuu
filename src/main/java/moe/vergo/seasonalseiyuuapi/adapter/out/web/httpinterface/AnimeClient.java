package moe.vergo.seasonalseiyuuapi.adapter.out.web.httpinterface;

import moe.vergo.seasonalseiyuuapi.adapter.out.web.dto.AnimeCharactersResponseDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(url = "/anime")
public interface AnimeClient {
    @GetExchange("/{id}/characters")
    AnimeCharactersResponseDto getCharactersById(@PathVariable("id") int id);
}
