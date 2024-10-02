package salt.consultanttracker.api.client.timekeeper;

public enum Activity {
    OWN_ADMINISTRATION("Egen administration"),
    VACATION("Semester"),
    SICK_LEAVE("Sjuk"),
    SICK_CHILD_CARE("VAB"),
    PARENTAL_LEAVE("Föräldraledig"),
    LEAVE_OF_ABSENCE("Tjänstledig"),
    CONSULTANCY_TIME("Konsult-tid"),
    ON_CALL("Jourtid"),
    PGP("PGP"),
    UPSKILLING("Upskilling"),
    REMAINING_DAYS("Remaining Days"),
    ON_ASSIGNMENT("På uppdrag");
    public final String activity;
    Activity(String activity) {
        this.activity = activity;
    }
}
