package com.facebook.react.modules.storage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.facebook.common.e.a;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.GuardedAsyncTask;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.common.ModuleDataCleaner;
import java.util.HashSet;

@ReactModule(name = "AsyncSQLiteDBStorage")
public final class AsyncStorageModule extends ReactContextBaseJavaModule implements ModuleDataCleaner.Cleanable {
  public ReactDatabaseSupplier mReactDatabaseSupplier;
  
  private boolean mShuttingDown;
  
  public AsyncStorageModule(ReactApplicationContext paramReactApplicationContext) {
    super(paramReactApplicationContext);
    this.mReactDatabaseSupplier = ReactDatabaseSupplier.getInstance((Context)paramReactApplicationContext);
  }
  
  @ReactMethod
  public final void clear(final Callback callback) {
    (new GuardedAsyncTask<Void, Void>((ReactContext)getReactApplicationContext()) {
        protected void doInBackgroundGuarded(Void... param1VarArgs) {
          if (!AsyncStorageModule.this.mReactDatabaseSupplier.ensureDatabase()) {
            callback.invoke(new Object[] { AsyncStorageErrorUtil.getDBError(null) });
            return;
          } 
          try {
            AsyncStorageModule.this.mReactDatabaseSupplier.clear();
            callback.invoke(new Object[0]);
            return;
          } catch (Exception exception) {
            a.b("ReactNative", exception.getMessage(), exception);
            callback.invoke(new Object[] { AsyncStorageErrorUtil.getError(null, exception.getMessage()) });
            return;
          } 
        }
      }).execute((Object[])new Void[0]);
  }
  
  public final void clearSensitiveData() {
    this.mReactDatabaseSupplier.clearAndCloseDatabase();
  }
  
  public final boolean ensureDatabase() {
    return (!this.mShuttingDown && this.mReactDatabaseSupplier.ensureDatabase());
  }
  
  @ReactMethod
  public final void getAllKeys(final Callback callback) {
    (new GuardedAsyncTask<Void, Void>((ReactContext)getReactApplicationContext()) {
        protected void doInBackgroundGuarded(Void... param1VarArgs) {
          if (!AsyncStorageModule.this.ensureDatabase()) {
            callback.invoke(new Object[] { AsyncStorageErrorUtil.getDBError(null), null });
            return;
          } 
          WritableArray writableArray = Arguments.createArray();
          Cursor cursor = AsyncStorageModule.this.mReactDatabaseSupplier.get().query("catalystLocalStorage", new String[] { "key" }, null, null, null, null, null);
          try {
            if (cursor.moveToFirst()) {
              boolean bool;
              do {
                writableArray.pushString(cursor.getString(0));
                bool = cursor.moveToNext();
              } while (bool);
            } 
            cursor.close();
            callback.invoke(new Object[] { null, writableArray });
            return;
          } catch (Exception exception) {
            a.b("ReactNative", exception.getMessage(), exception);
            callback.invoke(new Object[] { AsyncStorageErrorUtil.getError(null, exception.getMessage()), null });
            cursor.close();
            return;
          } finally {}
          cursor.close();
          throw writableArray;
        }
      }).execute((Object[])new Void[0]);
  }
  
  public final String getName() {
    return "AsyncSQLiteDBStorage";
  }
  
  public final void initialize() {
    super.initialize();
    this.mShuttingDown = false;
  }
  
  @ReactMethod
  public final void multiGet(final ReadableArray keys, final Callback callback) {
    if (keys == null) {
      callback.invoke(new Object[] { AsyncStorageErrorUtil.getInvalidKeyError(null), null });
      return;
    } 
    (new GuardedAsyncTask<Void, Void>((ReactContext)getReactApplicationContext()) {
        protected void doInBackgroundGuarded(Void... param1VarArgs) {
          if (!AsyncStorageModule.this.ensureDatabase()) {
            callback.invoke(new Object[] { AsyncStorageErrorUtil.getDBError(null), null });
            return;
          } 
          HashSet<String> hashSet = new HashSet();
          WritableArray writableArray = Arguments.createArray();
          int i = 0;
          while (i < keys.size()) {
            int j = Math.min(keys.size() - i, 999);
            SQLiteDatabase sQLiteDatabase = AsyncStorageModule.this.mReactDatabaseSupplier.get();
            str = AsyncLocalStorageUtil.buildKeySelection(j);
            String[] arrayOfString = AsyncLocalStorageUtil.buildKeySelectionArgs(keys, i, j);
            Cursor cursor = sQLiteDatabase.query("catalystLocalStorage", new String[] { "key", "value" }, str, arrayOfString, null, null, null);
            hashSet.clear();
            try {
              if (cursor.getCount() != keys.size())
                for (int k = i; k < i + j; k++)
                  hashSet.add(keys.getString(k));  
              if (cursor.moveToFirst()) {
                boolean bool;
                do {
                  WritableArray writableArray1 = Arguments.createArray();
                  writableArray1.pushString(cursor.getString(0));
                  writableArray1.pushString(cursor.getString(1));
                  writableArray.pushArray(writableArray1);
                  hashSet.remove(cursor.getString(0));
                  bool = cursor.moveToNext();
                } while (bool);
              } 
              cursor.close();
              for (String str : hashSet) {
                WritableArray writableArray1 = Arguments.createArray();
                writableArray1.pushString(str);
                writableArray1.pushNull();
                writableArray.pushArray(writableArray1);
              } 
              hashSet.clear();
              i += 999;
              continue;
            } catch (Exception exception) {
              a.b("ReactNative", exception.getMessage(), exception);
              callback.invoke(new Object[] { AsyncStorageErrorUtil.getError(null, exception.getMessage()), null });
              cursor.close();
              return;
            } finally {}
            cursor.close();
            throw hashSet;
          } 
          callback.invoke(new Object[] { null, writableArray });
        }
      }).execute((Object[])new Void[0]);
  }
  
  @ReactMethod
  public final void multiMerge(final ReadableArray keyValueArray, final Callback callback) {
    (new GuardedAsyncTask<Void, Void>((ReactContext)getReactApplicationContext()) {
        protected void doInBackgroundGuarded(Void... param1VarArgs) {
          boolean bool = AsyncStorageModule.this.ensureDatabase();
          param1VarArgs = null;
          if (!bool) {
            callback.invoke(new Object[] { AsyncStorageErrorUtil.getDBError(null) });
            return;
          } 
          try {
            AsyncStorageModule.this.mReactDatabaseSupplier.get().beginTransaction();
            for (int i = 0; i < keyValueArray.size(); i++) {
              if (keyValueArray.getArray(i).size() != 2) {
                WritableMap writableMap = AsyncStorageErrorUtil.getInvalidValueError(null);
                try {
                  AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                  return;
                } catch (Exception exception) {
                  a.b("ReactNative", exception.getMessage(), exception);
                  if (writableMap == null)
                    AsyncStorageErrorUtil.getError(null, exception.getMessage()); 
                  return;
                } 
              } 
              if (keyValueArray.getArray(i).getString(0) == null) {
                WritableMap writableMap = AsyncStorageErrorUtil.getInvalidKeyError(null);
                try {
                  AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                  return;
                } catch (Exception exception) {
                  a.b("ReactNative", exception.getMessage(), exception);
                  if (writableMap == null)
                    AsyncStorageErrorUtil.getError(null, exception.getMessage()); 
                  return;
                } 
              } 
              if (keyValueArray.getArray(i).getString(1) == null) {
                WritableMap writableMap = AsyncStorageErrorUtil.getInvalidValueError(null);
                try {
                  AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                  return;
                } catch (Exception exception) {
                  a.b("ReactNative", exception.getMessage(), exception);
                  if (writableMap == null)
                    AsyncStorageErrorUtil.getError(null, exception.getMessage()); 
                  return;
                } 
              } 
              if (!AsyncLocalStorageUtil.mergeImpl(AsyncStorageModule.this.mReactDatabaseSupplier.get(), keyValueArray.getArray(i).getString(0), keyValueArray.getArray(i).getString(1))) {
                WritableMap writableMap = AsyncStorageErrorUtil.getDBError(null);
                try {
                  AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                  return;
                } catch (Exception exception) {
                  a.b("ReactNative", exception.getMessage(), exception);
                  if (writableMap == null)
                    AsyncStorageErrorUtil.getError(null, exception.getMessage()); 
                  return;
                } 
              } 
            } 
            AsyncStorageModule.this.mReactDatabaseSupplier.get().setTransactionSuccessful();
            try {
              AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
            } catch (Exception exception) {
              a.b("ReactNative", exception.getMessage(), exception);
              WritableMap writableMap = AsyncStorageErrorUtil.getError(null, exception.getMessage());
            } 
          } catch (Exception exception) {
            a.b("ReactNative", exception.getMessage(), exception);
            WritableMap writableMap = AsyncStorageErrorUtil.getError(null, exception.getMessage());
            try {
              AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
            } catch (Exception exception1) {
              a.b("ReactNative", exception1.getMessage(), exception1);
              if (writableMap == null)
                writableMap = AsyncStorageErrorUtil.getError(null, exception1.getMessage()); 
            } 
          } finally {}
          if (param1VarArgs != null) {
            callback.invoke(new Object[] { param1VarArgs });
            return;
          } 
          callback.invoke(new Object[0]);
        }
      }).execute((Object[])new Void[0]);
  }
  
  @ReactMethod
  public final void multiRemove(final ReadableArray keys, final Callback callback) {
    if (keys.size() == 0) {
      callback.invoke(new Object[] { AsyncStorageErrorUtil.getInvalidKeyError(null) });
      return;
    } 
    (new GuardedAsyncTask<Void, Void>((ReactContext)getReactApplicationContext()) {
        protected void doInBackgroundGuarded(Void... param1VarArgs) {
          boolean bool = AsyncStorageModule.this.ensureDatabase();
          param1VarArgs = null;
          if (!bool) {
            callback.invoke(new Object[] { AsyncStorageErrorUtil.getDBError(null) });
            return;
          } 
          try {
            AsyncStorageModule.this.mReactDatabaseSupplier.get().beginTransaction();
            for (int i = 0; i < keys.size(); i += 999) {
              int j = Math.min(keys.size() - i, 999);
              AsyncStorageModule.this.mReactDatabaseSupplier.get().delete("catalystLocalStorage", AsyncLocalStorageUtil.buildKeySelection(j), AsyncLocalStorageUtil.buildKeySelectionArgs(keys, i, j));
            } 
            AsyncStorageModule.this.mReactDatabaseSupplier.get().setTransactionSuccessful();
            try {
              AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
            } catch (Exception exception) {
              a.b("ReactNative", exception.getMessage(), exception);
              WritableMap writableMap = AsyncStorageErrorUtil.getError(null, exception.getMessage());
            } 
          } catch (Exception exception) {
            a.b("ReactNative", exception.getMessage(), exception);
            WritableMap writableMap = AsyncStorageErrorUtil.getError(null, exception.getMessage());
            try {
              AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
            } catch (Exception exception1) {
              a.b("ReactNative", exception1.getMessage(), exception1);
              if (writableMap == null)
                writableMap = AsyncStorageErrorUtil.getError(null, exception1.getMessage()); 
            } 
          } finally {}
          if (param1VarArgs != null) {
            callback.invoke(new Object[] { param1VarArgs });
            return;
          } 
          callback.invoke(new Object[0]);
        }
      }).execute((Object[])new Void[0]);
  }
  
  @ReactMethod
  public final void multiSet(final ReadableArray keyValueArray, final Callback callback) {
    if (keyValueArray.size() == 0) {
      callback.invoke(new Object[] { AsyncStorageErrorUtil.getInvalidKeyError(null) });
      return;
    } 
    (new GuardedAsyncTask<Void, Void>((ReactContext)getReactApplicationContext()) {
        protected void doInBackgroundGuarded(Void... param1VarArgs) {
          boolean bool = AsyncStorageModule.this.ensureDatabase();
          param1VarArgs = null;
          if (!bool) {
            callback.invoke(new Object[] { AsyncStorageErrorUtil.getDBError(null) });
            return;
          } 
          SQLiteStatement sQLiteStatement = AsyncStorageModule.this.mReactDatabaseSupplier.get().compileStatement("INSERT OR REPLACE INTO catalystLocalStorage VALUES (?, ?);");
          try {
            AsyncStorageModule.this.mReactDatabaseSupplier.get().beginTransaction();
            for (int i = 0; i < keyValueArray.size(); i++) {
              if (keyValueArray.getArray(i).size() != 2) {
                WritableMap writableMap = AsyncStorageErrorUtil.getInvalidValueError(null);
                try {
                  AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                  return;
                } catch (Exception null) {
                  a.b("ReactNative", exception.getMessage(), exception);
                  if (writableMap == null)
                    AsyncStorageErrorUtil.getError(null, exception.getMessage()); 
                  return;
                } 
              } 
              if (keyValueArray.getArray(i).getString(0) == null) {
                WritableMap writableMap = AsyncStorageErrorUtil.getInvalidKeyError(null);
                try {
                  AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                  return;
                } catch (Exception null) {
                  a.b("ReactNative", exception.getMessage(), exception);
                  if (writableMap == null)
                    AsyncStorageErrorUtil.getError(null, exception.getMessage()); 
                  return;
                } 
              } 
              if (keyValueArray.getArray(i).getString(1) == null) {
                WritableMap writableMap = AsyncStorageErrorUtil.getInvalidValueError(null);
                try {
                  AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                  return;
                } catch (Exception exception) {
                  a.b("ReactNative", exception.getMessage(), exception);
                  if (writableMap == null)
                    AsyncStorageErrorUtil.getError(null, exception.getMessage()); 
                  return;
                } 
              } 
              exception.clearBindings();
              exception.bindString(1, keyValueArray.getArray(i).getString(0));
              exception.bindString(2, keyValueArray.getArray(i).getString(1));
              exception.execute();
            } 
            AsyncStorageModule.this.mReactDatabaseSupplier.get().setTransactionSuccessful();
            try {
              AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
            } catch (Exception exception) {
              a.b("ReactNative", exception.getMessage(), exception);
              WritableMap writableMap = AsyncStorageErrorUtil.getError(null, exception.getMessage());
            } 
          } catch (Exception exception) {
            a.b("ReactNative", exception.getMessage(), exception);
            WritableMap writableMap = AsyncStorageErrorUtil.getError(null, exception.getMessage());
            try {
              AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
            } catch (Exception exception1) {
              a.b("ReactNative", exception1.getMessage(), exception1);
              if (writableMap == null)
                writableMap = AsyncStorageErrorUtil.getError(null, exception1.getMessage()); 
            } 
          } finally {}
          if (param1VarArgs != null) {
            callback.invoke(new Object[] { param1VarArgs });
            return;
          } 
          callback.invoke(new Object[0]);
        }
      }).execute((Object[])new Void[0]);
  }
  
  public final void onCatalystInstanceDestroy() {
    this.mShuttingDown = true;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\storage\AsyncStorageModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */