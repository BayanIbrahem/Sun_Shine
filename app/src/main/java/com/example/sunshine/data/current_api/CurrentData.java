package com.example.sunshine.data.current_api;

import com.example.sunshine.R;
import com.example.sunshine.enums.attributes.CurrentAttrs;
import com.example.sunshine.enums.Values;

public class CurrentData {
  private boolean completed;
  //query info:
  private long id;
  private long code;
  private String icon;
  private long date;

  //general:
  private String state;
  private String description;

  //place info:
  private double longitude;
  private double latitude;
  private String city;

  //status:
  private Values.Unit tempUnit;
  private double avg_temp;
  private double min_temp;
  private double max_temp;
  private double pressure;
  private double humidity;
  private double wind_speed;
  private double wind_degree;
  private double cloud;


  public CurrentData() {
    completed = false;
    setAvg_temp(-999);
    setMax_temp(-999);
    setMin_temp(-999);
  }

  public long getId() {
    return id;
  }
  public void setId(long id) {
    this.id = id;
  }

  public long getCode() {
    return code;
  }
  public void setCode(long code) {
    this.code = code;
  }

  public void setDate(long date) {
    this.date = date;
  }

  public String getIcon() {
    return icon;
  }
  public void setIcon(String icon) {
    this.icon = icon;
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

  public double getLongitude() {
    return longitude;
  }
  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public double getLatitude() {
    return latitude;
  }
  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public String getCity() {
    return city;
  }
  public void setCity(String city) {
    this.city = city;
  }

  public double getAvg_temp() {
    return avg_temp;
  }
  public void setAvg_temp(double avg_temp) {
    this.avg_temp = avg_temp;
  }

  public double getMin_temp() {
    return min_temp;
  }
  public void setMin_temp(double min_temp) {
    this.min_temp = min_temp;
  }

  public double getMax_temp() {
    return max_temp;
  }
  public void setMax_temp(double max_temp) {
    this.max_temp = max_temp;
  }

  public Values.Unit getTempUnit(){
    return tempUnit;
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
    switch (icon){
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
        .append(code)
        .append("\nicon: ")
        .append(icon)
        .append("\nstate: ")
        .append(state)
        .append("\ndescribtion")
        .append(description)
        .append("\nlongitude: ")
        .append(longitude)
        .append("\nlatitude:")
        .append(latitude)
        .append("\ncity: ")
        .append(city)
        .append("\navirage temp: ")
        .append(avg_temp)
        .append("\nmin temp: ")
        .append(min_temp)
        .append("\nmax temp: ")
        .append(max_temp)
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
      return new double[]{getAvg_temp(), getMax_temp(), getMin_temp()};
    switch (unit){
      case CELSIUS:
        if (getTempUnit() == Values.Unit.FAHRENHEIT){
          double cAvg = celsiusToFahrenheit(getAvg_temp());
          double cMin = celsiusToFahrenheit(getMin_temp());
          double cMax = celsiusToFahrenheit(getMax_temp());
          return new double[]{cAvg, cMin, cMax};
        }
        if (getTempUnit() == Values.Unit.KELVIN){
          double cAvg = celsiusToKelvin(getAvg_temp());
          double cMin = celsiusToKelvin(getMin_temp());
          double cMax = celsiusToKelvin(getMax_temp());
          return new double[]{cAvg, cMin, cMax};
        }
        return new double[]{getAvg_temp(), getMax_temp(), getMin_temp()};
      case FAHRENHEIT:
        if(getTempUnit() == Values.Unit.CELSIUS){
          double fAvg = fahrenheitToCelsius(getAvg_temp());
          double fMin = fahrenheitToCelsius(getMin_temp());
          double fMax = fahrenheitToCelsius(getMax_temp());
          return new double[]{fAvg, fMin, fMax};
        }
        if(getTempUnit() == Values.Unit.KELVIN){
          double fAvg = fahrenheitToKelvin(getAvg_temp());
          double fMin = fahrenheitToKelvin(getMin_temp());
          double fMax = fahrenheitToKelvin(getMax_temp());
          return new double[] {fAvg, fMin, fMax};
        }
        return new double[]{getAvg_temp(), getMax_temp(), getMin_temp()};
      case KELVIN:
        if(getTempUnit() == Values.Unit.CELSIUS){
          double kAvg = kelvinToCelsius(getAvg_temp());
          double kMin = kelvinToCelsius(getMin_temp());
          double kMax = kelvinToCelsius(getMax_temp());
          return new double[]{kAvg, kMin, kMax};
        }
        if(getTempUnit() == Values.Unit.FAHRENHEIT){
          double kAvg = kelvinToFahrenheit(getAvg_temp());
          double kMin = kelvinToFahrenheit(getMin_temp());
          double kMax = kelvinToFahrenheit(getMax_temp());
          return new double[]{kAvg, kMin, kMax};
        }
        return new double[]{getAvg_temp(), getMax_temp(), getMin_temp()};
    }
    return new double[]{getAvg_temp(), getMax_temp(), getMin_temp()};
  }
  public String getDate(){
    //TODO: FORMAT THE DATE
    return String.valueOf(getDate());
  }
}
