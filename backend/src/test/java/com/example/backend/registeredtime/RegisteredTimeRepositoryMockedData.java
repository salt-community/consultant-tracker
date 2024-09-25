package com.example.backend.registeredtime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class RegisteredTimeRepositoryMockedData {
    static UUID mockedId = UUID.fromString("01438d07-91cb-4f4d-adab-aa050462779e");
    private static final DateTimeFormatter formatterSeconds = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static List<RegisteredTime> createMockedListOfRegisteredTime(){
        var mockedRegisteredTime2 =  new RegisteredTime(new RegisteredTimeKey(mockedId, LocalDateTime.parse("2024-03-07 00:00:00", formatterSeconds)),
                "Semester",
                LocalDateTime.parse("2024-03-07 23:59:59", formatterSeconds),
                8D,
                "H&M");
        var mockedRegisteredTime3 =  new RegisteredTime(new RegisteredTimeKey(mockedId, LocalDateTime.parse("2024-03-08 00:00:00", formatterSeconds)),
                "Konsult-tid",
                LocalDateTime.parse("2024-03-08 23:59:59", formatterSeconds),
                8D,
                "H&M");
        var mockedRegisteredTime4 =  new RegisteredTime(new RegisteredTimeKey(mockedId, LocalDateTime.parse("2024-04-05 00:00:00", formatterSeconds)),
                "Semester",
                LocalDateTime.parse("2024-04-05 23:59:59", formatterSeconds),
                8D,
                "H&M");
        return List.of(mockedRegisteredTime2, mockedRegisteredTime3, mockedRegisteredTime4);
    }

    public static List<RegisteredTime> createMockedListOfRegisteredTimeWithOneSemesterRegistered(){
        var mockedRegisteredTime2 =  new RegisteredTime(new RegisteredTimeKey(mockedId, LocalDateTime.parse("2024-03-07 00:00:00", formatterSeconds)),
                "Semester",
                LocalDateTime.parse("2024-03-07 23:59:59", formatterSeconds),
                8D,
                "H&M");
        var mockedRegisteredTime3 =  new RegisteredTime(new RegisteredTimeKey(mockedId, LocalDateTime.parse("2024-03-08 00:00:00", formatterSeconds)),
                "Konsult-tid",
                LocalDateTime.parse("2024-03-08 23:59:59", formatterSeconds),
                8D,
                "H&M");
        return List.of(mockedRegisteredTime2, mockedRegisteredTime3);
    }

    public static RegisteredTime createMockedRegisteredTime06Feb(){
        return new RegisteredTime(new RegisteredTimeKey(mockedId,
                LocalDateTime.parse("2024-02-06 00:00:00",
                        formatterSeconds)),
                "Konsult-tid",
                LocalDateTime.parse("2024-02-06 23:59:59", formatterSeconds),
                8D,
                "H&M");
    }

    public static RegisteredTime createMockedRegisteredTime04Feb(){
        return new RegisteredTime(new RegisteredTimeKey(mockedId,
                LocalDateTime.parse("2024-02-04 00:00:00",
                        formatterSeconds)),
                "Konsult-tid",
                LocalDateTime.parse("2024-02-04 23:59:59", formatterSeconds),
                8D,
                "H&M");
    }
}
