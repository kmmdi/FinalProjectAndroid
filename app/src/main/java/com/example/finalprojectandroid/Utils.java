package com.example.finalprojectandroid;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

/**
 * This class provides some utilities for reuse across different classes
 * @author Kazi Muntaha Mahdi
 */
public class Utils {

    /**
     * This method helps decide which activity to navigate to
     * @param item corresponds to menu item selected
     * @param context context of the activity that calls this method
     * @param view view of the activity that calls this method
     * @return boolean
     */
    public static boolean onNavigationItemSelectedHelper(MenuItem item, Context context, View view) {
        Intent intent;
        switch(item.getItemId()) {
            case R.id.myHome:
                intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
                break;
            case R.id.myNews:
                intent = new Intent(context, NewsList.class);
                context.startActivity(intent);
                break;
            case R.id.myFav:
                intent = new Intent(context, Favorites.class);
                context.startActivity(intent);
                break;
            case R.id.mySettings:
                intent = new Intent(context, Settings.class);
                context.startActivity(intent);
                break;
        }
        DrawerLayout drawerLayout = view.findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    /**
     * Another utility function for navigating the app
     * @param item corresponds to menu item selected
     * @param context context of the activity that calls this method
     * @return boolean
     */
    public static boolean onOptionsItemSelectedHelpder(MenuItem item, Context context) {
        Intent intent;
        switch(item.getItemId()) {
            case R.id.myHome:
                intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
                break;
            case R.id.myNews:
                intent = new Intent(context, NewsList.class);
                context.startActivity(intent);
                break;
            case R.id.myFav:
                intent = new Intent(context, Favorites.class);
                context.startActivity(intent);
                break;
            case R.id.mySettings:
                intent = new Intent(context, Settings.class);
                context.startActivity(intent);
                break;
        }
        return true;
    }

}
