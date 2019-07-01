package br.com.todoservice.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@SuppressWarnings({ "squid:S2176" })
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateUtils extends org.apache.commons.lang3.time.DateUtils {

	public static Calendar toCalendar(final LocalDateTime localDateTime) {
		if (localDateTime == null) {
			return null;
		}

		ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
		return GregorianCalendar.from(zonedDateTime);
	}

	public static LocalDate toLocalDate(final Date date) {
		if (date == null) {
			return null;
		}

		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static LocalDateTime toLocalDateTime(final Calendar calendar) {
		if (calendar == null) {
			return null;
		}

		TimeZone tz = calendar.getTimeZone();
		ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();

		return LocalDateTime.ofInstant(calendar.toInstant(), zid);
	}

	public static LocalDateTime toLocalDateTime(final Date date) {
		if (date == null) {
			return null;
		}

		return new java.sql.Timestamp(date.getTime()).toLocalDateTime();
	}

}
