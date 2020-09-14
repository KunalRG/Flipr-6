package com.pentagon.android.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pentagon.android.Adapter.ContactAdapter;
import com.pentagon.android.Object.Contact;
import com.pentagon.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ContactFragment extends Fragment {

    private static final String TAG = "ContactFragment";
    private RequestQueue mQueue;
    private TextView mNumber, mEmail, mTwitter, mFacebook;
    private ProgressBar mProgress;
    private List<Contact> mContactList;
    private RecyclerView mRecycler;
    private String num, eml, twt, fb;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contact, container, false);
        mQueue= Volley.newRequestQueue(getActivity());
        mNumber = v.findViewById(R.id.fc_number);
        mEmail = v.findViewById(R.id.fc_email);
        mFacebook = v.findViewById(R.id.fc_facebook);
        mTwitter = v.findViewById(R.id.fc_twitter);
        mProgress = v.findViewById(R.id.fc_progress);
        mContactList = new ArrayList<>();
        mRecycler = v.findViewById(R.id.fc_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setHasFixedSize(true);
        mNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (num!=null){
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", num, null));
                    startActivity(intent);
                }
            }
        });
        mFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fb != null){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(fb)));
                }
            }
        });
        mTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (twt != null){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(twt)));
                }
            }
        });
        jsonParse("https://api.rootnet.in/covid19-in/contacts");
        return v;
    }
    private void jsonParse(String url) {
        mProgress.setVisibility(View.VISIBLE);
        mContactList.clear();
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject contacts = response.getJSONObject("data").getJSONObject("contacts");
                    JSONObject primary = contacts.getJSONObject("primary");
                    JSONArray regional = contacts.getJSONArray("regional");
//                    Log.d(TAG, "onResponse: \n" +
//                            "Number:"+primary.getString("number")+
//                            "Email:"+primary.getString("email"));
                    num = primary.getString("number");
                    eml = primary.getString("email");
                    twt = primary.getString("twitter");
                    fb = primary.getString("facebook");
                    mNumber.setText(num);
                    mEmail.setText(eml);
                    mTwitter.setText(twt);
                    mFacebook.setText(fb);
                    for (int i=0; i<regional.length(); i++){
                        JSONObject object = regional.getJSONObject(i);
                        Contact contact = new Contact(object.getString("loc"), object.getString("number"));
//                        Log.d(TAG, "onResponse: "+contact.getLoc()+contact.getNumber());
                        mContactList.add(contact);
                    }
                    loadContacts();
                } catch (JSONException e) {
                    Log.d(TAG, "onResponse: "+e.getMessage());
                }
                mProgress.setVisibility(View.INVISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgress.setVisibility(View.INVISIBLE);
                Log.d(TAG, "onErrorResponse: " + error.toString());
            }
        });
        mQueue.add(request);
        Log.d(TAG, "jsonParse: finish");
    }

    private void loadContacts() {
        ContactAdapter adapter = new ContactAdapter(getActivity(), mContactList);
        mRecycler.setAdapter(adapter);
    }
}
