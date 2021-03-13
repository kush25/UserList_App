package com.hcl.userlist_jsonexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


    private final OkHttpClient client = new OkHttpClient();
    private RecyclerView mRecyclerView;
    private UserAdapter userAdapter;
    private ArrayList<User> mUserList;
    private RequestQueue mRequestQueue;
    private TextView rslt;
    private User[] users;

    int[] users_img_url  =
            {
                    R.drawable.user1,
                    R.drawable.user2,
                    R.drawable.user3,
                    R.drawable.user4,
                    R.drawable.user5,
                    R.drawable.user6,
                    R.drawable.user7,
                    R.drawable.user8,
                    R.drawable.user9,
                    R.drawable.user10

            };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycle_view);
        rslt = findViewById(R.id.rslt);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mUserList = new ArrayList<>();



        parseJSON();
    }

    public void parseJSON(){

        Request request = new Request.Builder().url("http://jsonplaceholder.typicode.com/users")
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rslt.setText("Failed due to some reasons");
                    }
                });

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                if (response.isSuccessful()) {

                    final String myResponse = response.body().string();



                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Gson gson = new Gson();
                            User[] users = gson.fromJson(myResponse, User[].class);
                            for(User u : users) {
                                mUserList.add(u);
                                Log.i("USER", u.toString());


                            }

                            userAdapter = new UserAdapter(MainActivity.this,mUserList,users_img_url);
                            mRecyclerView.setAdapter(userAdapter);
                        }
                    });
                }
            }
        });



//        String url = "https://api.randomuser.me/?results=5";
//
//        JsonObjectRequest request =  new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            JSONObject jsonObject1 = new JSONObject(response.toString());
//                            JSONArray jsonArray = jsonObject1.getJSONArray("results");
//
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject hit = jsonArray.getJSONObject(i);
//
//                                String name = hit.getString("gender");
//                                String username = hit.getString("phone");
//                                String email = hit.getString("email");
//
//                                mUserList.add(new User(name, username, email));
//
//                            }
//
//                            userAdapter = new UserAdapter(MainActivity.this,mUserList);
//                            mRecyclerView.setAdapter(userAdapter);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                        error.printStackTrace();
//                    }
//                });



    }
}