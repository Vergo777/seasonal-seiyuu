package moe.vergo.seasonalseiyuuapi.domain;

import java.util.List;

public record Character(int id, String name, String role, String url, String imageUrl, List<Integer> seiyuuIds) {
}
