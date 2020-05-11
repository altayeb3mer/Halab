package halab2018.halab;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Altayeb on 9/25/2018.
 */
public class SharePref extends AppCompatActivity{

    private static final String SHARED_PREF_NAME = "halab_share_pref";

    public static void SaveUsername(String username,String userId ,Context context) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("username", username);
        edit.putString("user_id", userId);
        edit.apply();

    }


    public static String Get_userId(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        return sp.getString("user_id", null);
    }

    public static void save_full_name(String full_name, Context context) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("full_name", full_name);
        edit.apply();

    }

    //
    public static boolean Is_Logged_in(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        boolean x = false;
        String name = sp.getString("username", null);
        if (name != null) {
            x = true;
        }
        return x;
    }

    public static String Get_username(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        return sp.getString("username", null);
    }

    public static void Logout(Context context) {
        final SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.clear();
        edit.apply();
    }


}
