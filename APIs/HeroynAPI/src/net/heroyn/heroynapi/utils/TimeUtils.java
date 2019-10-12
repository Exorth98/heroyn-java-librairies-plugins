package net.heroyn.heroynapi.utils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class TimeUtils
{
  public static final long SECOND = 1000L;
  public static final long MINUTE = 60000L;
  public static final long HOUR = 3600000L;
  public static final long DAY = 86400000L;
  public static final List<Character> letters = Arrays.asList(new Character[] { Character.valueOf('d'), Character.valueOf('h'), Character.valueOf('m'), Character.valueOf('s') });
  public static final long[] values = { 86400000L, 3600000L, 60000L, 1000L };
  
  public static String timeToString(long time)
  {
    String s = "";
    for (int i = 0; (i < letters.size()) && (time > 0L); i++)
    {
      long o = time / values[i];
      if (o > 0L) {
        s = s + o + letters.get(i);
      }
      time %= values[i];
    }
    return s;
  }
  
  public static long stringToTime(String str)
  {
    long time = 0L;
    
    int i = -1;
    do
    {
      char c = str.charAt(i);
      if ((c < '0') || (c > '9'))
      {
        if (!letters.contains(Character.valueOf(c))) {
          return -1L;
        }
        try
        {
          time += Long.parseLong(str.substring(0, i)) * values[letters.indexOf(Character.valueOf(c))];
        }
        catch (NumberFormatException e)
        {
          e.printStackTrace();
          return -1L;
        }
        str = str.substring(i + 1, str.length());
        i = -1;
      }
      i++;
    } while (i < str.length());
    return time;
  }
  
  public static String addTimeMonth(int count)
  {
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("dd:MM:yyyy");
    cal.add(2, count);
    return sdf.format(cal.getTime());
  }
}
