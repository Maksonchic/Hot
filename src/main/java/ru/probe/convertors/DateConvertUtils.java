package ru.probe.convertors;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class DateConvertUtils {
    /**
     * from String date like yyyy-dd-mm to dd.mm.yyyy
     * @param dateUS String US date
     * @return String rus date
     */
    public static String fromUsToRus(String dateUS) {
        if (dateUS == null) return null;

        String[] dateParts = dateUS.split("-");

        if (dateParts.length != 3) return null;

        return String.format(
                "%s.%s.%s",
                dateParts[2],
                dateParts[1],
                dateParts[0]
        );
    }

    /**
     * from String date like dd.mm.yyyy to yyyy-dd-mm
     * @param dateRUS String rus date
     * @return String US date
     */
    public static String fromRusToUs(String dateRUS) {
        if (dateRUS == null) return null;

        String[] dateParts = dateRUS.split("[.]");

        if (dateParts.length != 3) return null;

        return String.format(
                "%s-%s-%s",
                dateParts[2],
                dateParts[1],
                dateParts[0]
        );
    }

    /**
     * use this for converting usual java.util.Date to java.sql.Date
     * for write to database
     *
     * @param uDate as java.util.Date
     * @return sDate as java.sql.Date
     */
    public static java.sql.Date convertDateToSqlDate(java.util.Date uDate) {
        return new java.sql.Date(uDate.getTime());
    }

	public static String getRus() {
		String pattern = "dd.MM.yyyy HH:mm:ss";
		DateFormat df = new SimpleDateFormat(pattern);
		Date today = Calendar.getInstance().getTime();
		
		return df.format(today);
	}
}
