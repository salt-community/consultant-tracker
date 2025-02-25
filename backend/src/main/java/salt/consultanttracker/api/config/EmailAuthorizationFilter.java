package salt.consultanttracker.api.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class EmailAuthorizationFilter extends OncePerRequestFilter {

    private final String USER_EMAILS;
    private final String ADMIN_EMAILS;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            String email = jwt.getClaim("email_address");
            if (email != null && isAuthorizedEmail(email)) {
                System.out.println("Email authorized: " + email);
                filterChain.doFilter(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized email");
            }
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Unauthenticated");
        }
    }

    private boolean isAuthorizedEmail(String email) {
        List<String> userEmails = List.of(USER_EMAILS.split(","));
        List<String> adminEmails = List.of(ADMIN_EMAILS.split(","));
        return userEmails.contains(email) || adminEmails.contains(email);
    }

    public String assignRole(String email) {
        List<String> adminEmails = List.of(ADMIN_EMAILS.split(","));
        return ADMIN_EMAILS.contains(email) ? "ADMIN" : "USER";
    }
}