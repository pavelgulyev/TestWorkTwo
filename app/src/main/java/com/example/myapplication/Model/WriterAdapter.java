package com.example.myapplication.Model;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;


import com.example.myapplication.R;

import java.util.List;

public class WriterAdapter extends ArrayAdapter<Writer> {

    private LayoutInflater inflater;
    private int layout;
    private List<Writer> writers;
    public WriterAdapter(@NonNull Context context, int resource, @NonNull List<Writer> writers) {
        super(context, resource, writers);
        this.writers = writers;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);

        TextView nameView = view.findViewById(R.id.Name);
        TextView surNameView = view.findViewById(R.id.Surname);
        TextView PatronymicView = view.findViewById(R.id.Patronymic);
        Writer state = writers.get(position);
        nameView.setText(state.getName());
        surNameView.setText(state.getSurname());
        PatronymicView.setText(state.getPatronymic());
        return view;
    }
}
