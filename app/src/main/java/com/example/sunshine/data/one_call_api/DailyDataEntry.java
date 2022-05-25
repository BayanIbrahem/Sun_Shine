package com.example.sunshine.data.one_call_api;

import com.example.sunshine.enums.Values;
import com.example.sunshine.enums.DataType;

public class DailyDataEntry extends RawDataEntry implements Cloneable{

  private boolean completed;


  //feels like temp:
  //place info:
  //query info:
  //temp:
  private double day_feeling_temp;
  private double day_temp;
  private double evening_feeling_temp;
  private double evening_temp;
  private double max_temp;
  private double min_temp;
  private double moon_phase;
  private double morning_feeling_temp;
  private double morning_temp;
  private double night_feeling_temp;
  private double night_temp;
  private long moonrise;
  private long moonset;
  private long sunrise;
  private long sunset;

  public DailyDataEntry() {
    super(DataType.DAILY);
    completed = false;
    setMax_temp(-999);
    setMin_temp(-999);
  }

  public void completedState(boolean completed) {
    this.completed = completed;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("id: ")
        .append(getId())
        .append("\nicon: ")
        .append(getIconString())
        .append("\nstate: ")
        .append(getState())
        .append("\ndescription")
        .append(getDescription())
        .append("\naverage temp: ")
        .append(getTemp())
        .append("\nmin temp: ")
        .append(getMax_temp())
        .append("\nmax temp: ")
        .append(getMin_temp())
        .append("\npressure: ")
        .append(getPressure())
        .append("\nhumidity: ")
        .append(getHumidity())
        .append("\nwind speed: ")
        .append(getWind_degree())
        .append("\nwind degree: ")
        .append(getWind_degree())
        .append("\nwind gust: ")
        .append(getWind_gust())
        .append("\nclouds: ")
        .append(getCloud());
    return builder.toString();
  }

  public double[] convertTempUnit(Values.Unit unit) {
    if (getTempUnit() == null || getTempUnit() == unit)
      return new double[]{getAvg_temp(), getMax_temp(), getMin_temp()};
    switch (unit) {
      case CELSIUS:
        if (getTempUnit() == Values.Unit.FAHRENHEIT) {
          double cAvg = celsiusToFahrenheit(getAvg_temp());
          double cMin = celsiusToFahrenheit(getMin_temp());
          double cMax = celsiusToFahrenheit(getMax_temp());
          return new double[]{cAvg, cMin, cMax};
        }
        if (getTempUnit() == Values.Unit.KELVIN) {
          double cAvg = celsiusToKelvin(getAvg_temp());
          double cMin = celsiusToKelvin(getMin_temp());
          double cMax = celsiusToKelvin(getMax_temp());
          return new double[]{cAvg, cMin, cMax};
        }
        return new double[]{getAvg_temp(), getMax_temp(), getMin_temp()};
      case FAHRENHEIT:
        if (getTempUnit() == Values.Unit.CELSIUS) {
          double fAvg = fahrenheitToCelsius(getAvg_temp());
          double fMin = fahrenheitToCelsius(getMin_temp());
          double fMax = fahrenheitToCelsius(getMax_temp());
          return new double[]{fAvg, fMin, fMax};
        }
        if (getTempUnit() == Values.Unit.KELVIN) {
          double fAvg = fahrenheitToKelvin(getAvg_temp());
          double fMin = fahrenheitToKelvin(getMin_temp());
          double fMax = fahrenheitToKelvin(getMax_temp());
          return new double[]{fAvg, fMin, fMax};
        }
        return new double[]{getAvg_temp(), getMax_temp(), getMin_temp()};
      case KELVIN:
        if (getTempUnit() == Values.Unit.CELSIUS) {
          double kAvg = kelvinToCelsius(getAvg_temp());
          double kMin = kelvinToCelsius(getMin_temp());
          double kMax = kelvinToCelsius(getMax_temp());
          return new double[]{kAvg, kMin, kMax};
        }
        if (getTempUnit() == Values.Unit.FAHRENHEIT) {
          double kAvg = kelvinToFahrenheit(getAvg_temp());
          double kMin = kelvinToFahrenheit(getMin_temp());
          double kMax = kelvinToFahrenheit(getMax_temp());
          return new double[]{kAvg, kMin, kMax};
        }
        return new double[]{getAvg_temp(), getMax_temp(), getMin_temp()};
    }
    return new double[]{getAvg_temp(), getMax_temp(), getMin_temp()};
  }

  public String getDate() {
    //TODO: FORMAT THE DATE
    return String.valueOf(getDate());
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

  public long getMoonrise() {
    return moonrise;
  }

  public void setMoonrise(long moonrise) {
    this.moonrise = moonrise;
  }

  public long getMoonset() {
    return moonset;
  }

  public void setMoonset(long moonset) {
    this.moonset = moonset;
  }

  public double getMoon_phase() {
    return moon_phase;
  }

  public void setMoon_phase(double moon_phase) {
    this.moon_phase = moon_phase;
  }

  public double getDay_temp() {
    return day_temp;
  }

  public void setDay_temp(double day_temp) {
    this.day_temp = day_temp;
  }

  public double getNight_temp() {
    return night_temp;
  }

  public void setNight_temp(double night_temp) {
    this.night_temp = night_temp;
  }

  public double getEvening_temp() {
    return evening_temp;
  }

  public void setEvening_temp(double evening_temp) {
    this.evening_temp = evening_temp;
  }

  public double getMorning_temp() {
    return morning_temp;
  }

  public void setMorning_temp(double morning_temp) {
    this.morning_temp = morning_temp;
  }

  public double getMin_temp() {
    return this.min_temp;
  }

  public void setMin_temp(double min_temp) {
    this.min_temp = min_temp;
    if (isSetAllTemps()) {
      setTemp((getMin_temp() + getMax_temp()) * 0.5);
    }
  }

  public double getMax_temp() {
    return this.max_temp;
  }

  public void setMax_temp(double max_temp) {
    this.max_temp = max_temp;
    if (isSetAllTemps()) {
      setTemp((getMin_temp() + getMax_temp()) * 0.5);
    }
  }

  public double getDay_feeling_temp() {
    return day_feeling_temp;
  }

  public void setDay_feeling_temp(double day_feeling_temp) {
    this.day_feeling_temp = day_feeling_temp;
  }

  public double getNight_feeling_temp() {
    return night_feeling_temp;
  }

  public void setNight_feeling_temp(double night_feeling_temp) {
    this.night_feeling_temp = night_feeling_temp;
  }

  public double getEvening_feeling_temp() {
    return evening_feeling_temp;
  }

  public void setEvening_feeling_temp(double evening_feeling_temp) {
    this.evening_feeling_temp = evening_feeling_temp;
  }

  public double getMorning_feeling_temp() {
    return morning_feeling_temp;
  }

  public void setMorning_feeling_temp(double morning_feeling_temp) {
    this.morning_feeling_temp = morning_feeling_temp;
  }

  public double getAvg_temp() {
    return super.getTemp();
  }

  public void setAvg_temp(double temp) {
    super.setTemp(temp);
  }

  public boolean isSetAllTemps() {
    return
        getMin_temp() != -999 &&
            getMax_temp() != -999;
  }

  public DailyDataEntry clone(){
    return (DailyDataEntry) super.clone();
  }
}
