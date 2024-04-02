package com.example.eventsua;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MyActivity extends AppCompatActivity {

    protected int id_user;
    protected List<Event> events;
    protected ListView list_events;
    protected TextView no_tickets;

    public void open_ticket() {
        //get id of user
        Intent intent =getIntent();
        id_user = intent.getIntExtra("id_user", 0);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

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

        Call<Response> call=Connection.getConnection().create(Interface.class).show_my(id_user, 3);
        //listen response  from  response class
        list_events = findViewById(R.id.list_my_events);


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
                            //System.out.println("result.length = "+result.length);


                            int k = result.length;
                            if (k==0){
                                list_events.setVisibility(View.INVISIBLE);
                                no_tickets =findViewById(R.id.no_tickets);
                                no_tickets.setVisibility(View.VISIBLE);
                                return;
                            }
                            events = new ArrayList<>();


                            for(int i = 0; i < k; i++){


                                String event_name = result[i][0];
                                String event_place = result[i][1];
                                String date =  result[i][2];
                                String time =  result[i][3];
                                String image_url = result[i][4];
                                String ticket_name = result[i][5];

                                String[] date_words = date.split("-");
                                String[] time_words = time.split(":");
                                Calendar event_date = new GregorianCalendar(Integer.parseInt(date_words[0]), Integer.parseInt(date_words[1])-1,
                                        Integer.parseInt(date_words[2]));
                                event_date.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time_words[0]));
                                event_date.set(Calendar.MINUTE, Integer.parseInt(time_words[1]));
                                Event e = new Event(event_name, event_place, event_date, image_url, ticket_name);
                                events.add(e);
                                //System.out.println("event: " +  e.toString());


                            }
                            //System.out.println(events.toString());



                            CustomAdapter adapter = new CustomAdapter(MyActivity.this, R.layout.event_item, events,2);

                            list_events.setAdapter(adapter);

                        }else {
                            Toast.makeText(MyActivity.this, "Error is in db connection", Toast.LENGTH_LONG).show();

                        }
                    }
                }
                else
                {
                    //no db connection
                    Toast.makeText(MyActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(MyActivity.this, "Error", Toast.LENGTH_LONG).show();

            }
        });


    }
}