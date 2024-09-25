package salt.consultanttracker.api.reddays;

import salt.consultanttracker.api.reddays.dto.RedDaysResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/redDays")
@RequiredArgsConstructor
@CrossOrigin
public class RedDayController {
    private final RedDayService redDaysService;

    @GetMapping
    public ResponseEntity<RedDaysResponseDto> getAllRedDays(){
        return ResponseEntity.ok(redDaysService.getAllRedDays());
    }

    @GetMapping("/test")
    public void test(){
        redDaysService.getRedDaysFromNager();
    }
}
