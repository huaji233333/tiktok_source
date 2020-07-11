package com.facebook.react.bridge;

import android.content.Context;
import com.facebook.react.common.DebugServerException;

public abstract class JSBundleLoader {
  public boolean mDebugRemote;
  
  public RNJavaScriptRuntime.SplitCommonType mUseCommonSplit = RNJavaScriptRuntime.SplitCommonType.NONE;
  
  public JSBundleLoader() {
    this.mUseCommonSplit = RNJavaScriptRuntime.SplitCommonType.NONE;
  }
  
  public JSBundleLoader(RNJavaScriptRuntime.SplitCommonType paramSplitCommonType) {
    this.mUseCommonSplit = paramSplitCommonType;
  }
  
  public JSBundleLoader(RNJavaScriptRuntime.SplitCommonType paramSplitCommonType, boolean paramBoolean) {
    this.mUseCommonSplit = paramSplitCommonType;
    this.mDebugRemote = paramBoolean;
  }
  
  public static JSBundleLoader createAssetLoader(final Context context, final String assetUrl, final boolean loadSynchronously) {
    return new JSBundleLoader(RNJavaScriptRuntime.SplitCommonType.NONE) {
        public final String loadScript(CatalystInstanceImpl param1CatalystInstanceImpl) {
          param1CatalystInstanceImpl.loadScriptFromAssets(context.getAssets(), assetUrl, loadSynchronously);
          return assetUrl;
        }
      };
  }
  
  public static JSBundleLoader createAssetLoader(final Context context, final String assetUrl, final boolean loadSynchronously, RNJavaScriptRuntime.SplitCommonType paramSplitCommonType) {
    return new JSBundleLoader(paramSplitCommonType) {
        public final String loadScript(CatalystInstanceImpl param1CatalystInstanceImpl) {
          param1CatalystInstanceImpl.loadScriptFromAssets(context.getAssets(), assetUrl, loadSynchronously);
          return assetUrl;
        }
      };
  }
  
  public static JSBundleLoader createCachedBundleFromNetworkLoader(final String sourceURL, final String cachedFileLocation, RNJavaScriptRuntime.SplitCommonType paramSplitCommonType) {
    return new JSBundleLoader(paramSplitCommonType) {
        public final String loadScript(CatalystInstanceImpl param1CatalystInstanceImpl) {
          try {
            param1CatalystInstanceImpl.loadScriptFromFile(cachedFileLocation, sourceURL, false, true);
            return sourceURL;
          } catch (Exception exception) {
            throw DebugServerException.makeGeneric(exception.getMessage(), exception);
          } 
        }
      };
  }
  
  public static JSBundleLoader createFileLoader(String paramString) {
    return createFileLoader(paramString, paramString, false, RNJavaScriptRuntime.SplitCommonType.NONE);
  }
  
  public static JSBundleLoader createFileLoader(String paramString, RNJavaScriptRuntime.SplitCommonType paramSplitCommonType) {
    return createFileLoader(paramString, paramString, false, paramSplitCommonType);
  }
  
  public static JSBundleLoader createFileLoader(final String fileName, final String assetUrl, final boolean loadSynchronously, RNJavaScriptRuntime.SplitCommonType paramSplitCommonType) {
    return new JSBundleLoader(paramSplitCommonType) {
        public final String loadScript(CatalystInstanceImpl param1CatalystInstanceImpl) {
          param1CatalystInstanceImpl.loadScriptFromFile(fileName, assetUrl, loadSynchronously);
          return fileName;
        }
      };
  }
  
  public static JSBundleLoader createRemoteDebuggerBundleLoader(final String proxySourceURL, final String realSourceURL, RNJavaScriptRuntime.SplitCommonType paramSplitCommonType) {
    return new JSBundleLoader(paramSplitCommonType, true) {
        public final String loadScript(CatalystInstanceImpl param1CatalystInstanceImpl) {
          param1CatalystInstanceImpl.setSourceURLs(realSourceURL, proxySourceURL);
          return realSourceURL;
        }
      };
  }
  
  public abstract String loadScript(CatalystInstanceImpl paramCatalystInstanceImpl);
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\JSBundleLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */