package com.bytedance.ies.bullet.kit.rn.pkg.fastimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import com.facebook.common.k.g;
import com.facebook.drawee.c.b;
import com.facebook.drawee.c.c;
import com.facebook.drawee.c.f;
import com.facebook.drawee.e.b;
import com.facebook.drawee.e.q;
import com.facebook.drawee.f.b;
import com.facebook.drawee.f.e;
import com.facebook.drawee.view.GenericDraweeView;
import com.facebook.imagepipeline.common.c;
import com.facebook.imagepipeline.j.f;
import com.facebook.imagepipeline.m.b;
import com.facebook.imagepipeline.o.b;
import com.facebook.imagepipeline.o.c;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.fresco.ReactNetworkImageRequest;
import com.facebook.react.uimanager.FloatUtil;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.views.image.GlobalImageLoadListener;
import com.facebook.react.views.image.ImageResizeMethod;
import com.facebook.react.views.image.ImageResizeMode;
import com.facebook.react.views.imagehelper.ImageSource;
import com.facebook.react.views.imagehelper.MultiSourceHelper;
import com.facebook.react.views.imagehelper.ResourceDrawableIdHelper;
import com.ss.android.ugc.aweme.lancet.g;
import java.util.LinkedList;
import java.util.List;

public final class d extends GenericDraweeView {
  public static float[] a = new float[4];
  
  public static final Matrix b = new Matrix();
  
  public static final Matrix c = new Matrix();
  
  private b.b A;
  
  public q.b d = ImageResizeMode.defaultValue();
  
  private ImageResizeMethod e = ImageResizeMethod.AUTO;
  
  private final List<ImageSource> f;
  
  private ImageSource g;
  
  private ImageSource h;
  
  private Drawable i;
  
  private int j;
  
  private int k;
  
  private float l;
  
  private float m = 1.0E21F;
  
  private float[] n;
  
  private boolean o;
  
  private final b p;
  
  private final a q;
  
  private b r;
  
  private com.facebook.drawee.c.d s;
  
  private com.facebook.drawee.c.d t;
  
  private GlobalImageLoadListener u;
  
  private final Object v;
  
  private int w = -1;
  
  private boolean x;
  
  private ReadableMap y;
  
  private c z;
  
  public d(Context paramContext, b paramb, GlobalImageLoadListener paramGlobalImageLoadListener, Object paramObject) {
    super(paramContext, (new b(paramContext.getResources())).a(e.b(0.0F)).a());
    this.p = paramb;
    this.q = new a();
    this.u = paramGlobalImageLoadListener;
    this.v = paramObject;
    this.f = new LinkedList<ImageSource>();
  }
  
  private boolean a(ImageSource paramImageSource) {
    return (this.e == ImageResizeMethod.AUTO) ? (!g.d(paramImageSource.getUri()) ? (g.c(paramImageSource.getUri())) : true) : ((this.e == ImageResizeMethod.RESIZE));
  }
  
  private boolean b() {
    return (this.f.size() > 1);
  }
  
  private void c() {
    this.g = null;
    if (this.f.isEmpty())
      return; 
    if (b()) {
      MultiSourceHelper.MultiSourceResult multiSourceResult = MultiSourceHelper.getBestSourceForSize(getWidth(), getHeight(), this.f);
      this.g = multiSourceResult.getBestResult();
      this.h = multiSourceResult.getBestResultInCache();
      return;
    } 
    this.g = this.f.get(0);
  }
  
  public final void a() {
    boolean bool;
    b b1;
    com.facebook.imagepipeline.common.d d1;
    if (!this.o)
      return; 
    if (b() && (getWidth() <= 0 || getHeight() <= 0))
      return; 
    c();
    ImageSource imageSource1 = this.g;
    if (imageSource1 == null)
      return; 
    boolean bool1 = a(imageSource1);
    if (bool1 && (getWidth() <= 0 || getHeight() <= 0))
      return; 
    com.facebook.drawee.f.a a1 = (com.facebook.drawee.f.a)getHierarchy();
    a1.a(this.d);
    Drawable drawable = this.i;
    if (drawable != null)
      a1.a(drawable, q.b.e); 
    if (this.d != q.b.g && this.d != q.b.h) {
      bool = true;
    } else {
      bool = false;
    } 
    e e = a1.a;
    if (bool) {
      e.a(0.0F);
    } else {
      a(a);
      float[] arrayOfFloat = a;
      e.a(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2], arrayOfFloat[3]);
    } 
    e.a(this.j, this.l);
    int i = this.k;
    if (i != 0) {
      e.a(i);
    } else {
      e.a(e.a.BITMAP_ONLY);
    } 
    a1.a(e);
    i = this.w;
    if (i < 0)
      if (this.g.isResource()) {
        i = 0;
      } else {
        i = 300;
      }  
    a1.a(i);
    e = null;
    if (bool) {
      a a2 = this.q;
    } else {
      b1 = this.r;
      if (b1 == null)
        b1 = null; 
    } 
    if (bool1)
      d1 = new com.facebook.imagepipeline.common.d(getWidth(), getHeight()); 
    c c1 = c.a(this.g.getUri()).a((com.facebook.imagepipeline.o.d)b1).a(d1).a(true).b(this.x);
    c c2 = this.z;
    if (c2 != null)
      c1.a(c2); 
    b.b b2 = this.A;
    if (b2 != null)
      c1.a(b2); 
    (new c()).a(c1);
    ReactNetworkImageRequest reactNetworkImageRequest = ReactNetworkImageRequest.fromBuilderWithHeaders(c1, this.y);
    GlobalImageLoadListener globalImageLoadListener = this.u;
    if (globalImageLoadListener != null)
      globalImageLoadListener.onLoadAttempt(this.g.getUri()); 
    this.p.b();
    this.p.c(true).a(this.v).a(getController()).b(reactNetworkImageRequest);
    ImageSource imageSource2 = this.h;
    if (imageSource2 != null) {
      b b3 = c.a(imageSource2.getUri()).a((com.facebook.imagepipeline.o.d)b1).a(d1).a(true).b(this.x).a();
      this.p.c(b3);
    } 
    if (this.s != null && this.t != null) {
      f f = new f();
      f.a(this.s);
      f.a(this.t);
      this.p.a((com.facebook.drawee.c.d)f);
    } else {
      com.facebook.drawee.c.d d2 = this.t;
      if (d2 != null) {
        this.p.a(d2);
      } else {
        d2 = this.s;
        if (d2 != null)
          this.p.a(d2); 
      } 
    } 
    setController((com.facebook.drawee.h.a)this.p.c());
    this.o = false;
    this.p.b();
  }
  
  public final void a(float[] paramArrayOffloat) {
    float f1;
    if (!com.facebook.yoga.a.a(this.m)) {
      f1 = this.m;
    } else {
      f1 = 0.0F;
    } 
    float[] arrayOfFloat = this.n;
    if (arrayOfFloat != null && !com.facebook.yoga.a.a(arrayOfFloat[0])) {
      f2 = this.n[0];
    } else {
      f2 = f1;
    } 
    paramArrayOffloat[0] = f2;
    arrayOfFloat = this.n;
    if (arrayOfFloat != null && !com.facebook.yoga.a.a(arrayOfFloat[1])) {
      f2 = this.n[1];
    } else {
      f2 = f1;
    } 
    paramArrayOffloat[1] = f2;
    arrayOfFloat = this.n;
    if (arrayOfFloat != null && !com.facebook.yoga.a.a(arrayOfFloat[2])) {
      f2 = this.n[2];
    } else {
      f2 = f1;
    } 
    paramArrayOffloat[2] = f2;
    arrayOfFloat = this.n;
    float f2 = f1;
    if (arrayOfFloat != null) {
      f2 = f1;
      if (!com.facebook.yoga.a.a(arrayOfFloat[3]))
        f2 = this.n[3]; 
    } 
    paramArrayOffloat[3] = f2;
  }
  
  public final boolean hasOverlappingRendering() {
    return false;
  }
  
  public final void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    g.a(this);
  }
  
  protected final void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if (paramInt1 > 0 && paramInt2 > 0) {
      boolean bool;
      if (this.o || b()) {
        bool = true;
      } else {
        bool = false;
      } 
      this.o = bool;
      a();
    } 
  }
  
  public final void setBlurRadius(float paramFloat) {
    int i = (int)PixelUtil.toPixelFromDIP(paramFloat);
    if (i == 0) {
      this.r = null;
    } else {
      this.r = new b(i);
    } 
    this.o = true;
  }
  
  public final void setBorderColor(int paramInt) {
    this.j = paramInt;
    this.o = true;
  }
  
  public final void setBorderRadius(float paramFloat) {
    if (!FloatUtil.floatsEqual(this.m, paramFloat)) {
      this.m = paramFloat;
      this.o = true;
    } 
  }
  
  public final void setBorderWidth(float paramFloat) {
    this.l = PixelUtil.toPixelFromDIP(paramFloat);
    this.o = true;
  }
  
  public final void setControllerListener(com.facebook.drawee.c.d paramd) {
    this.t = paramd;
    this.o = true;
    a();
  }
  
  public final void setFadeDuration(int paramInt) {
    this.w = paramInt;
  }
  
  public final void setHeaders(ReadableMap paramReadableMap) {
    this.y = paramReadableMap;
  }
  
  public final void setLoadingIndicatorSource(String paramString) {
    Drawable drawable = ResourceDrawableIdHelper.getInstance().getResourceDrawable(getContext(), paramString);
    if (drawable != null) {
      b b1 = new b(drawable, 1000);
    } else {
      drawable = null;
    } 
    this.i = drawable;
    this.o = true;
  }
  
  public final void setOverlayColor(int paramInt) {
    this.k = paramInt;
    this.o = true;
  }
  
  public final void setProgressiveRenderingEnabled(boolean paramBoolean) {
    this.x = paramBoolean;
  }
  
  public final void setResizeMethod(ImageResizeMethod paramImageResizeMethod) {
    this.e = paramImageResizeMethod;
    this.o = true;
  }
  
  public final void setScaleType(q.b paramb) {
    this.d = paramb;
    this.o = true;
  }
  
  public final void setShouldNotifyLoadEvents(boolean paramBoolean) {
    if (!paramBoolean) {
      this.s = null;
    } else {
      this.s = (com.facebook.drawee.c.d)new c<f>(this, (RCTEventEmitter)((ReactContext)getContext()).getJSModule(RCTEventEmitter.class)) {
          public final void onFailure(String param1String, Throwable param1Throwable) {
            this.a.receiveEvent(this.b.getId(), "onFastImageError", (WritableMap)new WritableNativeMap());
            this.a.receiveEvent(this.b.getId(), "onFastImageLoadEnd", (WritableMap)new WritableNativeMap());
          }
          
          public final void onSubmit(String param1String, Object param1Object) {
            this.a.receiveEvent(this.b.getId(), "onFastImageLoadStart", (WritableMap)new WritableNativeMap());
          }
        };
    } 
    this.o = true;
  }
  
  public final void setSource(ReadableMap paramReadableMap) {
    this.f.clear();
    Boolean bool = Boolean.valueOf(true);
    if (paramReadableMap != null && paramReadableMap.hasKey("uri")) {
      int i;
      String str = paramReadableMap.getString("uri");
      if (str == null || str.trim().isEmpty()) {
        i = 1;
      } else {
        i = 0;
      } 
      if (!i) {
        b.b b1;
        str = paramReadableMap.getString("uri");
        ImageSource imageSource = new ImageSource(getContext(), str);
        this.f.add(imageSource);
        Uri.EMPTY.equals(imageSource.getUri());
        if (paramReadableMap.hasKey("headers")) {
          ReadableMap readableMap = paramReadableMap.getMap("headers");
          if (readableMap != null)
            setHeaders(readableMap); 
        } 
        this.z = a.<c>a("priority", "normal", a.b, paramReadableMap);
        a.a a1 = a.<a.a>a("cache", "immutable", a.a, paramReadableMap);
        imageSource = null;
        Boolean bool2 = Boolean.valueOf(false);
        Boolean bool1 = Boolean.valueOf(false);
        i = a.null.a[a1.ordinal()];
        if (i != 1) {
          if (i != 2)
            bool = bool2; 
        } else {
          bool1 = bool;
          bool = bool2;
        } 
        if (bool1.booleanValue()) {
          b1 = b.b.FULL_FETCH;
        } else {
          ImageSource imageSource1 = imageSource;
          if (bool.booleanValue())
            b1 = b.b.DISK_CACHE; 
        } 
        this.A = b1;
        setShouldNotifyLoadEvents(true);
      } 
    } 
    this.o = true;
  }
  
  final class a extends com.facebook.imagepipeline.o.a {
    private a(d this$0) {}
    
    public final void process(Bitmap param1Bitmap1, Bitmap param1Bitmap2) {
      this.a.a(d.a);
      param1Bitmap1.setHasAlpha(true);
      if (FloatUtil.floatsEqual(d.a[0], 0.0F) && FloatUtil.floatsEqual(d.a[1], 0.0F) && FloatUtil.floatsEqual(d.a[2], 0.0F) && FloatUtil.floatsEqual(d.a[3], 0.0F)) {
        super.process(param1Bitmap1, param1Bitmap2);
        return;
      } 
      Paint paint = new Paint();
      paint.setAntiAlias(true);
      Shader.TileMode tileMode = Shader.TileMode.CLAMP;
      paint.setShader((Shader)new BitmapShader(param1Bitmap2, tileMode, tileMode));
      Canvas canvas = new Canvas(param1Bitmap1);
      float[] arrayOfFloat1 = new float[8];
      float[] arrayOfFloat2 = d.a;
      this.a.d.a(d.b, new Rect(0, 0, param1Bitmap2.getWidth(), param1Bitmap2.getHeight()), param1Bitmap2.getWidth(), param1Bitmap2.getHeight(), 0.0F, 0.0F);
      d.b.invert(d.c);
      arrayOfFloat1[0] = d.c.mapRadius(arrayOfFloat2[0]);
      arrayOfFloat1[1] = arrayOfFloat1[0];
      arrayOfFloat1[2] = d.c.mapRadius(arrayOfFloat2[1]);
      arrayOfFloat1[3] = arrayOfFloat1[2];
      arrayOfFloat1[4] = d.c.mapRadius(arrayOfFloat2[2]);
      arrayOfFloat1[5] = arrayOfFloat1[4];
      arrayOfFloat1[6] = d.c.mapRadius(arrayOfFloat2[3]);
      arrayOfFloat1[7] = arrayOfFloat1[6];
      Path path = new Path();
      path.addRoundRect(new RectF(0.0F, 0.0F, param1Bitmap2.getWidth(), param1Bitmap2.getHeight()), arrayOfFloat1, Path.Direction.CW);
      canvas.drawPath(path, paint);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\bytedance\ies\bullet\kit\rn\pkg\fastimage\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */