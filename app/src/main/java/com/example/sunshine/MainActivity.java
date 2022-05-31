package com.example.sunshine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sunshine.data.one_call_api.RawDataEntry;
import com.example.sunshine.data.one_call_api.WeatherDataInfo;
import com.example.sunshine.enums.Values;
import com.example.sunshine.enums.states.RefreshState;
import com.example.sunshine.helper_classes.NeededValues;
import com.example.sunshine.networking.*;
import com.example.sunshine.widgets.recycler_view.RecyclerViewAdapter;
import com.example.sunshine.widgets.recycler_view.WeatherDataViewModel;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
  //recyclerView variables
  private RecyclerView recyclerView;
  private RecyclerViewAdapter recyclerViewAdapter;
  private TextView dataRetrieveStateTextView;
  private AutomatedToast automatedToast;

  //refreshing variables
  private Boolean refreshInProgress = false;
  private int refreshState = 0; //0: no refresh required, 1: successes, -1: fail

  private WeatherDataViewModel weatherDataViewModel;

  //intents and content provider:
  Intent activitySwapIntent;

  private OneCallURLGenerator urlGenerator;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Bundle state = new Bundle();

    initVariablesInOnCreate();

    weatherDataViewModel = new ViewModelProvider(this).get(WeatherDataViewModel.class);
    weatherDataViewModel.getLiveWeatherData().observe(this, liveWeatherData -> {
      updateUi();
    });
    setRecyclerViewOptions();
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

        break;
      case R.id.appBarMenu_Logcat:
        automatedToast.resetPinnedToast("View Model Data", true);
        break;
    }
    return true;
  }

  //private methods <story>:
  private void initVariablesInOnCreate(){
    //recyclerView
    recyclerView = findViewById(R.id.home_recyclerView);
    dataRetrieveStateTextView = findViewById(R.id.home_tv_state);
    //toast:
    automatedToast = new AutomatedToast(this);
    //url generator:
    urlGenerator = new OneCallURLGenerator(0, 0);
  }

  private void setRecyclerViewDependencies(){
    //TODO: make the next line cleaner
    WeatherDataInfo recyclerViewData= weatherDataViewModel.getLiveWeatherData().getValue();
    for (int i=0; weatherDataViewModel == null; i++) {
      try {
        Thread.sleep(1000);
        Log.println(Log.DEBUG, "one loop occurred...", " round #"+i);
        recyclerViewData= weatherDataViewModel.getLiveWeatherData().getValue();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    recyclerViewAdapter = new RecyclerViewAdapter(recyclerViewData.getHourlyData(), new ClickListener());
    recyclerView.setAdapter(recyclerViewAdapter);
    RecyclerView.LayoutManager recyclerViewLayoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(recyclerViewLayoutManager);
    automatedToast.addPinnedToast("RecyclerView initialized", true);
  }

  private void setRecyclerViewOptions(){
    recyclerView.setHasFixedSize(true);
    //TODO: make a subclass of recycler view and over ride adapter method by one that has edited onChange listener
    //https://stackoverflow.com/questions/28217436/how-to-show-an-empty-view-with-a-recyclerview
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
      /*new URL("https://api.openweathermap.org/data/2.5/weather?q=London&lat=35&lon=139&appid=ce58ff3b1ee960a325c8aa1544daca90");
       */
      //new URL("https://api.openweathermap.org/data/2.5/onecall?lat=0&lon=0&timezone-offset=10800&/*exclude=current,minutely*/&appid=ce58ff3b1ee960a325c8aa1544daca90");

  private void updateUi(){
    //TODO: set the content
    setRecyclerViewDependencies();
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
