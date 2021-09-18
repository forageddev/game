package dev.foraged.game.util

import java.util.regex.Matcher
import java.util.regex.Pattern

class TimeUtil {

    static long parseTime(String time) {
        if ("perm" in time.toLowerCase()) return Long.MAX_VALUE
        long totalTime = 0L
        boolean found = false
        Matcher matcher = Pattern.compile("\\d+\\D+").matcher(time)

        while(matcher.find()) {
            String s = matcher.group()
            Long value = Long.parseLong(s.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)")[0])
            String type = s.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)")[1]

            switch (type) {
                case "s":
                    totalTime += value
                    found = true
                    break
                case "m":
                    totalTime += value * 60
                    found = true
                    break
                case "h":
                    totalTime += value * 60 * 60
                    found = true
                    break
                case "d":
                    totalTime += value * 60 * 60 * 24
                    found = true
                    break
                case "w":
                    totalTime += value * 60 * 60 * 24 * 7
                    found = true
                    break
                case "M":
                    totalTime += value * 60 * 60 * 24 * 30
                    found = true
                    break
                case "y":
                    totalTime += value * 60 * 60 * 24 * 365
                    found = true
                    break
            }
        }

        return !found ? -1 : totalTime * 1000
    }

    static String millisToRoundedTime(long millis) {
        String finalString = ""
        millis += 1L

        long seconds = millis / 1000L
        long minutes = seconds / 60L
        long hours = minutes / 60L
        long days = hours / 24L
        long weeks = days / 7L
        long months = weeks / 4L
        long years = months / 12L

        if (years > 0) {
            finalString = finalString + "${years}y "
        } else if (months > 0) {
            finalString = finalString + "${months}mo "
        } else if (weeks > 0) {
            finalString = finalString + "${weeks}w "
        } else if (days > 0) {
            finalString = finalString + "${days}d "
        } else if (hours > 0) {
            finalString = finalString + "${hours}h "
        } else if (minutes > 0) {
            finalString = finalString + "${minutes}m "
        } else {
            finalString = finalString + "${seconds}s "
        }
        return finalString
    }

    static String dateToString(Date date) {
        Calendar calendar = Calendar.getInstance()
        calendar.setTime(date)

        return calendar.time.toString()
    }

}