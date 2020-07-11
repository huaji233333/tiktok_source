package com.ss.android.ugc.aweme.df.photomovie;

import com.ss.android.ugc.aweme.services.photomovie.IPhotoMovieService;
import com.ss.android.ugc.aweme.services.photomovie.PhotoMovieService;

public class PhotoMovieServiceFactory {
  public IPhotoMovieService create() {
    return (IPhotoMovieService)new PhotoMovieService();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_photomovie\classes.jar!\com\ss\androi\\ugc\aweme\df\photomovie\PhotoMovieServiceFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */