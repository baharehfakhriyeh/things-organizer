package com.fkhr.thingsorganizer.common.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Utils {
    public static Date getCurrentDate(){
        LocalDate today = LocalDate.now();
        return Date.from(today.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}
