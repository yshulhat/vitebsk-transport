package com.spax.vitebsktransport.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.spax.vitebsktransport.R;
import com.spax.vitebsktransport.domain.PathHolder;
import com.spax.vitebsktransport.domain.Stop;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StopAdapter extends ArrayAdapter<Stop> {
    private Context context;
    private int layoutResourceId;
    private List<Stop> stops;

    public StopAdapter(Context context, int textViewResourceId, List<Stop> list) {
        super(context, textViewResourceId, list);
        this.context = context;
        layoutResourceId = textViewResourceId;
        stops = new ArrayList<Stop>(list);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
        }

        ImageView fromStop = (ImageView) row.findViewById(R.id.start_point);
        fromStop.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Stop stop = getItem(position);
                PathHolder.getInstance().setFromStop(stop.getId());
                Toast.makeText(context, "Выбрана начальная точка маршрута: " + stop.getName(), Toast.LENGTH_LONG)
                        .show();
            }
        });

        ImageView toStop = (ImageView) row.findViewById(R.id.end_point);
        toStop.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Stop stop = getItem(position);
                PathHolder.getInstance().setToStop(stop.getId());
                Toast.makeText(context, "Выбрана конечная точка маршрута: " + stop.getName(), Toast.LENGTH_LONG).show();
            }
        });

        TextView item = (TextView) row.findViewById(R.id.stop_list_item);
        Stop stop = getItem(position);
        item.setText(stop.toString());

        return row;
    }

    public void filter(CharSequence s) {
        String txt = s.toString().toLowerCase(Locale.getDefault());
        clear();
        if (txt.length() == 0) {
            for (Stop stop : stops) {
                add(stop);
            }
        } else {
            for (Stop stop : stops) {
                if (stop.getName().toLowerCase(Locale.getDefault()).contains(txt)) {
                    add(stop);
                }
            }
        }
        notifyDataSetChanged();
    }
}
