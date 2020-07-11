package com.facebook.react.devsupport;

import d.f.b.l;
import g.f;
import g.h;
import g.i;
import g.x;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MultipartStreamReader {
  private final String mBoundary;
  
  private long mLastProgressEvent;
  
  private final h mSource;
  
  public MultipartStreamReader(h paramh, String paramString) {
    this.mSource = paramh;
    this.mBoundary = paramString;
  }
  
  private void emitChunk(f paramf, boolean paramBoolean, ChunkListener paramChunkListener) throws IOException {
    i i = i.encodeUtf8("\r\n\r\n");
    l.b(i, "bytes");
    long l = paramf.a(i, 0L);
    if (l == -1L) {
      paramChunkListener.onChunkComplete(null, paramf, paramBoolean);
      return;
    } 
    f f1 = new f();
    f f2 = new f();
    paramf.read(f1, l);
    paramf.i(i.size());
    paramf.a((x)f2);
    paramChunkListener.onChunkComplete(parseHeaders(f1), f2, paramBoolean);
  }
  
  private void emitProgress(Map<String, String> paramMap, long paramLong, boolean paramBoolean, ChunkListener paramChunkListener) throws IOException {
    if (paramMap != null) {
      if (paramChunkListener == null)
        return; 
      long l = System.currentTimeMillis();
      if (l - this.mLastProgressEvent > 16L || paramBoolean) {
        this.mLastProgressEvent = l;
        if (paramMap.get("Content-Length") != null) {
          l = Long.parseLong(paramMap.get("Content-Length"));
        } else {
          l = 0L;
        } 
        paramChunkListener.onChunkProgress(paramMap, paramLong, l);
      } 
    } 
  }
  
  private Map<String, String> parseHeaders(f paramf) {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    for (String str : paramf.r().split("\r\n")) {
      int i = str.indexOf(":");
      if (i != -1)
        hashMap.put(str.substring(0, i).trim(), str.substring(i + 1).trim()); 
    } 
    return (Map)hashMap;
  }
  
  public boolean readAllParts(ChunkListener paramChunkListener) throws IOException {
    StringBuilder stringBuilder = new StringBuilder("\r\n--");
    stringBuilder.append(this.mBoundary);
    stringBuilder.append("\r\n");
    i i1 = i.encodeUtf8(stringBuilder.toString());
    stringBuilder = new StringBuilder("\r\n--");
    stringBuilder.append(this.mBoundary);
    stringBuilder.append("--\r\n");
    i i2 = i.encodeUtf8(stringBuilder.toString());
    i i3 = i.encodeUtf8("\r\n\r\n");
    f f = new f();
    long l4 = 0L;
    long l2 = l4;
    long l1 = l2;
    stringBuilder = null;
    long l3 = l2;
    for (l2 = l4;; l2 = l3) {
      boolean bool;
      Map<String, String> map;
      l4 = Math.max(l2 - i2.size(), l3);
      l2 = f.a(i1, l4);
      if (l2 == -1L) {
        l2 = f.a(i2, l4);
        bool = true;
      } else {
        bool = false;
      } 
      if (l2 == -1L) {
        l2 = f.b;
        if (stringBuilder == null) {
          long l = f.a(i3, l4);
          if (l >= 0L) {
            this.mSource.read(f, l);
            f f1 = new f();
            f.a(f1, l4, l - l4);
            l1 = f1.b + i3.size();
            map = parseHeaders(f1);
          } 
        } else {
          emitProgress(map, f.b - l1, false, paramChunkListener);
        } 
        if (this.mSource.read(f, 4096L) <= 0L)
          return false; 
        continue;
      } 
      if (l3 > 0L) {
        f f1 = new f();
        f.i(l3);
        f.read(f1, l2 - l3);
        emitProgress(map, f1.b - l1, true, paramChunkListener);
        emitChunk(f1, bool, paramChunkListener);
        l1 = 0L;
        map = null;
      } else {
        f.i(l2);
      } 
      if (bool)
        return true; 
      l3 = i1.size();
    } 
  }
  
  public static interface ChunkListener {
    void onChunkComplete(Map<String, String> param1Map, f param1f, boolean param1Boolean) throws IOException;
    
    void onChunkProgress(Map<String, String> param1Map, long param1Long1, long param1Long2) throws IOException;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\devsupport\MultipartStreamReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */