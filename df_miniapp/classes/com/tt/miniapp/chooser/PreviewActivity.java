package com.tt.miniapp.chooser;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.k;
import android.support.v4.app.q;
import android.support.v4.content.c;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tt.miniapp.chooser.view.PreviewFragment;
import com.tt.miniapp.view.swipeback.SwipeBackActivity;
import com.tt.miniapphost.entity.MediaEntity;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PreviewActivity extends SwipeBackActivity implements ViewPager.e, View.OnClickListener {
  TextView bar_title;
  
  ImageView check_image;
  
  LinearLayout check_layout;
  
  Button done;
  
  ArrayList<MediaEntity> preRawList;
  
  ArrayList<MediaEntity> selects;
  
  ViewPager viewpager;
  
  public void done(ArrayList<MediaEntity> paramArrayList, int paramInt) {
    Intent intent = new Intent();
    intent.putParcelableArrayListExtra("select_result", paramArrayList);
    setResult(paramInt, intent);
    finish();
  }
  
  public int isSelect(MediaEntity paramMediaEntity, ArrayList<MediaEntity> paramArrayList) {
    if (paramArrayList.size() <= 0)
      return -1; 
    for (int i = 0; i < paramArrayList.size(); i++) {
      if (((MediaEntity)paramArrayList.get(i)).path.equals(paramMediaEntity.path))
        return i; 
    } 
    return -1;
  }
  
  public void onBackPressed() {
    done(this.selects, 1990);
    super.onBackPressed();
  }
  
  public void onClick(View paramView) {
    int i = paramView.getId();
    if (i == 2097545250) {
      done(this.selects, 1990);
      return;
    } 
    if (i == 2097545269) {
      done(this.selects, 19901026);
      return;
    } 
    if (i == 2097545258) {
      MediaEntity mediaEntity = this.preRawList.get(this.viewpager.getCurrentItem());
      i = isSelect(mediaEntity, this.selects);
      if (i < 0) {
        this.check_image.setImageDrawable(c.a((Context)this, 2097479695));
        this.selects.add(mediaEntity);
      } else {
        this.check_image.setImageDrawable(c.a((Context)this, 2097479696));
        this.selects.remove(i);
      } 
      setDoneView(this.selects.size());
    } 
  }
  
  public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2097676344);
    findViewById(2097545250).setOnClickListener(this);
    this.check_image = (ImageView)findViewById(2097545257);
    this.check_layout = (LinearLayout)findViewById(2097545258);
    this.check_layout.setOnClickListener(this);
    this.bar_title = (TextView)findViewById(2097545248);
    this.done = (Button)findViewById(2097545269);
    this.done.setOnClickListener(this);
    this.viewpager = (ViewPager)findViewById(2097545500);
    this.preRawList = getIntent().getParcelableArrayListExtra("pre_raw_List");
    this.selects = new ArrayList<MediaEntity>();
    this.selects.addAll(this.preRawList);
    setView(this.preRawList);
  }
  
  public void onPageScrollStateChanged(int paramInt) {}
  
  public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2) {}
  
  public void onPageSelected(int paramInt) {
    TextView textView = this.bar_title;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramInt + 1);
    stringBuilder.append("/");
    stringBuilder.append(this.preRawList.size());
    textView.setText(stringBuilder.toString());
    ImageView imageView = this.check_image;
    if (isSelect(this.preRawList.get(paramInt), this.selects) < 0) {
      paramInt = 2097479696;
    } else {
      paramInt = 2097479695;
    } 
    imageView.setImageDrawable(c.a((Context)this, paramInt));
  }
  
  void setDoneView(int paramInt) {
    Button button = this.done;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(getString(2097741885));
    stringBuilder.append("(");
    stringBuilder.append(paramInt);
    stringBuilder.append("/");
    stringBuilder.append(getIntent().getIntExtra("max_select_count", 40));
    stringBuilder.append(")");
    button.setText(stringBuilder.toString());
  }
  
  void setView(ArrayList<MediaEntity> paramArrayList) {
    setDoneView(paramArrayList.size());
    TextView textView = this.bar_title;
    StringBuilder stringBuilder = new StringBuilder("1/");
    stringBuilder.append(this.preRawList.size());
    textView.setText(stringBuilder.toString());
    ArrayList<PreviewFragment> arrayList = new ArrayList();
    Iterator<MediaEntity> iterator = paramArrayList.iterator();
    while (iterator.hasNext())
      arrayList.add(PreviewFragment.newInstance(iterator.next(), "")); 
    AdapterFragment adapterFragment = new AdapterFragment(getSupportFragmentManager(), (List)arrayList);
    this.viewpager.setAdapter((PagerAdapter)adapterFragment);
    this.viewpager.addOnPageChangeListener(this);
  }
  
  public class AdapterFragment extends q {
    private List<Fragment> mFragments;
    
    public AdapterFragment(k param1k, List<Fragment> param1List) {
      super(param1k);
      this.mFragments = param1List;
    }
    
    public int getCount() {
      return this.mFragments.size();
    }
    
    public Fragment getItem(int param1Int) {
      return this.mFragments.get(param1Int);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\chooser\PreviewActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */