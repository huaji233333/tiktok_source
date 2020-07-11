package com.tt.miniapp.component.nativeview.liveplayer.context;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.os.Build;
import android.text.TextUtils;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import com.bytedance.sandboxapp.protocol.service.j.a;
import com.bytedance.sandboxapp.protocol.service.j.a.a;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.component.nativeview.liveplayer.LivePlayer;
import com.tt.miniapp.component.nativeview.liveplayer.context.surface.SurfaceHolder;
import com.tt.miniapp.component.nativeview.liveplayer.util.LivePlayerUtil;
import com.tt.miniapp.component.nativeview.liveplayer.view.LivePlayerTextureView;
import com.tt.miniapp.liveplayer.ITTLivePlayer;
import com.tt.miniapp.liveplayer.listener.ITTLivePlayerListener;
import com.tt.miniapp.liveplayer.listener.adapter.TTLivePlayerListenerAdapter;
import com.tt.miniapp.manager.NetStateManager;
import com.tt.miniapp.view.webcore.AbsoluteLayout;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import d.f.b.g;
import d.f.b.l;
import d.u;
import org.json.JSONObject;

public final class LiveComponentContext implements a.c, ILivePlayerContext, NetStateManager.NetStateChangeListener {
  public static final Companion Companion = new Companion(null);
  
  private final Context application;
  
  private final LivePlayer component;
  
  private final ITTLivePlayer livePlayer;
  
  private a mAudioFocusRequest;
  
  public SurfaceHolder mCacheSurface;
  
  private ITTLivePlayer.DisplayMode mDisplayMode;
  
  private boolean mFullScreen;
  
  private final LivePlayerTextureListener mLivePlayerTextureListener;
  
  private a mMediaServer;
  
  public NetStateManager.NetworkType mNetworkStatus;
  
  public String mPlayingUrl;
  
  private LayoutInfo mPreviousLayoutInfo;
  
  private boolean mResumePlayOnFocusGain;
  
  private boolean mResumePlayOnViewResume;
  
  private boolean mResumeWhenNetworkAvailable;
  
  public TextureView mTextureView;
  
  private final WebViewManager.IRender render;
  
  public LiveComponentContext(Context paramContext, WebViewManager.IRender paramIRender, LivePlayer paramLivePlayer, ITTLivePlayer paramITTLivePlayer) {
    this.application = paramContext;
    this.render = paramIRender;
    this.component = paramLivePlayer;
    this.livePlayer = paramITTLivePlayer;
    this.mLivePlayerTextureListener = new LivePlayerTextureListener();
    this.mPlayingUrl = "";
    this.livePlayer.setListener((ITTLivePlayerListener)new LivePlayerListener());
    AppbrandApplicationImpl appbrandApplicationImpl = AppbrandApplicationImpl.getInst();
    l.a(appbrandApplicationImpl, "AppbrandApplicationImpl.getInst()");
    this.mMediaServer = (a)appbrandApplicationImpl.getMiniAppContext().getService(a.class);
    NetStateManager.getInst().registerNetStateChangeReceiver(this);
  }
  
  private final void applyFullScreen(a.a parama, int paramInt) {
    if (Build.VERSION.SDK_INT < 23) {
      ViewTreeObserver viewTreeObserver = this.component.getViewTreeObserver();
      viewTreeObserver.addOnGlobalLayoutListener(new LiveComponentContext$applyFullScreen$1(parama, paramInt, viewTreeObserver));
      return;
    } 
    AbsoluteLayout.LayoutParams layoutParams = new AbsoluteLayout.LayoutParams(new ViewGroup.LayoutParams(-1, -1));
    layoutParams.x = 0;
    layoutParams.y = 0;
    layoutParams.zIndex = paramInt;
    layoutParams.isFullScreen = true;
    this.component.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
  }
  
  private final void applyPreviousLayoutInfo() {
    ViewParent viewParent = this.component.getParent();
    if (viewParent instanceof AbsoluteLayout) {
      LayoutInfo layoutInfo1 = this.mPreviousLayoutInfo;
      if (layoutInfo1 == null)
        return; 
      if (layoutInfo1 == null)
        l.a(); 
      AbsoluteLayout absoluteLayout = (AbsoluteLayout)viewParent;
      layoutInfo1.update(absoluteLayout);
      LivePlayer livePlayer = this.component;
      LayoutInfo layoutInfo2 = this.mPreviousLayoutInfo;
      if (layoutInfo2 == null)
        l.a(); 
      livePlayer.setLayoutParams((ViewGroup.LayoutParams)layoutInfo2.getPreviousLayoutParams());
      View view = (View)this.component;
      layoutInfo2 = this.mPreviousLayoutInfo;
      if (layoutInfo2 == null)
        l.a(); 
      absoluteLayout.updateNativeViewOffset(view, layoutInfo2.getPreviousOffset());
    } 
  }
  
  private final void savePreviousLayoutInfo() {
    ViewParent viewParent = this.component.getParent();
    if (viewParent instanceof AbsoluteLayout) {
      ViewGroup.LayoutParams layoutParams = this.component.getLayoutParams();
      if (layoutParams != null) {
        AbsoluteLayout.LayoutParams layoutParams1 = (AbsoluteLayout.LayoutParams)layoutParams;
        AbsoluteLayout absoluteLayout = (AbsoluteLayout)viewParent;
        AbsoluteLayout.ViewOffset viewOffset = absoluteLayout.getViewOffset(this.component.getId());
        l.a(viewOffset, "parent.getViewOffset(component.id)");
        this.mPreviousLayoutInfo = new LayoutInfo(layoutParams1, viewOffset, absoluteLayout.getCurScrollX(), absoluteLayout.getCurScrollY());
        return;
      } 
      throw new u("null cannot be cast to non-null type com.tt.miniapp.view.webcore.AbsoluteLayout.LayoutParams");
    } 
  }
  
  public final void bindSurface(TextureView paramTextureView) {
    l.b(paramTextureView, "textureView");
    this.mTextureView = paramTextureView;
    paramTextureView = this.mTextureView;
    if (paramTextureView == null)
      l.a(); 
    paramTextureView.setSurfaceTextureListener(this.mLivePlayerTextureListener);
    setDisplayMode(new ITTLivePlayer.DisplayMode(ITTLivePlayer.ObjectFit.CONTAIN, ITTLivePlayer.Orientation.VERTICAL));
  }
  
  public final void exitFullScreen(View paramView) {
    l.b(paramView, "targetView");
    if (!this.mFullScreen)
      return; 
    a a1 = this.mMediaServer;
    if (a1 != null)
      a1.exitFullScreen(paramView); 
    applyPreviousLayoutInfo();
    this.mFullScreen = false;
    this.component.onLivePlayerFullscreenChange(this.mFullScreen, a.a.PORTRAIT);
  }
  
  public final Context getApplication() {
    return this.application;
  }
  
  public final LivePlayer getComponent() {
    return this.component;
  }
  
  public final ITTLivePlayer getLivePlayer() {
    return this.livePlayer;
  }
  
  public final WebViewManager.IRender getRender() {
    return this.render;
  }
  
  public final boolean isFullScreen() {
    return this.mFullScreen;
  }
  
  public final boolean isPlaying() {
    return this.livePlayer.isPlaying();
  }
  
  public final void onAudioFocusChanged(a.b paramb) {
    // Byte code:
    //   0: aload_1
    //   1: ldc_w 'state'
    //   4: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   7: getstatic com/tt/miniapp/component/nativeview/liveplayer/context/LiveComponentContext$WhenMappings.$EnumSwitchMapping$0 : [I
    //   10: aload_1
    //   11: invokevirtual ordinal : ()I
    //   14: iaload
    //   15: istore_2
    //   16: iload_2
    //   17: iconst_1
    //   18: if_icmpeq -> 137
    //   21: iload_2
    //   22: iconst_2
    //   23: if_icmpeq -> 137
    //   26: iload_2
    //   27: iconst_3
    //   28: if_icmpeq -> 94
    //   31: iload_2
    //   32: iconst_4
    //   33: if_icmpeq -> 62
    //   36: iload_2
    //   37: iconst_5
    //   38: if_icmpeq -> 62
    //   41: ldc_w 'LiveComponentContext'
    //   44: iconst_2
    //   45: anewarray java/lang/Object
    //   48: dup
    //   49: iconst_0
    //   50: ldc_w 'onAudioFocusChanged  unexpected state'
    //   53: aastore
    //   54: dup
    //   55: iconst_1
    //   56: aload_1
    //   57: aastore
    //   58: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   61: return
    //   62: aload_0
    //   63: monitorenter
    //   64: aload_0
    //   65: aload_0
    //   66: getfield livePlayer : Lcom/tt/miniapp/liveplayer/ITTLivePlayer;
    //   69: invokeinterface isPlaying : ()Z
    //   74: putfield mResumePlayOnFocusGain : Z
    //   77: aload_0
    //   78: monitorexit
    //   79: aload_0
    //   80: getfield livePlayer : Lcom/tt/miniapp/liveplayer/ITTLivePlayer;
    //   83: invokeinterface stop : ()V
    //   88: return
    //   89: astore_1
    //   90: aload_0
    //   91: monitorexit
    //   92: aload_1
    //   93: athrow
    //   94: aload_0
    //   95: monitorenter
    //   96: aload_0
    //   97: iconst_0
    //   98: putfield mResumePlayOnFocusGain : Z
    //   101: aload_0
    //   102: monitorexit
    //   103: aload_0
    //   104: getfield mMediaServer : Lcom/bytedance/sandboxapp/protocol/service/j/a;
    //   107: astore_1
    //   108: aload_1
    //   109: ifnull -> 122
    //   112: aload_1
    //   113: aload_0
    //   114: getfield mAudioFocusRequest : Lcom/bytedance/sandboxapp/protocol/service/j/a/a;
    //   117: invokeinterface abandonAudioFocus : (Lcom/bytedance/sandboxapp/protocol/service/j/a/a;)V
    //   122: aload_0
    //   123: getfield livePlayer : Lcom/tt/miniapp/liveplayer/ITTLivePlayer;
    //   126: invokeinterface stop : ()V
    //   131: return
    //   132: astore_1
    //   133: aload_0
    //   134: monitorexit
    //   135: aload_1
    //   136: athrow
    //   137: aload_0
    //   138: getfield mResumePlayOnFocusGain : Z
    //   141: ifeq -> 168
    //   144: aload_0
    //   145: monitorenter
    //   146: aload_0
    //   147: iconst_0
    //   148: putfield mResumePlayOnFocusGain : Z
    //   151: aload_0
    //   152: monitorexit
    //   153: aload_0
    //   154: getfield livePlayer : Lcom/tt/miniapp/liveplayer/ITTLivePlayer;
    //   157: invokeinterface play : ()V
    //   162: return
    //   163: astore_1
    //   164: aload_0
    //   165: monitorexit
    //   166: aload_1
    //   167: athrow
    //   168: return
    // Exception table:
    //   from	to	target	type
    //   64	77	89	finally
    //   96	101	132	finally
    //   146	151	163	finally
  }
  
  public final void onNetStateChange(NetStateManager.NetworkType paramNetworkType) {
    boolean bool;
    l.b(paramNetworkType, "newNetworkType");
    if (paramNetworkType.isAvailable() && this.mResumeWhenNetworkAvailable) {
      play();
      bool = false;
    } else {
      bool = this.livePlayer.isPlaying();
    } 
    this.mResumeWhenNetworkAvailable = bool;
  }
  
  public final void onPause() {
    if (this.livePlayer.isPlaying()) {
      stop();
      this.mResumePlayOnViewResume = true;
    } 
  }
  
  public final void onResume() {
    if (this.mResumePlayOnViewResume) {
      play();
      this.mResumePlayOnViewResume = false;
    } 
  }
  
  public final void pause() {
    a a1 = this.mMediaServer;
    if (a1 != null)
      a1.abandonAudioFocus(this.mAudioFocusRequest); 
    this.livePlayer.pause();
  }
  
  public final void play() {
    if (this.mAudioFocusRequest == null)
      this.mAudioFocusRequest = new a(a.b.GAIN_TRANSIENT, a.f.USAGE_MEDIA, a.e.SHARE, this); 
    a a1 = this.mMediaServer;
    if (a1 != null) {
      a.d d = a1.acquireAudioFocus(this.mAudioFocusRequest);
    } else {
      a1 = null;
    } 
    if (a1 != a.d.FOCUS_REQUEST_GRANTED)
      AppBrandLogger.e("LiveComponentContext", new Object[] { "acquireAudioFocus fail", a1 }); 
    this.livePlayer.play();
  }
  
  public final void release() {
    SurfaceHolder surfaceHolder = this.mCacheSurface;
    if (surfaceHolder != null)
      surfaceHolder.release(); 
    this.livePlayer.release();
    a a1 = this.mMediaServer;
    if (a1 != null)
      a1.abandonAudioFocus(this.mAudioFocusRequest); 
    this.mAudioFocusRequest = null;
    NetStateManager.getInst().unregisterNetStateChangeReceiver(this);
  }
  
  public final void requestFullScreen(View paramView, a.a parama, JSONObject paramJSONObject) {
    int i;
    l.b(paramView, "targetView");
    l.b(parama, "direction");
    if (this.mFullScreen)
      return; 
    savePreviousLayoutInfo();
    if (paramJSONObject != null) {
      i = paramJSONObject.optInt("zIndex");
    } else {
      i = Integer.MAX_VALUE;
    } 
    applyFullScreen(parama, i);
    a a1 = this.mMediaServer;
    if (a1 != null)
      a1.enterFullScreen(paramView, parama); 
    this.mFullScreen = true;
    this.component.onLivePlayerFullscreenChange(this.mFullScreen, parama);
  }
  
  public final void setDisplayMode(ITTLivePlayer.DisplayMode paramDisplayMode) {
    l.b(paramDisplayMode, "displayMode");
    ITTLivePlayer.DisplayMode displayMode = this.mDisplayMode;
    if (displayMode != null) {
      if (displayMode == null)
        l.a(); 
      if (displayMode.getObjectFit() == paramDisplayMode.getObjectFit()) {
        displayMode = this.mDisplayMode;
        if (displayMode == null)
          l.a(); 
        if (displayMode.getDisplayOrientation() == paramDisplayMode.getDisplayOrientation())
          return; 
      } 
    } 
    TextureView textureView = this.mTextureView;
    if (textureView instanceof LivePlayerTextureView)
      if (textureView != null) {
        ((LivePlayerTextureView)textureView).updateDisplayMode(paramDisplayMode);
      } else {
        throw new u("null cannot be cast to non-null type com.tt.miniapp.component.nativeview.liveplayer.view.LivePlayerTextureView");
      }  
    this.mDisplayMode = paramDisplayMode;
  }
  
  public final void setMuted(boolean paramBoolean) {
    this.livePlayer.muted(paramBoolean);
  }
  
  public final void setPlayUrl(String paramString) {
    l.b(paramString, "url");
    setPlayUrl(paramString, null);
  }
  
  public final void setPlayUrl(String paramString, JSONObject paramJSONObject) {
    l.b(paramString, "url");
    if (!TextUtils.equals(this.mPlayingUrl, paramString)) {
      if (!TextUtils.isEmpty(this.mPlayingUrl))
        this.livePlayer.reset(); 
      this.mPlayingUrl = paramString;
      this.livePlayer.setDataSource(this.mPlayingUrl, paramJSONObject);
    } 
  }
  
  public final void stop() {
    a a1 = this.mMediaServer;
    if (a1 != null)
      a1.abandonAudioFocus(this.mAudioFocusRequest); 
    this.livePlayer.stop();
  }
  
  public static final class Companion {
    private Companion() {}
  }
  
  public final class LayoutInfo {
    private AbsoluteLayout.LayoutParams previousLayoutParams;
    
    private AbsoluteLayout.ViewOffset previousOffset;
    
    private int previousScrollX;
    
    private int previousScrollY;
    
    public LayoutInfo(AbsoluteLayout.LayoutParams param1LayoutParams, AbsoluteLayout.ViewOffset param1ViewOffset, int param1Int1, int param1Int2) {
      this.previousLayoutParams = param1LayoutParams;
      this.previousOffset = param1ViewOffset;
      this.previousScrollX = param1Int1;
      this.previousScrollY = param1Int2;
    }
    
    public final AbsoluteLayout.LayoutParams getPreviousLayoutParams() {
      return this.previousLayoutParams;
    }
    
    public final AbsoluteLayout.ViewOffset getPreviousOffset() {
      return this.previousOffset;
    }
    
    public final int getPreviousScrollX() {
      return this.previousScrollX;
    }
    
    public final int getPreviousScrollY() {
      return this.previousScrollY;
    }
    
    public final void setPreviousLayoutParams(AbsoluteLayout.LayoutParams param1LayoutParams) {
      l.b(param1LayoutParams, "<set-?>");
      this.previousLayoutParams = param1LayoutParams;
    }
    
    public final void setPreviousOffset(AbsoluteLayout.ViewOffset param1ViewOffset) {
      l.b(param1ViewOffset, "<set-?>");
      this.previousOffset = param1ViewOffset;
    }
    
    public final void setPreviousScrollX(int param1Int) {
      this.previousScrollX = param1Int;
    }
    
    public final void setPreviousScrollY(int param1Int) {
      this.previousScrollY = param1Int;
    }
    
    public final void update(AbsoluteLayout param1AbsoluteLayout) {
      l.b(param1AbsoluteLayout, "absoluteLayout");
      int i = param1AbsoluteLayout.getCurScrollX() - this.previousScrollX;
      int j = param1AbsoluteLayout.getCurScrollY() - this.previousScrollY;
      AbsoluteLayout.LayoutParams layoutParams = this.previousLayoutParams;
      layoutParams.x -= i;
      layoutParams = this.previousLayoutParams;
      layoutParams.y -= j;
      AbsoluteLayout.ViewOffset viewOffset = this.previousOffset;
      viewOffset.curScrollX += i;
      viewOffset = this.previousOffset;
      viewOffset.curScrollY += j;
    }
  }
  
  final class LivePlayerListener extends TTLivePlayerListenerAdapter {
    public final void onError(ITTLivePlayer.LiveError param1LiveError, String param1String) {
      l.b(param1LiveError, "errorType");
      l.b(param1String, "errMsg");
      AppBrandLogger.i("LiveComponentContext", new Object[] { "onError" });
      int i = param1LiveError.getCode();
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("url", LiveComponentContext.this.mPlayingUrl);
      jSONObject.put("errorMsg", param1String);
      NetStateManager.NetworkType networkType = LiveComponentContext.this.mNetworkStatus;
      if (networkType != null) {
        Integer integer = Integer.valueOf(networkType.getValue());
      } else {
        networkType = null;
      } 
      jSONObject.put("networkStatus", networkType);
      AppBrandMonitor.statusRate("mp_live_player_error", i, jSONObject);
      LiveComponentContext.this.getComponent().onLivePlayerError();
    }
    
    public final void onMonitorLog(JSONObject param1JSONObject) {
      StringBuilder stringBuilder = new StringBuilder("onMonitorLog data:");
      stringBuilder.append(param1JSONObject);
      AppBrandLogger.i("LiveComponentContext", new Object[] { stringBuilder.toString() });
    }
    
    public final void onPlayStateChanged(ITTLivePlayer.PlayerState param1PlayerState) {
      l.b(param1PlayerState, "state");
      int i = LiveComponentContext$LivePlayerListener$WhenMappings.$EnumSwitchMapping$0[param1PlayerState.ordinal()];
      if (i != 1) {
        if (i != 2) {
          if (i != 3) {
            if (i != 4) {
              AppBrandLogger.i("LiveComponentContext", new Object[] { "onPlayStateChanged:", param1PlayerState });
              return;
            } 
            LiveComponentContext.this.getComponent().onLivePlayerStateChange(2006);
            return;
          } 
          LiveComponentContext.this.getComponent().onLivePlayerStateChange(2007);
          return;
        } 
        LiveComponentContext.this.getComponent().onLivePlayerStateChange(2007);
        return;
      } 
      LiveComponentContext.this.getComponent().onLivePlayerStateChange(2004);
    }
    
    public final void onVideoSizeChanged(int param1Int1, int param1Int2) {
      StringBuilder stringBuilder = new StringBuilder("onVideoSizeChanged videoWidth:");
      stringBuilder.append(param1Int1);
      stringBuilder.append(" videoHeight:");
      stringBuilder.append(param1Int2);
      AppBrandLogger.i("LiveComponentContext", new Object[] { stringBuilder.toString() });
      if (LiveComponentContext.this.mTextureView instanceof LivePlayerTextureView) {
        TextureView textureView = LiveComponentContext.this.mTextureView;
        if (textureView != null) {
          ((LivePlayerTextureView)textureView).onVideoSizeChanged(param1Int1, param1Int2);
        } else {
          throw new u("null cannot be cast to non-null type com.tt.miniapp.component.nativeview.liveplayer.view.LivePlayerTextureView");
        } 
      } 
      LiveComponentContext.this.getComponent().onLivePlayerStateChange(2009);
    }
  }
  
  final class LivePlayerTextureListener implements TextureView.SurfaceTextureListener {
    public final void onSurfaceTextureAvailable(SurfaceTexture param1SurfaceTexture, int param1Int1, int param1Int2) {
      // Byte code:
      //   0: new java/lang/StringBuilder
      //   3: dup
      //   4: ldc 'onSurfaceTextureAvailable width:'
      //   6: invokespecial <init> : (Ljava/lang/String;)V
      //   9: astore #4
      //   11: aload #4
      //   13: iload_2
      //   14: invokevirtual append : (I)Ljava/lang/StringBuilder;
      //   17: pop
      //   18: aload #4
      //   20: ldc ' height:'
      //   22: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   25: pop
      //   26: aload #4
      //   28: iload_3
      //   29: invokevirtual append : (I)Ljava/lang/StringBuilder;
      //   32: pop
      //   33: ldc 'LiveComponentContext'
      //   35: iconst_1
      //   36: anewarray java/lang/Object
      //   39: dup
      //   40: iconst_0
      //   41: aload #4
      //   43: invokevirtual toString : ()Ljava/lang/String;
      //   46: aastore
      //   47: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
      //   50: aload_0
      //   51: getfield this$0 : Lcom/tt/miniapp/component/nativeview/liveplayer/context/LiveComponentContext;
      //   54: getfield mCacheSurface : Lcom/tt/miniapp/component/nativeview/liveplayer/context/surface/SurfaceHolder;
      //   57: ifnull -> 109
      //   60: aload_0
      //   61: getfield this$0 : Lcom/tt/miniapp/component/nativeview/liveplayer/context/LiveComponentContext;
      //   64: getfield mCacheSurface : Lcom/tt/miniapp/component/nativeview/liveplayer/context/surface/SurfaceHolder;
      //   67: astore #4
      //   69: aload #4
      //   71: ifnonnull -> 77
      //   74: invokestatic a : ()V
      //   77: aload #4
      //   79: invokevirtual available : ()Z
      //   82: ifne -> 88
      //   85: goto -> 109
      //   88: aload_0
      //   89: getfield this$0 : Lcom/tt/miniapp/component/nativeview/liveplayer/context/LiveComponentContext;
      //   92: getfield mTextureView : Landroid/view/TextureView;
      //   95: astore #4
      //   97: aload #4
      //   99: ifnull -> 192
      //   102: aload #4
      //   104: aload_1
      //   105: invokevirtual setSurfaceTexture : (Landroid/graphics/SurfaceTexture;)V
      //   108: return
      //   109: aload_0
      //   110: getfield this$0 : Lcom/tt/miniapp/component/nativeview/liveplayer/context/LiveComponentContext;
      //   113: getfield mCacheSurface : Lcom/tt/miniapp/component/nativeview/liveplayer/context/surface/SurfaceHolder;
      //   116: astore #4
      //   118: aload #4
      //   120: ifnull -> 129
      //   123: aload #4
      //   125: invokevirtual release : ()Z
      //   128: pop
      //   129: aload_1
      //   130: ifnull -> 192
      //   133: aload_0
      //   134: getfield this$0 : Lcom/tt/miniapp/component/nativeview/liveplayer/context/LiveComponentContext;
      //   137: new com/tt/miniapp/component/nativeview/liveplayer/context/surface/SurfaceHolder
      //   140: dup
      //   141: aload_1
      //   142: new android/view/Surface
      //   145: dup
      //   146: aload_1
      //   147: invokespecial <init> : (Landroid/graphics/SurfaceTexture;)V
      //   150: invokespecial <init> : (Landroid/graphics/SurfaceTexture;Landroid/view/Surface;)V
      //   153: putfield mCacheSurface : Lcom/tt/miniapp/component/nativeview/liveplayer/context/surface/SurfaceHolder;
      //   156: aload_0
      //   157: getfield this$0 : Lcom/tt/miniapp/component/nativeview/liveplayer/context/LiveComponentContext;
      //   160: invokevirtual getLivePlayer : ()Lcom/tt/miniapp/liveplayer/ITTLivePlayer;
      //   163: astore_1
      //   164: aload_0
      //   165: getfield this$0 : Lcom/tt/miniapp/component/nativeview/liveplayer/context/LiveComponentContext;
      //   168: getfield mCacheSurface : Lcom/tt/miniapp/component/nativeview/liveplayer/context/surface/SurfaceHolder;
      //   171: astore #4
      //   173: aload #4
      //   175: ifnonnull -> 181
      //   178: invokestatic a : ()V
      //   181: aload_1
      //   182: aload #4
      //   184: invokevirtual getSurface : ()Landroid/view/Surface;
      //   187: invokeinterface setSurface : (Landroid/view/Surface;)V
      //   192: return
    }
    
    public final boolean onSurfaceTextureDestroyed(SurfaceTexture param1SurfaceTexture) {
      AppBrandLogger.d("LiveComponentContext", new Object[] { "onSurfaceTextureDestroyed" });
      TextureView textureView = LiveComponentContext.this.mTextureView;
      if (textureView != null)
        textureView.setKeepScreenOn(false); 
      if (LiveComponentContext.this.mCacheSurface != null) {
        SurfaceHolder surfaceHolder = LiveComponentContext.this.mCacheSurface;
        if (surfaceHolder == null)
          l.a(); 
        return surfaceHolder.release();
      } 
      return true;
    }
    
    public final void onSurfaceTextureSizeChanged(SurfaceTexture param1SurfaceTexture, int param1Int1, int param1Int2) {
      StringBuilder stringBuilder = new StringBuilder("onSurfaceTextureSizeChanged width:");
      stringBuilder.append(param1Int1);
      stringBuilder.append(" height:");
      stringBuilder.append(param1Int2);
      AppBrandLogger.d("LiveComponentContext", new Object[] { stringBuilder.toString() });
      LiveComponentContext.this.getLivePlayer().setSurface(new Surface(param1SurfaceTexture));
    }
    
    public final void onSurfaceTextureUpdated(SurfaceTexture param1SurfaceTexture) {}
  }
  
  public static final class LiveComponentContext$applyFullScreen$1 implements ViewTreeObserver.OnGlobalLayoutListener {
    LiveComponentContext$applyFullScreen$1(a.a param1a, int param1Int, ViewTreeObserver param1ViewTreeObserver) {}
    
    public final void onGlobalLayout() {
      LivePlayerUtil livePlayerUtil = LivePlayerUtil.INSTANCE;
      Context context = LiveComponentContext.this.getComponent().getContext();
      l.a(context, "component.context");
      if (livePlayerUtil.isExpectScreenOrientation(context, this.$direction)) {
        AbsoluteLayout.LayoutParams layoutParams = new AbsoluteLayout.LayoutParams(new ViewGroup.LayoutParams(LiveComponentContext.this.getRender().getRenderWidth(), LiveComponentContext.this.getRender().getRenderHeight()));
        layoutParams.x = 0;
        layoutParams.y = 0;
        layoutParams.zIndex = this.$zIndex;
        layoutParams.isFullScreen = true;
        LiveComponentContext.this.getComponent().setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        this.$viewTreeObserver.removeOnGlobalLayoutListener(this);
      } 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\liveplayer\context\LiveComponentContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */