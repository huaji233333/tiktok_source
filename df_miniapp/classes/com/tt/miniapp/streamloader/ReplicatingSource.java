package com.tt.miniapp.streamloader;

import g.f;
import g.l;
import g.z;
import java.io.IOException;

public final class ReplicatingSource extends l {
  private final f readBuffer = new f();
  
  private final f replBuffer = new f();
  
  private boolean stopped;
  
  public ReplicatingSource(z paramz) {
    super(paramz);
  }
  
  public final void close() throws IOException {
    stop();
    super.close();
  }
  
  public final f getReplBuffer() {
    return this.replBuffer;
  }
  
  public final long read(f paramf, long paramLong) throws IOException {
    paramLong = super.read(this.readBuffer, paramLong);
    if (paramLong == -1L) {
      stop();
      return paramLong;
    } 
    synchronized (this.replBuffer) {
      if (!this.stopped)
        this.readBuffer.a(this.replBuffer, 0L, paramLong); 
      paramf.a(this.readBuffer, paramLong);
      return paramLong;
    } 
  }
  
  public final void stop() {
    synchronized (this.replBuffer) {
      this.stopped = true;
      this.replBuffer.close();
      return;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\streamloader\ReplicatingSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */