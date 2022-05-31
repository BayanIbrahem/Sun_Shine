package com.example.sunshine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sunshine.data.one_call_api.DailyDataEntry;
import com.example.sunshine.data.one_call_api.RawDataEntry;
import com.example.sunshine.enums.DataType;

public class DetailedWeatherDataActivity extends AppCompatActivity {
  //textViews:
  private TextView avgTempTextView;
  private TextView cloudsTextView;
  private TextView dateTextView;
  private TextView dayFeelingTempTextView;
  private TextView dayTempTextView;
  private TextView descriptionTextView;
  private TextView eveningFeelingTempTextView;
  private TextView eveningTempTextView;
  private TextView humidityTextView;
  private TextView mainStateTextView;
  private TextView maxTempTextView;
  private TextView minTempTextView;
  private TextView morningFeelingTempTextView;
  private TextView morningTempTextView;
  private TextView nightFeelingTempTextView;
  private TextView nightTempTextView;
  private TextView pressureTextView;
  private TextView rainTextView;
  private TextView windDegreeTextView;
  private TextView windGustTextView;
  private TextView windLabelTextView;
  private TextView windSpeedTextView;
  //icons:
  private ImageView backgroundImage;
  //buttons:
  private ImageButton expandTempInfoButton;
  //flags:
  private boolean isExtraTempInfoCollapsed = true;
  //data:
  private RawDataEntry weatherDetailedData;
  //data_type:
  private DataType data_type;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detailed_weather_data);
    initTextViewInOnCreate();
    initOtherUiWidgets();
    getIntentMessageValue();
    setWidgetsResources();
    getIntentData();
    setExpandButtonClickAction();
  }
  //private methods:
  //initialisation:
  private void initTextViewInOnCreate(){
    avgTempTextView = findViewById(R.id.details_tv_avgTemp);
    cloudsTextView = findViewById(R.id.details_tv_clouds);
    dateTextView = findViewById(R.id.details_tv_date);
    dayFeelingTempTextView = findViewById(R.id.details_tv_feelingDayTemp);
    dayTempTextView = findViewById(R.id.details_tv_dayTemp);
    descriptionTextView = findViewById(R.id.details_tv_weatherDetailsInfo);
    eveningFeelingTempTextView = findViewById(R.id.details_tv_feelingEviningTemp);
    eveningTempTextView = findViewById(R.id.details_tv_eveningTemp);
    humidityTextView = findViewById(R.id.details_tv_humidity);
    mainStateTextView = findViewById(R.id.details_tv_mainState);
    maxTempTextView = findViewById(R.id.details_tv_maxTemp);
    minTempTextView = findViewById(R.id.details_tv_minTemp);
    morningFeelingTempTextView = findViewById(R.id.details_tv_feelingMorningTemp);
    morningTempTextView = findViewById(R.id.details_tv_morningTemp);
    nightFeelingTempTextView = findViewById(R.id.details_tv_feelingNightTemp);
    nightTempTextView = findViewById(R.id.details_tv_nightTemp);
    pressureTextView = findViewById(R.id.details_tv_pressure);
    rainTextView = findViewById(R.id.details_tv_rain);
    windDegreeTextView = findViewById(R.id.details_tv_windDegree);
    windGustTextView = findViewById(R.id.details_tv_windGust);
    windLabelTextView = findViewById(R.id.details_tv_wind);
    windSpeedTextView = findViewById(R.id.details_tv_windSpeed);
  }
  private void initOtherUiWidgets(){
    backgroundImage = findViewById(R.id.details_image_backgroundImage);
    expandTempInfoButton = findViewById(R.id.details_button_expandTemp);
  }
  private void getIntentMessageValue(){
    Intent intent = getIntent();
    weatherDetailedData = (RawDataEntry) intent.getSerializableExtra(Constants.WEATHER_DATA_INTENT_KEY);
    data_type = (DataType)intent.getSerializableExtra(Constants.WEATHER_TYPE_INTENT_KEY);
  }
  private void setWidgetsResources(){
    setTempWidgetsResources();
    setOtherWidgetsResources();
  }
  private void setTempWidgetsResources(){
    if(data_type == DataType.HOURLY){
      actionWithHourlyType();
    }
    else if(data_type == DataType.CURRENT){
      actionWithCurrentType();
    }
    else if(data_type == DataType.DAILY){
      actionWithDailyType();
    }

  }
  private void actionWithHourlyType(){

    avgTempTextView.append(String.valueOf(weatherDetailedData.getTemp()));
    dayFeelingTempTextView.setVisibility(View.GONE);
    dayTempTextView.setVisibility(View.GONE);
    eveningFeelingTempTextView.setVisibility(View.GONE);
    eveningTempTextView.setVisibility(View.GONE);
    maxTempTextView.setVisibility(View.GONE);
    minTempTextView.setVisibility(View.GONE);
    morningFeelingTempTextView.setVisibility(View.GONE);
    morningTempTextView.setVisibility(View.GONE);
    nightFeelingTempTextView.setVisibility(View.GONE);
    nightTempTextView.setVisibility(View.GONE);
  }
  private void actionWithCurrentType(){
    //TODO: add sun set and sun rise.
    //they any approximately the same
    actionWithHourlyType();
  }
  private void actionWithDailyType(){
    DailyDataEntry dailyTypeFormattedData= (DailyDataEntry) weatherDetailedData;
    //abbreviation
    final DailyDataEntry d = dailyTypeFormattedData;
    dayFeelingTempTextView.append    (String.valueOf(d.getFeelingTemp() +
                                      String.valueOf(d.getTempUnitChar())));

    dayTempTextView.append           (String.valueOf(d.getDay_temp() +
                                      String.valueOf(d.getTempUnitChar())));

    eveningFeelingTempTextView.append(String.valueOf(d.getEvening_feeling_temp() +
                                      String.valueOf(d.getTempUnitChar())));

    eveningTempTextView.append       (String.valueOf(d.getEvening_temp() +
                                      String.valueOf(d.getTempUnitChar())));

    maxTempTextView.append           (String.valueOf(d.getMax_temp() +
                                      String.valueOf(d.getTempUnitChar())));

    minTempTextView.append           (String.valueOf(d.getMin_temp() +
                                      String.valueOf(d.getTempUnitChar())));

    morningFeelingTempTextView.append(String.valueOf(d.getMorning_feeling_temp() +
                                      String.valueOf(d.getTempUnitChar())));

    morningTempTextView.append       (String.valueOf(d.getMorning_temp() +
                                      String.valueOf(d.getTempUnitChar())));

    nightFeelingTempTextView.append  (String.valueOf(d.getNight_feeling_temp() +
                                      String.valueOf(d.getTempUnitChar())));

    nightTempTextView.append         (String.valueOf(d.getNight_temp() +
                                      String.valueOf(d.getTempUnitChar())));
  }
  private void setOtherWidgetsResources(){
    //abbreviation
    final RawDataEntry w = weatherDetailedData;
    //main resources:
    dateTextView.append(String.valueOf(w.getDate()));
    descriptionTextView.append(String.valueOf(w.getDescription()));
    mainStateTextView.append(String.valueOf(w.getState()));

    //details resources:
    cloudsTextView.append(String.valueOf(w.getCloud()));
    humidityTextView.append(String.valueOf(w.getHumidity()));
    pressureTextView.append(String.valueOf(w.getPressure()));
    rainTextView.append(String.valueOf(w.getRain()));
    windSpeedTextView.append(String.valueOf(w.getWind_speed()) + "km/h");
    windDegreeTextView.append(String.valueOf(w.getWind_degree()));
    windGustTextView.append(String.valueOf(w.getWind_gust()));
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