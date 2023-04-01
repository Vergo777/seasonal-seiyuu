package moe.vergo.seasonalseiyuuapi.adapter.out.web;

import com.google.common.util.concurrent.RateLimiter;
import moe.vergo.seasonalseiyuuapi.adapter.out.web.dto.SeiyuuDataWrapperDto;
import moe.vergo.seasonalseiyuuapi.adapter.out.web.dto.SeiyuuResponseDto;
import moe.vergo.seasonalseiyuuapi.adapter.out.web.dto.SeiyuuVoicesDto;
import moe.vergo.seasonalseiyuuapi.adapter.out.web.httpinterface.SeiyuuClient;
import moe.vergo.seasonalseiyuuapi.application.port.out.SeiyuuPort;
import moe.vergo.seasonalseiyuuapi.domain.Role;
import moe.vergo.seasonalseiyuuapi.domain.Seiyuu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.stream.Collectors;

public class RestSeiyuuAdapter implements SeiyuuPort {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SeiyuuClient seiyuuClient;
    private final RateLimiter rateLimiter;

    public RestSeiyuuAdapter(SeiyuuClient seiyuuClient, RateLimiter rateLimiter) {
        this.seiyuuClient = seiyuuClient;
        this.rateLimiter = rateLimiter;
    }

    @Override
    @Cacheable("seiyuuCache")
    public Seiyuu getSeiyuuDetails(int seiyuuId) {
        SeiyuuDataWrapperDto dto = getSeiyuuDetailsFromEndpoint(seiyuuId);

        return new Seiyuu(
                dto.getMalId(),
                dto.getUrl(),
                dto.getImages().getJpg().getImageUrl(),
                dto.getName(),
                dto.getVoices().stream()
                        .map(this::toDomainRole)
                        .collect(Collectors.toList())
        );
    }

    private SeiyuuDataWrapperDto getSeiyuuDetailsFromEndpoint(int seiyuuId) {
        LOGGER.info("Making request to seiyuu endpoint for id {}", seiyuuId);
        rateLimiter.acquire();
        int attempts = 1;

        while(attempts <= 3) {
            try {
                return seiyuuClient.getFullDetailsById(seiyuuId).getData();
            } catch (HttpServerErrorException | ResourceAccessException |
                     WebClientResponseException | IllegalArgumentException exception) {
                LOGGER.error("Request for seiyuu ID {} failed, retrying", seiyuuId, exception);
                attempts++;
            }
        }

        throw new RuntimeException("Failed to fetch details from endpoint for seiyuu ID " + seiyuuId);
    }

    private Role toDomainRole(SeiyuuVoicesDto seiyuuVoicesDto) {
        return new Role(
                seiyuuVoicesDto.getAnime().getTitle(),
                seiyuuVoicesDto.getAnime().getUrl(),
                seiyuuVoicesDto.getAnime().getMalId(),
                seiyuuVoicesDto.getCharacter().getName(),
                seiyuuVoicesDto.getCharacter().getUrl(),
                seiyuuVoicesDto.getCharacter().getImages().getJpg().getImageUrl()
        );
    }
}
