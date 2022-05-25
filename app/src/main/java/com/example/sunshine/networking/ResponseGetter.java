package com.example.sunshine.networking;

import android.util.Log;

import com.example.sunshine.data.Data_State_History;
import com.example.sunshine.enums.states.ConnectionState;
import com.example.sunshine.enums.states.DataState;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

public abstract class ResponseGetter<Type> {
  protected URL url;
  public ResponseGetter(URL url){
    this.url = url;
  }
  /**connect and get input steam nullable*/
  public  InputStream getInputStream() {
    InputStream result = null;
    HttpURLConnection httpConnection = null;
    try {
      httpConnection = (HttpURLConnection) url.openConnection();
    } catch (IOException e) {
      Data_State_History.connectionState = ConnectionState.CANNOT_OPEN_CONNECTION;
    }
//    httpConnection.setConnectTimeout(100000);//ms
//    httpConnection.setReadTimeout(150000);   //ms
    try {
      httpConnection.setRequestMethod("GET");
    } catch (ProtocolException e) {
      Data_State_History.connectionState = ConnectionState.CANNOT_SET_REQUEST_METHOD;
    }
    try {
      httpConnection.connect();
    } catch (IOException e) {
      Data_State_History.connectionState = ConnectionState.CANNOT_CONNECT;
    }
    try {
      result = httpConnection.getInputStream();
    } catch (IOException e) {
      try {
        Log.w("Problem With Connection", "fail to get Input Stream error "+httpConnection.getResponseCode() + url.toString());
      } catch (IOException ioException) {
        ioException.printStackTrace();
      }
      Data_State_History.connectionState = ConnectionState.CANNOT_GET_INPUT_STREAM;
    }
    try {
      Data_State_History.connectionCode =  httpConnection.getResponseCode();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }
  /** convert the binary response to String, not null*/
  protected String getString(InputStream result){
    Log.d("INPUT_STREAM", String.valueOf(result == null));
    if(result == null){
      Data_State_History.dataState = DataState.NULL_INPUT_STREAM;
      return "{\"connection_error\": \"fail to get response...\"}";
    }
    Scanner scanner = new Scanner(result);
    scanner.useDelimiter("\\A");
    if(scanner.hasNext()) {
      String str = scanner.next();
      Log.println(Log.DEBUG, "JSON:", str);
      return str;
    }
    else{
      Data_State_History.dataState = DataState.BLANK_INPUT_STREAM;
    }
    return "{\"connection_error\": \"empty response\"}";
  }
  /**convert string value to json value, nullable*/
  protected  JSONObject retrieveDate(InputStream response){
    JSONObject data = null;
    String result = getString(response);
    try {
      data = new JSONObject(result);
      Log.println(Log.DEBUG, "JSON:", "successfully get json");
    } catch (JSONException e) {
      Log.w("Error_In_Data", "Fail to create json object");
      Data_State_History.dataState = DataState.CANNOT_CREATE_JSON;
//        String error = data.getString("connection_error");
//        STATE_CHECKER.errorJsonMessage = error;
//        STATE_CHECKER.dataState = DataState.INVALID_JSON;
    }
    return data;
  }
  public abstract Type getFormattedData(InputStream result);
}
