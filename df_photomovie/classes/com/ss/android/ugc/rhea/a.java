package com.ss.android.ugc.rhea;

import android.content.Context;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class a {
  public static final Executor a;
  
  public static Context b;
  
  private static final int c = Runtime.getRuntime().availableProcessors();
  
  static {
    int i = c;
    a = new ThreadPoolExecutor(i * 2 + 1, i * 2 + 1, 1L, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_photomovie\classes.jar!\com\ss\androi\\ugc\rhea\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */