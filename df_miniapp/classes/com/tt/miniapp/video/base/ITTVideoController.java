package com.tt.miniapp.video.base;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.tt.miniapp.video.view.CoreVideoView;
import java.util.Objects;

public interface ITTVideoController {
  int getCurrentPosition();
  
  int getDuration();
  
  int getPlayDuration();
  
  CoreVideoView getVideoView();
  
  boolean isFullScreen();
  
  boolean isVideoLooping();
  
  boolean isVideoPaused();
  
  boolean isVideoPausedByAudioFocus();
  
  boolean isVideoPlaybackCompleted();
  
  boolean isVideoPlaying();
  
  boolean isVideoReleased();
  
  boolean isVideoStarted();
  
  void pauseVideo();
  
  void play(PlayerEntity paramPlayerEntity);
  
  void releaseMedia();
  
  void retry();
  
  void seekTo(int paramInt);
  
  void setIsMute(boolean paramBoolean);
  
  void setLooping(boolean paramBoolean);
  
  void setVideoView(CoreVideoView paramCoreVideoView);
  
  void startVideo();
  
  void stopVideo();
  
  void updateShowState(ShowStateEntity paramShowStateEntity);
  
  public static class PlayerEntity {
    private long adId;
    
    private String authToken;
    
    private boolean autoPlay;
    
    private String category;
    
    private String decryptToken;
    
    private String definition;
    
    private String encodedKey;
    
    private int height;
    
    private long itemId;
    
    private String localUrl;
    
    private boolean mLoop;
    
    private int playApiVersion;
    
    private String poster;
    
    private String resolution;
    
    private int sp;
    
    private int startPosition = -1;
    
    private String title;
    
    private String vidDataSourceUrl;
    
    private String videoId;
    
    private String videoModelJsonStr;
    
    private String videoUrl;
    
    private int width;
    
    public boolean equals(Object param1Object) {
      if (this == param1Object)
        return true; 
      if (param1Object != null) {
        if (getClass() != param1Object.getClass())
          return false; 
        param1Object = param1Object;
        if (this.playApiVersion == ((PlayerEntity)param1Object).playApiVersion && Objects.equals(this.videoId, ((PlayerEntity)param1Object).videoId) && Objects.equals(this.decryptToken, ((PlayerEntity)param1Object).decryptToken) && Objects.equals(this.encodedKey, ((PlayerEntity)param1Object).encodedKey) && Objects.equals(this.authToken, ((PlayerEntity)param1Object).authToken) && Objects.equals(this.vidDataSourceUrl, ((PlayerEntity)param1Object).vidDataSourceUrl) && Objects.equals(this.videoUrl, ((PlayerEntity)param1Object).videoUrl) && Objects.equals(this.localUrl, ((PlayerEntity)param1Object).localUrl) && Objects.equals(this.definition, ((PlayerEntity)param1Object).definition) && Objects.equals(this.videoModelJsonStr, ((PlayerEntity)param1Object).videoModelJsonStr))
          return true; 
      } 
      return false;
    }
    
    public long getAdId() {
      return this.adId;
    }
    
    public String getAuthToken() {
      return this.authToken;
    }
    
    public boolean getAutoPlay() {
      return this.autoPlay;
    }
    
    public String getCategory() {
      return this.category;
    }
    
    public String getDecryptToken() {
      return this.decryptToken;
    }
    
    public String getDefinition() {
      return this.definition;
    }
    
    public String getEncodedKey() {
      return this.encodedKey;
    }
    
    public int getHeight() {
      return this.height;
    }
    
    public long getItemId() {
      return this.itemId;
    }
    
    public String getLocalUrl() {
      return this.localUrl;
    }
    
    public int getPlayApiVersion() {
      return this.playApiVersion;
    }
    
    public String getPoster() {
      return this.poster;
    }
    
    public String getResolution() {
      return this.resolution;
    }
    
    public int getSp() {
      return this.sp;
    }
    
    public int getStartPosition() {
      return this.startPosition;
    }
    
    public String getTitle() {
      return this.title;
    }
    
    public String getVidDataSourceUrl() {
      return this.vidDataSourceUrl;
    }
    
    public String getVideoId() {
      return this.videoId;
    }
    
    public String getVideoModelJsonStr() {
      return this.videoModelJsonStr;
    }
    
    public String getVideoUrl() {
      return this.videoUrl;
    }
    
    public int getWidth() {
      return this.width;
    }
    
    public int hashCode() {
      return Objects.hash(new Object[] { this.videoId, this.decryptToken, this.encodedKey, this.authToken, Integer.valueOf(this.playApiVersion), this.vidDataSourceUrl, this.videoUrl, this.localUrl, this.definition, this.videoModelJsonStr });
    }
    
    public boolean isLoop() {
      return this.mLoop;
    }
    
    public PlayerEntity setAdId(long param1Long) {
      this.adId = param1Long;
      return this;
    }
    
    public PlayerEntity setAuthToken(String param1String) {
      this.authToken = param1String;
      return this;
    }
    
    public PlayerEntity setAutoPlay(boolean param1Boolean) {
      this.autoPlay = param1Boolean;
      return this;
    }
    
    public PlayerEntity setCategory(String param1String) {
      this.category = param1String;
      return this;
    }
    
    public PlayerEntity setDecryptToken(String param1String) {
      this.decryptToken = param1String;
      return this;
    }
    
    public PlayerEntity setDefinition(String param1String) {
      this.definition = param1String;
      return this;
    }
    
    public PlayerEntity setEncodedKey(String param1String) {
      this.encodedKey = param1String;
      return this;
    }
    
    public PlayerEntity setHeight(int param1Int) {
      this.height = param1Int;
      return this;
    }
    
    public PlayerEntity setItemId(long param1Long) {
      this.itemId = param1Long;
      return this;
    }
    
    public PlayerEntity setLocalUrl(String param1String) {
      this.localUrl = param1String;
      return this;
    }
    
    public PlayerEntity setLoop(boolean param1Boolean) {
      this.mLoop = param1Boolean;
      return this;
    }
    
    public PlayerEntity setPlayApiVersion(int param1Int) {
      this.playApiVersion = param1Int;
      return this;
    }
    
    public PlayerEntity setPoster(String param1String) {
      this.poster = param1String;
      return this;
    }
    
    public PlayerEntity setResolution(String param1String) {
      this.resolution = param1String;
      return this;
    }
    
    public PlayerEntity setSp(int param1Int) {
      this.sp = param1Int;
      return this;
    }
    
    public PlayerEntity setStartPosition(int param1Int) {
      this.startPosition = param1Int;
      return this;
    }
    
    public PlayerEntity setTitle(String param1String) {
      this.title = param1String;
      return this;
    }
    
    public PlayerEntity setVidDataSourceUrl(String param1String) {
      this.vidDataSourceUrl = param1String;
      return this;
    }
    
    public PlayerEntity setVideoId(String param1String) {
      this.videoId = param1String;
      return this;
    }
    
    public PlayerEntity setVideoModelJsonStr(String param1String) {
      this.videoModelJsonStr = param1String;
      return this;
    }
    
    public PlayerEntity setVideoUrl(String param1String) {
      this.videoUrl = param1String;
      return this;
    }
    
    public PlayerEntity setWidth(int param1Int) {
      this.width = param1Int;
      return this;
    }
  }
  
  public static class ShowStateEntity implements Parcelable {
    public static final Parcelable.Creator<ShowStateEntity> CREATOR = new Parcelable.Creator<ShowStateEntity>() {
        public final ITTVideoController.ShowStateEntity createFromParcel(Parcel param2Parcel) {
          return new ITTVideoController.ShowStateEntity(param2Parcel);
        }
        
        public final ITTVideoController.ShowStateEntity[] newArray(int param2Int) {
          return new ITTVideoController.ShowStateEntity[param2Int];
        }
      };
    
    private boolean mControls;
    
    private String mObjectFit;
    
    private String mPlayBtnPosition;
    
    private boolean mShowFullScreenBtn;
    
    private boolean mShowPlayBtn;
    
    public ShowStateEntity() {}
    
    protected ShowStateEntity(Parcel param1Parcel) {
      boolean bool1;
      byte b = param1Parcel.readByte();
      boolean bool2 = true;
      if (b != 0) {
        bool1 = true;
      } else {
        bool1 = false;
      } 
      this.mControls = bool1;
      if (param1Parcel.readByte() != 0) {
        bool1 = true;
      } else {
        bool1 = false;
      } 
      this.mShowFullScreenBtn = bool1;
      if (param1Parcel.readByte() != 0) {
        bool1 = bool2;
      } else {
        bool1 = false;
      } 
      this.mShowPlayBtn = bool1;
      this.mObjectFit = param1Parcel.readString();
      this.mPlayBtnPosition = param1Parcel.readString();
    }
    
    public int describeContents() {
      return 0;
    }
    
    public String getObjectFit() {
      return this.mObjectFit;
    }
    
    public boolean isControls() {
      return this.mControls;
    }
    
    public ShowStateEntity setControls(boolean param1Boolean) {
      this.mControls = param1Boolean;
      return this;
    }
    
    public ShowStateEntity setObjectFit(String param1String) {
      this.mObjectFit = param1String;
      return this;
    }
    
    public ShowStateEntity setPlayBtnPosition(String param1String) {
      this.mPlayBtnPosition = param1String;
      return this;
    }
    
    public ShowStateEntity setShowFullScreenBtn(boolean param1Boolean) {
      this.mShowFullScreenBtn = param1Boolean;
      return this;
    }
    
    public ShowStateEntity setShowPlayBtn(boolean param1Boolean) {
      this.mShowPlayBtn = param1Boolean;
      return this;
    }
    
    public boolean shouldShowBottomPlayBtn() {
      return (this.mControls && this.mShowPlayBtn && TextUtils.equals(this.mPlayBtnPosition, "bottom"));
    }
    
    public boolean shouldShowCenterPlayBtn() {
      return (this.mControls && this.mShowPlayBtn && TextUtils.equals(this.mPlayBtnPosition, "center"));
    }
    
    public boolean shouldShowFullScreenBtn() {
      return (this.mControls && this.mShowFullScreenBtn);
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      param1Parcel.writeByte((byte)this.mControls);
      param1Parcel.writeByte((byte)this.mShowFullScreenBtn);
      param1Parcel.writeByte((byte)this.mShowPlayBtn);
      param1Parcel.writeString(this.mObjectFit);
      param1Parcel.writeString(this.mPlayBtnPosition);
    }
  }
  
  static final class null implements Parcelable.Creator<ShowStateEntity> {
    public final ITTVideoController.ShowStateEntity createFromParcel(Parcel param1Parcel) {
      return new ITTVideoController.ShowStateEntity(param1Parcel);
    }
    
    public final ITTVideoController.ShowStateEntity[] newArray(int param1Int) {
      return new ITTVideoController.ShowStateEntity[param1Int];
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\video\base\ITTVideoController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */