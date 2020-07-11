package com.bytedance.ies.bullet.kit.rn.pkg.viewshot;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.net.Uri;
import android.util.Base64;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.NativeViewHierarchyManager;
import com.facebook.react.uimanager.UIBlock;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.zip.Deflater;

public class c implements UIBlock {
  private static final String a = c.class.getSimpleName();
  
  private static byte[] b = new byte[65536];
  
  private static final Object o = new Object();
  
  private static final Set<Bitmap> p = Collections.newSetFromMap(new WeakHashMap<Bitmap, Boolean>());
  
  private final int c;
  
  private final String d;
  
  private final int e;
  
  private final double f;
  
  private final Integer g;
  
  private final Integer h;
  
  private final File i;
  
  private final String j;
  
  private final Promise k;
  
  private final Boolean l;
  
  private final ReactApplicationContext m;
  
  private final Activity n;
  
  public c(int paramInt1, String paramString1, int paramInt2, double paramDouble, Integer paramInteger1, Integer paramInteger2, File paramFile, String paramString2, Boolean paramBoolean, ReactApplicationContext paramReactApplicationContext, Activity paramActivity, Promise paramPromise) {
    this.c = paramInt1;
    this.d = paramString1;
    this.e = paramInt2;
    this.f = paramDouble;
    this.g = paramInteger1;
    this.h = paramInteger2;
    this.i = paramFile;
    this.j = paramString2;
    this.l = paramBoolean;
    this.m = paramReactApplicationContext;
    this.n = paramActivity;
    this.k = paramPromise;
  }
  
  private static Bitmap a(int paramInt1, int paramInt2) {
    synchronized (o) {
      for (Bitmap bitmap : p) {
        if (bitmap.getWidth() >= paramInt1 && bitmap.getHeight() >= paramInt2) {
          p.remove(bitmap);
          bitmap.eraseColor(0);
          return bitmap;
        } 
      } 
      return Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
    } 
  }
  
  private static Matrix a(Canvas paramCanvas, View paramView1, View paramView2) {
    Matrix matrix = new Matrix();
    LinkedList<View> linkedList = new LinkedList();
    view = paramView2;
    while (true) {
      linkedList.add(view);
      View view1 = (View)view.getParent();
      view = view1;
      if (view1 == paramView1) {
        Collections.reverse(linkedList);
        for (View view : linkedList) {
          paramCanvas.save();
          int j = view.getLeft();
          byte b = 0;
          if (view != paramView2) {
            i = view.getPaddingLeft();
          } else {
            i = 0;
          } 
          float f1 = (j + i) + view.getTranslationX();
          j = view.getTop();
          int i = b;
          if (view != paramView2)
            i = view.getPaddingTop(); 
          float f2 = (j + i) + view.getTranslationY();
          paramCanvas.translate(f1, f2);
          paramCanvas.rotate(view.getRotation(), view.getPivotX(), view.getPivotY());
          paramCanvas.scale(view.getScaleX(), view.getScaleY());
          matrix.postTranslate(f1, f2);
          matrix.postRotate(view.getRotation(), view.getPivotX(), view.getPivotY());
          matrix.postScale(view.getScaleX(), view.getScaleY());
        } 
        return matrix;
      } 
    } 
  }
  
  private Point a(View paramView, OutputStream paramOutputStream) throws IOException {
    try {
      String str1;
      StringBuilder stringBuilder;
      String str2 = a;
      Activity activity = this.n;
      View view = activity.findViewById(16908290);
      if (view == null) {
        stringBuilder = new StringBuilder("Activity [");
        stringBuilder.append(activity.getClass().getSimpleName());
        stringBuilder.append("] is not initialized yet. ");
        str1 = stringBuilder.toString();
      } else {
        str1 = a.a((View)stringBuilder);
      } 
      a.a(str2, str1);
      return b(paramView, paramOutputStream);
    } finally {
      paramOutputStream.close();
    } 
  }
  
  private static void a(Bitmap paramBitmap) {
    synchronized (o) {
      p.add(paramBitmap);
      return;
    } 
  }
  
  private void a(View paramView) throws IOException {
    boolean bool;
    if (-1 == this.e) {
      bool = true;
    } else {
      bool = false;
    } 
    boolean bool1 = "zip-base64".equals(this.j);
    b b = new b(b);
    Point point = a(paramView, b);
    b = b.a();
    int i = b.size();
    String str = com.a.a(Locale.US, "%d:%d|", new Object[] { Integer.valueOf(point.x), Integer.valueOf(point.y) });
    if (!bool)
      str = ""; 
    if (bool1) {
      Deflater deflater = new Deflater();
      deflater.setInput(b, 0, i);
      deflater.finish();
      b = new b(new byte[32]);
      byte[] arrayOfByte = new byte[1024];
      while (!deflater.finished())
        b.write(arrayOfByte, 0, deflater.deflate(arrayOfByte)); 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str);
      stringBuilder.append(Base64.encodeToString(b.a(), 0, b.size(), 2));
      str = stringBuilder.toString();
    } else {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str);
      stringBuilder.append(Base64.encodeToString(b, 0, i, 2));
      str = stringBuilder.toString();
    } 
    this.k.resolve(str);
  }
  
  private static Bitmap b(int paramInt1, int paramInt2) {
    synchronized (o) {
      for (Bitmap bitmap : p) {
        if (bitmap.getWidth() == paramInt1 && bitmap.getHeight() == paramInt2) {
          p.remove(bitmap);
          bitmap.eraseColor(0);
          return bitmap;
        } 
      } 
      return Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
    } 
  }
  
  private Point b(View paramView, OutputStream paramOutputStream) {
    int j = paramView.getWidth();
    int i = paramView.getHeight();
    if (j > 0 && i > 0) {
      Bitmap bitmap1;
      if (this.l.booleanValue()) {
        ScrollView scrollView = (ScrollView)paramView;
        int k = 0;
        i = 0;
        while (k < scrollView.getChildCount()) {
          i += scrollView.getChildAt(k).getHeight();
          k++;
        } 
      } 
      Point point = new Point(j, i);
      Bitmap bitmap2 = a(j, i);
      Paint paint = new Paint();
      paint.setAntiAlias(true);
      paint.setFilterBitmap(true);
      paint.setDither(true);
      Canvas canvas = new Canvas(bitmap2);
      paramView.draw(canvas);
      for (View view : b(paramView)) {
        if (view instanceof TextureView && view.getVisibility() == 0) {
          TextureView textureView = (TextureView)view;
          textureView.setOpaque(false);
          Bitmap bitmap = textureView.getBitmap(b(view.getWidth(), view.getHeight()));
          int k = canvas.save();
          a(canvas, paramView, view);
          canvas.drawBitmap(bitmap, 0.0F, 0.0F, paint);
          canvas.restoreToCount(k);
          a(bitmap);
        } 
      } 
      Integer integer = this.g;
      if (integer != null && this.h != null && (integer.intValue() != j || this.h.intValue() != i)) {
        bitmap1 = Bitmap.createScaledBitmap(bitmap2, this.g.intValue(), this.h.intValue(), true);
        a(bitmap2);
      } else {
        bitmap1 = bitmap2;
      } 
      if (-1 == this.e && paramOutputStream instanceof b) {
        i = j * i * 4;
        paramOutputStream = paramOutputStream;
        bitmap1.copyPixelsToBuffer(paramOutputStream.a(i));
        paramOutputStream.b(i);
      } else {
        bitmap1.compress(a.a[this.e], (int)(this.f * 100.0D), paramOutputStream);
      } 
      a(bitmap1);
      return point;
    } 
    RuntimeException runtimeException = new RuntimeException("Impossible to snapshot the view: view is invalid");
    throw runtimeException;
  }
  
  private List<View> b(View paramView) {
    if (!(paramView instanceof ViewGroup)) {
      ArrayList<View> arrayList1 = new ArrayList();
      arrayList1.add(paramView);
      return arrayList1;
    } 
    ArrayList<View> arrayList = new ArrayList();
    ViewGroup viewGroup = (ViewGroup)paramView;
    for (int i = 0; i < viewGroup.getChildCount(); i++)
      arrayList.addAll(b(viewGroup.getChildAt(i))); 
    return arrayList;
  }
  
  public void execute(NativeViewHierarchyManager paramNativeViewHierarchyManager) {
    View view;
    Promise promise;
    int i = this.c;
    if (i == -1) {
      view = this.n.getWindow().getDecorView().findViewById(16908290);
    } else {
      view = view.resolveView(i);
    } 
    if (view == null) {
      promise = this.k;
      StringBuilder stringBuilder = new StringBuilder("No view found with reactTag: ");
      stringBuilder.append(this.c);
      promise.reject("E_UNABLE_TO_SNAPSHOT", stringBuilder.toString());
      return;
    } 
    try {
      Point point;
      b b = new b(b);
      b.b(Math.min(promise.getWidth() * promise.getHeight() * 4, 32));
      b = b.a();
      if ("tmpfile".equals(this.j) && -1 == this.e) {
        String str = Uri.fromFile(this.i).toString();
        FileOutputStream fileOutputStream = new FileOutputStream(this.i);
        b b1 = new b(b);
        point = a((View)promise, b1);
        b = b1.a();
        i = b1.size();
        fileOutputStream.write(com.a.a(Locale.US, "%d:%d|", new Object[] { Integer.valueOf(point.x), Integer.valueOf(point.y) }).getBytes(Charset.forName("US-ASCII")));
        fileOutputStream.write(b, 0, i);
        return;
      } 
      if ("tmpfile".equals(this.j) && -1 != this.e)
        return; 
    } finally {
      promise = null;
      this.k.reject("E_UNABLE_TO_SNAPSHOT", "Failed to capture view snapshot");
    } 
  }
  
  public static @interface a {
    public static final Bitmap.CompressFormat[] a = new Bitmap.CompressFormat[] { Bitmap.CompressFormat.JPEG, Bitmap.CompressFormat.PNG, Bitmap.CompressFormat.WEBP };
  }
  
  public static final class b extends ByteArrayOutputStream {
    public b(byte[] param1ArrayOfbyte) {
      super(0);
      this.buf = param1ArrayOfbyte;
    }
    
    public final ByteBuffer a(int param1Int) {
      if (this.buf.length < param1Int) {
        int j = this.buf.length << 1;
        int i = j;
        if (j - param1Int < 0)
          i = param1Int; 
        j = i;
        if (i - 2147483639 > 0)
          if (param1Int >= 0) {
            if (param1Int > 2147483639) {
              j = Integer.MAX_VALUE;
            } else {
              j = 2147483639;
            } 
          } else {
            throw new OutOfMemoryError();
          }  
        this.buf = Arrays.copyOf(this.buf, j);
      } 
      return ByteBuffer.wrap(this.buf);
    }
    
    public final byte[] a() {
      return this.buf;
    }
    
    public final void b(int param1Int) {
      this.count = param1Int;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\bytedance\ies\bullet\kit\rn\pkg\viewshot\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */