package com.example.sunshine.threading;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class ThreadHandling {

  private Thread thread;
  private Handler threadHandler;
  private Handler mainHandler;
  public ThreadHandling(){
    Log.println(Log.DEBUG, "THREADING", "Start of Handler thread constructor");
    Runnable initNewThread = new Runnable() {
      @Override
      public void run() {
        Log.println(Log.DEBUG, "THREADING", "start of initNewThreadRunnable");
        Looper.prepare();
        Log.println(Log.DEBUG, "THREADING", "preparing looping in initNewThreadRunnable");
        threadHandler = new Handler(Looper.myLooper());
        Log.println(Log.DEBUG, "THREADING", "init threadHandler");
        mainHandler = new Handler(Looper.getMainLooper());
        Log.println(Log.DEBUG, "THREADING", "initMainHandler");
        Looper.loop();
        Log.println(Log.DEBUG, "THREADING", "start looping in initNewThreadRunnable");
      }
    };
    thread = new Thread(initNewThread);
    Log.println(Log.DEBUG, "THREADING", "finishing initializing thread");
    thread.setDaemon(true);
    Log.println(Log.DEBUG, "THREADING", "set the thread as demon");
    try {
      thread.join();
      Log.println(Log.DEBUG, "THREADING", "joining thread");
    } catch (InterruptedException e) {
      e.printStackTrace();
      Log.println(Log.DEBUG, "FailToCreateThread:", "can not join the thread");
    }
    thread.start();
    Log.println(Log.DEBUG, "THREADING", "starting thread");
    while(threadHandler == null){
      Log.println(Log.DEBUG, "THREADING", "in null loop checker");
      try {
        Thread.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
        Log.println(Log.DEBUG, "FailToCreateThread:", "can not sleep to create handler");
      }
    }
    Log.println(Log.DEBUG, "THREADING", "finishing null loop checker and finishing the constructor");
  }
  public void pushTask(Runnable task, int delay){
    if(delay<0){
      delay = 0;
      Log.println(Log.DEBUG, "warningInTask", "delay in negative number");

    }
    threadHandler.postDelayed(task, delay);
  }
  public void pushTaskAndResult(Runnable task, Runnable result, int task_delay, int result_delay){
    if (task_delay < 0) {
      task_delay = 0;
      Log.println(Log.DEBUG, "warningInTask", "delay in negative number");
    }
    if (result_delay < 0){
      result_delay = 0;
      Log.println(Log.DEBUG, "warningInTask", "delay in negative number");
    }
    final int finalResult_delay = result_delay;
    Runnable run = new Runnable() {
      @Override
      public void run() {
        task.run();
        mainHandler.postDelayed(result, finalResult_delay);
      }
    };
    threadHandler.postDelayed(run, task_delay);
  }
}
