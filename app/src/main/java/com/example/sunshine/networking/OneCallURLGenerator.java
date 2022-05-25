package com.example.sunshine.networking;

import android.net.Uri;
import android.util.Log;

import com.example.sunshine.enums.Values;
import com.example.sunshine.helper_classes.NeededValues;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class OneCallURLGenerator {
  private final String BASE_URL = "https://api.openweathermap.org/data/2.5/onecall?";
  private final String ATTR_KEY = "appid";     //required
  private final String ATTR_LANG = "lang";     //optional
  private final String ATTR_LATITUDE = "lat";  //required
  private final String ATTR_LONGITUDE = "lon"; //required
  private final String ATTR_MODE = "mode";     //optional
  private final String ATTR_OFFSET = "timezone_offset"; //optional
  private final String ATTR_QUERY = "q";       //logically required
  private final String ATTR_TIMEZONE = "timezone";
  private final String ATTR_TYPE = "exclude";     //optional
  private final String ATTR_UNIT = "units";    //optional
  private final String VAL_KEY = "ce58ff3b1ee960a325c8aa1544daca90";
  private final String VAL_LANG_AR = "ar";
  private final String VAL_LANG_EN = "en";
  private final String VAL_MODE_HTML = "html";
  private final String VAL_MODE_JSON = "json";
  private final String VAL_MODE_XML = "xml";
  private final String VAL_TYPE_ALERT = "alerts";
  private final String VAL_TYPE_CURRENT = "current";
  private final String VAL_TYPE_DAY = "daily";
  private final String VAL_TYPE_HOUR = "hourly";
  private final String VAL_TYPE_MIN = "minutely";
  private final String VAL_UNIT_CELSIUS = "metric";
  private final String VAL_UNIT_FAHRENHEIT = "imperial";

  private boolean lang;
  private boolean latitude;
  private boolean longitude;
  private boolean mode;
  private boolean query;
  private boolean unit;
  private Values.Unit tempUnitCurrentValue;


  Uri.Builder buildUri;

  public OneCallURLGenerator(double longitude, double latitude){
    buildUri = Uri.parse(BASE_URL).buildUpon().appendQueryParameter(ATTR_KEY, VAL_KEY);
    buildUri.appendQueryParameter(ATTR_LONGITUDE, String.valueOf(longitude));
    buildUri.appendQueryParameter(ATTR_LATITUDE, String.valueOf(latitude));
    this.latitude = this.longitude = true;
    mode = unit = lang = false;
    this.tempUnitCurrentValue = Values.Unit.KELVIN;
  }
  public NeededValues setType(Values.Type... items){
    List<Values.Type> arr = new ArrayList<>(5);
    NeededValues values = new NeededValues(tempUnitCurrentValue);
    arr.add(Values.Type.CURR);
    arr.add(Values.Type.MIN);
    arr.add(Values.Type.HOUR);
    arr.add(Values.Type.DAY);
    arr.add(Values.Type.ALERT);
    for(Values.Type item: items){
      if(item == Values.Type.CURR){
        values.setCurrent(true);
      }
      if(item == Values.Type.MIN){
        values.setMinutely(true);
      }
      if(item == Values.Type.HOUR){
        values.setHourly(true);
      }
      if(item == Values.Type.DAY){
        values.setDaily(true);
      }
      arr.remove(item);
    }
    String strValue = "";
    for(Values.Type item: arr) {
      switch (item){
      case CURR:
        strValue += VAL_TYPE_CURRENT + ",";
        break;
      case MIN:
        strValue += VAL_TYPE_MIN + ",";
        break;
      case HOUR:
        strValue += VAL_TYPE_HOUR + ",";
        break;
      case DAY:
        strValue += VAL_TYPE_DAY + ",";
        break;
      case ALERT:
        strValue += VAL_TYPE_ALERT + ",";
        break;
      }
    }
    buildUri.appendQueryParameter(ATTR_TYPE, strValue.substring(0, strValue.length()-1));
    return values;
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
    this.unit = true;
    this.tempUnitCurrentValue = unit;
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
  public void setOffset(int seconds){
    buildUri.appendQueryParameter(ATTR_OFFSET, String.valueOf(seconds));
  }
  public void setCity(String city){
    buildUri.appendQueryParameter(ATTR_QUERY, city);
  }
  public void setTimeZone(String timeZone){
    buildUri.appendQueryParameter(ATTR_TIMEZONE, timeZone);
  }

  public URL getURL() throws MalformedURLException {
    String str = buildUri.build().toString();
    Log.println(Log.DEBUG, "URLBuilder:", str);
    return new URL(str);

  }
}
