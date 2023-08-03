package com.bluegate.autodoor.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.bluegate.autodoor.Notification;
import com.bluegate.autodoor.R;

public class MainActivity extends AppCompatActivity {

    ImageButton register_btn; //펫 추가 버튼
    Switch toggle; //문 제어 토글
    TextView door_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //펫 추가 버튼
        register_btn = (ImageButton) findViewById(R.id.plus_button);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        //문 토글
        toggle = (Switch) findViewById(R.id.toggleButton);
        door_status = (TextView) findViewById(R.id.door_status);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            Notification notification = new Notification(getApplicationContext());
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isOpen) {
                if (isOpen) {
                    door_status.setText("Opened");
                    notification.updateNotification(true);
                }
                else {
                    door_status.setText("Closed");
                    notification.updateNotification(false);
                }

            }
        });
    }
}