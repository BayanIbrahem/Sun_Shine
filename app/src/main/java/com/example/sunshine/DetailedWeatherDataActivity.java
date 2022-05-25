package com.example.sunshine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sunshine.data.one_call_api.RawDataEntry;

public class DetailedWeatherDataActivity extends AppCompatActivity {
  //constants:
  public static class Constants{
    public static final int VISIBLE_VIEW = View.VISIBLE;      //0
    public static final int INVISIBLE_VIEW = View.INVISIBLE;  //4
    public static final int GONE_VIEW_VISIBILITY = View.GONE; //8
  }
  //textViews:
  private TextView dateTextView;
  private TextView mainStateTextView;
  private TextView descriptionTextView;
  private TextView avgTempTextView;
  private TextView maxTempTextView;
  private TextView minTempTextView;
  private TextView dayTempTextView;
  private TextView dayFeelingTempTextView;
  private TextView nightTempTextView;
  private TextView nightFeelingTempTextView;
  private TextView morningTempTextView;
  private TextView morningFeelingTempTextView;
  private TextView eveningTempTextView;
  private TextView eveningFeelingTempTextView;
  private TextView pressureTextView;
  private TextView humidityTextView;
  private TextView windLabelTextView;
  private TextView windSpeedTextView;
  private TextView windDegreeTextView;
  private TextView windGustTextView;
  private TextView cloudsTextView;
  private TextView rainTextView;
  //icons:
  private ImageView backgroundImage;
  //buttons:
  private ImageButton expandTempInfoButton;

  //flags:
  private boolean isExtraTempInfoCollapsed = true;
  //data:
  RawDataEntry weatherDetailedData;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detailed_weather_data);
    initTextViewInOnCreate();
    initOtherUiWidgets();
    setWidgetsResources();
    getIntentData();
    setExpandButtonClickAction();
  }
  //private methods:
  //initialisation:
  private void initTextViewInOnCreate(){
    dateTextView = findViewById(R.id.details_tv_date);
    mainStateTextView = findViewById(R.id.details_tv_mainState);
    descriptionTextView = findViewById(R.id.details_tv_weatherDetailsInfo);
    avgTempTextView = findViewById(R.id.details_tv_avgTemp);
    maxTempTextView = findViewById(R.id.details_tv_maxTemp);
    minTempTextView = findViewById(R.id.details_tv_minTemp);
    dayTempTextView = findViewById(R.id.details_tv_dayTemp);
    dayFeelingTempTextView = findViewById(R.id.details_tv_feelingDayTemp);
    nightTempTextView = findViewById(R.id.details_tv_nightTemp);
    nightFeelingTempTextView = findViewById(R.id.details_tv_feelingNightTemp);
    morningTempTextView = findViewById(R.id.details_tv_morningTemp);
    morningFeelingTempTextView = findViewById(R.id.details_tv_feelingMorningTemp);
    eveningTempTextView = findViewById(R.id.details_tv_eveningTemp);
    eveningFeelingTempTextView = findViewById(R.id.details_tv_feelingEviningTemp);
    pressureTextView = findViewById(R.id.details_tv_pressure);
    humidityTextView = findViewById(R.id.details_tv_humidity);
    windLabelTextView = findViewById(R.id.details_tv_wind);
    windSpeedTextView = findViewById(R.id.details_tv_windSpeed);
    windDegreeTextView = findViewById(R.id.details_tv_windDegree);
    windGustTextView = findViewById(R.id.details_tv_windGust);
    cloudsTextView = findViewById(R.id.details_tv_clouds);
    rainTextView = findViewById(R.id.details_tv_rain);
  }
  private void initOtherUiWidgets(){
    backgroundImage = findViewById(R.id.details_image_backgroundImage);
    expandTempInfoButton = findViewById(R.id.details_button_expandTemp);
  }
  private void setWidgetsResources(){
    setTempWidgetsResources();
    setOtherWidgetsResources();
  }
  private void setTempWidgetsResources(){
    avgTempTextView.setText("");
    maxTempTextView.setText("");
    minTempTextView.setText("");
    dayTempTextView.setText("");
    dayFeelingTempTextView.setText("");
    nightTempTextView.setText("");
    nightFeelingTempTextView.setText("");
    morningTempTextView.setText("");
    morningFeelingTempTextView.setText("");
    eveningTempTextView.setText("");
    eveningFeelingTempTextView.setText("");
  }
  private void setOtherWidgetsResources(){
    //main resources:
    dateTextView.setText("");
    mainStateTextView.setText("");
    descriptionTextView.setText("");

    //details resources:
    pressureTextView.setText("");
    humidityTextView.setText("");
    windLabelTextView.setText("");
    windSpeedTextView.setText("");
    windDegreeTextView.setText("");
    windGustTextView.setText("");
    cloudsTextView.setText("");
    rainTextView.setText("");
    //icons:
    backgroundImage.setImageResource(weatherDetailedData.getIconId());
  }
  private void getIntentData(){
    //TODO: build body
  }
  //set button on click action:
  private void setExpandButtonClickAction(){
    expandTempInfoButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if(isExtraTempInfoCollapsed){
          setExtraTempItemsVisibility(Constants.VISIBLE_VIEW);
        }
        else{
          setExtraTempItemsVisibility(Constants.GONE_VIEW_VISIBILITY);
        }
        isExtraTempInfoCollapsed = !isExtraTempInfoCollapsed;//change value
      }
    });
  }
  //set the visibility gone or visible
  private void setExtraTempItemsVisibility(int constantInteger){
    if(isValidValueForVisibility(constantInteger)) {
      maxTempTextView.setVisibility(constantInteger);
      minTempTextView.setVisibility(constantInteger);
      dayTempTextView.setVisibility(constantInteger);
      dayFeelingTempTextView.setVisibility(constantInteger);
      nightTempTextView.setVisibility(constantInteger);
      nightFeelingTempTextView.setVisibility(constantInteger);
      morningTempTextView.setVisibility(constantInteger);
      morningFeelingTempTextView.setVisibility(constantInteger);
      eveningTempTextView.setVisibility(constantInteger);
      eveningFeelingTempTextView.setVisibility(constantInteger);
    }
  }
  //check of visibility value validity
  private boolean isValidValueForVisibility(int testedValue){
    return testedValue == 0 || testedValue == 4 || testedValue == 8;
  }
}