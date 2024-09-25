package salt.consultanttracker.api.client.timekeeper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record TimekeeperRegisteredTimeListResponseDto(@JsonProperty("results")
                                                      List<TimekeeperRegisteredTimeResponseDto> consultancyTime,
                                                      int totalPages) {
}
