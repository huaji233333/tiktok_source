package com.facebook.react.modules.camera;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import com.facebook.react.bridge.GuardedAsyncTask;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.module.annotations.ReactModule;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

@ReactModule(name = "CameraRollManager")
public class CameraRollManager extends ReactContextBaseJavaModule {
  public static final boolean IS_JELLY_BEAN_OR_LATER;
  
  public static final String[] PROJECTION = new String[] { "_id", "mime_type", "bucket_display_name", "datetaken", "longitude", "latitude" };
  
  public CameraRollManager(ReactApplicationContext paramReactApplicationContext) {
    super(paramReactApplicationContext);
  }
  
  private static void putBasicNodeInfo(Cursor paramCursor, WritableMap paramWritableMap, int paramInt1, int paramInt2, int paramInt3) {
    paramWritableMap.putString("type", paramCursor.getString(paramInt1));
    paramWritableMap.putString("group_name", paramCursor.getString(paramInt2));
    double d = paramCursor.getLong(paramInt3);
    Double.isNaN(d);
    paramWritableMap.putDouble("timestamp", d / 1000.0D);
  }
  
  public static void putEdges(ContentResolver paramContentResolver, Cursor paramCursor, WritableMap paramWritableMap, int paramInt, String paramString) {
    byte b1;
    byte b2;
    WritableNativeArray writableNativeArray = new WritableNativeArray();
    paramCursor.moveToFirst();
    int j = paramCursor.getColumnIndex("_id");
    int m = paramCursor.getColumnIndex("mime_type");
    int n = paramCursor.getColumnIndex("bucket_display_name");
    int i1 = paramCursor.getColumnIndex("datetaken");
    if (IS_JELLY_BEAN_OR_LATER) {
      b1 = paramCursor.getColumnIndex("width");
    } else {
      b1 = -1;
    } 
    if (IS_JELLY_BEAN_OR_LATER) {
      b2 = paramCursor.getColumnIndex("height");
    } else {
      b2 = -1;
    } 
    int i2 = paramCursor.getColumnIndex("longitude");
    int k = paramCursor.getColumnIndex("latitude");
    int i;
    for (i = 0; i < paramInt && !paramCursor.isAfterLast(); i++) {
      WritableNativeMap writableNativeMap1 = new WritableNativeMap();
      WritableNativeMap writableNativeMap2 = new WritableNativeMap();
      if (putImageInfo(paramContentResolver, paramCursor, (WritableMap)writableNativeMap2, j, b1, b2, paramString)) {
        putBasicNodeInfo(paramCursor, (WritableMap)writableNativeMap2, m, n, i1);
        putLocationInfo(paramCursor, (WritableMap)writableNativeMap2, i2, k);
        writableNativeMap1.putMap("node", (WritableMap)writableNativeMap2);
        writableNativeArray.pushMap((WritableMap)writableNativeMap1);
      } else {
        i--;
      } 
      paramCursor.moveToNext();
    } 
    paramWritableMap.putArray("edges", (WritableArray)writableNativeArray);
  }
  
  private static boolean putImageInfo(ContentResolver paramContentResolver, Cursor paramCursor, WritableMap paramWritableMap, int paramInt1, int paramInt2, int paramInt3, String paramString) {
    // Byte code:
    //   0: new com/facebook/react/bridge/WritableNativeMap
    //   3: dup
    //   4: invokespecial <init> : ()V
    //   7: astore #13
    //   9: aload #6
    //   11: ifnull -> 42
    //   14: aload #6
    //   16: ldc 'Videos'
    //   18: invokevirtual equals : (Ljava/lang/Object;)Z
    //   21: ifeq -> 42
    //   24: getstatic android/provider/MediaStore$Video$Media.EXTERNAL_CONTENT_URI : Landroid/net/Uri;
    //   27: aload_1
    //   28: iload_3
    //   29: invokeinterface getString : (I)Ljava/lang/String;
    //   34: invokestatic withAppendedPath : (Landroid/net/Uri;Ljava/lang/String;)Landroid/net/Uri;
    //   37: astore #12
    //   39: goto -> 57
    //   42: getstatic android/provider/MediaStore$Images$Media.EXTERNAL_CONTENT_URI : Landroid/net/Uri;
    //   45: aload_1
    //   46: iload_3
    //   47: invokeinterface getString : (I)Ljava/lang/String;
    //   52: invokestatic withAppendedPath : (Landroid/net/Uri;Ljava/lang/String;)Landroid/net/Uri;
    //   55: astore #12
    //   57: aload #13
    //   59: ldc 'uri'
    //   61: aload #12
    //   63: invokevirtual toString : ()Ljava/lang/String;
    //   66: invokeinterface putString : (Ljava/lang/String;Ljava/lang/String;)V
    //   71: getstatic com/facebook/react/modules/camera/CameraRollManager.IS_JELLY_BEAN_OR_LATER : Z
    //   74: istore #11
    //   76: ldc -1.0
    //   78: fstore #8
    //   80: iload #11
    //   82: ifeq -> 110
    //   85: aload_1
    //   86: iload #4
    //   88: invokeinterface getInt : (I)I
    //   93: i2f
    //   94: fstore #8
    //   96: aload_1
    //   97: iload #5
    //   99: invokeinterface getInt : (I)I
    //   104: i2f
    //   105: fstore #7
    //   107: goto -> 114
    //   110: ldc -1.0
    //   112: fstore #7
    //   114: fload #8
    //   116: fstore #10
    //   118: fload #7
    //   120: fstore #9
    //   122: aload #6
    //   124: ifnull -> 361
    //   127: fload #8
    //   129: fstore #10
    //   131: fload #7
    //   133: fstore #9
    //   135: aload #6
    //   137: ldc 'Videos'
    //   139: invokevirtual equals : (Ljava/lang/Object;)Z
    //   142: ifeq -> 361
    //   145: fload #8
    //   147: fstore #10
    //   149: fload #7
    //   151: fstore #9
    //   153: getstatic android/os/Build$VERSION.SDK_INT : I
    //   156: bipush #10
    //   158: if_icmplt -> 361
    //   161: aload_0
    //   162: aload #12
    //   164: ldc 'r'
    //   166: invokevirtual openAssetFileDescriptor : (Landroid/net/Uri;Ljava/lang/String;)Landroid/content/res/AssetFileDescriptor;
    //   169: astore_1
    //   170: new android/media/MediaMetadataRetriever
    //   173: dup
    //   174: invokespecial <init> : ()V
    //   177: astore #6
    //   179: aload #6
    //   181: aload_1
    //   182: invokevirtual getFileDescriptor : ()Ljava/io/FileDescriptor;
    //   185: invokevirtual setDataSource : (Ljava/io/FileDescriptor;)V
    //   188: fload #8
    //   190: fconst_0
    //   191: fcmpg
    //   192: ifle -> 206
    //   195: fload #7
    //   197: fstore #9
    //   199: fload #7
    //   201: fconst_0
    //   202: fcmpg
    //   203: ifgt -> 232
    //   206: aload #6
    //   208: bipush #18
    //   210: invokevirtual extractMetadata : (I)Ljava/lang/String;
    //   213: invokestatic parseInt : (Ljava/lang/String;)I
    //   216: i2f
    //   217: fstore #8
    //   219: aload #6
    //   221: bipush #19
    //   223: invokevirtual extractMetadata : (I)Ljava/lang/String;
    //   226: invokestatic parseInt : (Ljava/lang/String;)I
    //   229: i2f
    //   230: fstore #9
    //   232: aload #13
    //   234: ldc 'playableDuration'
    //   236: aload #6
    //   238: bipush #9
    //   240: invokevirtual extractMetadata : (I)Ljava/lang/String;
    //   243: invokestatic parseInt : (Ljava/lang/String;)I
    //   246: sipush #1000
    //   249: idiv
    //   250: invokeinterface putInt : (Ljava/lang/String;I)V
    //   255: aload #6
    //   257: invokevirtual release : ()V
    //   260: aload_1
    //   261: invokevirtual close : ()V
    //   264: fload #8
    //   266: fstore #10
    //   268: goto -> 361
    //   271: astore_0
    //   272: goto -> 317
    //   275: astore_0
    //   276: new java/lang/StringBuilder
    //   279: dup
    //   280: ldc 'Number format exception occurred while trying to fetch video metadata for '
    //   282: invokespecial <init> : (Ljava/lang/String;)V
    //   285: astore_2
    //   286: aload_2
    //   287: aload #12
    //   289: invokevirtual toString : ()Ljava/lang/String;
    //   292: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   295: pop
    //   296: ldc 'ReactNative'
    //   298: aload_2
    //   299: invokevirtual toString : ()Ljava/lang/String;
    //   302: aload_0
    //   303: invokestatic c : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   306: aload #6
    //   308: invokevirtual release : ()V
    //   311: aload_1
    //   312: invokevirtual close : ()V
    //   315: iconst_0
    //   316: ireturn
    //   317: aload #6
    //   319: invokevirtual release : ()V
    //   322: aload_1
    //   323: invokevirtual close : ()V
    //   326: aload_0
    //   327: athrow
    //   328: astore_0
    //   329: new java/lang/StringBuilder
    //   332: dup
    //   333: ldc 'Could not get video metadata for '
    //   335: invokespecial <init> : (Ljava/lang/String;)V
    //   338: astore_1
    //   339: aload_1
    //   340: aload #12
    //   342: invokevirtual toString : ()Ljava/lang/String;
    //   345: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   348: pop
    //   349: ldc 'ReactNative'
    //   351: aload_1
    //   352: invokevirtual toString : ()Ljava/lang/String;
    //   355: aload_0
    //   356: invokestatic c : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   359: iconst_0
    //   360: ireturn
    //   361: fload #10
    //   363: fconst_0
    //   364: fcmpg
    //   365: ifle -> 379
    //   368: fload #9
    //   370: fstore #7
    //   372: fload #9
    //   374: fconst_0
    //   375: fcmpg
    //   376: ifgt -> 429
    //   379: aload_0
    //   380: aload #12
    //   382: ldc 'r'
    //   384: invokevirtual openAssetFileDescriptor : (Landroid/net/Uri;Ljava/lang/String;)Landroid/content/res/AssetFileDescriptor;
    //   387: astore_0
    //   388: new android/graphics/BitmapFactory$Options
    //   391: dup
    //   392: invokespecial <init> : ()V
    //   395: astore_1
    //   396: aload_1
    //   397: iconst_1
    //   398: putfield inJustDecodeBounds : Z
    //   401: aload_0
    //   402: invokevirtual getFileDescriptor : ()Ljava/io/FileDescriptor;
    //   405: aconst_null
    //   406: aload_1
    //   407: invokestatic decodeFileDescriptor : (Ljava/io/FileDescriptor;Landroid/graphics/Rect;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   410: pop
    //   411: aload_1
    //   412: getfield outWidth : I
    //   415: i2f
    //   416: fstore #10
    //   418: aload_1
    //   419: getfield outHeight : I
    //   422: i2f
    //   423: fstore #7
    //   425: aload_0
    //   426: invokevirtual close : ()V
    //   429: aload #13
    //   431: ldc 'width'
    //   433: fload #10
    //   435: f2d
    //   436: invokeinterface putDouble : (Ljava/lang/String;D)V
    //   441: aload #13
    //   443: ldc 'height'
    //   445: fload #7
    //   447: f2d
    //   448: invokeinterface putDouble : (Ljava/lang/String;D)V
    //   453: aload_2
    //   454: ldc_w 'image'
    //   457: aload #13
    //   459: invokeinterface putMap : (Ljava/lang/String;Lcom/facebook/react/bridge/WritableMap;)V
    //   464: iconst_1
    //   465: ireturn
    //   466: astore_0
    //   467: new java/lang/StringBuilder
    //   470: dup
    //   471: ldc_w 'Could not get width/height for '
    //   474: invokespecial <init> : (Ljava/lang/String;)V
    //   477: astore_1
    //   478: aload_1
    //   479: aload #12
    //   481: invokevirtual toString : ()Ljava/lang/String;
    //   484: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   487: pop
    //   488: ldc 'ReactNative'
    //   490: aload_1
    //   491: invokevirtual toString : ()Ljava/lang/String;
    //   494: aload_0
    //   495: invokestatic c : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   498: iconst_0
    //   499: ireturn
    // Exception table:
    //   from	to	target	type
    //   161	188	328	java/io/IOException
    //   206	232	275	java/lang/NumberFormatException
    //   206	232	271	finally
    //   232	255	275	java/lang/NumberFormatException
    //   232	255	271	finally
    //   255	264	328	java/io/IOException
    //   276	306	271	finally
    //   306	315	328	java/io/IOException
    //   317	328	328	java/io/IOException
    //   379	429	466	java/io/IOException
  }
  
  private static void putLocationInfo(Cursor paramCursor, WritableMap paramWritableMap, int paramInt1, int paramInt2) {
    double d1 = paramCursor.getDouble(paramInt1);
    double d2 = paramCursor.getDouble(paramInt2);
    if (d1 > 0.0D || d2 > 0.0D) {
      WritableNativeMap writableNativeMap = new WritableNativeMap();
      writableNativeMap.putDouble("longitude", d1);
      writableNativeMap.putDouble("latitude", d2);
      paramWritableMap.putMap("location", (WritableMap)writableNativeMap);
    } 
  }
  
  public static void putPageInfo(Cursor paramCursor, WritableMap paramWritableMap, int paramInt) {
    boolean bool;
    WritableNativeMap writableNativeMap = new WritableNativeMap();
    if (paramInt < paramCursor.getCount()) {
      bool = true;
    } else {
      bool = false;
    } 
    writableNativeMap.putBoolean("has_next_page", bool);
    if (paramInt < paramCursor.getCount()) {
      paramCursor.moveToPosition(paramInt - 1);
      writableNativeMap.putString("end_cursor", paramCursor.getString(paramCursor.getColumnIndex("datetaken")));
    } 
    paramWritableMap.putMap("page_info", (WritableMap)writableNativeMap);
  }
  
  public String getName() {
    return "CameraRollManager";
  }
  
  @ReactMethod
  public void getPhotos(ReadableMap paramReadableMap, Promise paramPromise) {
    String str1;
    String str2;
    String str3;
    ReadableArray readableArray;
    int i = paramReadableMap.getInt("first");
    if (paramReadableMap.hasKey("after")) {
      str1 = paramReadableMap.getString("after");
    } else {
      str1 = null;
    } 
    if (paramReadableMap.hasKey("groupName")) {
      str2 = paramReadableMap.getString("groupName");
    } else {
      str2 = null;
    } 
    if (paramReadableMap.hasKey("assetType")) {
      str3 = paramReadableMap.getString("assetType");
    } else {
      str3 = null;
    } 
    if (paramReadableMap.hasKey("mimeTypes")) {
      readableArray = paramReadableMap.getArray("mimeTypes");
    } else {
      readableArray = null;
    } 
    if (!paramReadableMap.hasKey("groupTypes")) {
      (new GetPhotosTask((ReactContext)getReactApplicationContext(), i, str1, str2, readableArray, str3, paramPromise)).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new Void[0]);
      return;
    } 
    throw new JSApplicationIllegalArgumentException("groupTypes is not supported on Android");
  }
  
  @ReactMethod
  public void saveToCameraRoll(String paramString1, String paramString2, Promise paramPromise) {
    (new SaveToCameraRoll((ReactContext)getReactApplicationContext(), Uri.parse(paramString1), paramPromise)).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new Void[0]);
  }
  
  static {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 16) {
      bool = true;
    } else {
      bool = false;
    } 
    IS_JELLY_BEAN_OR_LATER = bool;
    if (bool) {
      PROJECTION = new String[] { "_id", "mime_type", "bucket_display_name", "datetaken", "width", "height", "longitude", "latitude" };
      return;
    } 
  }
  
  static class GetPhotosTask extends GuardedAsyncTask<Void, Void> {
    private final String mAfter;
    
    private final String mAssetType;
    
    private final Context mContext;
    
    private final int mFirst;
    
    private final String mGroupName;
    
    private final ReadableArray mMimeTypes;
    
    private final Promise mPromise;
    
    private GetPhotosTask(ReactContext param1ReactContext, int param1Int, String param1String1, String param1String2, ReadableArray param1ReadableArray, String param1String3, Promise param1Promise) {
      super(param1ReactContext);
      this.mContext = (Context)param1ReactContext;
      this.mFirst = param1Int;
      this.mAfter = param1String1;
      this.mGroupName = param1String2;
      this.mMimeTypes = param1ReadableArray;
      this.mPromise = param1Promise;
      this.mAssetType = param1String3;
    }
    
    protected void doInBackgroundGuarded(Void... param1VarArgs) {
      StringBuilder stringBuilder = new StringBuilder("1");
      ArrayList<String> arrayList = new ArrayList();
      if (!TextUtils.isEmpty(this.mAfter)) {
        stringBuilder.append(" AND datetaken < ?");
        arrayList.add(this.mAfter);
      } 
      if (!TextUtils.isEmpty(this.mGroupName)) {
        stringBuilder.append(" AND bucket_display_name = ?");
        arrayList.add(this.mGroupName);
      } 
      ReadableArray readableArray = this.mMimeTypes;
      if (readableArray != null && readableArray.size() > 0) {
        stringBuilder.append(" AND mime_type IN (");
        for (int i = 0; i < this.mMimeTypes.size(); i++) {
          stringBuilder.append("?,");
          arrayList.add(this.mMimeTypes.getString(i));
        } 
        stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), ")");
      } 
      WritableNativeMap writableNativeMap = new WritableNativeMap();
      ContentResolver contentResolver = this.mContext.getContentResolver();
      try {
        Uri uri;
        if (this.mAssetType != null && this.mAssetType.equals("Videos")) {
          uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        } else {
          uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        } 
        String[] arrayOfString2 = CameraRollManager.PROJECTION;
        String str = stringBuilder.toString();
        String[] arrayOfString1 = arrayList.<String>toArray(new String[arrayList.size()]);
        StringBuilder stringBuilder1 = new StringBuilder("datetaken DESC, date_modified DESC LIMIT ");
        stringBuilder1.append(this.mFirst + 1);
        Cursor cursor = contentResolver.query(uri, arrayOfString2, str, arrayOfString1, stringBuilder1.toString());
        if (cursor == null) {
          this.mPromise.reject("E_UNABLE_TO_LOAD", "Could not get photos");
          return;
        } 
        try {
          CameraRollManager.putEdges(contentResolver, cursor, (WritableMap)writableNativeMap, this.mFirst, this.mAssetType);
          CameraRollManager.putPageInfo(cursor, (WritableMap)writableNativeMap, this.mFirst);
          return;
        } finally {
          cursor.close();
          this.mPromise.resolve(writableNativeMap);
        } 
      } catch (SecurityException securityException) {
        this.mPromise.reject("E_UNABLE_TO_LOAD_PERMISSION", "Could not get photos: need READ_EXTERNAL_STORAGE permission", securityException);
        return;
      } 
    }
  }
  
  static class SaveToCameraRoll extends GuardedAsyncTask<Void, Void> {
    private final Context mContext;
    
    public final Promise mPromise;
    
    private final Uri mUri;
    
    public SaveToCameraRoll(ReactContext param1ReactContext, Uri param1Uri, Promise param1Promise) {
      super(param1ReactContext);
      this.mContext = (Context)param1ReactContext;
      this.mUri = param1Uri;
      this.mPromise = param1Promise;
    }
    
    protected void doInBackgroundGuarded(Void... param1VarArgs) {
      Throwable throwable1;
      Throwable throwable2;
      IOException iOException2;
      File file = new File(this.mUri.getPath());
      Context context = null;
      try {
        int i;
        String str2;
        IOException iOException;
        File file2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        file2.mkdirs();
        if (!file2.isDirectory())
          return; 
        File file1 = new File(file2, file.getName());
        String str1 = file.getName();
        if (str1.indexOf('.') >= 0) {
          String str = str1.substring(0, str1.lastIndexOf('.'));
          str2 = str1.substring(str1.lastIndexOf('.'));
          i = 0;
          str1 = str;
        } else {
          str2 = "";
          i = 0;
        } 
        while (!file1.createNewFile()) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(str1);
          stringBuilder.append("_");
          stringBuilder.append(i);
          stringBuilder.append(str2);
          file1 = new File(file2, stringBuilder.toString());
          i++;
        } 
        FileChannel fileChannel = (new FileInputStream(file)).getChannel();
        try {
          FileChannel fileChannel1 = (new FileOutputStream(file1)).getChannel();
        } catch (IOException iOException3) {
        
        } finally {
          file1 = null;
        } 
        iOException2 = iOException;
      } catch (IOException null) {
      
      } finally {
        param1VarArgs = null;
        throwable2 = null;
      } 
      try {
        this.mPromise.reject(throwable2);
        return;
      } finally {
        iOException2 = null;
        throwable2 = iOException1;
      } 
    }
  }
  
  class null implements MediaScannerConnection.OnScanCompletedListener {
    public void onScanCompleted(String param1String, Uri param1Uri) {
      if (param1Uri != null) {
        this.this$0.mPromise.resolve(param1Uri.toString());
        return;
      } 
      this.this$0.mPromise.reject("E_UNABLE_TO_SAVE", "Could not add image to gallery");
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\camera\CameraRollManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */