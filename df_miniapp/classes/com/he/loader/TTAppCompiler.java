package com.he.loader;

import android.content.Context;
import android.content.ContextWrapper;
import com.he.SettingsProvider;

public class TTAppCompiler implements Runnable {
  private static volatile boolean libs_loaded;
  
  private long _ptr;
  
  private int cacheMinSize = 32768;
  
  private Callback callback;
  
  public TTAppCompiler() {
    if (!libs_loaded)
      loadLibs(); 
  }
  
  public TTAppCompiler(Callback paramCallback) {
    if (!libs_loaded)
      loadLibs(); 
    this.callback = paramCallback;
  }
  
  private static native void cleanupCompiler(long paramLong);
  
  private static native void clearTasks(long paramLong);
  
  private static void loadLibs() {
    // Byte code:
    //   0: ldc com/he/loader/TTAppCompiler
    //   2: monitorenter
    //   3: getstatic com/he/loader/TTAppCompiler.libs_loaded : Z
    //   6: istore_0
    //   7: iload_0
    //   8: ifeq -> 15
    //   11: ldc com/he/loader/TTAppCompiler
    //   13: monitorexit
    //   14: return
    //   15: ldc 'c++_shared'
    //   17: invokestatic load : (Ljava/lang/String;)V
    //   20: goto -> 32
    //   23: astore_1
    //   24: ldc 'TTAppCompiler'
    //   26: ldc 'library for c++_shared not loaded'
    //   28: aload_1
    //   29: invokestatic eWithThrowable : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   32: ldc 'v8_libbase.cr'
    //   34: invokestatic load : (Ljava/lang/String;)V
    //   37: ldc 'v8_libplatform.cr'
    //   39: invokestatic load : (Ljava/lang/String;)V
    //   42: ldc 'v8.cr'
    //   44: invokestatic load : (Ljava/lang/String;)V
    //   47: ldc 'skialite'
    //   49: invokestatic load : (Ljava/lang/String;)V
    //   52: goto -> 64
    //   55: astore_1
    //   56: ldc 'TTAppCompiler'
    //   58: ldc 'load skialite failed'
    //   60: aload_1
    //   61: invokestatic eWithThrowable : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   64: ldc 'helium'
    //   66: invokestatic load : (Ljava/lang/String;)V
    //   69: goto -> 81
    //   72: astore_1
    //   73: ldc 'TTAppCompiler'
    //   75: ldc 'library for v8xxx not loaded'
    //   77: aload_1
    //   78: invokestatic eWithThrowable : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   81: iconst_1
    //   82: putstatic com/he/loader/TTAppCompiler.libs_loaded : Z
    //   85: ldc com/he/loader/TTAppCompiler
    //   87: monitorexit
    //   88: return
    //   89: astore_1
    //   90: ldc com/he/loader/TTAppCompiler
    //   92: monitorexit
    //   93: aload_1
    //   94: athrow
    // Exception table:
    //   from	to	target	type
    //   3	7	89	finally
    //   15	20	23	finally
    //   24	32	89	finally
    //   32	47	72	finally
    //   47	52	55	finally
    //   56	64	72	finally
    //   64	69	72	finally
    //   73	81	89	finally
    //   81	85	89	finally
  }
  
  private static native void pauseCompiler(long paramLong);
  
  private static native int queueTask(long paramLong, byte[] paramArrayOfbyte, String paramString, boolean paramBoolean, int paramInt);
  
  private void reflectedOnAsyncCompile(int paramInt1, int paramInt2, int paramInt3) {
    StringBuilder stringBuilder = new StringBuilder("compiled, id:  ");
    stringBuilder.append(paramInt1);
    stringBuilder.append(", duration: ");
    stringBuilder.append(paramInt2);
    stringBuilder.append(", cache_size: ");
    stringBuilder.append(paramInt3);
    Log.i("TTAppCompiler", new Object[] { stringBuilder.toString() });
    Callback callback = this.callback;
    if (callback != null)
      callback.onCompiled(paramInt1, paramInt2, paramInt3); 
  }
  
  private static native boolean removeTask(long paramLong, int paramInt);
  
  private static native void resumeCompiler(long paramLong);
  
  private native long setupCompiler(String paramString);
  
  private static native void startCompiler(long paramLong);
  
  private static native void stopCompiler(long paramLong);
  
  public void cleanup() {
    cleanupCompiler(this._ptr);
    this._ptr = 0L;
  }
  
  public void clearTasks() {
    long l = this._ptr;
    if (l != 0L) {
      clearTasks(l);
      return;
    } 
    throw new RuntimeException("TTAppCompilerPtr is null");
  }
  
  public void pause() {
    long l = this._ptr;
    if (l != 0L) {
      pauseCompiler(l);
      return;
    } 
    throw new RuntimeException("TTAppCompilerPtr is null");
  }
  
  public int queueTask(byte[] paramArrayOfbyte, String paramString, boolean paramBoolean, int paramInt) {
    long l = this._ptr;
    if (l != 0L)
      return queueTask(l, paramArrayOfbyte, paramString, paramBoolean, paramInt); 
    throw new RuntimeException("TTAppCompilerPtr is null");
  }
  
  public boolean removeTask(int paramInt) {
    long l = this._ptr;
    if (l != 0L)
      return removeTask(l, paramInt); 
    throw new RuntimeException("TTAppCompilerPtr is null");
  }
  
  public void resume() {
    long l = this._ptr;
    if (l != 0L) {
      resumeCompiler(l);
      return;
    } 
    throw new RuntimeException("TTAppCompilerPtr is null");
  }
  
  public void run() {
    long l = this._ptr;
    if (l != 0L) {
      startCompiler(l);
      return;
    } 
    throw new RuntimeException("TTAppCompilerPtr is null");
  }
  
  public void setup(ContextWrapper paramContextWrapper, SettingsProvider paramSettingsProvider) {
    String str = "com.he.loader.js_cache";
    if (paramSettingsProvider != null) {
      str = paramSettingsProvider.getSetting((Context)paramContextWrapper, TTAppLoader.Settings.CODECACHE_DIR, "com.he.loader.js_cache");
      this.cacheMinSize = paramSettingsProvider.getSetting((Context)paramContextWrapper, TTAppLoader.Settings.CODECACHE_MINSIZE, this.cacheMinSize);
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramContextWrapper.getCacheDir());
    stringBuilder.append("/");
    stringBuilder.append(str);
    stringBuilder.append("/");
    this._ptr = setupCompiler(stringBuilder.toString());
  }
  
  public boolean shouldCache(int paramInt) {
    return (paramInt >= this.cacheMinSize);
  }
  
  public void stop() {
    long l = this._ptr;
    if (l != 0L) {
      stopCompiler(l);
      return;
    } 
    throw new RuntimeException("TTAppCompilerPtr is null");
  }
  
  public static interface Callback {
    void onCompiled(int param1Int1, int param1Int2, int param1Int3);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\he\loader\TTAppCompiler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */