package com.innovatesoft.learnjson;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    // URL to get contacts
    private static String url = "https://api.androidhive.info/contacts/";
    ProgressDialog dialog;
    RecyclerView recyclerView;
    ArrayList<HashMap<String, String>> contactList;
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        ItemOffsetDecoration decoration = new ItemOffsetDecoration(16);
        recyclerView.addItemDecoration(decoration);
        contactList = new ArrayList<>();
        dialog = new ProgressDialog(MainActivity.this);
        dialog.setCancelable(false);
        dialog.setMessage("Please Wait...");
        dialog.show();

        new GetAllContacts().execute();
    }

    private class GetAllContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            HttpHandler handler = new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = handler.getServiceResponse(url);
            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                JSONObject jsonObj;
                try {
                    jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("contacts");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String name = c.getString("name");
                        String email = c.getString("email");
                        String gender = c.getString("gender");

                        // Phone node is JSON Object
                        JSONObject phone = c.getJSONObject("phone");
                        String mobile = phone.getString("mobile");
                        String home = phone.getString("home");

                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("name", name);
                        contact.put("email", email);
                        contact.put("gender", gender);
                        contact.put("mobile", mobile);
                        contact.put("home", home);

                        // adding contact to contact list
                        contactList.add(contact);

                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            // Dismiss the progress dialog
            if (dialog.isShowing())
                dialog.dismiss();

            ContactListAdapter adapter = new ContactListAdapter(MainActivity.this, contactList);
            recyclerView.setAdapter(adapter);
        }
    }
}
