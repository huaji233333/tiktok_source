package com.tt.miniapp.util;

import android.text.format.DateUtils;
import com.tt.miniapphost.AppBrandLogger;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DateUtils extends DateUtils {
  public static int calculateDaysInMonth(int paramInt) {
    return calculateDaysInMonth(0, paramInt);
  }
  
  public static int calculateDaysInMonth(int paramInt1, int paramInt2) {
    List<String> list1 = Arrays.asList(new String[] { "1", "3", "5", "7", "8", "10", "12" });
    List<String> list2 = Arrays.asList(new String[] { "4", "6", "9", "11" });
    return list1.contains(String.valueOf(paramInt2)) ? 31 : (list2.contains(String.valueOf(paramInt2)) ? 30 : ((paramInt1 <= 0) ? 29 : (((paramInt1 % 4 == 0 && paramInt1 % 100 != 0) || paramInt1 % 400 == 0) ? 29 : 28)));
  }
  
  public static long calculateDifference(long paramLong1, long paramLong2, int paramInt) {
    return calculateDifference(new Date(paramLong1), new Date(paramLong2), paramInt);
  }
  
  public static long calculateDifference(Date paramDate1, Date paramDate2, int paramInt) {
    long[] arrayOfLong = calculateDifference(paramDate1, paramDate2);
    return (paramInt == 1) ? arrayOfLong[2] : ((paramInt == 2) ? arrayOfLong[1] : ((paramInt == 3) ? arrayOfLong[0] : arrayOfLong[3]));
  }
  
  private static long[] calculateDifference(long paramLong) {
    long l1 = paramLong / 86400000L;
    long l2 = paramLong % 86400000L;
    paramLong = l2 / 3600000L;
    long l3 = l2 % 3600000L;
    l2 = l3 / 60000L;
    l3 %= 60000L;
    long l4 = l3 / 1000L;
    AppBrandLogger.i("ShareInfoResp", new Object[] { Locale.CHINA, "different: %d ms, %d days, %d hours, %d minutes, %d seconds", Long.valueOf(l3), Long.valueOf(l1), Long.valueOf(paramLong), Long.valueOf(l2), Long.valueOf(l4) });
    return new long[] { l1, paramLong, l2, l4 };
  }
  
  private static long[] calculateDifference(Date paramDate1, Date paramDate2) {
    return calculateDifference(paramDate2.getTime() - paramDate1.getTime());
  }
  
  public static long calculateDifferentDay(long paramLong1, long paramLong2) {
    return calculateDifference(paramLong1, paramLong2, 3);
  }
  
  public static long calculateDifferentDay(Date paramDate1, Date paramDate2) {
    return calculateDifference(paramDate1, paramDate2, 3);
  }
  
  public static long calculateDifferentHour(long paramLong1, long paramLong2) {
    return calculateDifference(paramLong1, paramLong2, 2);
  }
  
  public static long calculateDifferentHour(Date paramDate1, Date paramDate2) {
    return calculateDifference(paramDate1, paramDate2, 2);
  }
  
  public static long calculateDifferentMinute(long paramLong1, long paramLong2) {
    return calculateDifference(paramLong1, paramLong2, 1);
  }
  
  public static long calculateDifferentMinute(Date paramDate1, Date paramDate2) {
    return calculateDifference(paramDate1, paramDate2, 1);
  }
  
  public static long calculateDifferentSecond(long paramLong1, long paramLong2) {
    return calculateDifference(paramLong1, paramLong2, 0);
  }
  
  public static long calculateDifferentSecond(Date paramDate1, Date paramDate2) {
    return calculateDifference(paramDate1, paramDate2, 0);
  }
  
  public static String fillZero(int paramInt) {
    if (paramInt < 10) {
      StringBuilder stringBuilder1 = new StringBuilder("0");
      stringBuilder1.append(paramInt);
      return stringBuilder1.toString();
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramInt);
    return stringBuilder.toString();
  }
  
  public static String formatDate(String paramString) {
    return formatDate(Calendar.getInstance(Locale.CHINA).getTime(), paramString);
  }
  
  public static String formatDate(Date paramDate, String paramString) {
    return (new SimpleDateFormat(paramString, Locale.PRC)).format(paramDate);
  }
  
  public static boolean isSameDay(Date paramDate) {
    if (paramDate != null) {
      Calendar calendar1 = Calendar.getInstance();
      Calendar calendar2 = Calendar.getInstance();
      calendar2.setTime(paramDate);
      return (calendar1.get(0) == calendar2.get(0) && calendar1.get(1) == calendar2.get(1) && calendar1.get(6) == calendar2.get(6));
    } 
    throw new IllegalArgumentException("date is null");
  }
  
  public static String parseDate(long paramLong) {
    try {
      return (new SimpleDateFormat("yyyy-MM-dd")).format(new Date(paramLong));
    } catch (Exception exception) {
      return null;
    } 
  }
  
  public static Date parseDate(String paramString) {
    return parseDate(paramString, "yyyy-MM-dd HH:mm:ss");
  }
  
  public static Date parseDate(String paramString1, String paramString2) {
    try {
      return new Date((new SimpleDateFormat(paramString2, Locale.PRC)).parse(paramString1).getTime());
    } catch (ParseException parseException) {
      AppBrandLogger.e("ShareInfoResp", new Object[] { "parseDate", parseException });
      return null;
    } 
  }
  
  public static String parseDateToSecond(long paramLong) {
    try {
      return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date(paramLong));
    } catch (Exception exception) {
      return null;
    } 
  }
  
  public static int trimZero(String paramString) {
    String str = paramString;
    try {
      if (paramString.startsWith("0"))
        str = paramString.substring(1); 
      return Integer.parseInt(str);
    } catch (NumberFormatException numberFormatException) {
      AppBrandLogger.e("ShareInfoResp", new Object[] { "trimZero", numberFormatException });
      return 0;
    } 
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface DifferenceMode {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\DateUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */