package salt.consultanttracker.api.responsiblept;

public enum Role {
    PT("pt"),
    ADMIN("admin");
    public final String role;
    Role(String role) {
        this.role = role;
    }
}
