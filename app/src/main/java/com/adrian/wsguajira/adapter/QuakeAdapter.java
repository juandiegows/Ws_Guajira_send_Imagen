package com.adrian.wsguajira.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.adrian.wsguajira.R;
import com.adrian.wsguajira.model.Quake;

import java.util.List;

public class QuakeAdapter extends BaseAdapter {
    Context context;
    List<Quake> quakeList;

    public QuakeAdapter(Context context, List<Quake> quakeList) {
        this.context = context;
        this.quakeList = quakeList;
    }

    @Override
    public int getCount() {
        return quakeList.size();
    }

    @Override
    public Object getItem(int position) {
        return quakeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_earth_quake, parent, false);

        TextView title, place, mag;

        title = view.findViewById(R.id.titleRow);
        place = view.findViewById(R.id.placeRow);
        mag = view.findViewById(R.id.magRow);

        title.setText(quakeList.get(position).getProperties().getTitle());
        place.setText(quakeList.get(position).getProperties().getPlace());
        mag.setText(quakeList.get(position).getProperties().getMag()+"");

        return view;
    }
}
