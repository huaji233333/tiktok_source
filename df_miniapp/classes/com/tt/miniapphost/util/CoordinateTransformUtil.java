package com.tt.miniapphost.util;

public class CoordinateTransformUtil {
  static double a = 6378245.0D;
  
  static double ee = 0.006693421622965943D;
  
  static double pi = 3.141592653589793D;
  
  static double x_pi = 52.35987755982988D;
  
  public static double[] bd09togcj02(double paramDouble1, double paramDouble2) {
    paramDouble1 -= 0.0065D;
    double d = paramDouble2 - 0.006D;
    paramDouble2 = Math.sqrt(paramDouble1 * paramDouble1 + d * d) - Math.sin(x_pi * d) * 2.0E-5D;
    paramDouble1 = Math.atan2(d, paramDouble1) - Math.cos(paramDouble1 * x_pi) * 3.0E-6D;
    return new double[] { Math.cos(paramDouble1) * paramDouble2, paramDouble2 * Math.sin(paramDouble1) };
  }
  
  public static double[] bd09towgs84(double paramDouble1, double paramDouble2) {
    double[] arrayOfDouble = bd09togcj02(paramDouble1, paramDouble2);
    return gcj02towgs84(arrayOfDouble[0], arrayOfDouble[1]);
  }
  
  public static double[] gcj02tobd09(double paramDouble1, double paramDouble2) {
    double d = Math.sqrt(paramDouble1 * paramDouble1 + paramDouble2 * paramDouble2) + Math.sin(x_pi * paramDouble2) * 2.0E-5D;
    paramDouble1 = Math.atan2(paramDouble2, paramDouble1) + Math.cos(paramDouble1 * x_pi) * 3.0E-6D;
    return new double[] { Math.cos(paramDouble1) * d + 0.0065D, d * Math.sin(paramDouble1) + 0.006D };
  }
  
  public static double[] gcj02towgs84(double paramDouble1, double paramDouble2) {
    if (outOfChina(paramDouble1, paramDouble2))
      return new double[] { paramDouble1, paramDouble2 }; 
    double d2 = paramDouble1 - 105.0D;
    double d3 = paramDouble2 - 35.0D;
    double d1 = transformlat(d2, d3);
    d2 = transformlng(d2, d3);
    d3 = paramDouble2 / 180.0D * pi;
    double d4 = Math.sin(d3);
    double d6 = 1.0D - ee * d4 * d4;
    d4 = Math.sqrt(d6);
    double d5 = a;
    d1 = d1 * 180.0D / (1.0D - ee) * d5 / d6 * d4 * pi;
    return new double[] { paramDouble1 * 2.0D - paramDouble1 + d2 * 180.0D / d5 / d4 * Math.cos(d3) * pi, paramDouble2 * 2.0D - paramDouble2 + d1 };
  }
  
  public static boolean isValidLatLng(double paramDouble1, double paramDouble2) {
    if (paramDouble1 >= -90.0D) {
      if (paramDouble1 > 90.0D)
        return false; 
      if (paramDouble2 >= -180.0D)
        return !(paramDouble2 > 180.0D); 
    } 
    return false;
  }
  
  public static boolean outOfChina(double paramDouble1, double paramDouble2) {
    if (paramDouble1 >= 72.004D) {
      if (paramDouble1 > 137.8347D)
        return true; 
      if (paramDouble2 >= 0.8293D)
        return (paramDouble2 > 55.8271D); 
    } 
    return true;
  }
  
  public static double transformlat(double paramDouble1, double paramDouble2) {
    double d = paramDouble1 * 2.0D;
    return -100.0D + d + paramDouble2 * 3.0D + paramDouble2 * 0.2D * paramDouble2 + 0.1D * paramDouble1 * paramDouble2 + Math.sqrt(Math.abs(paramDouble1)) * 0.2D + (Math.sin(paramDouble1 * 6.0D * pi) * 20.0D + Math.sin(d * pi) * 20.0D) * 2.0D / 3.0D + (Math.sin(pi * paramDouble2) * 20.0D + Math.sin(paramDouble2 / 3.0D * pi) * 40.0D) * 2.0D / 3.0D + (Math.sin(paramDouble2 / 12.0D * pi) * 160.0D + Math.sin(paramDouble2 * pi / 30.0D) * 320.0D) * 2.0D / 3.0D;
  }
  
  public static double transformlng(double paramDouble1, double paramDouble2) {
    double d = paramDouble1 * 0.1D;
    return paramDouble1 + 300.0D + paramDouble2 * 2.0D + d * paramDouble1 + d * paramDouble2 + Math.sqrt(Math.abs(paramDouble1)) * 0.1D + (Math.sin(6.0D * paramDouble1 * pi) * 20.0D + Math.sin(paramDouble1 * 2.0D * pi) * 20.0D) * 2.0D / 3.0D + (Math.sin(pi * paramDouble1) * 20.0D + Math.sin(paramDouble1 / 3.0D * pi) * 40.0D) * 2.0D / 3.0D + (Math.sin(paramDouble1 / 12.0D * pi) * 150.0D + Math.sin(paramDouble1 / 30.0D * pi) * 300.0D) * 2.0D / 3.0D;
  }
  
  public static double[] wgs84tobd09(double paramDouble1, double paramDouble2) {
    double[] arrayOfDouble = wgs84togcj02(paramDouble1, paramDouble2);
    return gcj02tobd09(arrayOfDouble[0], arrayOfDouble[1]);
  }
  
  public static double[] wgs84togcj02(double paramDouble1, double paramDouble2) {
    if (outOfChina(paramDouble1, paramDouble2))
      return new double[] { paramDouble1, paramDouble2 }; 
    double d2 = paramDouble1 - 105.0D;
    double d3 = paramDouble2 - 35.0D;
    double d1 = transformlat(d2, d3);
    d2 = transformlng(d2, d3);
    d3 = paramDouble2 / 180.0D * pi;
    double d4 = Math.sin(d3);
    double d6 = 1.0D - ee * d4 * d4;
    d4 = Math.sqrt(d6);
    double d5 = a;
    d1 = d1 * 180.0D / (1.0D - ee) * d5 / d6 * d4 * pi;
    return new double[] { paramDouble1 + d2 * 180.0D / d5 / d4 * Math.cos(d3) * pi, paramDouble2 + d1 };
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphos\\util\CoordinateTransformUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */