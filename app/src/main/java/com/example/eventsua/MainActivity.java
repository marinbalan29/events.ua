package com.example.eventsua;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText email_element, password_element;
        email_element = findViewById(R.id.enteremail);
        password_element = findViewById(R.id.enterpass);


        Button enter = findViewById(R.id.enter_button);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_element.getText().toString();
                String password = password_element.getText().toString();
                if(email.equals("") || password.equals("")){
                    Toast.makeText(v.getContext(), "Fill in all cells", Toast.LENGTH_LONG).show();
                }
                else{
                    login(email, password, v);
                }
            }
        });
    }

    protected void login(String email, String password, View v){
        Call<Response> call=Connection.getConnection().create(Interface.class).login(email,password, 1);
        //listen response  from  response class

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                //listen response

                //listen server

                if (response.code() == 200) {
                    if (response.body().getStatus().equals("ok")) {
                        if (response.body().getResult_code() == 0) {
                            Toast.makeText(v.getContext(), "Login is not found", Toast.LENGTH_LONG).show();
                        }else if(response.body().getResult_code() == 1){
                            Toast.makeText(v.getContext(), "Password is wrong", Toast.LENGTH_LONG).show();
                        }else if(response.body().getResult_code() == 2){
                            Toast.makeText(v.getContext(), "You are authorized", Toast.LENGTH_LONG).show();
                            int id_user=response.body().getidusere();
                            Intent intent = new Intent(v.getContext(), AllActivity.class);
                            intent.putExtra("id_user", id_user);
                            startActivity(intent);


                        }else {
                            Toast.makeText(v.getContext(), "Error is in db connection", Toast.LENGTH_LONG).show();

                        }
                    }
                }
                else
                {
                    //no db connection
                    Toast.makeText(MainActivity.this, "Error1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(v.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }
}