package com.example.dynamicformactivity.widgets;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.dynamicformactivity.R;

public class CommentWidget extends LinearLayout {

    private EditText edtComments;
    private Switch swComments;
    private TextView tvCommentWidgetTitle;
    private CommentSectionListener commentSectionListener;
    private String commentID;

    public CommentWidget(Context context) {
        super(context);
        initLayout(context);
    }

    public void setCommentSectionListener(CommentSectionListener commentSectionListener) {
        this.commentSectionListener = commentSectionListener;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }

    private void initLayout(Context context) {
        View commentWidgetLayout = LayoutInflater.from(context).inflate(R.layout.form_element_comment, null, true);
        swComments = commentWidgetLayout.findViewById(R.id.sw_comment);
        edtComments = commentWidgetLayout.findViewById(R.id.edt_comments);
        tvCommentWidgetTitle = commentWidgetLayout.findViewById(R.id.tv_comment_title);
        addView(commentWidgetLayout);

        initListeners();

    }

    public void setCommentWidgetTitle(String title) {
        tvCommentWidgetTitle.setText(title);
    }

    private void initListeners() {
        swComments.setOnCheckedChangeListener((buttonView, isChecked) -> edtComments.setVisibility(isChecked ? VISIBLE : GONE));

        edtComments.setOnKeyListener((v, keyCode, event) -> {
            if(commentSectionListener != null) {
                commentSectionListener.onCommentChanged(commentID, edtComments.getText().toString().trim());
            }
            return false;
        });
    }

    public void setCommentsGiven(String commentsGiven) {
        edtComments.setText(commentsGiven);
        boolean isEnabled = commentsGiven != null && !commentsGiven.isEmpty();
        edtComments.setVisibility(isEnabled ? VISIBLE : GONE);
        swComments.setChecked(isEnabled);
    }

    public interface CommentSectionListener {
        void onCommentChanged(String commentID, String comment);
    }
}
