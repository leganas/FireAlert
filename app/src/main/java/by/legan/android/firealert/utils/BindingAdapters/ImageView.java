package by.legan.android.firealert.utils.BindingAdapters;

import android.graphics.Bitmap;

import androidx.databinding.BindingAdapter;

import by.legan.android.firealert.R;

public class ImageView {
    @BindingAdapter("load_image_bitmap")
    public static void loadImageBitmap(android.widget.ImageView view, Bitmap bitmap) {
        if (bitmap != null) view.setImageBitmap(bitmap); else view.setImageResource(R.drawable.logo);
    }
}
