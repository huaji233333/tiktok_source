package com.he.loader;

import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;
import com.he.Monitor;
import com.he.SettingsProvider;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.LinkedList;

public final class TTAppLoader {
  private long _ptr;
  
  public final Loader impl;
  
  private LoadScriptSample.Callback loadScriptSampleCallback = null;
  
  private final LinkedList<ResolverImpl> pending_files = new LinkedList<ResolverImpl>();
  
  public TTAppLoader(Loader paramLoader) {
    this.impl = paramLoader;
  }
  
  public static native void loadScript(String paramString);
  
  private static native void nativeCleanup();
  
  public static native void nativeReject(long paramLong, String paramString);
  
  public static native void nativeResolve(long paramLong, ByteBuffer paramByteBuffer);
  
  public static native void nativeResolveBytes(long paramLong, byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
  
  public static native void nativeResolveFile(long paramLong, String paramString);
  
  private void reflectedLoad(String paramString, long paramLong) {
    synchronized (this.pending_files) {
      ResolverImpl resolverImpl = new ResolverImpl(paramString, paramLong);
      this.impl.load(paramString, resolverImpl);
      if (resolverImpl.resolved)
        return; 
      this.pending_files.push(resolverImpl);
      return;
    } 
  }
  
  private String reflectedLoadMedia(String paramString) {
    Uri uri = this.impl.loadMedia(paramString);
    return (uri == null) ? null : uri.toString();
  }
  
  private byte[] reflectedLoadSync(String paramString) {
    return this.impl.loadSync(paramString);
  }
  
  private void reflectedLoadUrl(String paramString, long paramLong) {
    this.impl.loadUrl(paramString, new ResolverImpl(paramString, paramLong));
  }
  
  private void reflectedOnAsyncCompile(int paramInt1, int paramInt2, int paramInt3) {
    Monitor.impl.onAsyncCompile(paramInt1, paramInt2, paramInt3);
  }
  
  private void reflectedOnLoadScriptSample(ByteBuffer paramByteBuffer) {
    LoadScriptSample.Callback callback = this.loadScriptSampleCallback;
    if (callback != null) {
      int i = 0;
      int j = paramByteBuffer.capacity();
      while (i < j) {
        callback.onSample(new LoadScriptSample(paramByteBuffer, i));
        i += 320;
      } 
    } 
  }
  
  private void reflectedPostUrl(String paramString1, byte[] paramArrayOfbyte, String paramString2, long paramLong) {
    try {
      this.impl.getClass().getDeclaredMethod("post", new Class[] { String.class, byte[].class, String.class, Resolver.class }).invoke(this.impl, new Object[] { paramString1, paramArrayOfbyte, paramString2, new ResolverImpl(paramString1, paramLong) });
      return;
    } catch (ReflectiveOperationException reflectiveOperationException) {
      return;
    } 
  }
  
  private native void toggleLoadScriptSample(boolean paramBoolean);
  
  public final void cleanup() {
    this._ptr = 0L;
    nativeCleanup();
  }
  
  public final native void loadPackage(String paramString);
  
  public final void reject(String paramString, IOException paramIOException) {
    synchronized (this.pending_files) {
      Iterator<ResolverImpl> iterator = this.pending_files.iterator();
      while (iterator.hasNext()) {
        ResolverImpl resolverImpl = iterator.next();
        if (resolverImpl.path.equals(paramString)) {
          resolverImpl.reject(paramIOException);
          iterator.remove();
        } 
      } 
      return;
    } 
  }
  
  public final void resolve(String paramString, ByteBuffer paramByteBuffer) {
    synchronized (this.pending_files) {
      Iterator<ResolverImpl> iterator = this.pending_files.iterator();
      while (iterator.hasNext()) {
        ResolverImpl resolverImpl = iterator.next();
        if (resolverImpl.path.equals(paramString)) {
          resolverImpl.resolve(paramByteBuffer);
          iterator.remove();
        } 
      } 
      return;
    } 
  }
  
  public final void resolve(String paramString, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    synchronized (this.pending_files) {
      Iterator<ResolverImpl> iterator = this.pending_files.iterator();
      while (iterator.hasNext()) {
        ResolverImpl resolverImpl = iterator.next();
        if (resolverImpl.path.equals(paramString)) {
          resolverImpl.resolve(paramArrayOfbyte, paramInt1, paramInt2);
          iterator.remove();
        } 
      } 
      return;
    } 
  }
  
  public final void setLoadScriptSampleCallback(LoadScriptSample.Callback paramCallback) {
    boolean bool1;
    boolean bool2;
    LoadScriptSample.Callback callback = this.loadScriptSampleCallback;
    if (callback == paramCallback)
      return; 
    boolean bool3 = true;
    if (callback == null) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    if (paramCallback == null) {
      bool2 = true;
    } else {
      bool2 = false;
    } 
    if (bool1 != bool2) {
      if (paramCallback == null)
        bool3 = false; 
      toggleLoadScriptSample(bool3);
    } 
    this.loadScriptSampleCallback = paramCallback;
  }
  
  public final void setup(ContextWrapper paramContextWrapper, SettingsProvider paramSettingsProvider) {
    int i = 32768;
    boolean bool = false;
    String str = "com.he.loader.js_cache";
    if (paramSettingsProvider != null) {
      str = paramSettingsProvider.getSetting((Context)paramContextWrapper, Settings.CODECACHE_DIR, "com.he.loader.js_cache");
      i = paramSettingsProvider.getSetting((Context)paramContextWrapper, Settings.CODECACHE_MINSIZE, 32768);
      bool = paramSettingsProvider.getSetting((Context)paramContextWrapper, Settings.CODECACHE_BACKGROUND_CREATE, false);
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramContextWrapper.getCacheDir());
    stringBuilder.append("/");
    stringBuilder.append(str);
    stringBuilder.append("/");
    setup(stringBuilder.toString(), i, bool);
  }
  
  public final native void setup(String paramString, int paramInt, boolean paramBoolean);
  
  static final class ResolverImpl implements Resolver {
    final long id;
    
    final String path;
    
    boolean resolved;
    
    ResolverImpl(String param1String, long param1Long) {
      this.path = param1String;
      this.id = param1Long;
    }
    
    public final void reject(IOException param1IOException) {
      if (!this.resolved) {
        this.resolved = true;
        TTAppLoader.nativeReject(this.id, param1IOException.getMessage());
        return;
      } 
      throw new RuntimeException("request has been resolved");
    }
    
    public final void resolve(File param1File) {
      if (!this.resolved) {
        this.resolved = true;
        TTAppLoader.nativeResolveFile(this.id, param1File.getPath());
        return;
      } 
      throw new RuntimeException("request has been resolved");
    }
    
    public final void resolve(ByteBuffer param1ByteBuffer) {
      if (!this.resolved) {
        this.resolved = true;
        if (param1ByteBuffer.isDirect()) {
          TTAppLoader.nativeResolve(this.id, param1ByteBuffer);
          return;
        } 
        byte[] arrayOfByte = param1ByteBuffer.array();
        TTAppLoader.nativeResolveBytes(this.id, arrayOfByte, 0, arrayOfByte.length);
        return;
      } 
      throw new RuntimeException("request has been resolved");
    }
    
    public final void resolve(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) {
      if (!this.resolved) {
        this.resolved = true;
        TTAppLoader.nativeResolveBytes(this.id, param1ArrayOfbyte, param1Int1, param1Int2);
        return;
      } 
      throw new RuntimeException("request has been resolved");
    }
  }
  
  public enum Settings {
    CODECACHE_BACKGROUND_CREATE,
    CODECACHE_DIR,
    CODECACHE_MINSIZE("codecache_minsize");
    
    private final String name;
    
    static {
      CODECACHE_BACKGROUND_CREATE = new Settings("CODECACHE_BACKGROUND_CREATE", 2, "codecache_bg_create");
      $VALUES = new Settings[] { CODECACHE_MINSIZE, CODECACHE_DIR, CODECACHE_BACKGROUND_CREATE };
    }
    
    Settings(String param1String1) {
      this.name = param1String1;
    }
    
    public final String toString() {
      return this.name;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\he\loader\TTAppLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */