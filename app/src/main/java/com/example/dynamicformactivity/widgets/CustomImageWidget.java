package com.example.dynamicformactivity.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.dynamicformactivity.R;

public class CustomImageWidget extends LinearLayout {

    private ImageView imgSelectedImage;
    private ImageView imgDelete;
    private Bitmap selectedBitmap;
    private String imageID;
    private ImageClickListener imageClickListener;
    private TextView tvWidgetTitle;

    public CustomImageWidget(Context context) {
        super(context);
        initLayout(context);
    }

    public CustomImageWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initLayout(context);
    }

    public CustomImageWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context);
    }

    public void setImageClickListener(ImageClickListener imageClickListener) {
        this.imageClickListener = imageClickListener;
    }

    private void initLayout(Context context) {
        View imagePickingLayout = LayoutInflater.from(context).inflate(R.layout.form_element_photo, null, false);
        imgSelectedImage = imagePickingLayout.findViewById(R.id.img_form_element_photo);
        imgDelete = imagePickingLayout.findViewById(R.id.img_delete);
        tvWidgetTitle = imagePickingLayout.findViewById(R.id.tv_image_title);
        imgDelete.setVisibility(GONE);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        imagePickingLayout.setLayoutParams(layoutParams);
        addView(imagePickingLayout);
        initImageClickListener();
    }

    private void initImageClickListener() {
        imgSelectedImage.setOnClickListener(v -> {
            if(imageClickListener != null) {
                if(selectedBitmap == null) {
                    imageClickListener.formImageClicked(this);
                } else {
                    imageClickListener.showEnlargedImage(selectedBitmap);
                }
            }
        });

        imgDelete.setOnClickListener(v -> {
            if(selectedBitmap != null) {
                selectedBitmap = null;
                imgSelectedImage.setImageBitmap(null);
                imgDelete.setVisibility(GONE);
            }
        });
    }

    public void setWidgetTitle(String title) {
        tvWidgetTitle.setText(title);
    }

    public void setSelectedImageToView(Bitmap selectedBitmapImage) {
        imgSelectedImage.setImageBitmap(selectedBitmapImage);
        selectedBitmap = selectedBitmapImage;
        imgDelete.setVisibility(selectedBitmap != null ? VISIBLE : GONE);
    }

    public void setImageId(String formItemID) {
        imageID = formItemID;
    }

    public String getImageID() {
        return imageID;
    }

    public interface ImageClickListener {
        void formImageClicked(CustomImageWidget imageView);

        void showEnlargedImage(Bitmap selectedBitmap);
    }
}
