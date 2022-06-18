package uz.ngs.lesson_2;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class MySharepreference {
    private static SharedPreferences sharedPreferences;
    public static SharedPreferences initSharedPreferences(Context context){
        if (sharedPreferences == null){
         sharedPreferences =context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
        }
        return sharedPreferences;
    }
   public static void putInt(String key,Integer value){
        SharedPreferences.Editor preferenseEditor = sharedPreferences.edit();
        preferenseEditor.putInt(key,value).apply();
    }
    public static Integer getInt(String key, Integer defauldValue){
        if (sharedPreferences.getInt(key,defauldValue) != 0) {
            return sharedPreferences.getInt(key, defauldValue);
        }else return Integer.MAX_VALUE;
    }
    public static void putString(String key,String value){
        SharedPreferences.Editor preferenseEditor = sharedPreferences.edit();
        preferenseEditor.putString(key,value).apply();
    }
    public static String getString(String key, String defauldValue){
        return sharedPreferences.getString(key,defauldValue);
    }

}
