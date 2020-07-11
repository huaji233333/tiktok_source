package com.bytedance.ies.bullet.kit.rn.pkg.animation;

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
import com.facebook.common.k.g;
import com.facebook.drawee.c.b;
import com.facebook.drawee.c.c;
import com.facebook.drawee.c.d;
import com.facebook.drawee.c.f;
import com.facebook.drawee.e.b;
import com.facebook.drawee.e.q;
import com.facebook.drawee.f.b;
import com.facebook.drawee.f.e;
import com.facebook.drawee.view.GenericDraweeView;
import com.facebook.imagepipeline.common.d;
import com.facebook.imagepipeline.common.e;
import com.facebook.imagepipeline.j.f;
import com.facebook.imagepipeline.o.b;
import com.facebook.imagepipeline.o.c;
import com.facebook.imagepipeline.o.d;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.FloatUtil;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.views.image.ImageLoadEvent;
import com.facebook.react.views.image.ImageResizeMethod;
import com.facebook.react.views.image.ImageResizeMode;
import com.facebook.react.views.imagehelper.ImageSource;
import com.facebook.react.views.imagehelper.MultiSourceHelper;
import com.facebook.react.views.imagehelper.ResourceDrawableIdHelper;
import com.ss.android.ugc.aweme.lancet.g;
import java.util.LinkedList;
import java.util.List;

public final class a extends GenericDraweeView {
  public static float[] a = new float[4];
  
  public static final Matrix b = new Matrix();
  
  public static final Matrix c = new Matrix();
  
  public ImageSource d;
  
  float[] e;
  
  public q.b f = ImageResizeMode.defaultValue();
  
  boolean g;
  
  public Animatable h;
  
  private ImageResizeMethod i = ImageResizeMethod.AUTO;
  
  private final List<ImageSource> j;
  
  private ImageSource k;
  
  private Drawable l;
  
  private int m;
  
  private int n;
  
  private float o;
  
  private float p = 1.0E21F;
  
  private final b q;
  
  private final a r;
  
  private d s;
  
  private d t;
  
  private final Object u;
  
  private int v = -1;
  
  private boolean w;
  
  private boolean x;
  
  public a(Context paramContext, b paramb, Object paramObject) {
    super(paramContext, (new b(paramContext.getResources())).a(e.b(0.0F)).a());
    this.q = paramb;
    this.r = new a();
    this.u = paramObject;
    this.j = new LinkedList<ImageSource>();
    this.x = true;
  }
  
  private boolean a(ImageSource paramImageSource) {
    return (this.i == ImageResizeMethod.AUTO) ? (!g.d(paramImageSource.getUri()) ? (g.c(paramImageSource.getUri())) : true) : ((this.i == ImageResizeMethod.RESIZE));
  }
  
  private boolean c() {
    return (this.j.size() > 1);
  }
  
  private void d() {
    this.d = null;
    if (this.j.isEmpty())
      return; 
    if (c()) {
      MultiSourceHelper.MultiSourceResult multiSourceResult = MultiSourceHelper.getBestSourceForSize(getWidth(), getHeight(), this.j);
      this.d = multiSourceResult.getBestResult();
      this.k = multiSourceResult.getBestResultInCache();
      return;
    } 
    this.d = this.j.get(0);
  }
  
  public final void a() {
    Animatable animatable = this.h;
    if (animatable != null) {
      if (this.x && !animatable.isRunning()) {
        this.h.start();
        return;
      } 
      if (!this.x)
        this.h.stop(); 
    } 
  }
  
  public final void a(float[] paramArrayOffloat) {
    float f1;
    if (!com.facebook.yoga.a.a(this.p)) {
      f1 = this.p;
    } else {
      f1 = 0.0F;
    } 
    float[] arrayOfFloat = this.e;
    if (arrayOfFloat != null && !com.facebook.yoga.a.a(arrayOfFloat[0])) {
      f2 = this.e[0];
    } else {
      f2 = f1;
    } 
    paramArrayOffloat[0] = f2;
    arrayOfFloat = this.e;
    if (arrayOfFloat != null && !com.facebook.yoga.a.a(arrayOfFloat[1])) {
      f2 = this.e[1];
    } else {
      f2 = f1;
    } 
    paramArrayOffloat[1] = f2;
    arrayOfFloat = this.e;
    if (arrayOfFloat != null && !com.facebook.yoga.a.a(arrayOfFloat[2])) {
      f2 = this.e[2];
    } else {
      f2 = f1;
    } 
    paramArrayOffloat[2] = f2;
    arrayOfFloat = this.e;
    float f2 = f1;
    if (arrayOfFloat != null) {
      f2 = f1;
      if (!com.facebook.yoga.a.a(arrayOfFloat[3]))
        f2 = this.e[3]; 
    } 
    paramArrayOffloat[3] = f2;
  }
  
  public final void b() {
    boolean bool;
    d d1;
    if (!this.g)
      return; 
    if (c() && (getWidth() <= 0 || getHeight() <= 0))
      return; 
    d();
    ImageSource imageSource1 = this.d;
    if (imageSource1 == null)
      return; 
    boolean bool1 = a(imageSource1);
    if (bool1 && (getWidth() <= 0 || getHeight() <= 0))
      return; 
    com.facebook.drawee.f.a a1 = (com.facebook.drawee.f.a)getHierarchy();
    a1.a(this.f);
    Drawable drawable = this.l;
    if (drawable != null)
      a1.a(drawable, q.b.e); 
    if (this.f != q.b.g && this.f != q.b.h) {
      bool = true;
    } else {
      bool = false;
    } 
    e e = a1.a;
    if (e != null) {
      if (bool) {
        e.a(0.0F);
      } else {
        a(a);
        float[] arrayOfFloat = a;
        e.a(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2], arrayOfFloat[3]);
      } 
      e.a(this.m, this.o);
      int j = this.n;
      if (j != 0) {
        e.a(j);
      } else {
        e.a(e.a.BITMAP_ONLY);
      } 
    } 
    a1.a(e);
    int i = this.v;
    if (i < 0)
      if (this.d.isResource()) {
        i = 0;
      } else {
        i = 300;
      }  
    a1.a(i);
    e = null;
    if (bool) {
      a a2 = this.r;
    } else {
      a1 = null;
    } 
    if (bool1)
      d1 = new d(getWidth(), getHeight()); 
    b b1 = c.a(this.d.getUri()).a((d)a1).a(d1).a(e.a()).b(this.w).a();
    this.q.b();
    this.q.c(true).a(this.u).a(getController()).b(b1);
    ImageSource imageSource2 = this.k;
    if (imageSource2 != null) {
      b b2 = c.a(imageSource2.getUri()).a((d)a1).a(d1).a(e.a()).b(this.w).a();
      this.q.c(b2);
    } 
    if (this.s != null && this.t != null) {
      f f = new f();
      f.a(this.s);
      f.a(this.t);
      this.q.a((d)f);
    } else {
      d d2 = this.t;
      if (d2 != null) {
        this.q.a(d2);
      } else {
        d2 = this.s;
        if (d2 != null)
          this.q.a(d2); 
      } 
    } 
    setController((com.facebook.drawee.h.a)this.q.c());
    this.g = false;
  }
  
  public final boolean hasOverlappingRendering() {
    return false;
  }
  
  public final void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    g.a(this);
  }
  
  protected final void onDraw(Canvas paramCanvas) {
    a();
    super.onDraw(paramCanvas);
  }
  
  protected final void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if (paramInt1 > 0 && paramInt2 > 0) {
      boolean bool;
      if (this.g || c()) {
        bool = true;
      } else {
        bool = false;
      } 
      this.g = bool;
      b();
    } 
  }
  
  public final void setBorderColor(int paramInt) {
    this.m = paramInt;
    this.g = true;
  }
  
  public final void setBorderRadius(float paramFloat) {
    if (!FloatUtil.floatsEqual(this.p, paramFloat)) {
      this.p = paramFloat;
      this.g = true;
    } 
  }
  
  public final void setBorderWidth(float paramFloat) {
    this.o = PixelUtil.toPixelFromDIP(paramFloat);
    this.g = true;
  }
  
  public final void setControllerListener(d paramd) {
    this.t = paramd;
    this.g = true;
    b();
  }
  
  public final void setFadeDuration(int paramInt) {
    this.v = paramInt;
  }
  
  public final void setLoadingIndicatorSource(String paramString) {
    Drawable drawable = ResourceDrawableIdHelper.getInstance().getResourceDrawable(getContext(), paramString);
    if (drawable != null) {
      b b1 = new b(drawable, 1000);
    } else {
      drawable = null;
    } 
    this.l = drawable;
    this.g = true;
  }
  
  public final void setOverlayColor(int paramInt) {
    this.n = paramInt;
    this.g = true;
  }
  
  public final void setProgressiveRenderingEnabled(boolean paramBoolean) {
    this.w = paramBoolean;
  }
  
  public final void setResizeMethod(ImageResizeMethod paramImageResizeMethod) {
    this.i = paramImageResizeMethod;
    this.g = true;
  }
  
  public final void setScaleType(q.b paramb) {
    this.f = paramb;
    this.g = true;
  }
  
  public final void setShouldNotifyLoadEvents(boolean paramBoolean) {
    if (!paramBoolean) {
      this.s = null;
    } else {
      this.s = (d)new c<f>(this, ((UIManagerModule)((ReactContext)getContext()).getNativeModule(UIManagerModule.class)).getEventDispatcher()) {
          public final void onFailure(String param1String, Throwable param1Throwable) {
            this.a.dispatchEvent((Event)new ImageLoadEvent(this.b.getId(), 1));
            this.a.dispatchEvent((Event)new ImageLoadEvent(this.b.getId(), 3));
          }
          
          public final void onSubmit(String param1String, Object param1Object) {
            this.a.dispatchEvent((Event)new ImageLoadEvent(this.b.getId(), 4));
          }
        };
    } 
    this.g = true;
  }
  
  public final void setShouldPlay(boolean paramBoolean) {
    if (this.x == paramBoolean)
      return; 
    this.x = paramBoolean;
    a();
    this.g = true;
  }
  
  public final void setSource(ReadableArray paramReadableArray) {
    this.j.clear();
    if (paramReadableArray != null && paramReadableArray.size() != 0) {
      int j = paramReadableArray.size();
      int i = 0;
      if (j == 1) {
        this.j.add(new ImageSource(getContext(), paramReadableArray.getMap(0).getString("uri")));
      } else {
        while (i < paramReadableArray.size()) {
          ReadableMap readableMap = paramReadableArray.getMap(i);
          this.j.add(new ImageSource(getContext(), readableMap.getString("uri"), readableMap.getDouble("width"), readableMap.getDouble("height")));
          i++;
        } 
      } 
    } 
    this.g = true;
  }
  
  final class a extends com.facebook.imagepipeline.o.a {
    private a(a this$0) {}
    
    public final void process(Bitmap param1Bitmap1, Bitmap param1Bitmap2) {
      this.a.a(a.a);
      param1Bitmap1.setHasAlpha(true);
      if (FloatUtil.floatsEqual(a.a[0], 0.0F) && FloatUtil.floatsEqual(a.a[1], 0.0F) && FloatUtil.floatsEqual(a.a[2], 0.0F) && FloatUtil.floatsEqual(a.a[3], 0.0F)) {
        super.process(param1Bitmap1, param1Bitmap2);
        return;
      } 
      Paint paint = new Paint();
      paint.setAntiAlias(true);
      Shader.TileMode tileMode = Shader.TileMode.CLAMP;
      paint.setShader((Shader)new BitmapShader(param1Bitmap2, tileMode, tileMode));
      Canvas canvas = new Canvas(param1Bitmap1);
      float[] arrayOfFloat1 = new float[8];
      float[] arrayOfFloat2 = a.a;
      this.a.f.a(a.b, new Rect(0, 0, param1Bitmap2.getWidth(), param1Bitmap2.getHeight()), param1Bitmap2.getWidth(), param1Bitmap2.getHeight(), 0.0F, 0.0F);
      a.b.invert(a.c);
      arrayOfFloat1[0] = a.c.mapRadius(arrayOfFloat2[0]);
      arrayOfFloat1[1] = arrayOfFloat1[0];
      arrayOfFloat1[2] = a.c.mapRadius(arrayOfFloat2[1]);
      arrayOfFloat1[3] = arrayOfFloat1[2];
      arrayOfFloat1[4] = a.c.mapRadius(arrayOfFloat2[2]);
      arrayOfFloat1[5] = arrayOfFloat1[4];
      arrayOfFloat1[6] = a.c.mapRadius(arrayOfFloat2[3]);
      arrayOfFloat1[7] = arrayOfFloat1[6];
      Path path = new Path();
      path.addRoundRect(new RectF(0.0F, 0.0F, param1Bitmap2.getWidth(), param1Bitmap2.getHeight()), arrayOfFloat1, Path.Direction.CW);
      canvas.drawPath(path, paint);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\bytedance\ies\bullet\kit\rn\pkg\animation\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */