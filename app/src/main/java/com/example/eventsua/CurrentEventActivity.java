package com.example.eventsua;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

public class CurrentEventActivity extends AppCompatActivity {

    protected int id_user;
    protected int id_event;

    protected RadioGroup radioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_event);

        //get id of user
        Intent intent = getIntent();
        id_user = intent.getIntExtra("id_user", 0);
        id_event = intent.getIntExtra("id_event", 0);

        showEvent(id_event);

        Button buy = findViewById(R.id.buy);
        radioGroup = findViewById(R.id.radioGroup);

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id_ticket = radioGroup.getCheckedRadioButtonId();
                System.out.println("id_ticket: " +  id_ticket);
                Call<Response> call=Connection.getConnection().create(Interface.class).buy(id_user, id_ticket, 5);
                call.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        //Toast.makeText(AllActivity.this, "Error5", Toast.LENGTH_LONG).show();

                        //listen response

                        //listen server

                        if (response.code() == 200) {
                            if (response.body().getStatus().equals("ok")) {
                                if(response.body().getResult_code() == 1){
                                    Toast.makeText(CurrentEventActivity.this, "The ticket is bought. You can see it in 'My tickets'", Toast.LENGTH_LONG).show();


                                }else {
                                    Toast.makeText(CurrentEventActivity.this, "Error is in db connection", Toast.LENGTH_LONG).show();

                                }
                            }
                        }
                        else
                        {
                            //no db connection
                            Toast.makeText(CurrentEventActivity.this, "Error1", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        Toast.makeText(CurrentEventActivity.this, "Error2", Toast.LENGTH_LONG).show();

                    }
                });



            }
        });



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

    private void showEvent(int id_event) {
        Call<Response> call=Connection.getConnection().create(Interface.class).show_event(id_event, 4);

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
                            String[] result_event = response.body().getresulevent();
                            //System.out.println("result.length = "+result.length);


                            int k = result.length;

                            radioGroup = findViewById(R.id.radioGroup);
                            RadioButton button;


                            for(int i = 0; i < k; i++){
                                int ticket_id = Integer.parseInt(result[i][0]);
                                String ticket_name = result[i][1];
                                int cost = Integer.parseInt(result[i][2]);
                                Ticket ticket = new Ticket(ticket_id, ticket_name, cost);



                                button = new RadioButton(CurrentEventActivity.this);
                                button.setText(ticket.getName_Cost());
                                button.setId(ticket.getTicket_id());
                                radioGroup.addView(button);
                                //System.out.println("event: " +  e.toString());
                            }
                            String event_name = result_event[0];
                            String event_place = result_event[1];
                            String date =  result_event[2];
                            String time =  result_event[3];
                            String image_url = result_event[4];
                            String description = result_event[5];

                            String[] date_words = date.split("-");
                            String[] time_words = time.split(":");
                            Calendar event_date = new GregorianCalendar(Integer.parseInt(date_words[0]), Integer.parseInt(date_words[1])-1,
                                    Integer.parseInt(date_words[2]));
                            event_date.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time_words[0]));
                            event_date.set(Calendar.MINUTE, Integer.parseInt(time_words[1]));

                            Event e = new Event(event_name, event_place, image_url, description, event_date);

                            TextView textViewName = CurrentEventActivity.this.findViewById(R.id.name_event);
                            TextView textViewPlace = CurrentEventActivity.this.findViewById(R.id.place_event);
                            TextView textViewDate = CurrentEventActivity.this.findViewById(R.id.date_event);
                            TextView textViewDesrc= CurrentEventActivity.this.findViewById(R.id.descr_event);

                            ImageView image = CurrentEventActivity.this.findViewById(R.id.image);

                            textViewName.setText(e.getEvent_name());
                            textViewPlace.setText(e.getEvent_place());
                            textViewDesrc.setText(e.getEvent_description());

                            SimpleDateFormat formati = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.ENGLISH);
                            textViewDate.setText(formati.format(e.getEvent_date().getTime()));

                            Picasso.get().load(e.getImage_url()).into(image);







                        }else {
                            Toast.makeText(CurrentEventActivity.this, "Error is in db connection", Toast.LENGTH_LONG).show();

                        }
                    }
                }
                else
                {
                    //no db connection
                    Toast.makeText(CurrentEventActivity.this, "Error1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(CurrentEventActivity.this, "Error2", Toast.LENGTH_LONG).show();

            }
        });


    }
}