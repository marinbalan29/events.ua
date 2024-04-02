package com.example.eventsua;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class AllActivity extends AppCompatActivity {

    protected int id_user;
    protected List<Event> events;
    ListView list_events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);



        //get id of user
        Intent intent =getIntent();
        id_user = intent.getIntExtra("id_user", 0);


        //show events
        show_events(id_user);



        // menu
        Button all_events, my_tickets;
        all_events = findViewById(R.id.all_button_id);
        my_tickets = findViewById(R.id.my_button_id);
        all_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AllActivity.class);
                intent.putExtra("id_user", id_user);
                startActivity(intent);
            }
        });
        my_tickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MyActivity.class);
                intent.putExtra("id_user", id_user);
                startActivity(intent);
            }
        });
        //end of menu



    }


    private void show_events(int id_user) {

        Call<Response> call=Connection.getConnection().create(Interface.class).show_all(2);
        //listen response  from  response class


        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                //Toast.makeText(AllActivity.this, "Error5", Toast.LENGTH_LONG).show();

                //listen response

                //listen server

                if (response.code() == 200) {
                    if (response.body().getStatus().equals("ok")) {
                        if(response.body().getResult_code() == 1){
                            String[][] result = response.body().getresult();


                            int k = result.length;
                            events = new ArrayList<>();


                            for(int i = 0; i < k; i++){

                                int event_id = Integer.parseInt(result[i][0]);
                                String event_name = result[i][1];
                                String event_place = result[i][2];
                                String date =  result[i][3];
                                String time =  result[i][4];
                                String image_url = result[i][5];

                                String[] date_words = date.split("-");
                                String[] time_words = time.split(":");
                                Calendar event_date = new GregorianCalendar(Integer.parseInt(date_words[0]), Integer.parseInt(date_words[1])-1,
                                        Integer.parseInt(date_words[2]));
                                event_date.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time_words[0]));
                                event_date.set(Calendar.MINUTE, Integer.parseInt(time_words[1]));
                                Event e = new Event(event_id, event_name, event_place, event_date, image_url);
                                events.add(e);
                                //System.out.println(e.toString());


                            }

                            list_events = findViewById(R.id.list_events);

                            CustomAdapter adapter = new CustomAdapter(AllActivity.this, R.layout.event_item, events,1, id_user);

                            list_events.setAdapter(adapter);

                        }else {
                            Toast.makeText(AllActivity.this, "Error is in db connection", Toast.LENGTH_LONG).show();

                        }
                    }
                }
                else
                {
                    //no db connection
                    Toast.makeText(AllActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(AllActivity.this, "Error", Toast.LENGTH_LONG).show();

            }
        });


    }


}