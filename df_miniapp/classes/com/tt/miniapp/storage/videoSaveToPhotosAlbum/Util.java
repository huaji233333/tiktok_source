package com.tt.miniapp.storage.videoSaveToPhotosAlbum;

import java.lang.reflect.Method;

public class Util {
  private static boolean sIsEngpath;
  
  private static boolean sIsPathInited;
  
  private static float getRomVersion() {
    try {
      return ((Float)invokeStaticMethod("android.os.FtBuild", "getRomVersion", new Object[0])).floatValue();
    } catch (ClassNotFoundException|Exception classNotFoundException) {
      return 2.5F;
    } 
  }
  
  private static Object invokeStaticMethod(String paramString1, String paramString2, Object... paramVarArgs) throws Exception {
    Class<?> clazz = Class.forName(paramString1);
    Class[] arrayOfClass = new Class[paramVarArgs.length];
    int j = paramVarArgs.length;
    for (int i = 0; i < j; i++)
      arrayOfClass[i] = paramVarArgs[i].getClass(); 
    Method method = clazz.getMethod(paramString2, arrayOfClass);
    method.setAccessible(true);
    return method.invoke((Object)null, paramVarArgs);
  }
  
  public static boolean isEngPath() {
    if (!sIsPathInited) {
      float f = getRomVersion();
      if (isExported() || f >= 3.6F)
        sIsEngpath = true; 
      sIsPathInited = true;
    } 
    return sIsEngpath;
  }
  
  private static boolean isExported() {
    String str = "no";
    try {
      String str1 = (String)invokeStaticMethod("android.os.SystemProperties", "get", new Object[] { "ro.vivo.product.overseas", "no" });
      str = str1;
    } catch (ClassNotFoundException|Exception classNotFoundException) {}
    return "yes".equals(str);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\storage\videoSaveToPhotosAlbum\Util.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */