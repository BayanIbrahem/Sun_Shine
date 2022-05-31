
package com.example.sunshine.data.one_call_api;

import android.graphics.drawable.Drawable;

import com.example.sunshine.R;
import com.example.sunshine.enums.Values;
import com.example.sunshine.enums.DataType;

import java.io.Serializable;

public class RawDataEntry implements Cloneable, Serializable {
  private DataType dataType;
  private boolean completed;
  //query info:
  private long id;
  private String iconString;
  private Drawable iconDrawable;
  private long date;

  //general:
  private String state;
  private String description;

  //status:
  private Values.Unit tempUnit;
  private double temp;
  private double feels_like_temp;
  private double pressure;
  private double humidity;
  private double wind_speed;
  private double wind_degree;
  private double wind_gust;
  private double cloud;
  private double rain;

  public RawDataEntry(DataType type) {
    completed = false;
    setTemp(-999);
    setFeels_like_temp(-999);
    this.dataType = type;
  }
  public long getId(){
    return this.id;
  }
  public void setId(long id) {
    this.id = id;
  }

  public String getIconString() {
    return iconString;
  }
  public void setIconString(String iconString) {
    this.iconString = iconString;
  }

  public String getState() {
    return state;
  }
  public void setState(String state) {
    this.state = state;
  }

  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  public double getTemp() {
    return temp;
  }
  public void setTemp(double temp) {
    this.temp = temp;
  }
  public double getFeelingTemp() {
    return feels_like_temp;
  }
  public void setFeelingTemp(double temp) {
    this.feels_like_temp = temp;
  }

  public Values.Unit getTempUnit(){
    return tempUnit;
  }
  public char getTempUnitChar(){
    switch (tempUnit){
      case CELSIUS:
        return 'C';
      case FAHRENHEIT:
        return 'F';
      case KELVIN:
        return 'K';
    }
    return '?';
  }
  public void setTempUnit(Values.Unit unit){
    this.tempUnit = unit;

  }

  public double getPressure() {
    return pressure;
  }
  public void setPressure(double pressure) {
    this.pressure = pressure;
  }

  public double getHumidity() {
    return humidity;
  }
  public void setHumidity(double humidity) {
    this.humidity = humidity;
  }

  public double getWind_speed() {
    return wind_speed;
  }
  public void setWind_speed(double wind_speed) {
    this.wind_speed = wind_speed;
  }

  public double getWind_degree() {
    return wind_degree;
  }
  public void setWind_degree(double wind_degree) {
    this.wind_degree = wind_degree;
  }

  public double getCloud() {
    return cloud;
  }
  public void setCloud(double cloud) {
    this.cloud = cloud;
  }

  public int getIconId(){
    int id = -1;
    switch (iconString){
      case "01d":
        return R.drawable._01d;
      case "01n":
        return R.drawable._01n;
      case "02d":
        return R.drawable._02d;
      case "02n":
        return R.drawable._02n;
      case "03d":
        return R.drawable._03d;
      case "03n":
        return R.drawable._03n;
      case "04d":
        return R.drawable._04d;
      case "04n":
        return R.drawable._04n;
      case "09d":
        return R.drawable._09d;
      case "09n":
        return R.drawable._09n;
      case "10d":
        return R.drawable._10d;
      case "10n":
        return R.drawable._10n;
      case "11d":
        return R.drawable._11d;
      case "11n":
        return R.drawable._11n;
      case "50d":
        return R.drawable._50d;
      case "50n":
        return R.drawable._50n;
    }
    return R.drawable._00;
  }

  public boolean isCompleted() {
    return completed;
  }
  public void completedState(boolean completed) {
    this.completed = completed;
  }

  public static double celsiusToFahrenheit(double cTemp){
    return cTemp * 1.8 + 32;
  }
  public static double celsiusToKelvin(double cTemp){
    return cTemp + 273;
  }
  public static double fahrenheitToCelsius(double fTemp){
    return (fTemp - 32)/1.8;
  }
  public static double fahrenheitToKelvin(double fTemp){
    return (fTemp + 459.4)/1.8 ;
  }
  public static double kelvinToCelsius(double kTemp){
    return kTemp - 273;
  }
  public static double kelvinToFahrenheit(double kTemp){
    return kTemp * 1.8 - 459.4;
  }

  @Override
  public String toString(){
    StringBuilder builder = new StringBuilder();
    builder.append("id: ")
        .append(id)
        .append("\ncode: ")
        .append("\nicon: ")
        .append(iconString)
        .append("\nstate: ")
        .append(state)
        .append("\ndescribtion")
        .append(description)
        .append("\naverage temp: ")
        .append(temp)
        .append("\nfeels like temp")
        .append(feels_like_temp)
        .append("\npressure: ")
        .append(pressure)
        .append("\nhumidity")
        .append(humidity)
        .append("\nwind speed: ")
        .append(wind_speed)
        .append("\nwind degree")
        .append(wind_degree)
        .append("\nclouds: ")
        .append(cloud);
    return builder.toString();
  }

  public double[] convertTempUnit(Values.Unit unit){
    if(getTempUnit() == null || getTempUnit() == unit)
      return new double[]{getTemp(), getFeels_like_temp()};
    switch (unit){
      case CELSIUS:
        if (getTempUnit() == Values.Unit.FAHRENHEIT){
          double cAvg = celsiusToFahrenheit(getTemp());
          double cFeeling = celsiusToFahrenheit(getFeels_like_temp());
          return new double[]{cAvg, cFeeling};
        }
        if (getTempUnit() == Values.Unit.KELVIN){
          double cAvg = celsiusToKelvin(getTemp());
          double cFeeling = celsiusToKelvin(getFeels_like_temp());
          return new double[]{cAvg, cFeeling};
        }
        return new double[]{getTemp(), getFeels_like_temp()};
      case FAHRENHEIT:
        if(getTempUnit() == Values.Unit.CELSIUS){
          double fAvg = fahrenheitToCelsius(getTemp());
          double fFeeling = fahrenheitToCelsius(getFeels_like_temp());
          return new double[]{fAvg, fFeeling};
        }
        if(getTempUnit() == Values.Unit.KELVIN){
          double fAvg = fahrenheitToKelvin(getTemp());
          double fFeeling = fahrenheitToKelvin(getFeels_like_temp());
          return new double[] {fAvg, fFeeling};
        }
        return new double[]{getTemp(), getFeels_like_temp()};
      case KELVIN:
        if(getTempUnit() == Values.Unit.CELSIUS){
          double kAvg = kelvinToCelsius(getTemp());
          double kFeeling = kelvinToCelsius(getFeels_like_temp());
          return new double[]{kAvg, kFeeling};
        }
        if(getTempUnit() == Values.Unit.FAHRENHEIT){
          double kAvg = kelvinToFahrenheit(getTemp());
          double kFeeling = kelvinToFahrenheit(getFeels_like_temp());
          return new double[]{kAvg, kFeeling};
        }
        return new double[]{getTemp(), getFeels_like_temp()};
    }
    return new double[]{getTemp(), getFeels_like_temp()};
  }
  public String getDate(){
    return String.valueOf(this.date);
  }


  public double getFeels_like_temp() {
    return feels_like_temp;
  }
  public void setFeels_like_temp(double feels_like_temp) {
    this.feels_like_temp = feels_like_temp;
  }

  public double getWind_gust() {
    return wind_gust;
  }
  public void setWind_gust(double wind_gust) {
    this.wind_gust = wind_gust;
  }

  public double getRain() {
    return rain;
  }

  public void setRain(double rain) {
    this.rain = rain;
  }

  public DataType getDataType() {
    return dataType;
  }

  public RawDataEntry clone(){
    try {
      return (RawDataEntry) super.clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void setDate(long date) {
    this.date = date;
  }

  public Drawable getIconDrawable() {
    return iconDrawable;
  }
  public void setIconDrawable(Drawable iconDrawable) {
    this.iconDrawable = iconDrawable;
  }
}
