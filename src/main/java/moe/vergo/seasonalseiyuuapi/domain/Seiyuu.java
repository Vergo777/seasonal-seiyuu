package moe.vergo.seasonalseiyuuapi.domain;

import java.util.List;

public record Seiyuu(int id, String url, String imageUrl, String name, List<Role> roles) {
}
