package com.example.sunshine.networking;

import android.net.Uri;

import com.example.sunshine.enums.Values;

import java.net.MalformedURLException;
import java.net.URL;

public class CurrentURLGenerator {
  private final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?";
  private final String ATTR_KEY = "appid";     //required
  private final String ATTR_MODE = "mode";     //optional
  private final String ATTR_LANG = "lang";     //optional
  private final String ATTR_LATITUDE = "lat";  //required
  private final String ATTR_LONGITUDE = "lon"; //required
  private final String ATTR_QUERY = "q";       //logically required
  private final String ATTR_UNIT = "units";    //optional

  private boolean latitude;
  private boolean longitude;
  private boolean query;
  private boolean mode;
  private boolean unit;
  private boolean lang;

  private final String VAL_KEY = "ce58ff3b1ee960a325c8aa1544daca90";
  private final String VAL_MODE_JSON = "json";
  private final String VAL_MODE_XML = "xml";
  private final String VAL_MODE_HTML = "html";
  private final String VAL_UNIT_FAHRENHEIT = "imperial";
  private final String VAL_UNIT_CELSIUS = "metric";
  private final String VAL_LANG_AR = "ar";
  private final String VAL_LANG_EN = "en";

  Uri.Builder buildUri;

  public CurrentURLGenerator(String city, double longitude, double latitude){
    buildUri.appendQueryParameter(ATTR_KEY, VAL_KEY);
    buildUri.appendQueryParameter(ATTR_QUERY, city);
    buildUri.appendQueryParameter(ATTR_LONGITUDE, String.valueOf(longitude));
    buildUri.appendQueryParameter(ATTR_LATITUDE, String.valueOf(latitude));
    this.latitude = this.longitude = query= true;
    mode = unit = lang = false;
  }
  public CurrentURLGenerator(double longitude, double latitude){
    buildUri = Uri.parse(BASE_URL).buildUpon().appendQueryParameter(ATTR_KEY, VAL_KEY);
    buildUri.appendQueryParameter(ATTR_LONGITUDE, String.valueOf(longitude));
    buildUri.appendQueryParameter(ATTR_LATITUDE, String.valueOf(latitude));
    this.latitude = this.longitude = true;
    mode = unit = lang = false;
  }
  public void setMode(Values.Mode mode){
    if(this.mode)
      return;
    switch (mode){
      case JSON:
        buildUri.appendQueryParameter(ATTR_MODE, VAL_MODE_JSON);
        break;
      case XML:
        buildUri.appendQueryParameter(ATTR_MODE, VAL_MODE_XML);
        break;
      case HTML:
        buildUri.appendQueryParameter(ATTR_MODE, VAL_MODE_HTML);
        break;
    }
    this.mode = true;
  }
  public void setUnit(Values.Unit unit){
    if(this.unit)
      return;
    switch (unit) {
      case CELSIUS:
        buildUri.appendQueryParameter(ATTR_UNIT, VAL_UNIT_CELSIUS);
        break;
      case FAHRENHEIT:
        buildUri.appendQueryParameter(ATTR_UNIT, VAL_UNIT_FAHRENHEIT);
        break;
    }
  }
  public void setLang(Values.Lang lang){
    if(this.lang)
      return;
    switch (lang){
      case AR:
        buildUri.appendQueryParameter(ATTR_LANG, VAL_LANG_AR);
        break;
      case EN:
        buildUri.appendQueryParameter(ATTR_LANG, VAL_LANG_EN);
        break;
    }
    this.lang = true;
  }
  public void setCity(String city){
    buildUri.appendQueryParameter(ATTR_QUERY, city);
  }
  public URL getURL() throws MalformedURLException {
    return new URL(buildUri.build().toString());

  }
}

/*test(
      {
         "coord":{
            "lon":-0.13,
            "lat":51.51
         },
         "weather":[
            {
               "id":300,
               "main":"Drizzle",
               "description":"light intensity drizzle",
               "icon":"09d"
            }
         ],
         "base":"stations",
         "main":{
            "temp":280.32,
            "pressure":1012,
            "humidity":81,
            "temp_min":279.15,
            "temp_max":281.15
         },
         "visibility":10000,
         "wind":{
            "speed":4.1,
            "deg":80
         },
         "clouds":{
            "all":90
         },
         "dt":1485789600,
         "sys":{
            "type":1,
            "id":5091,
            "message":0.0103,
            "country":"GB",
            "sunrise":1485762037,
            "sunset":1485794875
         },
         "id":2643743,
         "name":"London",
         "cod":200
      }
   )
*/