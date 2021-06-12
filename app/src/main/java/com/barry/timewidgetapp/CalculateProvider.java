package com.barry.timewidgetapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

public class CalculateProvider extends AppWidgetProvider {

    private static final String TAG = CalculateProvider.class.getSimpleName();
    private static final String PLUS_CLICK_ACTION = "com.barry.widgetapp.plus.CLICK";
    private static final String MINUS_CLICK_ACTION = "com.barry.widgetapp.minus.CLICK";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(PLUS_CLICK_ACTION)) {
            Toast.makeText(context, "PLUS", Toast.LENGTH_SHORT).show();
            Intent plusIntent = new Intent();
            plusIntent.setClass(context, CalculateService.CalculateReceiver.class);
            plusIntent.setAction(PLUS_CLICK_ACTION);
            context.sendBroadcast(plusIntent);
        } else if (intent.getAction().equals(MINUS_CLICK_ACTION)) {
            Toast.makeText(context, "MINUS", Toast.LENGTH_SHORT).show();
            Intent minusIntent = new Intent();
            minusIntent.setClass(context, CalculateService.CalculateReceiver.class);
            minusIntent.setAction(MINUS_CLICK_ACTION);
            context.sendBroadcast(minusIntent);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.d(TAG, "onUpdate...");
        //开启一个服务
        Intent intent = new Intent(context, CalculateService.class);
        context.startService(intent);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        //关闭服务
        Intent intent = new Intent(context, CalculateService.class);
        context.stopService(intent);
    }
}
