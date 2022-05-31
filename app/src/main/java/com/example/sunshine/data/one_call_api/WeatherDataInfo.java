package com.example.sunshine.data.one_call_api;

import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sunshine.enums.Values;
import com.example.sunshine.helper_classes.NeededValues;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WeatherDataInfo implements Serializable {
  private double latitude;
  private double longitude;
  private String timezone;
  private int timezone_offset;

  private Values.Unit tempUnit;
  private CurrentData currentData;         //x01
  private MinutelyData minutelyData;       //x60
  private List<RawDataEntry> hourlyData;   //x48
  private List<DailyDataEntry> dailyData;  //x07

  private boolean hasCurrentData;
  private boolean hasMinutelyData;
  private boolean hasHourlyData;
  private boolean hasFulledHourlyData;
  private boolean hasDailyData;
  private boolean hasFulledDailyData;

  private boolean needCurrentData;
  private boolean needMinutelyData;
  private boolean needHourlyData;
  private boolean needDailyData;

  public WeatherDataInfo(){
    this(Values.Unit.KELVIN);
  }
  public WeatherDataInfo(Values.Unit tempUnit) {
    this.tempUnit = tempUnit;

    this.hasCurrentData =
    this.hasMinutelyData =
    this.hasHourlyData =
    this.hasDailyData = false;

    this.hasFulledDailyData =
    this.hasFulledHourlyData = false;

    this.hourlyData = new ArrayList<>();
    this.dailyData = new ArrayList<>();
  }

  public double getLatitude() {
    return latitude;
  }
  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }
  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public String getTimezone() {
    return timezone;
  }
  public void setTimezone(String timezone) {
    this.timezone = timezone;
  }

  public int getTimezone_offset() {
    return timezone_offset;
  }
  public void setTimezone_offset(int timezone_offset) {
    this.timezone_offset = timezone_offset;
  }

  public void setCurrentData(CurrentData currentData){
    if(!this.needCurrentData){
      Log.d("Error cloning", "do not need current data");
      return;
    }
    this.currentData = currentData.clone();
    if(this.currentData == null){
      Log.d("Error cloning", "can not clone current data");
    }
    else {
      this.hasCurrentData = true;
      this.currentData.setTempUnit(this.tempUnit);
    }
  }
  public void setMinutelyData(MinutelyData minutelyData){
    if(!this.needMinutelyData){
      Log.d("Error cloning", "do not need current data");
      return;
    }
    this.minutelyData = minutelyData.clone();
    if(this.minutelyData == null){
      Log.d("Error cloning", "can not clone minutely data");
    }
    else {
      this.hasMinutelyData = true;
      this.minutelyData.setTempUnit(this.tempUnit);
    }
  }
  public void appendHourlyData(RawDataEntry... hourlyData){
    if(!needHourlyData){
      return;
    }
    for(RawDataEntry entry: hourlyData){
      entry.setTempUnit(this.tempUnit);
      this.hourlyData.add(entry);
    }
    if(hourlyData.length >= 48){
      hasFulledHourlyData = true;
    }
    this.hasHourlyData = true;
  }
  public void appendDailyData(DailyDataEntry... dailyData){
    if(!needDailyData){
      return;
    }
    for(DailyDataEntry entry: dailyData){
      entry.setTempUnit(this.tempUnit);
      this.dailyData.add(entry);
    }
    if(dailyData.length >= 7){
      hasFulledDailyData = true;
    }
    this.hasDailyData = true;
  }
  public int getHourlyDataCount(){
    return this.hourlyData.size();
  }
  public int getDailyDataCount(){
    return this.dailyData.size();
  }

  public CurrentData getCurrentData(){
    return this.currentData;
  }
  public MinutelyData getMinutelyData(){
    return this.minutelyData;
  }
  public List<RawDataEntry> getHourlyData(){
    return this.hourlyData;
  }
  public List<DailyDataEntry> getDailyData(){
    return this.dailyData;
  }

  public boolean isHasCurrentData() {
    return hasCurrentData;
  }
  public boolean isHasMinutelyData() {
    return hasMinutelyData;
  }
  public boolean isHasHourlyData() {
    return hasHourlyData;
  }
  public boolean isHasDailyData() {
    return hasDailyData;
  }

  public void setCurrentDataState(boolean currentDataState){
    this.hasCurrentData = currentDataState;
  }
  public void setMinutelyDataState(boolean minutelyDataState){
    this.hasMinutelyData = minutelyDataState;
  }
  public void setHourlyDataState(boolean hourlyDataState){
    this.hasHourlyData = hourlyDataState;
  }
  public void setDailyDataState(boolean dailyDataState){
    this.hasDailyData = dailyDataState;
  }

  public boolean isNeedCurrentData() {
    return needCurrentData;
  }
  public boolean isNeedMinutelyData() {
    return needMinutelyData;
  }
  public boolean isNeedHourlyData() {
    return needHourlyData;
  }
  public boolean isNeedDailyData() {
    return needDailyData;
  }

  public boolean isHasFulledDailyData() {
    return hasFulledDailyData;
  }
  public boolean isHasFulledHourlyData() {
    return hasFulledHourlyData;
  }

  public boolean isFilledAllData(){
    return
        isHasCurrentData() &&
        isHasMinutelyData() &&
        isHasFulledDailyData() &&
        isHasFulledHourlyData();
  }
  public boolean isFilledInData(){
    return
        isHasCurrentData() &&
        isHasMinutelyData()&&
        isHasHourlyData() &&
        isHasDailyData();
  }
  public String toString(){
    StringBuilder builder = new StringBuilder();
    builder.append("longitude: ").append(longitude);
    builder.append("\nlatitude: ").append(latitude);
    builder.append("\ntime zone: ").append(timezone);
    builder.append("\ntime zone offset: ").append(timezone_offset);
    builder.append("\nHas current data? ").append(hasCurrentData);
    if(needCurrentData){
      builder.append("\nCurrent data: ").append(currentData.toString());
    }
    if(needMinutelyData){
      builder.append("\nHas Minutely data? ").append(hasCurrentData);
    }
    if(needHourlyData){
      builder.append("\nHas Hourly data? ").append(hasCurrentData);
    }
    builder.append("\nHourly data: ").append(hourlyData.toString());
    builder.append("\nHas Daily data? ").append(hasCurrentData);
    builder.append("\nDaily data: ").append(dailyData.toString());
    return builder.toString();
  }

  public void setNeedCurrentData(boolean needCurrentData) {
    this.needCurrentData = needCurrentData;
  }
  public void setNeedMinutelyData(boolean needMinutelyData) {
    this.needMinutelyData = needMinutelyData;
  }
  public void setNeedHourlyData(boolean needHourlyData) {
    this.needHourlyData = needHourlyData;
  }
  public void setNeedDailyData(boolean needDailyData) {
    this.needDailyData = needDailyData;
  }

  public void setNeededData(NeededValues values){
    setNeedCurrentData(values.isCurrent());
    setNeedMinutelyData(values.isMinutely());
    setNeedHourlyData(values.isHourly());
    setNeedDailyData(values.isDaily());
    setTempUnit(values.getUnit());
  }
  public NeededValues getNeededData(){
    return new NeededValues(isNeedCurrentData(), isNeedMinutelyData(), isNeedHourlyData(), isNeedDailyData(), getTempUnit());
  }

  public Values.Unit getTempUnit() {
    return tempUnit;
  }

  public void setTempUnit(Values.Unit tempUnit) {
    this.tempUnit = tempUnit;
  }
  //Serializable methods:
  @Override
  public int hashCode() {
    //TODO: make hashCode method
    return super.hashCode();
  }

  @Override
  public boolean equals(@Nullable Object obj) {
    //TODO: make equals method.
    return super.equals(obj);
  }

  @NonNull
  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  @Override
  protected void finalize() throws Throwable {
    super.finalize();
  }
}
