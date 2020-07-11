package com.tt.miniapp.feedback.screenrecord;

import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.os.AsyncTask;
import android.util.SparseArray;
import com.tt.miniapphost.AppBrandLogger;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class ScreenRecordUtils {
  static SparseArray<String> sAACProfiles = new SparseArray();
  
  static SparseArray<String> sAVCLevels;
  
  static SparseArray<String> sAVCProfiles = new SparseArray();
  
  static SparseArray<String> sColorFormats;
  
  static {
    sAVCLevels = new SparseArray();
    sColorFormats = new SparseArray();
  }
  
  public static String avcProfileLevelToString(MediaCodecInfo.CodecProfileLevel paramCodecProfileLevel) {
    if (sAVCProfiles.size() == 0 || sAVCLevels.size() == 0)
      initProfileLevels(); 
    int i = sAVCProfiles.indexOfKey(paramCodecProfileLevel.profile);
    String str2 = null;
    if (i >= 0) {
      str1 = (String)sAVCProfiles.valueAt(i);
    } else {
      str1 = null;
    } 
    i = sAVCLevels.indexOfKey(paramCodecProfileLevel.level);
    if (i >= 0)
      str2 = (String)sAVCLevels.valueAt(i); 
    String str3 = str1;
    if (str1 == null)
      str3 = String.valueOf(paramCodecProfileLevel.profile); 
    String str1 = str2;
    if (str2 == null)
      str1 = String.valueOf(paramCodecProfileLevel.level); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(str3);
    stringBuilder.append('-');
    stringBuilder.append(str1);
    return stringBuilder.toString();
  }
  
  static MediaCodecInfo[] findEncodersByType(String paramString) {
    MediaCodecList mediaCodecList = new MediaCodecList(1);
    ArrayList<MediaCodecInfo> arrayList = new ArrayList();
    MediaCodecInfo[] arrayOfMediaCodecInfo = mediaCodecList.getCodecInfos();
    int j = arrayOfMediaCodecInfo.length;
    int i = 0;
    while (true) {
      if (i < j) {
        MediaCodecInfo mediaCodecInfo = arrayOfMediaCodecInfo[i];
        if (mediaCodecInfo.isEncoder())
          try {
            MediaCodecInfo.CodecCapabilities codecCapabilities = mediaCodecInfo.getCapabilitiesForType(paramString);
            if (codecCapabilities != null)
              arrayList.add(mediaCodecInfo); 
          } catch (IllegalArgumentException illegalArgumentException) {} 
        i++;
        continue;
      } 
      return arrayList.<MediaCodecInfo>toArray(new MediaCodecInfo[arrayList.size()]);
    } 
  }
  
  public static void findEncodersByTypeAsync(String paramString, Callback paramCallback) {
    (new EncoderFinder(paramCallback)).execute((Object[])new String[] { paramString });
  }
  
  private static void initColorFormatFields() {
    Field[] arrayOfField = MediaCodecInfo.CodecCapabilities.class.getFields();
    int j = arrayOfField.length;
    int i = 0;
    while (true) {
      if (i < j) {
        Field field = arrayOfField[i];
        if ((field.getModifiers() & 0x18) != 0) {
          String str = field.getName();
          if (str.startsWith("COLOR_"))
            try {
              int k = field.getInt(null);
              sColorFormats.put(k, str);
            } catch (IllegalAccessException illegalAccessException) {} 
        } 
        i++;
        continue;
      } 
      return;
    } 
  }
  
  private static void initProfileLevels() {
    for (Field field : MediaCodecInfo.CodecProfileLevel.class.getFields()) {
      if ((field.getModifiers() & 0x18) != 0) {
        SparseArray<String> sparseArray;
        String str = field.getName();
        if (str.startsWith("AVCProfile")) {
          sparseArray = sAVCProfiles;
        } else if (str.startsWith("AVCLevel")) {
          sparseArray = sAVCLevels;
        } else if (str.startsWith("AACObject")) {
          sparseArray = sAACProfiles;
        } else {
          continue;
        } 
        try {
          sparseArray.put(field.getInt(null), str);
        } catch (IllegalAccessException illegalAccessException) {
          AppBrandLogger.stacktrace(6, "tma_ScreenRecordUtils", illegalAccessException.getStackTrace());
        } 
      } 
      continue;
    } 
  }
  
  private static <T> int keyOfValue(SparseArray<T> paramSparseArray, T paramT) {
    int j = paramSparseArray.size();
    for (int i = 0; i < j; i++) {
      Object object = paramSparseArray.valueAt(i);
      if (object == paramT || object.equals(paramT))
        return paramSparseArray.keyAt(i); 
    } 
    return -1;
  }
  
  static int toColorFormat(String paramString) {
    if (sColorFormats.size() == 0)
      initColorFormatFields(); 
    int i = keyOfValue(sColorFormats, paramString);
    return (i > 0) ? i : (paramString.startsWith("0x") ? Integer.parseInt(paramString.substring(2), 16) : 0);
  }
  
  public static String toHumanReadable(int paramInt) {
    if (sColorFormats.size() == 0)
      initColorFormatFields(); 
    int i = sColorFormats.indexOfKey(paramInt);
    if (i >= 0)
      return (String)sColorFormats.valueAt(i); 
    StringBuilder stringBuilder = new StringBuilder("0x");
    stringBuilder.append(Integer.toHexString(paramInt));
    return stringBuilder.toString();
  }
  
  static MediaCodecInfo.CodecProfileLevel toProfileLevel(String paramString) {
    // Byte code:
    //   0: getstatic com/tt/miniapp/feedback/screenrecord/ScreenRecordUtils.sAVCProfiles : Landroid/util/SparseArray;
    //   3: invokevirtual size : ()I
    //   6: ifeq -> 27
    //   9: getstatic com/tt/miniapp/feedback/screenrecord/ScreenRecordUtils.sAVCLevels : Landroid/util/SparseArray;
    //   12: invokevirtual size : ()I
    //   15: ifeq -> 27
    //   18: getstatic com/tt/miniapp/feedback/screenrecord/ScreenRecordUtils.sAACProfiles : Landroid/util/SparseArray;
    //   21: invokevirtual size : ()I
    //   24: ifne -> 30
    //   27: invokestatic initProfileLevels : ()V
    //   30: aload_0
    //   31: bipush #45
    //   33: invokevirtual indexOf : (I)I
    //   36: istore_1
    //   37: iload_1
    //   38: ifle -> 59
    //   41: aload_0
    //   42: iconst_0
    //   43: iload_1
    //   44: invokevirtual substring : (II)Ljava/lang/String;
    //   47: astore_2
    //   48: aload_0
    //   49: iload_1
    //   50: iconst_1
    //   51: iadd
    //   52: invokevirtual substring : (I)Ljava/lang/String;
    //   55: astore_0
    //   56: goto -> 65
    //   59: aconst_null
    //   60: astore_3
    //   61: aload_0
    //   62: astore_2
    //   63: aload_3
    //   64: astore_0
    //   65: new android/media/MediaCodecInfo$CodecProfileLevel
    //   68: dup
    //   69: invokespecial <init> : ()V
    //   72: astore_3
    //   73: aload_2
    //   74: ldc 'AVC'
    //   76: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   79: ifeq -> 96
    //   82: aload_3
    //   83: getstatic com/tt/miniapp/feedback/screenrecord/ScreenRecordUtils.sAVCProfiles : Landroid/util/SparseArray;
    //   86: aload_2
    //   87: invokestatic keyOfValue : (Landroid/util/SparseArray;Ljava/lang/Object;)I
    //   90: putfield profile : I
    //   93: goto -> 127
    //   96: aload_2
    //   97: ldc 'AAC'
    //   99: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   102: ifeq -> 119
    //   105: aload_3
    //   106: getstatic com/tt/miniapp/feedback/screenrecord/ScreenRecordUtils.sAACProfiles : Landroid/util/SparseArray;
    //   109: aload_2
    //   110: invokestatic keyOfValue : (Landroid/util/SparseArray;Ljava/lang/Object;)I
    //   113: putfield profile : I
    //   116: goto -> 127
    //   119: aload_3
    //   120: aload_2
    //   121: invokestatic parseInt : (Ljava/lang/String;)I
    //   124: putfield profile : I
    //   127: aload_0
    //   128: ifnull -> 165
    //   131: aload_0
    //   132: ldc 'AVC'
    //   134: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   137: ifeq -> 154
    //   140: aload_3
    //   141: getstatic com/tt/miniapp/feedback/screenrecord/ScreenRecordUtils.sAVCLevels : Landroid/util/SparseArray;
    //   144: aload_0
    //   145: invokestatic keyOfValue : (Landroid/util/SparseArray;Ljava/lang/Object;)I
    //   148: putfield level : I
    //   151: goto -> 165
    //   154: aload_3
    //   155: aload_0
    //   156: invokestatic parseInt : (Ljava/lang/String;)I
    //   159: putfield level : I
    //   162: goto -> 165
    //   165: aload_3
    //   166: getfield profile : I
    //   169: ifle -> 181
    //   172: aload_3
    //   173: getfield level : I
    //   176: iflt -> 181
    //   179: aload_3
    //   180: areturn
    //   181: aconst_null
    //   182: areturn
    //   183: astore_0
    //   184: aconst_null
    //   185: areturn
    //   186: astore_0
    //   187: aconst_null
    //   188: areturn
    // Exception table:
    //   from	to	target	type
    //   119	127	183	java/lang/NumberFormatException
    //   154	162	186	java/lang/NumberFormatException
  }
  
  public static interface Callback {
    void onResult(MediaCodecInfo[] param1ArrayOfMediaCodecInfo);
  }
  
  static final class EncoderFinder extends AsyncTask<String, Void, MediaCodecInfo[]> {
    private ScreenRecordUtils.Callback mCallback;
    
    EncoderFinder(ScreenRecordUtils.Callback param1Callback) {
      this.mCallback = param1Callback;
    }
    
    protected final MediaCodecInfo[] doInBackground(String... param1VarArgs) {
      return ScreenRecordUtils.findEncodersByType(param1VarArgs[0]);
    }
    
    protected final void onPostExecute(MediaCodecInfo[] param1ArrayOfMediaCodecInfo) {
      this.mCallback.onResult(param1ArrayOfMediaCodecInfo);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\feedback\screenrecord\ScreenRecordUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */