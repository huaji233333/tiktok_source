package com.tt.miniapp.senser;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import com.bytedance.v.a.a.a.b;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.manager.ForeBackgroundManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import org.json.JSONObject;

public class AccelermeterManager implements SensorEventListener {
  private static float G = 9.8F;
  
  private static int UPDATE_INTERVAL = 200;
  
  static AccelermeterManager sInstance;
  
  boolean enable;
  
  private int interval = UPDATE_INTERVAL;
  
  private long last_update_time = -1L;
  
  private Context mContext;
  
  public volatile boolean mCurrentOpen;
  
  private SensorManager mSensorManager;
  
  public volatile boolean mStopListenWhenBackground;
  
  private AccelermeterManager(Context paramContext) {
    if (paramContext != null)
      this.mContext = paramContext.getApplicationContext(); 
    AppbrandApplicationImpl.getInst().getForeBackgroundManager().registerForeBackgroundListener((ForeBackgroundManager.ForeBackgroundListener)new ForeBackgroundManager.DefaultForeBackgroundListener() {
          public void onBackground() {
            if (!AccelermeterManager.this.mStopListenWhenBackground)
              synchronized (AccelermeterManager.this) {
                if (AccelermeterManager.this.mCurrentOpen) {
                  AccelermeterManager.this.stopListen();
                  AccelermeterManager.this.mStopListenWhenBackground = true;
                } 
                return;
              }  
          }
          
          public void onForeground() {
            if (AccelermeterManager.this.mStopListenWhenBackground)
              synchronized (AccelermeterManager.this) {
                if (AccelermeterManager.this.mCurrentOpen)
                  AccelermeterManager.this.startListen(); 
                AccelermeterManager.this.mStopListenWhenBackground = false;
                return;
              }  
          }
        });
  }
  
  private static boolean com_tt_miniapp_senser_AccelermeterManager_android_hardware_SensorManager_registerListener(SensorManager paramSensorManager, SensorEventListener paramSensorEventListener, Sensor paramSensor, int paramInt) {
    boolean bool = paramSensorManager.registerListener(paramSensorEventListener, paramSensor, paramInt);
    b.a(Boolean.valueOf(bool), paramSensorManager, new Object[] { paramSensorEventListener, paramSensor, Integer.valueOf(paramInt) }, false, 100700, "android.hardware.SensorManager.registerListener(android.hardware.SensorEventListener,android.hardware.Sensor,int)");
    return bool;
  }
  
  private static void com_tt_miniapp_senser_AccelermeterManager_android_hardware_SensorManager_unregisterListener(SensorManager paramSensorManager, SensorEventListener paramSensorEventListener) {
    paramSensorManager.unregisterListener(paramSensorEventListener);
    b.a(null, paramSensorManager, new Object[] { paramSensorEventListener }, false, 100701, "android.hardware.SensorManager.unregisterListener(android.hardware.SensorEventListener)");
  }
  
  public static AccelermeterManager getInst(Context paramContext) {
    // Byte code:
    //   0: getstatic com/tt/miniapp/senser/AccelermeterManager.sInstance : Lcom/tt/miniapp/senser/AccelermeterManager;
    //   3: ifnonnull -> 38
    //   6: ldc com/tt/miniapp/senser/AccelermeterManager
    //   8: monitorenter
    //   9: getstatic com/tt/miniapp/senser/AccelermeterManager.sInstance : Lcom/tt/miniapp/senser/AccelermeterManager;
    //   12: ifnonnull -> 26
    //   15: new com/tt/miniapp/senser/AccelermeterManager
    //   18: dup
    //   19: aload_0
    //   20: invokespecial <init> : (Landroid/content/Context;)V
    //   23: putstatic com/tt/miniapp/senser/AccelermeterManager.sInstance : Lcom/tt/miniapp/senser/AccelermeterManager;
    //   26: ldc com/tt/miniapp/senser/AccelermeterManager
    //   28: monitorexit
    //   29: goto -> 38
    //   32: astore_0
    //   33: ldc com/tt/miniapp/senser/AccelermeterManager
    //   35: monitorexit
    //   36: aload_0
    //   37: athrow
    //   38: getstatic com/tt/miniapp/senser/AccelermeterManager.sInstance : Lcom/tt/miniapp/senser/AccelermeterManager;
    //   41: areturn
    // Exception table:
    //   from	to	target	type
    //   9	26	32	finally
    //   26	29	32	finally
    //   33	36	32	finally
  }
  
  public boolean close() {
    // Byte code:
    //   0: aload_0
    //   1: iconst_0
    //   2: putfield enable : Z
    //   5: aload_0
    //   6: monitorenter
    //   7: aload_0
    //   8: getfield mCurrentOpen : Z
    //   11: ifeq -> 23
    //   14: aload_0
    //   15: invokevirtual stopListen : ()V
    //   18: aload_0
    //   19: iconst_0
    //   20: putfield mCurrentOpen : Z
    //   23: aload_0
    //   24: monitorexit
    //   25: iconst_1
    //   26: ireturn
    //   27: astore_1
    //   28: aload_0
    //   29: monitorexit
    //   30: aload_1
    //   31: athrow
    // Exception table:
    //   from	to	target	type
    //   7	23	27	finally
    //   23	25	27	finally
    //   28	30	27	finally
  }
  
  public boolean enable() {
    return this.enable;
  }
  
  public void onAccuracyChanged(Sensor paramSensor, int paramInt) {}
  
  public void onSensorChanged(SensorEvent paramSensorEvent) {
    if (!this.enable)
      return; 
    if (paramSensorEvent.sensor.getType() == 1) {
      float f1;
      float f2 = -paramSensorEvent.values[0] / G;
      float f3 = -paramSensorEvent.values[1] / G;
      float f4 = -paramSensorEvent.values[2] / G;
      if (f2 > 1.0F) {
        f1 = 1.0F;
      } else {
        f1 = f2;
        if (f2 < -1.0F)
          f1 = -1.0F; 
      } 
      if (f3 > 1.0F) {
        f2 = 1.0F;
      } else {
        f2 = f3;
        if (f3 < -1.0F)
          f2 = -1.0F; 
      } 
      if (f4 > 1.0F) {
        f3 = 1.0F;
      } else {
        f3 = f4;
        if (f4 < -1.0F)
          f3 = -1.0F; 
      } 
      if (System.currentTimeMillis() - this.last_update_time < this.interval)
        return; 
      this.last_update_time = System.currentTimeMillis();
      try {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("x", f1);
        jSONObject.put("y", f2);
        jSONObject.put("z", f3);
        AppbrandApplication.getInst().getJsBridge().sendMsgToJsCore("onAccelerometerChange", jSONObject.toString());
        return;
      } catch (Exception exception) {
        AppBrandLogger.stacktrace(6, "tma_AccelermeterManager", exception.getStackTrace());
      } 
    } 
  }
  
  public boolean open() {
    // Byte code:
    //   0: aload_0
    //   1: iconst_1
    //   2: putfield enable : Z
    //   5: aload_0
    //   6: getfield mCurrentOpen : Z
    //   9: ifeq -> 14
    //   12: iconst_1
    //   13: ireturn
    //   14: aload_0
    //   15: monitorenter
    //   16: aload_0
    //   17: aload_0
    //   18: invokevirtual startListen : ()Z
    //   21: putfield mCurrentOpen : Z
    //   24: aload_0
    //   25: monitorexit
    //   26: aload_0
    //   27: getfield mCurrentOpen : Z
    //   30: ireturn
    //   31: astore_1
    //   32: aload_0
    //   33: monitorexit
    //   34: aload_1
    //   35: athrow
    // Exception table:
    //   from	to	target	type
    //   16	26	31	finally
    //   32	34	31	finally
  }
  
  public void setInterval(int paramInt) {
    this.interval = paramInt;
  }
  
  public boolean startListen() {
    if (AppbrandApplicationImpl.getInst().getForeBackgroundManager().isBackground()) {
      this.mStopListenWhenBackground = true;
      return true;
    } 
    this.mSensorManager = (SensorManager)this.mContext.getSystemService("sensor");
    Sensor sensor = this.mSensorManager.getDefaultSensor(1);
    return (sensor != null) ? com_tt_miniapp_senser_AccelermeterManager_android_hardware_SensorManager_registerListener(this.mSensorManager, this, sensor, 1) : false;
  }
  
  public void stopListen() {
    com_tt_miniapp_senser_AccelermeterManager_android_hardware_SensorManager_unregisterListener(this.mSensorManager, this);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\senser\AccelermeterManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */