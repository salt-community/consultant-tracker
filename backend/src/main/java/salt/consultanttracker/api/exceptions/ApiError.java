package salt.consultanttracker.api.exceptions;



public record ApiError(int status, String reasonPhrase,  String message) {
}