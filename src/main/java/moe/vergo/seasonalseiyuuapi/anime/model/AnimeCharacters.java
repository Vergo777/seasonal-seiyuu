package moe.vergo.seasonalseiyuuapi.anime.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnimeCharacters implements Serializable {
    @JsonProperty("characters")
    private List<Character> characters;

    @JsonProperty("characters")
    public List<Character> getCharacters() {
        return characters;
    }

    @JsonProperty("characters")
    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("characters", characters).toString();
    }
}
