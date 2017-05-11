package com.doximity.callwithdoxdialer;


import com.doximity.callwithdoxdialerlib.DoxDialerCaller;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class DialActivity extends AppCompatActivity {

    private TextView mSamplePhoneNumberText;
    private EditText mPhoneNumberEditText;
    private ImageView mDoxDialerButton;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dial_activity);
        mSamplePhoneNumberText = (TextView) findViewById(R.id.samplePhoneNumberTextView);
        mPhoneNumberEditText = (EditText) findViewById(R.id.phoneNumberEditText);
        mDoxDialerButton = (ImageView) findViewById(R.id.callButton);
        setUpViews();
    }

    private void setUpViews() {
        mSamplePhoneNumberText.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                dialOnDoxDialerApp(mSamplePhoneNumberText.getText().toString());
            }
        });

        //Set Dox Dialer icon
        mDoxDialerButton.setImageDrawable(DoxDialerCaller.shared().getDialerIcon(DialActivity.this));

        //Format a phone number
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mPhoneNumberEditText.addTextChangedListener(new PhoneNumberFormattingTextWatcher(Locale.getDefault().getCountry()));
        } else {
            mPhoneNumberEditText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        }

        mDoxDialerButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                dialOnDoxDialerApp(mPhoneNumberEditText.getText().toString());
            }
        });
    }

    private void dialOnDoxDialerApp(String phoneNumber) {
        DoxDialerCaller.shared().dialPhoneNumber(DialActivity.this, phoneNumber);
    }
}
