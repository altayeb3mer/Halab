package halab2018.halab;

import android.content.Context;
import android.net.ConnectivityManager;

import java.net.InetAddress;

/**
 * Created by Altayeb on 9/25/2018.
 */
public class URL {

   // public static String ROOT_URL = "http://altayeb20182018.eu5.org/";
    public static String ROOT_URL = "http://halab-q.com/";
    //public static String ROOT_URL = "http://192.168.43.209/";
    // http://altayeb20182018.eu5.org
    //http://isn-technology.com/halab/code2/connection.php
    public static String NEW_POST_URL = ROOT_URL + "halabScript/add_post.php";
    public static String GET_POSTS_URL = ROOT_URL + "halabScript/get_posts.php";
    //public static String IMAGE_PATH = ROOT_URL + "halabScript/uploads/";
    public static String IMAGE_PATH = ROOT_URL + "images/uploads/";
    public static String GET_POST_DETAILS_URL = ROOT_URL + "halabScript/get_post_details.php";
    public static String REG_URL = ROOT_URL + "halabScript/registration.php";
    public static String LOGIN_URL = ROOT_URL + "halabScript/login.php";
    public static String MY_ADDED_POSTS = ROOT_URL + "halabScript/get_my_added_posts.php";
    public static String GET_MY_ADDED_POST_DETAILS = ROOT_URL + "halabScript/get_my_added_post_details.php";
    //
    public static String UPDATE_MY_POST = ROOT_URL + "halabScript/update_my_added_post.php";


    public static String  GET_PREMIUM_URL= ROOT_URL + "halabScript/get_premium.php";
    public static String  SEND_MESSAGE_URL= ROOT_URL + "halabScript/send_msg.php";
    public static String  DELETE_POST_URL= ROOT_URL + "halabScript/delete_row.php";

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public static boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName(ROOT_URL);//You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }

}
