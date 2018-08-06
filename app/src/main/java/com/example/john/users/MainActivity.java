/**
 * @file   MainActivity.java
 * @brief  Main Activity.
 * @date   01.08.2018
 * @autor  M.Gusev
 */

package com.example.john.users;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;


/** @class MainActivity */
public class MainActivity extends AppCompatActivity {
    private ArrayList<UserItem> userList_= null;
    private ProgressBar progressbar_= null;
    private ListView userListView_ = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* Query string */
        String url = "https://api.randomuser.me/?inc=name,location,email,nat," +
                                                                       "phone,picture&results=150";

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressbar_ = findViewById(R.id.progressBar);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        new AsyncRequest().execute(url);
    }

    /** Updating the visual list */
    public void updateList() {
        progressbar_.setVisibility(View.GONE);

        userListView_ = findViewById(R.id.custom_list);
        userListView_.setVisibility(View.VISIBLE);
        userListView_.setAdapter(new CustomListAdapter(this, userList_));

        /* Event processing by user's choice. */
        userListView_.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object obj = userListView_.getItemAtPosition(position);
                UserItem userData = (UserItem) obj;

                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("user", userData);
                startActivity(intent);
            }
        });
    }

    /** @brief Parse the information received from the server.
    *   @param json [IN] - JSON object. */
    public void parseJson(JSONObject json) {
        try {
            JSONArray results = json.getJSONArray("results");
            userList_ = new ArrayList<UserItem>();

            for (int i = 0; i < results.length(); i++) {
                UserItem item = new UserItem();
                JSONObject result = (JSONObject) results.getJSONObject(i);

				/* Retrieving data from the 'name' field. */
                JSONObject name = result.getJSONObject("name");
                if (name != null) {
                    item.setNameFirst(name.getString("first"));
                    item.setNameLast(name.getString("last"));
                }

				/* Retrieving data from the 'location' field. */
                JSONObject location  = result.getJSONObject("location");
                if (location != null) {
                    item.setLocation(location.getString("street") + ", " +
                                     location.getString("city") + ", " +
                                     location.getString("state") + ", " +
                                     location.getString("postcode"));
                }

				/* Processing the 'email', 'phone', 'nat' fields */
                item.setEmail(result.getString("email"));
                item.setPhone(result.getString("phone"));
                item.setNation(result.getString("nat"));

				/* Retrieving data from the 'picture' field. */
                JSONObject picture  = result.getJSONObject("picture");
                if (picture != null) {
                    item.setPicLargeUrl(picture.getString("large"));
                    item.setPicThumbnailUrl(picture.getString("thumbnail"));
                }

                userList_.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /** Converting String to JSON. */
    @SuppressWarnings("resource")
    public JSONObject strinToJson(String answer) {
        JSONObject jObj = null;

        try {
            jObj = new JSONObject(answer);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return jObj;
    }

    /** Converting Stream to String. */
    private static String streamToString(BufferedReader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;

        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }

        return sb.toString();
    }

    /** @class AsyncRequest
     *  @brief Async Request */
    class AsyncRequest extends AsyncTask<String, Integer, String> {

//        @Override
        protected String doInBackground(String... arg) {
            InputStream is = null;
            String jsonText = null;

            try {
                is = new URL(arg[0]).openStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is,
                                                                        Charset.forName("UTF-8")));
                jsonText = streamToString(rd);
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonText;
        }

//        @Override
        protected void onPostExecute(String s) {
//            super.onPostExecute(s);

            if (s == null) {
                return;
            }

            parseJson(strinToJson(s));

            if (userList_ != null) {
                updateList();
            }
        }
    }
}
