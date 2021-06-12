package com.barry.timewidgetapp;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;

public class CalculateService extends Service {

    private static final String PLUS_CLICK_ACTION = "com.barry.widgetapp.plus.CLICK";
    private static final String MINUS_CLICK_ACTION = "com.barry.widgetapp.minus.CLICK";
    static int count = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        int[] idLs = manager.getAppWidgetIds(new ComponentName(getPackageName(), CalculateProvider.class.getName()));
        //用于遍历所有保存的widget的id
        for (int i : idLs) {
            int appID = i;
            //创建一个远程view，绑定我们要操控的widget布局文件
            RemoteViews remoteView = new RemoteViews(getPackageName(), R.layout.layout_widget);
            Intent intentPlus = new Intent();
            Intent intentMinus = new Intent();
            intentPlus.setClass(this,CalculateProvider.class);
            intentPlus.setAction(PLUS_CLICK_ACTION);
            intentMinus.setClass(this,CalculateProvider.class);
            intentMinus.setAction(MINUS_CLICK_ACTION);
            //为布局文件中的按钮设置点击监听
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intentPlus,PendingIntent.FLAG_UPDATE_CURRENT);
            remoteView.setOnClickPendingIntent(R.id.btn_plus, pendingIntent);
            pendingIntent = PendingIntent.getBroadcast(this,0,intentMinus,PendingIntent.FLAG_UPDATE_CURRENT);
            remoteView.setOnClickPendingIntent(R.id.btn_minus,pendingIntent);
            // 更新 widget
            manager.updateAppWidget(appID, remoteView);
        }
    }

    /**
     * 此广播接收器用于接收点击“+”或“-”按钮时发出的广播（CalculateProvider中发出）
     */
    public static class CalculateReceiver extends BroadcastReceiver{

        private static final String TAG = CalculateReceiver.class.getSimpleName();

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(PLUS_CLICK_ACTION)) {
                count++;
                RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.layout_widget);
                remoteView.setTextViewText(R.id.tv, String.valueOf(count));
                AppWidgetManager manager = AppWidgetManager.getInstance(context);
                manager.updateAppWidget(new ComponentName(context,CalculateProvider.class),remoteView);
            }else if(intent.getAction().equals(MINUS_CLICK_ACTION)){
                count--;
                RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.layout_widget);
                remoteView.setTextViewText(R.id.tv, String.valueOf(count));
                AppWidgetManager manager = AppWidgetManager.getInstance(context);
                manager.updateAppWidget(new ComponentName(context,CalculateProvider.class),remoteView);
            }
        }
    }
}
