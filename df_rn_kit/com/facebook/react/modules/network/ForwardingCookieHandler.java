package com.facebook.react.modules.network;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.GuardedAsyncTask;
import com.facebook.react.bridge.GuardedResultAsyncTask;
import com.facebook.react.bridge.ReactContext;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.URI;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ForwardingCookieHandler extends CookieHandler {
  public static final boolean USES_LEGACY_STORE;
  
  private final ReactContext mContext;
  
  private CookieManager mCookieManager;
  
  public final CookieSaver mCookieSaver;
  
  static {
    boolean bool;
    if (Build.VERSION.SDK_INT < 21) {
      bool = true;
    } else {
      bool = false;
    } 
    USES_LEGACY_STORE = bool;
  }
  
  public ForwardingCookieHandler(ReactContext paramReactContext) {
    this.mContext = paramReactContext;
    this.mCookieSaver = new CookieSaver();
  }
  
  private void addCookieAsync(String paramString1, String paramString2) {
    getCookieManager().setCookie(paramString1, paramString2, null);
  }
  
  private void addCookies(final String url, final List<String> cookies) {
    if (USES_LEGACY_STORE) {
      runInBackground(new Runnable() {
            public void run() {
              for (String str : cookies)
                ForwardingCookieHandler.this.getCookieManager().setCookie(url, str); 
              ForwardingCookieHandler.this.mCookieSaver.onCookiesModified();
            }
          });
      return;
    } 
    Iterator<String> iterator = cookies.iterator();
    while (iterator.hasNext())
      addCookieAsync(url, iterator.next()); 
    this.mCookieSaver.onCookiesModified();
  }
  
  private void clearCookiesAsync(final Callback callback) {
    getCookieManager().removeAllCookies(new ValueCallback<Boolean>() {
          public void onReceiveValue(Boolean param1Boolean) {
            ForwardingCookieHandler.this.mCookieSaver.onCookiesModified();
            callback.invoke(new Object[] { param1Boolean });
          }
        });
  }
  
  private static boolean isCookieHeader(String paramString) {
    return (paramString.equalsIgnoreCase("Set-cookie") || paramString.equalsIgnoreCase("Set-cookie2"));
  }
  
  private static void possiblyWorkaroundSyncManager(Context paramContext) {
    if (USES_LEGACY_STORE)
      CookieSyncManager.createInstance(paramContext).sync(); 
  }
  
  public void clearCookies(final Callback callback) {
    if (USES_LEGACY_STORE) {
      (new GuardedResultAsyncTask<Boolean>(this.mContext) {
          protected Boolean doInBackgroundGuarded() {
            ForwardingCookieHandler.this.getCookieManager().removeAllCookie();
            ForwardingCookieHandler.this.mCookieSaver.onCookiesModified();
            return Boolean.valueOf(true);
          }
          
          protected void onPostExecuteGuarded(Boolean param1Boolean) {
            callback.invoke(new Object[] { param1Boolean });
          }
        }).execute((Object[])new Void[0]);
      return;
    } 
    clearCookiesAsync(callback);
  }
  
  public void destroy() {
    if (USES_LEGACY_STORE) {
      getCookieManager().removeExpiredCookie();
      this.mCookieSaver.persistCookies();
    } 
  }
  
  public Map<String, List<String>> get(URI paramURI, Map<String, List<String>> paramMap) throws IOException {
    String str = getCookieManager().getCookie(paramURI.toString());
    return TextUtils.isEmpty(str) ? Collections.emptyMap() : Collections.singletonMap("Cookie", Collections.singletonList(str));
  }
  
  public CookieManager getCookieManager() {
    if (this.mCookieManager == null) {
      possiblyWorkaroundSyncManager((Context)this.mContext);
      this.mCookieManager = CookieManager.getInstance();
      if (USES_LEGACY_STORE)
        this.mCookieManager.removeExpiredCookie(); 
    } 
    return this.mCookieManager;
  }
  
  public void put(URI paramURI, Map<String, List<String>> paramMap) throws IOException {
    String str = paramURI.toString();
    for (Map.Entry<String, List<String>> entry : paramMap.entrySet()) {
      String str1 = (String)entry.getKey();
      if (str1 != null && isCookieHeader(str1))
        addCookies(str, (List<String>)entry.getValue()); 
    } 
  }
  
  public void runInBackground(final Runnable runnable) {
    (new GuardedAsyncTask<Void, Void>(this.mContext) {
        protected void doInBackgroundGuarded(Void... param1VarArgs) {
          runnable.run();
        }
      }).execute((Object[])new Void[0]);
  }
  
  class CookieSaver {
    private final Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
          public boolean handleMessage(Message param2Message) {
            if (param2Message.what == 1) {
              ForwardingCookieHandler.CookieSaver.this.persistCookies();
              return true;
            } 
            return false;
          }
        });
    
    public void flush() {
      ForwardingCookieHandler.this.getCookieManager().flush();
    }
    
    public void onCookiesModified() {
      if (ForwardingCookieHandler.USES_LEGACY_STORE)
        this.mHandler.sendEmptyMessageDelayed(1, 30000L); 
    }
    
    public void persistCookies() {
      this.mHandler.removeMessages(1);
      ForwardingCookieHandler.this.runInBackground(new Runnable() {
            public void run() {
              if (ForwardingCookieHandler.USES_LEGACY_STORE) {
                CookieSyncManager.getInstance().sync();
                return;
              } 
              ForwardingCookieHandler.CookieSaver.this.flush();
            }
          });
    }
  }
  
  class null implements Handler.Callback {
    public boolean handleMessage(Message param1Message) {
      if (param1Message.what == 1) {
        this.this$1.persistCookies();
        return true;
      } 
      return false;
    }
  }
  
  class null implements Runnable {
    public void run() {
      if (ForwardingCookieHandler.USES_LEGACY_STORE) {
        CookieSyncManager.getInstance().sync();
        return;
      } 
      this.this$1.flush();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\network\ForwardingCookieHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */