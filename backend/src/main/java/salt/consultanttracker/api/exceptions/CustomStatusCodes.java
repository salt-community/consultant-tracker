package salt.consultanttracker.api.exceptions;

public enum CustomStatusCodes {
        // Define custom status codes
        EXTERNAL_API_ERROR(567, "External API Error");

        // Fields for status code and reason phrase
        private final int code;
        private final String reasonPhrase;

        // Constructor
        CustomStatusCodes(int code, String reasonPhrase) {
            this.code = code;
            this.reasonPhrase = reasonPhrase;
        }

        // Getters
        public int getCode() {
            return code;
        }

        public String getReasonPhrase() {
            return reasonPhrase;
        }

        public static CustomStatusCodes fromCode(int code) {
            for (CustomStatusCodes status : values()) {
                if (status.getCode() == code) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Unknown status code: " + code);
        }

        @Override
        public String toString() {
            return code + " " + reasonPhrase;
        }
}
