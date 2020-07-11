package com.yqritc.scalablevideoview;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.TextureView;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Map;

public class ScalableVideoView extends TextureView implements MediaPlayer.OnVideoSizeChangedListener, TextureView.SurfaceTextureListener {
  public MediaPlayer m;
  
  protected b n = b.NONE;
  
  public ScalableVideoView(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public ScalableVideoView(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public ScalableVideoView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    if (paramAttributeSet == null)
      return; 
    TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, new int[] { 1979842560 }, 0, 0);
    if (typedArray == null)
      return; 
    paramInt = typedArray.getInt(0, b.NONE.ordinal());
    typedArray.recycle();
    this.n = b.values()[paramInt];
  }
  
  private void a(int paramInt1, int paramInt2) {
    if (paramInt1 != 0) {
      if (paramInt2 == 0)
        return; 
      Matrix matrix = (new c(new d(getWidth(), getHeight()), new d(paramInt1, paramInt2))).a(this.n);
      if (matrix != null)
        setTransform(matrix); 
    } 
  }
  
  private void b() {
    if (this.m == null) {
      this.m = new MediaPlayer();
      this.m.setOnVideoSizeChangedListener(this);
      setSurfaceTextureListener(this);
      return;
    } 
    c();
  }
  
  private void c() {
    this.m.reset();
  }
  
  private void setDataSource(AssetFileDescriptor paramAssetFileDescriptor) throws IOException {
    a(paramAssetFileDescriptor.getFileDescriptor(), paramAssetFileDescriptor.getStartOffset(), paramAssetFileDescriptor.getLength());
    paramAssetFileDescriptor.close();
  }
  
  public final void a() {
    c();
    this.m.release();
    this.m = null;
  }
  
  public final void a(float paramFloat1, float paramFloat2) {
    this.m.setVolume(paramFloat1, paramFloat2);
  }
  
  public final void a(Context paramContext, Uri paramUri) throws IOException {
    b();
    this.m.setDataSource(paramContext, paramUri);
  }
  
  public final void a(Context paramContext, Uri paramUri, Map<String, String> paramMap) throws IOException {
    b();
    this.m.setDataSource(paramContext, paramUri, paramMap);
  }
  
  public final void a(MediaPlayer.OnPreparedListener paramOnPreparedListener) throws IllegalStateException {
    this.m.setOnPreparedListener(paramOnPreparedListener);
    this.m.prepareAsync();
  }
  
  public final void a(FileDescriptor paramFileDescriptor, long paramLong1, long paramLong2) throws IOException {
    b();
    this.m.setDataSource(paramFileDescriptor, paramLong1, paramLong2);
  }
  
  public int getCurrentPosition() {
    return this.m.getCurrentPosition();
  }
  
  public int getDuration() {
    return this.m.getDuration();
  }
  
  public int getVideoHeight() {
    return this.m.getVideoHeight();
  }
  
  public int getVideoWidth() {
    return this.m.getVideoWidth();
  }
  
  public boolean isPlaying() {
    return this.m.isPlaying();
  }
  
  public void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    if (this.m == null)
      return; 
    if (isPlaying())
      this.m.stop(); 
    a();
  }
  
  public void onSurfaceTextureAvailable(SurfaceTexture paramSurfaceTexture, int paramInt1, int paramInt2) {
    Surface surface = new Surface(paramSurfaceTexture);
    MediaPlayer mediaPlayer = this.m;
    if (mediaPlayer != null)
      mediaPlayer.setSurface(surface); 
  }
  
  public boolean onSurfaceTextureDestroyed(SurfaceTexture paramSurfaceTexture) {
    return false;
  }
  
  public void onSurfaceTextureSizeChanged(SurfaceTexture paramSurfaceTexture, int paramInt1, int paramInt2) {}
  
  public void onSurfaceTextureUpdated(SurfaceTexture paramSurfaceTexture) {}
  
  public void onVideoSizeChanged(MediaPlayer paramMediaPlayer, int paramInt1, int paramInt2) {
    a(paramInt1, paramInt2);
  }
  
  public void pause() {
    this.m.pause();
  }
  
  public void seekTo(int paramInt) {
    this.m.seekTo(paramInt);
  }
  
  public void setAssetData(String paramString) throws IOException {
    setDataSource(getContext().getAssets().openFd(paramString));
  }
  
  public void setDataSource(FileDescriptor paramFileDescriptor) throws IOException {
    b();
    this.m.setDataSource(paramFileDescriptor);
  }
  
  public void setDataSource(String paramString) throws IOException {
    b();
    this.m.setDataSource(paramString);
  }
  
  public void setLooping(boolean paramBoolean) {
    this.m.setLooping(paramBoolean);
  }
  
  public void setOnCompletionListener(MediaPlayer.OnCompletionListener paramOnCompletionListener) {
    this.m.setOnCompletionListener(paramOnCompletionListener);
  }
  
  public void setOnErrorListener(MediaPlayer.OnErrorListener paramOnErrorListener) {
    this.m.setOnErrorListener(paramOnErrorListener);
  }
  
  public void setOnInfoListener(MediaPlayer.OnInfoListener paramOnInfoListener) {
    this.m.setOnInfoListener(paramOnInfoListener);
  }
  
  public void setRawData(int paramInt) throws IOException {
    setDataSource(getResources().openRawResourceFd(paramInt));
  }
  
  public void setScalableType(b paramb) {
    this.n = paramb;
    a(getVideoWidth(), getVideoHeight());
  }
  
  public void start() {
    this.m.start();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\yqritc\scalablevideoview\ScalableVideoView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */