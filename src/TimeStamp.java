//北京时间
public class TimeStamp {
    private int year, month, day, hour, minute, second;
    private long stampTime;
    private int isLeapYear[] = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private int isNotLeapYear[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public TimeStamp(String newDate) {
        String[] daysAndTimes = newDate.split("-|\\:|\\s+");
        year = Integer.parseInt(daysAndTimes[0]);
        month = Integer.parseInt(daysAndTimes[1]);
        day = Integer.parseInt(daysAndTimes[2]);
        hour = Integer.parseInt(daysAndTimes[3]);
        minute = Integer.parseInt(daysAndTimes[4]);
        second = Integer.parseInt(daysAndTimes[5]);
    }


    public TimeStamp(long newStampTime) {
        stampTime = newStampTime;
    }

    private boolean isLeap(int year) {
        if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) return true;
        else return false;
    }

    public long dateToStamptime() { //日期转时间戳。大体思路为先计算今年之前的总天数，再计算本年天数。
        int getStampTime;
        int countDays = 0;
        int countLeapYear = 0;
        int countYear = year - 1970;
        for (int i = 1970; i < year; i++) {
            if (isLeap(i)) countLeapYear++;//计算闰年年数
        }
        countDays = countYear * 365 + countLeapYear;//总天数
        if (isLeap(year)) {
            for (int i = 0; i < month - 1; i++) {
                countDays += isLeapYear[i];
            }
        } else {
            for (int i = 0; i < month - 1; i++) {
                countDays += isNotLeapYear[i];
            }
        }//分闰年和非闰年按月处理
        countDays += day;
        getStampTime = (countDays - 1) * 24 * 60 * 60 + hour * 60 * 60 + minute * 60 + second - 8 * 60 * 60;
        return getStampTime;
    }

    public String stampTimeToDate() {//时间戳转日期。思路为将时间戳分为日期和时分秒后分别处理
        int countDays = Integer.parseInt(String.valueOf(stampTime / (60 * 60 * 24)));
        int countTimes = Integer.parseInt(String.valueOf(stampTime % (60 * 60 * 24)));
        int countLeapYear = 0;
        int months = 1;
        int days = 1;
        int years = 1970;
        int hours = countTimes / 3600;
        int minutes = (countTimes - hours * 3600) / 60;
        int seconds = countTimes % 60;
        String date;

        hours += 8;//北京时间
        if (hours >= 24) {
            hours -= 24;
            days++;
        }
        years += countDays / 365;//大体计算年数
        for (int i = 1970; i < years; i++) {
            if (isLeap(i)) {
                countLeapYear++;
            }
        }//计算闰年数
        countDays -= 365 * (years - 1970) + countLeapYear;//将剩余天数减去已计入年数的天数
        if (countDays <= 0) {
            years--;
            if (isLeap(years)) {
                countDays += 366;
            } else {
                countDays += 365;
            }
        }//特殊情况处理。years本为大体计算的年数，之前计算时当做每年365天，再减去闰年产生的天数可能会产生剩余天数小于0的情况。
        if (isLeap(years)) {
            for (int i = 0; countDays > isLeapYear[i]; i++) {
                countDays -= isLeapYear[i];
                months++;
            }
        } else {
            for (int i = 0; countDays > isNotLeapYear[i]; i++) {
                countDays -= isNotLeapYear[i];
                months++;
            }
        }//当前年分闰年和非闰年处理
        days += countDays;
        if (isLeap(years)) {
            if (days > isLeapYear[months - 1]) {
                days -= isLeapYear[months - 1];
                months++;
                if (months > 12) {
                    months -= 12;
                    years++;
                }
            }
        } else {
            if (days > isNotLeapYear[months - 1]) {
                days -= isNotLeapYear[months - 1];
                months++;
                if (months > 12) {
                    months -= 12;
                    years++;
                }
            }
        }//特殊情况，分闰年非闰年处理。days可能会大于本月天数，按情况将其处理。
        date = String.format("%d-%02d-%02d %02d:%02d:%02d", years, months, days, hours, minutes, seconds);
        return date;
    }
}
