package com.example.finalprojectandroid;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class CustomActivityGreeting extends Fragment {

    private Bundle dataFromActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.custom_activity_greeting_fragment, container, false);

        dataFromActivity = getArguments();
        final SharedPrefHelper sharedPrefHelper = new SharedPrefHelper(getContext());
        String sharedPrefKey = dataFromActivity.getString("sharedPrefKey");
        final EditText et = (EditText) view.findViewById(R.id.editTextFragment);
        et.setText(sharedPrefHelper.load(sharedPrefKey));

        Button setGreeting = (Button) view.findViewById(R.id.setGreetingFragment);

        setGreeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPrefHelper.store(et.getText().toString(), sharedPrefKey);
                et.setText(et.getText());
            }
        });

        return view;
    }

}
