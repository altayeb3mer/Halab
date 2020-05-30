package halab2018.halab.Utils;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class RetroApi {


    public interface getPremium {
        @GET("halabScript/get_premium.php")
        Call<String> getStringScalar(@Query("section") String section, @HeaderMap HashMap<String, String> headerMap);
    }
    public interface getPosts {
        @GET("halabScript/get_posts.php")
        Call<String> getStringScalar(@Query("section") String section, @HeaderMap HashMap<String, String> headerMap);
    }
    public interface getMyAdded {
        @GET("halabScript/get_my_added_posts.php")
        Call<String> getStringScalar(@Query("section") String section,@Query("user_id") String userId, @HeaderMap HashMap<String, String> headerMap);
    }
    public interface getPostDetails {
        @FormUrlEncoded
        @POST("halabScript/get_post_details.php")
//        Call<String> getStringScalar(@Field("id") String id, @HeaderMap HashMap<String, String> headerMap);
        Call<String> getStringScalar(@Field("id") String id);
    }
    public interface login {
        @FormUrlEncoded
        @POST("halabScript/login.php")
        Call<String> getStringScalar(@Field("username") String username, @Field("password") String password, @HeaderMap HashMap<String, String> headerMap);
    }
    public interface registration {
        @FormUrlEncoded
        @POST("halabScript/registration.php")
        Call<String> getStringScalar(@Field("username") String username,@Field("email") String email, @Field("password") String password);
    }
    public interface contactUs {
        @FormUrlEncoded
        @POST("halabScript/send_msg.php")
        Call<String> getStringScalar(@Field("name") String username,@Field("phone") String phone,@Field("email") String email, @Field("message") String message);
    }


}
