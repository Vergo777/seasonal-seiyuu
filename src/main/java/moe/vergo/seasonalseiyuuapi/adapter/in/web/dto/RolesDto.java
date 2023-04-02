package moe.vergo.seasonalseiyuuapi.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonView;

import java.io.Serializable;

public class RolesDto implements Serializable {
    @JsonView(Views.Simple.class)
    private String characterName;
    @JsonView(Views.Simple.class)
    private String characterThumbnail;
    @JsonView(Views.Simple.class)
    private String seriesName;
    @JsonView(Views.Simple.class)
    private int seriesID;

    public RolesDto(String characterName, String characterThumbnail, String seriesName, int seriesID) {
        this.characterName = characterName;
        this.characterThumbnail = characterThumbnail;
        this.seriesName = seriesName;
        this.seriesID = seriesID;
    }

    public String getCharacterName() {
        return characterName;
    }

    public String getCharacterThumbnail() {
        return characterThumbnail;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public int getSeriesID() {
        return seriesID;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public void setCharacterThumbnail(String characterThumbnail) {
        this.characterThumbnail = characterThumbnail;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public void setSeriesID(int seriesID) {
        this.seriesID = seriesID;
    }

    @Override
    public String toString() {
        return "RolesDto{" +
                "characterName='" + characterName + '\'' +
                ", characterThumbnail='" + characterThumbnail + '\'' +
                ", seriesName='" + seriesName + '\'' +
                ", seriesID=" + seriesID +
                '}';
    }
}
