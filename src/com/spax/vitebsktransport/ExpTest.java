package com.spax.vitebsktransport;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

import com.spax.vitebsktransport.adapters.ExpandableListAdapter;
import com.spax.vitebsktransport.domain.TimeTableRecord;
import com.spax.vitebsktransport.service.DepartureService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExpTest extends Activity {
    private DepartureService departureService;
    private ExpandableListView expListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exp_test);

        departureService = new DepartureService(this);

        long stopId = 562L;
        long directionId = 1L;

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
                final String selected = (String) expListAdapter.getChild(groupPosition, childPosition);
                Toast.makeText(getBaseContext(), selected, Toast.LENGTH_LONG).show();
                return true;
            }
        });
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
