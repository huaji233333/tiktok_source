package com.tt.miniapp.liveplayer.listener.adapter;

import com.tt.miniapp.liveplayer.ITTLivePlayer;
import com.tt.miniapp.liveplayer.listener.ITTLivePlayerListener;
import d.f.b.l;
import org.json.JSONObject;

public class TTLivePlayerListenerAdapter implements ITTLivePlayerListener {
  public void onCompletion() {}
  
  public void onError(ITTLivePlayer.LiveError paramLiveError, String paramString) {
    l.b(paramLiveError, "errorType");
    l.b(paramString, "errMsg");
  }
  
  public void onFirstFrame(boolean paramBoolean) {}
  
  public void onLivePlayerPrepared() {}
  
  public void onMonitorLog(JSONObject paramJSONObject) {}
  
  public void onPlayStateChanged(ITTLivePlayer.PlayerState paramPlayerState) {
    l.b(paramPlayerState, "state");
  }
  
  public void onResolutionDegrade(String paramString) {}
  
  public void onSeiUpdate(String paramString) {}
  
  public void onStallEnd() {}
  
  public void onStallStart() {}
  
  public void onVideoSizeChanged(int paramInt1, int paramInt2) {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\liveplayer\listener\adapter\TTLivePlayerListenerAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */