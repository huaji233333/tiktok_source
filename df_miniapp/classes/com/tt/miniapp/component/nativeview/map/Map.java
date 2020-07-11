package com.tt.miniapp.component.nativeview.map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.storage.async.Function;
import com.storage.async.Observable;
import com.storage.async.Schedulers;
import com.storage.async.Subscriber;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.component.nativeview.NativeComponent;
import com.tt.miniapp.view.webcore.AbsoluteLayout;
import com.tt.miniapp.view.webcore.NativeNestWebView;
import com.tt.miniapp.webbridge.sync.map.MapParamParseException;
import com.tt.miniapp.webbridge.sync.map.MapTempUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.util.UIUtils;
import com.tt.option.e.k;
import com.tt.option.m.a;
import com.tt.option.m.c;
import com.tt.option.m.d;
import com.tt.option.m.e;
import org.json.JSONArray;
import org.json.JSONObject;

public class Map extends FrameLayout implements NativeComponent {
  public a mMapContext;
  
  private View mMapView;
  
  private e mMyLocatePointStyle;
  
  public Location mMyLocation;
  
  private NativeNestWebView mNativeNestWebView;
  
  private AbsoluteLayout mParent;
  
  private WebViewManager.IRender mRender;
  
  private final int mViewId;
  
  public Map(int paramInt, AbsoluteLayout paramAbsoluteLayout, WebViewManager.IRender paramIRender, NativeNestWebView paramNativeNestWebView) {
    super(paramAbsoluteLayout.getContext());
    this.mViewId = paramInt;
    this.mParent = paramAbsoluteLayout;
    this.mRender = paramIRender;
    this.mNativeNestWebView = paramNativeNestWebView;
    this.mMyLocatePointStyle = new e();
  }
  
  private void addCircles(MapModel paramMapModel) {
    // Byte code:
    //   0: aload_1
    //   1: getfield circles : Lorg/json/JSONArray;
    //   4: astore #13
    //   6: iconst_0
    //   7: istore #10
    //   9: iload #10
    //   11: aload #13
    //   13: invokevirtual length : ()I
    //   16: if_icmpge -> 224
    //   19: aload #13
    //   21: iload #10
    //   23: invokevirtual getJSONObject : (I)Lorg/json/JSONObject;
    //   26: astore_1
    //   27: aload_1
    //   28: ldc 'latitude'
    //   30: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   33: invokestatic parseDouble : (Ljava/lang/String;)D
    //   36: dstore_2
    //   37: aload_1
    //   38: ldc 'longitude'
    //   40: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   43: invokestatic parseDouble : (Ljava/lang/String;)D
    //   46: dstore #4
    //   48: dload_2
    //   49: dload #4
    //   51: invokestatic isValidLatLng : (DD)Z
    //   54: ifne -> 60
    //   57: goto -> 190
    //   60: aload_1
    //   61: ldc 'radius'
    //   63: ldc2_w 100.0
    //   66: invokevirtual optDouble : (Ljava/lang/String;D)D
    //   69: dstore #6
    //   71: aload_1
    //   72: ldc 'fillColor'
    //   74: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   77: ldc '#3352AFFF'
    //   79: invokestatic rgbaToFullARGBStr : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   82: ldc '#3352AFFF'
    //   84: invokestatic parseColor : (Ljava/lang/String;Ljava/lang/String;)I
    //   87: istore #11
    //   89: aload_1
    //   90: ldc 'strokeWidth'
    //   92: dconst_1
    //   93: invokevirtual optDouble : (Ljava/lang/String;D)D
    //   96: dstore #8
    //   98: aload_1
    //   99: ldc 'color'
    //   101: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   104: ldc '#9952AFFF'
    //   106: invokestatic rgbaToFullARGBStr : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   109: ldc '#9952AFFF'
    //   111: invokestatic parseColor : (Ljava/lang/String;Ljava/lang/String;)I
    //   114: istore #12
    //   116: new com/tt/option/m/b
    //   119: dup
    //   120: invokespecial <init> : ()V
    //   123: astore_1
    //   124: aload_1
    //   125: new com/tt/option/m/c
    //   128: dup
    //   129: dload_2
    //   130: dload #4
    //   132: invokespecial <init> : (DD)V
    //   135: putfield a : Lcom/tt/option/m/c;
    //   138: aload_1
    //   139: dload #6
    //   141: putfield b : D
    //   144: aload_1
    //   145: iload #11
    //   147: putfield c : I
    //   150: aload_1
    //   151: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
    //   154: invokevirtual getApplicationContext : ()Landroid/app/Application;
    //   157: dload #8
    //   159: d2f
    //   160: invokestatic dip2Px : (Landroid/content/Context;F)F
    //   163: f2d
    //   164: putfield d : D
    //   167: aload_1
    //   168: iload #12
    //   170: putfield e : I
    //   173: goto -> 190
    //   176: astore_1
    //   177: ldc 'tma_Map'
    //   179: iconst_1
    //   180: anewarray java/lang/Object
    //   183: dup
    //   184: iconst_0
    //   185: aload_1
    //   186: aastore
    //   187: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   190: goto -> 215
    //   193: astore_1
    //   194: goto -> 202
    //   197: astore_1
    //   198: goto -> 202
    //   201: astore_1
    //   202: ldc 'tma_Map'
    //   204: iconst_1
    //   205: anewarray java/lang/Object
    //   208: dup
    //   209: iconst_0
    //   210: aload_1
    //   211: aastore
    //   212: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   215: iload #10
    //   217: iconst_1
    //   218: iadd
    //   219: istore #10
    //   221: goto -> 9
    //   224: return
    // Exception table:
    //   from	to	target	type
    //   19	27	201	org/json/JSONException
    //   27	48	176	java/lang/Exception
    //   27	48	201	org/json/JSONException
    //   48	57	201	org/json/JSONException
    //   60	89	201	org/json/JSONException
    //   89	173	193	org/json/JSONException
    //   177	190	193	org/json/JSONException
  }
  
  private void addMarkers(MapModel paramMapModel) {
    final JSONArray markersJA = paramMapModel.markers;
    for (final int finalIndex = 0; i < jSONArray.length(); i++) {
      Observable.create(new Function<d>() {
            public d fun() {
              JSONObject jSONObject = markersJA.optJSONObject(finalIndex);
              String str = jSONObject.optString("id", "0");
              try {
                double d1 = Double.parseDouble(jSONObject.optString("latitude"));
                double d2 = Double.parseDouble(jSONObject.optString("longitude"));
                if (!MapUtil.isValidLatLng(d1, d2))
                  return null; 
                String str1 = jSONObject.optString("title", null);
                Bitmap bitmap = MapUtil.parseMiniAppImagePath(jSONObject.optString("iconPath"), BitmapFactory.decodeResource(AppbrandContext.getInst().getApplicationContext().getResources(), 2097479735));
                d d = new d();
                d.a = str;
                d.d = str1;
                return d.a(bitmap).a(new c(d1, d2));
              } catch (Exception exception) {
                AppBrandLogger.e("tma_Map", new Object[] { exception });
                return null;
              } 
            }
          }).schudleOn(Schedulers.longIO()).subscribe((Subscriber)new Subscriber.ResultableSubscriber<d>() {
            public void onError(Throwable param1Throwable) {
              AppBrandLogger.e("tma_Map", new Object[] { param1Throwable });
            }
            
            public void onSuccess(d param1d) {}
          });
    } 
  }
  
  private String initMap(MapModel paramMapModel) {
    MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
    this.mMapContext = HostDependManager.getInst().createMapInstance();
    if (miniappHostBase == null)
      return MapTempUtil.makeFailMsg("insertMapContext", "activity is null", 205); 
    a a1 = this.mMapContext;
    if (a1 == null)
      return MapTempUtil.makeFailMsg("insertMapContext", "map context create fail", 206); 
    this.mMapView = a1.a((Activity)miniappHostBase);
    if (this.mMapView == null)
      return MapTempUtil.makeFailMsg("insertMapContext", "create map view failed", 203); 
    new Object() {
        public void onLocationChanged(Location param1Location, String param1String) {
          Map.this.mMyLocation = param1Location;
        }
      };
    new Bundle();
    FrameLayout.LayoutParams layoutParams1 = new FrameLayout.LayoutParams(-1, -1);
    addView(this.mMapView, (ViewGroup.LayoutParams)layoutParams1);
    AbsoluteLayout.LayoutParams layoutParams = new AbsoluteLayout.LayoutParams((int)UIUtils.dip2Px((Context)miniappHostBase, (float)paramMapModel.width), (int)UIUtils.dip2Px((Context)miniappHostBase, (float)paramMapModel.height), (int)UIUtils.dip2Px((Context)miniappHostBase, paramMapModel.left), (int)UIUtils.dip2Px((Context)miniappHostBase, paramMapModel.top));
    this.mParent.addView((View)this, (ViewGroup.LayoutParams)layoutParams);
    this.mMyLocatePointStyle.a = BitmapFactory.decodeResource(miniappHostBase.getResources(), 2097479681);
    return null;
  }
  
  private void setLocatePointVisibility(boolean paramBoolean) {
    this.mMyLocatePointStyle.b = paramBoolean;
  }
  
  private void updateMapContext(MapModel paramMapModel) {
    new c(paramMapModel.latitude, paramMapModel.longitude);
    if (paramMapModel.hasShowLocation)
      setLocatePointVisibility(paramMapModel.showLocation); 
    if (paramMapModel.hasMarkers)
      addMarkers(paramMapModel); 
    if (paramMapModel.hasCircles)
      addCircles(paramMapModel); 
  }
  
  public void addView(String paramString, k paramk) {
    try {
      MapModel mapModel = MapModel.parse(paramString);
      if (mapModel == null)
        return; 
      String str = initMap(mapModel);
      if (str != null)
        return; 
      updateMapContext(mapModel);
    } catch (MapParamParseException mapParamParseException) {
      return;
    } finally {
      paramString = null;
      paramk.invokeHandler(MapTempUtil.makeFailMsg("insertMapContext", (Throwable)paramString, 201));
    } 
  }
  
  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent) {
    boolean bool;
    if ((paramMotionEvent.getAction() & 0xFF) == 0) {
      bool = true;
    } else {
      bool = false;
    } 
    if (bool)
      requestDisallowInterceptTouchEvent(true); 
    return super.dispatchTouchEvent(paramMotionEvent);
  }
  
  public a getMapContext() {
    return this.mMapContext;
  }
  
  public Location getMyLocation() {
    return this.mMyLocation;
  }
  
  public boolean onBackPressed() {
    return false;
  }
  
  public void onDestroy() {}
  
  public void onViewPause() {}
  
  public void onViewResume() {}
  
  public void removeView(int paramInt, k paramk) {}
  
  public void updateView(String paramString, k paramk) {
    MiniappHostBase miniappHostBase;
    try {
      MapModel mapModel = MapModel.parse(paramString);
      if (mapModel == null)
        return; 
      miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
      if ((mapModel.hasLeft || mapModel.hasTop || mapModel.hasWidth || mapModel.hasHeight) && miniappHostBase != null) {
        int i;
        int j;
        int m;
        int n;
        AbsoluteLayout.LayoutParams layoutParams = (AbsoluteLayout.LayoutParams)getLayoutParams();
        if (mapModel.hasLeft) {
          i = (int)UIUtils.dip2Px((Context)miniappHostBase, mapModel.left);
        } else {
          i = layoutParams.x;
        } 
        if (mapModel.hasTop) {
          j = (int)UIUtils.dip2Px((Context)miniappHostBase, mapModel.top);
        } else {
          j = layoutParams.y;
        } 
        if (mapModel.hasWidth) {
          m = (int)UIUtils.dip2Px((Context)miniappHostBase, (float)mapModel.width);
        } else {
          m = layoutParams.width;
        } 
        if (mapModel.hasHeight) {
          n = (int)UIUtils.dip2Px((Context)miniappHostBase, (float)mapModel.height);
        } else {
          n = layoutParams.height;
        } 
        setLayoutParams((ViewGroup.LayoutParams)new AbsoluteLayout.LayoutParams(m, n, i, j));
      } 
      if (mapModel.hasLatitude || mapModel.hasLongitude) {
        double d1 = mapModel.latitude;
        double d2 = mapModel.longitude;
        if (MapUtil.isValidLatLng(d1, d2))
          new c(d1, d2); 
      } 
      if (mapModel.hasMarkers)
        addMarkers(mapModel); 
      return;
    } catch (MapParamParseException mapParamParseException) {
      return;
    } finally {
      paramString = null;
      miniappHostBase.invokeHandler(MapTempUtil.makeFailMsg("updateMapContext", (Throwable)paramString, 201));
    } 
  }
  
  public static class MapModel {
    JSONArray circles;
    
    boolean hasCircles;
    
    boolean hasHeight;
    
    boolean hasLatitude;
    
    boolean hasLeft;
    
    boolean hasLongitude;
    
    boolean hasMarkers;
    
    boolean hasScale;
    
    boolean hasShowLocation;
    
    boolean hasTop;
    
    boolean hasWidth;
    
    double height;
    
    double latitude;
    
    int left;
    
    double longitude;
    
    JSONArray markers;
    
    double scale = 16.0D;
    
    boolean showLocation;
    
    int top;
    
    double width;
    
    public static MapModel parse(String param1String) throws Throwable {
      double d;
      MapModel mapModel = new MapModel();
      JSONObject jSONObject = (new JSONObject(param1String)).optJSONObject("model");
      if (jSONObject == null)
        return null; 
      mapModel.top = jSONObject.optInt("top", 0);
      mapModel.hasTop = jSONObject.has("top");
      mapModel.left = jSONObject.optInt("left", 0);
      mapModel.hasLeft = jSONObject.has("left");
      mapModel.width = jSONObject.optDouble("width", 0.0D);
      mapModel.hasWidth = jSONObject.has("width");
      mapModel.height = jSONObject.optDouble("height", 0.0D);
      mapModel.hasHeight = jSONObject.has("height");
      String str = jSONObject.optString("latitude", null);
      param1String = jSONObject.optString("longitude", null);
      if (str == null || param1String == null) {
        mapModel.latitude = 39.907957D;
        mapModel.longitude = 116.397493D;
      } else {
        try {
          mapModel.latitude = Double.parseDouble(str);
          if (!MapUtil.isValidLat(mapModel.latitude))
            throw new MapParamParseException("invalid latitude"); 
        } catch (NumberFormatException numberFormatException) {
          mapModel.latitude = 0.0D;
        } 
        try {
          d = Double.parseDouble(param1String);
          if (MapUtil.isValidLng(d)) {
            mapModel.longitude = d;
          } else {
            throw new MapParamParseException("invalid longitude");
          } 
        } catch (NumberFormatException numberFormatException) {
          mapModel.longitude = 0.0D;
        } 
      } 
      mapModel.hasLatitude = jSONObject.has("latitude");
      mapModel.hasLongitude = jSONObject.has("longitude");
      Object object = jSONObject.opt("scale");
      if (object == null) {
        d = 16.0D;
      } else {
        Object object1;
        if (object instanceof Integer) {
          object = Double.valueOf(((Integer)object).intValue());
        } else if (object instanceof Double) {
          object = object;
        } else {
          throw new MapParamParseException("invalid scale");
        } 
        if (object.doubleValue() < 3.0D) {
          object1 = Double.valueOf(3.0D);
        } else {
          object1 = object;
          if (object.doubleValue() > 19.0D)
            object1 = Double.valueOf(19.0D); 
        } 
        d = object1.doubleValue();
      } 
      mapModel.scale = d;
      mapModel.hasScale = jSONObject.has("scale");
      mapModel.markers = jSONObject.optJSONArray("markers");
      mapModel.hasMarkers = jSONObject.has("markers");
      mapModel.circles = jSONObject.optJSONArray("circles");
      mapModel.hasCircles = jSONObject.has("circles");
      mapModel.showLocation = jSONObject.optBoolean("showLocation", false);
      mapModel.hasShowLocation = jSONObject.has("showLocation");
      return mapModel;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\map\Map.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */