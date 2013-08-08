package com.spax.vitebsktransport;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

import com.spax.vitebsktransport.adapters.ExpandableListAdapter;
import com.spax.vitebsktransport.domain.Stop;
import com.spax.vitebsktransport.domain.TimeTableRecord;
import com.spax.vitebsktransport.service.DepartureService;
import com.spax.vitebsktransport.service.StopService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExpandableDeparturesList extends Activity {
    private DepartureService departureService;
    private ExpandableListView expListView;
    private StopService stopService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_departures_list);
        departureService = new DepartureService(this);
        stopService = new StopService(this);

        long stopId = getIntent().getLongExtra("stop", -1L);
        long directionId = getIntent().getLongExtra("direction", -1L);
        if (directionId == -1L || stopId == -1) {
            new AlertDialog.Builder(this)
                .setTitle("Ошибка")
                .setMessage("Ошибка открытия расписания")
                .setPositiveButton("Ok", null).show();
        } else {
            Map<String, List<String>> groupedItems = new LinkedHashMap<String, List<String>>();
            List<String> days = departureService.findDaysForDirection(directionId);

            for (String day : days) {
                List<TimeTableRecord> recs = departureService.getTimeTable(directionId, stopId, day);
                groupedItems.put(day, formChilds(recs));
            }

            expListView = (ExpandableListView) findViewById(R.id.dep_list);
            final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(this, days, groupedItems);
            expListView.setAdapter(expListAdapter);

            setGroupIndicatorToRight();

            expListView.setOnChildClickListener(new OnChildClickListener() {
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition,
                        long id) {
//                    final String selected = (String) expListAdapter.getChild(groupPosition, childPosition);
                    Toast.makeText(getBaseContext(), "Скоро будет открываться подробное описание маршрута на это время",
                            Toast.LENGTH_LONG).show();
                    return true;
                }
            });

            if (!days.isEmpty()) {
                expListView.expandGroup(0);
            }
        }

        final String routeName =  getIntent().getStringExtra("routeName");
        Stop stop = stopService.find(stopId);
        if (stop != null) {
            setTitle("№" + routeName + ", " + stop.getName());
        } else {
            setTitle("№" + routeName);
        }
    }

    private List<String> formChilds(List<TimeTableRecord> laptopModels) {
        List<String> items = new ArrayList<String>();
        for (TimeTableRecord model : laptopModels) {
            items.add(model.toString());
        }
        return items;
    }

    private void setGroupIndicatorToRight() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        expListView.setIndicatorBounds(width - pixelsToDips(35), width - pixelsToDips(5));
    }

    public int pixelsToDips(float pixels) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (pixels * scale + 0.5f);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

}
