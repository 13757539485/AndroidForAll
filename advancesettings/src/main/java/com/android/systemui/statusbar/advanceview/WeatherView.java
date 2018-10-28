package com.android.systemui.statusbar.advanceview;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

//import com.android.systemui.R;
import com.android.advancesettings.R;

public class WeatherView extends TextView {
    private static final Uri WEATHER_URI = Uri.parse("content://weather/weather");
    private final String NO_WEATHER = getResources().getString(R.string.no_weather_description);
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            String weatherText = (String) msg.obj;
            setText(TextUtils.isEmpty(weatherText) ? getResources().getString(R.string.no_weather_description) : weatherText);
            return false;
        }
    });
    private final ContentObserver mWeatherObserver = new WeatherContentObserver(mHandler);
    private Context mContext;
    private WeatherRunnable mWeatherRunnable;

    public WeatherView(Context context) {
        this(context, null);
    }

    public WeatherView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs , -1);
    }

    public WeatherView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mWeatherRunnable = new WeatherRunnable();
        context.getContentResolver().registerContentObserver(WEATHER_URI, true, mWeatherObserver);
        updateWeatherInfo();
    }

    private void updateWeatherInfo() {
        mHandler.removeCallbacks(mWeatherRunnable);
        mHandler.postDelayed(mWeatherRunnable,200);
    }

    private class WeatherRunnable implements Runnable {

        @Override
        public void run() {
            String build = "";
            try {
                Cursor query = mContext.getContentResolver().query(WEATHER_URI, null, null, null, null);
                if (query != null) {
                    if (query.moveToFirst()) {
                        String cityName = query.getString(query.getColumnIndexOrThrow("city_name"));
                        String description = query.getString(query.getColumnIndexOrThrow("description"));
                        String temperature = query.getString(query.getColumnIndexOrThrow("temperature"));
                        build = cityName + " " + description + " " + temperature;
                    }
                    query.close();
                }
            } catch (IllegalArgumentException e) {
                build = NO_WEATHER;
            }finally {
                Message message = mHandler.obtainMessage();
                message.what = 100;
                message.obj = build;
                mHandler.sendMessage(message);
            }
        }
    }

    private class WeatherContentObserver extends ContentObserver {

        public WeatherContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            updateWeatherInfo();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mWeatherObserver != null) {
            mContext.getContentResolver().unregisterContentObserver(mWeatherObserver);
        }
    }
}
