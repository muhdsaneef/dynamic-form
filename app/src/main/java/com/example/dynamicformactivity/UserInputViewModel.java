package com.example.dynamicformactivity;

import android.graphics.Bitmap;

import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.Map;

class UserInputViewModel extends ViewModel {

    private Map<String, Bitmap> imageSelections;
    private Map<String, String> commentSections;
    private Map<String, String> choiceSelections;
    private boolean isImmutable;

    void setCommentInput(String commentID, String comments) {
        if(commentSections == null) {
            commentSections = new HashMap<>();
        }
        if(comments != null && !comments.isEmpty() && !isImmutable) {
            commentSections.put(commentID, comments);
        }
    }

    public boolean isImmutable() {
        return isImmutable;
    }

    public void setImmutable(boolean immutable) {
        isImmutable = immutable;
    }

    void setChoiceSelection(String choiceID, String choice) {
        if(choiceSelections == null) {
            choiceSelections = new HashMap<>();
        }

        choiceSelections.put(choiceID, choice);
    }

    String getChoiceInput(String choiceID) {
        if(choiceSelections == null) {
            choiceSelections = new HashMap<>();
        }
        return choiceSelections.get(choiceID);
    }

    String getCommentInput(String commentID) {
        if(commentSections == null) {
            commentSections = new HashMap<>();
        }
        return commentSections.get(commentID);
    }

    Map<String, String> getAllChoices() {
        return choiceSelections;
    }

    void setImageBitmap(String itemID, Bitmap selectedBitmap) {
        if(imageSelections == null) {
            imageSelections = new HashMap<>();
        }
        imageSelections.put(itemID, selectedBitmap);
    }

    Bitmap getBitmap(String itemId) {
        if(imageSelections == null) {
            imageSelections = new HashMap<>();
        }
        return imageSelections.get(itemId);
    }
}
