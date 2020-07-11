package com.tt.miniapp.chooser.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.tt.b.c;
import com.tt.miniapp.chooser.adapter.VideoThumbLoader;
import com.tt.miniapp.util.DevicesUtil;
import com.tt.miniapp.util.ImageUtil;
import com.tt.miniapp.util.UriUtil;
import com.tt.miniapphost.entity.MediaEntity;
import com.tt.miniapphost.host.HostDependManager;
import java.io.File;

public class PreviewFragment extends Fragment {
  private ImageView mPhotoView;
  
  ImageView play_view;
  
  public static PreviewFragment newInstance(MediaEntity paramMediaEntity, String paramString) {
    PreviewFragment previewFragment = new PreviewFragment();
    Bundle bundle = new Bundle();
    bundle.putParcelable("media", (Parcelable)paramMediaEntity);
    previewFragment.setArguments(bundle);
    return previewFragment;
  }
  
  public void onCreate(Bundle paramBundle) {
    setRetainInstance(true);
    super.onCreate(paramBundle);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
    return paramLayoutInflater.inflate(2097676343, paramViewGroup, false);
  }
  
  public void onDestroyView() {
    super.onDestroyView();
  }
  
  public void onViewCreated(View paramView, Bundle paramBundle) {
    super.onViewCreated(paramView, paramBundle);
    MediaEntity mediaEntity = (MediaEntity)getArguments().getParcelable("media");
    this.play_view = (ImageView)paramView.findViewById(2097545368);
    this.mPhotoView = (ImageView)paramView.findViewById(2097545367);
    setPlayView(mediaEntity);
  }
  
  void setPlayView(final MediaEntity media) {
    if (media.mediaType == 3) {
      String str = ImageUtil.getVideoThumbPath(getContext(), media.id);
      if (str != null && (new File(str)).exists() && getActivity() != null) {
        c c = (new c(new File(str))).a().a(DevicesUtil.getScreenWidth((Context)getActivity()), DevicesUtil.getScreenHight((Context)getActivity())).a((View)this.mPhotoView);
        HostDependManager.getInst().loadImage((Context)getActivity(), c);
      } else {
        (new VideoThumbLoader()).showThumb(media.path, this.mPhotoView);
      } 
      this.play_view.setVisibility(0);
      this.play_view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
              try {
                PreviewFragment.this.startActivity(UriUtil.generateCommonIntent((Context)PreviewFragment.this.getActivity(), media.path, "video/*"));
                return;
              } catch (Exception exception) {
                return;
              } 
            }
          });
      return;
    } 
    if (getActivity() != null) {
      c c = (new c(new File(media.path))).a().a(DevicesUtil.getScreenWidth((Context)getActivity()), DevicesUtil.getScreenHight((Context)getActivity())).a((View)this.mPhotoView);
      HostDependManager.getInst().loadImage((Context)getActivity(), c);
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\chooser\view\PreviewFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */