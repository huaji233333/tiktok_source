package com.tt.miniapphost.util;

import android.content.ContentUris;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;
import com.tt.miniapphost.AppBrandLogger;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

public class CharacterUtils {
  static final char[] HEX_CHARS = new char[] { 
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
      'a', 'b', 'c', 'd', 'e', 'f' };
  
  private static String _queryPathFromMediaStore(Context paramContext, Uri paramUri, String paramString, String[] paramArrayOfString) {
    String str3 = null;
    String str2 = null;
    String str1 = str2;
    try {
      Cursor cursor = paramContext.getContentResolver().query(paramUri, new String[] { "_data" }, paramString, paramArrayOfString, null);
      str1 = str3;
      if (cursor != null) {
        str1 = str2;
        int i = cursor.getColumnIndexOrThrow("_data");
        str1 = str2;
        cursor.moveToFirst();
        str1 = str2;
        String str = cursor.getString(i);
        str1 = str;
        cursor.close();
        return str;
      } 
    } catch (IllegalArgumentException illegalArgumentException) {
      AppBrandLogger.e("CharacterUtils", new Object[] { illegalArgumentException.toString() });
    } 
    return str1;
  }
  
  public static String empty() {
    return "";
  }
  
  public static List<Object> getArrayFromJsonArray(JSONArray paramJSONArray) throws Exception {
    ArrayList<Object<String, Object>> arrayList = new ArrayList();
    for (int i = 0; i < paramJSONArray.size(); i++) {
      Object<String, Object> object1;
      Object<String, Object> object2 = (Object<String, Object>)paramJSONArray.get(i);
      if (object2 instanceof JSONArray) {
        List<Object> list = getArrayFromJsonArray((JSONArray)object2);
      } else {
        object1 = object2;
        if (object2 instanceof JSONObject)
          object1 = (Object<String, Object>)getMapFromJson((JSONObject)object2); 
      } 
      arrayList.add(object1);
    } 
    return (List)arrayList;
  }
  
  private static String getCharSet(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return paramString; 
    byte b = -1;
    switch (paramString.hashCode()) {
      case 111113226:
        if (paramString.equals("ucs-2"))
          b = 1; 
        break;
      case 3584301:
        if (paramString.equals("ucs2"))
          b = 0; 
        break;
      case -119555963:
        if (paramString.equals("utf16le"))
          b = 2; 
        break;
      case -1109877331:
        if (paramString.equals("latin1"))
          b = 4; 
        break;
      case -1388966911:
        if (paramString.equals("binary"))
          b = 3; 
        break;
    } 
    return (b != 0 && b != 1 && b != 2) ? ((b != 3 && b != 4) ? paramString : "latin1") : "UTF-16LE";
  }
  
  public static JSONObject getJsonFromMap(Map paramMap) {
    if (paramMap == null)
      return null; 
    try {
      return (JSONObject)(new JSONParser()).parse(JSONValue.toJSONString(paramMap));
    } catch (Exception exception) {
      AppBrandLogger.e("CharacterUtils", new Object[] { exception.toString() });
      return null;
    } 
  }
  
  public static Map<String, Object> getMapFromJson(String paramString) throws Exception {
    return TextUtils.isEmpty(paramString) ? null : getMapFromJson((JSONObject)(new JSONParser()).parse(paramString));
  }
  
  public static Map<String, Object> getMapFromJson(JSONObject paramJSONObject) throws Exception {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    for (String str : paramJSONObject.keySet()) {
      Object<String, Object> object1;
      Object<String, Object> object2 = (Object<String, Object>)paramJSONObject.get(str);
      if (object2 instanceof JSONArray) {
        List<Object> list = getArrayFromJsonArray((JSONArray)object2);
      } else {
        object1 = object2;
        if (object2 instanceof JSONObject)
          object1 = (Object<String, Object>)getMapFromJson((JSONObject)object2); 
      } 
      hashMap.put(str, object1);
    } 
    return (Map)hashMap;
  }
  
  public static byte[] hexStringToBytes(String paramString) throws IllegalArgumentException {
    char[] arrayOfChar;
    if (paramString != null && paramString.length() % 2 != 1) {
      arrayOfChar = paramString.toCharArray();
      int j = arrayOfChar.length;
      byte[] arrayOfByte = new byte[j / 2];
      for (int i = 0; i < j; i += 2)
        arrayOfByte[i / 2] = (byte)((Character.digit(arrayOfChar[i], 16) << 4) + Character.digit(arrayOfChar[i + 1], 16)); 
      return arrayOfByte;
    } 
    StringBuilder stringBuilder = new StringBuilder("hexBinary needs to be even-length: ");
    stringBuilder.append((String)arrayOfChar);
    IllegalArgumentException illegalArgumentException = new IllegalArgumentException(stringBuilder.toString());
    throw illegalArgumentException;
  }
  
  public static final boolean isChinese(char paramChar) {
    Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(paramChar);
    return (unicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || unicodeBlock == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || unicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || unicodeBlock == Character.UnicodeBlock.GENERAL_PUNCTUATION || unicodeBlock == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || unicodeBlock == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS);
  }
  
  public static int length(String paramString) {
    return (paramString == null) ? 0 : paramString.length();
  }
  
  public static String md5Hex(File paramFile) {
    try {
      MessageDigest messageDigest = MessageDigest.getInstance("MD5");
      if (messageDigest == null)
        return null; 
      FileInputStream fileInputStream = new FileInputStream(paramFile);
      byte[] arrayOfByte = new byte[8192];
      while (true) {
        int i = fileInputStream.read(arrayOfByte, 0, 8192);
        if (i > 0) {
          messageDigest.update(arrayOfByte, 0, i);
          continue;
        } 
        fileInputStream.close();
        return toHexString(messageDigest.digest());
      } 
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "CharacterUtils", exception.getStackTrace());
      return null;
    } 
  }
  
  public static String md5Hex(String paramString) {
    if (paramString != null)
      try {
        if (paramString.length() == 0)
          return null; 
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(paramString.getBytes("UTF-8"));
        return toHexString(messageDigest.digest());
      } catch (Exception exception) {
        AppBrandLogger.stacktrace(6, "CharacterUtils", exception.getStackTrace());
      }  
    return null;
  }
  
  public static String md5Hex(byte[] paramArrayOfbyte) {
    if (paramArrayOfbyte != null)
      try {
        if (paramArrayOfbyte.length == 0)
          return null; 
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(paramArrayOfbyte);
        return toHexString(messageDigest.digest());
      } catch (Exception exception) {
        AppBrandLogger.stacktrace(6, "CharacterUtils", exception.getStackTrace());
      }  
    return null;
  }
  
  public static String md5Hex(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    if (paramArrayOfbyte != null && paramInt1 >= 0 && paramInt2 > 0)
      try {
        if (paramInt1 + paramInt2 > paramArrayOfbyte.length)
          return null; 
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(paramArrayOfbyte, paramInt1, paramInt2);
        return toHexString(messageDigest.digest());
      } catch (Exception exception) {
        AppBrandLogger.stacktrace(6, "CharacterUtils", exception.getStackTrace());
      }  
    return null;
  }
  
  public static String null2Empty(String paramString) {
    String str = paramString;
    if (paramString == null)
      str = empty(); 
    return str;
  }
  
  public static String nullValue() {
    return null;
  }
  
  public static String replaceBlank(String paramString) {
    String str = "";
    if (paramString != null)
      str = Pattern.compile("\\s*|\t|\r|\n").matcher(paramString).replaceAll(""); 
    return str;
  }
  
  public static String splitCharByPoints(String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    char[] arrayOfChar = paramString.toCharArray();
    for (int i = 0; i < arrayOfChar.length; i++) {
      stringBuilder.append(arrayOfChar[i]);
      if (i != arrayOfChar.length - 1)
        stringBuilder.append("."); 
    } 
    return stringBuilder.toString();
  }
  
  public static String toAndroidStyleColor(String paramString) {
    int i = paramString.length();
    if (i == 7)
      return paramString; 
    String str = paramString;
    if (i == 4) {
      str = paramString;
      if (paramString.charAt(0) == '#') {
        StringBuilder stringBuilder = new StringBuilder("#");
        stringBuilder.append(paramString.charAt(1));
        stringBuilder.append(paramString.charAt(1));
        stringBuilder.append(paramString.charAt(2));
        stringBuilder.append(paramString.charAt(2));
        stringBuilder.append(paramString.charAt(3));
        stringBuilder.append(paramString.charAt(3));
        str = stringBuilder.toString();
      } 
    } 
    return str;
  }
  
  public static String toBinaryString(int paramInt) {
    return Integer.toBinaryString(paramInt);
  }
  
  public static String toBinaryString(byte... paramVarArgs) {
    char[] arrayOfChar1 = new char[16];
    arrayOfChar1[0] = '0';
    arrayOfChar1[1] = '1';
    arrayOfChar1[2] = '2';
    arrayOfChar1[3] = '3';
    arrayOfChar1[4] = '4';
    arrayOfChar1[5] = '5';
    arrayOfChar1[6] = '6';
    arrayOfChar1[7] = '7';
    arrayOfChar1[8] = '8';
    arrayOfChar1[9] = '9';
    arrayOfChar1[10] = 'a';
    arrayOfChar1[11] = 'b';
    arrayOfChar1[12] = 'c';
    arrayOfChar1[13] = 'd';
    arrayOfChar1[14] = 'e';
    arrayOfChar1[15] = 'f';
    char[] arrayOfChar2 = new char[paramVarArgs.length * 8];
    int i = 0;
    int j = 0;
    while (i < paramVarArgs.length) {
      int k;
      if (paramVarArgs[i] < 0) {
        k = paramVarArgs[i] + 256;
      } else {
        k = paramVarArgs[i];
      } 
      int m = j + 1;
      arrayOfChar2[j] = arrayOfChar1[k >>> 7 & 0x1];
      j = m + 1;
      arrayOfChar2[m] = arrayOfChar1[k >>> 6 & 0x1];
      m = j + 1;
      arrayOfChar2[j] = arrayOfChar1[k >>> 5 & 0x1];
      j = m + 1;
      arrayOfChar2[m] = arrayOfChar1[k >>> 4 & 0x1];
      m = j + 1;
      arrayOfChar2[j] = arrayOfChar1[k >>> 3 & 0x1];
      j = m + 1;
      arrayOfChar2[m] = arrayOfChar1[k >>> 2 & 0x1];
      m = j + 1;
      arrayOfChar2[j] = arrayOfChar1[k >>> 1 & 0x1];
      j = m + 1;
      arrayOfChar2[m] = arrayOfChar1[k & 0x1];
      i++;
    } 
    return new String(arrayOfChar2);
  }
  
  public static Bitmap toBitmap(Drawable paramDrawable) {
    if (paramDrawable instanceof BitmapDrawable)
      return ((BitmapDrawable)paramDrawable).getBitmap(); 
    if (paramDrawable instanceof ColorDrawable) {
      Bitmap bitmap = Bitmap.createBitmap(32, 32, Bitmap.Config.ARGB_8888);
      (new Canvas(bitmap)).drawColor(((ColorDrawable)paramDrawable).getColor());
      return bitmap;
    } 
    if (paramDrawable instanceof android.graphics.drawable.NinePatchDrawable) {
      Bitmap bitmap = Bitmap.createBitmap(paramDrawable.getIntrinsicWidth(), paramDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
      Canvas canvas = new Canvas(bitmap);
      paramDrawable.setBounds(0, 0, paramDrawable.getIntrinsicWidth(), paramDrawable.getIntrinsicHeight());
      paramDrawable.draw(canvas);
      return bitmap;
    } 
    return null;
  }
  
  public static Bitmap toBitmap(View paramView) {
    int k = paramView.getWidth();
    int i = paramView.getHeight();
    if (paramView instanceof ListView) {
      ListView listView = (ListView)paramView;
      int n = 0;
      int m = 0;
      while (true) {
        i = m;
        if (n < listView.getChildCount()) {
          m += listView.getChildAt(n).getHeight();
          n++;
          continue;
        } 
        break;
      } 
    } else if (paramView instanceof ScrollView) {
      ScrollView scrollView = (ScrollView)paramView;
      int n = 0;
      int m = 0;
      while (true) {
        i = m;
        if (n < scrollView.getChildCount()) {
          m += scrollView.getChildAt(n).getHeight();
          n++;
          continue;
        } 
        break;
      } 
    } 
    paramView.setDrawingCacheEnabled(true);
    paramView.clearFocus();
    paramView.setPressed(false);
    boolean bool = paramView.willNotCacheDrawing();
    paramView.setWillNotCacheDrawing(false);
    int j = paramView.getDrawingCacheBackgroundColor();
    paramView.setDrawingCacheBackgroundColor(-1);
    if (j != -1)
      paramView.destroyDrawingCache(); 
    paramView.buildDrawingCache();
    Bitmap bitmap1 = paramView.getDrawingCache();
    if (bitmap1 == null)
      return null; 
    Bitmap bitmap2 = Bitmap.createBitmap(k, i, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap2);
    canvas.drawBitmap(bitmap1, 0.0F, 0.0F, null);
    canvas.save();
    canvas.restore();
    if (!bitmap2.isRecycled())
      bitmap2.recycle(); 
    paramView.destroyDrawingCache();
    paramView.setWillNotCacheDrawing(bool);
    paramView.setDrawingCacheBackgroundColor(j);
    return bitmap2;
  }
  
  public static Bitmap toBitmap(byte[] paramArrayOfbyte) {
    return toBitmap(paramArrayOfbyte, -1, -1);
  }
  
  public static Bitmap toBitmap(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    int i = paramArrayOfbyte.length;
    Bitmap bitmap1 = null;
    Bitmap bitmap2 = null;
    if (i != 0) {
      bitmap1 = bitmap2;
      try {
        BitmapFactory.Options options = new BitmapFactory.Options();
        bitmap1 = bitmap2;
        options.inDither = false;
        bitmap1 = bitmap2;
        options.inPreferredConfig = null;
        if (paramInt1 > 0 && paramInt2 > 0) {
          bitmap1 = bitmap2;
          options.outWidth = paramInt1;
          bitmap1 = bitmap2;
          options.outHeight = paramInt2;
        } 
        bitmap1 = bitmap2;
        Bitmap bitmap = BitmapFactory.decodeByteArray(paramArrayOfbyte, 0, paramArrayOfbyte.length, options);
        bitmap1 = bitmap;
        bitmap.setDensity(96);
        return bitmap;
      } catch (Exception exception) {
        AppBrandLogger.e("CharacterUtils", new Object[] { exception.getMessage() });
      } 
    } 
    return bitmap1;
  }
  
  public static byte[] toByteArray(Bitmap paramBitmap) {
    if (paramBitmap == null)
      return null; 
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    paramBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
    byte[] arrayOfByte = byteArrayOutputStream.toByteArray();
    try {
      byteArrayOutputStream.close();
      return arrayOfByte;
    } catch (IOException iOException) {
      AppBrandLogger.w("CharacterUtils", new Object[] { iOException.getMessage() });
      return arrayOfByte;
    } 
  }
  
  public static byte[] toByteArray(Drawable paramDrawable) {
    return toByteArray(toBitmap(paramDrawable));
  }
  
  public static byte[] toByteArray(InputStream paramInputStream) {
    if (paramInputStream == null)
      return null; 
    try {
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      byte[] arrayOfByte = new byte[100];
      while (true) {
        int i = paramInputStream.read(arrayOfByte, 0, 100);
        if (i != -1) {
          byteArrayOutputStream.write(arrayOfByte, 0, i);
          continue;
        } 
        arrayOfByte = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        paramInputStream.close();
        return arrayOfByte;
      } 
    } catch (IOException iOException) {
      AppBrandLogger.w("CharacterUtils", new Object[] { iOException.getMessage() });
      return null;
    } 
  }
  
  public static byte[] toByteArray(String paramString, boolean paramBoolean) {
    if (paramString == null || paramString.equals(""))
      return null; 
    if (!paramBoolean)
      return paramString.getBytes(); 
    String str = paramString.replaceAll("\\s+", "");
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(str.length() / 2);
    for (int i = 0; i < str.length(); i += 2)
      byteArrayOutputStream.write("0123456789ABCDEF".indexOf(str.charAt(i)) << 4 | "0123456789ABCDEF".indexOf(str.charAt(i + 1))); 
    byte[] arrayOfByte = byteArrayOutputStream.toByteArray();
    try {
      byteArrayOutputStream.close();
      return arrayOfByte;
    } catch (IOException iOException) {
      AppBrandLogger.e("CharacterUtils", new Object[] { iOException.getMessage() });
      return arrayOfByte;
    } 
  }
  
  public static int toDp(Context paramContext, float paramFloat) {
    return (int)(paramFloat / (paramContext.getResources().getDisplayMetrics()).density + 0.5F);
  }
  
  public static Drawable toDrawable(Bitmap paramBitmap) {
    return (Drawable)((paramBitmap == null) ? null : new BitmapDrawable(Resources.getSystem(), paramBitmap));
  }
  
  public static Drawable toDrawable(byte[] paramArrayOfbyte) {
    return toDrawable(toBitmap(paramArrayOfbyte));
  }
  
  public static String toFileSizeString(long paramLong) {
    StringBuilder stringBuilder1;
    DecimalFormat decimalFormat = new DecimalFormat("0.00");
    if (paramLong < 1024L) {
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append(paramLong);
      stringBuilder1.append("B");
      return stringBuilder1.toString();
    } 
    if (paramLong < 1048576L) {
      StringBuilder stringBuilder = new StringBuilder();
      double d1 = paramLong;
      Double.isNaN(d1);
      stringBuilder.append(stringBuilder1.format(d1 / 1024.0D));
      stringBuilder.append("K");
      return stringBuilder.toString();
    } 
    if (paramLong < 1073741824L) {
      StringBuilder stringBuilder = new StringBuilder();
      double d1 = paramLong;
      Double.isNaN(d1);
      stringBuilder.append(stringBuilder1.format(d1 / 1048576.0D));
      stringBuilder.append("M");
      return stringBuilder.toString();
    } 
    StringBuilder stringBuilder2 = new StringBuilder();
    double d = paramLong;
    Double.isNaN(d);
    stringBuilder2.append(stringBuilder1.format(d / 1.073741824E9D));
    stringBuilder2.append("G");
    return stringBuilder2.toString();
  }
  
  public static float toFloat(Object paramObject) {
    try {
      return Float.parseFloat(paramObject.toString());
    } catch (NumberFormatException numberFormatException) {
      return -1.0F;
    } 
  }
  
  public static String toHexString(int paramInt) {
    return Integer.toHexString(paramInt);
  }
  
  public static String toHexString(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return ""; 
    StringBuilder stringBuilder = new StringBuilder();
    byte[] arrayOfByte = paramString.getBytes();
    int j = arrayOfByte.length;
    for (int i = 0; i < j; i++) {
      stringBuilder.append(Integer.toHexString(arrayOfByte[i] & 0xFF));
      stringBuilder.append(" ");
    } 
    return stringBuilder.toString();
  }
  
  public static String toHexString(byte... paramVarArgs) {
    char[] arrayOfChar1 = new char[16];
    arrayOfChar1[0] = '0';
    arrayOfChar1[1] = '1';
    arrayOfChar1[2] = '2';
    arrayOfChar1[3] = '3';
    arrayOfChar1[4] = '4';
    arrayOfChar1[5] = '5';
    arrayOfChar1[6] = '6';
    arrayOfChar1[7] = '7';
    arrayOfChar1[8] = '8';
    arrayOfChar1[9] = '9';
    arrayOfChar1[10] = 'a';
    arrayOfChar1[11] = 'b';
    arrayOfChar1[12] = 'c';
    arrayOfChar1[13] = 'd';
    arrayOfChar1[14] = 'e';
    arrayOfChar1[15] = 'f';
    char[] arrayOfChar2 = new char[paramVarArgs.length * 2];
    int i = 0;
    int j = 0;
    while (i < paramVarArgs.length) {
      int k;
      if (paramVarArgs[i] < 0) {
        k = paramVarArgs[i] + 256;
      } else {
        k = paramVarArgs[i];
      } 
      int m = j + 1;
      arrayOfChar2[j] = arrayOfChar1[k >>> 4];
      j = m + 1;
      arrayOfChar2[m] = arrayOfChar1[k & 0xF];
      i++;
    } 
    return new String(arrayOfChar2);
  }
  
  public static String toHexString(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    if (paramArrayOfbyte != null) {
      if (paramInt1 >= 0 && paramInt1 + paramInt2 <= paramArrayOfbyte.length) {
        int k = paramInt2 * 2;
        char[] arrayOfChar = new char[k];
        int i = 0;
        int j = 0;
        while (i < paramInt2) {
          int m = paramArrayOfbyte[i + paramInt1] & 0xFF;
          int n = j + 1;
          char[] arrayOfChar1 = HEX_CHARS;
          arrayOfChar[j] = arrayOfChar1[m >> 4];
          j = n + 1;
          arrayOfChar[n] = arrayOfChar1[m & 0xF];
          i++;
        } 
        return new String(arrayOfChar, 0, k);
      } 
      throw new IndexOutOfBoundsException();
    } 
    NullPointerException nullPointerException = new NullPointerException("bytes is null");
    throw nullPointerException;
  }
  
  public static int toInt(Object paramObject) {
    try {
      return Integer.parseInt(paramObject.toString());
    } catch (NumberFormatException numberFormatException) {
      return -1;
    } 
  }
  
  public static int toInt(byte[] paramArrayOfbyte) {
    int i = 0;
    int j = 0;
    while (i < paramArrayOfbyte.length) {
      j += (paramArrayOfbyte[i] & 0xFF) << i * 8;
      i++;
    } 
    return j;
  }
  
  public static <T> List<T> toList(T[] paramArrayOfT) {
    return Arrays.asList(paramArrayOfT);
  }
  
  public static long toLong(Object paramObject) {
    try {
      return Long.parseLong(paramObject.toString());
    } catch (NumberFormatException numberFormatException) {
      return -1L;
    } 
  }
  
  public static String toPath(Context paramContext, Uri paramUri) {
    StringBuilder stringBuilder;
    Uri uri;
    byte b;
    String[] arrayOfString;
    if (paramUri == null) {
      AppBrandLogger.d("CharacterUtils", new Object[] { "\"uri is null\"" });
      return "";
    } 
    AppBrandLogger.d("CharacterUtils", new Object[] { "uri: ", paramUri.toString() });
    String str3 = paramUri.getPath();
    String str2 = paramUri.getScheme();
    String str4 = paramUri.getAuthority();
    if (Build.VERSION.SDK_INT >= 19) {
      b = 1;
    } else {
      b = 0;
    } 
    String str1 = null;
    if (b && DocumentsContract.isDocumentUri(paramContext, paramUri)) {
      String str5 = DocumentsContract.getDocumentId(paramUri);
      arrayOfString = str5.split(":");
      String str6 = arrayOfString[0];
      b = -1;
      int i = str4.hashCode();
      if (i != 320699453) {
        if (i != 596745902) {
          if (i == 1734583286 && str4.equals("com.android.providers.media.documents"))
            b = 2; 
        } else if (str4.equals("com.android.externalstorage.documents")) {
          b = 0;
        } 
      } else if (str4.equals("com.android.providers.downloads.documents")) {
        b = 1;
      } 
      if (b != 0) {
        if (b != 1) {
          if (b == 2) {
            if ("image".equals(str6)) {
              uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if ("video".equals(str6)) {
              uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            } else {
              str5 = str1;
              if ("audio".equals(str6))
                uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI; 
            } 
            return _queryPathFromMediaStore(paramContext, uri, "_id=?", new String[] { arrayOfString[1] });
          } 
        } else {
          return _queryPathFromMediaStore(paramContext, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf((String)uri).longValue()), null, null);
        } 
      } else if ("primary".equalsIgnoreCase(str6)) {
        stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStorageDirectory());
        stringBuilder.append("/");
        stringBuilder.append(arrayOfString[1]);
        return stringBuilder.toString();
      } 
    } else {
      if ("content".equalsIgnoreCase((String)arrayOfString))
        return str4.equals("com.google.android.apps.photos.content") ? uri.getLastPathSegment() : _queryPathFromMediaStore((Context)stringBuilder, uri, null, null); 
      if ("file".equalsIgnoreCase((String)arrayOfString))
        return uri.getPath(); 
    } 
    AppBrandLogger.d("CharacterUtils", new Object[] { "uri to path: ", str3 });
    return str3;
  }
  
  public static int toPx(Context paramContext, float paramFloat) {
    return (int)(paramFloat * (paramContext.getResources().getDisplayMetrics()).density + 0.5F);
  }
  
  public static String toSlashString(String paramString) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual toCharArray : ()[C
    //   4: astore #5
    //   6: aload #5
    //   8: arraylength
    //   9: istore_3
    //   10: ldc ''
    //   12: astore_0
    //   13: iconst_0
    //   14: istore_2
    //   15: iload_2
    //   16: iload_3
    //   17: if_icmpge -> 111
    //   20: aload #5
    //   22: iload_2
    //   23: caload
    //   24: istore_1
    //   25: iload_1
    //   26: bipush #34
    //   28: if_icmpeq -> 46
    //   31: iload_1
    //   32: bipush #39
    //   34: if_icmpeq -> 46
    //   37: aload_0
    //   38: astore #4
    //   40: iload_1
    //   41: bipush #92
    //   43: if_icmpne -> 78
    //   46: new java/lang/StringBuilder
    //   49: dup
    //   50: invokespecial <init> : ()V
    //   53: astore #4
    //   55: aload #4
    //   57: aload_0
    //   58: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   61: pop
    //   62: aload #4
    //   64: ldc_w '\'
    //   67: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   70: pop
    //   71: aload #4
    //   73: invokevirtual toString : ()Ljava/lang/String;
    //   76: astore #4
    //   78: new java/lang/StringBuilder
    //   81: dup
    //   82: invokespecial <init> : ()V
    //   85: astore_0
    //   86: aload_0
    //   87: aload #4
    //   89: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   92: pop
    //   93: aload_0
    //   94: iload_1
    //   95: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   98: pop
    //   99: aload_0
    //   100: invokevirtual toString : ()Ljava/lang/String;
    //   103: astore_0
    //   104: iload_2
    //   105: iconst_1
    //   106: iadd
    //   107: istore_2
    //   108: goto -> 15
    //   111: aload_0
    //   112: areturn
  }
  
  public static int toSp(Context paramContext, float paramFloat) {
    return (int)(paramFloat / (paramContext.getResources().getDisplayMetrics()).scaledDensity + 0.5F);
  }
  
  public static String toString(InputStream paramInputStream) {
    return toString(paramInputStream, "utf-8");
  }
  
  public static String toString(InputStream paramInputStream, String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    try {
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(paramInputStream, paramString));
      while (true) {
        String str = bufferedReader.readLine();
        if (str != null) {
          stringBuilder.append(str);
          stringBuilder.append("\n");
          continue;
        } 
        bufferedReader.close();
        paramInputStream.close();
        return stringBuilder.toString();
      } 
    } catch (IOException iOException) {
      AppBrandLogger.e("CharacterUtils", new Object[] { "toString", iOException });
    } 
    return stringBuilder.toString();
  }
  
  public static String toString(Object[] paramArrayOfObject) {
    return Arrays.deepToString(paramArrayOfObject);
  }
  
  public static String toString(Object[] paramArrayOfObject, String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    int j = paramArrayOfObject.length;
    for (int i = 0; i < j; i++) {
      stringBuilder.append(paramArrayOfObject[i]);
      stringBuilder.append(paramString);
    } 
    return stringBuilder.toString();
  }
  
  public static final String trimString(String paramString1, int paramInt, boolean paramBoolean, String paramString2) {
    String str1;
    if (TextUtils.isEmpty(paramString1))
      return null; 
    StringBuilder stringBuilder = new StringBuilder();
    char[] arrayOfChar = paramString1.toCharArray();
    int j = arrayOfChar.length;
    boolean bool = false;
    int i;
    for (i = 0; i < j; i++) {
      char c = arrayOfChar[i];
      if (paramInt >= 2) {
        stringBuilder.append(c);
      } else if (paramInt == 1) {
        if (isChinese(c)) {
          if (paramBoolean)
            stringBuilder.append(c); 
        } else {
          stringBuilder.append(c);
        } 
      } else {
        break;
      } 
      if (isChinese(c)) {
        paramInt -= 2;
      } else {
        paramInt--;
      } 
    } 
    String str2 = stringBuilder.toString();
    paramInt = bool;
    if (!str2.contentEquals(paramString1)) {
      paramInt = bool;
      if (paramString2 != null)
        paramInt = 1; 
    } 
    paramString1 = str2;
    if (paramInt != 0) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(str2);
      stringBuilder1.append(paramString2);
      str1 = stringBuilder1.toString();
    } 
    return str1;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphos\\util\CharacterUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */