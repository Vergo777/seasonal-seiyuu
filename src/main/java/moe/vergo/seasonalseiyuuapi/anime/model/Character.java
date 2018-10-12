package moe.vergo.seasonalseiyuuapi.anime.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Character implements Serializable {

    @JsonProperty("mal_id")
    private Integer malId;
    @JsonProperty("url")
    private String url;
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("name")
    private String name;
    @JsonProperty("role")
    private String role;
    @JsonProperty("voice_actors")
    private List<CharacterSeiyuuDetails> characterSeiyuuDetailsList;

    @JsonProperty("mal_id")
    public Integer getMalId() {
        return malId;
    }

    @JsonProperty("mal_id")
    public void setMalId(Integer malId) {
        this.malId = malId;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty("image_url")
    public String getImageUrl() {
        return imageUrl;
    }

    @JsonProperty("image_url")
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("role")
    public String getRole() {
        return role;
    }

    @JsonProperty("role")
    public void setRole(String role) {
        this.role = role;
    }

    @JsonProperty("voice_actors")
    public List<CharacterSeiyuuDetails> getCharacterSeiyuuDetailsList() {
        return this.characterSeiyuuDetailsList;
    }

    @JsonProperty("voice_actors")
    public void setCharacterSeiyuuDetailsList(List<CharacterSeiyuuDetails> characterSeiyuuDetailsList) {
        this.characterSeiyuuDetailsList = characterSeiyuuDetailsList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("malId", malId).append("url", url).append("imageUrl", imageUrl).append("name", name).append("role", role).append("voiceActors", characterSeiyuuDetailsList).toString();
    }

}