package com.tt.miniapp.component.nativeview.liveplayer.view;

import android.content.Context;
import android.graphics.Matrix;
import android.view.TextureView;
import android.view.View;
import com.tt.miniapp.liveplayer.ITTLivePlayer;
import d.f.b.l;
import java.util.HashMap;

public final class LivePlayerTextureView extends TextureView {
  private HashMap _$_findViewCache;
  
  private ITTLivePlayer.DisplayMode mPendingDisplayMode;
  
  private Matrix mTransform = new Matrix();
  
  private int mVideoHeight;
  
  private int mVideoWidth;
  
  public LivePlayerTextureView(Context paramContext) {
    super(paramContext);
  }
  
  private final void resolveDisplayMode(float paramFloat1, float paramFloat2) {
    int i = this.mVideoWidth;
    if (i != 0) {
      ITTLivePlayer.ObjectFit objectFit;
      int j = this.mVideoHeight;
      if (j == 0)
        return; 
      this.mTransform.reset();
      Matrix matrix = this.mTransform;
      float f1 = i;
      float f2 = (paramFloat1 - f1) / 2.0F;
      float f3 = j;
      matrix.preTranslate(f2, (paramFloat2 - f3) / 2.0F);
      this.mTransform.preScale(f1 / paramFloat1, f3 / paramFloat2);
      ITTLivePlayer.DisplayMode displayMode1 = this.mPendingDisplayMode;
      ITTLivePlayer.DisplayMode displayMode2 = null;
      if (displayMode1 != null) {
        ITTLivePlayer.Orientation orientation = displayMode1.getDisplayOrientation();
      } else {
        displayMode1 = null;
      } 
      int m = i;
      int k = j;
      if (displayMode1 == ITTLivePlayer.Orientation.HORIZONTAL) {
        this.mTransform.postRotate(90.0F, paramFloat1 / 2.0F, paramFloat2 / 2.0F);
        k = i;
        m = j;
      } 
      f1 = paramFloat1 / m;
      f2 = paramFloat2 / k;
      ITTLivePlayer.DisplayMode displayMode3 = this.mPendingDisplayMode;
      displayMode1 = displayMode2;
      if (displayMode3 != null)
        objectFit = displayMode3.getObjectFit(); 
      if (objectFit == ITTLivePlayer.ObjectFit.FILLCROP) {
        f1 = Math.max(f1, f2);
        this.mTransform.postScale(f1, f1, paramFloat1 / 2.0F, paramFloat2 / 2.0F);
      } else {
        f1 = Math.min(f1, f2);
        this.mTransform.postScale(f1, f1, paramFloat1 / 2.0F, paramFloat2 / 2.0F);
      } 
      setTransform(this.mTransform);
      postInvalidate();
    } 
  }
  
  public final void _$_clearFindViewByIdCache() {
    HashMap hashMap = this._$_findViewCache;
    if (hashMap != null)
      hashMap.clear(); 
  }
  
  public final View _$_findCachedViewById(int paramInt) {
    if (this._$_findViewCache == null)
      this._$_findViewCache = new HashMap<Object, Object>(); 
    View view2 = (View)this._$_findViewCache.get(Integer.valueOf(paramInt));
    View view1 = view2;
    if (view2 == null) {
      view1 = findViewById(paramInt);
      this._$_findViewCache.put(Integer.valueOf(paramInt), view1);
    } 
    return view1;
  }
  
  protected final void onMeasure(int paramInt1, int paramInt2) {
    resolveDisplayMode(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    super.onMeasure(paramInt1, paramInt2);
  }
  
  public final void onVideoSizeChanged(int paramInt1, int paramInt2) {
    this.mVideoWidth = paramInt1;
    this.mVideoHeight = paramInt2;
    requestLayout();
  }
  
  public final void updateDisplayMode(ITTLivePlayer.DisplayMode paramDisplayMode) {
    l.b(paramDisplayMode, "displayMode");
    this.mPendingDisplayMode = paramDisplayMode;
    requestLayout();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\liveplayer\view\LivePlayerTextureView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */