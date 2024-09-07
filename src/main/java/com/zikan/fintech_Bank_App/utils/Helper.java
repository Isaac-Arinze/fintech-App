package com.zikan.fintech_Bank_App.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class Helper {

    public static final DecimalFormat df2dp = new DecimalFormat();
    public static final SimpleDateFormat LONG_DATE_FORMAT = new SimpleDateFormat();
    private static final Logger logger = LoggerFactory.getLogger(Helper.class);


    public  static <T> boolean isEmpty (T value) {
        if (value == null)
            return true;
        if (value instanceof String && ((String) value).trim().isEmpty())
            return true;
        return false;
    }

}


