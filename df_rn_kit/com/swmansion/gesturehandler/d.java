package com.swmansion.gesturehandler;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public final class d {
  private static final PointF g = new PointF();
  
  private static final float[] h = new float[2];
  
  private static final Matrix i = new Matrix();
  
  private static final float[] j = new float[2];
  
  private static final Comparator<b> k = new Comparator<b>() {
    
    };
  
  final b[] a = new b[20];
  
  int b;
  
  boolean c;
  
  int d;
  
  boolean e;
  
  public float f;
  
  private final ViewGroup l;
  
  private final e m;
  
  private final p n;
  
  private final b[] o = new b[20];
  
  private final b[] p = new b[20];
  
  private final b[] q = new b[20];
  
  private int r;
  
  private int s;
  
  public d(ViewGroup paramViewGroup, e parame, p paramp) {
    this.l = paramViewGroup;
    this.m = parame;
    this.n = paramp;
  }
  
  private void a(View paramView, MotionEvent paramMotionEvent, float[] paramArrayOffloat) {
    if (paramView == this.l) {
      paramArrayOffloat[0] = paramMotionEvent.getX();
      paramArrayOffloat[1] = paramMotionEvent.getY();
      return;
    } 
    if (paramView != null && paramView.getParent() instanceof ViewGroup) {
      ViewGroup viewGroup = (ViewGroup)paramView.getParent();
      a((View)viewGroup, paramMotionEvent, paramArrayOffloat);
      PointF pointF = g;
      a(paramArrayOffloat[0], paramArrayOffloat[1], viewGroup, paramView, pointF);
      paramArrayOffloat[0] = pointF.x;
      paramArrayOffloat[1] = pointF.y;
      return;
    } 
    throw new IllegalArgumentException("Parent is null? View is no longer in the tree");
  }
  
  private boolean a(float paramFloat1, float paramFloat2, ViewGroup paramViewGroup, View paramView, PointF paramPointF) {
    boolean bool1;
    float f1 = paramFloat1 + paramViewGroup.getScrollX() - paramView.getLeft();
    float f2 = paramFloat2 + paramViewGroup.getScrollY() - paramView.getTop();
    Matrix matrix = paramView.getMatrix();
    paramFloat2 = f1;
    paramFloat1 = f2;
    if (!matrix.isIdentity()) {
      float[] arrayOfFloat = h;
      arrayOfFloat[0] = f1;
      arrayOfFloat[1] = f2;
      Matrix matrix1 = i;
      matrix.invert(matrix1);
      matrix1.mapPoints(arrayOfFloat);
      paramFloat2 = arrayOfFloat[0];
      paramFloat1 = arrayOfFloat[1];
    } 
    paramPointF.set(paramFloat2, paramFloat1);
    ArrayList<b> arrayList = this.m.a(paramView);
    if (arrayList != null) {
      int j = arrayList.size();
      boolean bool = false;
      int i = 0;
      while (true) {
        bool1 = bool;
        if (!bool) {
          bool1 = bool;
          if (i < j) {
            bool = ((b)arrayList.get(i)).a(paramView, paramFloat2, paramFloat1);
            i++;
            continue;
          } 
        } 
        break;
      } 
    } else {
      bool1 = false;
    } 
    boolean bool2 = bool1;
    if (!bool1) {
      if (paramFloat2 >= 0.0F && paramFloat2 <= paramView.getWidth() && paramFloat1 >= 0.0F && paramFloat1 < paramView.getHeight())
        return true; 
      bool2 = false;
    } 
    return bool2;
  }
  
  static boolean a(int paramInt) {
    return (paramInt != 3 && paramInt != 1) ? ((paramInt == 5)) : true;
  }
  
  private boolean a(View paramView) {
    return (paramView.getVisibility() == 0 && paramView.getAlpha() >= this.f);
  }
  
  private static boolean a(View paramView, float[] paramArrayOffloat) {
    return (!(paramView instanceof ViewGroup) || paramView.getBackground() != null);
  }
  
  private boolean a(View paramView, float[] paramArrayOffloat, int paramInt) {
    ArrayList<b> arrayList = this.m.a(paramView);
    boolean bool = false;
    if (arrayList != null) {
      int j = arrayList.size();
      int i = 0;
      for (bool = false; i < j; bool = bool1) {
        b b1 = arrayList.get(i);
        boolean bool1 = bool;
        if (b1.k) {
          bool1 = bool;
          if (b1.a(paramView, paramArrayOffloat[0], paramArrayOffloat[1])) {
            int k = 0;
            while (true) {
              int m = this.r;
              if (k < m) {
                if (this.o[k] != b1) {
                  k++;
                  continue;
                } 
                break;
              } 
              b[] arrayOfB = this.o;
              if (m < arrayOfB.length) {
                this.r = m + 1;
                arrayOfB[m] = b1;
                b1.s = false;
                b1.t = false;
                b1.r = Integer.MAX_VALUE;
                if (b1.f == null && b1.o == null) {
                  Arrays.fill(b1.c, -1);
                  b1.d = 0;
                  b1.g = 0;
                  b1.f = paramView;
                  b1.o = this;
                  break;
                } 
                throw new IllegalStateException("Already prepared or hasn't been reset");
              } 
              throw new IllegalStateException("Too many recognizers");
            } 
            b1.a(paramInt);
            bool1 = true;
          } 
        } 
        i++;
      } 
    } 
    return bool;
  }
  
  private boolean a(ViewGroup paramViewGroup, float[] paramArrayOffloat, int paramInt) {
    int i;
    for (i = paramViewGroup.getChildCount() - 1; i >= 0; i--) {
      View view = this.n.a(paramViewGroup, i);
      PointF pointF = g;
      if (a(view) && a(paramArrayOffloat[0], paramArrayOffloat[1], paramViewGroup, view, pointF)) {
        float f1 = paramArrayOffloat[0];
        float f2 = paramArrayOffloat[1];
        paramArrayOffloat[0] = pointF.x;
        paramArrayOffloat[1] = pointF.y;
        boolean bool = b(view, paramArrayOffloat, paramInt);
        paramArrayOffloat[0] = f1;
        paramArrayOffloat[1] = f2;
        if (bool)
          return true; 
      } 
    } 
    return false;
  }
  
  static boolean a(b paramb1, b paramb2) {
    return (paramb1 != paramb2 && (paramb1.c(paramb2) || paramb2.b(paramb1)));
  }
  
  private boolean b(View paramView, float[] paramArrayOffloat, int paramInt) {
    l l = this.n.a(paramView);
    if (l == l.NONE)
      return false; 
    if (l == l.BOX_ONLY)
      return !a(paramView, paramArrayOffloat, paramInt) ? (a(paramView, paramArrayOffloat)) : true; 
    if (l == l.BOX_NONE)
      return (paramView instanceof ViewGroup) ? a((ViewGroup)paramView, paramArrayOffloat, paramInt) : false; 
    if (l == l.AUTO) {
      boolean bool;
      if (paramView instanceof ViewGroup) {
        bool = a((ViewGroup)paramView, paramArrayOffloat, paramInt);
      } else {
        bool = false;
      } 
      return (!a(paramView, paramArrayOffloat, paramInt) && !bool) ? (a(paramView, paramArrayOffloat)) : true;
    } 
    StringBuilder stringBuilder = new StringBuilder("Unknown pointer event type: ");
    stringBuilder.append(l.toString());
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  private boolean b(b paramb) {
    for (int i = 0; i < this.r; i++) {
      b b1 = this.o[i];
      if (!a(b1.g) && a(paramb, b1))
        return true; 
    } 
    return false;
  }
  
  private static boolean b(b paramb1, b paramb2) {
    return (paramb1 == paramb2 || paramb1.d(paramb2) || paramb2.d(paramb1));
  }
  
  private void c(b paramb) {
    int k = paramb.g;
    paramb.t = false;
    paramb.s = true;
    int i = this.s;
    this.s = i + 1;
    paramb.r = i;
    i = 0;
    int j;
    for (j = 0; i < this.r; j = m) {
      b b1 = this.o[i];
      int m = j;
      if (c(b1, paramb)) {
        this.q[j] = b1;
        m = j + 1;
      } 
      i++;
    } 
    for (i = j - 1; i >= 0; i--)
      this.q[i].c(); 
    for (i = this.b - 1; i >= 0; i--) {
      b b1 = this.a[i];
      if (c(b1, paramb)) {
        b1.c();
        b1.t = false;
      } 
    } 
    b();
    paramb.a(4, 2);
    if (k != 4) {
      paramb.a(5, 4);
      if (k != 5)
        paramb.a(0, 5); 
    } 
  }
  
  private static boolean c(b paramb1, b paramb2) {
    return !paramb1.a(paramb2) ? false : (b(paramb1, paramb2) ? false : ((paramb1 != paramb2 && (paramb1.t || paramb1.g == 4)) ? paramb1.e(paramb2) : true));
  }
  
  private void d(b paramb) {
    int i = 0;
    while (true) {
      int j = this.b;
      if (i < j) {
        if (this.a[i] == paramb)
          return; 
        i++;
        continue;
      } 
      b[] arrayOfB = this.a;
      if (j < arrayOfB.length) {
        this.b = j + 1;
        arrayOfB[j] = paramb;
        paramb.t = true;
        i = this.s;
        this.s = i + 1;
        paramb.r = i;
        return;
      } 
      IllegalStateException illegalStateException = new IllegalStateException("Too many recognizers");
      throw illegalStateException;
    } 
  }
  
  void a() {
    int i = this.r - 1;
    int j;
    for (j = 0; i >= 0; j = k) {
      b b1 = this.o[i];
      int k = j;
      if (a(b1.g)) {
        k = j;
        if (!b1.t) {
          this.o[i] = null;
          b1.h();
          b1.s = false;
          b1.t = false;
          b1.r = Integer.MAX_VALUE;
          k = 1;
        } 
      } 
      i--;
    } 
    if (j) {
      i = 0;
      for (j = 0; i < this.r; j = k) {
        b[] arrayOfB = this.o;
        int k = j;
        if (arrayOfB[i] != null) {
          arrayOfB[j] = arrayOfB[i];
          k = j + 1;
        } 
        i++;
      } 
      this.r = j;
    } 
    this.e = false;
  }
  
  void a(b paramb) {
    if (b(paramb)) {
      d(paramb);
      return;
    } 
    c(paramb);
    paramb.t = false;
  }
  
  public final boolean a(MotionEvent paramMotionEvent) {
    this.c = true;
    int i = paramMotionEvent.getActionMasked();
    if (i == 0 || i == 5) {
      i = paramMotionEvent.getActionIndex();
      int k = paramMotionEvent.getPointerId(i);
      j[0] = paramMotionEvent.getX(i);
      j[1] = paramMotionEvent.getY(i);
      b((View)this.l, j, k);
      a(this.l, j, k);
    } else if (i == 3) {
      for (i = this.b - 1; i >= 0; i--)
        this.a[i].c(); 
      int k = this.r;
      for (i = 0; i < k; i++)
        this.p[i] = this.o[i]; 
      for (i = k - 1; i >= 0; i--)
        this.p[i].c(); 
    } 
    int j = this.r;
    for (i = 0; i < j; i++)
      this.p[i] = this.o[i]; 
    Arrays.sort(this.p, 0, j, k);
    for (i = 0; i < j; i++) {
      int k;
      b b1 = this.p[i];
      if (b1.k && b1.g != 1 && b1.g != 3 && b1.g != 5 && b1.d > 0) {
        k = 1;
      } else {
        k = 0;
      } 
      if (k) {
        k = paramMotionEvent.getActionMasked();
        if (!b1.t || k != 2) {
          float[] arrayOfFloat = j;
          a(b1.f, paramMotionEvent, arrayOfFloat);
          float f1 = paramMotionEvent.getX();
          float f2 = paramMotionEvent.getY();
          paramMotionEvent.setLocation(arrayOfFloat[0], arrayOfFloat[1]);
          b1.b(paramMotionEvent);
          if (b1.s && b1.p != null)
            b1.p.a(b1, paramMotionEvent); 
          paramMotionEvent.setLocation(f1, f2);
          if (k == 1 || k == 6) {
            k = paramMotionEvent.getPointerId(paramMotionEvent.getActionIndex());
            if (b1.c[k] != -1) {
              b1.c[k] = -1;
              b1.d--;
            } 
          } 
        } 
      } 
    } 
    this.c = false;
    if (this.e && this.d == 0)
      a(); 
    return true;
  }
  
  void b() {
    int i = 0;
    int j;
    for (j = 0; i < this.b; j = k) {
      int k = j;
      if ((this.a[i]).t) {
        b[] arrayOfB = this.a;
        arrayOfB[j] = arrayOfB[i];
        k = j + 1;
      } 
      i++;
    } 
    this.b = j;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\swmansion\gesturehandler\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */