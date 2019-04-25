package com.example.dynamicformactivity.widgets;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

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
        edtComments.setFreezesText(false);
        addView(commentWidgetLayout);

        initListeners();

    }

    public void setCommentWidgetTitle(String title) {
        tvCommentWidgetTitle.setText(title);
    }

    private void initListeners() {
        swComments.setOnCheckedChangeListener((buttonView, isChecked) -> {
            edtComments.setVisibility(isChecked ? VISIBLE : GONE);
            if(commentSectionListener != null) {
                commentSectionListener.onCommentEnabled(commentID, isChecked);
            }
        });

        edtComments.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(commentSectionListener != null) {
                    commentSectionListener.onCommentChanged(commentID, edtComments.getText().toString().trim());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void setCommentsGiven(String commentsGiven) {
        new Handler().post(() -> {
            edtComments.setText(commentsGiven);
        });
    }

    public void setSectionEnabled(boolean isEnabled){
        new Handler().post(() -> {
            edtComments.setVisibility(isEnabled ? VISIBLE : GONE);
            swComments.setChecked(isEnabled);
        });
    }

    public interface CommentSectionListener {
        void onCommentChanged(String commentID, String comment);

        void onCommentEnabled(String commentID, boolean isEnabled);
    }
}
