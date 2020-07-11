package com.facebook.jni;

public class CpuCapabilitiesJni {
  public static native boolean nativeDeviceSupportsNeon();
  
  public static native boolean nativeDeviceSupportsVFPFP16();
  
  public static native boolean nativeDeviceSupportsX86();
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\jni\CpuCapabilitiesJni.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */