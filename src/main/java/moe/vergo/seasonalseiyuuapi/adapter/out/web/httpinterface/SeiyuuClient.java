package moe.vergo.seasonalseiyuuapi.adapter.out.web.httpinterface;

import moe.vergo.seasonalseiyuuapi.adapter.out.web.dto.SeiyuuResponseDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(url = "/people")
public interface SeiyuuClient {
    @GetExchange("/{id}/full")
    SeiyuuResponseDto getFullDetailsById(@PathVariable("id") int id);
}
