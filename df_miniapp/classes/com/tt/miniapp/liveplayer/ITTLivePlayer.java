package com.tt.miniapp.liveplayer;

import android.graphics.Point;
import android.view.Surface;
import com.tt.miniapp.liveplayer.listener.ITTLivePlayerListener;
import org.json.JSONObject;

public interface ITTLivePlayer {
  PlayerState getPlayState();
  
  Point getVideoSize();
  
  boolean isPlaying();
  
  void muted(boolean paramBoolean);
  
  void pause();
  
  void play();
  
  void release();
  
  void reset();
  
  void setDataSource(String paramString, JSONObject paramJSONObject);
  
  void setListener(ITTLivePlayerListener paramITTLivePlayerListener);
  
  void setSurface(Surface paramSurface);
  
  void stop();
  
  public static final class DisplayMode {
    private final ITTLivePlayer.Orientation displayOrientation;
    
    private final ITTLivePlayer.ObjectFit objectFit;
    
    public DisplayMode(ITTLivePlayer.ObjectFit param1ObjectFit, ITTLivePlayer.Orientation param1Orientation) {
      this.objectFit = param1ObjectFit;
      this.displayOrientation = param1Orientation;
    }
    
    public final ITTLivePlayer.Orientation getDisplayOrientation() {
      return this.displayOrientation;
    }
    
    public final ITTLivePlayer.ObjectFit getObjectFit() {
      return this.objectFit;
    }
  }
  
  public enum LiveError {
    ERROR_H265_URL_IS_NULL, ERROR_INTERNAL, ERROR_NETWORK, ERROR_PARAM, ERROR_SEI_UPLOAD_TIME_OUT, ERROR_SERVER;
    
    private final int code;
    
    static {
      LiveError liveError1 = new LiveError("ERROR_PARAM", 0, 1);
      ERROR_PARAM = liveError1;
      LiveError liveError2 = new LiveError("ERROR_SERVER", 1, 2);
      ERROR_SERVER = liveError2;
      LiveError liveError3 = new LiveError("ERROR_INTERNAL", 2, 3);
      ERROR_INTERNAL = liveError3;
      LiveError liveError4 = new LiveError("ERROR_NETWORK", 3, 4);
      ERROR_NETWORK = liveError4;
      LiveError liveError5 = new LiveError("ERROR_SEI_UPLOAD_TIME_OUT", 4, 5);
      ERROR_SEI_UPLOAD_TIME_OUT = liveError5;
      LiveError liveError6 = new LiveError("ERROR_H265_URL_IS_NULL", 5, 6);
      ERROR_H265_URL_IS_NULL = liveError6;
      $VALUES = new LiveError[] { liveError1, liveError2, liveError3, liveError4, liveError5, liveError6 };
    }
    
    LiveError(int param1Int1) {
      this.code = param1Int1;
    }
    
    public final int getCode() {
      return this.code;
    }
  }
  
  public enum ObjectFit {
    CONTAIN, FILLCROP;
    
    private final String value;
    
    static {
      ObjectFit objectFit1 = new ObjectFit("CONTAIN", 0, "contain");
      CONTAIN = objectFit1;
      ObjectFit objectFit2 = new ObjectFit("FILLCROP", 1, "fillCrop");
      FILLCROP = objectFit2;
      $VALUES = new ObjectFit[] { objectFit1, objectFit2 };
    }
    
    ObjectFit(String param1String1) {
      this.value = param1String1;
    }
    
    public final String getValue() {
      return this.value;
    }
  }
  
  public enum Orientation {
    HORIZONTAL, VERTICAL;
    
    private final String value;
    
    static {
      Orientation orientation1 = new Orientation("VERTICAL", 0, "vertical");
      VERTICAL = orientation1;
      Orientation orientation2 = new Orientation("HORIZONTAL", 1, "horizontal");
      HORIZONTAL = orientation2;
      $VALUES = new Orientation[] { orientation1, orientation2 };
    }
    
    Orientation(String param1String1) {
      this.value = param1String1;
    }
    
    public final String getValue() {
      return this.value;
    }
  }
  
  public enum PlayerState {
    COMPLETED, ERROR, FETCHING, INIT, PLAYING, PREPARED, STALLING, STOPPED;
    
    static {
      PlayerState playerState1 = new PlayerState("INIT", 0);
      INIT = playerState1;
      PlayerState playerState2 = new PlayerState("FETCHING", 1);
      FETCHING = playerState2;
      PlayerState playerState3 = new PlayerState("PREPARED", 2);
      PREPARED = playerState3;
      PlayerState playerState4 = new PlayerState("PLAYING", 3);
      PLAYING = playerState4;
      PlayerState playerState5 = new PlayerState("STALLING", 4);
      STALLING = playerState5;
      PlayerState playerState6 = new PlayerState("STOPPED", 5);
      STOPPED = playerState6;
      PlayerState playerState7 = new PlayerState("COMPLETED", 6);
      COMPLETED = playerState7;
      PlayerState playerState8 = new PlayerState("ERROR", 7);
      ERROR = playerState8;
      $VALUES = new PlayerState[] { playerState1, playerState2, playerState3, playerState4, playerState5, playerState6, playerState7, playerState8 };
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\liveplayer\ITTLivePlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */