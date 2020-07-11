package com.tt.miniapp.component.nativeview.game;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.util.UIUtils;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONObject;

public class GameBannerManager {
  private static GameBannerManager sManager;
  
  public GameAbsoluteLayout mParentLayout;
  
  private volatile AtomicInteger mViewIdCount;
  
  private volatile SparseArray<View> mViewMap;
  
  public GameBannerManager(GameAbsoluteLayout paramGameAbsoluteLayout) {
    this.mParentLayout = paramGameAbsoluteLayout;
    this.mViewMap = new SparseArray();
    this.mViewIdCount = new AtomicInteger(0);
  }
  
  public static GameBannerManager get() {
    return sManager;
  }
  
  public static void measureView(FrameLayout.LayoutParams paramLayoutParams, int paramInt, View paramView, OnMeasuredListener paramOnMeasuredListener) {
    int i;
    int j = paramLayoutParams.width;
    if (paramLayoutParams.width < 0) {
      i = Integer.MIN_VALUE;
    } else {
      i = 1073741824;
    } 
    paramView.measure(View.MeasureSpec.makeMeasureSpec(j, i), View.MeasureSpec.makeMeasureSpec(1073741823, -2147483648));
    paramOnMeasuredListener.onMeasured(paramInt, paramView);
  }
  
  public static void set(GameBannerManager paramGameBannerManager) {
    sManager = paramGameBannerManager;
  }
  
  public static FrameLayout.LayoutParams style2Params(Context paramContext, JSONObject paramJSONObject) {
    boolean bool1;
    boolean bool2;
    GameAbsoluteLayout.LayoutParams layoutParams = new GameAbsoluteLayout.LayoutParams(-2, -2, 0, 0);
    if (paramJSONObject == null)
      return layoutParams; 
    double d1 = UIUtils.dip2Px(paramContext, (float)paramJSONObject.optDouble("top", 0.0D));
    double d2 = UIUtils.dip2Px(paramContext, (float)paramJSONObject.optDouble("left", 0.0D));
    double d3 = UIUtils.dip2Px(paramContext, (float)paramJSONObject.optDouble("right", 0.0D));
    double d4 = UIUtils.dip2Px(paramContext, (float)paramJSONObject.optDouble("bottom", 0.0D));
    String str1 = paramJSONObject.optString("horizontalAlign", "left");
    String str2 = paramJSONObject.optString("verticalAlign", "top");
    if ("center".equalsIgnoreCase(str1)) {
      bool1 = true;
    } else if ("right".equalsIgnoreCase(str1)) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    if ("center".equalsIgnoreCase(str2)) {
      bool2 = true;
    } else if ("bottom".equalsIgnoreCase(str2)) {
      bool2 = true;
    } else {
      bool2 = false;
    } 
    int i = paramJSONObject.optInt("width", -2);
    layoutParams.topMargin = (int)d1;
    layoutParams.leftMargin = (int)d2;
    layoutParams.rightMargin = (int)d3;
    layoutParams.bottomMargin = (int)d4;
    layoutParams.setGravity(bool1, bool2);
    layoutParams.width = (int)UIUtils.dip2Px(paramContext, i);
    return layoutParams;
  }
  
  public boolean add(int paramInt, View paramView, JSONObject paramJSONObject, OnMeasuredListener paramOnMeasuredListener) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mViewMap : Landroid/util/SparseArray;
    //   4: iload_1
    //   5: invokevirtual get : (I)Ljava/lang/Object;
    //   8: ifnull -> 13
    //   11: iconst_0
    //   12: ireturn
    //   13: aload_0
    //   14: monitorenter
    //   15: aload_0
    //   16: getfield mViewMap : Landroid/util/SparseArray;
    //   19: iload_1
    //   20: aload_2
    //   21: invokevirtual put : (ILjava/lang/Object;)V
    //   24: aload_0
    //   25: monitorexit
    //   26: aload_2
    //   27: bipush #8
    //   29: invokevirtual setVisibility : (I)V
    //   32: aload_0
    //   33: getfield mParentLayout : Lcom/tt/miniapp/component/nativeview/game/GameAbsoluteLayout;
    //   36: invokevirtual getContext : ()Landroid/content/Context;
    //   39: aload_3
    //   40: invokestatic style2Params : (Landroid/content/Context;Lorg/json/JSONObject;)Landroid/widget/FrameLayout$LayoutParams;
    //   43: astore_3
    //   44: aload_0
    //   45: getfield mParentLayout : Lcom/tt/miniapp/component/nativeview/game/GameAbsoluteLayout;
    //   48: aload_2
    //   49: aload_3
    //   50: invokevirtual addView : (Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V
    //   53: aload #4
    //   55: ifnull -> 66
    //   58: aload_3
    //   59: iload_1
    //   60: aload_2
    //   61: aload #4
    //   63: invokestatic measureView : (Landroid/widget/FrameLayout$LayoutParams;ILandroid/view/View;Lcom/tt/miniapp/component/nativeview/game/GameBannerManager$OnMeasuredListener;)V
    //   66: iconst_1
    //   67: ireturn
    //   68: astore_2
    //   69: aload_0
    //   70: monitorexit
    //   71: aload_2
    //   72: athrow
    // Exception table:
    //   from	to	target	type
    //   15	26	68	finally
    //   69	71	68	finally
  }
  
  public int generateBannerId() {
    return this.mViewIdCount.addAndGet(1);
  }
  
  public boolean remove(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mViewMap : Landroid/util/SparseArray;
    //   6: iload_1
    //   7: invokevirtual get : (I)Ljava/lang/Object;
    //   10: checkcast android/view/View
    //   13: astore_2
    //   14: aload_2
    //   15: ifnonnull -> 22
    //   18: aload_0
    //   19: monitorexit
    //   20: iconst_0
    //   21: ireturn
    //   22: aload_0
    //   23: getfield mViewMap : Landroid/util/SparseArray;
    //   26: iload_1
    //   27: invokevirtual remove : (I)V
    //   30: new com/tt/miniapp/component/nativeview/game/GameBannerManager$1
    //   33: dup
    //   34: aload_0
    //   35: aload_2
    //   36: invokespecial <init> : (Lcom/tt/miniapp/component/nativeview/game/GameBannerManager;Landroid/view/View;)V
    //   39: invokestatic runOnUIThread : (Ljava/lang/Runnable;)V
    //   42: aload_0
    //   43: monitorexit
    //   44: iconst_1
    //   45: ireturn
    //   46: astore_2
    //   47: aload_0
    //   48: monitorexit
    //   49: aload_2
    //   50: athrow
    // Exception table:
    //   from	to	target	type
    //   2	14	46	finally
    //   22	42	46	finally
  }
  
  public boolean setVisible(int paramInt, final boolean shown) {
    final View view = (View)this.mViewMap.get(paramInt);
    if (view == null)
      return false; 
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            byte b;
            View view = view;
            if (shown) {
              b = 0;
            } else {
              b = 4;
            } 
            view.setVisibility(b);
          }
        });
    return true;
  }
  
  public boolean update(final int id, final JSONObject style, final OnMeasuredListener listener) {
    final View view = (View)this.mViewMap.get(id);
    if (view == null)
      return false; 
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            FrameLayout.LayoutParams layoutParams = GameBannerManager.style2Params(GameBannerManager.this.mParentLayout.getContext(), style);
            view.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
            GameBannerManager.OnMeasuredListener onMeasuredListener = listener;
            if (onMeasuredListener == null)
              return; 
            GameBannerManager.measureView(layoutParams, id, view, onMeasuredListener);
          }
        });
    return true;
  }
  
  public static interface OnMeasuredListener {
    void onMeasured(int param1Int, View param1View);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\game\GameBannerManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */