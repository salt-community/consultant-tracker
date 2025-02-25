package salt.consultanttracker.api.client.timekeeper;

import io.netty.handler.ssl.SslContext;
import salt.consultanttracker.api.client.timekeeper.dto.TimekeeperRegisteredTimeListResponseDto;
import salt.consultanttracker.api.client.timekeeper.dto.TimekeeperRegisteredTimeResponseDto;
import salt.consultanttracker.api.client.timekeeper.dto.TimekeeperUserListResponseDto;
import salt.consultanttracker.api.client.timekeeper.dto.TimekeeperUserDto;
import io.netty.handler.ssl.SslContextBuilder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import salt.consultanttracker.api.exceptions.ExternalAPIException;
import salt.consultanttracker.api.messages.Messages;

import javax.net.ssl.SSLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
@Data
public class TimekeeperClient {
    private final WebClient CLIENT_URL;
    private final String HEADER;
    private static final Logger LOGGER = Logger.getLogger(TimekeeperClient.class.getName());

    public TimekeeperClient(@Value("${TIMEKEEPER.URL}") String baseUrl,
                            @Value("${TIMEKEEPER.AUTH}") String HEADER) {
        try{
            SslContext sslContext = SslContextBuilder.forClient()
                    .protocols("TLSv1.2", "TLSv1.3")
                    .build();
            HttpClient httpClient = HttpClient.create()
                    .secure(spec -> spec.sslContext(sslContext)
                            .handshakeTimeout(Duration.ofSeconds(60))
                            .closeNotifyFlushTimeout(Duration.ofSeconds(60))
                            .closeNotifyReadTimeout(Duration.ofSeconds(60)));
            CLIENT_URL = WebClient.builder()
                    .clientConnector(new ReactorClientHttpConnector(httpClient))
                    .baseUrl(baseUrl).exchangeStrategies(ExchangeStrategies.builder()
                            .codecs(configurer -> configurer
                                    .defaultCodecs()
                                    .maxInMemorySize(16 * 1024 * 1024))
                            .build())
                    .build();
        }catch(SSLException e){
            LOGGER.severe(Messages.SSL_EXCEPTION);
            throw new ExternalAPIException(Messages.SSL_EXCEPTION);
        }
        this.HEADER = HEADER;
    }

    public List<TimekeeperUserDto> getUsers() {
        try {
            int index = 0;
            int numOfPages = 1;
            List<TimekeeperUserDto> users = new ArrayList<>();
            while (index < numOfPages) {
                TimekeeperUserListResponseDto dto = CLIENT_URL.get()
                        .uri("api/v1/user?PageIndex={index}&pageSize=100&TagIDs=8561&TagIDs=8571", index)
                        .header("Authorization", HEADER)
                        .retrieve()
                        .bodyToMono(TimekeeperUserListResponseDto.class)
                        .block();
                assert dto != null;
                users.addAll(dto.timekeeperUsers().stream()
                        .map(tkUser -> new TimekeeperUserDto(
                                tkUser.firstName(),
                                tkUser.lastName(),
                                tkUser.email(),
                                tkUser.phone(),
                                tkUser.tags(),
                                tkUser.id(),
                                tkUser.isActive(),
                                tkUser.client(),
                                tkUser.responsiblePT(),
                                tkUser.isEmployee())).toList());
                if (index == 0) {
                    numOfPages = dto.totalPages();
                }
                index++;
            }
            return users;
        } catch (Exception e) {
            LOGGER.severe(Messages.TIMEKEEPER_FETCH_FAIL);
            throw new ExternalAPIException(Messages.TIMEKEEPER_FETCH_FAIL);
        }
    }

    public List<TimekeeperRegisteredTimeResponseDto> getTimeRegisteredByConsultant(Long id, Long countTimeRegistered) {
        try {
            int index = 0;
            int numOfPages = 1;
            List<TimekeeperRegisteredTimeResponseDto> registeredTime = new ArrayList<>();
            String startTime = LocalDateTime.now().minusMonths(2).toString();
            if (countTimeRegistered == 0) {
                startTime = "";
            }
            while (index < numOfPages) {
                TimekeeperRegisteredTimeListResponseDto dto = CLIENT_URL.get()
                        .uri("api/v1/TimeRegistration?UserId={id}&PageIndex={index}&pageSize=1000&FromDate={fromDate}", id, index, startTime)
                        .header("Authorization", HEADER)
                        .retrieve()
                        .onStatus(HttpStatus.NOT_FOUND::equals,
                                response -> Mono.empty())
                        .bodyToMono(TimekeeperRegisteredTimeListResponseDto.class)
                        .block();
                assert dto != null;
                if (dto.consultancyTime() == null) {
                    break;
                }
                registeredTime.addAll(dto.consultancyTime().stream()
                        .map(time -> new TimekeeperRegisteredTimeResponseDto(
                                time.totalHours(),
                                time.activityName(),
                                time.date(),
                                time.projectName())).toList());

                if (index == 0) {
                    numOfPages = dto.totalPages();
                }
                index++;
            }
            return registeredTime;
        } catch (Exception e) {
            LOGGER.severe(Messages.TIMEKEEPER_FETCH_FAIL);
            throw new ExternalAPIException(Messages.TIMEKEEPER_FETCH_FAIL);
        }
    }
}