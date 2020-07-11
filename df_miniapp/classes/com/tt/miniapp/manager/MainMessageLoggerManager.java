package com.tt.miniapp.manager;

import android.os.Looper;
import android.util.Printer;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.AppbrandServiceManager;
import com.tt.miniapp.LifeCycleManager;
import com.tt.miniapp.LifeCycleManager.LifecycleInterest;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MainMessageLoggerManager extends AppbrandServiceManager.ServiceBase implements Printer {
  private List<Printer> mPrinters = new CopyOnWriteArrayList<Printer>();
  
  public MainMessageLoggerManager(AppbrandApplicationImpl paramAppbrandApplicationImpl) {
    super(paramAppbrandApplicationImpl);
  }
  
  @LifecycleInterest({LifeCycleManager.LifeCycleEvent.ON_APP_CREATE})
  public void onAppCreated() {
    Looper.getMainLooper().setMessageLogging(this);
  }
  
  public void println(String paramString) {
    Iterator<Printer> iterator = this.mPrinters.iterator();
    while (iterator.hasNext())
      ((Printer)iterator.next()).println(paramString); 
  }
  
  public void register(Printer paramPrinter) {
    this.mPrinters.add(paramPrinter);
  }
  
  public void unregister(Printer paramPrinter) {
    this.mPrinters.remove(paramPrinter);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\manager\MainMessageLoggerManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */