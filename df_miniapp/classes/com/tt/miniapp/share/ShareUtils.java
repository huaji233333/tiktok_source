package com.tt.miniapp.share;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.tt.miniapp.media.utils.MediaEditUtil;
import com.tt.miniapp.streamloader.StreamLoader;
import com.tt.miniapphost.AppBrandLogger;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class ShareUtils {
  public static String getCompressImg(String paramString) {
    File file1 = new File(paramString);
    if (file1.length() / 1024L < 200L)
      return paramString; 
    Bitmap bitmap = BitmapFactory.decodeFile(paramString);
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    int i = (int)(20000000L / file1.length());
    bitmap.compress(Bitmap.CompressFormat.JPEG, i, byteArrayOutputStream);
    while ((byteArrayOutputStream.toByteArray()).length / 1024 > 200 && i > 0) {
      byteArrayOutputStream.reset();
      i -= 10;
      bitmap.compress(Bitmap.CompressFormat.JPEG, i, byteArrayOutputStream);
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(file1.getAbsoluteFile().getParent());
    stringBuilder.append("/com_");
    stringBuilder.append(file1.getName());
    String str = stringBuilder.toString();
    File file2 = new File(str);
    try {
      byteArrayOutputStream.writeTo(new FileOutputStream(file2));
      return str;
    } catch (Exception exception) {
      AppBrandLogger.e("ShareUtils", new Object[] { "getCompressImg", exception });
      return str;
    } 
  }
  
  public static boolean isVideoTooShort(String paramString) {
    int i = MediaEditUtil.getMediaDuration(StreamLoader.waitExtractFinishIfNeeded(paramString));
    boolean bool = false;
    if (i < 3000) {
      bool = true;
      AppBrandLogger.e("ShareUtils", new Object[] { "video too short duration == ", Integer.valueOf(i) });
    } 
    return bool;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\share\ShareUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */