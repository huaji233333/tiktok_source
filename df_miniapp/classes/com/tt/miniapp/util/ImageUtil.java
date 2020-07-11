package com.tt.miniapp.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import com.tt.b.a;
import com.tt.b.c;
import com.tt.miniapphost.host.HostDependManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtil {
  private static final String[] PATH_PROJECTION = new String[] { "_data" };
  
  private static int calculateInSampleSize(BitmapFactory.Options paramOptions, int paramInt1, int paramInt2) {
    int k = paramOptions.outHeight;
    int m = paramOptions.outWidth;
    int j = 1;
    int i = 1;
    if (k > paramInt2 || m > paramInt1) {
      k /= 2;
      m /= 2;
      while (true) {
        j = i;
        if (k / i >= paramInt2) {
          j = i;
          if (m / i >= paramInt1) {
            i *= 2;
            continue;
          } 
        } 
        break;
      } 
    } 
    return j;
  }
  
  public static File compressImage(File paramFile, int paramInt1, int paramInt2, Bitmap.CompressFormat paramCompressFormat, int paramInt3, String paramString) throws IOException {
    File file = (new File(paramString)).getParentFile();
    if (!file.exists())
      file.mkdirs(); 
    try {
      FileOutputStream fileOutputStream = new FileOutputStream(paramString);
    } finally {
      paramCompressFormat = null;
    } 
    if (paramFile != null) {
      paramFile.flush();
      paramFile.close();
    } 
    throw paramCompressFormat;
  }
  
  public static Bitmap cropCenterBitmap(Bitmap paramBitmap) {
    int j = paramBitmap.getWidth();
    int k = paramBitmap.getHeight();
    int i = j;
    if (j >= k)
      i = k; 
    return Bitmap.createBitmap(paramBitmap, (paramBitmap.getWidth() - i) / 2, (paramBitmap.getHeight() - i) / 2, i, i);
  }
  
  public static Bitmap decodeSampledBitmapFromFile(File paramFile, int paramInt1, int paramInt2) throws IOException {
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(paramFile.getAbsolutePath(), options);
    options.inSampleSize = calculateInSampleSize(options, paramInt1, paramInt2);
    options.inJustDecodeBounds = false;
    Bitmap bitmap2 = BitmapFactory.decodeFile(paramFile.getAbsolutePath(), options);
    Bitmap bitmap1 = bitmap2;
    if (bitmap2 != null) {
      Matrix matrix = new Matrix();
      paramInt1 = (new ExifInterface(paramFile.getAbsolutePath())).getAttributeInt("Orientation", 0);
      if (paramInt1 == 6) {
        matrix.postRotate(90.0F);
      } else if (paramInt1 == 3) {
        matrix.postRotate(180.0F);
      } else if (paramInt1 == 8) {
        matrix.postRotate(270.0F);
      } 
      bitmap1 = Bitmap.createBitmap(bitmap2, 0, 0, bitmap2.getWidth(), bitmap2.getHeight(), matrix, true);
    } 
    return bitmap1;
  }
  
  public static String getVideoThumbPath(Context paramContext, int paramInt) {
    Context context = null;
    try {
      Cursor cursor = paramContext.getContentResolver().query(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, PATH_PROJECTION, "kind = 1 AND video_id = ?", new String[] { String.valueOf(paramInt) }, null);
      return null;
    } finally {
      paramContext = context;
      if (paramContext != null)
        paramContext.close(); 
    } 
  }
  
  public static void loadBitmap(Context paramContext, String paramString, Rect paramRect, final BitmapCallback cb) {
    if (paramContext != null) {
      if (TextUtils.isEmpty(paramString))
        return; 
      final ImageView imageView = new ImageView(paramContext);
      c c = (new c(paramString)).a(new a() {
            public final void onFail(Exception param1Exception) {
              ImageUtil.BitmapCallback bitmapCallback = cb;
              if (bitmapCallback == null)
                return; 
              bitmapCallback.onFailed();
            }
            
            public final void onSuccess() {
              if (cb == null)
                return; 
              Drawable drawable = imageView.getDrawable();
              if (drawable instanceof BitmapDrawable) {
                cb.onSucceed(((BitmapDrawable)drawable).getBitmap());
                return;
              } 
              cb.onFailed();
            }
          });
      if (paramRect != null)
        c.a(paramRect.width(), paramRect.height()); 
      c.a((View)imageView);
      HostDependManager.getInst().loadImage(imageView.getContext(), c);
    } 
  }
  
  public static void loadBitmap(Context paramContext, String paramString, BitmapCallback paramBitmapCallback) {
    loadBitmap(paramContext, paramString, null, paramBitmapCallback);
  }
  
  public static Bitmap rotaingImageView(int paramInt, Bitmap paramBitmap) {
    Matrix matrix = new Matrix();
    matrix.postRotate(paramInt);
    Bitmap bitmap = Bitmap.createBitmap(paramBitmap, 0, 0, paramBitmap.getWidth(), paramBitmap.getHeight(), matrix, true);
    if (bitmap != paramBitmap && paramBitmap != null && !paramBitmap.isRecycled())
      paramBitmap.recycle(); 
    return bitmap;
  }
  
  public static interface BitmapCallback {
    void onFailed();
    
    void onSucceed(Bitmap param1Bitmap);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\ImageUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */