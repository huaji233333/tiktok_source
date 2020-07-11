package com.facebook.react.bridge;

import com.facebook.common.e.a;
import com.facebook.jni.HybridData;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Inspector {
  private final HybridData mHybridData;
  
  private Inspector(HybridData paramHybridData) {
    this.mHybridData = paramHybridData;
  }
  
  public static LocalConnection connect(int paramInt, RemoteConnection paramRemoteConnection) {
    try {
      return instance().connectNative(paramInt, paramRemoteConnection);
    } catch (UnsatisfiedLinkError unsatisfiedLinkError) {
      a.c("ReactNative", "Inspector doesn't work in open source yet", unsatisfiedLinkError);
      throw new RuntimeException(unsatisfiedLinkError);
    } 
  }
  
  private native LocalConnection connectNative(int paramInt, RemoteConnection paramRemoteConnection);
  
  public static List<Page> getPages() {
    try {
      return Arrays.asList(instance().getPagesNative());
    } catch (UnsatisfiedLinkError unsatisfiedLinkError) {
      a.c("ReactNative", "Inspector doesn't work in open source yet", unsatisfiedLinkError);
      return Collections.emptyList();
    } 
  }
  
  private native Page[] getPagesNative();
  
  private static native Inspector instance();
  
  public static class LocalConnection {
    private final HybridData mHybridData;
    
    private LocalConnection(HybridData param1HybridData) {
      this.mHybridData = param1HybridData;
    }
    
    public native void disconnect();
    
    public native void sendMessage(String param1String);
  }
  
  public static class Page {
    private final int mId;
    
    private final String mTitle;
    
    private final String mVM;
    
    private Page(int param1Int, String param1String1, String param1String2) {
      this.mId = param1Int;
      this.mTitle = param1String1;
      this.mVM = param1String2;
    }
    
    public int getId() {
      return this.mId;
    }
    
    public String getTitle() {
      return this.mTitle;
    }
    
    public String getVM() {
      return this.mVM;
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder("Page{mId=");
      stringBuilder.append(this.mId);
      stringBuilder.append(", mTitle='");
      stringBuilder.append(this.mTitle);
      stringBuilder.append('\'');
      stringBuilder.append('}');
      return stringBuilder.toString();
    }
  }
  
  public static interface RemoteConnection {
    void onDisconnect();
    
    void onMessage(String param1String);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\Inspector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */