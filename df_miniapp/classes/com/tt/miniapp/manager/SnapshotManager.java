package com.tt.miniapp.manager;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.view.ViewGroup;
import com.storage.async.Action;
import com.tt.miniapp.thread.ThreadPools;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.IActivityProxy;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.game.IGameSnapshotProvider;
import com.tt.miniapphost.util.FileUtil;
import com.tt.miniapphost.util.ProcessUtil;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.ref.WeakReference;

public class SnapshotManager {
  public static Bitmap mCacheSnapshot;
  
  public static final Object mCacheSnapshotLock = new Object();
  
  public static WeakReference<Activity> mSnapshotActivityWr = null;
  
  private static Runnable sClearCacheSnapshotRunnable = new Runnable() {
      public final void run() {
        SnapshotManager.clearCacheSnapshot();
      }
    };
  
  public static void clearCacheSnapshot() {
    AppBrandLogger.d("SnapshotManager", new Object[] { "clearCacheSnapshot" });
    ThreadUtil.cancelUIRunnable(sClearCacheSnapshotRunnable);
    if (mCacheSnapshot != null)
      synchronized (mCacheSnapshotLock) {
        mCacheSnapshot = null;
        mSnapshotActivityWr = null;
        return;
      }  
  }
  
  public static void clearSnapshotDir(final Context context) {
    ThreadUtil.runOnWorkThread(new Action() {
          public final void act() {
            FileUtil.clearDir(SnapshotManager.getSnapshotDir(context));
          }
        },  ThreadPools.defaults(), false);
  }
  
  public static byte[] compressSnapshot(Bitmap paramBitmap) {
    byte[] arrayOfByte;
    ByteArrayOutputStream byteArrayOutputStream = null;
    if (paramBitmap == null)
      return null; 
    int i = 100;
    while (i > 60) {
      byteArrayOutputStream = new ByteArrayOutputStream();
      paramBitmap.compress(Bitmap.CompressFormat.JPEG, i, byteArrayOutputStream);
      byte[] arrayOfByte1 = byteArrayOutputStream.toByteArray();
      i -= 20;
      arrayOfByte = arrayOfByte1;
      if (arrayOfByte1.length < 524288) {
        arrayOfByte = arrayOfByte1;
        break;
      } 
    } 
    return arrayOfByte;
  }
  
  private static Bitmap generateCacheSnapshot(Activity paramActivity) {
    AppBrandLogger.i("SnapshotManager", new Object[] { "generateCacheSnapshot activity:", paramActivity });
    if (paramActivity instanceof MiniappHostBase) {
      IActivityProxy iActivityProxy = ((MiniappHostBase)paramActivity).getActivityProxy();
      if (iActivityProxy instanceof IGameSnapshotProvider)
        return ((IGameSnapshotProvider)iActivityProxy).getSnapshot(); 
    } 
    synchronized (mCacheSnapshotLock) {
      if (mCacheSnapshot != null) {
        bitmap = mCacheSnapshot;
        return bitmap;
      } 
      ThreadUtil.runOnUIThread(new Runnable() {
            public final void run() {
              ViewGroup viewGroup = (ViewGroup)activity.getWindow().getDecorView();
              int i = viewGroup.getMeasuredWidth();
              int j = viewGroup.getMeasuredHeight();
              Bitmap bitmap2 = null;
              Bitmap bitmap1 = null;
              null = bitmap2;
              if (i > 0) {
                null = bitmap2;
                if (j > 0) {
                  null = bitmap1;
                  try {
                    bitmap1 = Bitmap.createBitmap(i, j, Bitmap.Config.RGB_565);
                    null = bitmap1;
                    viewGroup.draw(new Canvas(bitmap1));
                    null = bitmap1;
                  } catch (OutOfMemoryError outOfMemoryError) {
                    AppBrandLogger.e("SnapshotManager", new Object[] { "generateCacheSnapshot error:", outOfMemoryError });
                  } 
                } 
              } 
              synchronized (SnapshotManager.mCacheSnapshotLock) {
                SnapshotManager.mCacheSnapshot = null;
                SnapshotManager.mSnapshotActivityWr = new WeakReference<Activity>(activity);
                SnapshotManager.mCacheSnapshotLock.notifyAll();
                return;
              } 
            }
          });
      try {
        mCacheSnapshotLock.wait();
      } catch (InterruptedException interruptedException) {
        AppBrandLogger.e("SnapshotManager", new Object[] { "generateCacheSnapshot", interruptedException });
      } 
      final Bitmap activity = mCacheSnapshot;
      return bitmap;
    } 
  }
  
  public static Bitmap getCropBitmap(Bitmap paramBitmap, int paramInt1, int paramInt2) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual getWidth : ()I
    //   4: istore #4
    //   6: aload_0
    //   7: invokevirtual getHeight : ()I
    //   10: istore #5
    //   12: iload_1
    //   13: ifle -> 24
    //   16: iload_1
    //   17: istore_3
    //   18: iload_1
    //   19: iload #4
    //   21: if_icmplt -> 27
    //   24: iload #4
    //   26: istore_3
    //   27: iload_2
    //   28: ifle -> 39
    //   31: iload_2
    //   32: istore_1
    //   33: iload_2
    //   34: iload #5
    //   36: if_icmplt -> 42
    //   39: iload #5
    //   41: istore_1
    //   42: iload_3
    //   43: iload #4
    //   45: if_icmpne -> 59
    //   48: iload_1
    //   49: iload #5
    //   51: if_icmpeq -> 57
    //   54: goto -> 59
    //   57: aload_0
    //   58: areturn
    //   59: aload_0
    //   60: iconst_0
    //   61: iconst_0
    //   62: iload_3
    //   63: iload_1
    //   64: invokestatic createBitmap : (Landroid/graphics/Bitmap;IIII)Landroid/graphics/Bitmap;
    //   67: areturn
  }
  
  public static Bitmap getSnapshot(Activity paramActivity) {
    if (paramActivity == null) {
      AppBrandLogger.e("SnapshotManager", new Object[] { "getSnapshot activity == null" });
      return null;
    } 
    ThreadUtil.cancelUIRunnable(sClearCacheSnapshotRunnable);
    if (mCacheSnapshot != null)
      synchronized (mCacheSnapshotLock) {
        if (mSnapshotActivityWr == null || paramActivity != mSnapshotActivityWr.get()) {
          AppBrandLogger.i("SnapshotManager", new Object[] { "clear old snapshot" });
          mSnapshotActivityWr = null;
          mCacheSnapshot = null;
        } else {
          ThreadUtil.runOnUIThread(sClearCacheSnapshotRunnable, 3000L);
          return mCacheSnapshot;
        } 
      }  
    Bitmap bitmap = generateCacheSnapshot(paramActivity);
    if (bitmap != null)
      ThreadUtil.runOnUIThread(sClearCacheSnapshotRunnable, 3000L); 
    return bitmap;
  }
  
  public static File getSnapshotDir(Context paramContext) {
    File file = new File(paramContext.getCacheDir(), "miniapp_ss");
    if (!file.exists())
      file.mkdirs(); 
    return file;
  }
  
  public static BitmapDrawable getSnapshotDrawableFromFile(Resources paramResources, String paramString) {
    try {
      return getSnapshotDrawableFromFile(paramResources, paramString, -1, -1);
    } catch (OutOfMemoryError outOfMemoryError) {
      AppBrandLogger.e("SnapshotManager", new Object[] { "getSnapshotDrawableFromFile OutOfMemoryError:", outOfMemoryError });
      FileUtil.delete(new File(paramString));
      return null;
    } 
  }
  
  public static BitmapDrawable getSnapshotDrawableFromFile(Resources paramResources, String paramString, int paramInt1, int paramInt2) {
    byte[] arrayOfByte = FileUtil.readBytes(paramString);
    if (arrayOfByte == null)
      return null; 
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inPreferredConfig = Bitmap.Config.RGB_565;
    Bitmap bitmap = BitmapFactory.decodeByteArray(arrayOfByte, 0, arrayOfByte.length, options);
    FileUtil.delete(new File(paramString));
    return new BitmapDrawable(paramResources, getCropBitmap(bitmap, paramInt1, paramInt2));
  }
  
  public static String getSnapshotSuffix() {
    String str = ProcessUtil.getProcessIdentify();
    int i = str.hashCode();
    if (i != 1716294567) {
      switch (i) {
        default:
          i = -1;
          break;
        case -1359418614:
          if (str.equals("miniapp4")) {
            i = 5;
            break;
          } 
        case -1359418615:
          if (str.equals("miniapp3")) {
            i = 4;
            break;
          } 
        case -1359418616:
          if (str.equals("miniapp2")) {
            i = 3;
            break;
          } 
        case -1359418617:
          if (str.equals("miniapp1")) {
            i = 2;
            break;
          } 
        case -1359418618:
          if (str.equals("miniapp0")) {
            i = 1;
            break;
          } 
      } 
    } else if (str.equals("hostProcess")) {
      i = 0;
    } else {
    
    } 
    return (i != 0) ? ((i != 1) ? ((i != 2) ? ((i != 3) ? ((i != 4) ? ((i != 5) ? "" : ".m4") : ".m3") : ".m2") : ".m1") : ".m0") : ".h";
  }
  
  public static File saveSnapshotFile(Activity paramActivity, byte[] paramArrayOfbyte) {
    if (paramActivity != null && paramArrayOfbyte != null) {
      File file = getSnapshotDir((Context)paramActivity);
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(System.currentTimeMillis());
      stringBuilder.append(getSnapshotSuffix());
      file = new File(file, stringBuilder.toString());
      FileUtil.writeByteToFile(file.getAbsolutePath(), paramArrayOfbyte);
      return file;
    } 
    return null;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\manager\SnapshotManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */