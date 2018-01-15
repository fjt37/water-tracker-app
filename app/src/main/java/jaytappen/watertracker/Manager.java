package jaytappen.watertracker;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Class to manage the shared preference values
 * as well as provide some managerial functions,
 * such as updating the widget, etc.
 *
 * Created by jay tappen on 1/3/2018.
 */

class Manager {

    static int DEFAULT_GOAL = 4;

    private Context context;
    private SharedPreferences values;
    private SharedPreferences.Editor valuesEditor;

    Manager(Context c) {
        context = c;
        values = context.getSharedPreferences(context.getString(R.string.prefs), Context.MODE_PRIVATE);
        valuesEditor = values.edit();
        if (!values.contains(context.getString(R.string.count))) {
            resetCount();
        }
    }

    int getCount() {
        return values.getInt(context.getString(R.string.count), -1);
    }

    void resetCount() {
        valuesEditor.putInt(context.getString(R.string.count), 0);
        commit();
    }

    void incCount() {
        valuesEditor.putInt(context.getString(R.string.count), getCount() + 1);
        commit();
    }

    int getGoal() {
        return values.getInt(context.getString(R.string.goal), DEFAULT_GOAL);
    }

    void setGoal(int newGoal) {
        valuesEditor.putInt(context.getString(R.string.goal), newGoal);
        commit();
    }

    int getRemaining() {
        return getGoal() - getCount();
    }

    boolean goalMet() {
        return getCount() >= getGoal();
    }

    boolean commit() {
        boolean committed = valuesEditor.commit();
        if (committed)
            updateWidget();
        return committed;
    }

    private void updateWidget() {
        Intent intent = new Intent(context, SmallAppWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] smallIds = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, SmallAppWidget.class));
        int[] medIds = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, MedAppWidget.class));
        int[] ids = new int[smallIds.length + medIds.length];
        System.arraycopy(smallIds, 0, ids, 0, smallIds.length);
        System.arraycopy(medIds, 0, ids, smallIds.length, medIds.length);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        context.sendBroadcast(intent);
    }

}
