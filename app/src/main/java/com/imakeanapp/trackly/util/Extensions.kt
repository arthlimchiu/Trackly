package com.imakeanapp.trackly.util

import com.imakeanapp.trackly.core.DEFAULT_DATE_TIME_PATTERN
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime
import org.threeten.bp.Period
import org.threeten.bp.format.DateTimeFormatter

/**
 * Pluralize a word.
 *
 * @param count a Long that represents the count
 * @return a pluralized String or itself
 */
fun String.pluralize(count: Long): String {
    return if (count > 1) {
        "${this}s"
    } else {
        this
    }
}

/**
 * Compute the time elapsed.
 * Ex: 1yr 1mo 1day 1hr 1min 1sec
 *
 * @return a String that represents that time elapsed
 */
fun String.toTimeElapsed(): String {
    val then = LocalDateTime.parse(this, DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN))
    val now = LocalDateTime.now()

    val period = Period.between(then.toLocalDate(), now.toLocalDate())
    val duration = Duration.between(then, now)

    return when {
        period.years > 0 -> "${period.years} yr".pluralize(period.years.toLong())
        period.months > 0 -> "${period.months} mo".pluralize(period.months.toLong())
        period.days > 0 -> "${period.days} day".pluralize(period.days.toLong())
        duration.toHours() > 0 -> "${duration.toHours()} hr".pluralize(duration.toHours())
        duration.toMinutes() > 0 -> "${duration.toMinutes()} min".pluralize(duration.toMinutes())
        duration.seconds > 0 -> "${duration.seconds} sec".pluralize(duration.seconds)
        else -> ""
    }
}