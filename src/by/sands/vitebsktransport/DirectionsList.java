package by.sands.vitebsktransport;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import by.sands.vitebsktransport.model.Direction;
import by.sands.vitebsktransport.service.DirectionService;

import java.util.List;

public class DirectionsList extends ListActivity {
    private DirectionService directionService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions_list);
        directionService = new DirectionService(this);
        directionService.open();

        long routeId =  getIntent().getLongExtra("route", -1);
        if (routeId == -1) {
            new AlertDialog.Builder(this)
                .setTitle("Ошибка")
                .setMessage("Ошибка открытия списка направлений")
                .setPositiveButton("Ok", null)
                .show();
        } else {
            List<Direction> routes = directionService.findAll(routeId);
            ArrayAdapter<Direction> adapter = new ArrayAdapter<Direction>(this, android.R.layout.simple_list_item_1,
                    routes);
            setListAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_directions_list, menu);
        return true;
    }

    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Direction> adapter = (ArrayAdapter<Direction>) getListAdapter();
        switch (view.getId()) {
        // add cases here...
        }
        adapter.notifyDataSetChanged();
    }
}
