package com.example.sunshine.networking;

import android.util.Log;

import com.example.sunshine.data.current_api.CurrentData;
import com.example.sunshine.data.Data_State_History;
import com.example.sunshine.enums.states.DataState;
import com.example.sunshine.enums.Values;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

public class CurrentResponseGetter extends ResponseGetter<CurrentData>{
  private CurrentData data;
  public CurrentResponseGetter(URL url){
    super(url);
    data = new CurrentData();
  }
  public CurrentData getFormattedData(InputStream result){
    JSONObject jsonData = retrieveDate(result);
    try {
      JSONObject position = jsonData.getJSONObject("coord");
      double lon = position.getDouble("lon");
      double lat = position.getDouble("lat");
      data.setLongitude(lon);
      data.setLatitude(lat);
    } catch (JSONException e) {
      Log.w("Error IN JSON Data", "can not get position json object");
      Data_State_History.dataState = DataState.INVALID_JSON;
    }
    try {
      JSONObject weather = jsonData.getJSONArray("weather").getJSONObject(0);
      long id = weather.getLong("id");
      String main = weather.getString("main");
      String description = weather.getString("description");
      String icon = weather.getString("icon");
      data.setId(id);
      data.setState(main);
      data.setDescription(description);
      data.setIcon(icon);
    } catch (JSONException e) {
      Log.w("Error IN JSON Data", "can not get weather json object");
      Data_State_History.dataState = DataState.INVALID_JSON;
    }
    try {
      JSONObject details = jsonData.getJSONObject("main");
      double tempAvg = details.getDouble("temp");
      int pressure = details.getInt("pressure");
      int humidity = details.getInt("humidity");
      double minTemp = details.getDouble("temp_min");
      double maxTemp = details.getDouble("temp_max");
      data.setAvg_temp(tempAvg);
      data.setPressure(pressure);
      data.setHumidity(humidity);
      data.setMin_temp(minTemp);
      data.setMax_temp(maxTemp);
    } catch (JSONException e) {
      Log.w("Error IN JSON Data", "can not get main json object");
      Data_State_History.dataState = DataState.INVALID_JSON;
    }
    try {
      JSONObject wind = jsonData.getJSONObject("wind");
      double speed = wind.getDouble("speed");
      double deg = wind.getInt("deg");
      data.setWind_speed(speed);
      data.setWind_degree(deg);
    } catch (JSONException e) {
      Log.w("Error IN JSON Data", "can not get wind json object");
      Data_State_History.dataState = DataState.INVALID_JSON;
    }
    try {
      int clouds = jsonData.getJSONObject("clouds").getInt("all");
      data.setCloud(clouds);
    } catch (JSONException e) {
      Log.w("Error IN JSON Data", "can not get cloud json object");
      Data_State_History.dataState = DataState.INVALID_JSON;
    }
    try {
      data.setDate(jsonData.getLong("dt"));
      data.setCode(jsonData.getInt("cod"));
      data.setCity(jsonData.getString("name"));
      data.setId(jsonData.getLong("id"));
    } catch (JSONException e) {
      Log.w("Error IN JSON Data", "can not get info json object");
      Data_State_History.dataState = DataState.INVALID_JSON;
    }
    data.completedState(true);
    data.setTempUnit(Values.Unit.CELSIUS);
    return data;
  }

}
