package com.example.backend.tag;

import com.example.backend.client.timekeeper.dto.TimekeeperUserDto;
import lombok.Data;

import java.util.List;

@Data
public class Tag {
    private String name;

    public static String extractCountryTagFromTkUser(TimekeeperUserDto tkUser) {
        List<Tag> countryTagList = tkUser.tags()
                .stream()
                .filter(el -> el.getName().trim().equals("Norge")
                        || el.getName().trim().equals("Sverige"))
                .toList();
        return !countryTagList.isEmpty() ? countryTagList.getFirst().getName().trim() : "Sverige";
    }
}
