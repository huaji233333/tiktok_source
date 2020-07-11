package com.facebook.react.views.toolbar;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.views.toolbar.events.ToolbarClickEvent;
import java.util.Map;

public class ReactToolbarManager extends ViewGroupManager<ReactToolbar> {
  private static int[] getDefaultColors(Context paramContext) {
    Exception exception1;
    Context context1;
    TypedArray typedArray;
    Context context3;
    Resources.Theme theme = paramContext.getTheme();
    Exception exception2 = null;
    Context context2 = null;
    try {
    
    } finally {
      Exception exception = null;
      context2 = null;
      paramContext = context2;
      context1 = paramContext;
      context3 = paramContext;
    } 
    recycleQuietly((TypedArray)context3);
    recycleQuietly(typedArray);
    recycleQuietly((TypedArray)context1);
    recycleQuietly((TypedArray)context2);
    throw exception1;
  }
  
  private int[] getDefaultContentInsets(Context paramContext) {
    Exception exception;
    Resources.Theme theme = paramContext.getTheme();
    TypedArray typedArray1 = null;
    TypedArray typedArray2 = null;
    try {
      TypedArray typedArray = theme.obtainStyledAttributes(new int[] { getIdentifier(paramContext, "toolbarStyle") });
      typedArray1 = typedArray2;
    } finally {
      exception = null;
    } 
    recycleQuietly((TypedArray)paramContext);
    recycleQuietly(typedArray1);
    throw exception;
  }
  
  private static int getIdentifier(Context paramContext, String paramString) {
    return paramContext.getResources().getIdentifier(paramString, "attr", paramContext.getPackageName());
  }
  
  private static void recycleQuietly(TypedArray paramTypedArray) {
    if (paramTypedArray != null)
      paramTypedArray.recycle(); 
  }
  
  protected void addEventEmitters(ThemedReactContext paramThemedReactContext, final ReactToolbar view) {
    final EventDispatcher mEventDispatcher = ((UIManagerModule)paramThemedReactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher();
    view.setNavigationOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            mEventDispatcher.dispatchEvent((Event)new ToolbarClickEvent(view.getId(), -1));
          }
        });
    view.setOnMenuItemClickListener(new Toolbar.b() {
          public boolean onMenuItemClick(MenuItem param1MenuItem) {
            mEventDispatcher.dispatchEvent((Event)new ToolbarClickEvent(view.getId(), param1MenuItem.getOrder()));
            return true;
          }
        });
  }
  
  protected ReactToolbar createViewInstance(ThemedReactContext paramThemedReactContext) {
    return new ReactToolbar((Context)paramThemedReactContext);
  }
  
  public Map<String, Object> getExportedViewConstants() {
    return MapBuilder.of("ShowAsAction", MapBuilder.of("never", Integer.valueOf(0), "always", Integer.valueOf(2), "ifRoom", Integer.valueOf(1)));
  }
  
  public String getName() {
    return "ToolbarAndroid";
  }
  
  public boolean needsCustomLayoutForChildren() {
    return true;
  }
  
  @ReactProp(name = "nativeActions")
  public void setActions(ReactToolbar paramReactToolbar, ReadableArray paramReadableArray) {
    paramReactToolbar.setActions(paramReadableArray);
  }
  
  @ReactProp(defaultFloat = NaNF, name = "contentInsetEnd")
  public void setContentInsetEnd(ReactToolbar paramReactToolbar, float paramFloat) {
    int i;
    if (Float.isNaN(paramFloat)) {
      i = getDefaultContentInsets(paramReactToolbar.getContext())[1];
    } else {
      i = Math.round(PixelUtil.toPixelFromDIP(paramFloat));
    } 
    paramReactToolbar.setContentInsetsRelative(paramReactToolbar.getContentInsetStart(), i);
  }
  
  @ReactProp(defaultFloat = NaNF, name = "contentInsetStart")
  public void setContentInsetStart(ReactToolbar paramReactToolbar, float paramFloat) {
    int i;
    if (Float.isNaN(paramFloat)) {
      i = getDefaultContentInsets(paramReactToolbar.getContext())[0];
    } else {
      i = Math.round(PixelUtil.toPixelFromDIP(paramFloat));
    } 
    paramReactToolbar.setContentInsetsRelative(i, paramReactToolbar.getContentInsetEnd());
  }
  
  @ReactProp(name = "logo")
  public void setLogo(ReactToolbar paramReactToolbar, ReadableMap paramReadableMap) {
    paramReactToolbar.setLogoSource(paramReadableMap);
  }
  
  @ReactProp(name = "navIcon")
  public void setNavIcon(ReactToolbar paramReactToolbar, ReadableMap paramReadableMap) {
    paramReactToolbar.setNavIconSource(paramReadableMap);
  }
  
  @ReactProp(name = "overflowIcon")
  public void setOverflowIcon(ReactToolbar paramReactToolbar, ReadableMap paramReadableMap) {
    paramReactToolbar.setOverflowIconSource(paramReadableMap);
  }
  
  @ReactProp(name = "rtl")
  public void setRtl(ReactToolbar paramReactToolbar, boolean paramBoolean) {
    throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  @ReactProp(name = "subtitle")
  public void setSubtitle(ReactToolbar paramReactToolbar, String paramString) {
    paramReactToolbar.setSubtitle(paramString);
  }
  
  @ReactProp(customType = "Color", name = "subtitleColor")
  public void setSubtitleColor(ReactToolbar paramReactToolbar, Integer paramInteger) {
    int[] arrayOfInt = getDefaultColors(paramReactToolbar.getContext());
    if (paramInteger != null) {
      paramReactToolbar.setSubtitleTextColor(paramInteger.intValue());
      return;
    } 
    paramReactToolbar.setSubtitleTextColor(arrayOfInt[1]);
  }
  
  @ReactProp(name = "title")
  public void setTitle(ReactToolbar paramReactToolbar, String paramString) {
    paramReactToolbar.setTitle(paramString);
  }
  
  @ReactProp(customType = "Color", name = "titleColor")
  public void setTitleColor(ReactToolbar paramReactToolbar, Integer paramInteger) {
    int[] arrayOfInt = getDefaultColors(paramReactToolbar.getContext());
    if (paramInteger != null) {
      paramReactToolbar.setTitleTextColor(paramInteger.intValue());
      return;
    } 
    paramReactToolbar.setTitleTextColor(arrayOfInt[0]);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\toolbar\ReactToolbarManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */