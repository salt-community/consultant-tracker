package salt.consultanttracker.api.config;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@CrossOrigin
@Tag(name = "Authorization")
public class AuthorizationController {
    private final JwtDecoder jwtDecoder;
    private final String USER_EMAILS;
    private final String ADMIN_EMAILS;

    public AuthorizationController(@Value("${USER_EMAILS}") String userEmails,
                                   @Value("${ADMIN_EMAILS}") String adminEmails,
                                   @Qualifier("jwtDecoder") JwtDecoder jwtDecoder) {
        this.USER_EMAILS = userEmails;
        this.ADMIN_EMAILS = adminEmails;
        this.jwtDecoder = jwtDecoder;
    }

    @GetMapping
    @Operation(description = "Authorized emails")
    public String auth(@RequestHeader("Authorization") String jwt) {
        String jwtStripped = jwt.substring("Bearer ".length());
        String emailAddress = jwtDecoder.decode(jwtStripped).getClaim("email_address");
        EmailAuthorizationFilter emailAuthorizationFilter = new EmailAuthorizationFilter(USER_EMAILS, ADMIN_EMAILS);
        return emailAuthorizationFilter.assignRole(emailAddress);
    }
}
