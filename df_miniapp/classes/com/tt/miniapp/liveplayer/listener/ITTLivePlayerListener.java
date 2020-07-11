package com.tt.miniapp.liveplayer.listener;

import com.tt.miniapp.liveplayer.ITTLivePlayer;
import org.json.JSONObject;

public interface ITTLivePlayerListener {
  void onCompletion();
  
  void onError(ITTLivePlayer.LiveError paramLiveError, String paramString);
  
  void onFirstFrame(boolean paramBoolean);
  
  void onLivePlayerPrepared();
  
  void onMonitorLog(JSONObject paramJSONObject);
  
  void onPlayStateChanged(ITTLivePlayer.PlayerState paramPlayerState);
  
  void onResolutionDegrade(String paramString);
  
  void onSeiUpdate(String paramString);
  
  void onStallEnd();
  
  void onStallStart();
  
  void onVideoSizeChanged(int paramInt1, int paramInt2);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\liveplayer\listener\ITTLivePlayerListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */