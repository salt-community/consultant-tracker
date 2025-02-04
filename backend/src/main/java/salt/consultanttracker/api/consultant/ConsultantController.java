package salt.consultanttracker.api.consultant;
import salt.consultanttracker.api.consultant.dto.*;
import salt.consultanttracker.api.demo.DemoConsultantService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/consultants")
@RequiredArgsConstructor
@CrossOrigin
public class ConsultantController {

    private final ConsultantService consultantService;
    private final DemoConsultantService demoConsultantService;
    @Value("${app.mode}")
    private String appMode;

    @GetMapping
    public ResponseEntity<ConsultantResponseListDto> getConsultantsAndRegisteredTime(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "", required = false) String name,
            @RequestParam(defaultValue = "", required = false) List<String> client,
            @RequestParam(defaultValue = "", required = false) List<String> pt,
            @RequestParam(defaultValue = "false", required = false) boolean includePgps) {
        if ("demo".equalsIgnoreCase(appMode)) {
            System.out.println("IN DEMO MODE");
            return ResponseEntity.ok(demoConsultantService.getAllDemoConsultantDtos(page, pageSize, name, pt, client, includePgps));
        }
        ConsultantResponseListDto consultantsResponse = consultantService.getAllConsultantsDto(page, pageSize, name, pt, client, includePgps);
        return ResponseEntity.ok(consultantsResponse);
    }

    @GetMapping("/infographics/{pt}")
    public ResponseEntity<InfographicResponseDto> getInfographicsByPt(@PathVariable String pt) {
        return ResponseEntity.ok(consultantService.getInfographicsByPt(pt));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleConsultantResponseListDto> getConsultantById(@PathVariable UUID id) {
        if ("demo".equalsIgnoreCase(appMode)) {
            return ResponseEntity.ok(demoConsultantService.getDemoConsultantById(id));
        }
        return ResponseEntity.ok(consultantService.getConsultantById(id));
    }

    @GetMapping("/all-clients-and-pts")
    public ResponseEntity<ClientsAndPtsListDto> getAllClientsAndPts(
            @RequestParam(defaultValue = "false", required = false) boolean includePgps
    ) {
        if ("demo".equalsIgnoreCase(appMode)) {
            return ResponseEntity.ok(demoConsultantService.getAllDemoClientsAndPts(includePgps));
        }
        return ResponseEntity.ok(consultantService.getAllClientsAndPts(includePgps));
    }
}
