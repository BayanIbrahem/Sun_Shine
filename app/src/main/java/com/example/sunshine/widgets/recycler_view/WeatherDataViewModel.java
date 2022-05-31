package com.example.sunshine.widgets.recycler_view;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sunshine.data.one_call_api.WeatherDataInfo;
import com.example.sunshine.enums.Values;
import com.example.sunshine.networking.NetworkingProcess;
import com.example.sunshine.networking.OneCallURLGenerator;

import java.net.MalformedURLException;

public class WeatherDataViewModel extends ViewModel {

  private MutableLiveData<WeatherDataInfo> liveWeatherData;
  WeatherDataInfo[] weatherDataInfo = new WeatherDataInfo[1];

  public MutableLiveData<WeatherDataInfo> getLiveWeatherData(){
    Log.println(Log.DEBUG, "Weather Data View Model Class: getLiveWeatherData: ", "Method executed");
    //trigger data load
    if(this.liveWeatherData == null){
      Log.println(Log.ERROR, "Weather Data View Model Class: getLiveWeatherData: ", "data is null");
      //trigger data load
      this.liveWeatherData = new MutableLiveData<>();
      loadData();
    }
    if(weatherDataInfo[0] != null) {
      Log.println(
          Log.DEBUG,
          "Weather Data View Model Class: getLiveWeatherData: checking data: \n",
          "set result value \n" + weatherDataInfo[0].toString());
    }
    Log.println(
        Log.ERROR,
        "Weather Data View Model Class: getLiveWeatherData: ",
        "is life data value is null? " + String.valueOf(this.liveWeatherData.getValue() == null));
    if(this.liveWeatherData.getValue() == null){
      Log.println(Log.ERROR, "Weather Data View Model Class: getLiveWeatherData: ", "inner data is null");
      if(weatherDataInfo[0] != null){
        this.liveWeatherData.setValue(weatherDataInfo[0]);
      }
      else {
        //trigger data load
        loadData();
      }
    }
    Log.println(Log.DEBUG, "Weather Data View Model Class: getLiveWeatherData: ", "returning live data");
    return this.liveWeatherData;
  }
  public void loadData(){
    Log.println(Log.DEBUG, "Weather Data View Model Class: loadData: ", "Method executed");
    NetworkingProcess networkingProcess = null;
    //weatherDataVariables
    OneCallURLGenerator urlGenerator = getURLGenerator();
    try {
      networkingProcess = new NetworkingProcess(urlGenerator);
      Log.println(Log.DEBUG, "Weather Data View Model Class: loadData: ", "networkingProcess initiated");
    } catch (MalformedURLException e) {
      Log.println(Log.ERROR, "Weather Data View Model Class: loadData: ", "error: networkingProcess not initiated");
      e.printStackTrace();
    }
    Runnable task = getUiUpdateTask();
    networkingProcess.getReadyResult(weatherDataInfo, task);
    Log.println(Log.DEBUG, "Weather Data View Model Class: loadData: ", "start retrieving data");
  }
  private OneCallURLGenerator getURLGenerator(){
    Log.println(Log.DEBUG, "Weather Data View Model Class: getURLGenerator: ", "method executed");
    OneCallURLGenerator generator = new OneCallURLGenerator(0, 0);
    generator.setType(Values.Type.HOUR);
    generator.setLang(Values.Lang.EN);
    generator.setUnit(Values.Unit.CELSIUS);
    generator.setMode(Values.Mode.JSON);
    Log.println(Log.DEBUG, "Weather Data View Model Class: getURLGenerator: ", "returning value");
    return generator;
  }
  private Runnable getUiUpdateTask(){
    return new Runnable() {
      @Override
      public void run() {
        Log.println(Log.DEBUG, "Weather Data View Model Class: getUiUpdateTask: ", "task executed");
        liveWeatherData.setValue(weatherDataInfo[0]);
        Log.println(Log.DEBUG, "Weather Data View Model Class: getUiUpdateTask: ", "value of data: " + weatherDataInfo.toString());
      }
    };
  }
}
