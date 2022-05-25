package com.example.sunshine.data.one_call_api;

import com.example.sunshine.enums.Values;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MinutelyData implements Cloneable {
  private List<Query> items;
  private Values.Unit tempUnit;
  public MinutelyData(){
    this(Values.Unit.KELVIN);
  }
  public MinutelyData(Values.Unit tempUnit){
    items = new ArrayList<>(60);
    this.tempUnit = tempUnit;
  }
  public void addToItems(long date, int precipitation){
    int index = 0;
    while(items.get(index).date < date && index<items.size()){
      index++;
    }
    items.add(index, new Query(date, precipitation));
  }
  public long getDateAt(int index){
    return items.get(index).getDate();
  }
  public String getFormattedDateAt(int index){
    //TODO: FORMAT THE DATE
    return String.valueOf(items.get(index).getDate());
  }
  public long getPrecipitationAt(int index){
    return items.get(index).getPrecipitation();
  }

  public Values.Unit getTempUnit() {
    return tempUnit;
  }

  public void setTempUnit(Values.Unit tempUnit) {
    this.tempUnit = tempUnit;
  }

  private class Query implements Cloneable{
    private long date;
    private int precipitation;

    public Query(long date, int precipitation){
      this.date = date;
      this.precipitation = precipitation;
    }
    public Query(){
      this.date = 0;
      this.precipitation = -1;
    }

    public long getDate() {
      return date;
    }
    public void setDate(long date) {
      this.date = date;
    }

    public int getPrecipitation() {
      return precipitation;
    }
    public void setPrecipitation(int precipitation) {
      this.precipitation = precipitation;
    }
    public Query clone(){
      try {
        return (Query)super.clone();
      } catch (CloneNotSupportedException e) {
        e.printStackTrace();
      }
      return null;
    }
    public String toString(){
      StringBuilder builder = new StringBuilder();
      builder.append("date: ").append(date).append("precipitation").append(precipitation);
      return builder.toString();
    }
  }

  public MinutelyData clone(){
    MinutelyData value = null;
    try {
      value = (MinutelyData) super.clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
    if(value == null)
      return null;
    List<Query> itemsCopy = new ArrayList<>(value.items.size());
    Iterator<Query> iterator = value.items.listIterator();
    while(iterator.hasNext()){
      itemsCopy.add((Query) iterator.next().clone());
    }
    value.items = itemsCopy;
    return value;
  }
  public String toString(){
    StringBuilder builder = new StringBuilder();
    for(int i=0; i<items.size(); i++){
      builder.append("minute #").append(i+1).append(":\n\t").append(items.get(i).toString());
    }
    return builder.toString();
  }
}
