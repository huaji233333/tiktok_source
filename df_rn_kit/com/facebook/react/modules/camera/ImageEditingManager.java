package com.facebook.react.modules.camera;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import com.a;
import com.facebook.common.e.a;
import com.facebook.i.a.a;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.GuardedAsyncTask;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.module.annotations.ReactModule;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@ReactModule(name = "ImageEditingManager")
public class ImageEditingManager extends ReactContextBaseJavaModule {
  private static final String[] EXIF_ATTRIBUTES;
  
  private static final List<String> LOCAL_URI_PREFIXES = Arrays.asList(new String[] { "file://", "content://" });
  
  static {
    EXIF_ATTRIBUTES = new String[] { 
        "FNumber", "DateTime", "DateTimeDigitized", "ExposureTime", "Flash", "FocalLength", "GPSAltitude", "GPSAltitudeRef", "GPSDateStamp", "GPSLatitude", 
        "GPSLatitudeRef", "GPSLongitude", "GPSLongitudeRef", "GPSProcessingMethod", "GPSTimeStamp", "ImageLength", "ImageWidth", "ISOSpeedRatings", "Make", "Model", 
        "Orientation", "SubSecTime", "SubSecTimeDigitized", "SubSecTimeOriginal", "WhiteBalance" };
  }
  
  public ImageEditingManager(ReactApplicationContext paramReactApplicationContext) {
    super(paramReactApplicationContext);
    (new CleanTask((ReactContext)getReactApplicationContext())).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new Void[0]);
  }
  
  public static void copyExif(Context paramContext, Uri paramUri, File paramFile) throws IOException {
    StringBuilder stringBuilder;
    File file = getFileFromUri(paramContext, paramUri);
    if (file == null) {
      stringBuilder = new StringBuilder("Couldn't get real path for uri: ");
      stringBuilder.append(paramUri);
      a.b("ReactNative", stringBuilder.toString());
      return;
    } 
    ExifInterface exifInterface1 = new ExifInterface(stringBuilder.getAbsolutePath());
    ExifInterface exifInterface2 = new ExifInterface(paramFile.getAbsolutePath());
    for (String str1 : EXIF_ATTRIBUTES) {
      String str2 = exifInterface1.getAttribute(str1);
      if (str2 != null)
        exifInterface2.setAttribute(str1, str2); 
    } 
    exifInterface2.saveAttributes();
  }
  
  public static File createTempFile(Context paramContext, String paramString) throws IOException {
    File file1 = paramContext.getExternalCacheDir();
    File file2 = paramContext.getCacheDir();
    if (file1 != null || file2 != null) {
      File file = file2;
      if (file1 != null) {
        if (file2 == null) {
          file = file1;
          return File.createTempFile("ReactNative_cropped_image_", getFileExtensionForType(paramString), file);
        } 
        file = file2;
        if (file1.getFreeSpace() > file2.getFreeSpace()) {
          file = file1;
          return File.createTempFile("ReactNative_cropped_image_", getFileExtensionForType(paramString), file);
        } 
      } 
      return File.createTempFile("ReactNative_cropped_image_", getFileExtensionForType(paramString), file);
    } 
    throw new IOException("No cache directory available");
  }
  
  private static Bitmap.CompressFormat getCompressFormatForType(String paramString) {
    return "image/png".equals(paramString) ? Bitmap.CompressFormat.PNG : ("image/webp".equals(paramString) ? Bitmap.CompressFormat.WEBP : Bitmap.CompressFormat.JPEG);
  }
  
  public static int getDecodeSampleSize(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    int i = 1;
    boolean bool = true;
    if (paramInt2 > paramInt3 || paramInt1 > paramInt4) {
      paramInt2 /= 2;
      int j = paramInt1 / 2;
      paramInt1 = bool;
      while (true) {
        i = paramInt1;
        if (j / paramInt1 >= paramInt3) {
          i = paramInt1;
          if (paramInt2 / paramInt1 >= paramInt4) {
            paramInt1 *= 2;
            continue;
          } 
        } 
        break;
      } 
    } 
    return i;
  }
  
  private static String getFileExtensionForType(String paramString) {
    return "image/png".equals(paramString) ? ".png" : ("image/webp".equals(paramString) ? ".webp" : ".jpg");
  }
  
  private static File getFileFromUri(Context paramContext, Uri paramUri) {
    if (paramUri.getScheme().equals("file"))
      return new File(paramUri.getPath()); 
    if (paramUri.getScheme().equals("content")) {
      Cursor cursor = paramContext.getContentResolver().query(paramUri, new String[] { "_data" }, null, null, null);
      if (cursor != null)
        try {
          if (cursor.moveToFirst()) {
            String str = cursor.getString(0);
            if (!TextUtils.isEmpty(str))
              return new File(str); 
          } 
        } finally {
          cursor.close();
        }  
    } 
    return null;
  }
  
  public static boolean isLocalUri(String paramString) {
    Iterator<String> iterator = LOCAL_URI_PREFIXES.iterator();
    while (iterator.hasNext()) {
      if (paramString.startsWith(iterator.next()))
        return true; 
    } 
    return false;
  }
  
  public static void writeCompressedBitmapToFile(Bitmap paramBitmap, String paramString, File paramFile) throws IOException {
    FileOutputStream fileOutputStream = new FileOutputStream(paramFile);
    try {
      paramBitmap.compress(getCompressFormatForType(paramString), 90, fileOutputStream);
      return;
    } finally {
      fileOutputStream.close();
    } 
  }
  
  @ReactMethod
  public void cropImage(String paramString, ReadableMap paramReadableMap, Callback paramCallback1, Callback paramCallback2) {
    ReadableMap readableMap1;
    boolean bool = paramReadableMap.hasKey("offset");
    ReadableMap readableMap2 = null;
    if (bool) {
      readableMap1 = paramReadableMap.getMap("offset");
    } else {
      readableMap1 = null;
    } 
    if (paramReadableMap.hasKey("size"))
      readableMap2 = paramReadableMap.getMap("size"); 
    if (readableMap1 != null && readableMap2 != null && readableMap1.hasKey("x") && readableMap1.hasKey("y") && readableMap2.hasKey("width") && readableMap2.hasKey("height")) {
      if (paramString != null && !paramString.isEmpty()) {
        CropTask cropTask = new CropTask((ReactContext)getReactApplicationContext(), paramString, (int)readableMap1.getDouble("x"), (int)readableMap1.getDouble("y"), (int)readableMap2.getDouble("width"), (int)readableMap2.getDouble("height"), paramCallback1, paramCallback2);
        if (paramReadableMap.hasKey("displaySize")) {
          paramReadableMap = paramReadableMap.getMap("displaySize");
          cropTask.setTargetSize((int)paramReadableMap.getDouble("width"), (int)paramReadableMap.getDouble("height"));
        } 
        cropTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new Void[0]);
        return;
      } 
      throw new JSApplicationIllegalArgumentException("Please specify a URI");
    } 
    throw new JSApplicationIllegalArgumentException("Please specify offset and size");
  }
  
  public Map<String, Object> getConstants() {
    return Collections.emptyMap();
  }
  
  public String getName() {
    return "ImageEditingManager";
  }
  
  public void onCatalystInstanceDestroy() {
    (new CleanTask((ReactContext)getReactApplicationContext())).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new Void[0]);
  }
  
  static class CleanTask extends GuardedAsyncTask<Void, Void> {
    private final Context mContext;
    
    private CleanTask(ReactContext param1ReactContext) {
      super(param1ReactContext);
      this.mContext = (Context)param1ReactContext;
    }
    
    private void cleanDirectory(File param1File) {
      File[] arrayOfFile = param1File.listFiles(new FilenameFilter() {
            public boolean accept(File param2File, String param2String) {
              return param2String.startsWith("ReactNative_cropped_image_");
            }
          });
      if (arrayOfFile != null) {
        int j = arrayOfFile.length;
        for (int i = 0; i < j; i++)
          arrayOfFile[i].delete(); 
      } 
    }
    
    protected void doInBackgroundGuarded(Void... param1VarArgs) {
      cleanDirectory(this.mContext.getCacheDir());
      File file = this.mContext.getExternalCacheDir();
      if (file != null)
        cleanDirectory(file); 
    }
  }
  
  class null implements FilenameFilter {
    public boolean accept(File param1File, String param1String) {
      return param1String.startsWith("ReactNative_cropped_image_");
    }
  }
  
  static class CropTask extends GuardedAsyncTask<Void, Void> {
    final Context mContext;
    
    final Callback mError;
    
    final int mHeight;
    
    final Callback mSuccess;
    
    int mTargetHeight;
    
    int mTargetWidth;
    
    final String mUri;
    
    final int mWidth;
    
    final int mX;
    
    final int mY;
    
    private CropTask(ReactContext param1ReactContext, String param1String, int param1Int1, int param1Int2, int param1Int3, int param1Int4, Callback param1Callback1, Callback param1Callback2) {
      super(param1ReactContext);
      if (param1Int1 >= 0 && param1Int2 >= 0 && param1Int3 > 0 && param1Int4 > 0) {
        this.mContext = (Context)param1ReactContext;
        this.mUri = param1String;
        this.mX = param1Int1;
        this.mY = param1Int2;
        this.mWidth = param1Int3;
        this.mHeight = param1Int4;
        this.mSuccess = param1Callback1;
        this.mError = param1Callback2;
        return;
      } 
      throw new JSApplicationIllegalArgumentException(a.a("Invalid crop rectangle: [%d, %d, %d, %d]", new Object[] { Integer.valueOf(param1Int1), Integer.valueOf(param1Int2), Integer.valueOf(param1Int3), Integer.valueOf(param1Int4) }));
    }
    
    private Bitmap crop(BitmapFactory.Options param1Options) throws IOException {
      InputStream inputStream = openBitmapInputStream();
      BitmapRegionDecoder bitmapRegionDecoder = BitmapRegionDecoder.newInstance(inputStream, false);
      try {
        return bitmapRegionDecoder.decodeRegion(new Rect(this.mX, this.mY, this.mX + this.mWidth, this.mY + this.mHeight), param1Options);
      } finally {
        if (inputStream != null)
          inputStream.close(); 
        bitmapRegionDecoder.recycle();
      } 
    }
    
    private Bitmap cropAndResize(int param1Int1, int param1Int2, BitmapFactory.Options param1Options) throws IOException {
      a.b(param1Options);
      BitmapFactory.Options options = new BitmapFactory.Options();
      options.inJustDecodeBounds = true;
      InputStream inputStream = openBitmapInputStream();
      try {
        float f5;
        BitmapFactory.decodeStream(inputStream, null, options);
        if (inputStream != null)
          inputStream.close(); 
        int i = this.mWidth;
        float f1 = i;
        int j = this.mHeight;
        float f2 = f1 / j;
        float f3 = param1Int1;
        float f4 = param1Int2;
        f1 = f3 / f4;
        if (f2 > f1) {
          f2 = j * f1;
          f3 = j;
          f5 = this.mX + (i - f2) / 2.0F;
          f1 = this.mY;
          f4 /= j;
        } else {
          f2 = i;
          f1 = i / f1;
          f5 = this.mX;
          float f6 = this.mY;
          float f7 = (j - f1) / 2.0F;
          f4 = f3 / i;
          f6 = f7 + f6;
          f3 = f1;
          f1 = f6;
        } 
        param1Options.inSampleSize = ImageEditingManager.getDecodeSampleSize(this.mWidth, this.mHeight, param1Int1, param1Int2);
        options.inJustDecodeBounds = false;
        inputStream = openBitmapInputStream();
      } finally {
        if (inputStream != null)
          inputStream.close(); 
      } 
    }
    
    private InputStream openBitmapInputStream() throws IOException {
      InputStream inputStream;
      if (ImageEditingManager.isLocalUri(this.mUri)) {
        inputStream = this.mContext.getContentResolver().openInputStream(Uri.parse(this.mUri));
      } else {
        inputStream = (new URL(this.mUri)).openConnection().getInputStream();
      } 
      if (inputStream != null)
        return inputStream; 
      StringBuilder stringBuilder = new StringBuilder("Cannot open bitmap: ");
      stringBuilder.append(this.mUri);
      throw new IOException(stringBuilder.toString());
    }
    
    protected void doInBackgroundGuarded(Void... param1VarArgs) {
      try {
        Bitmap bitmap;
        boolean bool;
        BitmapFactory.Options options = new BitmapFactory.Options();
        if (this.mTargetWidth > 0 && this.mTargetHeight > 0) {
          bool = true;
        } else {
          bool = false;
        } 
        if (bool) {
          bitmap = cropAndResize(this.mTargetWidth, this.mTargetHeight, options);
        } else {
          bitmap = crop(options);
        } 
        String str = options.outMimeType;
        if (str != null && !str.isEmpty()) {
          File file = ImageEditingManager.createTempFile(this.mContext, str);
          ImageEditingManager.writeCompressedBitmapToFile(bitmap, str, file);
          if (str.equals("image/jpeg"))
            ImageEditingManager.copyExif(this.mContext, Uri.parse(this.mUri), file); 
          this.mSuccess.invoke(new Object[] { Uri.fromFile(file).toString() });
          return;
        } 
        throw new IOException("Could not determine MIME type");
      } catch (Exception exception) {
        this.mError.invoke(new Object[] { exception.getMessage() });
        return;
      } 
    }
    
    public void setTargetSize(int param1Int1, int param1Int2) {
      if (param1Int1 > 0 && param1Int2 > 0) {
        this.mTargetWidth = param1Int1;
        this.mTargetHeight = param1Int2;
        return;
      } 
      throw new JSApplicationIllegalArgumentException(a.a("Invalid target size: [%d, %d]", new Object[] { Integer.valueOf(param1Int1), Integer.valueOf(param1Int2) }));
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\camera\ImageEditingManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */