package com.tt.miniapphost.process.base;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.tt.miniapphost.process.data.CrossProcessCallEntity;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;

public interface IMiniApp2HostBinderInterface extends IInterface {
  void asyncCall(CrossProcessCallEntity paramCrossProcessCallEntity, int paramInt) throws RemoteException;
  
  CrossProcessDataEntity syncCall(CrossProcessCallEntity paramCrossProcessCallEntity) throws RemoteException;
  
  public static abstract class Stub extends Binder implements IMiniApp2HostBinderInterface {
    public Stub() {
      attachInterface(this, "com.tt.miniapphost.process.base.IMiniApp2HostBinderInterface");
    }
    
    public static IMiniApp2HostBinderInterface asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("com.tt.miniapphost.process.base.IMiniApp2HostBinderInterface");
      return (iInterface instanceof IMiniApp2HostBinderInterface) ? (IMiniApp2HostBinderInterface)iInterface : new Proxy(param1IBinder);
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      if (param1Int1 != 1) {
        if (param1Int1 != 2) {
          if (param1Int1 != 1598968902)
            return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2); 
          param1Parcel2.writeString("com.tt.miniapphost.process.base.IMiniApp2HostBinderInterface");
          return true;
        } 
        param1Parcel1.enforceInterface("com.tt.miniapphost.process.base.IMiniApp2HostBinderInterface");
        asyncCall((CrossProcessCallEntity)CrossProcessCallEntity.CREATOR.createFromParcel(param1Parcel1), param1Parcel1.readInt());
        return true;
      } 
      param1Parcel1.enforceInterface("com.tt.miniapphost.process.base.IMiniApp2HostBinderInterface");
      CrossProcessDataEntity crossProcessDataEntity = syncCall((CrossProcessCallEntity)CrossProcessCallEntity.CREATOR.createFromParcel(param1Parcel1));
      param1Parcel2.writeNoException();
      if (crossProcessDataEntity != null) {
        param1Parcel2.writeInt(1);
        crossProcessDataEntity.writeToParcel(param1Parcel2, 1);
        return true;
      } 
      param1Parcel2.writeInt(0);
      return true;
    }
    
    static class Proxy implements IMiniApp2HostBinderInterface {
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public void asyncCall(CrossProcessCallEntity param2CrossProcessCallEntity, int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.tt.miniapphost.process.base.IMiniApp2HostBinderInterface");
          param2CrossProcessCallEntity.writeToParcel(parcel1, 0);
          parcel1.writeInt(param2Int);
          this.mRemote.transact(2, parcel1, parcel2, 1);
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public CrossProcessDataEntity syncCall(CrossProcessCallEntity param2CrossProcessCallEntity) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.tt.miniapphost.process.base.IMiniApp2HostBinderInterface");
          param2CrossProcessCallEntity.writeToParcel(parcel1, 0);
          this.mRemote.transact(1, parcel1, parcel2, 0);
          parcel2.readException();
          if (parcel2.readInt() == 1) {
            CrossProcessDataEntity crossProcessDataEntity = (CrossProcessDataEntity)CrossProcessDataEntity.CREATOR.createFromParcel(parcel2);
          } else {
            param2CrossProcessCallEntity = null;
          } 
          return (CrossProcessDataEntity)param2CrossProcessCallEntity;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
    }
  }
  
  static class Proxy implements IMiniApp2HostBinderInterface {
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public void asyncCall(CrossProcessCallEntity param1CrossProcessCallEntity, int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.tt.miniapphost.process.base.IMiniApp2HostBinderInterface");
        param1CrossProcessCallEntity.writeToParcel(parcel1, 0);
        parcel1.writeInt(param1Int);
        this.mRemote.transact(2, parcel1, parcel2, 1);
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public CrossProcessDataEntity syncCall(CrossProcessCallEntity param1CrossProcessCallEntity) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.tt.miniapphost.process.base.IMiniApp2HostBinderInterface");
        param1CrossProcessCallEntity.writeToParcel(parcel1, 0);
        this.mRemote.transact(1, parcel1, parcel2, 0);
        parcel2.readException();
        if (parcel2.readInt() == 1) {
          CrossProcessDataEntity crossProcessDataEntity = (CrossProcessDataEntity)CrossProcessDataEntity.CREATOR.createFromParcel(parcel2);
        } else {
          param1CrossProcessCallEntity = null;
        } 
        return (CrossProcessDataEntity)param1CrossProcessCallEntity;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\process\base\IMiniApp2HostBinderInterface.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */