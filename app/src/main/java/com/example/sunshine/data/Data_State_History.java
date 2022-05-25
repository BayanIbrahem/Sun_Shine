package com.example.sunshine.data;

import com.example.sunshine.enums.states.ConnectionState;
import com.example.sunshine.enums.states.DataState;

public class Data_State_History {
  public static ConnectionState connectionState = ConnectionState.SUCCESS_CONNECTION;
  public static DataState dataState = DataState.SUCCESS_GETTING_DATA;
  public static DataState.ParseJson generalData = DataState.ParseJson.SUCCESS_PARSING_JSON_GENERAL_DATA;
  public static DataState.ParseJson currentData = DataState.ParseJson.SUCCESS_PARSING_JSON_CURRENT_DATA;
  public static DataState.ParseJson minutelyData = DataState.ParseJson.SUCCESS_PARSING_JSON_MINUTELY_DATA;
  public static DataState.ParseJson hourlyData = DataState.ParseJson.SUCCESS_PARSING_JSON_HOURLY_DATA;
  public static DataState.ParseJson dailyData = DataState.ParseJson.SUCCESS_PARSING_JSON_DAILY_DATA;
  public static DataState.ParseJson alertsData = DataState.ParseJson.SUCCESS_PARSING_JSON_ALERT_DATA;
  public static DataState.ParseJson rawData = DataState.ParseJson.CANNOT_PARSE_JSON_RAW_DATA;
  public static int connectionCode = -1;
  public static String errorJsonMessage = "no error in Json";
}
