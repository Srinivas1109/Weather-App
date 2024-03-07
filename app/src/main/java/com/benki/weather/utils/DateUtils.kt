package com.benki.weather.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateUtils {
    fun convertDateTime(dateTimeString: String): Triple<String, String, String> {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val dateTime = LocalDateTime.parse(dateTimeString, formatter)

        val formattedDate = dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
        val formattedDayOfWeek = dateTime.format(DateTimeFormatter.ofPattern("EEEE"))
        val formattedTime = dateTime.format(DateTimeFormatter.ofPattern("hh:mm a"))

        return Triple(formattedDate, formattedTime, formattedDayOfWeek)
    }

    fun convertDateToDay(date: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val dateTime = LocalDate.parse(date, formatter)

        return dateTime.format(DateTimeFormatter.ofPattern("EEE"))
    }
}