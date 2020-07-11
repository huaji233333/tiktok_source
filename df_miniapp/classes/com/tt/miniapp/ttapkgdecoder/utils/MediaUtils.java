package com.tt.miniapp.ttapkgdecoder.utils;

import java.util.HashMap;
import java.util.Locale;

public class MediaUtils {
  private static final HashMap<String, MediaFileType> sFileTypeMap = new HashMap<String, MediaFileType>();
  
  private static final HashMap<String, Integer> sFileTypeToFormatMap;
  
  private static final HashMap<Integer, String> sFormatToMimeTypeMap;
  
  private static final HashMap<String, Integer> sMimeTypeMap = new HashMap<String, Integer>();
  
  private static final HashMap<String, Integer> sMimeTypeToFormatMap;
  
  static {
    sFileTypeToFormatMap = new HashMap<String, Integer>();
    sMimeTypeToFormatMap = new HashMap<String, Integer>();
    sFormatToMimeTypeMap = new HashMap<Integer, String>();
    addFileType("MP3", 1, "audio/mpeg", 12297);
    addFileType("MPGA", 1, "audio/mpeg", 12297);
    addFileType("M4A", 2, "audio/mp4", 12299);
    addFileType("WAV", 3, "audio/x-wav", 12296);
    addFileType("AMR", 4, "audio/amr");
    addFileType("AWB", 5, "audio/amr-wb");
    addFileType("OGG", 7, "audio/ogg", 47362);
    addFileType("OGG", 7, "application/ogg", 47362);
    addFileType("OGA", 7, "application/ogg", 47362);
    addFileType("AAC", 8, "audio/aac", 47363);
    addFileType("AAC", 8, "audio/aac-adts", 47363);
    addFileType("MKA", 9, "audio/x-matroska");
    addFileType("MID", 11, "audio/midi");
    addFileType("MIDI", 11, "audio/midi");
    addFileType("XMF", 11, "audio/midi");
    addFileType("RTTTL", 11, "audio/midi");
    addFileType("SMF", 12, "audio/sp-midi");
    addFileType("IMY", 13, "audio/imelody");
    addFileType("RTX", 11, "audio/midi");
    addFileType("OTA", 11, "audio/midi");
    addFileType("MXMF", 11, "audio/midi");
    addFileType("MPEG", 21, "video/mpeg", 12299);
    addFileType("MPG", 21, "video/mpeg", 12299);
    addFileType("MP4", 21, "video/mp4", 12299);
    addFileType("M4V", 22, "video/mp4", 12299);
    addFileType("3GP", 23, "video/3gpp", 47492);
    addFileType("3GPP", 23, "video/3gpp", 47492);
    addFileType("3G2", 24, "video/3gpp2", 47492);
    addFileType("3GPP2", 24, "video/3gpp2", 47492);
    addFileType("MKV", 27, "video/x-matroska");
    addFileType("WEBM", 30, "video/webm");
    addFileType("TS", 28, "video/mp2ts");
    addFileType("AVI", 29, "video/avi");
    addFileType("JPG", 31, "image/jpeg", 14337);
    addFileType("JPEG", 31, "image/jpeg", 14337);
    addFileType("GIF", 32, "image/gif", 14343);
    addFileType("PNG", 33, "image/png", 14347);
    addFileType("BMP", 34, "image/x-ms-bmp", 14340);
    addFileType("WBMP", 35, "image/vnd.wap.wbmp");
    addFileType("WEBP", 36, "image/webp");
    addFileType("M3U", 41, "audio/x-mpegurl", 47633);
    addFileType("M3U", 41, "application/x-mpegurl", 47633);
    addFileType("PLS", 42, "audio/x-scpls", 47636);
    addFileType("WPL", 43, "application/vnd.ms-wpl", 47632);
    addFileType("M3U8", 44, "application/vnd.apple.mpegurl");
    addFileType("M3U8", 44, "audio/mpegurl");
    addFileType("M3U8", 44, "audio/x-mpegurl");
    addFileType("FL", 51, "application/x-android-drm-fl");
    addFileType("TXT", 100, "text/plain", 12292);
    addFileType("HTM", 101, "text/html", 12293);
    addFileType("HTML", 101, "text/html", 12293);
    addFileType("PDF", 102, "application/pdf");
    addFileType("DOC", 104, "application/msword", 47747);
    addFileType("XLS", 105, "application/vnd.ms-excel", 47749);
    addFileType("PPT", 106, "application/mspowerpoint", 47750);
    addFileType("FLAC", 10, "audio/flac", 47366);
    addFileType("ZIP", 107, "application/zip");
    addFileType("MPG", 200, "video/mp2p");
    addFileType("MPEG", 200, "video/mp2p");
  }
  
  static void addFileType(String paramString1, int paramInt, String paramString2) {
    sFileTypeMap.put(paramString1, new MediaFileType(paramInt, paramString2));
    sMimeTypeMap.put(paramString2, Integer.valueOf(paramInt));
  }
  
  static void addFileType(String paramString1, int paramInt1, String paramString2, int paramInt2) {
    addFileType(paramString1, paramInt1, paramString2);
    sFileTypeToFormatMap.put(paramString1, Integer.valueOf(paramInt2));
    sMimeTypeToFormatMap.put(paramString2, Integer.valueOf(paramInt2));
    sFormatToMimeTypeMap.put(Integer.valueOf(paramInt2), paramString2);
  }
  
  public static String getFileTitle(String paramString) {
    int i = paramString.lastIndexOf('/');
    String str = paramString;
    if (i >= 0) {
      i++;
      str = paramString;
      if (i < paramString.length())
        str = paramString.substring(i); 
    } 
    i = str.lastIndexOf('.');
    paramString = str;
    if (i > 0)
      paramString = str.substring(0, i); 
    return paramString;
  }
  
  public static MediaFileType getFileType(String paramString) {
    int i = paramString.lastIndexOf('.');
    return (i < 0) ? null : sFileTypeMap.get(paramString.substring(i + 1).toUpperCase(Locale.ROOT));
  }
  
  public static int getFileTypeForMimeType(String paramString) {
    Integer integer = sMimeTypeMap.get(paramString);
    return (integer == null) ? 0 : integer.intValue();
  }
  
  public static int getFormatCode(String paramString1, String paramString2) {
    if (paramString2 != null) {
      Integer integer = sMimeTypeToFormatMap.get(paramString2);
      if (integer != null)
        return integer.intValue(); 
    } 
    int i = paramString1.lastIndexOf('.');
    if (i > 0) {
      paramString1 = paramString1.substring(i + 1).toUpperCase(Locale.ROOT);
      Integer integer = sFileTypeToFormatMap.get(paramString1);
      if (integer != null)
        return integer.intValue(); 
    } 
    return 12288;
  }
  
  public static String getMimeTypeForFile(String paramString) {
    MediaFileType mediaFileType = getFileType(paramString);
    return (mediaFileType == null) ? null : mediaFileType.mimeType;
  }
  
  public static String getMimeTypeForFormatCode(int paramInt) {
    return sFormatToMimeTypeMap.get(Integer.valueOf(paramInt));
  }
  
  public static boolean isAudioFileType(int paramInt) {
    return ((paramInt > 0 && paramInt <= 10) || (paramInt >= 11 && paramInt <= 13));
  }
  
  public static boolean isDrmFileType(int paramInt) {
    return (paramInt >= 51 && paramInt <= 51);
  }
  
  public static boolean isImageFileType(int paramInt) {
    return (paramInt >= 31 && paramInt <= 36);
  }
  
  public static boolean isMimeTypeMedia(String paramString) {
    int i = getFileTypeForMimeType(paramString);
    return (isAudioFileType(i) || isVideoFileType(i) || isImageFileType(i) || isPlayListFileType(i));
  }
  
  public static boolean isPlayListFileType(int paramInt) {
    return (paramInt >= 41 && paramInt <= 44);
  }
  
  public static boolean isVideoFileType(int paramInt) {
    return ((paramInt >= 21 && paramInt <= 30) || (paramInt >= 200 && paramInt <= 200));
  }
  
  public static class MediaFileType {
    public final int fileType;
    
    public final String mimeType;
    
    MediaFileType(int param1Int, String param1String) {
      this.fileType = param1Int;
      this.mimeType = param1String;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\ttapkgdecode\\utils\MediaUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */