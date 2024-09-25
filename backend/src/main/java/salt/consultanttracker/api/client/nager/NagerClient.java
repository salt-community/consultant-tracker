package salt.consultanttracker.api.client.nager;

import salt.consultanttracker.api.client.nager.dto.RedDaysFromNagerDto;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@Data
public class NagerClient {
    private final WebClient CLIENT_URL;

    public NagerClient(@Value("${NAGER.URL}") String baseUrl) {
        CLIENT_URL = WebClient.create(baseUrl);
    }

    public List<RedDaysFromNagerDto> getRedDaysPerYear(int year, String[] countryCodes) {
        List<RedDaysFromNagerDto> dto = new ArrayList<>();
        for (String countryCode : countryCodes) {
           dto.addAll(Arrays.stream(Objects.requireNonNull(CLIENT_URL.get()
                    .uri("/{year}/{countryCode}", year, countryCode)
                    .retrieve()
                    .bodyToMono(RedDaysFromNagerDto[].class)
                    .block())).toList());
        }
        return dto;
    }
}
