package by.sands.vitebsktransport;

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
import by.sands.vitebsktransport.domain.Route;
import by.sands.vitebsktransport.service.RouteService;

import java.util.List;

public class TransportNumberList extends ListActivity {
    private RouteService routeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_number_list);
        // Show the Up button in the action bar.
        // getActionBar().setDisplayHomeAsUpEnabled(true);
        routeService = new RouteService(this);

        String transportType =  getIntent().getStringExtra("type");
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_bus_number_list, menu);
        return true;
    }

    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Route> adapter = (ArrayAdapter<Route>) getListAdapter();
        // Route route = null;
        switch (view.getId()) {
        // case R.id.add:
        // String[] numbers = new String[] { "1", "2", "2a" };
        // String[] names = new String[] { "Минск - Пинск", "Витебск - Брест", "Минск - Шминск" };
        // int nextInt = new Random().nextInt(3);
        // // Save the new comment to the database
        // route = routeService.create(numbers[nextInt], names[nextInt]);
        // adapter.add(route);
        // break;
        // case R.id.delete:
        // if (getListAdapter().getCount() > 0) {
        // route = (Route) getListAdapter().getItem(0);
        // routeService.delete(route);
        // adapter.remove(route);
        // }
        // break;
        }
        adapter.notifyDataSetChanged();
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
