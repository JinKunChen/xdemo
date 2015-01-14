package com.topsem.common.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Chen on 14-7-16.
 */
public class StringToDateConverter implements Converter<String, Date> {

    private final static String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private DateFormat dateFormat;

    public StringToDateConverter() {
        this.dateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
        this.dateFormat.setLenient(false);

    }

    public StringToDateConverter(String pattern) {
        this.dateFormat = new SimpleDateFormat(pattern);
        this.dateFormat.setLenient(false);
    }

    public StringToDateConverter(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public Date convert(String source) {
        try {
            return dateFormat.parse(source);
        } catch (ParseException e) {
            String pattern;
            if (dateFormat instanceof SimpleDateFormat) {
                pattern = ((SimpleDateFormat) dateFormat).toPattern();
            } else {
                pattern = dateFormat.toString();
            }
            throw new IllegalArgumentException(e.getMessage() + ", format: [" + pattern + "]");
        }
    }

}
