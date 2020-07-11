package com.tt.miniapp.streamloader.cache;

import android.os.Handler;
import com.tt.miniapp.thread.HandlerThreadUtil;
import com.tt.miniapp.ttapkgdecoder.TTAPkgFile;
import com.tt.miniapphost.util.IOUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;

public class StreamCache {
  public Handler mH = new Handler(HandlerThreadUtil.getNewHandlerThread("StreamCache").getLooper());
  
  private ConcurrentHashMap<String, StreamObject> map = new ConcurrentHashMap<String, StreamObject>();
  
  public void closeStream(TTAPkgFile paramTTAPkgFile) {
    String str = paramTTAPkgFile.getFileName();
    StreamObject streamObject2 = this.map.get(str);
    StreamObject streamObject1 = streamObject2;
    if (streamObject2 == null) {
      streamObject1 = new StreamObject(str);
      streamObject1.take();
      streamObject1 = this.map.putIfAbsent(str, streamObject1);
    } 
    if (streamObject1 != null)
      streamObject1.close(); 
  }
  
  public StreamObject prepareStream(TTAPkgFile paramTTAPkgFile, byte[] paramArrayOfbyte) {
    try {
      String str = paramTTAPkgFile.getFileName();
      StreamObject streamObject = this.map.get(str);
      if (streamObject == null) {
        streamObject = new StreamObject(str);
        StreamObject streamObject1 = this.map.putIfAbsent(str, streamObject);
        if (streamObject1 != null) {
          streamObject1.prepare(paramArrayOfbyte);
          return streamObject1;
        } 
        streamObject.prepare(paramArrayOfbyte);
        return streamObject;
      } 
      streamObject.prepare(paramArrayOfbyte);
      return streamObject;
    } catch (Exception exception) {
      return null;
    } 
  }
  
  public void pushToStream(TTAPkgFile paramTTAPkgFile, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    StreamObject streamObject = this.map.get(paramTTAPkgFile.getFileName());
    if (streamObject == null)
      return; 
    streamObject.write(paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  void release() {
    Handler handler = this.mH;
    if (handler != null && handler.getLooper() != null)
      this.mH.getLooper().quitSafely(); 
  }
  
  public InputStream takeStream(TTAPkgFile paramTTAPkgFile) {
    String str = paramTTAPkgFile.getFileName();
    StreamObject streamObject2 = this.map.get(str);
    StreamObject streamObject1 = streamObject2;
    if (streamObject2 == null)
      streamObject1 = prepareStream(paramTTAPkgFile, null); 
    return (streamObject1 == null) ? null : (streamObject1.take() ? streamObject1.pis : null);
  }
  
  class StreamObject {
    volatile int byteAvailable;
    
    volatile int byteWritten;
    
    byte[] bytes;
    
    boolean isClosed;
    
    boolean isTaken;
    
    String name;
    
    BufferPipedInputStream pis;
    
    BufferPipedOutputStream pos;
    
    StreamObject(String param1String) {
      this.name = param1String;
    }
    
    private void flush() {
      StreamCache.this.mH.post(new Runnable() {
            public void run() {
              if (StreamCache.StreamObject.this.byteWritten < StreamCache.StreamObject.this.byteAvailable)
                try {
                  int i = StreamCache.StreamObject.this.byteAvailable;
                  StreamCache.StreamObject.this.pos.write(StreamCache.StreamObject.this.bytes, StreamCache.StreamObject.this.byteWritten, i - StreamCache.StreamObject.this.byteWritten);
                  StreamCache.StreamObject.this.byteWritten = i;
                  return;
                } catch (IOException iOException) {
                  return;
                }  
            }
          });
    }
    
    void close() {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield isClosed : Z
      //   6: ifne -> 40
      //   9: aload_0
      //   10: iconst_1
      //   11: putfield isClosed : Z
      //   14: aload_0
      //   15: getfield pos : Lcom/tt/miniapp/streamloader/cache/BufferPipedOutputStream;
      //   18: ifnull -> 40
      //   21: aload_0
      //   22: getfield this$0 : Lcom/tt/miniapp/streamloader/cache/StreamCache;
      //   25: getfield mH : Landroid/os/Handler;
      //   28: new com/tt/miniapp/streamloader/cache/StreamCache$StreamObject$1
      //   31: dup
      //   32: aload_0
      //   33: invokespecial <init> : (Lcom/tt/miniapp/streamloader/cache/StreamCache$StreamObject;)V
      //   36: invokevirtual post : (Ljava/lang/Runnable;)Z
      //   39: pop
      //   40: aload_0
      //   41: monitorexit
      //   42: return
      //   43: astore_1
      //   44: aload_0
      //   45: monitorexit
      //   46: aload_1
      //   47: athrow
      // Exception table:
      //   from	to	target	type
      //   2	40	43	finally
    }
    
    void prepare(byte[] param1ArrayOfbyte) throws IOException {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield pos : Lcom/tt/miniapp/streamloader/cache/BufferPipedOutputStream;
      //   6: ifnonnull -> 35
      //   9: aload_0
      //   10: new com/tt/miniapp/streamloader/cache/BufferPipedInputStream
      //   13: dup
      //   14: invokespecial <init> : ()V
      //   17: putfield pis : Lcom/tt/miniapp/streamloader/cache/BufferPipedInputStream;
      //   20: aload_0
      //   21: new com/tt/miniapp/streamloader/cache/BufferPipedOutputStream
      //   24: dup
      //   25: aload_0
      //   26: getfield pis : Lcom/tt/miniapp/streamloader/cache/BufferPipedInputStream;
      //   29: invokespecial <init> : (Lcom/tt/miniapp/streamloader/cache/BufferPipedInputStream;)V
      //   32: putfield pos : Lcom/tt/miniapp/streamloader/cache/BufferPipedOutputStream;
      //   35: aload_0
      //   36: monitorexit
      //   37: return
      //   38: astore_1
      //   39: aload_0
      //   40: monitorexit
      //   41: aload_1
      //   42: athrow
      // Exception table:
      //   from	to	target	type
      //   2	35	38	finally
    }
    
    boolean take() {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield isClosed : Z
      //   6: ifne -> 32
      //   9: aload_0
      //   10: getfield isTaken : Z
      //   13: ifeq -> 19
      //   16: goto -> 32
      //   19: aload_0
      //   20: iconst_1
      //   21: putfield isTaken : Z
      //   24: aload_0
      //   25: invokespecial flush : ()V
      //   28: aload_0
      //   29: monitorexit
      //   30: iconst_1
      //   31: ireturn
      //   32: aload_0
      //   33: monitorexit
      //   34: iconst_0
      //   35: ireturn
      //   36: astore_1
      //   37: aload_0
      //   38: monitorexit
      //   39: aload_1
      //   40: athrow
      // Exception table:
      //   from	to	target	type
      //   2	16	36	finally
      //   19	28	36	finally
    }
    
    void write(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield isClosed : Z
      //   6: ifne -> 32
      //   9: aload_0
      //   10: aload_1
      //   11: putfield bytes : [B
      //   14: aload_0
      //   15: iload_2
      //   16: iload_3
      //   17: iadd
      //   18: putfield byteAvailable : I
      //   21: aload_0
      //   22: getfield isTaken : Z
      //   25: ifeq -> 32
      //   28: aload_0
      //   29: invokespecial flush : ()V
      //   32: aload_0
      //   33: monitorexit
      //   34: return
      //   35: astore_1
      //   36: aload_0
      //   37: monitorexit
      //   38: aload_1
      //   39: athrow
      // Exception table:
      //   from	to	target	type
      //   2	32	35	finally
    }
  }
  
  class null implements Runnable {
    public void run() {
      IOUtils.close(this.this$1.pos);
    }
  }
  
  class null implements Runnable {
    public void run() {
      if (this.this$1.byteWritten < this.this$1.byteAvailable)
        try {
          int i = this.this$1.byteAvailable;
          this.this$1.pos.write(this.this$1.bytes, this.this$1.byteWritten, i - this.this$1.byteWritten);
          this.this$1.byteWritten = i;
          return;
        } catch (IOException iOException) {
          return;
        }  
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\streamloader\cache\StreamCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */