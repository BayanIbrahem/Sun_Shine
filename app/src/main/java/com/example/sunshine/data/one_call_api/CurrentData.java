package com.example.sunshine.data.one_call_api;

import com.example.sunshine.enums.DataType;

public class CurrentData extends RawDataEntry implements Cloneable{

  private long sunrise;
  private long sunset;

  public CurrentData() {
    super(DataType.CURRENT);
  }

  public long getSunrise() {
    return sunrise;
  }

  public void setSunrise(long sunrise) {
    this.sunrise = sunrise;
  }

  public long getSunset() {
    return sunset;
  }

  public void setSunset(long sunset) {
    this.sunset = sunset;
  }

  public CurrentData clone(){
    return (CurrentData) super.clone();
  }
  public String toString(){
    return super.toString() +
        "sunrise: " + sunrise +
        "sunset: " + sunset;
  }
}
