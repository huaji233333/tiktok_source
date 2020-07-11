package com.tt.miniapp.component.nativeview.liveplayer.context;

import android.view.TextureView;
import android.view.View;
import com.bytedance.sandboxapp.protocol.service.j.a;
import com.tt.miniapp.liveplayer.ITTLivePlayer;
import org.json.JSONObject;

public interface ILivePlayerContext {
  void bindSurface(TextureView paramTextureView);
  
  void exitFullScreen(View paramView);
  
  boolean isFullScreen();
  
  boolean isPlaying();
  
  void onPause();
  
  void onResume();
  
  void pause();
  
  void play();
  
  void release();
  
  void requestFullScreen(View paramView, a.a parama, JSONObject paramJSONObject);
  
  void setDisplayMode(ITTLivePlayer.DisplayMode paramDisplayMode);
  
  void setMuted(boolean paramBoolean);
  
  void setPlayUrl(String paramString);
  
  void setPlayUrl(String paramString, JSONObject paramJSONObject);
  
  void stop();
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\liveplayer\context\ILivePlayerContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */