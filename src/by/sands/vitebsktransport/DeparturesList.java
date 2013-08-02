package by.sands.vitebsktransport;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import by.sands.vitebsktransport.domain.TimeTableRecord;
import by.sands.vitebsktransport.service.DepartureService;

import java.util.List;

public class DeparturesList extends ListActivity {
    private DepartureService departureService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departures_list);
        departureService = new DepartureService(this);

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_departures_list, menu);
        return true;
    }

}
