package com.example.sunshine.networking;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sunshine.data.one_call_api.WeatherDataInfo;
import com.example.sunshine.helper_classes.NeededValues;
import com.example.sunshine.threading.ThreadHandling;

import java.net.MalformedURLException;
import java.net.URL;

public class NetworkingProcess {
  private URL httpURL;
  private WeatherDataInfo weatherData;
  private OneCallResponseGetter byteResponse;
  private ThreadHandling threadHandler;
  public NetworkingProcess(OneCallURLGenerator urlGenerator) throws MalformedURLException {
    this.httpURL = urlGenerator.getURL();
    byteResponse = new OneCallResponseGetter(httpURL);
    NeededValues neededValues = urlGenerator.getNeededDataTypesValues();
    byteResponse.setNeededData(neededValues);

    threadHandler = new ThreadHandling();
  }
  public void getReadyResult(WeatherDataInfo[] result, Runnable afterFinishingTask){
    Runnable task = new Runnable() {
      @Override
      public void run() {
        result[0] = byteResponse.getFormattedData(byteResponse.getInputStream());
        Log.println(
            Log.DEBUG,
            "NetWorkingProcess class: getReady result, runnable result",
            "set result value \n" + result[0].toString());
      }
    };
    threadHandler.pushTaskAndResult(task, afterFinishingTask, 0, 0);
  }
}
