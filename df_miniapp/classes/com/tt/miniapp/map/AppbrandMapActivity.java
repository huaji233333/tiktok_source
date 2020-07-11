package com.tt.miniapp.map;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.c;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tt.miniapp.ImmersedStatusBarHelper;
import com.tt.miniapp.map.model.ThirdMapModel;
import com.tt.miniapp.map.utils.MapUtils;
import com.tt.miniapp.permission.PermissionsManager;
import com.tt.miniapp.permission.PermissionsResultAction;
import com.tt.miniapp.view.swipeback.SwipeBackActivity;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.util.UIUtils;
import com.tt.option.m.a;
import com.tt.option.m.c;
import com.tt.option.m.d;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class AppbrandMapActivity extends SwipeBackActivity {
  public boolean isClickPosition;
  
  private TextView mAddress;
  
  private View mBack;
  
  private String mDestinationAddress;
  
  private Double mDestinationLat;
  
  private Double mDestinationLon;
  
  private String mDestinationName;
  
  public Dialog mDialog;
  
  public ThirdMapModel mEndModel;
  
  public c mEndPoint;
  
  private ImmersedStatusBarHelper mImmersedStatusBarHelper;
  
  public Location mLastLocatingLocation;
  
  private TextView mLocating;
  
  public ImageView mLocation;
  
  private int mLocationErrorCode;
  
  private TextView mLocationName;
  
  public a mMapInstance;
  
  private ImageView mNav;
  
  private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {
      public boolean onTouch(View param1View, MotionEvent param1MotionEvent) {
        int i = param1MotionEvent.getAction();
        if (i != 0) {
          if (i == 1 || i == 3)
            param1View.setAlpha(1.0F); 
        } else {
          param1View.setAlpha(0.5F);
        } 
        return false;
      }
    };
  
  public c mOriginPoint;
  
  public int mScale = 18;
  
  public TextView mShowLines;
  
  public ThirdMapModel mStartModel;
  
  private View mapView;
  
  private void addDestinationMaker(c paramc) {
    (new d()).a(paramc).a(BitmapFactory.decodeResource(getResources(), 2097479757));
  }
  
  private void bindView() {
    this.mBack = findViewById(2097545333);
    this.mLocation = (ImageView)findViewById(2097545342);
    this.mNav = (ImageView)findViewById(2097545344);
    this.mLocationName = (TextView)findViewById(2097545343);
    this.mAddress = (TextView)findViewById(2097545331);
    this.mLocating = (TextView)findViewById(2097545345);
    UIUtils.setViewVisibility((View)this.mLocationName, 8);
    UIUtils.setViewVisibility((View)this.mAddress, 8);
    UIUtils.setViewVisibility((View)this.mLocating, 0);
    this.mLocating.setText(getResources().getString(2097741941));
    this.mBack.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            AppbrandMapActivity.this.finish();
          }
        });
    this.mLocation.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            AppbrandMapActivity appbrandMapActivity = AppbrandMapActivity.this;
            appbrandMapActivity.isClickPosition = true;
            if (appbrandMapActivity.mOriginPoint != null && !Objects.equals(AppbrandMapActivity.this.mLocation.getDrawable().getCurrent().getConstantState(), c.a((Context)AppbrandMapActivity.this, 2097479761).getConstantState()))
              AppbrandMapActivity.this.mLocation.setImageResource(2097479761); 
            AppbrandMapActivity.this.startLocation();
          }
        });
    this.mLocation.setOnTouchListener(this.mOnTouchListener);
    this.mNav.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            AppbrandMapActivity.this.openBottomDialog();
          }
        });
  }
  
  private void requestPermission() {
    HashSet<String> hashSet = new HashSet();
    hashSet.add("android.permission.ACCESS_COARSE_LOCATION");
    hashSet.add("android.permission.ACCESS_FINE_LOCATION");
    PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult((Activity)this, hashSet, new PermissionsResultAction() {
          public void onDenied(String param1String) {
            AppbrandMapActivity.this.moveCamera();
            AppbrandMapActivity.this.initEndPoint();
          }
          
          public void onGranted() {
            try {
              AppbrandMapActivity.this.moveCamera();
              AppbrandMapActivity.this.initEndPoint();
              return;
            } catch (Exception exception) {
              AppBrandLogger.e("tma_AppbrandMapActivity", new Object[] { "", exception });
              return;
            } 
          }
        });
  }
  
  protected ImmersedStatusBarHelper.ImmersedStatusBarConfig getImmersedStatusBarConfig() {
    return (new ImmersedStatusBarHelper.ImmersedStatusBarConfig()).setFitsSystemWindows(true).setStatusBarColor(Color.parseColor("#717171"));
  }
  
  public void initEndPoint() {
    if (this.mDestinationLon.doubleValue() == 0.0D && this.mDestinationLat.doubleValue() == 0.0D) {
      if (TextUtils.isEmpty(this.mDestinationName))
        showLoadingStatus(getString(2097741942)); 
    } else {
      c c1 = new c(this.mDestinationLat.doubleValue(), this.mDestinationLon.doubleValue());
      c c2 = this.mEndPoint;
      if (c2 == null) {
        this.mEndPoint = new c(this.mDestinationLat.doubleValue(), this.mDestinationLon.doubleValue());
      } else {
        c2.a = this.mDestinationLat.doubleValue();
        this.mEndPoint.b = this.mDestinationLon.doubleValue();
      } 
      new c(this.mDestinationLat.doubleValue(), this.mDestinationLon.doubleValue());
      addDestinationMaker(c1);
    } 
    if (TextUtils.isEmpty(this.mDestinationName) || TextUtils.isEmpty(this.mDestinationAddress)) {
      if (this.mDestinationLon.doubleValue() != 0.0D && this.mDestinationLat.doubleValue() != 0.0D) {
        new c(this.mDestinationLat.doubleValue(), this.mDestinationLon.doubleValue());
        return;
      } 
      showLoadingStatus(getResources().getString(2097741942));
      return;
    } 
    this.mLocationName.setText(this.mDestinationName);
    this.mAddress.setText(this.mDestinationAddress);
    showLocateInfoReadyStatus();
  }
  
  public boolean isInBackArea(float paramFloat1, float paramFloat2) {
    return (paramFloat1 > this.mBack.getLeft() && paramFloat1 < this.mBack.getRight() && paramFloat2 > this.mBack.getTop() && paramFloat2 < this.mBack.getBottom());
  }
  
  public void moveCamera() {}
  
  public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2097676328);
    ViewGroup viewGroup = (ViewGroup)findViewById(2097545360);
    this.mMapInstance = HostDependManager.getInst().createMapInstance();
    if (this.mMapInstance == null) {
      finish();
      return;
    } 
    new Object() {
        public void onLocationChanged(Location param1Location, String param1String) {
          if (param1Location == null)
            return; 
          if (param1Location.getLatitude() != 0.0D) {
            if (param1Location.getLongitude() == 0.0D)
              return; 
            AppbrandMapActivity appbrandMapActivity2 = AppbrandMapActivity.this;
            appbrandMapActivity2.mLastLocatingLocation = param1Location;
            if (appbrandMapActivity2.mOriginPoint == null) {
              AppbrandMapActivity.this.mOriginPoint = new c(param1Location.getLatitude(), param1Location.getLongitude());
              if (AppbrandMapActivity.this.isClickPosition && !Objects.equals(AppbrandMapActivity.this.mLocation.getDrawable().getCurrent().getConstantState(), c.a((Context)AppbrandMapActivity.this, 2097479761).getConstantState()))
                AppbrandMapActivity.this.mLocation.setImageResource(2097479761); 
            } else if (AppbrandMapActivity.this.isClickPosition) {
              AppbrandMapActivity.this.mOriginPoint.a = param1Location.getLatitude();
              AppbrandMapActivity.this.mOriginPoint.b = param1Location.getLongitude();
            } 
            if (AppbrandMapActivity.this.isClickPosition)
              AppbrandMapActivity.this.isClickPosition = false; 
            AppbrandMapActivity appbrandMapActivity1 = AppbrandMapActivity.this;
            appbrandMapActivity1.mStartModel = new ThirdMapModel(param1String, appbrandMapActivity1.mOriginPoint);
          } 
        }
      };
    View view = this.mMapInstance.a((Activity)this);
    this.mapView = view;
    viewGroup.addView(view, 0, (ViewGroup.LayoutParams)new RelativeLayout.LayoutParams(-1, -1));
    viewGroup.setOnTouchListener(new View.OnTouchListener() {
          public boolean onTouch(View param1View, MotionEvent param1MotionEvent) {
            AppbrandMapActivity.this.onMapTouch(param1MotionEvent);
            return false;
          }
        });
    this.mImmersedStatusBarHelper = new ImmersedStatusBarHelper((Activity)this, getImmersedStatusBarConfig());
    this.mImmersedStatusBarHelper.setup(false);
    if (getIntent() != null) {
      this.mDestinationName = getIntent().getStringExtra("name");
      this.mDestinationAddress = getIntent().getStringExtra("address");
      this.mDestinationLat = Double.valueOf(getIntent().getDoubleExtra("latitude", 0.0D));
      this.mDestinationLon = Double.valueOf(getIntent().getDoubleExtra("longitude", 0.0D));
      this.mScale = getIntent().getIntExtra("scale", 18);
      int i = this.mScale;
      if (i >= 19) {
        this.mScale = 19;
      } else if (i <= 3) {
        this.mScale = 3;
      } 
    } 
    bindView();
    requestPermission();
  }
  
  public void onDestroy() {
    super.onDestroy();
    if (this.mMapInstance != null)
      this.mMapInstance = null; 
  }
  
  public void onGeocoderSearchedListener(boolean paramBoolean, c paramc) {
    if (!paramBoolean)
      return; 
    this.mDestinationLat = Double.valueOf(paramc.a);
    this.mDestinationLon = Double.valueOf(paramc.b);
    paramc = this.mEndPoint;
    if (paramc == null) {
      this.mEndPoint = new c(this.mDestinationLat.doubleValue(), this.mDestinationLon.doubleValue());
    } else {
      paramc.a = this.mDestinationLat.doubleValue();
      this.mEndPoint.a = this.mDestinationLon.doubleValue();
    } 
    addDestinationMaker(this.mEndPoint);
  }
  
  public void onMapTouch(MotionEvent paramMotionEvent) {
    AppBrandLogger.d("tma_AppbrandMapActivity", new Object[] { "onTouch ", Integer.valueOf(paramMotionEvent.getAction()) });
    if (paramMotionEvent.getAction() != 2)
      return; 
    if (!Objects.equals(this.mLocation.getDrawable().getCurrent().getConstantState(), c.a((Context)this, 2097479760).getConstantState()))
      this.mLocation.setImageResource(2097479760); 
  }
  
  public void onPause() {
    super.onPause();
  }
  
  public void onRegeocodeSearchedListener(boolean paramBoolean, String paramString1, String paramString2) {
    if (!paramBoolean)
      return; 
    this.mLocationName.setText(paramString1);
    this.mAddress.setText(paramString2);
    UIUtils.setViewVisibility((View)this.mLocationName, 0);
    UIUtils.setViewVisibility((View)this.mAddress, 0);
    UIUtils.setViewVisibility((View)this.mLocating, 8);
  }
  
  public void onResume() {
    super.onResume();
  }
  
  public void onSaveInstanceState(Bundle paramBundle) {
    super.onSaveInstanceState(paramBundle);
  }
  
  public void openBottomDialog() {
    c c1 = this.mEndPoint;
    if (c1 != null)
      this.mEndModel = new ThirdMapModel(this.mDestinationName, c1); 
    if (this.mDialog == null) {
      List list = MapUtils.getLocalInstallMap((Context)this);
      this.mDialog = new Dialog((Context)this, 2097807364);
      this.mDialog.setCancelable(true);
      this.mDialog.setCanceledOnTouchOutside(true);
      this.mDialog.requestWindowFeature(1);
      LinearLayout linearLayout = (LinearLayout)LayoutInflater.from((Context)this).inflate(2097676329, null);
      View view1 = linearLayout.findViewById(2097545218);
      this.mShowLines = (TextView)linearLayout.findViewById(2097545338);
      View view2 = linearLayout.findViewById(2097545339);
      TextView textView1 = (TextView)linearLayout.findViewById(2097545340);
      View view3 = linearLayout.findViewById(2097545341);
      TextView textView2 = (TextView)linearLayout.findViewById(2097545336);
      View view4 = linearLayout.findViewById(2097545337);
      TextView textView3 = (TextView)linearLayout.findViewById(2097545334);
      TextView textView4 = (TextView)linearLayout.findViewById(2097545335);
      if (list.contains(getResources().getString(2097741948))) {
        UIUtils.setViewVisibility((View)textView1, 0);
      } else {
        UIUtils.setViewVisibility((View)textView1, 8);
        UIUtils.setViewVisibility(view3, 8);
      } 
      if (list.contains(getResources().getString(2097741945))) {
        UIUtils.setViewVisibility((View)textView2, 0);
      } else {
        UIUtils.setViewVisibility((View)textView2, 8);
        UIUtils.setViewVisibility(view4, 8);
      } 
      if (list.contains(getResources().getString(2097741943))) {
        UIUtils.setViewVisibility((View)textView3, 0);
      } else {
        UIUtils.setViewVisibility((View)textView3, 8);
      } 
      if (list.isEmpty())
        UIUtils.setViewVisibility(view2, 8); 
      this.mDialog.setContentView((View)linearLayout);
      Window window = this.mDialog.getWindow();
      if (window == null)
        return; 
      window.setGravity(80);
      window.setDimAmount(0.0F);
      window.setBackgroundDrawableResource(2097348674);
      if (!isFinishing())
        this.mDialog.show(); 
      view1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
              if (AppbrandMapActivity.this.mDialog.isShowing())
                _lancet.com_ss_android_ugc_aweme_lancet_DebugCheckLancet_dialogDismiss(AppbrandMapActivity.this.mDialog); 
            }
            
            class null {}
          });
      textView1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
              AppbrandMapActivity appbrandMapActivity = AppbrandMapActivity.this;
              MapUtils.jumpToTencentMap((Context)appbrandMapActivity, appbrandMapActivity.mStartModel, AppbrandMapActivity.this.mEndModel);
              if (AppbrandMapActivity.this.mDialog.isShowing())
                _lancet.com_ss_android_ugc_aweme_lancet_DebugCheckLancet_dialogDismiss(AppbrandMapActivity.this.mDialog); 
            }
            
            class null {}
          });
      textView2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
              AppbrandMapActivity appbrandMapActivity = AppbrandMapActivity.this;
              MapUtils.jumpToGaodeMap((Context)appbrandMapActivity, appbrandMapActivity.mStartModel, AppbrandMapActivity.this.mEndModel);
              if (AppbrandMapActivity.this.mDialog.isShowing())
                _lancet.com_ss_android_ugc_aweme_lancet_DebugCheckLancet_dialogDismiss(AppbrandMapActivity.this.mDialog); 
            }
            
            class null {}
          });
      textView3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
              AppbrandMapActivity appbrandMapActivity = AppbrandMapActivity.this;
              MapUtils.jumpToBaiduMap((Context)appbrandMapActivity, appbrandMapActivity.mStartModel, AppbrandMapActivity.this.mEndModel);
              if (AppbrandMapActivity.this.mDialog.isShowing())
                _lancet.com_ss_android_ugc_aweme_lancet_DebugCheckLancet_dialogDismiss(AppbrandMapActivity.this.mDialog); 
            }
            
            class null {}
          });
      textView4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
              if (AppbrandMapActivity.this.mDialog.isShowing())
                _lancet.com_ss_android_ugc_aweme_lancet_DebugCheckLancet_dialogDismiss(AppbrandMapActivity.this.mDialog); 
            }
            
            class null {}
          });
    } else if (!isFinishing()) {
      this.mDialog.show();
    } 
    this.mShowLines.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            if (TextUtils.equals(AppbrandMapActivity.this.mShowLines.getText(), AppbrandMapActivity.this.getResources().getString(2097741946))) {
              if (AppbrandMapActivity.this.mEndPoint != null && AppbrandMapActivity.this.mOriginPoint != null)
                new Object() {
                    public void onSearchRouteComplete() {
                      if (AppbrandMapActivity.this.mShowLines != null)
                        AppbrandMapActivity.this.mShowLines.setText(AppbrandMapActivity.this.getResources().getString(2097741947)); 
                    }
                  }; 
              if (!Objects.equals(AppbrandMapActivity.this.mLocation.getDrawable().getCurrent().getConstantState(), c.a((Context)AppbrandMapActivity.this, 2097479760).getConstantState()))
                AppbrandMapActivity.this.mLocation.setImageResource(2097479760); 
            } else {
              if (AppbrandMapActivity.this.mMapInstance.b())
                AppbrandMapActivity.this.mShowLines.setText(AppbrandMapActivity.this.getResources().getString(2097741946)); 
              if (!Objects.equals(AppbrandMapActivity.this.mLocation.getDrawable().getCurrent().getConstantState(), c.a((Context)AppbrandMapActivity.this, 2097479761).getConstantState()))
                AppbrandMapActivity.this.mLocation.setImageResource(2097479760); 
            } 
            if (AppbrandMapActivity.this.mDialog.isShowing())
              _lancet.com_ss_android_ugc_aweme_lancet_DebugCheckLancet_dialogDismiss(AppbrandMapActivity.this.mDialog); 
          }
          
          class null {}
        });
  }
  
  public void showLoadingStatus(String paramString) {
    UIUtils.setViewVisibility((View)this.mLocationName, 8);
    UIUtils.setViewVisibility((View)this.mAddress, 8);
    UIUtils.setViewVisibility((View)this.mLocating, 0);
    if (paramString != null)
      this.mLocating.setText(paramString); 
  }
  
  public void showLocateInfoReadyStatus() {
    UIUtils.setViewVisibility((View)this.mLocationName, 0);
    UIUtils.setViewVisibility((View)this.mAddress, 0);
    UIUtils.setViewVisibility((View)this.mLocating, 8);
  }
  
  public void startLocation() {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\map\AppbrandMapActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */