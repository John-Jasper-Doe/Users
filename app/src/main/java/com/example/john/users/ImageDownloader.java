/**
 * @file   ImageDownloader.java
 * @brief  Uploading images.
 * @date   01.08.2018
 * @autor  M.Gusev
 */

package com.example.john.users;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.webkit.URLUtil;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


/** @class ImageDownloader
 *  @brief Uploading images */
public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
    private WeakReference<ImageView> imageViewReference_ = null;

    public ImageDownloader(ImageView imageView) {
        imageViewReference_ = new WeakReference<ImageView>(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        return downloadBitmap(params[0]);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }

        if (imageViewReference_ != null) {
            ImageView imageView = imageViewReference_.get();
            if (imageView != null) {
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                } else {
                    imageView.setImageDrawable(imageView.getContext().
                            getResources().getDrawable(R.drawable.
                            list_placeholder));
                }
            }
        }
    }

    static Bitmap downloadBitmap(String url) {
        if(URLUtil.isValidUrl(url)){
            InputStream isAnsw = null;
            URLConnection urlCon = null;

            try {
                urlCon = new URL(url).openConnection();
                isAnsw = (InputStream) urlCon.getContent();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Bitmap bitmap = BitmapFactory.decodeStream(isAnsw);
            return bitmap;
        }
        return null;
    }
}

