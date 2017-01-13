package com.repsly.library.timeline;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Activity with RecyclerView that displays TimelineView
 */

public class TimelineActivity extends AppCompatActivity {

    public static final String ORIENTATION = "orientation";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        int orientation = getIntent().getIntExtra(ORIENTATION, LinearLayoutManager.VERTICAL);

        RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this, orientation, false));

        TimelineAdapter adapter = new TimelineAdapter(orientation, getItems());
        recycler.setAdapter(adapter);
    }

    private List<ListItem> getItems() {
        List<ListItem> items = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 20; i++) {
            ListItem item = new ListItem();
            item.setName("Client number " + i);
            item.setAddress("Some address " + random.nextInt(100));
            items.add(item);
        }

        return items;
    }

}
