package moe.vergo.seasonalseiyuuapi.adapter.out.web;

import com.google.common.util.concurrent.RateLimiter;
import moe.vergo.seasonalseiyuuapi.adapter.out.web.dto.AnimeCharacterDataWrapperDto;
import moe.vergo.seasonalseiyuuapi.adapter.out.web.httpinterface.AnimeClient;
import moe.vergo.seasonalseiyuuapi.application.port.out.AnimeCharactersPort;
import moe.vergo.seasonalseiyuuapi.domain.Character;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RestAnimeCharactersAdapter implements AnimeCharactersPort {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final AnimeClient animeClient;
    private final RateLimiter rateLimiter;

    public RestAnimeCharactersAdapter(AnimeClient animeClient, RateLimiter rateLimiter) {
        this.animeClient = animeClient;
        this.rateLimiter = rateLimiter;
    }

    @Override
    @Cacheable("animeCharactersCache")
    public List<Character> getAnimeCharacters(int animeId) {
        return getCharactersFromEndpoint(animeId)
                .map(dto -> new Character(
                        dto.getCharacter().getMalId(),
                        dto.getCharacter().getName(),
                        dto.getRole(),
                        dto.getCharacter().getUrl(),
                        dto.getCharacter().getImages().getJpg().getImageUrl(),
                        getJapaneseSeiyuuIds(dto)
                ))
                .collect(Collectors.toList());
    }

    private Stream<AnimeCharacterDataWrapperDto> getCharactersFromEndpoint(int animeId) {
        LOGGER.info("Making request to anime characters endpoint for anime ID {}", animeId);
        rateLimiter.acquire();
        List<AnimeCharacterDataWrapperDto> data = animeClient.getCharactersById(animeId).getData();

        return data.stream();
    }

    private List<Integer> getJapaneseSeiyuuIds(AnimeCharacterDataWrapperDto wrapper) {
        return wrapper.getVoiceActors().stream()
                .filter(dto -> "Japanese".equals(dto.getLanguage()))
                .map(dto -> dto.getPerson().getMalId())
                .collect(Collectors.toList());
    }
}
