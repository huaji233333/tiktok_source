package com.facebook.react.modules.location;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import com.bytedance.v.a.a.a.b;
import com.facebook.common.e.a;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.SystemClock;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.DeviceEventManagerModule;

@ReactModule(name = "LocationObserver")
public class LocationModule extends ReactContextBaseJavaModule {
  private final LocationListener mLocationListener = new LocationListener() {
      public void onLocationChanged(Location param1Location) {
        ((DeviceEventManagerModule.RCTDeviceEventEmitter)LocationModule.this.getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit("geolocationDidChange", LocationModule.locationToMap(param1Location));
      }
      
      public void onProviderDisabled(String param1String) {}
      
      public void onProviderEnabled(String param1String) {}
      
      public void onStatusChanged(String param1String, int param1Int, Bundle param1Bundle) {
        if (param1Int == 0) {
          LocationModule locationModule = LocationModule.this;
          param1Int = PositionError.POSITION_UNAVAILABLE;
          StringBuilder stringBuilder = new StringBuilder("Provider ");
          stringBuilder.append(param1String);
          stringBuilder.append(" is out of service.");
          locationModule.emitError(param1Int, stringBuilder.toString());
          return;
        } 
        if (param1Int == 1) {
          LocationModule locationModule = LocationModule.this;
          param1Int = PositionError.TIMEOUT;
          StringBuilder stringBuilder = new StringBuilder("Provider ");
          stringBuilder.append(param1String);
          stringBuilder.append(" is temporarily unavailable.");
          locationModule.emitError(param1Int, stringBuilder.toString());
        } 
      }
    };
  
  private String mWatchedProvider;
  
  public LocationModule(ReactApplicationContext paramReactApplicationContext) {
    super(paramReactApplicationContext);
  }
  
  private static Location com_facebook_react_modules_location_LocationModule_android_location_LocationManager_getLastKnownLocation(LocationManager paramLocationManager, String paramString) {
    Location location = paramLocationManager.getLastKnownLocation(paramString);
    b.a(location, paramLocationManager, new Object[] { paramString }, false, 100000, "android.location.LocationManager.getLastKnownLocation(java.lang.String)");
    return location;
  }
  
  private static void com_facebook_react_modules_location_LocationModule_android_location_LocationManager_requestLocationUpdates(LocationManager paramLocationManager, String paramString, long paramLong, float paramFloat, LocationListener paramLocationListener) {
    paramLocationManager.requestLocationUpdates(paramString, paramLong, paramFloat, paramLocationListener);
    b.a(null, paramLocationManager, new Object[] { paramString, Long.valueOf(paramLong), Float.valueOf(paramFloat), paramLocationListener }, false, 100001, "android.location.LocationManager.requestLocationUpdates(java.lang.String,long,float,android.location.LocationListener)");
  }
  
  private static String getValidProvider(LocationManager paramLocationManager, boolean paramBoolean) {
    String str1;
    if (paramBoolean) {
      str1 = "gps";
    } else {
      str1 = "network";
    } 
    String str2 = str1;
    if (!paramLocationManager.isProviderEnabled(str1)) {
      if (str1.equals("gps")) {
        str1 = "network";
      } else {
        str1 = "gps";
      } 
      str2 = str1;
      if (!paramLocationManager.isProviderEnabled(str1))
        return null; 
    } 
    return str2;
  }
  
  public static WritableMap locationToMap(Location paramLocation) {
    WritableMap writableMap1 = Arguments.createMap();
    WritableMap writableMap2 = Arguments.createMap();
    writableMap2.putDouble("latitude", paramLocation.getLatitude());
    writableMap2.putDouble("longitude", paramLocation.getLongitude());
    writableMap2.putDouble("altitude", paramLocation.getAltitude());
    writableMap2.putDouble("accuracy", paramLocation.getAccuracy());
    writableMap2.putDouble("heading", paramLocation.getBearing());
    writableMap2.putDouble("speed", paramLocation.getSpeed());
    writableMap1.putMap("coords", writableMap2);
    writableMap1.putDouble("timestamp", paramLocation.getTime());
    if (Build.VERSION.SDK_INT >= 18)
      writableMap1.putBoolean("mocked", paramLocation.isFromMockProvider()); 
    return writableMap1;
  }
  
  private static void throwLocationPermissionMissing(SecurityException paramSecurityException) {
    throw new SecurityException("Looks like the app doesn't have the permission to access location.\nAdd the following line to your app's AndroidManifest.xml:\n<uses-permission android:name=\"android.permission.ACCESS_FINE_LOCATION\" />", paramSecurityException);
  }
  
  public void emitError(int paramInt, String paramString) {
    ((DeviceEventManagerModule.RCTDeviceEventEmitter)getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit("geolocationError", PositionError.buildError(paramInt, paramString));
  }
  
  @ReactMethod
  public void getCurrentPosition(ReadableMap paramReadableMap, Callback paramCallback1, Callback paramCallback2) {
    LocationOptions locationOptions = LocationOptions.fromReactMap(paramReadableMap);
    try {
      LocationManager locationManager = (LocationManager)getReactApplicationContext().getSystemService("location");
      String str = getValidProvider(locationManager, locationOptions.highAccuracy);
      if (str == null) {
        paramCallback2.invoke(new Object[] { PositionError.buildError(PositionError.POSITION_UNAVAILABLE, "No location provider available.") });
        return;
      } 
      Location location = com_facebook_react_modules_location_LocationModule_android_location_LocationManager_getLastKnownLocation(locationManager, str);
      if (location != null && (SystemClock.currentTimeMillis() - location.getTime()) < locationOptions.maximumAge) {
        paramCallback1.invoke(new Object[] { locationToMap(location) });
        return;
      } 
      (new SingleUpdateRequest(locationManager, str, locationOptions.timeout, paramCallback1, paramCallback2)).invoke(location);
      return;
    } catch (SecurityException securityException) {
      throwLocationPermissionMissing(securityException);
      return;
    } 
  }
  
  public String getName() {
    return "LocationObserver";
  }
  
  @ReactMethod
  public void startObserving(ReadableMap paramReadableMap) {
    if ("gps".equals(this.mWatchedProvider))
      return; 
    LocationOptions locationOptions = LocationOptions.fromReactMap(paramReadableMap);
    try {
      LocationManager locationManager = (LocationManager)getReactApplicationContext().getSystemService("location");
      String str = getValidProvider(locationManager, locationOptions.highAccuracy);
      if (str == null) {
        emitError(PositionError.POSITION_UNAVAILABLE, "No location provider available.");
        return;
      } 
      if (!str.equals(this.mWatchedProvider)) {
        locationManager.removeUpdates(this.mLocationListener);
        com_facebook_react_modules_location_LocationModule_android_location_LocationManager_requestLocationUpdates(locationManager, str, 1000L, locationOptions.distanceFilter, this.mLocationListener);
      } 
      this.mWatchedProvider = str;
      return;
    } catch (SecurityException securityException) {
      throwLocationPermissionMissing(securityException);
      return;
    } 
  }
  
  @ReactMethod
  public void stopObserving() {
    ((LocationManager)getReactApplicationContext().getSystemService("location")).removeUpdates(this.mLocationListener);
    this.mWatchedProvider = null;
  }
  
  static class LocationOptions {
    public final float distanceFilter;
    
    public final boolean highAccuracy;
    
    public final double maximumAge;
    
    public final long timeout;
    
    private LocationOptions(long param1Long, double param1Double, boolean param1Boolean, float param1Float) {
      this.timeout = param1Long;
      this.maximumAge = param1Double;
      this.highAccuracy = param1Boolean;
      this.distanceFilter = param1Float;
    }
    
    public static LocationOptions fromReactMap(ReadableMap param1ReadableMap) {
      double d;
      float f;
      long l;
      boolean bool;
      if (param1ReadableMap.hasKey("timeout")) {
        l = (long)param1ReadableMap.getDouble("timeout");
      } else {
        l = Long.MAX_VALUE;
      } 
      if (param1ReadableMap.hasKey("maximumAge")) {
        d = param1ReadableMap.getDouble("maximumAge");
      } else {
        d = Double.POSITIVE_INFINITY;
      } 
      if (param1ReadableMap.hasKey("enableHighAccuracy") && param1ReadableMap.getBoolean("enableHighAccuracy")) {
        bool = true;
      } else {
        bool = false;
      } 
      if (param1ReadableMap.hasKey("distanceFilter")) {
        f = (float)param1ReadableMap.getDouble("distanceFilter");
      } else {
        f = 100.0F;
      } 
      return new LocationOptions(l, d, bool, f);
    }
  }
  
  static class SingleUpdateRequest {
    public final Callback mError;
    
    public final Handler mHandler = new Handler();
    
    public final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location param2Location) {
          synchronized (LocationModule.SingleUpdateRequest.this) {
            if (!LocationModule.SingleUpdateRequest.this.mTriggered && LocationModule.SingleUpdateRequest.this.isBetterLocation(param2Location, LocationModule.SingleUpdateRequest.this.mOldLocation)) {
              LocationModule.SingleUpdateRequest.this.mSuccess.invoke(new Object[] { LocationModule.locationToMap(param2Location) });
              LocationModule.SingleUpdateRequest.this.mHandler.removeCallbacks(LocationModule.SingleUpdateRequest.this.mTimeoutRunnable);
              LocationModule.SingleUpdateRequest.this.mTriggered = true;
              LocationModule.SingleUpdateRequest.this.mLocationManager.removeUpdates(LocationModule.SingleUpdateRequest.this.mLocationListener);
            } 
            LocationModule.SingleUpdateRequest.this.mOldLocation = param2Location;
            return;
          } 
        }
        
        public void onProviderDisabled(String param2String) {}
        
        public void onProviderEnabled(String param2String) {}
        
        public void onStatusChanged(String param2String, int param2Int, Bundle param2Bundle) {}
      };
    
    public final LocationManager mLocationManager;
    
    public Location mOldLocation;
    
    private final String mProvider;
    
    public final Callback mSuccess;
    
    private final long mTimeout;
    
    public final Runnable mTimeoutRunnable = new Runnable() {
        public void run() {
          synchronized (LocationModule.SingleUpdateRequest.this) {
            if (!LocationModule.SingleUpdateRequest.this.mTriggered) {
              LocationModule.SingleUpdateRequest.this.mError.invoke(new Object[] { PositionError.buildError(PositionError.TIMEOUT, "Location request timed out") });
              LocationModule.SingleUpdateRequest.this.mLocationManager.removeUpdates(LocationModule.SingleUpdateRequest.this.mLocationListener);
              if (a.a.b(4))
                a.a.c("ReactNative", "LocationModule: Location request timed out"); 
              LocationModule.SingleUpdateRequest.this.mTriggered = true;
            } 
            return;
          } 
        }
      };
    
    public boolean mTriggered;
    
    private SingleUpdateRequest(LocationManager param1LocationManager, String param1String, long param1Long, Callback param1Callback1, Callback param1Callback2) {
      this.mLocationManager = param1LocationManager;
      this.mProvider = param1String;
      this.mTimeout = param1Long;
      this.mSuccess = param1Callback1;
      this.mError = param1Callback2;
    }
    
    private static void com_facebook_react_modules_location_LocationModule$SingleUpdateRequest_android_location_LocationManager_requestLocationUpdates(LocationManager param1LocationManager, String param1String, long param1Long, float param1Float, LocationListener param1LocationListener) {
      param1LocationManager.requestLocationUpdates(param1String, param1Long, param1Float, param1LocationListener);
      b.a(null, param1LocationManager, new Object[] { param1String, Long.valueOf(param1Long), Float.valueOf(param1Float), param1LocationListener }, false, 100001, "android.location.LocationManager.requestLocationUpdates(java.lang.String,long,float,android.location.LocationListener)");
    }
    
    private boolean isSameProvider(String param1String1, String param1String2) {
      return (param1String1 == null) ? ((param1String2 == null)) : param1String1.equals(param1String2);
    }
    
    public void invoke(Location param1Location) {
      this.mOldLocation = param1Location;
      com_facebook_react_modules_location_LocationModule$SingleUpdateRequest_android_location_LocationManager_requestLocationUpdates(this.mLocationManager, this.mProvider, 100L, 1.0F, this.mLocationListener);
      this.mHandler.postDelayed(this.mTimeoutRunnable, this.mTimeout);
    }
    
    public boolean isBetterLocation(Location param1Location1, Location param1Location2) {
      boolean bool1;
      boolean bool2;
      boolean bool3;
      if (param1Location2 == null)
        return true; 
      long l = param1Location1.getTime() - param1Location2.getTime();
      if (l > 120000L) {
        bool2 = true;
      } else {
        bool2 = false;
      } 
      if (l < -120000L) {
        bool3 = true;
      } else {
        bool3 = false;
      } 
      if (l > 0L) {
        bool1 = true;
      } else {
        bool1 = false;
      } 
      if (bool2)
        return true; 
      if (bool3)
        return false; 
      int i = (int)(param1Location1.getAccuracy() - param1Location2.getAccuracy());
      if (i > 0) {
        bool2 = true;
      } else {
        bool2 = false;
      } 
      if (i < 0) {
        bool3 = true;
      } else {
        bool3 = false;
      } 
      if (i > 200) {
        i = 1;
      } else {
        i = 0;
      } 
      boolean bool = isSameProvider(param1Location1.getProvider(), param1Location2.getProvider());
      return bool3 ? true : ((bool1 && !bool2) ? true : ((bool1 && i == 0 && bool)));
    }
  }
  
  class null implements Runnable {
    public void run() {
      synchronized (this.this$0) {
        if (!this.this$0.mTriggered) {
          this.this$0.mError.invoke(new Object[] { PositionError.buildError(PositionError.TIMEOUT, "Location request timed out") });
          this.this$0.mLocationManager.removeUpdates(this.this$0.mLocationListener);
          if (a.a.b(4))
            a.a.c("ReactNative", "LocationModule: Location request timed out"); 
          this.this$0.mTriggered = true;
        } 
        return;
      } 
    }
  }
  
  class null implements LocationListener {
    public void onLocationChanged(Location param1Location) {
      synchronized (this.this$0) {
        if (!this.this$0.mTriggered && this.this$0.isBetterLocation(param1Location, this.this$0.mOldLocation)) {
          this.this$0.mSuccess.invoke(new Object[] { LocationModule.locationToMap(param1Location) });
          this.this$0.mHandler.removeCallbacks(this.this$0.mTimeoutRunnable);
          this.this$0.mTriggered = true;
          this.this$0.mLocationManager.removeUpdates(this.this$0.mLocationListener);
        } 
        this.this$0.mOldLocation = param1Location;
        return;
      } 
    }
    
    public void onProviderDisabled(String param1String) {}
    
    public void onProviderEnabled(String param1String) {}
    
    public void onStatusChanged(String param1String, int param1Int, Bundle param1Bundle) {}
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\location\LocationModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */