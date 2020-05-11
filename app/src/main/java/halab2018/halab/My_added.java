package halab2018.halab;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Altayeb on 10/10/2018.
 */
public class My_added extends Fragment {


    SwipeRefreshLayout swipeRefreshLayout;

    ListView listView;
    public ArrayAdapter arrayAdapter;
    public static ArrayList<String> my_post_id, my_post_title;
    ProgressDialog progressDialog;
    View view;


    public My_added() {
    }
    //////////////////////////////////


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.all,container,false);


        my_post_id = new ArrayList<String>();
        my_post_title = new ArrayList<String>();




        //P_Dialog
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getResources().getString(R.string.progressDialog_txt));
        progressDialog.setCancelable(false);


        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));

        listView = (ListView) view.findViewById(R.id.all_listView_id);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getContext(), New_post.class);
                Bundle bundle = new Bundle();
                bundle.putString("my_post_id",my_post_id.get(position));
                bundle.putString("activity","my_added");
                i.putExtras(bundle);
                startActivity(i);
            }
        });



      swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                my_post_id.clear();
                my_post_title.clear();
                getMyPost();
            }
        });



            getMyPost();


        return view;
    }





   //Array Request
    private void getMyPost() {
        if (URL.isNetworkConnected(getContext())) {
            if (!URL.isInternetAvailable()) {
                showDialog();
                swipeRefreshLayout.setRefreshing(true);

                final HashMap<String, String> params = new HashMap<String, String>();
                params.put("user_id",SharePref.Get_userId(getContext()));
                params.put("section", Car_tab_layout.section);
                final JSONObject jsonObject = new JSONObject(params);

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, URL.MY_ADDED_POSTS, jsonObject, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = (JSONObject) response.get(i);
                                String message = object.getString("message");
                                String id = object.getString("id");
                                String title = object.getString("title");

                                if (message.equals("yes")) {
                                    my_post_id.add(i, id.toString().trim());
                                    my_post_title.add(i, title.toString().trim());


                                } else {
                                    hideDialog();
                                    Toast.makeText(getContext(), getResources().getString(R.string.sale_empty_dept), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                hideDialog();
                                Toast.makeText(getContext(), getResources().getString(R.string.sale_empty_dept), Toast.LENGTH_SHORT).show();
                                swipeRefreshLayout.setRefreshing(false);
                                e.printStackTrace();
                            }
                        }
                        if (my_post_id.isEmpty()) {
                            hideDialog();
                            swipeRefreshLayout.setRefreshing(false);
                        } else {
                            hideDialog();
                            arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, my_post_title);
                            listView.setAdapter(arrayAdapter);
                            hideDialog();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideDialog();
                        Toast.makeText(getActivity(), getString(R.string.login_error_server_not_found), Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
                MySingleton.getmInstance(getContext()).addToRequestque(jsonArrayRequest);
            } else {
                hideDialog();
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), getString(R.string.login_error_network_error), Toast.LENGTH_SHORT).show();

            }
        } else {
            hideDialog();
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getActivity(), getString(R.string.login_error_no_internet_access), Toast.LENGTH_SHORT).show();

        }
    }


    //Operate Dialog
    private void showDialog() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    private void hideDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


}
