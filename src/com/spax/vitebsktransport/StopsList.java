package com.spax.vitebsktransport;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.spax.vitebsktransport.adapters.StopAdapter;
import com.spax.vitebsktransport.domain.Stop;
import com.spax.vitebsktransport.service.StopService;

import java.util.List;

public class StopsList extends ListActivity {
    private StopService stopService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stops_list);

        stopService = new StopService(this);

        final long directionId = getIntent().getLongExtra("direction", -1);
        if (directionId == -1L) {
            new AlertDialog.Builder(this)
                .setTitle("Ошибка")
                .setMessage("Ошибка поиска остановок")
                .setPositiveButton("Ok", null).show();
        } else {
            List<Stop> routes = stopService.findByDirection(directionId);
            setListAdapter(new StopAdapter(this, R.layout.stops_list_item, routes));
        }

        final String routeName =  getIntent().getStringExtra("routeName");
        final String dirName =  getIntent().getStringExtra("directionName");
        setTitle("№" + routeName + ", " + dirName);

        final ListView busList = getListView();
        busList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Stop stop = (Stop) busList.getItemAtPosition(position);
                if (stop != null) {
                    Intent intent = new Intent(getApplicationContext(), ExpandableDeparturesList.class);
                    intent.putExtra("direction", directionId);
                    intent.putExtra("stop", stop.getId());
                    intent.putExtra("routeName", routeName);
                    startActivity(intent);
                } else {
                    new AlertDialog.Builder(StopsList.this)
                            .setTitle("Ошибка")
                            .setMessage("Ошибка открытия расписания")
                            .setPositiveButton("Ok", null).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

}
