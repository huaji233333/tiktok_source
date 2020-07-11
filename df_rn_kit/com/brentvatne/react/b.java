package com.brentvatne.react;

import android.app.Activity;
import android.content.Context;
import android.graphics.Matrix;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.widget.MediaController;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.yqritc.scalablevideoview.ScalableVideoView;
import com.yqritc.scalablevideoview.c;
import com.yqritc.scalablevideoview.d;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public final class b extends ScalableVideoView implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener, MediaPlayer.OnPreparedListener, MediaController.MediaPlayerControl, LifecycleEventListener {
  private float A = 1.0F;
  
  private float B = 1.0F;
  
  private boolean C = false;
  
  private int D = 0;
  
  private int E = 0;
  
  private boolean F = false;
  
  public RCTEventEmitter a;
  
  public Handler b = new Handler();
  
  public Runnable c = null;
  
  public MediaController d;
  
  public boolean e = false;
  
  public float f = 250.0F;
  
  public boolean g = false;
  
  boolean h = false;
  
  public boolean i = false;
  
  public int j = 0;
  
  public int k = 0;
  
  public boolean l = false;
  
  private ThemedReactContext o;
  
  private Handler p = new Handler();
  
  private String q = null;
  
  private String r = "mp4";
  
  private ReadableMap s = null;
  
  private boolean t = false;
  
  private boolean u = false;
  
  private com.yqritc.scalablevideoview.b v = com.yqritc.scalablevideoview.b.LEFT_TOP;
  
  private boolean w = false;
  
  private boolean x = false;
  
  private float y = 1.0F;
  
  private float z = 0.0F;
  
  public b(ThemedReactContext paramThemedReactContext) {
    super((Context)paramThemedReactContext);
    this.o = paramThemedReactContext;
    this.a = (RCTEventEmitter)paramThemedReactContext.getJSModule(RCTEventEmitter.class);
    paramThemedReactContext.addLifecycleEventListener(this);
    b();
    setSurfaceTextureListener((TextureView.SurfaceTextureListener)this);
    this.c = new Runnable(this) {
        public final void run() {
          if (this.a.i && !this.a.l && !this.a.e && !this.a.g) {
            WritableMap writableMap = Arguments.createMap();
            double d = this.a.m.getCurrentPosition();
            Double.isNaN(d);
            writableMap.putDouble("currentTime", d / 1000.0D);
            d = this.a.k;
            Double.isNaN(d);
            writableMap.putDouble("playableDuration", d / 1000.0D);
            d = this.a.j;
            Double.isNaN(d);
            writableMap.putDouble("seekableDuration", d / 1000.0D);
            this.a.a.receiveEvent(this.a.getId(), b.a.EVENT_PROGRESS.toString(), writableMap);
            this.a.b.postDelayed(this.a.c, Math.round(this.a.f));
          } 
        }
      };
  }
  
  private static Map<String, String> a(ReadableMap paramReadableMap) {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    if (paramReadableMap == null)
      return (Map)hashMap; 
    ReadableMapKeySetIterator readableMapKeySetIterator = paramReadableMap.keySetIterator();
    while (readableMapKeySetIterator.hasNextKey()) {
      String str = readableMapKeySetIterator.nextKey();
      hashMap.put(str, paramReadableMap.getString(str));
    } 
    return (Map)hashMap;
  }
  
  private void b() {
    if (this.m == null) {
      this.i = false;
      this.m = new MediaPlayer();
      this.m.setScreenOnWhilePlaying(true);
      this.m.setOnVideoSizeChangedListener((MediaPlayer.OnVideoSizeChangedListener)this);
      this.m.setOnErrorListener(this);
      this.m.setOnPreparedListener(this);
      this.m.setOnBufferingUpdateListener(this);
      this.m.setOnCompletionListener(this);
      this.m.setOnInfoListener(this);
    } 
  }
  
  private void c() {
    if (this.d == null)
      this.d = new MediaController(getContext()); 
  }
  
  private float d() {
    return (new BigDecimal((this.y * (1.0F - Math.abs(this.z))))).setScale(1, 4).floatValue();
  }
  
  public final void a(String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2, ReadableMap paramReadableMap) {
    a(paramString1, paramString2, paramBoolean1, paramBoolean2, paramReadableMap, 0, 0);
  }
  
  public final void a(String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2, ReadableMap paramReadableMap, int paramInt1, int paramInt2) {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: putfield q : Ljava/lang/String;
    //   5: aload_0
    //   6: aload_2
    //   7: putfield r : Ljava/lang/String;
    //   10: aload_0
    //   11: iload_3
    //   12: putfield t : Z
    //   15: aload_0
    //   16: iload #4
    //   18: putfield u : Z
    //   21: aload_0
    //   22: aload #5
    //   24: putfield s : Lcom/facebook/react/bridge/ReadableMap;
    //   27: aload_0
    //   28: iload #6
    //   30: putfield D : I
    //   33: aload_0
    //   34: iload #7
    //   36: putfield E : I
    //   39: aload_0
    //   40: iconst_0
    //   41: putfield i : Z
    //   44: aload_0
    //   45: iconst_0
    //   46: putfield j : I
    //   49: aload_0
    //   50: iconst_0
    //   51: putfield k : I
    //   54: aload_0
    //   55: invokespecial b : ()V
    //   58: aload_0
    //   59: getfield m : Landroid/media/MediaPlayer;
    //   62: invokevirtual reset : ()V
    //   65: iload_3
    //   66: ifeq -> 161
    //   69: invokestatic getInstance : ()Landroid/webkit/CookieManager;
    //   72: astore #8
    //   74: aload_1
    //   75: invokestatic parse : (Ljava/lang/String;)Landroid/net/Uri;
    //   78: astore #5
    //   80: aload #8
    //   82: aload #5
    //   84: invokevirtual buildUpon : ()Landroid/net/Uri$Builder;
    //   87: invokevirtual build : ()Landroid/net/Uri;
    //   90: invokevirtual toString : ()Ljava/lang/String;
    //   93: invokevirtual getCookie : (Ljava/lang/String;)Ljava/lang/String;
    //   96: astore #8
    //   98: new java/util/HashMap
    //   101: dup
    //   102: invokespecial <init> : ()V
    //   105: astore #9
    //   107: aload #8
    //   109: ifnull -> 125
    //   112: aload #9
    //   114: ldc_w 'Cookie'
    //   117: aload #8
    //   119: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   124: pop
    //   125: aload_0
    //   126: getfield s : Lcom/facebook/react/bridge/ReadableMap;
    //   129: ifnull -> 146
    //   132: aload #9
    //   134: aload_0
    //   135: getfield s : Lcom/facebook/react/bridge/ReadableMap;
    //   138: invokestatic a : (Lcom/facebook/react/bridge/ReadableMap;)Ljava/util/Map;
    //   141: invokeinterface putAll : (Ljava/util/Map;)V
    //   146: aload_0
    //   147: aload_0
    //   148: getfield o : Lcom/facebook/react/uimanager/ThemedReactContext;
    //   151: aload #5
    //   153: aload #9
    //   155: invokevirtual a : (Landroid/content/Context;Landroid/net/Uri;Ljava/util/Map;)V
    //   158: goto -> 400
    //   161: iload #4
    //   163: ifeq -> 203
    //   166: aload_1
    //   167: ldc_w 'content://'
    //   170: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   173: ifeq -> 195
    //   176: aload_1
    //   177: invokestatic parse : (Ljava/lang/String;)Landroid/net/Uri;
    //   180: astore #5
    //   182: aload_0
    //   183: aload_0
    //   184: getfield o : Lcom/facebook/react/uimanager/ThemedReactContext;
    //   187: aload #5
    //   189: invokevirtual a : (Landroid/content/Context;Landroid/net/Uri;)V
    //   192: goto -> 400
    //   195: aload_0
    //   196: aload_1
    //   197: invokevirtual setDataSource : (Ljava/lang/String;)V
    //   200: goto -> 400
    //   203: aconst_null
    //   204: astore #8
    //   206: aload_0
    //   207: getfield D : I
    //   210: istore #6
    //   212: aload #8
    //   214: astore #5
    //   216: iload #6
    //   218: ifle -> 312
    //   221: aload_0
    //   222: getfield o : Lcom/facebook/react/uimanager/ThemedReactContext;
    //   225: aload_0
    //   226: getfield D : I
    //   229: aload_0
    //   230: getfield E : I
    //   233: invokestatic a : (Landroid/content/Context;II)Lcom/android/vending/expansion/zipfile/b;
    //   236: astore #5
    //   238: new java/lang/StringBuilder
    //   241: dup
    //   242: invokespecial <init> : ()V
    //   245: astore #9
    //   247: aload #9
    //   249: aload_1
    //   250: ldc_w '.mp4'
    //   253: ldc_w ''
    //   256: invokevirtual replace : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   259: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   262: pop
    //   263: aload #9
    //   265: ldc_w '.mp4'
    //   268: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   271: pop
    //   272: aload #5
    //   274: aload #9
    //   276: invokevirtual toString : ()Ljava/lang/String;
    //   279: invokevirtual a : (Ljava/lang/String;)Landroid/content/res/AssetFileDescriptor;
    //   282: astore #5
    //   284: goto -> 312
    //   287: astore #5
    //   289: aload #5
    //   291: invokevirtual printStackTrace : ()V
    //   294: aload #8
    //   296: astore #5
    //   298: goto -> 312
    //   301: astore #5
    //   303: aload #5
    //   305: invokevirtual printStackTrace : ()V
    //   308: aload #8
    //   310: astore #5
    //   312: aload #5
    //   314: ifnonnull -> 381
    //   317: aload_0
    //   318: getfield o : Lcom/facebook/react/uimanager/ThemedReactContext;
    //   321: invokevirtual getResources : ()Landroid/content/res/Resources;
    //   324: aload_1
    //   325: ldc_w 'drawable'
    //   328: aload_0
    //   329: getfield o : Lcom/facebook/react/uimanager/ThemedReactContext;
    //   332: invokevirtual getPackageName : ()Ljava/lang/String;
    //   335: invokevirtual getIdentifier : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I
    //   338: istore #7
    //   340: iload #7
    //   342: istore #6
    //   344: iload #7
    //   346: ifne -> 372
    //   349: aload_0
    //   350: getfield o : Lcom/facebook/react/uimanager/ThemedReactContext;
    //   353: invokevirtual getResources : ()Landroid/content/res/Resources;
    //   356: aload_1
    //   357: ldc_w 'raw'
    //   360: aload_0
    //   361: getfield o : Lcom/facebook/react/uimanager/ThemedReactContext;
    //   364: invokevirtual getPackageName : ()Ljava/lang/String;
    //   367: invokevirtual getIdentifier : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I
    //   370: istore #6
    //   372: aload_0
    //   373: iload #6
    //   375: invokevirtual setRawData : (I)V
    //   378: goto -> 400
    //   381: aload_0
    //   382: aload #5
    //   384: invokevirtual getFileDescriptor : ()Ljava/io/FileDescriptor;
    //   387: aload #5
    //   389: invokevirtual getStartOffset : ()J
    //   392: aload #5
    //   394: invokevirtual getLength : ()J
    //   397: invokevirtual a : (Ljava/io/FileDescriptor;JJ)V
    //   400: invokestatic createMap : ()Lcom/facebook/react/bridge/WritableMap;
    //   403: astore #5
    //   405: invokestatic createMap : ()Lcom/facebook/react/bridge/WritableMap;
    //   408: astore #8
    //   410: aload #8
    //   412: aload_0
    //   413: getfield s : Lcom/facebook/react/bridge/ReadableMap;
    //   416: invokeinterface merge : (Lcom/facebook/react/bridge/ReadableMap;)V
    //   421: aload #5
    //   423: ldc_w 'uri'
    //   426: aload_1
    //   427: invokeinterface putString : (Ljava/lang/String;Ljava/lang/String;)V
    //   432: aload #5
    //   434: ldc_w 'type'
    //   437: aload_2
    //   438: invokeinterface putString : (Ljava/lang/String;Ljava/lang/String;)V
    //   443: aload #5
    //   445: ldc_w 'requestHeaders'
    //   448: aload #8
    //   450: invokeinterface putMap : (Ljava/lang/String;Lcom/facebook/react/bridge/WritableMap;)V
    //   455: aload #5
    //   457: ldc_w 'isNetwork'
    //   460: iload_3
    //   461: invokeinterface putBoolean : (Ljava/lang/String;Z)V
    //   466: aload_0
    //   467: getfield D : I
    //   470: istore #6
    //   472: iload #6
    //   474: ifle -> 512
    //   477: aload #5
    //   479: ldc_w 'mainVer'
    //   482: iload #6
    //   484: invokeinterface putInt : (Ljava/lang/String;I)V
    //   489: aload_0
    //   490: getfield E : I
    //   493: istore #6
    //   495: iload #6
    //   497: ifle -> 512
    //   500: aload #5
    //   502: ldc_w 'patchVer'
    //   505: iload #6
    //   507: invokeinterface putInt : (Ljava/lang/String;I)V
    //   512: invokestatic createMap : ()Lcom/facebook/react/bridge/WritableMap;
    //   515: astore_1
    //   516: aload_1
    //   517: ldc_w 'src'
    //   520: aload #5
    //   522: invokeinterface putMap : (Ljava/lang/String;Lcom/facebook/react/bridge/WritableMap;)V
    //   527: aload_0
    //   528: getfield a : Lcom/facebook/react/uimanager/events/RCTEventEmitter;
    //   531: aload_0
    //   532: invokevirtual getId : ()I
    //   535: getstatic com/brentvatne/react/b$a.EVENT_LOAD_START : Lcom/brentvatne/react/b$a;
    //   538: invokevirtual toString : ()Ljava/lang/String;
    //   541: aload_1
    //   542: invokeinterface receiveEvent : (ILjava/lang/String;Lcom/facebook/react/bridge/WritableMap;)V
    //   547: aload_0
    //   548: iconst_0
    //   549: putfield l : Z
    //   552: aload_0
    //   553: aload_0
    //   554: invokevirtual a : (Landroid/media/MediaPlayer$OnPreparedListener;)V
    //   557: return
    //   558: astore_1
    //   559: aload_1
    //   560: invokevirtual printStackTrace : ()V
    //   563: return
    //   564: aload_1
    //   565: invokevirtual printStackTrace : ()V
    //   568: return
    //   569: astore_1
    //   570: goto -> 564
    // Exception table:
    //   from	to	target	type
    //   69	107	569	java/lang/Exception
    //   112	125	569	java/lang/Exception
    //   125	146	569	java/lang/Exception
    //   146	158	569	java/lang/Exception
    //   166	192	569	java/lang/Exception
    //   195	200	569	java/lang/Exception
    //   206	212	569	java/lang/Exception
    //   221	284	301	java/io/IOException
    //   221	284	287	java/lang/NullPointerException
    //   221	284	569	java/lang/Exception
    //   289	294	569	java/lang/Exception
    //   303	308	569	java/lang/Exception
    //   317	340	569	java/lang/Exception
    //   349	372	569	java/lang/Exception
    //   372	378	569	java/lang/Exception
    //   381	400	569	java/lang/Exception
    //   552	557	558	java/lang/Exception
  }
  
  public final boolean canPause() {
    return true;
  }
  
  public final boolean canSeekBackward() {
    return true;
  }
  
  public final boolean canSeekForward() {
    return true;
  }
  
  public final int getAudioSessionId() {
    return 0;
  }
  
  public final int getBufferPercentage() {
    return 0;
  }
  
  protected final void onAttachedToWindow() {
    super.onAttachedToWindow();
    int i = this.D;
    if (i > 0) {
      a(this.q, this.r, this.t, this.u, this.s, i, this.E);
    } else {
      a(this.q, this.r, this.t, this.u, this.s);
    } 
    setKeepScreenOn(true);
  }
  
  public final void onBufferingUpdate(MediaPlayer paramMediaPlayer, int paramInt) {
    double d = (this.j * paramInt);
    Double.isNaN(d);
    this.k = (int)Math.round(d / 100.0D);
  }
  
  public final void onCompletion(MediaPlayer paramMediaPlayer) {
    this.l = true;
    this.a.receiveEvent(getId(), a.EVENT_END.toString(), null);
    if (!this.w)
      setKeepScreenOn(false); 
  }
  
  public final void onDetachedFromWindow() {
    this.i = false;
    super.onDetachedFromWindow();
    setKeepScreenOn(false);
  }
  
  public final boolean onError(MediaPlayer paramMediaPlayer, int paramInt1, int paramInt2) {
    WritableMap writableMap1 = Arguments.createMap();
    writableMap1.putInt("what", paramInt1);
    writableMap1.putInt("extra", paramInt2);
    WritableMap writableMap2 = Arguments.createMap();
    writableMap2.putMap("error", writableMap1);
    this.a.receiveEvent(getId(), a.EVENT_ERROR.toString(), writableMap2);
    return true;
  }
  
  public final void onHostDestroy() {}
  
  public final void onHostPause() {
    if (this.i && !this.e && !this.C) {
      this.g = true;
      this.m.pause();
    } 
  }
  
  public final void onHostResume() {
    this.g = false;
    if (this.i && !this.C && !this.e)
      (new Handler()).post(new Runnable(this) {
            public final void run() {
              this.a.setPausedModifier(false);
            }
          }); 
  }
  
  public final boolean onInfo(MediaPlayer paramMediaPlayer, int paramInt1, int paramInt2) {
    if (paramInt1 != 3) {
      if (paramInt1 != 701) {
        if (paramInt1 == 702)
          this.a.receiveEvent(getId(), a.EVENT_RESUME.toString(), Arguments.createMap()); 
      } else {
        this.a.receiveEvent(getId(), a.EVENT_STALLED.toString(), Arguments.createMap());
      } 
    } else {
      this.a.receiveEvent(getId(), a.EVENT_READY_FOR_DISPLAY.toString(), Arguments.createMap());
    } 
    return false;
  }
  
  protected final void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    if (paramBoolean) {
      if (!this.i)
        return; 
      paramInt1 = getVideoWidth();
      paramInt2 = getVideoHeight();
      if (paramInt1 != 0) {
        if (paramInt2 == 0)
          return; 
        Matrix matrix = (new c(new d(getWidth(), getHeight()), new d(paramInt1, paramInt2))).a(this.n);
        if (matrix != null)
          setTransform(matrix); 
      } 
    } 
  }
  
  public final void onPrepared(MediaPlayer paramMediaPlayer) {
    this.i = true;
    this.j = paramMediaPlayer.getDuration();
    WritableMap writableMap1 = Arguments.createMap();
    writableMap1.putInt("width", paramMediaPlayer.getVideoWidth());
    writableMap1.putInt("height", paramMediaPlayer.getVideoHeight());
    if (paramMediaPlayer.getVideoWidth() > paramMediaPlayer.getVideoHeight()) {
      writableMap1.putString("orientation", "landscape");
    } else {
      writableMap1.putString("orientation", "portrait");
    } 
    WritableMap writableMap2 = Arguments.createMap();
    double d = this.j;
    Double.isNaN(d);
    writableMap2.putDouble("duration", d / 1000.0D);
    d = paramMediaPlayer.getCurrentPosition();
    Double.isNaN(d);
    writableMap2.putDouble("currentTime", d / 1000.0D);
    writableMap2.putMap("naturalSize", writableMap1);
    writableMap2.putBoolean("canPlayFastForward", true);
    writableMap2.putBoolean("canPlaySlowForward", true);
    writableMap2.putBoolean("canPlaySlowReverse", true);
    writableMap2.putBoolean("canPlayReverse", true);
    writableMap2.putBoolean("canPlayFastForward", true);
    writableMap2.putBoolean("canStepBackward", true);
    writableMap2.putBoolean("canStepForward", true);
    this.a.receiveEvent(getId(), a.EVENT_LOAD.toString(), writableMap2);
    setResizeModeModifier(this.v);
    setRepeatModifier(this.w);
    setPausedModifier(this.e);
    setMutedModifier(this.x);
    setProgressUpdateInterval(this.f);
    setRateModifier(this.A);
    if (this.F) {
      c();
      this.d.setMediaPlayer(this);
      this.d.setAnchorView((View)this);
      this.p.post(new Runnable(this) {
            public final void run() {
              this.a.d.setEnabled(true);
              this.a.d.show();
            }
          });
    } 
  }
  
  public final boolean onTouchEvent(MotionEvent paramMotionEvent) {
    if (this.F) {
      c();
      this.d.show();
    } 
    return super.onTouchEvent(paramMotionEvent);
  }
  
  public final void seekTo(int paramInt) {
    if (this.i) {
      WritableMap writableMap = Arguments.createMap();
      double d = getCurrentPosition();
      Double.isNaN(d);
      writableMap.putDouble("currentTime", d / 1000.0D);
      d = paramInt;
      Double.isNaN(d);
      writableMap.putDouble("seekTime", d / 1000.0D);
      this.a.receiveEvent(getId(), a.EVENT_SEEK.toString(), writableMap);
      super.seekTo(paramInt);
      if (this.l) {
        int i = this.j;
        if (i != 0 && paramInt < i)
          this.l = false; 
      } 
    } 
  }
  
  public final void setControls(boolean paramBoolean) {
    this.F = paramBoolean;
  }
  
  public final void setFullscreen(boolean paramBoolean) {
    if (paramBoolean == this.h)
      return; 
    this.h = paramBoolean;
    Activity activity = this.o.getCurrentActivity();
    if (activity == null)
      return; 
    View view = activity.getWindow().getDecorView();
    if (this.h) {
      byte b1;
      if (Build.VERSION.SDK_INT >= 19) {
        b1 = 4102;
      } else {
        b1 = 6;
      } 
      this.a.receiveEvent(getId(), a.EVENT_FULLSCREEN_WILL_PRESENT.toString(), null);
      view.setSystemUiVisibility(b1);
      this.a.receiveEvent(getId(), a.EVENT_FULLSCREEN_DID_PRESENT.toString(), null);
      return;
    } 
    this.a.receiveEvent(getId(), a.EVENT_FULLSCREEN_WILL_DISMISS.toString(), null);
    view.setSystemUiVisibility(0);
    this.a.receiveEvent(getId(), a.EVENT_FULLSCREEN_DID_DISMISS.toString(), null);
  }
  
  public final void setMutedModifier(boolean paramBoolean) {
    this.x = paramBoolean;
    if (!this.i)
      return; 
    if (this.x) {
      a(0.0F, 0.0F);
      return;
    } 
    float f = this.z;
    if (f < 0.0F) {
      a(this.y, d());
      return;
    } 
    if (f > 0.0F) {
      a(d(), this.y);
      return;
    } 
    f = this.y;
    a(f, f);
  }
  
  public final void setPausedModifier(boolean paramBoolean) {
    this.e = paramBoolean;
    if (!this.i)
      return; 
    if (this.e) {
      if (this.m.isPlaying())
        pause(); 
    } else if (!this.m.isPlaying()) {
      start();
      float f = this.A;
      if (f != this.B)
        setRateModifier(f); 
      this.b.post(this.c);
    } 
    setKeepScreenOn(this.e ^ true);
  }
  
  public final void setPlayInBackground(boolean paramBoolean) {
    this.C = paramBoolean;
  }
  
  public final void setProgressUpdateInterval(float paramFloat) {
    this.f = paramFloat;
  }
  
  public final void setRateModifier(float paramFloat) {
    this.A = paramFloat;
    if (this.i && Build.VERSION.SDK_INT >= 23 && !this.e)
      try {
        this.m.setPlaybackParams(this.m.getPlaybackParams().setSpeed(paramFloat));
        this.B = paramFloat;
        return;
      } catch (Exception exception) {
        return;
      }  
  }
  
  public final void setRepeatModifier(boolean paramBoolean) {
    this.w = paramBoolean;
    if (this.i)
      setLooping(paramBoolean); 
  }
  
  public final void setResizeModeModifier(com.yqritc.scalablevideoview.b paramb) {
    this.v = paramb;
    if (this.i) {
      setScalableType(paramb);
      invalidate();
    } 
  }
  
  public final void setStereoPan(float paramFloat) {
    this.z = paramFloat;
    setMutedModifier(this.x);
  }
  
  public final void setVolumeModifier(float paramFloat) {
    this.y = paramFloat;
    setMutedModifier(this.x);
  }
  
  public enum a {
    EVENT_END,
    EVENT_ERROR,
    EVENT_FULLSCREEN_DID_DISMISS,
    EVENT_FULLSCREEN_DID_PRESENT,
    EVENT_FULLSCREEN_WILL_DISMISS,
    EVENT_FULLSCREEN_WILL_PRESENT,
    EVENT_LOAD,
    EVENT_LOAD_START("onVideoLoadStart"),
    EVENT_PROGRESS("onVideoLoadStart"),
    EVENT_READY_FOR_DISPLAY("onVideoLoadStart"),
    EVENT_RESUME("onVideoLoadStart"),
    EVENT_SEEK("onVideoLoadStart"),
    EVENT_STALLED("onVideoLoadStart");
    
    private final String a;
    
    static {
      EVENT_ERROR = new a("EVENT_ERROR", 2, "onVideoError");
      EVENT_PROGRESS = new a("EVENT_PROGRESS", 3, "onVideoProgress");
      EVENT_SEEK = new a("EVENT_SEEK", 4, "onVideoSeek");
      EVENT_END = new a("EVENT_END", 5, "onVideoEnd");
      EVENT_STALLED = new a("EVENT_STALLED", 6, "onPlaybackStalled");
      EVENT_RESUME = new a("EVENT_RESUME", 7, "onPlaybackResume");
      EVENT_READY_FOR_DISPLAY = new a("EVENT_READY_FOR_DISPLAY", 8, "onReadyForDisplay");
      EVENT_FULLSCREEN_WILL_PRESENT = new a("EVENT_FULLSCREEN_WILL_PRESENT", 9, "onVideoFullscreenPlayerWillPresent");
      EVENT_FULLSCREEN_DID_PRESENT = new a("EVENT_FULLSCREEN_DID_PRESENT", 10, "onVideoFullscreenPlayerDidPresent");
      EVENT_FULLSCREEN_WILL_DISMISS = new a("EVENT_FULLSCREEN_WILL_DISMISS", 11, "onVideoFullscreenPlayerWillDismiss");
      EVENT_FULLSCREEN_DID_DISMISS = new a("EVENT_FULLSCREEN_DID_DISMISS", 12, "onVideoFullscreenPlayerDidDismiss");
      b = new a[] { 
          EVENT_LOAD_START, EVENT_LOAD, EVENT_ERROR, EVENT_PROGRESS, EVENT_SEEK, EVENT_END, EVENT_STALLED, EVENT_RESUME, EVENT_READY_FOR_DISPLAY, EVENT_FULLSCREEN_WILL_PRESENT, 
          EVENT_FULLSCREEN_DID_PRESENT, EVENT_FULLSCREEN_WILL_DISMISS, EVENT_FULLSCREEN_DID_DISMISS };
    }
    
    a(String param1String1) {
      this.a = param1String1;
    }
    
    public final String toString() {
      return this.a;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\brentvatne\react\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */