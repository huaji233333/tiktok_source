package com.tt.miniapp.component.nativeview.liveplayer;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.bytedance.sandboxapp.protocol.service.j.a;
import com.tt.frontendapiinterface.j;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.component.nativeview.NativeComponent;
import com.tt.miniapp.component.nativeview.liveplayer.context.ILivePlayerContext;
import com.tt.miniapp.component.nativeview.liveplayer.context.LiveComponentContext;
import com.tt.miniapp.component.nativeview.liveplayer.util.LivePlayerUtil;
import com.tt.miniapp.component.nativeview.liveplayer.view.LivePlayerTextureView;
import com.tt.miniapp.liveplayer.ITTLivePlayer;
import com.tt.miniapp.liveplayer.dns.ILivePlayerDns;
import com.tt.miniapp.liveplayer.dns.LivePlayerDnsManager;
import com.tt.miniapp.view.webcore.AbsoluteLayout;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.util.NativeDimenUtil;
import com.tt.option.e.k;
import d.f;
import d.f.a.a;
import d.f.b.g;
import d.f.b.l;
import d.f.b.m;
import d.f.b.u;
import d.f.b.v;
import d.f.b.x;
import d.g;
import d.k.d;
import d.k.h;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONObject;

public final class LivePlayer extends RelativeLayout implements NativeComponent {
  public static final Companion Companion = new Companion(null);
  
  private HashMap _$_findViewCache;
  
  private final int mComponentId;
  
  private final Context mContext;
  
  private LivePlayerTextureView mHostTextureView;
  
  private ILivePlayerContext mLiveContext;
  
  private final f mLivePlayer$delegate;
  
  private final AbsoluteLayout mParent;
  
  private final JSONObject mPropertyObject;
  
  private final WebViewManager.IRender mRender;
  
  public LivePlayer(Context paramContext, int paramInt, AbsoluteLayout paramAbsoluteLayout, WebViewManager.IRender paramIRender) {
    super(paramContext);
    this.mContext = paramContext;
    this.mComponentId = paramInt;
    this.mParent = paramAbsoluteLayout;
    this.mRender = paramIRender;
    this.mPropertyObject = new JSONObject();
    this.mLivePlayer$delegate = g.a(LivePlayer$mLivePlayer$2.INSTANCE);
    setBackgroundColor(Color.parseColor("#ff000000"));
    this.mHostTextureView = new LivePlayerTextureView(getContext());
    View view = (View)this.mHostTextureView;
    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
    layoutParams.addRule(13, -1);
    addView(view, (ViewGroup.LayoutParams)layoutParams);
    HostDependManager hostDependManager = HostDependManager.getInst();
    AppbrandContext appbrandContext = AppbrandContext.getInst();
    l.a(appbrandContext, "AppbrandContext.getInst()");
    ILivePlayerDns iLivePlayerDns = hostDependManager.getLivePlayerDnsOptimizer((Context)appbrandContext.getApplicationContext());
    if (iLivePlayerDns != null && !LivePlayerDnsManager.Companion.getInstance().hasBind())
      LivePlayerDnsManager.Companion.getInstance().bindLifeCycle(iLivePlayerDns); 
  }
  
  private final ITTLivePlayer getMLivePlayer() {
    return (ITTLivePlayer)this.mLivePlayer$delegate.getValue();
  }
  
  private final void publishEventToJsContext(String paramString, JSONObject paramJSONObject) {
    AppbrandApplicationImpl appbrandApplicationImpl = AppbrandApplicationImpl.getInst();
    l.a(appbrandApplicationImpl, "AppbrandApplicationImpl.getInst()");
    j j = appbrandApplicationImpl.getJsBridge();
    if (j != null)
      j.sendMsgToJsCore(paramString, paramJSONObject.toString(), this.mRender.getWebViewId()); 
  }
  
  private final void publishEventToWebView(String paramString, JSONObject paramJSONObject) {
    AppbrandApplicationImpl appbrandApplicationImpl = AppbrandApplicationImpl.getInst();
    l.a(appbrandApplicationImpl, "AppbrandApplicationImpl.getInst()");
    WebViewManager webViewManager = appbrandApplicationImpl.getWebViewManager();
    if (webViewManager != null)
      webViewManager.publish(this.mRender.getWebViewId(), paramString, paramJSONObject.toString()); 
  }
  
  private final void setDisPlayMode(String paramString1, String paramString2) {
    ILivePlayerContext iLivePlayerContext = this.mLiveContext;
    if (iLivePlayerContext == null)
      l.a(); 
    iLivePlayerContext.setDisplayMode(new ITTLivePlayer.DisplayMode(LivePlayerUtil.INSTANCE.convertObjectFit(paramString1), LivePlayerUtil.INSTANCE.convertDisplayOrientation(paramString2)));
  }
  
  private final void setFilePath(String paramString, boolean paramBoolean) {
    if (TextUtils.isEmpty(paramString))
      return; 
    ILivePlayerContext iLivePlayerContext = this.mLiveContext;
    if (iLivePlayerContext == null)
      l.a(); 
    iLivePlayerContext.setPlayUrl(paramString);
    if (paramBoolean)
      operateLivePlayer("play", (Object)null); 
  }
  
  private final void setHidden(boolean paramBoolean) {
    if (paramBoolean) {
      if (getVisibility() == 0) {
        setVisibility(8);
        ILivePlayerContext iLivePlayerContext = this.mLiveContext;
        if (iLivePlayerContext == null)
          l.a(); 
        iLivePlayerContext.stop();
        return;
      } 
    } else {
      setVisibility(0);
    } 
  }
  
  private final void setLayout(JSONObject paramJSONObject) {
    ILivePlayerContext iLivePlayerContext = this.mLiveContext;
    if (iLivePlayerContext == null)
      l.a(); 
    if (iLivePlayerContext.isFullScreen())
      return; 
    ViewGroup.LayoutParams layoutParams = getLayoutParams();
    if (!(layoutParams instanceof AbsoluteLayout.LayoutParams))
      return; 
    if (paramJSONObject.has("position")) {
      JSONObject jSONObject = paramJSONObject.optJSONObject("position");
      int i = NativeDimenUtil.convertRxToPx(jSONObject.optInt("left"));
      int j = NativeDimenUtil.convertRxToPx(jSONObject.optInt("top"));
      int k = this.mParent.getCurScrollX();
      int m = this.mParent.getCurScrollY();
      layoutParams.height = NativeDimenUtil.convertRxToPx(jSONObject.optInt("height"));
      layoutParams.width = NativeDimenUtil.convertRxToPx(jSONObject.optInt("width"));
      AbsoluteLayout.LayoutParams layoutParams1 = (AbsoluteLayout.LayoutParams)layoutParams;
      layoutParams1.x = i - k;
      layoutParams1.y = j - m;
    } 
    if (paramJSONObject.has("fixed"))
      ((AbsoluteLayout.LayoutParams)layoutParams).isFixed = paramJSONObject.optBoolean("fixed"); 
    if (paramJSONObject.has("zIndex"))
      ((AbsoluteLayout.LayoutParams)layoutParams).zIndex = paramJSONObject.optInt("zIndex"); 
    requestLayout();
  }
  
  private final void setMuted(boolean paramBoolean) {
    String str;
    if (paramBoolean) {
      str = "mute";
    } else {
      str = "unmute";
    } 
    operateLivePlayer(str, (Object)null);
  }
  
  private final void updatePropertyObject(JSONObject paramJSONObject) {
    Iterator<String> iterator = paramJSONObject.keys();
    l.a(iterator, "diff.keys()");
    while (iterator.hasNext()) {
      String str = iterator.next();
      this.mPropertyObject.put(str, paramJSONObject.opt(str));
    } 
  }
  
  public final void _$_clearFindViewByIdCache() {
    HashMap hashMap = this._$_findViewCache;
    if (hashMap != null)
      hashMap.clear(); 
  }
  
  public final View _$_findCachedViewById(int paramInt) {
    if (this._$_findViewCache == null)
      this._$_findViewCache = new HashMap<Object, Object>(); 
    View view2 = (View)this._$_findViewCache.get(Integer.valueOf(paramInt));
    View view1 = view2;
    if (view2 == null) {
      view1 = findViewById(paramInt);
      this._$_findViewCache.put(Integer.valueOf(paramInt), view1);
    } 
    return view1;
  }
  
  public final void addView(String paramString, k paramk) {
    AppbrandApplicationImpl appbrandApplicationImpl = AppbrandApplicationImpl.getInst();
    l.a(appbrandApplicationImpl, "AppbrandApplicationImpl.getInst()");
    Context context = appbrandApplicationImpl.getMiniAppContext().getApplicationContext();
    l.a(context, "AppbrandApplicationImpl.…ontext.applicationContext");
    this.mLiveContext = (ILivePlayerContext)new LiveComponentContext(context, this.mRender, this, getMLivePlayer());
    ILivePlayerContext iLivePlayerContext = this.mLiveContext;
    if (iLivePlayerContext == null)
      l.a(); 
    LivePlayerTextureView livePlayerTextureView = this.mHostTextureView;
    if (livePlayerTextureView == null)
      l.a(); 
    iLivePlayerContext.bindSurface((TextureView)livePlayerTextureView);
    this.mParent.addView((View)this);
    updateView(paramString, paramk);
  }
  
  public final Context getMContext() {
    return this.mContext;
  }
  
  public final WebViewManager.IRender getMRender() {
    return this.mRender;
  }
  
  public final boolean onBackPressed() {
    ILivePlayerContext iLivePlayerContext = this.mLiveContext;
    if (iLivePlayerContext != null && iLivePlayerContext.isFullScreen()) {
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("livePlayerId", this.mComponentId);
      publishEventToWebView("onLivePlayerExitFullScreen", jSONObject);
      return true;
    } 
    return false;
  }
  
  public final void onDestroy() {
    removeView(this.mComponentId, (k)null);
  }
  
  public final void onLivePlayerError() {
    JSONObject jSONObject = new JSONObject();
    jSONObject.put("livePlayerId", this.mComponentId);
    jSONObject.put("data", this.mPropertyObject.opt("data"));
    publishEventToJsContext("onLivePlayerError", jSONObject);
  }
  
  public final void onLivePlayerFullscreenChange(boolean paramBoolean, a.a parama) {
    String str;
    l.b(parama, "direction");
    JSONObject jSONObject = new JSONObject();
    jSONObject.put("livePlayerId", this.mComponentId);
    jSONObject.put("data", this.mPropertyObject.opt("data"));
    jSONObject.put("fullScreen", paramBoolean);
    int i = LivePlayer$WhenMappings.$EnumSwitchMapping$0[parama.ordinal()];
    if (i != 1 && i != 2 && i != 3) {
      str = "vertical";
    } else {
      str = "horizontal";
    } 
    jSONObject.put("direction", str);
    publishEventToJsContext("onLivePlayerFullscreenChange", jSONObject);
  }
  
  public final void onLivePlayerStateChange(int paramInt) {
    JSONObject jSONObject = new JSONObject();
    jSONObject.put("livePlayerId", this.mComponentId);
    jSONObject.put("data", this.mPropertyObject.opt("data"));
    jSONObject.put("code", paramInt);
    publishEventToJsContext("onLivePlayerStateChange", jSONObject);
  }
  
  protected final void onMeasure(int paramInt1, int paramInt2) {
    ILivePlayerContext iLivePlayerContext = this.mLiveContext;
    if (iLivePlayerContext != null && iLivePlayerContext.isFullScreen()) {
      super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(paramInt1), 1073741824), View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(paramInt2), 1073741824));
      return;
    } 
    super.onMeasure(paramInt1, paramInt2);
  }
  
  public final void onViewPause() {
    ILivePlayerContext iLivePlayerContext = this.mLiveContext;
    if (iLivePlayerContext != null)
      iLivePlayerContext.onPause(); 
  }
  
  public final void onViewResume() {
    ILivePlayerContext iLivePlayerContext = this.mLiveContext;
    if (iLivePlayerContext != null)
      iLivePlayerContext.onResume(); 
  }
  
  public final boolean operateLivePlayer(String paramString, Object paramObject) {
    // Byte code:
    //   0: aload_1
    //   1: ldc_w 'operateType'
    //   4: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   7: aload_1
    //   8: invokevirtual hashCode : ()I
    //   11: lookupswitch default -> 76, -840405966 -> 337, -802181223 -> 303, 3363353 -> 272, 3443508 -> 242, 3540994 -> 212, 106440182 -> 199, 458133450 -> 79
    //   76: goto -> 368
    //   79: aload_1
    //   80: ldc_w 'requestFullScreen'
    //   83: invokevirtual equals : (Ljava/lang/Object;)Z
    //   86: ifeq -> 368
    //   89: aload_2
    //   90: instanceof org/json/JSONObject
    //   93: ifeq -> 197
    //   96: aload_0
    //   97: getfield mLiveContext : Lcom/tt/miniapp/component/nativeview/liveplayer/context/ILivePlayerContext;
    //   100: astore_1
    //   101: aload_1
    //   102: ifnonnull -> 108
    //   105: invokestatic a : ()V
    //   108: aload_0
    //   109: checkcast android/view/View
    //   112: astore #4
    //   114: getstatic com/tt/miniapp/component/nativeview/liveplayer/util/LivePlayerUtil.INSTANCE : Lcom/tt/miniapp/component/nativeview/liveplayer/util/LivePlayerUtil;
    //   117: astore #5
    //   119: aload_2
    //   120: checkcast org/json/JSONObject
    //   123: astore_2
    //   124: aload #5
    //   126: aload_2
    //   127: ldc_w 'direction'
    //   130: iconst_0
    //   131: invokevirtual optInt : (Ljava/lang/String;I)I
    //   134: invokevirtual convertDegreeToOrientation : (I)Lcom/bytedance/sandboxapp/protocol/service/j/a$a;
    //   137: astore #5
    //   139: new org/json/JSONObject
    //   142: dup
    //   143: invokespecial <init> : ()V
    //   146: astore #6
    //   148: aload_2
    //   149: ldc_w 'data'
    //   152: invokevirtual optJSONObject : (Ljava/lang/String;)Lorg/json/JSONObject;
    //   155: astore_2
    //   156: ldc_w 2147483647
    //   159: istore_3
    //   160: aload_2
    //   161: ifnull -> 175
    //   164: aload_2
    //   165: ldc_w 'zIndex'
    //   168: ldc_w 2147483647
    //   171: invokevirtual optInt : (Ljava/lang/String;I)I
    //   174: istore_3
    //   175: aload #6
    //   177: ldc_w 'zIndex'
    //   180: iload_3
    //   181: invokevirtual put : (Ljava/lang/String;I)Lorg/json/JSONObject;
    //   184: pop
    //   185: aload_1
    //   186: aload #4
    //   188: aload #5
    //   190: aload #6
    //   192: invokeinterface requestFullScreen : (Landroid/view/View;Lcom/bytedance/sandboxapp/protocol/service/j/a$a;Lorg/json/JSONObject;)V
    //   197: iconst_1
    //   198: ireturn
    //   199: aload_1
    //   200: ldc_w 'pause'
    //   203: invokevirtual equals : (Ljava/lang/Object;)Z
    //   206: ifeq -> 368
    //   209: goto -> 222
    //   212: aload_1
    //   213: ldc_w 'stop'
    //   216: invokevirtual equals : (Ljava/lang/Object;)Z
    //   219: ifeq -> 368
    //   222: aload_0
    //   223: getfield mLiveContext : Lcom/tt/miniapp/component/nativeview/liveplayer/context/ILivePlayerContext;
    //   226: astore_1
    //   227: aload_1
    //   228: ifnonnull -> 234
    //   231: invokestatic a : ()V
    //   234: aload_1
    //   235: invokeinterface stop : ()V
    //   240: iconst_1
    //   241: ireturn
    //   242: aload_1
    //   243: ldc_w 'play'
    //   246: invokevirtual equals : (Ljava/lang/Object;)Z
    //   249: ifeq -> 368
    //   252: aload_0
    //   253: getfield mLiveContext : Lcom/tt/miniapp/component/nativeview/liveplayer/context/ILivePlayerContext;
    //   256: astore_1
    //   257: aload_1
    //   258: ifnonnull -> 264
    //   261: invokestatic a : ()V
    //   264: aload_1
    //   265: invokeinterface play : ()V
    //   270: iconst_1
    //   271: ireturn
    //   272: aload_1
    //   273: ldc_w 'mute'
    //   276: invokevirtual equals : (Ljava/lang/Object;)Z
    //   279: ifeq -> 368
    //   282: aload_0
    //   283: getfield mLiveContext : Lcom/tt/miniapp/component/nativeview/liveplayer/context/ILivePlayerContext;
    //   286: astore_1
    //   287: aload_1
    //   288: ifnonnull -> 294
    //   291: invokestatic a : ()V
    //   294: aload_1
    //   295: iconst_1
    //   296: invokeinterface setMuted : (Z)V
    //   301: iconst_1
    //   302: ireturn
    //   303: aload_1
    //   304: ldc_w 'exitFullScreen'
    //   307: invokevirtual equals : (Ljava/lang/Object;)Z
    //   310: ifeq -> 368
    //   313: aload_0
    //   314: getfield mLiveContext : Lcom/tt/miniapp/component/nativeview/liveplayer/context/ILivePlayerContext;
    //   317: astore_1
    //   318: aload_1
    //   319: ifnonnull -> 325
    //   322: invokestatic a : ()V
    //   325: aload_1
    //   326: aload_0
    //   327: checkcast android/view/View
    //   330: invokeinterface exitFullScreen : (Landroid/view/View;)V
    //   335: iconst_1
    //   336: ireturn
    //   337: aload_1
    //   338: ldc_w 'unmute'
    //   341: invokevirtual equals : (Ljava/lang/Object;)Z
    //   344: ifeq -> 368
    //   347: aload_0
    //   348: getfield mLiveContext : Lcom/tt/miniapp/component/nativeview/liveplayer/context/ILivePlayerContext;
    //   351: astore_1
    //   352: aload_1
    //   353: ifnonnull -> 359
    //   356: invokestatic a : ()V
    //   359: aload_1
    //   360: iconst_0
    //   361: invokeinterface setMuted : (Z)V
    //   366: iconst_1
    //   367: ireturn
    //   368: ldc_w 'LivePlayer'
    //   371: iconst_1
    //   372: anewarray java/lang/Object
    //   375: dup
    //   376: iconst_0
    //   377: ldc_w 'unsupported operateType'
    //   380: aastore
    //   381: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   384: iconst_0
    //   385: ireturn
  }
  
  public final void removeView(int paramInt, k paramk) {
    operateLivePlayer("stop", (Object)null);
    ILivePlayerContext iLivePlayerContext = this.mLiveContext;
    if (iLivePlayerContext == null)
      l.a(); 
    iLivePlayerContext.release();
  }
  
  public final void updateView(String paramString, k paramk) {
    if (paramString != null) {
      JSONObject jSONObject = new JSONObject(paramString);
      setLayout(jSONObject);
      if (jSONObject.has("muted"))
        setMuted(jSONObject.optBoolean("muted")); 
      String str1 = jSONObject.optString("objectFit", ITTLivePlayer.ObjectFit.CONTAIN.name());
      l.a(str1, "updateParam.optString(NA…r.ObjectFit.CONTAIN.name)");
      String str2 = jSONObject.optString("orientation", ITTLivePlayer.Orientation.VERTICAL.name());
      l.a(str2, "updateParam.optString(NA…rientation.VERTICAL.name)");
      setDisPlayMode(str1, str2);
      if (jSONObject.has("hide"))
        setHidden(jSONObject.optBoolean("hide")); 
      if (jSONObject.has("filePath")) {
        boolean bool;
        if (jSONObject.has("autoplay")) {
          bool = jSONObject.optBoolean("autoplay");
        } else {
          bool = this.mPropertyObject.optBoolean("autoplay");
        } 
        str1 = jSONObject.optString("filePath");
        l.a(str1, "updateParam.optString(NAME_FILEPATH)");
        setFilePath(str1, bool);
      } 
      updatePropertyObject(jSONObject);
    } 
  }
  
  public static final class Companion {
    private Companion() {}
  }
  
  static final class LivePlayer$mLivePlayer$2 extends m implements a<ITTLivePlayer> {
    public static final LivePlayer$mLivePlayer$2 INSTANCE = new LivePlayer$mLivePlayer$2();
    
    LivePlayer$mLivePlayer$2() {
      super(0);
    }
    
    public final ITTLivePlayer invoke() {
      HostDependManager hostDependManager = HostDependManager.getInst();
      AppbrandContext appbrandContext = AppbrandContext.getInst();
      l.a(appbrandContext, "AppbrandContext.getInst()");
      ITTLivePlayer iTTLivePlayer = hostDependManager.createLivePlayer((Context)appbrandContext.getApplicationContext());
      if (iTTLivePlayer != null)
        return iTTLivePlayer; 
      throw (Throwable)new RuntimeException("feature is not supported in app");
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\liveplayer\LivePlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */