package com.spax.vitebsktransport;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import com.spax.vitebsktransport.R;
import com.spax.vitebsktransport.domain.Stop;
import com.spax.vitebsktransport.domain.TimeTableRecord;
import com.spax.vitebsktransport.service.DepartureService;
import com.spax.vitebsktransport.service.StopService;

import java.util.List;

public class DeparturesList extends ListActivity {
    private DepartureService departureService;
    private StopService stopService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departures_list);
        departureService = new DepartureService(this);
        stopService = new StopService(this);

        String day = getIntent().getStringExtra("day");
        long stopId = getIntent().getLongExtra("stop", -1L);
        long directionId = getIntent().getLongExtra("direction", -1L);
        if (directionId == -1L) {
            new AlertDialog.Builder(this)
                .setTitle("Ошибка")
                .setMessage("Ошибка открытия расписания")
                .setPositiveButton("Ok", null).show();
        } else {
            List<TimeTableRecord> routes = departureService.getTimeTable(directionId, stopId, day);
            setListAdapter(new ArrayAdapter<TimeTableRecord>(this, android.R.layout.simple_list_item_1, routes));
        }

        final String routeName =  getIntent().getStringExtra("routeName");
        Stop stop = stopService.find(stopId);
        if (stop != null) {
            setTitle("№" + routeName + ", " + stop.getName());
        } else {
            setTitle("№" + routeName);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

}
