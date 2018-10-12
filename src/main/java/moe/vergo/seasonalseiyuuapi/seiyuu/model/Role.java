package moe.vergo.seasonalseiyuuapi.seiyuu.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import moe.vergo.seasonalseiyuuapi.anime.model.Anime;
import moe.vergo.seasonalseiyuuapi.anime.model.Character;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Role implements Serializable {

    @JsonProperty("role")
    private String role;
    @JsonProperty("anime")
    private Anime anime;
    @JsonProperty("character")
    private Character character;

    @JsonProperty("role")
    public String getRole() {
        return role;
    }

    @JsonProperty("role")
    public void setRole(String role) {
        this.role = role;
    }

    @JsonProperty("anime")
    public Anime getAnime() {
        return anime;
    }

    @JsonProperty("anime")
    public void setAnime(Anime anime) {
        this.anime = anime;
    }

    @JsonProperty("character")
    public Character getCharacter() {
        return character;
    }

    @JsonProperty("character")
    public void setCharacter(Character character) {
        this.character = character;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("role", role).append("anime", anime).append("character", character).toString();
    }

}