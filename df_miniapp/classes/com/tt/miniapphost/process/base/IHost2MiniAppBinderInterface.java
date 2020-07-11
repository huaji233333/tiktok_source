package com.tt.miniapphost.process.base;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.tt.miniapphost.process.data.CrossProcessCallEntity;

public interface IHost2MiniAppBinderInterface extends IInterface {
  void asyncCallMiniProcess(CrossProcessCallEntity paramCrossProcessCallEntity, int paramInt) throws RemoteException;
  
  public static abstract class Stub extends Binder implements IHost2MiniAppBinderInterface {
    public Stub() {
      attachInterface(this, "com.tt.miniapphost.process.base.IHost2MiniAppBinderInterface");
    }
    
    public static IHost2MiniAppBinderInterface asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("com.tt.miniapphost.process.base.IHost2MiniAppBinderInterface");
      return (iInterface instanceof IHost2MiniAppBinderInterface) ? (IHost2MiniAppBinderInterface)iInterface : new Proxy(param1IBinder);
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      if (param1Int1 != 1) {
        if (param1Int1 != 1598968902)
          return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2); 
        param1Parcel2.writeString("com.tt.miniapphost.process.base.IHost2MiniAppBinderInterface");
        return true;
      } 
      param1Parcel1.enforceInterface("com.tt.miniapphost.process.base.IHost2MiniAppBinderInterface");
      asyncCallMiniProcess((CrossProcessCallEntity)CrossProcessCallEntity.CREATOR.createFromParcel(param1Parcel1), param1Parcel1.readInt());
      return true;
    }
    
    static class Proxy implements IHost2MiniAppBinderInterface {
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public void asyncCallMiniProcess(CrossProcessCallEntity param2CrossProcessCallEntity, int param2Int) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        try {
          parcel.writeInterfaceToken("com.tt.miniapphost.process.base.IHost2MiniAppBinderInterface");
          param2CrossProcessCallEntity.writeToParcel(parcel, 0);
          parcel.writeInt(param2Int);
          this.mRemote.transact(1, parcel, null, 1);
          return;
        } finally {
          parcel.recycle();
        } 
      }
      
      public String getInterfaceDescriptor() {
        return "com.tt.miniapphost.process.base.IHost2MiniAppBinderInterface";
      }
    }
  }
  
  static class Proxy implements IHost2MiniAppBinderInterface {
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public void asyncCallMiniProcess(CrossProcessCallEntity param1CrossProcessCallEntity, int param1Int) throws RemoteException {
      Parcel parcel = Parcel.obtain();
      try {
        parcel.writeInterfaceToken("com.tt.miniapphost.process.base.IHost2MiniAppBinderInterface");
        param1CrossProcessCallEntity.writeToParcel(parcel, 0);
        parcel.writeInt(param1Int);
        this.mRemote.transact(1, parcel, null, 1);
        return;
      } finally {
        parcel.recycle();
      } 
    }
    
    public String getInterfaceDescriptor() {
      return "com.tt.miniapphost.process.base.IHost2MiniAppBinderInterface";
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\process\base\IHost2MiniAppBinderInterface.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */