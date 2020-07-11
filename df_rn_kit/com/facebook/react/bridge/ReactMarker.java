package com.facebook.react.bridge;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReactMarker {
  private static final List<MarkerListener> sListeners = new ArrayList<MarkerListener>();
  
  public static void addListener(MarkerListener paramMarkerListener) {
    synchronized (sListeners) {
      if (!sListeners.contains(paramMarkerListener))
        sListeners.add(paramMarkerListener); 
      return;
    } 
  }
  
  public static void clearMarkerListeners() {
    synchronized (sListeners) {
      sListeners.clear();
      return;
    } 
  }
  
  public static void logMarker(ReactMarkerConstants paramReactMarkerConstants) {
    logMarker(paramReactMarkerConstants, (String)null, 0);
  }
  
  public static void logMarker(ReactMarkerConstants paramReactMarkerConstants, int paramInt) {
    logMarker(paramReactMarkerConstants, (String)null, paramInt);
  }
  
  public static void logMarker(ReactMarkerConstants paramReactMarkerConstants, String paramString) {
    logMarker(paramReactMarkerConstants, paramString, 0);
  }
  
  public static void logMarker(ReactMarkerConstants paramReactMarkerConstants, String paramString, int paramInt) {
    synchronized (sListeners) {
      Iterator<MarkerListener> iterator = sListeners.iterator();
      while (iterator.hasNext())
        ((MarkerListener)iterator.next()).logMarker(paramReactMarkerConstants, paramString, paramInt); 
      return;
    } 
  }
  
  public static void logMarker(String paramString) {
    logMarker(paramString, (String)null);
  }
  
  public static void logMarker(String paramString, int paramInt) {
    logMarker(paramString, (String)null, paramInt);
  }
  
  public static void logMarker(String paramString1, String paramString2) {
    logMarker(paramString1, paramString2, 0);
  }
  
  public static void logMarker(String paramString1, String paramString2, int paramInt) {
    logMarker(ReactMarkerConstants.valueOf(paramString1), paramString2, paramInt);
  }
  
  public static void removeListener(MarkerListener paramMarkerListener) {
    synchronized (sListeners) {
      sListeners.remove(paramMarkerListener);
      return;
    } 
  }
  
  public static interface MarkerListener {
    void logMarker(ReactMarkerConstants param1ReactMarkerConstants, String param1String, int param1Int);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\ReactMarker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */