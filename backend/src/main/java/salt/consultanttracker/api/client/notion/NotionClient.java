package salt.consultanttracker.api.client.notion;

import org.springframework.core.ParameterizedTypeReference;
import salt.consultanttracker.api.client.notion.dtos.ConsultantsNProxyDto;
import salt.consultanttracker.api.client.notion.dtos.ResponsiblePTDto;
import salt.consultanttracker.api.consultant.ConsultantService;
import salt.consultanttracker.api.consultant.dto.ConsultantGIThubDto;
import salt.consultanttracker.api.exceptions.ExternalAPIException;
import salt.consultanttracker.api.messages.Messages;
import salt.consultanttracker.api.responsiblept.ResponsiblePTService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.rmi.UnexpectedException;
import java.util.*;
import java.util.logging.Logger;


@Service
@Data
public class NotionClient {
    private final WebClient CLIENT_URL;
    private final String HEADER_AUTH;
    private final ResponsiblePTService saltUserService;
    private final ConsultantService consultantService;
    private static final Logger LOGGER = Logger.getLogger(NotionClient.class.getName());
    private final ResponsiblePTService responsiblePTService;

    public NotionClient(@Value("${NOTION_PROXY.URL}") String baseUrl,
                        @Value("${NOTION_PROXY.AUTH}") String HEADER_AUTH,
                        ResponsiblePTService saltUserService,
                        ConsultantService consultantService, ResponsiblePTService responsiblePTService) {
        this.HEADER_AUTH = HEADER_AUTH;
        CLIENT_URL = WebClient.builder().baseUrl(baseUrl)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.set("X-aPI-KeY", HEADER_AUTH);
                    httpHeaders.set("Content-Type", "application/json");
                }).exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer
                                .defaultCodecs()
                                .maxInMemorySize(16 * 1024 * 1024))
                        .build()).build();

        this.saltUserService = saltUserService;
        this.consultantService = consultantService;
        this.responsiblePTService = responsiblePTService;
    }


    @Scheduled(cron = "0 0 2 * * 4", zone = "Europe/Stockholm")
    public void getResponsiblePTFromNotion() {
        try {
            List<ResponsiblePTDto> dto = CLIENT_URL.get()
                    .uri("/responsible")
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<ResponsiblePTDto>>() {
                    })
                    .block();
            if (dto != null && !dto.isEmpty()) {
                responsiblePTService.updateResponsiblePt(dto);
            } else {
                throw new UnexpectedException(Messages.UNEXPECTED_RESPONSE_EXCEPTION_NPROXY);
            }
        } catch (Exception e) {
            LOGGER.severe(Messages.NOTION_PROXY_FETCH_FAIL);
            throw new ExternalAPIException(Messages.NOTION_PROXY_FETCH_FAIL);
        }
    }

    @Scheduled(cron = "0 0 2 * * 4", zone = "Europe/Stockholm")
    public void matchResponsiblePTForConsultants() {
        try {
            List<ConsultantsNProxyDto> dto = CLIENT_URL.get()
                    .uri("/consultants")
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<ConsultantsNProxyDto>>() {
                    })
                    .block();
            if (dto != null && !dto.isEmpty()) {
                consultantService.updateConsultantsTableWithNotionData(dto);
                System.out.println("consultantsnproxy------------------>: " + dto);
            } else {
                throw new UnexpectedException(Messages.UNEXPECTED_RESPONSE_EXCEPTION_NPROXY);
            }
        } catch (Exception e) {
            LOGGER.severe(Messages.NOTION_PROXY_FETCH_FAIL);
            throw new ExternalAPIException(Messages.NOTION_PROXY_FETCH_FAIL);
        }
    }

    //alu
    @Scheduled(cron = "0 0 2 * * 4", zone = "Europe/Stockholm")
    public String getClientGitHubFromNotion(String notionid) {
        try {
            List<ConsultantGIThubDto> dto = Collections.singletonList(CLIENT_URL.get()
                    .uri("/developers/" + notionid + "/scores")
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<ConsultantGIThubDto>() {
                    })
                    .block());
            if (dto != null && !dto.isEmpty()) {
                System.out.println(dto);
                return dto.toString();
            } else {
                throw new UnexpectedException(Messages.UNEXPECTED_RESPONSE_EXCEPTION_NPROXY);
            }
        } catch (Exception e) {
            LOGGER.severe(Messages.NOTION_PROXY_FETCH_FAIL);
            throw new ExternalAPIException(Messages.NOTION_PROXY_FETCH_FAIL);
        }
    }
}



