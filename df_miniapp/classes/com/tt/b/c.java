package com.tt.b;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import java.io.File;

public final class c {
  public Drawable a;
  
  public int b;
  
  public int c;
  
  public boolean d;
  
  public boolean e;
  
  public boolean f;
  
  public boolean g;
  
  public boolean h;
  
  public Bitmap.Config i = Bitmap.Config.RGB_565;
  
  public int j;
  
  public int k;
  
  public float l;
  
  public String m;
  
  public File n;
  
  public int o;
  
  public Uri p;
  
  public View q;
  
  public a r;
  
  public c(Uri paramUri) {
    this.p = paramUri;
  }
  
  public c(File paramFile) {
    this.n = paramFile;
  }
  
  public c(String paramString) {
    this.m = paramString;
  }
  
  public final c a() {
    this.d = true;
    return this;
  }
  
  public final c a(int paramInt) {
    this.c = 2097479705;
    return this;
  }
  
  public final c a(int paramInt1, int paramInt2) {
    this.j = paramInt1;
    this.k = paramInt2;
    return this;
  }
  
  public final c a(Drawable paramDrawable) {
    this.a = paramDrawable;
    return this;
  }
  
  public final c a(View paramView) {
    this.q = paramView;
    return this;
  }
  
  public final c a(a parama) {
    this.r = parama;
    return this;
  }
  
  public final c b() {
    this.f = true;
    return this;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\b\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */