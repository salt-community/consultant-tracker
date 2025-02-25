package salt.consultanttracker.api.tag;

import salt.consultanttracker.api.client.timekeeper.dto.TimekeeperUserDto;
import lombok.Data;

import java.util.List;

import static salt.consultanttracker.api.utils.Country.NORWAY;
import static salt.consultanttracker.api.utils.Country.SWEDEN;

@Data
public class Tag {
    private String name;

    public static String extractCountryTagFromTimekeeperUserDto(TimekeeperUserDto tkUser) {
        List<Tag> countryTagList = tkUser.tags()
                .stream()
                .filter(el -> el.getName().trim().equals(NORWAY.country)
                        || el.getName().trim().equals(SWEDEN.country))
                .toList();
        return !countryTagList.isEmpty() ? countryTagList.getFirst().getName().trim() : SWEDEN.country;
    }
}