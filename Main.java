import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Main {

  public static void main(String[] args) {
    LocalDateTime date = LocalDateTime.of(2010, 7, 9, 23, 36);
    String str = "0,45;12;1,2,6;3,6,14,18,21,24,28;1,2,3,4,5,6,7,8,9,10,11,12;";
    System.out.println(getNextStartDate(date, str));
  }

  private static LocalDateTime getNextStartDate(LocalDateTime date, String str) {
    String[] split = str.split(";");
    if (split.length < 5) {
      throw new RuntimeException("Incorrect input data");
    }
    while (!isDateCorrect(date, split)) {
      date = date.plusMinutes(1);
    }
    return date;
  }

  private static boolean isDateCorrect(LocalDateTime date, String[] split) {
    return checkMinutes(date.getMinute(), getData(split[0]))
        && checkHours(date.getHour(), getData(split[1]))
        && checkWeekDays(date.getDayOfWeek(), getData(split[2]))
        && checkMonthDays(date.getDayOfMonth(), getData(split[3]))
        && checkMonth(date.getDayOfMonth(), getData(split[4]));
  }

  private static boolean checkMinutes(int minute, List<Integer> list) {
    return list.contains(minute);
  }

  private static boolean checkHours(int hour, List<Integer> list) {
    return list.contains(hour);
  }

  private static boolean checkWeekDays(DayOfWeek weekDay, List<Integer> list) {
    return list.contains(weekDay.get(WeekFields.of(Locale.US).dayOfWeek()));
  }

  private static boolean checkMonthDays(int monthDay, List<Integer> list) {
    return list.contains(monthDay);
  }

  private static boolean checkMonth(int month, List<Integer> list) {
    return list.contains(month);
  }

  private static List<Integer> getData(String str) {
    return Arrays.stream(str.split(",")).map(Integer::parseInt).collect(Collectors.toList());
  }
}
