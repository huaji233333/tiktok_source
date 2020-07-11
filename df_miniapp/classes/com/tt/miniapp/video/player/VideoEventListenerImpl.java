package com.tt.miniapp.video.player;

import com.ss.ttvideoengine.log.VideoEventManager;
import com.storage.async.Action;
import com.storage.async.Schedulers;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import org.json.JSONArray;
import org.json.JSONObject;

public class VideoEventListenerImpl implements VideoEventListenerCompat {
  private VideoEventListenerImpl() {}
  
  public static VideoEventListenerImpl getInstance() {
    return LazyHolder.INSTANCE;
  }
  
  public void onEvent() {
    AppBrandLogger.d("VideoEventListenerImpl", new Object[] { "onEvent" });
    final JSONArray mVideoEvents = VideoEventManager.instance.popAllEvents();
    if (jSONArray != null) {
      if (jSONArray.length() <= 0)
        return; 
      ThreadUtil.runOnWorkThread(new Action() {
            public void act() {
              for (int i = 0; i < mVideoEvents.length(); i++) {
                try {
                  JSONObject jSONObject = mVideoEvents.getJSONObject(i);
                } finally {
                  Exception exception = null;
                } 
              } 
            }
          },  Schedulers.longIO());
    } 
  }
  
  public void onEventV2(String paramString) {}
  
  static class LazyHolder {
    static VideoEventListenerImpl INSTANCE = new VideoEventListenerImpl();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\video\player\VideoEventListenerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */