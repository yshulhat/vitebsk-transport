package com.spax.vitebsktransport;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.spax.vitebsktransport.R;
import com.spax.vitebsktransport.service.DepartureService;

import java.util.List;

public class DaysList extends ListActivity {
    private DepartureService departureService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days_list);

        departureService = new DepartureService(this);

        final long stop = getIntent().getLongExtra("stop", -1);
        final long directionId = getIntent().getLongExtra("direction", -1);
        if (directionId == -1L) {
            new AlertDialog.Builder(this)
                .setTitle("Ошибка")
                .setMessage("Ошибка поиска дней курсирования")
                .setPositiveButton("Ok", null).show();
        } else {
            List<String> routes = departureService.findDaysForDirection(directionId);
            setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, routes));
        }

        final String routeName =  getIntent().getStringExtra("routeName");
        setTitle("Дни курсирования маршрута №" + routeName);

        final ListView daysList = getListView();
        daysList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String day = (String) daysList.getItemAtPosition(position);
                if (day != null) {
                    Intent intent = new Intent(getApplicationContext(), DeparturesList.class);
                    intent.putExtra("direction", directionId);
                    intent.putExtra("stop", stop);
                    intent.putExtra("day", day);
                    intent.putExtra("routeName", routeName);
                    startActivity(intent);
                } else {
                    new AlertDialog.Builder(DaysList.this)
                            .setTitle("Ошибка")
                            .setMessage("Ошибка открытия расписания")
                            .setPositiveButton("Ok", null).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

}
