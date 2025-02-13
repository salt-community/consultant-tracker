package salt.consultanttracker.api.reddays;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import salt.consultanttracker.api.reddays.dto.RedDaysResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/red-days")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name="Red Days")
public class RedDayController {
    private final RedDayService redDaysService;

    @GetMapping
    @Operation(description = "Returns all Red Days from Sweden and Norway")
    public ResponseEntity<RedDaysResponseDto> getAllRedDays(){
        return ResponseEntity.ok(redDaysService.getAllRedDays());
    }
}
