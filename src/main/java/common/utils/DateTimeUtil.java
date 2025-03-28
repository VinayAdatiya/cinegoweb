package common.utils;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTimeUtil {

    public static Date toSqlDate(LocalDate localDate) {
        return localDate == null ? null : Date.valueOf(localDate);
    }

    public static Time toSqlTime(LocalTime localTime) {
        return localTime == null ? null : Time.valueOf(localTime);
    }

    public static LocalDateTime toLocalDateTime(Timestamp timestamp){
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}