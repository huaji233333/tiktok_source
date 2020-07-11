package com.tt.miniapphost.feedback;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IFeedbackRecordCallback extends IInterface {
  void onFail(String paramString) throws RemoteException;
  
  void onSuccess(String paramString) throws RemoteException;
  
  public static abstract class Stub extends Binder implements IFeedbackRecordCallback {
    public Stub() {
      attachInterface(this, "com.tt.miniapphost.feedback.IFeedbackRecordCallback");
    }
    
    public static IFeedbackRecordCallback asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("com.tt.miniapphost.feedback.IFeedbackRecordCallback");
      return (iInterface != null && iInterface instanceof IFeedbackRecordCallback) ? (IFeedbackRecordCallback)iInterface : new Proxy(param1IBinder);
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      if (param1Int1 != 1) {
        if (param1Int1 != 2) {
          if (param1Int1 != 1598968902)
            return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2); 
          param1Parcel2.writeString("com.tt.miniapphost.feedback.IFeedbackRecordCallback");
          return true;
        } 
        param1Parcel1.enforceInterface("com.tt.miniapphost.feedback.IFeedbackRecordCallback");
        onFail(param1Parcel1.readString());
        param1Parcel2.writeNoException();
        return true;
      } 
      param1Parcel1.enforceInterface("com.tt.miniapphost.feedback.IFeedbackRecordCallback");
      onSuccess(param1Parcel1.readString());
      param1Parcel2.writeNoException();
      return true;
    }
    
    static class Proxy implements IFeedbackRecordCallback {
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public String getInterfaceDescriptor() {
        return "com.tt.miniapphost.feedback.IFeedbackRecordCallback";
      }
      
      public void onFail(String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.tt.miniapphost.feedback.IFeedbackRecordCallback");
          parcel1.writeString(param2String);
          this.mRemote.transact(2, parcel1, parcel2, 0);
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void onSuccess(String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.tt.miniapphost.feedback.IFeedbackRecordCallback");
          parcel1.writeString(param2String);
          this.mRemote.transact(1, parcel1, parcel2, 0);
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
    }
  }
  
  static class Proxy implements IFeedbackRecordCallback {
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public String getInterfaceDescriptor() {
      return "com.tt.miniapphost.feedback.IFeedbackRecordCallback";
    }
    
    public void onFail(String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.tt.miniapphost.feedback.IFeedbackRecordCallback");
        parcel1.writeString(param1String);
        this.mRemote.transact(2, parcel1, parcel2, 0);
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void onSuccess(String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.tt.miniapphost.feedback.IFeedbackRecordCallback");
        parcel1.writeString(param1String);
        this.mRemote.transact(1, parcel1, parcel2, 0);
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\feedback\IFeedbackRecordCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */