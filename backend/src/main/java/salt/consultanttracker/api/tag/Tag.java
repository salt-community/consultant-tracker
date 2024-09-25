package salt.consultanttracker.api.tag;

import salt.consultanttracker.api.client.timekeeper.dto.TimekeeperUserDto;
import lombok.Data;

import java.util.List;

@Data
public class Tag {
    private String name;

    public static String extractCountryTagFromTimekeeperUserDto(TimekeeperUserDto tkUser) {
        List<Tag> countryTagList = tkUser.tags()
                .stream()
                .filter(el -> el.getName().trim().equals("Norge")
                        || el.getName().trim().equals("Sverige"))
                .toList();
        return !countryTagList.isEmpty() ? countryTagList.getFirst().getName().trim() : "Sverige";
    }
}
