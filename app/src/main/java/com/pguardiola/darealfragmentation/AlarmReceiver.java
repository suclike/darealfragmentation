/*
 * Copyright (C) 2016 Pablo Guardiola Sánchez.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pguardiola.darealfragmentation;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;
import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {

  @TargetApi(Build.VERSION_CODES.M)
  public static void startInexactThreeMinIntervalIdle(Context context) {
    AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    Intent alarmIntentThreeIdle = new Intent(context, AlarmReceiver.class);
    alarmIntentThreeIdle.setAction(Constants.THREE + Constants.IDLE);
    PendingIntent pendingIntentThreeIdle =
        PendingIntent.getBroadcast(context, 33, alarmIntentThreeIdle,
            PendingIntent.FLAG_CANCEL_CURRENT);

    //int interval = 8000;
    //manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
    // interval, pendingIntent);
    //manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
    //        60000,
    //        3 * 60000, pendingIntentThreeIdle);
    long elapsedRealTime = SystemClock.elapsedRealtime();
    manager.setAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, elapsedRealTime + 3 * 60000,
        pendingIntentThreeIdle);
    Log.d(Constants.TAG, Constants.ALARM_SET_AT + String.format(Constants.DATE_AND_TIME_FORMATTING,
        Calendar.getInstance()));
  }

  @TargetApi(Build.VERSION_CODES.M)
  public static void startInexactThirtyMinIntervalIdle(Context context) {
    AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    Intent alarmIntentThirtyIdle = new Intent(context, AlarmReceiver.class);
    alarmIntentThirtyIdle.setAction(Constants.THIRTY + Constants.IDLE);
    PendingIntent pendingIntentThirtyIdle =
        PendingIntent.getBroadcast(context, 303, alarmIntentThirtyIdle,
            PendingIntent.FLAG_CANCEL_CURRENT);

    //int interval = 8000;
    //manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
    // interval, pendingIntent);
    //manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
    //        60000,
    //        30 * 60000, pendingIntentThirtyIdle);
    long elapsedRealTime = SystemClock.elapsedRealtime();
    manager.setAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, elapsedRealTime + 30 * 60000,
        pendingIntentThirtyIdle);
    Log.d(Constants.TAG, Constants.ALARM_SET_AT + String.format(Constants.DATE_AND_TIME_FORMATTING,
        Calendar.getInstance()));
  }

  @Override public void onReceive(Context context, Intent intent) {
    if (Constants.THREE.equals(intent.getAction())) {
      Log.d(Constants.TAG,
          Constants.THREE + Constants.SEPARATOR + Constants.ALARM_RECEIVED_AT + String.format(
              Constants.DATE_AND_TIME_FORMATTING, Calendar.getInstance()));
      FileSaver.saveStringToFile(
          Constants.ALARM_RECEIVED_AT + String.format(Constants.DATE_AND_TIME_FORMATTING,
              Calendar.getInstance()) + Constants.NEW_LINE,
          context.getExternalFilesDir(null).toString() + Constants.INEXACT_THREE_MIN_LOG_FILE);
    } else if (Constants.THIRTY.equals(intent.getAction())) {
      Log.d(Constants.TAG,
          Constants.THIRTY + Constants.SEPARATOR + Constants.ALARM_RECEIVED_AT + String.format(
              Constants.DATE_AND_TIME_FORMATTING, Calendar.getInstance()));
      FileSaver.saveStringToFile(
          Constants.ALARM_RECEIVED_AT + String.format(Constants.DATE_AND_TIME_FORMATTING,
              Calendar.getInstance()) + Constants.NEW_LINE,
          context.getExternalFilesDir(null).toString() + Constants.INEXACT_THIRTY_MIN_LOG_FILE);
    } else if ((Constants.THREE + Constants.IDLE).equals(intent.getAction())) {
      Log.d(Constants.TAG, Constants.THREE
          + Constants.SEPARATOR
          + Constants.IDLE
          + Constants.ALARM_RECEIVED_AT
          + String.format(Constants.DATE_AND_TIME_FORMATTING, Calendar.getInstance()));
      FileSaver.saveStringToFile(
          Constants.ALARM_RECEIVED_AT + String.format(Constants.DATE_AND_TIME_FORMATTING,
              Calendar.getInstance()) + Constants.NEW_LINE,
          context.getExternalFilesDir(null).toString() + Constants.INEXACT_THREE_MIN_IDLE_LOG_FILE);
      startInexactThreeMinIntervalIdle(context);
    } else if ((Constants.THIRTY + Constants.IDLE).equals(intent.getAction())) {
      Log.d(Constants.TAG, Constants.THIRTY
          + Constants.SEPARATOR
          + Constants.IDLE
          + Constants.ALARM_RECEIVED_AT
          + String.format(Constants.DATE_AND_TIME_FORMATTING, Calendar.getInstance()));
      FileSaver.saveStringToFile(
          Constants.ALARM_RECEIVED_AT + String.format(Constants.DATE_AND_TIME_FORMATTING,
              Calendar.getInstance()) + Constants.NEW_LINE,
          context.getExternalFilesDir(null).toString()
              + Constants.INEXACT_THIRTY_MIN_IDLE_LOG_FILE);
      startInexactThirtyMinIntervalIdle(context);
    }
  }
}