package salt.consultanttracker.api.consultant;

public enum NotClient {
    PGP("PGP"),
    UPSKILLING("Upskilling");
    public final String value;
    NotClient(String value) {
        this.value = value;
    }
}
