package com.example.eventsua;

import com.google.gson.annotations.SerializedName;

public class Response {
    @SerializedName("status")
    private String status;

    @SerializedName("result_code")
    private int result_code;
    @SerializedName("iduser")
    private int iduser;
    @SerializedName("result")
    private String[][] result;
    @SerializedName("result_event")
    private String[] result_event;

    //create getter method


    public String getStatus() {
        return status;
    }
    public int getResult_code() {
        return result_code;
    }
    public int getidusere() {
        return iduser;
    }
    public String[][] getresult() {
        return result;
    }
    public String[] getresulevent() {
        return result_event;
    }
}
