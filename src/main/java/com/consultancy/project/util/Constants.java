package com.consultancy.project.util;

public final class Constants {

    private Constants() {}

    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_CONFIRMED = "CONFIRMED";
    public static final String STATUS_CANCELLED = "CANCELLED";
    public static final String STATUS_COMPLETED = "COMPLETED";
    public static final String CREATE = "CREATE";
    public static final String UPDATE = "UPDATE";
    public static final String PATCH = "PATCH";
    public static final String SAVE_CONSULTANT = "SAVE_CONSULTANT";
    // DTO validation
    public static final String STATUS_REGEX = "^(PENDING|CONFIRMED|CANCELLED|COMPLETED)$";

    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

    public static final String PHONE_REGEX = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$";

    public static final String TRACE_ID_KEY = "TRACE_ID";
}
