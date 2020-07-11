package com.tt.miniapphost.liveplayer;

import android.graphics.Point;
import android.view.Surface;
import com.ss.f.a.a;
import com.ss.f.a.a.a;
import com.ss.f.a.c.c;
import com.ss.f.a.e;
import com.tt.miniapp.liveplayer.ITTLivePlayer;
import com.tt.miniapp.liveplayer.listener.ITTLivePlayerListener;
import com.tt.miniapphost.AppBrandLogger;
import d.f;
import d.f.a.a;
import d.f.b.l;
import d.f.b.m;
import d.f.b.u;
import d.f.b.v;
import d.f.b.x;
import d.g;
import d.k.d;
import d.k.h;
import org.json.JSONObject;

public final class BDPLivePlayer implements ITTLivePlayer {
  public final String TAG;
  
  public final BDPLivePlayerBuilder livePlayerBuilder;
  
  public ITTLivePlayerListener mListener;
  
  private final f mPlayer$delegate;
  
  private volatile ITTLivePlayer.PlayerState mState;
  
  public BDPLivePlayer(BDPLivePlayerBuilder paramBDPLivePlayerBuilder) {
    this.livePlayerBuilder = paramBDPLivePlayerBuilder;
    this.TAG = "DBPLivePlayer";
    this.mState = ITTLivePlayer.PlayerState.INIT;
    this.mPlayer$delegate = g.a(new BDPLivePlayer$mPlayer$2());
  }
  
  private final e getMPlayer() {
    return (e)this.mPlayer$delegate.getValue();
  }
  
  public final ITTLivePlayer.PlayerState getPlayState() {
    return this.mState;
  }
  
  public final Point getVideoSize() {
    return new Point(getMPlayer().k(), getMPlayer().l());
  }
  
  public final boolean isPlaying() {
    ITTLivePlayer.PlayerState playerState = this.mState;
    int i = BDPLivePlayer$WhenMappings.$EnumSwitchMapping$0[playerState.ordinal()];
    return !(i != 1 && i != 2 && i != 3 && i != 4);
  }
  
  public final void muted(boolean paramBoolean) {
    getMPlayer().a(Boolean.valueOf(paramBoolean));
  }
  
  public final void pause() {
    if (this.mState == ITTLivePlayer.PlayerState.STOPPED)
      return; 
    getMPlayer().c();
    setState(ITTLivePlayer.PlayerState.STOPPED);
  }
  
  public final void play() {
    if (this.mState == ITTLivePlayer.PlayerState.PLAYING)
      return; 
    getMPlayer().b();
    setState(ITTLivePlayer.PlayerState.FETCHING);
  }
  
  public final void release() {
    if (getMPlayer().j())
      getMPlayer().c(); 
    getMPlayer().g();
  }
  
  public final void reset() {
    setState(ITTLivePlayer.PlayerState.INIT);
    getMPlayer().e();
  }
  
  public final void setDataSource(String paramString, JSONObject paramJSONObject) {
    l.b(paramString, "url");
    if (paramJSONObject != null) {
      String str = paramJSONObject.toString();
    } else {
      paramJSONObject = null;
    } 
    c c = new c(paramString, null, (String)paramJSONObject);
    getMPlayer().a(new c[] { c });
  }
  
  public final void setListener(ITTLivePlayerListener paramITTLivePlayerListener) {
    l.b(paramITTLivePlayerListener, "listener");
    this.mListener = paramITTLivePlayerListener;
  }
  
  public final void setState(ITTLivePlayer.PlayerState paramPlayerState) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_1
    //   4: putfield mState : Lcom/tt/miniapp/liveplayer/ITTLivePlayer$PlayerState;
    //   7: aload_0
    //   8: getfield mListener : Lcom/tt/miniapp/liveplayer/listener/ITTLivePlayerListener;
    //   11: astore_1
    //   12: aload_1
    //   13: ifnull -> 26
    //   16: aload_1
    //   17: aload_0
    //   18: getfield mState : Lcom/tt/miniapp/liveplayer/ITTLivePlayer$PlayerState;
    //   21: invokeinterface onPlayStateChanged : (Lcom/tt/miniapp/liveplayer/ITTLivePlayer$PlayerState;)V
    //   26: aload_0
    //   27: monitorexit
    //   28: return
    //   29: astore_1
    //   30: aload_0
    //   31: monitorexit
    //   32: aload_1
    //   33: athrow
    // Exception table:
    //   from	to	target	type
    //   2	12	29	finally
    //   16	26	29	finally
  }
  
  public final void setSurface(Surface paramSurface) {
    getMPlayer().a(paramSurface);
  }
  
  public final void stop() {
    if (this.mState == ITTLivePlayer.PlayerState.STOPPED)
      return; 
    getMPlayer().c();
    setState(ITTLivePlayer.PlayerState.STOPPED);
  }
  
  public final class LiveListenerWrapper implements a {
    private final ITTLivePlayer.LiveError parseError(a param1a) {
      if (param1a == null)
        return ITTLivePlayer.LiveError.ERROR_INTERNAL; 
      switch (param1a.code) {
        default:
          return ITTLivePlayer.LiveError.ERROR_INTERNAL;
        case -105:
        case -104:
          return ITTLivePlayer.LiveError.ERROR_SERVER;
        case -106:
        case -100:
          return ITTLivePlayer.LiveError.ERROR_PARAM;
        case -115:
        case -113:
        case -111:
        case -110:
        case -109:
        case -108:
        case -107:
          return ITTLivePlayer.LiveError.ERROR_NETWORK;
        case -116:
          return ITTLivePlayer.LiveError.ERROR_SEI_UPLOAD_TIME_OUT;
        case -117:
          break;
      } 
      return ITTLivePlayer.LiveError.ERROR_H265_URL_IS_NULL;
    }
    
    public final void onCacheFileCompletion() {
      AppBrandLogger.i(BDPLivePlayer.this.TAG, new Object[] { "onCacheFileCompletion" });
    }
    
    public final void onCompletion() {
      BDPLivePlayer.this.setState(ITTLivePlayer.PlayerState.COMPLETED);
      ITTLivePlayerListener iTTLivePlayerListener = BDPLivePlayer.this.mListener;
      if (iTTLivePlayerListener != null)
        iTTLivePlayerListener.onCompletion(); 
    }
    
    public final void onError(a param1a) {
      BDPLivePlayer.this.setState(ITTLivePlayer.PlayerState.ERROR);
      if (param1a != null) {
        String str = param1a.getInfoJSON();
        if (str != null) {
          ITTLivePlayerListener iTTLivePlayerListener = BDPLivePlayer.this.mListener;
          if (iTTLivePlayerListener != null)
            iTTLivePlayerListener.onError(parseError(param1a), str); 
        } 
      } 
    }
    
    public final void onFirstFrame(boolean param1Boolean) {
      BDPLivePlayer.this.setState(ITTLivePlayer.PlayerState.PLAYING);
      ITTLivePlayerListener iTTLivePlayerListener = BDPLivePlayer.this.mListener;
      if (iTTLivePlayerListener != null)
        iTTLivePlayerListener.onFirstFrame(param1Boolean); 
    }
    
    public final void onLiveStateResponse(int param1Int) {
      AppBrandLogger.i(BDPLivePlayer.this.TAG, new Object[] { "onLiveStateResponse", Integer.valueOf(param1Int) });
    }
    
    public final void onMonitorLog(JSONObject param1JSONObject) {
      ITTLivePlayerListener iTTLivePlayerListener = BDPLivePlayer.this.mListener;
      if (iTTLivePlayerListener != null)
        iTTLivePlayerListener.onMonitorLog(param1JSONObject); 
    }
    
    public final void onPrepared() {
      BDPLivePlayer.this.setState(ITTLivePlayer.PlayerState.PREPARED);
      ITTLivePlayerListener iTTLivePlayerListener = BDPLivePlayer.this.mListener;
      if (iTTLivePlayerListener != null)
        iTTLivePlayerListener.onLivePlayerPrepared(); 
    }
    
    public final void onResolutionDegrade(String param1String) {
      ITTLivePlayerListener iTTLivePlayerListener = BDPLivePlayer.this.mListener;
      if (iTTLivePlayerListener != null)
        iTTLivePlayerListener.onResolutionDegrade(param1String); 
    }
    
    public final void onSeiUpdate(String param1String) {
      ITTLivePlayerListener iTTLivePlayerListener = BDPLivePlayer.this.mListener;
      if (iTTLivePlayerListener != null)
        iTTLivePlayerListener.onSeiUpdate(param1String); 
    }
    
    public final void onStallEnd() {
      BDPLivePlayer.this.setState(ITTLivePlayer.PlayerState.PLAYING);
      ITTLivePlayerListener iTTLivePlayerListener = BDPLivePlayer.this.mListener;
      if (iTTLivePlayerListener != null)
        iTTLivePlayerListener.onStallEnd(); 
    }
    
    public final void onStallStart() {
      BDPLivePlayer.this.setState(ITTLivePlayer.PlayerState.STALLING);
      ITTLivePlayerListener iTTLivePlayerListener = BDPLivePlayer.this.mListener;
      if (iTTLivePlayerListener != null)
        iTTLivePlayerListener.onStallStart(); 
    }
    
    public final void onVideoSizeChanged(int param1Int1, int param1Int2) {
      ITTLivePlayerListener iTTLivePlayerListener = BDPLivePlayer.this.mListener;
      if (iTTLivePlayerListener != null)
        iTTLivePlayerListener.onVideoSizeChanged(param1Int1, param1Int2); 
    }
  }
  
  static final class BDPLivePlayer$mPlayer$2 extends m implements a<e> {
    BDPLivePlayer$mPlayer$2() {
      super(0);
    }
    
    public final e invoke() {
      return BDPLivePlayer.this.livePlayerBuilder.getRealPlayer(new BDPLivePlayer.LiveListenerWrapper());
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\liveplayer\BDPLivePlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */