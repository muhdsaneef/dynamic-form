package com.example.dynamicformactivity.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.dynamicformactivity.R;

import java.util.List;

public class CustomChoiceWidget extends LinearLayout {

    private RadioGroup optionsGroup;
    private TextView tvChoiceWidget;
    private String choiceID;
    private String selectedChoice;
    private ChoiceSelectionListener choiceSelectionListener;

    public CustomChoiceWidget(Context context) {
        super(context);
        initLayout(context);
    }

    private void initLayout(Context context) {
        View choiceLayout = LayoutInflater.from(context).inflate(R.layout.item_single_choice, null, false);
        optionsGroup = choiceLayout.findViewById(R.id.rg_choices);
        tvChoiceWidget = choiceLayout.findViewById(R.id.tv_choices_title);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        choiceLayout.setLayoutParams(layoutParams);
        addView(choiceLayout);

        initChoiceSelectionListener();
    }

    public void setChoiceSelectionListener(ChoiceSelectionListener choiceSelectionListener) {
        this.choiceSelectionListener = choiceSelectionListener;
    }

    public String getSelectedChoice() {
        return selectedChoice;
    }

    public void setSelectedChoice(String selectedChoice) {
        this.selectedChoice = selectedChoice;
    }

    private void initChoiceSelectionListener() {
        optionsGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if(choiceSelectionListener != null) {
                RadioButton radioButton = optionsGroup.findViewById(group.getCheckedRadioButtonId());
                choiceSelectionListener.onChoiceSelected(choiceID, radioButton.getText().toString());
                selectedChoice = radioButton.getText().toString();

            }
        });
    }

    public String getChoiceID() {
        return choiceID;
    }

    public void setChoiceID(String choiceID) {
        this.choiceID = choiceID;
    }

    public void setChoiceLabels(Context context, List<String> userOptions) {
        for(int i = 0; i < userOptions.size(); i++) {
            int j = -1;
            RadioButton radioButton = new RadioButton(context);
            radioButton.setText(userOptions.get(i));
            if(userOptions.get(i).equalsIgnoreCase(selectedChoice)) {
                j = i;
            }
            optionsGroup.addView(radioButton);
            if(j != -1) {
                optionsGroup.check(j);
            }
        }
    }

    public void setChoiceWidgetTitle(String title) {
        tvChoiceWidget.setText(title);
    }

    public interface ChoiceSelectionListener {
        void onChoiceSelected(String formItemId, String selectedChoice);
    }


}
