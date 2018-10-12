package moe.vergo.seasonalseiyuuapi.seiyuu.service;

import moe.vergo.seasonalseiyuuapi.anime.model.AnimeCharacters;
import moe.vergo.seasonalseiyuuapi.anime.model.Character;
import moe.vergo.seasonalseiyuuapi.anime.model.CharacterSeiyuuDetails;
import moe.vergo.seasonalseiyuuapi.anime.model.FullAnimeDetails;
import moe.vergo.seasonalseiyuuapi.anime.service.AnimeService;
import moe.vergo.seasonalseiyuuapi.seiyuu.model.Seiyuu;
import moe.vergo.seasonalseiyuuapi.seiyuu.repository.SeiyuuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeiyuuServiceImpl implements SeiyuuService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SeiyuuServiceImpl.class);
    private static final String SEIYUU_LANGUAGE = "Japanese";

    @Autowired
    private SeiyuuRepository seiyuuRepository;

    @Autowired
    private AnimeService animeService;

    public Seiyuu getSeiyuuInfo(int seiyuuID) {
        return seiyuuRepository.getSeiyuuInfo(seiyuuID);
    }

    public List<Seiyuu> getSeiyuusForCurrentlyAiringAnime() {
        List<FullAnimeDetails> currentlyAiringAnimeDetails = animeService.getDetailsForCurrentlyAiringAnime();
        List<Integer> seiyuuIDs = currentlyAiringAnimeDetails.stream().map(FullAnimeDetails::getAnimeCharacters).map(AnimeCharacters::getCharacters)
            .flatMap(List::stream).map(Character::getCharacterSeiyuuDetailsList)
            .flatMap(List::stream).filter(characterSeiyuuDetails -> characterSeiyuuDetails.getLanguage().equals(SEIYUU_LANGUAGE))
            .map(CharacterSeiyuuDetails::getMalId).collect(Collectors.toList());

        int index = 1;
        List<Seiyuu> seasonalSeiyuus = new ArrayList<>();
        for(Integer seiyuuID : seiyuuIDs) {
            LOGGER.info("Fetching details for seiyuu ID {}, {} of {} total seiyuus", seiyuuID, index, seiyuuIDs.size());
            Seiyuu seiyuu = getSeiyuuInfo(seiyuuID);
            seasonalSeiyuus.add(seiyuu);

            index++;
        }

        return seasonalSeiyuus;
    }


}
