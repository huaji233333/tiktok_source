package com.facebook.react.modules.image;

import android.net.Uri;
import android.util.SparseArray;
import com.facebook.common.b.a;
import com.facebook.common.h.a;
import com.facebook.d.b;
import com.facebook.d.c;
import com.facebook.d.e;
import com.facebook.drawee.a.a.c;
import com.facebook.imagepipeline.e.h;
import com.facebook.imagepipeline.j.c;
import com.facebook.imagepipeline.o.b;
import com.facebook.imagepipeline.o.c;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.GuardedAsyncTask;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.module.annotations.ReactModule;
import java.util.concurrent.Executor;

@ReactModule(name = "ImageLoader")
public class ImageLoaderModule extends ReactContextBaseJavaModule implements LifecycleEventListener {
  private final Object mCallerContext = this;
  
  private final Object mEnqueuedRequestMonitor = new Object();
  
  private final SparseArray<c<Void>> mEnqueuedRequests = new SparseArray();
  
  public ImageLoaderModule(ReactApplicationContext paramReactApplicationContext) {
    super(paramReactApplicationContext);
  }
  
  public ImageLoaderModule(ReactApplicationContext paramReactApplicationContext, Object paramObject) {
    super(paramReactApplicationContext);
  }
  
  private void registerRequest(int paramInt, c<Void> paramc) {
    synchronized (this.mEnqueuedRequestMonitor) {
      this.mEnqueuedRequests.put(paramInt, paramc);
      return;
    } 
  }
  
  @ReactMethod
  public void abortRequest(int paramInt) {
    c<Void> c = removeRequest(paramInt);
    if (c != null)
      c.g(); 
  }
  
  public String getName() {
    return "ImageLoader";
  }
  
  @ReactMethod
  public void getSize(String paramString, final Promise promise) {
    if (paramString == null || paramString.isEmpty()) {
      promise.reject("E_INVALID_URI", "Cannot get the size of an image for an empty URI");
      return;
    } 
    b b = c.a(Uri.parse(paramString)).a();
    c.c().b(b, this.mCallerContext).a((e)new b<a<c>>() {
          public void onFailureImpl(c<a<c>> param1c) {
            promise.reject("E_GET_SIZE_FAILURE", param1c.e());
          }
          
          public void onNewResultImpl(c<a<c>> param1c) {
            if (!param1c.b())
              return; 
            a a = (a)param1c.d();
            if (a != null) {
              try {
                c c1 = (c)a.a();
                WritableMap writableMap = Arguments.createMap();
                writableMap.putInt("width", c1.getWidth());
                writableMap.putInt("height", c1.getHeight());
                promise.resolve(writableMap);
              } catch (Exception exception) {
                promise.reject("E_GET_SIZE_FAILURE", exception);
              } finally {
                Exception exception;
              } 
              a.c(a);
              return;
            } 
            promise.reject("E_GET_SIZE_FAILURE");
          }
        }(Executor)a.a());
  }
  
  public void onHostDestroy() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mEnqueuedRequestMonitor : Ljava/lang/Object;
    //   4: astore_3
    //   5: aload_3
    //   6: monitorenter
    //   7: iconst_0
    //   8: istore_1
    //   9: aload_0
    //   10: getfield mEnqueuedRequests : Landroid/util/SparseArray;
    //   13: invokevirtual size : ()I
    //   16: istore_2
    //   17: iload_1
    //   18: iload_2
    //   19: if_icmpge -> 51
    //   22: aload_0
    //   23: getfield mEnqueuedRequests : Landroid/util/SparseArray;
    //   26: iload_1
    //   27: invokevirtual valueAt : (I)Ljava/lang/Object;
    //   30: checkcast com/facebook/d/c
    //   33: astore #4
    //   35: aload #4
    //   37: ifnull -> 74
    //   40: aload #4
    //   42: invokeinterface g : ()Z
    //   47: pop
    //   48: goto -> 74
    //   51: aload_0
    //   52: getfield mEnqueuedRequests : Landroid/util/SparseArray;
    //   55: invokevirtual clear : ()V
    //   58: aload_3
    //   59: monitorexit
    //   60: return
    //   61: astore #4
    //   63: aload_3
    //   64: monitorexit
    //   65: goto -> 71
    //   68: aload #4
    //   70: athrow
    //   71: goto -> 68
    //   74: iload_1
    //   75: iconst_1
    //   76: iadd
    //   77: istore_1
    //   78: goto -> 17
    // Exception table:
    //   from	to	target	type
    //   9	17	61	finally
    //   22	35	61	finally
    //   40	48	61	finally
    //   51	60	61	finally
    //   63	65	61	finally
  }
  
  public void onHostPause() {}
  
  public void onHostResume() {}
  
  @ReactMethod
  public void prefetchImage(String paramString, final int requestId, final Promise promise) {
    if (paramString == null || paramString.isEmpty()) {
      promise.reject("E_INVALID_URI", "Cannot prefetch an image for an empty URI");
      return;
    } 
    b b = c.a(Uri.parse(paramString)).a();
    c<Void> c = c.c().e(b, this.mCallerContext);
    b<Void> b1 = new b<Void>() {
        public void onFailureImpl(c<Void> param1c) {
          try {
            ImageLoaderModule.this.removeRequest(requestId);
            promise.reject("E_PREFETCH_FAILURE", param1c.e());
            return;
          } finally {
            param1c.g();
          } 
        }
        
        public void onNewResultImpl(c<Void> param1c) {
          if (!param1c.b())
            return; 
          try {
            ImageLoaderModule.this.removeRequest(requestId);
            promise.resolve(Boolean.valueOf(true));
            return;
          } finally {
            param1c.g();
          } 
        }
      };
    registerRequest(requestId, c);
    c.a((e)b1, (Executor)a.a());
  }
  
  @ReactMethod
  public void queryCache(final ReadableArray uris, final Promise promise) {
    (new GuardedAsyncTask<Void, Void>((ReactContext)getReactApplicationContext()) {
        protected void doInBackgroundGuarded(Void... param1VarArgs) {
          WritableMap writableMap = Arguments.createMap();
          h h = c.c();
          for (int i = 0; i < uris.size(); i++) {
            String str = uris.getString(i);
            Uri uri = Uri.parse(str);
            if (h.c(uri)) {
              writableMap.putString(str, "memory");
            } else if (h.d(uri)) {
              writableMap.putString(str, "disk");
            } 
          } 
          promise.resolve(writableMap);
        }
      }).executeOnExecutor(GuardedAsyncTask.THREAD_POOL_EXECUTOR, (Object[])new Void[0]);
  }
  
  public c<Void> removeRequest(int paramInt) {
    synchronized (this.mEnqueuedRequestMonitor) {
      c<Void> c = (c)this.mEnqueuedRequests.get(paramInt);
      this.mEnqueuedRequests.remove(paramInt);
      return c;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\image\ImageLoaderModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */