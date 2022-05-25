package com.example.sunshine.networking;

import android.util.Log;

import com.example.sunshine.data.Data_State_History;
import com.example.sunshine.data.one_call_api.CurrentData;
import com.example.sunshine.data.one_call_api.DailyDataEntry;
import com.example.sunshine.data.one_call_api.WeatherDataInfo;
import com.example.sunshine.data.one_call_api.MinutelyData;
import com.example.sunshine.data.one_call_api.RawDataEntry;
import com.example.sunshine.enums.DataType;
import com.example.sunshine.enums.states.DataState;
import com.example.sunshine.helper_classes.NeededValues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

public class OneCallResponseGetter extends ResponseGetter<WeatherDataInfo> {
  private WeatherDataInfo data;
  public OneCallResponseGetter(URL url){
    super(url);
    Log.println(Log.DEBUG, "URL:", this.url.toString());
    data = new WeatherDataInfo();
  }

  /**return the formatted final data, nullable*/
  @Override
  public WeatherDataInfo getFormattedData(InputStream result) {
    JSONObject jsonData = this.retrieveDate(result);
    if(Data_State_History.dataState == DataState.INVALID_JSON){
      return null;
    }
    if(jsonData == null){
      return data;
    }
    try {
      double latitude = jsonData.getDouble("lat");
      double longitude = jsonData.getDouble("lon");
      String timezone = jsonData.getString("timezone");
      int timezone_offset = jsonData.getInt("timezone_offset");
      data.setLatitude(latitude);
      data.setLongitude(longitude);
      data.setTimezone(timezone);
      data.setTimezone_offset(timezone_offset);
    } catch (JSONException e) {
      e.printStackTrace();
      Log.d("Fail to parse json full data: ", "can not get general data");
      Data_State_History.generalData = DataState.ParseJson.CANNOT_PARSE_JSON_GENERAL_DATA;
    }
    if(data.isNeedCurrentData()){
      try {
        JSONObject currentJson = jsonData.getJSONObject("current");
        long sunrise = currentJson.getLong("sunrise");
        long sunset = currentJson.getLong("sunset");
        CurrentData currentData =(CurrentData) fillSharedData(currentJson, DataType.CURRENT, "current");
        currentData.setSunrise(sunrise);
        currentData.setSunset(sunset);
        this.data.setCurrentData(currentData);
        this.data.setCurrentDataState(true);
      } catch (JSONException e) {
        e.printStackTrace();
        Log.d("Fail to parse json full data: ", "can not get current data");
        Data_State_History.currentData = DataState.ParseJson.CANNOT_PARSE_JSON_CURRENT_DATA;
      }
    }
    if(data.isNeedMinutelyData()){
      try {
        JSONArray minutelyJsonArray = jsonData.getJSONArray("minutely");
        MinutelyData minutelyData = new MinutelyData();
        for(int i=0; i<minutelyJsonArray.length(); i++){
          JSONObject item = minutelyJsonArray.getJSONObject(i);
          long date = item.getLong("dt");
          int precipitation = item.getInt("precipitation");
          minutelyData.addToItems(date, precipitation);
        }
        data.setMinutelyData(minutelyData);
        data.setMinutelyDataState(true);
      } catch (JSONException e) {
        e.printStackTrace();
        Log.d("Fail to parse json full data: ", "can not get minutely data");
        Data_State_History.minutelyData = DataState.ParseJson.CANNOT_PARSE_JSON_MINUTELY_DATA;
      }
    }
    if(data.isNeedHourlyData()){
      try {
        JSONArray hourlyJson = jsonData.getJSONArray("hourly");
        for(int i=0; i<hourlyJson.length(); i++){
          JSONObject hourlyJsonJSONObject = hourlyJson.getJSONObject(i);
          data.appendHourlyData(fillSharedData(hourlyJsonJSONObject, DataType.HOURLY, "hourly"));
        }
        data.setHourlyDataState(true);
      } catch (JSONException e) {
        e.printStackTrace();
        Log.d("Fail to parse json full data: ", "can not get hourly data");
        Data_State_History.hourlyData = DataState.ParseJson.CANNOT_PARSE_JSON_HOURLY_DATA;
      }
    }
    if(data.isNeedDailyData()){
      try {
        JSONArray dailyData = jsonData.getJSONArray("daily");
        for(int i=0; i<dailyData.length(); i++){
          JSONObject dailyJsonObject = dailyData.getJSONObject(i);
          DailyDataEntry dailyDataEntry = (DailyDataEntry) fillSharedData(dailyJsonObject, DataType.DAILY, "daily");

          //more data:
          dailyDataEntry.setSunrise(dailyJsonObject.getLong("sunrise"));
          dailyDataEntry.setSunset(dailyJsonObject.getLong("sunset"));
          dailyDataEntry.setMoonrise(dailyJsonObject.getLong("moonrise"));
          dailyDataEntry.setMoonset(dailyJsonObject.getLong("moonset"));
          dailyDataEntry.setMoon_phase(dailyJsonObject.getLong("moon_phase"));

          //temp:
          JSONObject tempJsonObject = dailyJsonObject.getJSONObject("temp");
          dailyDataEntry.setDay_temp(tempJsonObject.getDouble("day"));
          dailyDataEntry.setNight_temp(tempJsonObject.getDouble("night"));
          dailyDataEntry.setEvening_temp(tempJsonObject.getDouble("eve"));
          dailyDataEntry.setMorning_temp(tempJsonObject.getDouble("morn"));
          dailyDataEntry.setMax_temp(tempJsonObject.getDouble("max"));
          dailyDataEntry.setMin_temp(tempJsonObject.getDouble("min"));

          //feeling temp:
          JSONObject feelingLikeJsonObject = dailyJsonObject.getJSONObject("feels_like");
          dailyDataEntry.setDay_feeling_temp(feelingLikeJsonObject.getDouble("day"));
          dailyDataEntry.setNight_feeling_temp(feelingLikeJsonObject.getDouble("night"));
          dailyDataEntry.setEvening_feeling_temp(feelingLikeJsonObject.getDouble("eve"));
          dailyDataEntry.setMorning_feeling_temp(feelingLikeJsonObject.getDouble("morn"));

          //additional data:
          dailyDataEntry.setRain(dailyJsonObject.getDouble("rain"));
          data.appendDailyData(dailyDataEntry);
        }
        data.setDailyDataState(true);
      } catch (JSONException e) {
        e.printStackTrace();
        Log.d("Fail to parse json full data: ", "can not get daily data");
        Data_State_History.dailyData = DataState.ParseJson.CANNOT_PARSE_JSON_DAILY_DATA;
      }
    }
    return data;
  }

  /**fill the shared data in daily, current and hourly data, it is not null, but may cause class cast exception*/
  private  RawDataEntry fillSharedData(JSONObject jsonObject, DataType type, String message){
    RawDataEntry rawDataTempEntry = null;
    if(type == DataType.DAILY){
      rawDataTempEntry = new DailyDataEntry();
    }
    else if(type == DataType.CURRENT){
      rawDataTempEntry = new CurrentData();
    }
    else{
      rawDataTempEntry = new RawDataEntry(type);
    }
    try {
    long date = jsonObject.getLong("dt");
    double temp = jsonObject.getDouble("temp");
    double feelingTemp = jsonObject.getDouble("feels_like");
    double pressure = jsonObject.getDouble("pressure");
    double humidity = jsonObject.getDouble("humidity");
    double wind_speed = jsonObject.getDouble("wind_speed");
    double wind_deg = jsonObject.getDouble("wind_deg");
    double wind_gust = jsonObject.getDouble("wind_gust");
    double clouds = jsonObject.getDouble("clouds");

    JSONObject weather = jsonObject.getJSONArray("weather").getJSONObject(0);

    long id = weather.getLong("id");
    String status = weather.getString("main");
    String description = weather.getString("description");
    String icon = weather.getString("icon");

    rawDataTempEntry.setDate(date);
    rawDataTempEntry.setTemp(temp);
    rawDataTempEntry.setFeelingTemp(feelingTemp);
    rawDataTempEntry.setPressure(pressure);
    rawDataTempEntry.setHumidity(humidity);
    rawDataTempEntry.setWind_speed(wind_speed);
    rawDataTempEntry.setWind_degree(wind_deg);
    rawDataTempEntry.setWind_gust(wind_gust);
    rawDataTempEntry.setId(id);
    rawDataTempEntry.setState(status);
    rawDataTempEntry.setDescription(description);
    rawDataTempEntry.setIconString(icon);
    rawDataTempEntry.setCloud(clouds);

    JSONObject rainJson = jsonObject.getJSONObject("rain");
    rawDataTempEntry.setRain(rainJson.getDouble("1h"));

  } catch (JSONException e) {
    e.printStackTrace();
    Log.d("Fail to parse json full data: ", "can not get" + message + " data");
    Data_State_History.rawData = DataState.ParseJson.CANNOT_PARSE_JSON_RAW_DATA;
  }
    return rawDataTempEntry;
  }
  /**just specify the data types that we need*/
  public void setNeededData(NeededValues values){
    data.setNeededData(values);
  }
}

