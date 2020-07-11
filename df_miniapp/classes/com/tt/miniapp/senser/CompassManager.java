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

public class CompassManager implements SensorEventListener {
  private static int UPDATE_INTERVAL = 200;
  
  public static int modeUse;
  
  static CompassManager sInstance;
  
  private float[] accelerometerValues = new float[3];
  
  boolean enable;
  
  private int interval = UPDATE_INTERVAL;
  
  private long last_update_time = -1L;
  
  private Context mContext;
  
  public volatile boolean mCurrentOpen;
  
  private SensorManager mSensorManager;
  
  public boolean mStopListenWhenBackground;
  
  private float[] magneticFieldValues = new float[3];
  
  private CompassManager(Context paramContext) {
    if (paramContext != null)
      this.mContext = paramContext.getApplicationContext(); 
    AppbrandApplicationImpl.getInst().getForeBackgroundManager().registerForeBackgroundListener((ForeBackgroundManager.ForeBackgroundListener)new ForeBackgroundManager.DefaultForeBackgroundListener() {
          public void onBackground() {
            if (!CompassManager.this.mStopListenWhenBackground)
              synchronized (CompassManager.this) {
                if (CompassManager.this.mCurrentOpen) {
                  CompassManager.this.stopListen();
                  CompassManager.this.mStopListenWhenBackground = true;
                } 
                return;
              }  
          }
          
          public void onForeground() {
            if (CompassManager.this.mStopListenWhenBackground)
              synchronized (CompassManager.this) {
                if (CompassManager.this.mCurrentOpen)
                  CompassManager.this.startListen(); 
                CompassManager.this.mStopListenWhenBackground = false;
                return;
              }  
          }
        });
  }
  
  private void calculateOrientation() {
    float[] arrayOfFloat1 = new float[3];
    float[] arrayOfFloat2 = new float[9];
    SensorManager.getRotationMatrix(arrayOfFloat2, null, this.accelerometerValues, this.magneticFieldValues);
    SensorManager.getOrientation(arrayOfFloat2, arrayOfFloat1);
    float f2 = (float)Math.toDegrees(arrayOfFloat1[0]);
    if (System.currentTimeMillis() - this.last_update_time < this.interval)
      return; 
    this.last_update_time = System.currentTimeMillis();
    float f1 = f2;
    if (f2 < 0.0F)
      f1 = f2 + 360.0F; 
    try {
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("direction", f1);
      AppbrandApplication.getInst().getJsBridge().sendMsgToJsCore("onCompassChange", jSONObject.toString());
      return;
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "CompassManager", exception.getStackTrace());
      return;
    } 
  }
  
  private static boolean com_tt_miniapp_senser_CompassManager_android_hardware_SensorManager_registerListener(SensorManager paramSensorManager, SensorEventListener paramSensorEventListener, Sensor paramSensor, int paramInt) {
    boolean bool = paramSensorManager.registerListener(paramSensorEventListener, paramSensor, paramInt);
    b.a(Boolean.valueOf(bool), paramSensorManager, new Object[] { paramSensorEventListener, paramSensor, Integer.valueOf(paramInt) }, false, 100700, "android.hardware.SensorManager.registerListener(android.hardware.SensorEventListener,android.hardware.Sensor,int)");
    return bool;
  }
  
  private static void com_tt_miniapp_senser_CompassManager_android_hardware_SensorManager_unregisterListener(SensorManager paramSensorManager, SensorEventListener paramSensorEventListener) {
    paramSensorManager.unregisterListener(paramSensorEventListener);
    b.a(null, paramSensorManager, new Object[] { paramSensorEventListener }, false, 100701, "android.hardware.SensorManager.unregisterListener(android.hardware.SensorEventListener)");
  }
  
  public static CompassManager getInst(Context paramContext) {
    // Byte code:
    //   0: getstatic com/tt/miniapp/senser/CompassManager.sInstance : Lcom/tt/miniapp/senser/CompassManager;
    //   3: ifnonnull -> 38
    //   6: ldc com/tt/miniapp/senser/AccelermeterManager
    //   8: monitorenter
    //   9: getstatic com/tt/miniapp/senser/CompassManager.sInstance : Lcom/tt/miniapp/senser/CompassManager;
    //   12: ifnonnull -> 26
    //   15: new com/tt/miniapp/senser/CompassManager
    //   18: dup
    //   19: aload_0
    //   20: invokespecial <init> : (Landroid/content/Context;)V
    //   23: putstatic com/tt/miniapp/senser/CompassManager.sInstance : Lcom/tt/miniapp/senser/CompassManager;
    //   26: ldc com/tt/miniapp/senser/AccelermeterManager
    //   28: monitorexit
    //   29: goto -> 38
    //   32: astore_0
    //   33: ldc com/tt/miniapp/senser/AccelermeterManager
    //   35: monitorexit
    //   36: aload_0
    //   37: athrow
    //   38: getstatic com/tt/miniapp/senser/CompassManager.sInstance : Lcom/tt/miniapp/senser/CompassManager;
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
    int i = modeUse;
    if (i == 1) {
      if (paramSensorEvent.sensor.getType() == 2)
        this.magneticFieldValues = paramSensorEvent.values; 
      if (paramSensorEvent.sensor.getType() == 1)
        this.accelerometerValues = paramSensorEvent.values; 
      calculateOrientation();
      return;
    } 
    if (i == 0 && paramSensorEvent.sensor.getType() == 3) {
      float f = paramSensorEvent.values[0];
      if (System.currentTimeMillis() - this.last_update_time < this.interval)
        return; 
      this.last_update_time = System.currentTimeMillis();
      try {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("direction", f);
        AppbrandApplication.getInst().getJsBridge().sendMsgToJsCore("onCompassChange", jSONObject.toString());
        return;
      } catch (Exception exception) {
        AppBrandLogger.stacktrace(6, "CompassManager", exception.getStackTrace());
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
    int i = modeUse;
    if (i == 0) {
      Sensor sensor = this.mSensorManager.getDefaultSensor(3);
      if (sensor != null)
        return com_tt_miniapp_senser_CompassManager_android_hardware_SensorManager_registerListener(this.mSensorManager, this, sensor, 1); 
    } else if (i == 1) {
      Sensor sensor1 = this.mSensorManager.getDefaultSensor(2);
      Sensor sensor2 = this.mSensorManager.getDefaultSensor(1);
      return !com_tt_miniapp_senser_CompassManager_android_hardware_SensorManager_registerListener(this.mSensorManager, this, sensor1, 3) ? (com_tt_miniapp_senser_CompassManager_android_hardware_SensorManager_registerListener(this.mSensorManager, this, sensor2, 3)) : true;
    } 
    return false;
  }
  
  public void stopListen() {
    com_tt_miniapp_senser_CompassManager_android_hardware_SensorManager_unregisterListener(this.mSensorManager, this);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\senser\CompassManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */