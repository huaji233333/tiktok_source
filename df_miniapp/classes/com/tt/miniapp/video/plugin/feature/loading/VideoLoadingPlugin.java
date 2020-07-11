package com.tt.miniapp.video.plugin.feature.loading;

import android.os.Message;
import com.tt.miniapp.util.WeakHandler;
import com.tt.miniapp.video.plugin.base.BaseVideoPlugin;
import com.tt.miniapp.video.plugin.base.IVideoPluginEvent;

public class VideoLoadingPlugin extends BaseVideoPlugin implements WeakHandler.IHandler {
  private WeakHandler mHandler = new WeakHandler(this);
  
  private VideoLoadingLayout mLoadingLayout;
  
  private boolean isViewReady() {
    return (this.mLoadingLayout != null);
  }
  
  public int getPluginType() {
    return 207;
  }
  
  public void handleMsg(Message paramMessage) {
    if (paramMessage.what != 2001)
      return; 
    showLoading(true);
  }
  
  public boolean handleVideoEvent(IVideoPluginEvent paramIVideoPluginEvent) {
    // Byte code:
    //   0: aload_1
    //   1: ifnull -> 298
    //   4: ldc 'tma_VideoLoadingPlugin'
    //   6: iconst_2
    //   7: anewarray java/lang/Object
    //   10: dup
    //   11: iconst_0
    //   12: ldc 'handleVideoEvent '
    //   14: aastore
    //   15: dup
    //   16: iconst_1
    //   17: aload_1
    //   18: invokeinterface getType : ()I
    //   23: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   26: aastore
    //   27: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   30: aload_1
    //   31: invokeinterface getArgs : ()Landroid/os/Bundle;
    //   36: astore #4
    //   38: aload_1
    //   39: invokeinterface getType : ()I
    //   44: istore_2
    //   45: iload_2
    //   46: bipush #105
    //   48: if_icmpeq -> 293
    //   51: iload_2
    //   52: bipush #110
    //   54: if_icmpeq -> 270
    //   57: iload_2
    //   58: sipush #200
    //   61: if_icmpeq -> 257
    //   64: iload_2
    //   65: sipush #202
    //   68: if_icmpeq -> 229
    //   71: iload_2
    //   72: bipush #107
    //   74: if_icmpeq -> 221
    //   77: iload_2
    //   78: bipush #108
    //   80: if_icmpeq -> 194
    //   83: iload_2
    //   84: bipush #114
    //   86: if_icmpeq -> 186
    //   89: iload_2
    //   90: bipush #115
    //   92: if_icmpeq -> 213
    //   95: iload_2
    //   96: tableswitch default -> 128, 100 -> 162, 101 -> 139, 102 -> 221, 103 -> 131
    //   128: goto -> 298
    //   131: aload_0
    //   132: iconst_0
    //   133: invokevirtual showRetry : (Z)V
    //   136: goto -> 298
    //   139: aload_0
    //   140: getfield mHandler : Lcom/tt/miniapp/util/WeakHandler;
    //   143: sipush #2001
    //   146: invokevirtual removeMessages : (I)V
    //   149: aload_0
    //   150: iconst_0
    //   151: invokevirtual showLoading : (Z)V
    //   154: aload_0
    //   155: iconst_0
    //   156: invokevirtual showRetry : (Z)V
    //   159: goto -> 298
    //   162: aload_0
    //   163: getfield mHandler : Lcom/tt/miniapp/util/WeakHandler;
    //   166: astore #4
    //   168: aload #4
    //   170: ifnull -> 298
    //   173: aload #4
    //   175: sipush #2001
    //   178: lconst_0
    //   179: invokevirtual sendEmptyMessageDelayed : (IJ)Z
    //   182: pop
    //   183: goto -> 298
    //   186: aload_0
    //   187: iconst_1
    //   188: invokevirtual showLoading : (Z)V
    //   191: goto -> 298
    //   194: aload_0
    //   195: getfield mHandler : Lcom/tt/miniapp/util/WeakHandler;
    //   198: astore #4
    //   200: aload #4
    //   202: ifnull -> 298
    //   205: aload #4
    //   207: sipush #2001
    //   210: invokevirtual removeMessages : (I)V
    //   213: aload_0
    //   214: iconst_0
    //   215: invokevirtual showLoading : (Z)V
    //   218: goto -> 298
    //   221: aload_0
    //   222: iconst_0
    //   223: invokevirtual showLoading : (Z)V
    //   226: goto -> 298
    //   229: aload #4
    //   231: ldc 'fullscreen'
    //   233: invokevirtual getBoolean : (Ljava/lang/String;)Z
    //   236: istore_3
    //   237: aload_0
    //   238: getfield mLoadingLayout : Lcom/tt/miniapp/video/plugin/feature/loading/VideoLoadingLayout;
    //   241: astore #4
    //   243: aload #4
    //   245: ifnull -> 298
    //   248: aload #4
    //   250: iload_3
    //   251: invokevirtual setFullscreen : (Z)V
    //   254: goto -> 298
    //   257: aload_0
    //   258: invokespecial isViewReady : ()Z
    //   261: ifne -> 298
    //   264: aload_0
    //   265: invokevirtual initView : ()V
    //   268: iconst_1
    //   269: ireturn
    //   270: aload_0
    //   271: iconst_0
    //   272: invokevirtual showLoading : (Z)V
    //   275: aload_0
    //   276: getfield mHandler : Lcom/tt/miniapp/util/WeakHandler;
    //   279: sipush #2001
    //   282: invokevirtual removeMessages : (I)V
    //   285: aload_0
    //   286: iconst_1
    //   287: invokevirtual showRetry : (Z)V
    //   290: goto -> 298
    //   293: aload_0
    //   294: iconst_1
    //   295: invokevirtual showLoading : (Z)V
    //   298: aload_0
    //   299: aload_1
    //   300: invokespecial handleVideoEvent : (Lcom/tt/miniapp/video/plugin/base/IVideoPluginEvent;)Z
    //   303: ireturn
  }
  
  protected void initView() {
    if (this.mLoadingLayout == null) {
      this.mLoadingLayout = new VideoLoadingLayout();
      this.mLoadingLayout.initView(getContext(), getPluginMainContainer());
      this.mLoadingLayout.setUIListener(new VideoLoadingLayout.LoadingUIListener() {
            public void onRetryClick() {
              VideoLoadingPlugin.this.showRetry(false);
              VideoLoadingPlugin.this.showLoading(true);
              if (VideoLoadingPlugin.this.getHost() != null)
                VideoLoadingPlugin.this.getHost().execCommand(2012); 
            }
          });
    } 
  }
  
  public void showLoading(boolean paramBoolean) {
    if (isViewReady()) {
      VideoLoadingLayout videoLoadingLayout = this.mLoadingLayout;
      if (videoLoadingLayout != null)
        videoLoadingLayout.showLoading(paramBoolean); 
    } 
  }
  
  public void showRetry(boolean paramBoolean) {
    if (isViewReady()) {
      VideoLoadingLayout videoLoadingLayout = this.mLoadingLayout;
      if (videoLoadingLayout != null)
        videoLoadingLayout.showRetry(paramBoolean); 
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\video\plugin\feature\loading\VideoLoadingPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */