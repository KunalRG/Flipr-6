package com.pentagon.android.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pentagon.android.Adapter.NotificationAdapter;
import com.pentagon.android.Object.College;
import com.pentagon.android.Object.Notification;
import com.pentagon.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotiFragment extends Fragment {

    private static final String TAG = "NotiFragment";
    private RequestQueue mQueue;
    private ProgressBar mProgress;
    private RecyclerView mRecycler;
    private List<Notification> mNotificarionList;
    private EditText mSearch;
    private TextView mEmpty;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_noti, container, false);
        mQueue= Volley.newRequestQueue(getActivity());
        mNotificarionList = new ArrayList<>();
        mProgress = v.findViewById(R.id.fn_progress);
        mRecycler = v.findViewById(R.id.fn_recycler);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSearch = v.findViewById(R.id.fn_search);
        mEmpty = v.findViewById(R.id.fn_empty);
        mSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String ed = editable.toString().trim();
                if (ed.length()>0){
                    search(ed);
                }
            }
        });
        jsonParseNotification("https://api.rootnet.in/covid19-in/notifications");
        return v;
    }

    private void search(String str) {
        List<Notification> mSearchedList = new ArrayList<>();
        String ip[] = str.split(" ");
        for (int i=0; i<ip.length; i++){
            for (int j=0; j<mNotificarionList.size(); j++) {
                Notification notification = mNotificarionList.get(j);
                if (notification.getTitle().toLowerCase().trim().contains(ip[i].toLowerCase().trim())){
                    mSearchedList.add(notification);
                }
            }
        }
        adapt(mSearchedList);
    }

    private void adapt(List<Notification> mNewList) {
        if (mNewList.size() == 0){
            mEmpty.setVisibility(View.VISIBLE);
        }else {
            mEmpty.setVisibility(View.INVISIBLE);
        }
        NotificationAdapter adapter = new NotificationAdapter(getActivity(), mNewList);
        mRecycler.setAdapter(adapter);
    }

    private void jsonParseNotification(String url) {
        mProgress.setVisibility(View.VISIBLE);
        mNotificarionList.clear();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray notifications = response.getJSONObject("data").getJSONArray("notifications");
                    for (int i=0; i<notifications.length(); i++){
                        JSONObject object = notifications.getJSONObject(i);
                        Notification notification = new Notification(object.getString("title"), object.getString("link"));
//                        Log.d(TAG, "onResponse: \n\ttitle: " + notification.getTitle()+"\n\tlink: "+notification.getLink());
                        mNotificarionList.add(notification);
                    }
                    adapt(mNotificarionList);
                } catch (JSONException e) {
                    Log.d(TAG, "onResponse: "+e.getMessage());
                }
                mProgress.setVisibility(View.INVISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error.toString());
                mProgress.setVisibility(View.INVISIBLE);
            }
        });
        mQueue.add(request);
    }


}
