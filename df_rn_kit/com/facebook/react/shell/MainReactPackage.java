package com.facebook.react.shell;

import android.content.Context;
import android.preference.PreferenceManager;
import com.facebook.imagepipeline.e.i;
import com.facebook.react.LazyReactPackage;
import com.facebook.react.animated.NativeAnimatedModule;
import com.facebook.react.bridge.ModuleSpec;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.flat.FlatARTSurfaceViewManager;
import com.facebook.react.flat.RCTImageViewManager;
import com.facebook.react.flat.RCTModalHostManager;
import com.facebook.react.flat.RCTRawTextManager;
import com.facebook.react.flat.RCTTextInlineImageManager;
import com.facebook.react.flat.RCTTextInputManager;
import com.facebook.react.flat.RCTTextManager;
import com.facebook.react.flat.RCTViewManager;
import com.facebook.react.flat.RCTViewPagerManager;
import com.facebook.react.flat.RCTVirtualTextManager;
import com.facebook.react.module.model.ReactModuleInfoProvider;
import com.facebook.react.modules.accessibilityinfo.AccessibilityInfoModule;
import com.facebook.react.modules.appstate.AppStateModule;
import com.facebook.react.modules.blob.BlobModule;
import com.facebook.react.modules.blob.FileReaderModule;
import com.facebook.react.modules.camera.CameraRollManager;
import com.facebook.react.modules.camera.ImageEditingManager;
import com.facebook.react.modules.camera.ImageStoreManager;
import com.facebook.react.modules.clipboard.ClipboardModule;
import com.facebook.react.modules.datepicker.DatePickerDialogModule;
import com.facebook.react.modules.dialog.DialogModule;
import com.facebook.react.modules.fresco.FrescoModule;
import com.facebook.react.modules.i18nmanager.I18nManagerModule;
import com.facebook.react.modules.image.ImageLoaderModule;
import com.facebook.react.modules.intent.IntentModule;
import com.facebook.react.modules.jsruntask.JSRunTaskModule;
import com.facebook.react.modules.location.LocationModule;
import com.facebook.react.modules.netinfo.NetInfoModule;
import com.facebook.react.modules.network.NetworkingModule;
import com.facebook.react.modules.permissions.PermissionsModule;
import com.facebook.react.modules.share.ShareModule;
import com.facebook.react.modules.statusbar.StatusBarModule;
import com.facebook.react.modules.storage.AsyncStorageModule;
import com.facebook.react.modules.timepicker.TimePickerDialogModule;
import com.facebook.react.modules.toast.ToastModule;
import com.facebook.react.modules.vibration.VibrationModule;
import com.facebook.react.modules.websocket.WebSocketModule;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.views.art.ARTRenderableViewManager;
import com.facebook.react.views.art.ARTSurfaceViewManager;
import com.facebook.react.views.checkbox.ReactCheckBoxManager;
import com.facebook.react.views.drawer.ReactDrawerLayoutManager;
import com.facebook.react.views.image.ReactImageManager;
import com.facebook.react.views.modal.TranslucentModalHostManager;
import com.facebook.react.views.picker.ReactDialogPickerManager;
import com.facebook.react.views.picker.ReactDropdownPickerManager;
import com.facebook.react.views.progressbar.ReactProgressBarViewManager;
import com.facebook.react.views.scroll.ReactHorizontalScrollContainerViewManager;
import com.facebook.react.views.scroll.ReactHorizontalScrollViewManager;
import com.facebook.react.views.scroll.ReactScrollViewManager;
import com.facebook.react.views.slider.ReactSliderManager;
import com.facebook.react.views.swiperefresh.SwipeRefreshLayoutManager;
import com.facebook.react.views.switchview.ReactSwitchManager;
import com.facebook.react.views.text.ReactRawTextManager;
import com.facebook.react.views.text.ReactTextViewManager;
import com.facebook.react.views.text.ReactVirtualTextViewManager;
import com.facebook.react.views.text.frescosupport.FrescoBasedReactTextInlineImageViewManager;
import com.facebook.react.views.textinput.ReactTextInputManager;
import com.facebook.react.views.toolbar.ReactToolbarManager;
import com.facebook.react.views.view.ReactViewManager;
import com.facebook.react.views.viewpager.ReactViewPagerManager;
import com.facebook.react.views.webview.ReactWebViewManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.a.a;

public class MainReactPackage extends LazyReactPackage {
  public MainPackageConfig mConfig;
  
  public MainReactPackage() {}
  
  public MainReactPackage(MainPackageConfig paramMainPackageConfig) {
    this.mConfig = paramMainPackageConfig;
  }
  
  public List<ViewManager> createViewManagers(ReactApplicationContext paramReactApplicationContext) {
    ArrayList<ARTRenderableViewManager> arrayList = new ArrayList();
    arrayList.add(ARTRenderableViewManager.createARTGroupViewManager());
    arrayList.add(ARTRenderableViewManager.createARTShapeViewManager());
    arrayList.add(ARTRenderableViewManager.createARTTextViewManager());
    arrayList.add(new ReactCheckBoxManager());
    arrayList.add(new ReactDialogPickerManager());
    arrayList.add(new ReactDrawerLayoutManager());
    arrayList.add(new ReactDropdownPickerManager());
    arrayList.add(new ReactHorizontalScrollViewManager());
    arrayList.add(new ReactHorizontalScrollContainerViewManager());
    arrayList.add(new ReactProgressBarViewManager());
    arrayList.add(new ReactScrollViewManager());
    arrayList.add(new ReactSliderManager());
    arrayList.add(new ReactSwitchManager());
    arrayList.add(new ReactToolbarManager());
    arrayList.add(new ReactWebViewManager());
    arrayList.add(new SwipeRefreshLayoutManager());
    if (PreferenceManager.getDefaultSharedPreferences((Context)paramReactApplicationContext).getBoolean("flat_uiimplementation", false)) {
      arrayList.add(new FlatARTSurfaceViewManager());
      arrayList.add(new RCTTextInlineImageManager());
      arrayList.add(new RCTImageViewManager());
      arrayList.add(new RCTModalHostManager());
      arrayList.add(new RCTRawTextManager());
      arrayList.add(new RCTTextInputManager());
      arrayList.add(new RCTTextManager());
      arrayList.add(new RCTViewManager());
      arrayList.add(new RCTViewPagerManager());
      arrayList.add(new RCTVirtualTextManager());
      return (List)arrayList;
    } 
    arrayList.add(new ARTSurfaceViewManager());
    arrayList.add(new FrescoBasedReactTextInlineImageViewManager());
    arrayList.add(new ReactImageManager());
    arrayList.add(new TranslucentModalHostManager());
    arrayList.add(new ReactRawTextManager());
    arrayList.add(new ReactTextInputManager());
    arrayList.add(new ReactTextViewManager());
    arrayList.add(new ReactViewManager());
    arrayList.add(new ReactViewPagerManager());
    arrayList.add(new ReactVirtualTextViewManager());
    return (List)arrayList;
  }
  
  public List<ModuleSpec> getNativeModules(final ReactApplicationContext context) {
    return Arrays.asList(new ModuleSpec[] { 
          ModuleSpec.nativeModuleSpec(AccessibilityInfoModule.class, new a<NativeModule>() {
              public NativeModule get() {
                return (NativeModule)new AccessibilityInfoModule(context);
              }
            }), ModuleSpec.nativeModuleSpec(AppStateModule.class, new a<NativeModule>() {
              public NativeModule get() {
                return (NativeModule)new AppStateModule(context);
              }
            }), ModuleSpec.nativeModuleSpec(BlobModule.class, new a<NativeModule>() {
              public NativeModule get() {
                return (NativeModule)new BlobModule(context);
              }
            }), ModuleSpec.nativeModuleSpec(FileReaderModule.class, new a<NativeModule>() {
              public NativeModule get() {
                return (NativeModule)new FileReaderModule(context);
              }
            }), ModuleSpec.nativeModuleSpec(AsyncStorageModule.class, new a<NativeModule>() {
              public NativeModule get() {
                return (NativeModule)new AsyncStorageModule(context);
              }
            }), ModuleSpec.nativeModuleSpec(CameraRollManager.class, new a<NativeModule>() {
              public NativeModule get() {
                return (NativeModule)new CameraRollManager(context);
              }
            }), ModuleSpec.nativeModuleSpec(ClipboardModule.class, new a<NativeModule>() {
              public NativeModule get() {
                return (NativeModule)new ClipboardModule((Context)context);
              }
            }), ModuleSpec.nativeModuleSpec(DatePickerDialogModule.class, new a<NativeModule>() {
              public NativeModule get() {
                return (NativeModule)new DatePickerDialogModule(context);
              }
            }), ModuleSpec.nativeModuleSpec(DialogModule.class, new a<NativeModule>() {
              public NativeModule get() {
                return (NativeModule)new DialogModule(context);
              }
            }), ModuleSpec.nativeModuleSpec(FrescoModule.class, new a<NativeModule>() {
              public NativeModule get() {
                i i;
                ReactApplicationContext reactApplicationContext = context;
                if (MainReactPackage.this.mConfig != null) {
                  i = MainReactPackage.this.mConfig.getFrescoConfig();
                } else {
                  i = null;
                } 
                return (NativeModule)new FrescoModule(reactApplicationContext, true, i);
              }
            }), 
          ModuleSpec.nativeModuleSpec(I18nManagerModule.class, new a<NativeModule>() {
              public NativeModule get() {
                return (NativeModule)new I18nManagerModule((Context)context);
              }
            }), ModuleSpec.nativeModuleSpec(ImageEditingManager.class, new a<NativeModule>() {
              public NativeModule get() {
                return (NativeModule)new ImageEditingManager(context);
              }
            }), ModuleSpec.nativeModuleSpec(ImageLoaderModule.class, new a<NativeModule>() {
              public NativeModule get() {
                return (NativeModule)new ImageLoaderModule(context);
              }
            }), ModuleSpec.nativeModuleSpec(ImageStoreManager.class, new a<NativeModule>() {
              public NativeModule get() {
                return (NativeModule)new ImageStoreManager(context);
              }
            }), ModuleSpec.nativeModuleSpec(IntentModule.class, new a<NativeModule>() {
              public NativeModule get() {
                return (NativeModule)new IntentModule(context);
              }
            }), ModuleSpec.nativeModuleSpec(JSRunTaskModule.class, new a<NativeModule>() {
              public NativeModule get() {
                return (NativeModule)new JSRunTaskModule(context);
              }
            }), ModuleSpec.nativeModuleSpec(LocationModule.class, new a<NativeModule>() {
              public NativeModule get() {
                return (NativeModule)new LocationModule(context);
              }
            }), ModuleSpec.nativeModuleSpec(NativeAnimatedModule.class, new a<NativeModule>() {
              public NativeModule get() {
                return (NativeModule)new NativeAnimatedModule(context);
              }
            }), ModuleSpec.nativeModuleSpec(NetworkingModule.class, new a<NativeModule>() {
              public NativeModule get() {
                return (NativeModule)new NetworkingModule(context);
              }
            }), ModuleSpec.nativeModuleSpec(NetInfoModule.class, new a<NativeModule>() {
              public NativeModule get() {
                return (NativeModule)new NetInfoModule(context);
              }
            }), 
          ModuleSpec.nativeModuleSpec(PermissionsModule.class, new a<NativeModule>() {
              public NativeModule get() {
                return (NativeModule)new PermissionsModule(context);
              }
            }), ModuleSpec.nativeModuleSpec(ShareModule.class, new a<NativeModule>() {
              public NativeModule get() {
                return (NativeModule)new ShareModule(context);
              }
            }), ModuleSpec.nativeModuleSpec(StatusBarModule.class, new a<NativeModule>() {
              public NativeModule get() {
                return (NativeModule)new StatusBarModule(context);
              }
            }), ModuleSpec.nativeModuleSpec(TimePickerDialogModule.class, new a<NativeModule>() {
              public NativeModule get() {
                return (NativeModule)new TimePickerDialogModule(context);
              }
            }), ModuleSpec.nativeModuleSpec(ToastModule.class, new a<NativeModule>() {
              public NativeModule get() {
                return (NativeModule)new ToastModule(context);
              }
            }), ModuleSpec.nativeModuleSpec(VibrationModule.class, new a<NativeModule>() {
              public NativeModule get() {
                return (NativeModule)new VibrationModule(context);
              }
            }), ModuleSpec.nativeModuleSpec(WebSocketModule.class, new a<NativeModule>() {
              public NativeModule get() {
                return (NativeModule)new WebSocketModule(context);
              }
            }) });
  }
  
  public ReactModuleInfoProvider getReactModuleInfoProvider() {
    return LazyReactPackage.getReactModuleInfoProviderViaReflection(this);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\shell\MainReactPackage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */