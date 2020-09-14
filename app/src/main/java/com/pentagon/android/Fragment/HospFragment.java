package com.pentagon.android.Fragment;

import android.content.Context;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pentagon.android.Adapter.HospitalAdapter;
import com.pentagon.android.Object.Hospital;
import com.pentagon.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class HospFragment extends Fragment {

    private static final String TAG = "HospFragment";
    private RequestQueue mQueue;
    private TextView mCollege;
    private TextView mRH, mRB, mUH, mUB, mHT, mBT;
    private ProgressBar mProgress;
    private RecyclerView mRecycler;
    private List<Hospital> mHospitalList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hosp, container, false);
        mQueue = Volley.newRequestQueue(getActivity());
        mCollege = v.findViewById(R.id.fh_college);
        mRH = v.findViewById(R.id.fh_rh);
        mRB = v.findViewById(R.id.fh_rb);
        mUH = v.findViewById(R.id.fh_uh);
        mUB = v.findViewById(R.id.fh_ub);
        mHT = v.findViewById(R.id.fh_ht);
        mBT = v.findViewById(R.id.fh_bt);
        mProgress = v.findViewById(R.id.fh_progress);
        mRecycler = v.findViewById(R.id.fh_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setHasFixedSize(true);
        mHospitalList = new ArrayList<>();
        jsonParseHospitals("https://api.rootnet.in/covid19-in/hospitals/beds");
        mCollege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new MediFragment());
            }
        });
        return v;
    }
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.am_frame_layout, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private void jsonParseHospitals(String url) {
        mProgress.setVisibility(View.VISIBLE);
        mHospitalList.clear();
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject summary = response.getJSONObject("data").getJSONObject("summary");
                    mRH.setText(summary.getString("ruralHospitals"));
                    mRB.setText(summary.getString("ruralBeds"));
                    mUH.setText(summary.getString("urbanHospitals"));
                    mUB.setText(summary.getString("urbanBeds"));
                    mHT.setText(summary.getString("totalHospitals"));
                    mBT.setText(summary.getString("totalBeds"));
                    JSONArray jsonArray = response.getJSONObject("data").getJSONArray("regional");
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
//                        Log.d(TAG, "onResponse: "+object);
                        Hospital hospital = new Hospital(object.getString("state"), object.getString("ruralBeds"), object.getString("ruralHospitals"), object.getString("urbanBeds"), object.getString("urbanHospitals"), object.getString("totalHospitals"), object.getString("totalBeds"));
                        mHospitalList.add(hospital);
                    }
                    HospitalAdapter adapter = new HospitalAdapter(getActivity(), mHospitalList);
                    mRecycler.setAdapter(adapter);
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

    private static String[] suffix = new String[]{"","k", "m", "b", "t"};
    private static int MAX_LENGTH = 4;

    private static String format(String num) {
        double number = Double.valueOf(num);
        String r = new DecimalFormat("##0E0").format(number);
        r = r.replaceAll("E[0-9]", suffix[Character.getNumericValue(r.charAt(r.length() - 1)) / 3]);
        while (r.length() > MAX_LENGTH || r.matches("[0-9]+\\.[a-z]")) {
            r = r.substring(0, r.length() - 2) + r.substring(r.length() - 1);
        }
        return r;
    }
}
