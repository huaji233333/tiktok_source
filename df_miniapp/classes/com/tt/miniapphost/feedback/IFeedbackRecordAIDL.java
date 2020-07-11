package com.tt.miniapphost.feedback;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IFeedbackRecordAIDL extends IInterface {
  void start(IFeedbackRecordCallback paramIFeedbackRecordCallback) throws RemoteException;
  
  void stop(IFeedbackRecordCallback paramIFeedbackRecordCallback) throws RemoteException;
  
  public static abstract class Stub extends Binder implements IFeedbackRecordAIDL {
    public Stub() {
      attachInterface(this, "com.tt.miniapphost.feedback.IFeedbackRecordAIDL");
    }
    
    public static IFeedbackRecordAIDL asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("com.tt.miniapphost.feedback.IFeedbackRecordAIDL");
      return (iInterface != null && iInterface instanceof IFeedbackRecordAIDL) ? (IFeedbackRecordAIDL)iInterface : new Proxy(param1IBinder);
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      if (param1Int1 != 1) {
        if (param1Int1 != 2) {
          if (param1Int1 != 1598968902)
            return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2); 
          param1Parcel2.writeString("com.tt.miniapphost.feedback.IFeedbackRecordAIDL");
          return true;
        } 
        param1Parcel1.enforceInterface("com.tt.miniapphost.feedback.IFeedbackRecordAIDL");
        stop(IFeedbackRecordCallback.Stub.asInterface(param1Parcel1.readStrongBinder()));
        param1Parcel2.writeNoException();
        return true;
      } 
      param1Parcel1.enforceInterface("com.tt.miniapphost.feedback.IFeedbackRecordAIDL");
      start(IFeedbackRecordCallback.Stub.asInterface(param1Parcel1.readStrongBinder()));
      param1Parcel2.writeNoException();
      return true;
    }
    
    static class Proxy implements IFeedbackRecordAIDL {
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public String getInterfaceDescriptor() {
        return "com.tt.miniapphost.feedback.IFeedbackRecordAIDL";
      }
      
      public void start(IFeedbackRecordCallback param2IFeedbackRecordCallback) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.tt.miniapphost.feedback.IFeedbackRecordAIDL");
          if (param2IFeedbackRecordCallback != null) {
            IBinder iBinder = param2IFeedbackRecordCallback.asBinder();
          } else {
            param2IFeedbackRecordCallback = null;
          } 
          parcel1.writeStrongBinder((IBinder)param2IFeedbackRecordCallback);
          this.mRemote.transact(1, parcel1, parcel2, 0);
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void stop(IFeedbackRecordCallback param2IFeedbackRecordCallback) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.tt.miniapphost.feedback.IFeedbackRecordAIDL");
          if (param2IFeedbackRecordCallback != null) {
            IBinder iBinder = param2IFeedbackRecordCallback.asBinder();
          } else {
            param2IFeedbackRecordCallback = null;
          } 
          parcel1.writeStrongBinder((IBinder)param2IFeedbackRecordCallback);
          this.mRemote.transact(2, parcel1, parcel2, 0);
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
    }
  }
  
  static class Proxy implements IFeedbackRecordAIDL {
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public String getInterfaceDescriptor() {
      return "com.tt.miniapphost.feedback.IFeedbackRecordAIDL";
    }
    
    public void start(IFeedbackRecordCallback param1IFeedbackRecordCallback) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.tt.miniapphost.feedback.IFeedbackRecordAIDL");
        if (param1IFeedbackRecordCallback != null) {
          IBinder iBinder = param1IFeedbackRecordCallback.asBinder();
        } else {
          param1IFeedbackRecordCallback = null;
        } 
        parcel1.writeStrongBinder((IBinder)param1IFeedbackRecordCallback);
        this.mRemote.transact(1, parcel1, parcel2, 0);
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void stop(IFeedbackRecordCallback param1IFeedbackRecordCallback) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.tt.miniapphost.feedback.IFeedbackRecordAIDL");
        if (param1IFeedbackRecordCallback != null) {
          IBinder iBinder = param1IFeedbackRecordCallback.asBinder();
        } else {
          param1IFeedbackRecordCallback = null;
        } 
        parcel1.writeStrongBinder((IBinder)param1IFeedbackRecordCallback);
        this.mRemote.transact(2, parcel1, parcel2, 0);
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\feedback\IFeedbackRecordAIDL.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */