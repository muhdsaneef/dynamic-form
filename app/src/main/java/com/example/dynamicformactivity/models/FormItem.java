package com.example.dynamicformactivity.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FormItem {

    @SerializedName("type")
    private String formItemType;

    @SerializedName("id")
    private String formItemID;

    @SerializedName("title")
    private String formItemtTitle;

    @SerializedName("dataMap")
    private DataMap formItemDataMap;

    private String selectedOption;

    public String getFormItemType() {
        return formItemType;
    }

    public void setFormItemType(String formItemType) {
        this.formItemType = formItemType;
    }

    public String getFormItemID() {
        return formItemID;
    }

    public String getFormItemtTitle() {
        return formItemtTitle;
    }

    public DataMap getFormItemDataMap() {
        return formItemDataMap;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    public class DataMap {

        @SerializedName("options")
        private List<String> options;

        public List<String> getOptions() {
            return options;
        }
    }
}
