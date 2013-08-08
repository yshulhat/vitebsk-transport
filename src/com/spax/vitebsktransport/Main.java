package com.spax.vitebsktransport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.spax.vitebsktransport.R;
import com.spax.vitebsktransport.domain.TransportType;

public class Main extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button cityRoutesBtn = (Button) findViewById(R.id.city_routes);
        cityRoutesBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showtransportNumers(TransportType.CITY_BUS);
            }
        });

        final Button stopSearchBtn = (Button) findViewById(R.id.stop_search_btn);
        stopSearchBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StopsSearch.class));
            }
        });

//        final Button mapBtn = (Button) findViewById(R.id.map_btn);
//
//        mapBtn.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), MapTest.class);
//                startActivity(intent);
//            }
//        });
    }

    private void showtransportNumers(TransportType type) {
        Intent intent = new Intent(getApplicationContext(), TransportNumberList.class);
        intent.putExtra("type", type.getId());
        intent.putExtra("transportTitle", type.getTitle());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

}
