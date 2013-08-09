package com.spax.vitebsktransport.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.spax.vitebsktransport.R;
import com.spax.vitebsktransport.domain.Time;
import com.spax.vitebsktransport.domain.TimeTableRecord;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Activity context;
    private Map<String, List<TimeTableRecord>> items;
    private List<String> groups;
    private int childItemLayout;

    public ExpandableListAdapter(Activity context, List<String> groups, Map<String,
            List<TimeTableRecord>> items, int childItemLayout) {
        this.context = context;
        this.items = items;
        this.groups = groups;
        this.childItemLayout = childItemLayout;
    }

    public TimeTableRecord getChild(int groupPosition, int childPosition) {
        return items.get(groups.get(groupPosition)).get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @SuppressLint("InlinedApi")
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView,
            ViewGroup parent) {
        LinearLayout lout = (LinearLayout) convertView;
        if (lout == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            lout = (LinearLayout) inflater.inflate(childItemLayout, parent, false);
        }

        lout.removeViews(1, lout.getChildCount() - 1);

        TimeTableRecord rec = getChild(groupPosition, childPosition);
        TextView item = (TextView) lout.findViewById(R.id.hour_item);
        item.setText(rec.getTimes().get(0).getStringHours() + ": ");

        Date now = new Date();
        for (final Time t: rec.getTimes()) {
            Button btn = new Button(context);
            btn.setMinHeight(40);
            btn.setMinWidth(40);
            btn.setText(t.getStringMins());
            Calendar c = new GregorianCalendar();
            c.set(Calendar.HOUR_OF_DAY, t.getHours() == 0 ? 24 : t.getHours());
            c.set(Calendar.MINUTE, t.getMins());
            if (c.getTime().after(now)) {
                btn.setTypeface(null, Typeface.BOLD);
            }
            btn.setBackgroundResource(R.drawable.back_btn_light);
            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            lp.setMargins(3, 0, 3, 0);
            btn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Вы выбрали время " + t.toString(), Toast.LENGTH_SHORT).show();
                }
            });

            lout.addView(btn, lp);
        }
        return lout;
    }

    public int getChildrenCount(int groupPosition) {
        return items.get(groups.get(groupPosition)).size();
    }

    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    public int getGroupCount() {
        return groups.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String laptopName = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_item, null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.group_item);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(laptopName);
        return convertView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
