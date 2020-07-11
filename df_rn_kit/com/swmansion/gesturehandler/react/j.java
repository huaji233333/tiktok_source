package com.swmansion.gesturehandler.react;

import android.view.View;
import android.view.ViewGroup;
import com.facebook.react.uimanager.PointerEvents;
import com.facebook.react.uimanager.ReactPointerEventsView;
import com.facebook.react.views.view.ReactViewGroup;
import com.swmansion.gesturehandler.l;
import com.swmansion.gesturehandler.p;

public final class j implements p {
  public final View a(ViewGroup paramViewGroup, int paramInt) {
    return (paramViewGroup instanceof ReactViewGroup) ? paramViewGroup.getChildAt(((ReactViewGroup)paramViewGroup).getZIndexMappedChildIndex(paramInt)) : paramViewGroup.getChildAt(paramInt);
  }
  
  public final l a(View paramView) {
    PointerEvents pointerEvents;
    if (paramView instanceof ReactPointerEventsView) {
      pointerEvents = ((ReactPointerEventsView)paramView).getPointerEvents();
    } else {
      pointerEvents = PointerEvents.AUTO;
    } 
    if (!paramView.isEnabled()) {
      if (pointerEvents == PointerEvents.AUTO)
        return l.BOX_NONE; 
      if (pointerEvents == PointerEvents.BOX_ONLY)
        return l.NONE; 
    } 
    int i = null.a[pointerEvents.ordinal()];
    return (i != 1) ? ((i != 2) ? ((i != 3) ? l.AUTO : l.NONE) : l.BOX_NONE) : l.BOX_ONLY;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\swmansion\gesturehandler\react\j.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */