package com.example.dynamicformactivity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import com.example.dynamicformactivity.app.AppConstants;
import com.example.dynamicformactivity.models.FormItem;
import com.example.dynamicformactivity.utils.AppUtils;
import com.example.dynamicformactivity.widgets.CommentWidget;
import com.example.dynamicformactivity.widgets.CustomChoiceWidget;
import com.example.dynamicformactivity.widgets.CustomImageWidget;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CustomImageWidget.ImageClickListener, CustomChoiceWidget.ChoiceSelectionListener, CommentWidget.CommentSectionListener {


    private static final int CAMERA_REQUEST = 8452;
    private static final int REQUEST_CAMERA_PERMISSION_CODE = 1001;
    private static final String EMPTY_ANSWER = "";
    private static final String ANSWER_TAG = "Selected Answer";

    private List<FormItem> formItemList;
    private CustomImageWidget selectedImageView;
    private UserInputViewModel inputViewModel;

    @Override
    protected void onPause() {
        super.onPause();
        inputViewModel.setImmutable(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        inputViewModel.setImmutable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inputViewModel = ViewModelProviders.of(this).get(UserInputViewModel.class);

        fetchDynamicFormInput();

        initDynamicFormView();

    }

    private void fetchDynamicFormInput() {
        String formInputString = AppUtils.getDynamicFormInput(this);
        Type listType = new TypeToken<List<FormItem>>(){}.getType();
        formItemList = new Gson().fromJson(formInputString, listType);
    }

    private void initDynamicFormView() {

        ScrollView scrollView = new ScrollView(this);
        LinearLayout dynamicFormView = new LinearLayout(this);
        dynamicFormView.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(dynamicFormView);

        for(FormItem formItem : formItemList) {

            switch (formItem.getFormItemType()) {
                case AppConstants.FORM_ELEMENT_PHOTO:
                    dynamicFormView.addView(createImageWidget(formItem));
                    break;

                case AppConstants.FORM_ELEMENT_SINGLE_CHOICE:
                    dynamicFormView.addView(createChoiceWidget(formItem));
                    if(inputViewModel.getChoiceInput(formItem.getFormItemID()) == null) {
                        inputViewModel.setChoiceSelection(formItem.getFormItemID(), EMPTY_ANSWER);
                    }
                    break;

                case AppConstants.FORM_ELEMENT_COMMENT:
                    CommentWidget commentWidget = createCommentWidget(formItem);
                    dynamicFormView.addView(commentWidget);
                    if(inputViewModel.getCommentInput(formItem.getFormItemID()) != null && !inputViewModel.getCommentInput(formItem.getFormItemID()).isEmpty()) {
                        commentWidget.setCommentsGiven(inputViewModel.getCommentInput(formItem.getFormItemID()));
                    }
                    break;
            }
        }

        this.setContentView(scrollView);
    }

    private CustomImageWidget createImageWidget(FormItem formItem) {
        CustomImageWidget imageWidget = new CustomImageWidget(this);
        imageWidget.setWidgetTitle(formItem.getFormItemtTitle());
        imageWidget.setImageId(formItem.getFormItemID());
        imageWidget.setSelectedImageToView(inputViewModel.getBitmap(formItem.getFormItemID()));
        imageWidget.setImageClickListener(this);
        return imageWidget;
    }

    private CustomChoiceWidget createChoiceWidget(FormItem formItem) {
        CustomChoiceWidget choiceWidget = new CustomChoiceWidget(this);
        choiceWidget.setSelectedChoice(inputViewModel.getChoiceInput(formItem.getFormItemID()));
        choiceWidget.setChoiceLabels(this, formItem.getFormItemDataMap().getOptions());
        choiceWidget.setChoiceWidgetTitle(formItem.getFormItemtTitle());
        choiceWidget.setChoiceID(formItem.getFormItemID());
        choiceWidget.setChoiceSelectionListener(this);
        return choiceWidget;
    }

    private CommentWidget createCommentWidget(FormItem formItem) {
        CommentWidget commentWidget = new CommentWidget(this);
        commentWidget.setCommentWidgetTitle(formItem.getFormItemtTitle());
        commentWidget.setCommentID(formItem.getFormItemID());
        commentWidget.setCommentSectionListener(this);
        return commentWidget;
    }

    public void onImageClicked(View view) {
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION_CODE);
        }
        else {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            if(data != null && data.getExtras() != null && data.getExtras().get("data") != null) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                selectedImageView.setSelectedImageToView(photo);
                inputViewModel.setImageBitmap(selectedImageView.getImageID(), photo);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void formImageClicked(CustomImageWidget imageView) {
        selectedImageView = imageView;
        onImageClicked(selectedImageView);
    }

    @Override
    public void showEnlargedImage(Bitmap selectedBitmap) {
        final Dialog previewDialog = new Dialog(this,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        previewDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        previewDialog.setCancelable(false);
        previewDialog.setContentView(R.layout.preview_image);
        Button btnClose = previewDialog.findViewById(R.id.btn_close);
        ImageView ivPreview = previewDialog.findViewById(R.id.iv_preview_image);
        ivPreview.setImageBitmap(selectedBitmap);

        btnClose.setOnClickListener(arg0 -> previewDialog.dismiss());
        previewDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_submit) {
            logAllAnsweredItems();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logAllAnsweredItems() {
        for(String formId : inputViewModel.getAllChoices().keySet()) {
            Log.d(ANSWER_TAG, formId + " : " + inputViewModel.getAllChoices().get(formId));
        }
    }

    @Override
    public void onChoiceSelected(String formItemId, String selectedChoice) {
        inputViewModel.setChoiceSelection(formItemId, selectedChoice);
    }

    @Override
    public void onCommentChanged(String commentID, String comment) {
        if(comment != null && !comment.isEmpty()) {
            inputViewModel.setCommentInput(commentID, comment);
        }
    }
}
