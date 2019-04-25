package com.example.dynamicformactivity;

import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Map;

import androidx.lifecycle.ViewModel;

class UserInputViewModel extends ViewModel {

    private Map<String, Bitmap> imageSelections;
    private Map<String, String> commentSections;
    private Map<String, Boolean> commentSectionEnabled;
    private Map<String, String> choiceSelections;

    void setCommentInput(String commentID, String comments) {
        if(commentSections == null) {
            commentSections = new HashMap<>();
        }
        if(comments != null) {
            commentSections.put(commentID, comments);
        }
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

    void setCommentSectionEnabled(String commentID, boolean isEnabled) {
        if(commentSectionEnabled == null) {
            commentSectionEnabled = new HashMap<>();
        }
        commentSectionEnabled.put(commentID, isEnabled);
    }

    Boolean getCommentSectionEnabled(String commentID) {
        if(commentSectionEnabled == null) {
            commentSectionEnabled = new HashMap<>();
        }
        return commentSectionEnabled.get(commentID);
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
        if(selectedBitmap != null) {
            imageSelections.put(itemID, selectedBitmap);
        } else {
            imageSelections.remove(itemID);
        }
    }

    Bitmap getBitmap(String itemId) {
        if(imageSelections == null) {
            imageSelections = new HashMap<>();
        }
        return imageSelections.get(itemId);
    }
}
