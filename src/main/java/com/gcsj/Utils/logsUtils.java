package com.gcsj.Utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class logsUtils {
    public Long adminId;
    public String operation;
    public String FormName;
    public Long entityId;
    public static String username;

    public static String TransformTime()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
        return sdf.format(new Date());
    }
    public static String TransformTime_hm()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_hh:mm");
        return sdf.format(new Date());
    }
    public static String TransformTime(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(sdf.parse(date));
    }

    public static String SetUerName(String string)
    {
        return username = string;
    }


    public static String GetUerName()
    {
        return username;
    }

}
