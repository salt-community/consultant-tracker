package salt.consultanttracker.api.meetings;

import salt.consultanttracker.api.client.notion.NotionClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/meetings")
@RequiredArgsConstructor
@CrossOrigin
public class MeetingsController {
    private final MeetingsScheduleService meetingsScheduleService;
    private final NotionClient notionClient;

    @GetMapping
    public void saveMeetings() {
        meetingsScheduleService.assignMeetingsDatesForActiveConsultants();
    }

    @GetMapping("/notion")
    public void testNotion() {
        notionClient.getUsersFromNotion();
    }
}
