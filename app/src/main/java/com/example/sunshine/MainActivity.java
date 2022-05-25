package com.example.sunshine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sunshine.data.one_call_api.WeatherDataInfo;
import com.example.sunshine.enums.*;
import com.example.sunshine.enums.states.RefreshState;
import com.example.sunshine.helper_classes.NeededValues;
import com.example.sunshine.helper_classes.WeatherViewModel;
import com.example.sunshine.networking.*;
import com.example.sunshine.threading.ThreadHandling;
import com.example.sunshine.widgets.recycler_view.RecyclerViewAdapter;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
  //recyclerView variables
  private RecyclerView recyclerView;
  private RecyclerViewAdapter recyclerViewAdapter;
  private TextView dataRetrieveStateTextView;
  private AutomatedToast automatedToast;

  //refreshing variables
  private Boolean refreshInProgress = false;
  private int refreshState = 0; //0: no refresh required, 1: successes, -1: fail

  //helper_classes variables
  NeededValues neededDataTypes;

  //threading variables
  private ThreadHandling threadingHandler;

  //weatherDataVariables
  private WeatherDataInfo weatherData;
  private WeatherViewModel weatherViewModel;

  //intents and content provider:
  Intent activitySwapIntent;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initVariablesInOnCreate();

    setViewModelObserver();

    setRecyclerViewOptions();
    completeRecyclerViewOptions();

    organizeDataGet();

    initIntentWithBundle();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu){
    getMenuInflater().inflate(R.menu.home_appbar, menu);
    return true;
  }
  @Override
  public boolean onOptionsItemSelected(MenuItem item){
    switch (item.getItemId()){
      case R.id.appBarMenu_refresh:
        organizeDataGet();
        break;
      case R.id.appBarMenu_Logcat:
        break;
    }
    return true;
  }

  //private methods <story>:
  private void initVariablesInOnCreate(){
    //data:
    weatherData = new WeatherDataInfo();
    //recyclerView
    recyclerView = findViewById(R.id.home_recyclerView);
    //threading:
    threadingHandler = new ThreadHandling();
    dataRetrieveStateTextView = findViewById(R.id.home_tv_state);
    //toast:
    automatedToast = new AutomatedToast(this);
    //viewModel and liveData:
    weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
  }

  private Observer<WeatherDataInfo> getWeatherViewModelObserverInstance(){
    return new Observer<WeatherDataInfo>() {
      @Override
      public void onChanged(WeatherDataInfo weatherDataInfo) {
        if(true){
          recyclerViewAdapter.notifyDataSetChanged();
        }
      }
    };
  }
  private void setViewModelObserver(){
    final Observer<WeatherDataInfo> viewModelObserver = getWeatherViewModelObserverInstance();
    weatherViewModel.getWeatherDataDetails().observe(this, viewModelObserver);
  }

  private void setRecyclerViewOptions(){
    recyclerView.setHasFixedSize(true);
    //TODO: make a subclass of recycler view and over ride adapter method by one that has edited onChange listener
    //https://stackoverflow.com/questions/28217436/how-to-show-an-empty-view-with-a-recyclerview
  }
  private void completeRecyclerViewOptions(){
    LinearLayoutManager layout = new LinearLayoutManager(this);
    WeatherDataInfo dataEntry = weatherViewModel.getWeatherDataDetails().getValue();
    recyclerViewAdapter = new RecyclerViewAdapter(dataEntry.getHourlyData(), new ClickListener());
    this.recyclerView.setAdapter(recyclerViewAdapter);
    this.recyclerView.setLayoutManager(layout);
    refreshInProgress = false;
    refreshState(RefreshState.FINISHED);
    refreshState = 0;
  }

  private void refreshState(RefreshState state){
    //TODO: make abstract class RefreshState
    switch (state) {
      case IN_PROGRESS:
        this.dataRetrieveStateTextView.setVisibility(View.VISIBLE);
        this.recyclerView.setVisibility(View.GONE);
        this.dataRetrieveStateTextView.setText("refreshing");
        break;
      case FAILED:
        this.dataRetrieveStateTextView.setVisibility(View.VISIBLE);
        this.recyclerView.setVisibility(View.GONE);
        this.dataRetrieveStateTextView.setText("error while refreshing");
        break;
      case STOPPED:
        this.dataRetrieveStateTextView.setVisibility(View.GONE);
        this.recyclerView.setVisibility(View.VISIBLE);
        break;
      case FINISHED:
        this.dataRetrieveStateTextView.setVisibility(View.GONE);
        this.recyclerView.setVisibility(View.VISIBLE);
        this.dataRetrieveStateTextView.setText("finished");
        break;
    }
  }
  private URL getURLForDataCall(OneCallURLGenerator urlGenerator){
    urlGenerator.setMode(Values.Mode.JSON);
    urlGenerator.setLang(Values.Lang.EN);
    urlGenerator.setUnit(Values.Unit.CELSIUS);
    neededDataTypes = urlGenerator.setType(Values.Type.HOUR, Values.Type.MIN);
    Log.d("neddedDataType:", neededDataTypes.toString());
    urlGenerator.setOffset(10800);
    try {
      URL url = urlGenerator.getURL();
      return url;
      /*new URL("https://api.openweathermap.org/data/2.5/weather?q=London&lat=35&lon=139&appid=ce58ff3b1ee960a325c8aa1544daca90");
       */
      //new URL("https://api.openweathermap.org/data/2.5/onecall?lat=0&lon=0&timezone-offset=10800&/*exclude=current,minutely*/&appid=ce58ff3b1ee960a325c8aa1544daca90");
    } catch (MalformedURLException e) {
      return null;
    }
  }
  private Runnable getRequestThreadRunnable(OneCallResponseGetter byteResponse){
    return new Runnable() {
      @Override
      public void run() {
        weatherData = byteResponse.getFormattedData(byteResponse.getInputStream());
        if(weatherData != null){
          refreshState = 1;
        }
      }
    };
  }
  private Runnable getRequestResultRunnable(){
    return new Runnable() {
      @Override
      public void run() {
        weatherViewModel.setWeatherData(weatherData);
        //refreshRecyclerViewData();
        completeRecyclerViewOptions();
      }
    };
  }
  private void getWeatherDataFromSite(OneCallResponseGetter byteResponse){
    refreshState = -1;
    byteResponse.setNeededData(neededDataTypes);
    Runnable task = getRequestThreadRunnable(byteResponse);
    Runnable uiTask = getRequestResultRunnable();
    threadingHandler.pushTaskAndResult(task, uiTask, 0, 0);
  }
  private void organizeDataGet(){
    if(refreshInProgress)
      return;
    refreshState(RefreshState.IN_PROGRESS);
    refreshInProgress = true;
    OneCallURLGenerator urlGenerator = new OneCallURLGenerator(0, 0);
    OneCallResponseGetter byteResponse = new OneCallResponseGetter(getURLForDataCall(urlGenerator));
    getWeatherDataFromSite(byteResponse);
  }

  private void refreshRecyclerViewData(){
    weatherViewModel.setWeatherData(weatherData);
  }


  private void initIntentWithBundle(){
    //intent:
    activitySwapIntent = new Intent(this, DetailedWeatherDataActivity.class);
    //TODO: set intent message
  }

  //private classes:
  private class AutomatedToast {
    private Toast toast;
    private boolean isKillableByNext;
    private Context context;
    public AutomatedToast(Context context){
      isKillableByNext = true;
      this.context = context;
    }
    private void showToast (String message, boolean isLong, boolean killPrevious, boolean isKillableByNext){
      if(this.toast != null && killPrevious && this.isKillableByNext){
        this.toast.cancel();
      }
      if(isLong) {
        this.toast = Toast.makeText(context,  message, android.widget.Toast.LENGTH_LONG);
      }
      else {
        this.toast = Toast.makeText(context,  message, android.widget.Toast.LENGTH_SHORT);
      }
      this.toast.show();
      this.isKillableByNext = isKillableByNext;
    }
    public void addToast(String message, boolean isLong){
      boolean killPrevious = false;
      boolean isKillableByNext = true;
      showToast(message, isLong, killPrevious, isKillableByNext);
    }
    public void addPinnedToast(String message, boolean isLong){
      boolean killPrevious = false;
      boolean isKillableByNext = false;
      showToast(message, isLong, killPrevious, isKillableByNext);
    }
    public void resetToast(String message, boolean isLong){
      boolean killPrevious = true;
      boolean isKillableByNext = true;
      showToast(message, isLong, killPrevious, isKillableByNext);
    }
    public void resetPinnedToast(String message, boolean isLong){
      boolean killPrevious = true;
      boolean isKillableByNext = false;
      showToast(message, isLong, killPrevious, isKillableByNext);
    }
  }
  //private interfaces:
  private class ClickListener implements RecyclerViewAdapter.ItemClickListener {
    @Override
    public void ItemClick(int position) {
      startActivity(activitySwapIntent);
    }
  }
}
