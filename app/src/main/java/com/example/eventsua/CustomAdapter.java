package com.example.eventsua;

//import static androidx.core.content.ContextCompat.startActivity;
//import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CustomAdapter extends ArrayAdapter<Event> {
    List<Event> events;
    int resource;
    Context context;
    int type;
    int id_user;

    public CustomAdapter(Context context, int resource, List<Event> events, int type) {
        super(context, resource, events);
        this.context = context;
        this.resource = resource;
        this.events = events;
        this.type = type;
    }
    public CustomAdapter(Context context, int resource, List<Event> events, int type, int id_user) {
        super(context, resource, events);
        this.context = context;
        this.resource = resource;
        this.events = events;
        this.type = type;
        this.id_user = id_user;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        System.out.println(position);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        @SuppressLint("ViewHolder") View view = layoutInflater.inflate(resource, null, false);
        TextView textViewName = view.findViewById(R.id.name_event);
        TextView textViewPlace = view.findViewById(R.id.place_event);
        TextView textViewDate = view.findViewById(R.id.date_event);
        TextView textViewTicket = view.findViewById(R.id.ticket);

        ImageView image = view.findViewById(R.id.image);
        System.out.println(position);
        final Event event = events.get(position);
        textViewName.setText(event.getEvent_name());
        textViewPlace.setText(event.getEvent_place());

        SimpleDateFormat formati = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.ENGLISH);
        textViewDate.setText(formati.format(event.getEvent_date().getTime()));

        Picasso.get().load(event.getImage_url()).into(image);
        Button btn = view.findViewById(R.id.open_event);

        if(type == 1){

            textViewTicket.setVisibility(View.INVISIBLE);
            //btn.setOnClickListener(MyActivity.open_ticket());
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CurrentEventActivity.class);
                    intent.putExtra("id_user", id_user);
                    intent.putExtra("id_event", event.getEvent_id());
                    context.startActivity(intent);
                }
            });

        }
        else{

            btn.setVisibility(View.INVISIBLE);
            textViewTicket.setText(event.getTicket_name());
        }

        return view;



    }
}
