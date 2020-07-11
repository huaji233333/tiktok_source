package com.tt.miniapp.chooser.adapter;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;
import com.tt.miniapphost.AppBrandLogger;
import java.lang.ref.WeakReference;

public class VideoThumbLoader {
  private static LruCache<String, Bitmap> lruCache;
  
  public VideoThumbLoader() {
    int i = (int)Runtime.getRuntime().maxMemory() / 4;
    if (lruCache == null)
      lruCache = new LruCache<String, Bitmap>(i) {
          protected int sizeOf(String param1String, Bitmap param1Bitmap) {
            return param1Bitmap.getByteCount();
          }
        }; 
  }
  
  public static void addVideoThumbToCache(String paramString, Bitmap paramBitmap) {
    if (getVideoThumbToCache(paramString) == null)
      lruCache.put(paramString, paramBitmap); 
  }
  
  public static Bitmap getVideoThumbToCache(String paramString) {
    return (Bitmap)lruCache.get(paramString);
  }
  
  public void showThumb(String paramString, ImageView paramImageView) {
    if (getVideoThumbToCache(paramString) == null) {
      paramImageView.setTag(2097545452, paramString);
      (new BobAsyncTask(paramImageView, paramString)).execute((Object[])new String[] { paramString });
      return;
    } 
    paramImageView.setImageBitmap(getVideoThumbToCache(paramString));
  }
  
  static class BobAsyncTask extends AsyncTask<String, Void, Bitmap> {
    private WeakReference<ImageView> imgViewRef;
    
    private String path;
    
    public BobAsyncTask(ImageView param1ImageView, String param1String) {
      this.imgViewRef = new WeakReference<ImageView>(param1ImageView);
      this.path = param1String;
    }
    
    protected Bitmap doInBackground(String... param1VarArgs) {
      try {
        Bitmap bitmap1 = ThumbnailUtils.createVideoThumbnail(param1VarArgs[0], 3);
        if (bitmap1 == null)
          return null; 
        Bitmap bitmap2 = bitmap1;
        try {
          if (VideoThumbLoader.getVideoThumbToCache(param1VarArgs[0]) == null) {
            VideoThumbLoader.addVideoThumbToCache(this.path, bitmap1);
            return bitmap1;
          } 
        } catch (Exception exception1) {
          Bitmap bitmap = bitmap1;
          exception = exception1;
        } 
      } catch (Exception exception) {
        param1VarArgs = null;
      } 
      AppBrandLogger.stacktrace(6, "VideoThumbLoader", exception.getStackTrace());
      String[] arrayOfString = param1VarArgs;
    }
    
    protected void onPostExecute(Bitmap param1Bitmap) {
      ImageView imageView = this.imgViewRef.get();
      if (imageView != null && this.path.equals(imageView.getTag(2097545452)))
        imageView.setImageBitmap(param1Bitmap); 
    }
    
    protected void onPreExecute() {
      super.onPreExecute();
      ImageView imageView = this.imgViewRef.get();
      if (imageView != null)
        imageView.setImageBitmap(null); 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\chooser\adapter\VideoThumbLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */