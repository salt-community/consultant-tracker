package salt.consultanttracker.api.client.nager;

import salt.consultanttracker.api.client.nager.dto.RedDaysFromNagerDto;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import salt.consultanttracker.api.exceptions.ExternalAPIException;
import salt.consultanttracker.api.messages.Messages;
import salt.consultanttracker.api.reddays.RedDayService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@Service
@Data
public class NagerClient {
    private final WebClient CLIENT_URL;
    private static final Logger LOGGER = Logger.getLogger(NagerClient.class.getName());

    public NagerClient(@Value("${NAGER.URL}") String baseUrl) {
        CLIENT_URL = WebClient.create(baseUrl);
    }

    public List<RedDaysFromNagerDto> getRedDaysPerYear(int year, String[] countryCodes) throws ExternalAPIException {
        List<RedDaysFromNagerDto> dto = new ArrayList<>();
        try{
            for (String countryCode : countryCodes) {
                dto.addAll(Arrays.stream(Objects.requireNonNull(CLIENT_URL.get()
                        .uri("/{year}/{countryCode}", year, countryCode)
                        .retrieve()
                        .bodyToMono(RedDaysFromNagerDto[].class)
                        .block())).toList());
            }
        } catch(Exception e){
            LOGGER.severe(Messages.NAGER_FETCH_FAIL);
            throw new ExternalAPIException(Messages.NAGER_FETCH_FAIL);
        }
        return dto;
    }
}
