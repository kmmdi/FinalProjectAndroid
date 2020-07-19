package com.example.finalprojectandroid;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


public class Utils {

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
