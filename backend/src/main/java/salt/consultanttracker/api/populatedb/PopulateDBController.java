package salt.consultanttracker.api.populatedb;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import salt.consultanttracker.api.client.notion.NotionClient;
import salt.consultanttracker.api.consultant.ConsultantService;
import salt.consultanttracker.api.meetings.MeetingsScheduleService;
import salt.consultanttracker.api.reddays.RedDayService;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/populate-db")
@CrossOrigin
@RequiredArgsConstructor
@Tag(name = "Populate DB")
public class PopulateDBController {
    private final RedDayService redDayService;
    private final NotionClient notionClient;
    private final MeetingsScheduleService meetingsScheduleService;
    private final ConsultantService consultantService;
    private static final Logger LOGGER = Logger.getLogger(PopulateDBController.class.getName());

    @GetMapping
    @Operation(description = "Populates the database, fetching from other services such as Red Days, Timekeeper and Notion")
    public ResponseEntity<String> getAllConsultants() {
        LOGGER.info("Starting to populate db");
        redDayService.getRedDaysFromNager();
        LOGGER.info("Red days fetched from Nager");
        consultantService.fetchDataFromTimekeeper();
        LOGGER.info("Consultants and registered time fetched from Timekeeper");

        notionClient.getResponsiblePTFromNotion();
        LOGGER.info("PTs table populated");
        notionClient.matchResponsiblePTForConsultants();
        LOGGER.info("PTs matched with consultants based on Notion");

        meetingsScheduleService.assignMeetingsDatesForActiveConsultants();
        LOGGER.info("Meetings schedule updated");
        LOGGER.info("Finished populating db");
        return ResponseEntity.ok("Finished populating db");
    }

}
