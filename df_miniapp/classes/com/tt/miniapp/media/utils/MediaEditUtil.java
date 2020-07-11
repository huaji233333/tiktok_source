package com.tt.miniapp.media.utils;

import android.media.MediaMetadataRetriever;
import android.text.TextUtils;
import android.util.Size;
import com.tt.miniapp.media.MediaSupport;
import com.tt.miniapp.media.base.MediaEditListener;
import com.tt.miniapp.media.base.MediaEditParams;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import com.tt.miniapphost.util.CharacterUtils;
import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;

public class MediaEditUtil {
  public static int addAudioTrack(String paramString1, String paramString2, MediaEditParams.AudioElement[] paramArrayOfAudioElement, MediaEditListener paramMediaEditListener) {
    if (TextUtils.isEmpty(paramString1)) {
      if (paramMediaEditListener != null)
        paramMediaEditListener.onFail(-1001, "empty videoPath"); 
      return -1001;
    } 
    if (paramArrayOfAudioElement == null || paramArrayOfAudioElement.length <= 0) {
      if (paramMediaEditListener != null)
        paramMediaEditListener.onFail(-1001, "audioParams is invalid"); 
      return -1001;
    } 
    String str = paramString2;
    if (TextUtils.isEmpty(paramString2))
      str = createDefaultOutput(paramString1); 
    MediaEditParams.Builder builder = new MediaEditParams.Builder();
    byte b = 0;
    builder.addVideoElement(new MediaEditParams.VideoElement(paramString1, 0, -1, 1.0F));
    builder.outputPath(str);
    Size size = getVideoSize(paramString1);
    int i = b;
    if (size != null) {
      builder.outputSize(size);
      i = b;
    } 
    while (i < paramArrayOfAudioElement.length) {
      MediaEditParams.AudioElement audioElement = paramArrayOfAudioElement[i];
      if (audioElement != null)
        builder.addAudioElement(audioElement); 
      i++;
    } 
    return MediaSupport.getInstance().edit(builder.build(), paramMediaEditListener);
  }
  
  public static boolean cancelEdit(int paramInt) {
    return MediaSupport.getInstance().cancel(paramInt);
  }
  
  public static String createDefaultOutput(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return CharacterUtils.empty(); 
    File file = new File(paramString);
    String str = file.getName();
    int i = str.lastIndexOf(".");
    paramString = str;
    if (i > 0)
      paramString = str.substring(0, i); 
    if (paramString.indexOf("-edited") > 0) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramString);
      stringBuilder.append("-");
      stringBuilder.append(System.currentTimeMillis());
      stringBuilder.append(".mp4");
      paramString = stringBuilder.toString();
    } else {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramString);
      stringBuilder.append("-edited.mp4");
      paramString = stringBuilder.toString();
    } 
    return (new File(file.getParentFile(), paramString)).getAbsolutePath();
  }
  
  public static int getMediaDuration(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return 0; 
    try {
      MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
      mediaMetadataRetriever.setDataSource(paramString);
      return Integer.valueOf(mediaMetadataRetriever.extractMetadata(9)).intValue();
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("MediaEditUtil", "", exception);
      return 0;
    } 
  }
  
  public static Size getVideoSize(String paramString) {
    try {
      MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
      mediaMetadataRetriever.setDataSource(paramString);
      paramString = mediaMetadataRetriever.extractMetadata(18);
      String str = mediaMetadataRetriever.extractMetadata(19);
      return (Integer.valueOf(mediaMetadataRetriever.extractMetadata(24)).intValue() == 90) ? new Size(Integer.valueOf(str).intValue(), Integer.valueOf(paramString).intValue()) : new Size(Integer.valueOf(paramString).intValue(), Integer.valueOf(str).intValue());
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("MediaEditUtil", "", exception);
      return null;
    } 
  }
  
  public static void monitorEditStatus(int paramInt, long paramLong) {
    if (paramInt != -1007) {
      if (paramInt != -1005) {
        if (paramInt != 0) {
          if (paramInt != -1001) {
            if (paramInt != -1000) {
              paramInt = -2001;
            } else {
              paramInt = -2003;
            } 
          } else {
            paramInt = -2000;
          } 
        } else {
          paramInt = 0;
        } 
      } else {
        paramInt = -2002;
      } 
    } else {
      paramInt = -2004;
    } 
    JSONObject jSONObject = new JSONObject();
    if (paramLong > 0L)
      try {
        jSONObject.put("duration", paramLong);
      } catch (JSONException jSONException) {
        AppBrandLogger.eWithThrowable("MediaEditUtil", "", (Throwable)jSONException);
      }  
    AppBrandMonitor.statusRate("mp_vesdk_edit", paramInt, jSONObject);
  }
  
  public static int preEdit(String paramString1, String paramString2, int[] paramArrayOfint, float paramFloat, String paramString3, MediaEditListener paramMediaEditListener) {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   4: ifeq -> 28
    //   7: aload #5
    //   9: ifnull -> 24
    //   12: aload #5
    //   14: sipush #-1001
    //   17: ldc 'empty source videoPath'
    //   19: invokeinterface onFail : (ILjava/lang/String;)V
    //   24: sipush #-1001
    //   27: ireturn
    //   28: aload_1
    //   29: astore #13
    //   31: aload_1
    //   32: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   35: ifeq -> 44
    //   38: aload_0
    //   39: invokestatic createDefaultOutput : (Ljava/lang/String;)Ljava/lang/String;
    //   42: astore #13
    //   44: aload_2
    //   45: ifnull -> 55
    //   48: aload_2
    //   49: astore_1
    //   50: aload_2
    //   51: arraylength
    //   52: ifne -> 69
    //   55: iconst_2
    //   56: newarray int
    //   58: astore_1
    //   59: aload_1
    //   60: dup
    //   61: iconst_0
    //   62: iconst_0
    //   63: iastore
    //   64: dup
    //   65: iconst_1
    //   66: iconst_m1
    //   67: iastore
    //   68: pop
    //   69: new com/tt/miniapp/media/base/MediaEditParams$Builder
    //   72: dup
    //   73: invokespecial <init> : ()V
    //   76: astore_2
    //   77: aload_2
    //   78: aload #13
    //   80: invokevirtual outputPath : (Ljava/lang/String;)Lcom/tt/miniapp/media/base/MediaEditParams$Builder;
    //   83: pop
    //   84: aload_1
    //   85: arraylength
    //   86: istore #7
    //   88: iload #7
    //   90: istore #6
    //   92: iload #7
    //   94: iconst_1
    //   95: iand
    //   96: ifeq -> 105
    //   99: iload #7
    //   101: iconst_1
    //   102: isub
    //   103: istore #6
    //   105: iconst_0
    //   106: istore #11
    //   108: iconst_0
    //   109: istore #8
    //   111: iconst_0
    //   112: istore #7
    //   114: iload #11
    //   116: istore #9
    //   118: iload #8
    //   120: iload #6
    //   122: if_icmpge -> 202
    //   125: aload_1
    //   126: iload #8
    //   128: iaload
    //   129: istore #10
    //   131: aload_1
    //   132: iload #8
    //   134: iconst_1
    //   135: iadd
    //   136: iaload
    //   137: istore #12
    //   139: iload #10
    //   141: istore #9
    //   143: iload #10
    //   145: ifge -> 151
    //   148: iconst_0
    //   149: istore #9
    //   151: iload #9
    //   153: ifne -> 165
    //   156: iload #7
    //   158: istore #10
    //   160: iload #12
    //   162: ifeq -> 189
    //   165: iload #7
    //   167: iconst_1
    //   168: iadd
    //   169: istore #10
    //   171: aload_2
    //   172: new com/tt/miniapp/media/base/MediaEditParams$VideoElement
    //   175: dup
    //   176: aload_0
    //   177: iload #9
    //   179: iload #12
    //   181: fload_3
    //   182: invokespecial <init> : (Ljava/lang/String;IIF)V
    //   185: invokevirtual addVideoElement : (Lcom/tt/miniapp/media/base/MediaEditParams$VideoElement;)Lcom/tt/miniapp/media/base/MediaEditParams$Builder;
    //   188: pop
    //   189: iload #8
    //   191: iconst_2
    //   192: iadd
    //   193: istore #8
    //   195: iload #10
    //   197: istore #7
    //   199: goto -> 114
    //   202: iload #9
    //   204: iload #7
    //   206: iconst_1
    //   207: isub
    //   208: if_icmpge -> 227
    //   211: aload_2
    //   212: aload #4
    //   214: invokevirtual addTransition : (Ljava/lang/String;)Lcom/tt/miniapp/media/base/MediaEditParams$Builder;
    //   217: pop
    //   218: iload #9
    //   220: iconst_1
    //   221: iadd
    //   222: istore #9
    //   224: goto -> 202
    //   227: aload_0
    //   228: invokestatic getVideoSize : (Ljava/lang/String;)Landroid/util/Size;
    //   231: astore_0
    //   232: aload_0
    //   233: ifnull -> 242
    //   236: aload_2
    //   237: aload_0
    //   238: invokevirtual outputSize : (Landroid/util/Size;)Lcom/tt/miniapp/media/base/MediaEditParams$Builder;
    //   241: pop
    //   242: aload_2
    //   243: invokevirtual build : ()Lcom/tt/miniapp/media/base/MediaEditParams;
    //   246: astore_0
    //   247: invokestatic getInstance : ()Lcom/tt/miniapp/media/MediaSupport;
    //   250: aload_0
    //   251: aload #5
    //   253: invokevirtual edit : (Lcom/tt/miniapp/media/base/MediaEditParams;Lcom/tt/miniapp/media/base/MediaEditListener;)I
    //   256: ireturn
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\medi\\utils\MediaEditUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */