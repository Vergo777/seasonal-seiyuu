package moe.vergo.seasonalseiyuuapi.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonView;

import java.io.Serializable;
import java.util.List;

public class CurrentSeasonSummaryItemDto implements Serializable {
    @JsonView(Views.Simple.class)
    private String name;
    @JsonView(Views.Simple.class)
    private String image;
    @JsonView(Views.Simple.class)
    private int id;
    @JsonView(Views.Simple.class)
    private List<RolesDto> currentSeasonRolesArray;
    @JsonView(Views.Detailed.class)
    private List<RolesDto> overallRolesArray;

    public CurrentSeasonSummaryItemDto(String name, String image, int id, List<RolesDto> currentSeasonRolesArray, List<RolesDto> overallRolesArray) {
        this.name = name;
        this.image = image;
        this.id = id;
        this.currentSeasonRolesArray = currentSeasonRolesArray;
        this.overallRolesArray = overallRolesArray;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getId() {
        return id;
    }

    public List<RolesDto> getCurrentSeasonRolesArray() {
        return currentSeasonRolesArray;
    }

    public List<RolesDto> getOverallRolesArray() {
        return overallRolesArray;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCurrentSeasonRolesArray(List<RolesDto> currentSeasonRolesArray) {
        this.currentSeasonRolesArray = currentSeasonRolesArray;
    }

    public void setOverallRolesArray(List<RolesDto> overallRolesArray) {
        this.overallRolesArray = overallRolesArray;
    }
}
