package com.facebook.react.uimanager.util;

import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReactFindViewUtil {
  private static final Map<OnMultipleViewsFoundListener, Set<String>> mOnMultipleViewsFoundListener;
  
  private static final List<OnViewFoundListener> mOnViewFoundListeners = new ArrayList<OnViewFoundListener>();
  
  static {
    mOnMultipleViewsFoundListener = new HashMap<OnMultipleViewsFoundListener, Set<String>>();
  }
  
  public static void addViewListener(OnViewFoundListener paramOnViewFoundListener) {
    mOnViewFoundListeners.add(paramOnViewFoundListener);
  }
  
  public static void addViewsListener(OnMultipleViewsFoundListener paramOnMultipleViewsFoundListener, Set<String> paramSet) {
    mOnMultipleViewsFoundListener.put(paramOnMultipleViewsFoundListener, paramSet);
  }
  
  public static View findView(View paramView, String paramString) {
    String str = getNativeId(paramView);
    if (str != null && str.equals(paramString))
      return paramView; 
    if (paramView instanceof ViewGroup) {
      ViewGroup viewGroup = (ViewGroup)paramView;
      for (int i = 0; i < viewGroup.getChildCount(); i++) {
        View view = findView(viewGroup.getChildAt(i), paramString);
        if (view != null)
          return view; 
      } 
    } 
    return null;
  }
  
  public static void findView(View paramView, OnViewFoundListener paramOnViewFoundListener) {
    paramView = findView(paramView, paramOnViewFoundListener.getNativeId());
    if (paramView != null)
      paramOnViewFoundListener.onViewFound(paramView); 
    addViewListener(paramOnViewFoundListener);
  }
  
  private static String getNativeId(View paramView) {
    Object object = paramView.getTag(1979973671);
    return (object instanceof String) ? (String)object : null;
  }
  
  public static void notifyViewRendered(View paramView) {
    String str = getNativeId(paramView);
    if (str == null)
      return; 
    Iterator<OnViewFoundListener> iterator = mOnViewFoundListeners.iterator();
    while (iterator.hasNext()) {
      OnViewFoundListener onViewFoundListener = iterator.next();
      if (str != null && str.equals(onViewFoundListener.getNativeId())) {
        onViewFoundListener.onViewFound(paramView);
        iterator.remove();
      } 
    } 
    iterator = mOnMultipleViewsFoundListener.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry entry = (Map.Entry)iterator.next();
      Set set = (Set)entry.getValue();
      if (set.contains(str)) {
        ((OnMultipleViewsFoundListener)entry.getKey()).onViewFound(paramView, str);
        set.remove(str);
      } 
      if (set.isEmpty())
        iterator.remove(); 
    } 
  }
  
  public static void removeViewListener(OnViewFoundListener paramOnViewFoundListener) {
    mOnViewFoundListeners.remove(paramOnViewFoundListener);
  }
  
  public static void removeViewsListener(OnMultipleViewsFoundListener paramOnMultipleViewsFoundListener) {
    mOnMultipleViewsFoundListener.remove(paramOnMultipleViewsFoundListener);
  }
  
  public static interface OnMultipleViewsFoundListener {
    void onViewFound(View param1View, String param1String);
  }
  
  public static interface OnViewFoundListener {
    String getNativeId();
    
    void onViewFound(View param1View);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanage\\util\ReactFindViewUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */