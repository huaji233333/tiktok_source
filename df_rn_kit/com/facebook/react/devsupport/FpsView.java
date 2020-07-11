package com.facebook.react.devsupport;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.a;
import com.facebook.common.e.a;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.modules.core.ChoreographerCompat;
import com.facebook.react.modules.debug.FpsDebugFrameCallback;
import java.util.Locale;

public class FpsView extends FrameLayout {
  private final FPSMonitorRunnable mFPSMonitorRunnable;
  
  public final FpsDebugFrameCallback mFrameCallback;
  
  private final TextView mTextView;
  
  public FpsView(ReactContext paramReactContext) {
    super((Context)paramReactContext);
    inflate((Context)paramReactContext, 1980039169, (ViewGroup)this);
    this.mTextView = (TextView)findViewById(1979973645);
    this.mFrameCallback = new FpsDebugFrameCallback(ChoreographerCompat.getInstance(), paramReactContext);
    this.mFPSMonitorRunnable = new FPSMonitorRunnable();
    setCurrentFPS(0.0D, 0.0D, 0, 0);
  }
  
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    this.mFrameCallback.reset();
    this.mFrameCallback.start();
    this.mFPSMonitorRunnable.start();
  }
  
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    this.mFrameCallback.stop();
    this.mFPSMonitorRunnable.stop();
  }
  
  public void setCurrentFPS(double paramDouble1, double paramDouble2, int paramInt1, int paramInt2) {
    String str = a.a(Locale.US, "UI: %.1f fps\n%d dropped so far\n%d stutters (4+) so far\nJS: %.1f fps", new Object[] { Double.valueOf(paramDouble1), Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Double.valueOf(paramDouble2) });
    this.mTextView.setText(str);
    a.a("ReactNative", str);
  }
  
  class FPSMonitorRunnable implements Runnable {
    private boolean mShouldStop;
    
    private int mTotal4PlusFrameStutters;
    
    private int mTotalFramesDropped;
    
    private FPSMonitorRunnable() {}
    
    public void run() {
      if (this.mShouldStop)
        return; 
      this.mTotalFramesDropped += FpsView.this.mFrameCallback.getExpectedNumFrames() - FpsView.this.mFrameCallback.getNumFrames();
      this.mTotal4PlusFrameStutters += FpsView.this.mFrameCallback.get4PlusFrameStutters();
      FpsView fpsView = FpsView.this;
      fpsView.setCurrentFPS(fpsView.mFrameCallback.getFPS(), FpsView.this.mFrameCallback.getJSFPS(), this.mTotalFramesDropped, this.mTotal4PlusFrameStutters);
      FpsView.this.mFrameCallback.reset();
      FpsView.this.postDelayed(this, 500L);
    }
    
    public void start() {
      this.mShouldStop = false;
      FpsView.this.post(this);
    }
    
    public void stop() {
      this.mShouldStop = true;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\devsupport\FpsView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */