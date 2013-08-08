package com.spax.vitebsktransport;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.spax.vitebsktransport.R;
import com.spax.vitebsktransport.domain.Route;
import com.spax.vitebsktransport.service.RouteService;

import java.util.List;

public class TransportNumberList extends ListActivity {
    private RouteService routeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_number_list);
        routeService = new RouteService(this);

        String transportType =  getIntent().getStringExtra("type");
        String transportTitle =  getIntent().getStringExtra("transportTitle");
        setTitle(transportTitle);
        List<Route> routes = routeService.findAll(transportType);
        ArrayAdapter<Route> adapter = new ArrayAdapter<Route>(this, android.R.layout.simple_list_item_1, routes);
        setListAdapter(adapter);

        final ListView busList = getListView();
        busList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Route route = (Route) busList.getItemAtPosition(position);
                if (route != null) {
                    Intent intent = new Intent(getApplicationContext(), DirectionsList.class);
                    intent.putExtra("route", route.getId());
                    intent.putExtra("routeName", route.getNumber());
                    startActivity(intent);
                } else {
                    new AlertDialog.Builder(TransportNumberList.this)
                            .setTitle("Ошибка")
                            .setMessage("Ошибка открытия списка направлений")
                            .setPositiveButton("Ok", null)
                            .show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        routeService.close();
        super.onPause();
    }

    @Override
    protected void onResume() {
        routeService.open();
        super.onResume();
    }

}
