package by.sands.vitebsktransport;

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
import by.sands.vitebsktransport.domain.Direction;
import by.sands.vitebsktransport.service.DirectionService;

import java.util.List;

public class DirectionsList extends ListActivity {
    private DirectionService directionService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions_list);
        directionService = new DirectionService(this);

        long routeId =  getIntent().getLongExtra("route", -1L);
        if (routeId == -1L) {
            new AlertDialog.Builder(this)
                .setTitle("Ошибка")
                .setMessage("Ошибка открытия списка направлений")
                .setPositiveButton("Ok", null).show();
        } else {
            List<Direction> routes = directionService.findAll(routeId);
            ArrayAdapter<Direction> adapter = new ArrayAdapter<Direction>(this, android.R.layout.simple_list_item_1,
                    routes);
            setListAdapter(adapter);
        }

        final ListView busList = getListView();
        busList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Direction direction = (Direction) busList.getItemAtPosition(position);
                if (direction != null) {
                    Intent intent = new Intent(getApplicationContext(), StopsList.class);
                    intent.putExtra("direction", direction.getId());
                    startActivity(intent);
                } else {
                    new AlertDialog.Builder(DirectionsList.this)
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
