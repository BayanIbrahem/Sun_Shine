package com.example.sunshine.helper_classes;

import com.example.sunshine.enums.Values;

/**
 * this class detemine the needed boolean values for the request.
 * */
public class NeededValues {
  private boolean current;
  private boolean minutely;
  private boolean hourly;
  private boolean daily;
  private Values.Unit unit;

  public NeededValues(boolean current, boolean minutely, boolean hourly, boolean daily, Values.Unit unit){
    this.current = current;
    this.minutely = minutely;
    this.hourly = hourly;
    this.daily = daily;
    this.unit = unit;
  }
  public NeededValues(Values.Unit unit){
    this(false, false, false, false, unit);
  }
  public NeededValues(){
    this(false, false, false, false, Values.Unit.KELVIN);
  }

  public boolean isCurrent() {
    return current;
  }
  public void setCurrent(boolean current) {
    this.current = current;
  }

  public boolean isMinutely() {
    return minutely;
  }
  public void setMinutely(boolean minutely) {
    this.minutely = minutely;
  }

  public boolean isHourly() {
    return hourly;
  }
  public void setHourly(boolean hourly) {
    this.hourly = hourly;
  }

  public boolean isDaily() {
    return daily;
  }
  public void setDaily(boolean daily) {
    this.daily = daily;
  }

  public int numOfData(){
    return (current?1:0) + (minutely?1:0) + (hourly?1:0) + (daily?1:0);
  }

  public String toString(){
    return
         "\ncurr: " + isCurrent()
        +"\nmin : " + isMinutely()
        +"\nhour: " + isHourly()
        +"\nday : " + isDaily()
        +"\nunit: " + getUnit();
  }

  public Values.Unit getUnit() {
    return unit;
  }

  public void setUnit(Values.Unit unit) {
    this.unit = unit;
  }
}
