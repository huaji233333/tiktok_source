package com.tt.miniapp.msg.file;

import com.tt.miniapphost.AppBrandLogger;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.json.JSONObject;

public class Stats {
  private int lastAccessedTime;
  
  private int lastModifiedTime;
  
  private int mode;
  
  private int size;
  
  public static Stats getFileStats(String paramString) {
    if (!(new File(paramString)).exists())
      return null; 
    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(20);
    if (!getFileStatsInfo(paramString, byteBuffer))
      return null; 
    Stats stats = new Stats();
    byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
    stats.mode = byteBuffer.getInt(0);
    stats.size = byteBuffer.getInt(4);
    stats.lastAccessedTime = byteBuffer.getInt(8);
    stats.lastModifiedTime = byteBuffer.getInt(12);
    return stats;
  }
  
  private static native boolean getFileStatsInfo(String paramString, ByteBuffer paramByteBuffer);
  
  public long getLastAccessedTime() {
    return this.lastAccessedTime;
  }
  
  public long getLastModifiedTime() {
    return this.lastModifiedTime;
  }
  
  public int getMode() {
    return this.mode;
  }
  
  public long getSize() {
    return this.size;
  }
  
  public JSONObject toJson() {
    try {
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("mode", this.mode);
      jSONObject.put("size", this.size);
      jSONObject.put("lastAccessedTime", this.lastAccessedTime);
      jSONObject.put("lastModifiedTime", this.lastModifiedTime);
      return jSONObject;
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "Stats", exception.getStackTrace());
      return null;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\file\Stats.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */