package com.spax.vitebsktransport;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;

import com.spax.vitebsktransport.domain.StopTimeHolder;
import com.spax.vitebsktransport.domain.Time;
import com.spax.vitebsktransport.service.DepartureService;

import java.util.List;

public class TimeDetails extends ListActivity {
    private DepartureService stopService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_details);

        stopService = new DepartureService(this);

        final long directionId = getIntent().getLongExtra("direction", -1);
        final long stopId = getIntent().getLongExtra("stop", -1);
        final int hours = getIntent().getIntExtra("hours", -1);
        final int mins = getIntent().getIntExtra("mins", -1);
        if (directionId == -1L || stopId == -1 || hours == -1 || mins == -1) {
            new AlertDialog.Builder(this)
                .setTitle("Ошибка")
                .setMessage("Не переданы все параметры")
                .setPositiveButton("Что поделать...", null).show();
        } else {
            List<StopTimeHolder> stops = stopService.getRouteDetailsForTime(new Time(hours, mins), stopId, directionId);
            setListAdapter(new ArrayAdapter<StopTimeHolder>(this, android.R.layout.simple_list_item_1, stops));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


}
