package dev.foraged.game.util

import java.util.concurrent.TimeUnit
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

    static String formatTime(long millis) {
        int seconds = millis / 1000
        int day = (int) TimeUnit.SECONDS.toDays(seconds)
        long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24)
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60)
        long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60)

        return (day > 0 ? day + "d " : "") + (hours > 0 ? hours + "h " : "") + (minute > 0 ? minute + "m " : "") + (second > 0 ? second + "s " : "")
    }

    static String dateToString(Date date) {
        Calendar calendar = Calendar.getInstance()
        calendar.setTime(date)

        return calendar.time.toString()
    }

}