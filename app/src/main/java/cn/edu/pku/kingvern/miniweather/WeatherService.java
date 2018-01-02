package cn.edu.pku.kingvern.miniweather;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import static cn.edu.pku.kingvern.miniweather.MainActivity.cityCode;

/**
 * Created by kingvern on 18/1/2.
 */

public class WeatherService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("WeatherService", "onBind: WeatherService->onBind");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("WeatherService", "onBind: WeatherService->onCreate");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("WeatherService", "onBind: WeatherService->onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("WeatherService", "onBind: WeatherService->onDestroy");

    }
}
