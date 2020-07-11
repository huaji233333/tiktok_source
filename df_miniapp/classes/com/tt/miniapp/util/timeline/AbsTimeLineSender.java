package com.tt.miniapp.util.timeline;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.AppInfoEntity;
import d.f.b.g;
import d.f.b.l;
import d.u;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONArray;

public abstract class AbsTimeLineSender implements Handler.Callback {
  public static final Companion Companion = new Companion(null);
  
  private static final int MSG_ADD_POINT;
  
  private static final int MSG_ADD_RAW_AND_FLUSH;
  
  private static final int MSG_FLUSH;
  
  private static final int MSG_RELEASE;
  
  public static int MSG_SEED;
  
  private final Looper looper;
  
  private final AtomicInteger mGroupIndex;
  
  private Handler mHandler;
  
  private final LinkedList<MpPoint> mStashPointList;
  
  private final LinkedList<String> mStashRawList;
  
  private final String mUniqueId;
  
  static {
    int i = MSG_SEED;
    MSG_SEED = i + 1;
    MSG_ADD_POINT = i;
    i = MSG_SEED;
    MSG_SEED = i + 1;
    MSG_FLUSH = i;
    i = MSG_SEED;
    MSG_SEED = i + 1;
    MSG_ADD_RAW_AND_FLUSH = i;
    i = MSG_SEED;
    MSG_SEED = i + 1;
    MSG_RELEASE = i;
  }
  
  public AbsTimeLineSender(Looper paramLooper) {
    this.looper = paramLooper;
    String str = AppbrandContext.getInst().getUniqueId();
    l.a(str, "AppbrandContext.getInst().getUniqueId()");
    this.mUniqueId = str;
    this.mGroupIndex = new AtomicInteger(0);
    this.mHandler = new Handler(this.looper, this);
    this.mStashPointList = new LinkedList<MpPoint>();
    this.mStashRawList = new LinkedList<String>();
  }
  
  private final void triggerFlushPoints() {
    if (!isReadySend())
      return; 
    if (this.mStashPointList.isEmpty())
      return; 
    JSONArray jSONArray = new JSONArray();
    Iterator<MpPoint> iterator = this.mStashPointList.iterator();
    while (iterator.hasNext())
      jSONArray.put(((MpPoint)iterator.next()).toJSON()); 
    this.mStashPointList.clear();
    realSendData(jSONArray);
  }
  
  private final void triggerFlushRaw() {
    if (!isReadySend())
      return; 
    for (String str : this.mStashRawList) {
      l.a(str, "p");
      realSendData(str);
    } 
    this.mStashRawList.clear();
  }
  
  public final void addPoint(MpPoint paramMpPoint) {
    l.b(paramMpPoint, "point");
    Message message = this.mHandler.obtainMessage(MSG_ADD_POINT, paramMpPoint);
    if (message != null)
      message.sendToTarget(); 
  }
  
  public final void flush() {
    Message message = this.mHandler.obtainMessage(MSG_FLUSH);
    if (message != null)
      message.sendToTarget(); 
  }
  
  public final Looper getLooper() {
    return this.looper;
  }
  
  protected final AtomicInteger getMGroupIndex() {
    return this.mGroupIndex;
  }
  
  protected final Handler getMHandler() {
    return this.mHandler;
  }
  
  protected final LinkedList<MpPoint> getMStashPointList() {
    return this.mStashPointList;
  }
  
  protected final LinkedList<String> getMStashRawList() {
    return this.mStashRawList;
  }
  
  protected final String getMUniqueId() {
    return this.mUniqueId;
  }
  
  public boolean handleMessage(Message paramMessage) {
    Object object;
    l.b(paramMessage, "msg");
    int i = paramMessage.what;
    if (i == MSG_ADD_POINT) {
      if (isEnableTrace()) {
        LinkedList<MpPoint> linkedList = this.mStashPointList;
        object = paramMessage.obj;
        if (object != null) {
          linkedList.add((MpPoint)object);
          if (isThreshold()) {
            triggerFlushPoints();
            return true;
          } 
        } else {
          throw new u("null cannot be cast to non-null type com.tt.miniapp.util.timeline.MpPoint");
        } 
      } 
    } else {
      if (i == MSG_FLUSH) {
        AppBrandLogger.d(getClass().getName(), new Object[] { "MSG_FLUSH" });
        triggerFlushRaw();
        triggerFlushPoints();
        return true;
      } 
      if (i == MSG_ADD_RAW_AND_FLUSH) {
        if (isEnableTrace()) {
          AppBrandLogger.d(getClass().getName(), new Object[] { "MSG_ADD_RAW_AND_FLUSH" });
          LinkedList<String> linkedList = this.mStashRawList;
          object = ((Message)object).obj;
          if (object != null) {
            linkedList.add((String)object);
            triggerFlushRaw();
            return true;
          } 
          throw new u("null cannot be cast to non-null type kotlin.String");
        } 
      } else if (i == MSG_RELEASE) {
        this.mStashRawList.clear();
        this.mStashPointList.clear();
        AppBrandLogger.d(getClass().getName(), new Object[] { "clear" });
      } 
    } 
    return true;
  }
  
  public abstract boolean isEnableTrace();
  
  public abstract boolean isReadySend();
  
  public abstract boolean isThreshold();
  
  public void onAppInfoInited(AppInfoEntity paramAppInfoEntity) {
    l.b(paramAppInfoEntity, "appInfo");
  }
  
  public abstract void realSendData(String paramString);
  
  public abstract void realSendData(JSONArray paramJSONArray);
  
  public final void release() {
    Message message = this.mHandler.obtainMessage(MSG_RELEASE);
    if (message != null)
      message.sendToTarget(); 
  }
  
  public final void sendMessage(int paramInt, Object paramObject) {
    paramObject = this.mHandler.obtainMessage(paramInt, paramObject);
    if (paramObject != null)
      paramObject.sendToTarget(); 
  }
  
  public final void sendPointsDirectly(String paramString) {
    l.b(paramString, "points");
    Message message = this.mHandler.obtainMessage(MSG_ADD_RAW_AND_FLUSH, paramString);
    if (message != null)
      message.sendToTarget(); 
  }
  
  protected final void setMHandler(Handler paramHandler) {
    l.b(paramHandler, "<set-?>");
    this.mHandler = paramHandler;
  }
  
  public static final class Companion {
    private Companion() {}
    
    public final int getMSG_SEED$miniapp_release() {
      return AbsTimeLineSender.MSG_SEED;
    }
    
    public final void setMSG_SEED$miniapp_release(int param1Int) {
      AbsTimeLineSender.MSG_SEED = param1Int;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\timeline\AbsTimeLineSender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */