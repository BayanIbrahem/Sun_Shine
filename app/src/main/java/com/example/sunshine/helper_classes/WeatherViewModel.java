package com.example.sunshine.helper_classes;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sunshine.data.one_call_api.WeatherDataInfo;

public class WeatherViewModel extends ViewModel {
  private MutableLiveData<WeatherDataInfo> weatherDataDetails;
  public MutableLiveData<WeatherDataInfo> getWeatherDataDetails(){
    if(this.weatherDataDetails == null){
      this.weatherDataDetails = new MutableLiveData<>();
    }
    if(this.weatherDataDetails.getValue() == null){
      this.weatherDataDetails.setValue(new WeatherDataInfo());
    }
    return this.weatherDataDetails;
  }
  public void setWeatherData(WeatherDataInfo sourceWeatherData){
    this.getWeatherDataDetails().setValue(sourceWeatherData);
  }
  public void putExtraWeatherData(WeatherDataInfo extraWeatherData){
    this.getWeatherDataDetails().postValue(extraWeatherData);
  }
  public String toString(){
    return
        "-----------------------\n" +
        "View Model:\n" + getWeatherDataDetails().getValue().toString() +
        "\n-----------------------";
  }
}
