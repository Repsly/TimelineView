package com.repsly.library.timeline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_horizontal).setOnClickListener(this);
        findViewById(R.id.button_vertical).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, TimelineActivity.class);

        switch (v.getId()) {

            case R.id.button_horizontal:
                intent.putExtra(TimelineActivity.ORIENTATION, LinearLayoutManager.HORIZONTAL);
                break;

            case R.id.button_vertical:
                intent.putExtra(TimelineActivity.ORIENTATION, LinearLayoutManager.VERTICAL);
                break;

        }

        startActivity(intent);
    }
}
