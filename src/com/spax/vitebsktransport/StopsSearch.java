package com.spax.vitebsktransport;

import android.app.ListActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.spax.vitebsktransport.domain.Stop;
import com.spax.vitebsktransport.service.StopService;

import java.util.List;

public class StopsSearch extends ListActivity {
    private StopService stopService;
    private ArrayAdapter<Stop> adapter;
    private EditText field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stops_search);
        stopService = new StopService(this);

        List<Stop> routes = stopService.findAll();
        adapter = new ArrayAdapter<Stop>(this, android.R.layout.simple_list_item_1, routes);
        setListAdapter(adapter);

        field = (EditText) findViewById(R.id.stop_search_field);
        field.addTextChangedListener(filterTextWatcher);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.stops_search, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        field.removeTextChangedListener(filterTextWatcher);
    }

    private TextWatcher filterTextWatcher = new TextWatcher() {
        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            adapter.getFilter().filter(s);
        }
    };
}
