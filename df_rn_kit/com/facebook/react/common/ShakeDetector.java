package com.facebook.react.common;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import com.bytedance.v.a.a.a.b;
import com.facebook.i.a.a;
import java.util.concurrent.TimeUnit;

public class ShakeDetector implements SensorEventListener {
  private static final long MIN_TIME_BETWEEN_SAMPLES_NS = TimeUnit.NANOSECONDS.convert(20L, TimeUnit.MILLISECONDS);
  
  private static final float SHAKING_WINDOW_NS = (float)TimeUnit.NANOSECONDS.convert(3L, TimeUnit.SECONDS);
  
  private float mAccelerationX;
  
  private float mAccelerationY;
  
  private float mAccelerationZ;
  
  private long mLastShakeTimestamp;
  
  private long mLastTimestamp;
  
  private int mMinNumShakes;
  
  private int mNumShakes;
  
  private SensorManager mSensorManager;
  
  private final ShakeListener mShakeListener;
  
  public ShakeDetector(ShakeListener paramShakeListener) {
    this(paramShakeListener, 1);
  }
  
  public ShakeDetector(ShakeListener paramShakeListener, int paramInt) {
    this.mShakeListener = paramShakeListener;
    this.mMinNumShakes = paramInt;
  }
  
  private boolean atLeastRequiredForce(float paramFloat) {
    return (Math.abs(paramFloat) > 13.042845F);
  }
  
  private static boolean com_facebook_react_common_ShakeDetector_android_hardware_SensorManager_registerListener(SensorManager paramSensorManager, SensorEventListener paramSensorEventListener, Sensor paramSensor, int paramInt) {
    boolean bool = paramSensorManager.registerListener(paramSensorEventListener, paramSensor, paramInt);
    b.a(Boolean.valueOf(bool), paramSensorManager, new Object[] { paramSensorEventListener, paramSensor, Integer.valueOf(paramInt) }, false, 100700, "android.hardware.SensorManager.registerListener(android.hardware.SensorEventListener,android.hardware.Sensor,int)");
    return bool;
  }
  
  private static void com_facebook_react_common_ShakeDetector_android_hardware_SensorManager_unregisterListener(SensorManager paramSensorManager, SensorEventListener paramSensorEventListener) {
    paramSensorManager.unregisterListener(paramSensorEventListener);
    b.a(null, paramSensorManager, new Object[] { paramSensorEventListener }, false, 100701, "android.hardware.SensorManager.unregisterListener(android.hardware.SensorEventListener)");
  }
  
  private void maybeDispatchShake(long paramLong) {
    if (this.mNumShakes >= this.mMinNumShakes * 8) {
      reset();
      this.mShakeListener.onShake();
    } 
    if ((float)(paramLong - this.mLastShakeTimestamp) > SHAKING_WINDOW_NS)
      reset(); 
  }
  
  private void recordShake(long paramLong) {
    this.mLastShakeTimestamp = paramLong;
    this.mNumShakes++;
  }
  
  private void reset() {
    this.mNumShakes = 0;
    this.mAccelerationX = 0.0F;
    this.mAccelerationY = 0.0F;
    this.mAccelerationZ = 0.0F;
  }
  
  public void onAccuracyChanged(Sensor paramSensor, int paramInt) {}
  
  public void onSensorChanged(SensorEvent paramSensorEvent) {
    if (paramSensorEvent.timestamp - this.mLastTimestamp < MIN_TIME_BETWEEN_SAMPLES_NS)
      return; 
    float f1 = paramSensorEvent.values[0];
    float f2 = paramSensorEvent.values[1];
    float f3 = paramSensorEvent.values[2] - 9.80665F;
    this.mLastTimestamp = paramSensorEvent.timestamp;
    if (atLeastRequiredForce(f1) && this.mAccelerationX * f1 <= 0.0F) {
      recordShake(paramSensorEvent.timestamp);
      this.mAccelerationX = f1;
    } else if (atLeastRequiredForce(f2) && this.mAccelerationY * f2 <= 0.0F) {
      recordShake(paramSensorEvent.timestamp);
      this.mAccelerationY = f2;
    } else if (atLeastRequiredForce(f3) && this.mAccelerationZ * f3 <= 0.0F) {
      recordShake(paramSensorEvent.timestamp);
      this.mAccelerationZ = f3;
    } 
    maybeDispatchShake(paramSensorEvent.timestamp);
  }
  
  public void start(SensorManager paramSensorManager) {
    a.b(paramSensorManager);
    Sensor sensor = paramSensorManager.getDefaultSensor(1);
    if (sensor != null) {
      this.mSensorManager = paramSensorManager;
      this.mLastTimestamp = -1L;
      com_facebook_react_common_ShakeDetector_android_hardware_SensorManager_registerListener(this.mSensorManager, this, sensor, 2);
      this.mLastShakeTimestamp = 0L;
      reset();
    } 
  }
  
  public void stop() {
    SensorManager sensorManager = this.mSensorManager;
    if (sensorManager != null) {
      com_facebook_react_common_ShakeDetector_android_hardware_SensorManager_unregisterListener(sensorManager, this);
      this.mSensorManager = null;
    } 
  }
  
  public static interface ShakeListener {
    void onShake();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\common\ShakeDetector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */