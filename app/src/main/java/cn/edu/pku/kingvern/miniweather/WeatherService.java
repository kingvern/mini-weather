package cn.edu.pku.kingvern.miniweather;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;

import static cn.edu.pku.kingvern.miniweather.MainActivity.cityCode;

/**
 * Created by kingvern on 18/1/2.
 */

public class WeatherService extends Service {

    private static final int ALARM_DURATION  = 5 * 60 * 1000; // service 自启间隔
    private static final int UPDATE_DURATION = 10 * 1000;     // Widget 更新间隔
    private static final int UPDATE_MESSAGE  = 1000;

    private UpdateHandler updateHandler; // 更新 Widget 的 Handler


    @Override
    public IBinder onBind(Intent intent) {
        Log.d("WeatherService", "onBind: WeatherService->onBind");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("WeatherService", "onBind: WeatherService->onCreate");
        Message message = updateHandler.obtainMessage();
        message.what = UPDATE_MESSAGE;
        updateHandler = new UpdateHandler();
        updateHandler.sendMessageDelayed(message, UPDATE_DURATION);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(getBaseContext(), WeatherService.class);
        PendingIntent pendingIntent = PendingIntent.getService(getBaseContext(), 0,
                alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + ALARM_DURATION, pendingIntent);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("WeatherService", "onBind: WeatherService->onDestroy");

    }

    private void updateWidget() {
        // 更新 Widget
        RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.weather_widget);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        appWidgetManager.updateAppWidget(new ComponentName(this, WeatherWidget.class), remoteViews);

        // 发送下次更新的消息
        Message message = updateHandler.obtainMessage();
        message.what = UPDATE_MESSAGE;
        updateHandler.sendMessageDelayed(message, UPDATE_DURATION);
    }

    protected final class UpdateHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_MESSAGE:
                    updateWidget();
                    break;
                default:
                    break;
            }
        }
    }
}
