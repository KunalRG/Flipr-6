package com.pentagon.android.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pentagon.android.Adapter.CollegeAdapter;
import com.pentagon.android.Adapter.NotificationAdapter;
import com.pentagon.android.MainActivity;
import com.pentagon.android.Object.College;
import com.pentagon.android.Object.Notification;
import com.pentagon.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MediFragment extends Fragment {
    private static final String TAG = "MediFragment";
    private TextView mHospital, mEmpty;
    private RequestQueue mQueue;
    private RecyclerView mRecycler;
    private ProgressBar mProgress;
    private List<College> mCollegeList;
    private TextView mFilter, mFilterClose;
    private Spinner mFilterState, mFilterType;
    private Button mFilterFilter;
    private CardView mFilterCard;
    private EditText mSearch;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_medi, container, false);
        mQueue = Volley.newRequestQueue(getActivity());
        mHospital = v.findViewById(R.id.fm_hospital);
        mCollegeList = new ArrayList<>();
        mRecycler = v.findViewById(R.id.fm_recycler);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProgress = v.findViewById(R.id.fm_progress);
        mSearch = v.findViewById(R.id.fm_search);
        mEmpty = v.findViewById(R.id.fm_empty);
        mFilter = v.findViewById(R.id.fm_filter);
        mFilterClose = v.findViewById(R.id.fm_filter_close);
        mFilterState = v.findViewById(R.id.fm_filter_state);
        mFilterType = v.findViewById(R.id.fm_filter_type);
        mFilterFilter = v.findViewById(R.id.fm_filter_filter);
        mFilterCard = v.findViewById(R.id.fm_filter_card);
        mFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFilterCard.setVisibility(View.VISIBLE);
            }
        });
        mFilterClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFilterCard.setVisibility(View.INVISIBLE);
            }
        });
        mFilterFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterActivity();
            }
        });
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
        jsonParseColleges("https://api.rootnet.in/covid19-in/hospitals/medical-colleges");
        mHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new HospFragment());
            }
        });
        return v;
    }

    private void search(String str) {
        List<College> mSearchedList = new ArrayList<>();
        String ip[] = str.split(" ");
        for (int i=0; i<ip.length; i++){
            for (int j=0; j<mCollegeList.size(); j++) {
                College college = mCollegeList.get(j);
                if (college.getName().toLowerCase().contains(ip[i].toLowerCase()) || college.getState().toLowerCase().contains(ip[i].toLowerCase())){
                    mSearchedList.add(college);
                }
            }
        }
        adapt(mSearchedList);
    }

    private void filterActivity() {
        String state = mFilterState.getSelectedItem().toString();
        String type = mFilterType.getSelectedItem().toString();
        mFilterCard.setVisibility(View.INVISIBLE);
        List<College> mFilteredList = new ArrayList<>();
        for (int i=0; i<mCollegeList.size(); i++){
            College college = mCollegeList.get(i);
            if ((college.getState().equals(state) || state.equals("States")) && (college.getOwnership().equals(type) || type.equals("Types"))){
                mFilteredList.add(college);
            }
        }
        adapt(mFilteredList);
    }
    private void adapt(List<College> mNewList) {
        if (mNewList.size() == 0){
            mEmpty.setVisibility(View.VISIBLE);
        }else {
            mEmpty.setVisibility(View.INVISIBLE);
        }
        CollegeAdapter adapter = new CollegeAdapter(getActivity(), mNewList);
        mRecycler.setAdapter(adapter);
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
    private void jsonParseColleges(String url) {
        mProgress.setVisibility(View.VISIBLE);
        mCollegeList.clear();
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONObject("data").getJSONArray("medicalColleges");
//                    Log.d(TAG, "onResponse: "+jsonArray.toString());
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        College college = new College(object.getString("state"), object.getString("name"), object.getString("city"), object.getString("ownership"), object.getString("admissionCapacity"), object.getString("hospitalBeds"));
                        mCollegeList.add(college);
                    }
                    loadSpinner();
                    adapt(mCollegeList);
                } catch (JSONException e) {
                    Log.d(TAG, "onResponse: "+e.getMessage());
                }
                mProgress.setVisibility(View.INVISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgress.setVisibility(View.INVISIBLE);
                Log.d(TAG, "onErrorResponse: "+error.toString());
            }
        });
        mQueue.add(request);
    }

    private void loadSpinner() {
        List<String> stateArray = new ArrayList<>();
        List<String> typeArray = new ArrayList<>();
        stateArray.add("States");
        typeArray.add("Types");
        for (int i=0; i<mCollegeList.size(); i++){
            College college = mCollegeList.get(i);
            if (!stateArray.contains(college.getState())){
                stateArray.add(college.getState());
            }
            if (!typeArray.contains(college.getOwnership())){
                typeArray.add(college.getOwnership());
            }
        }
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, stateArray);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mFilterState.setAdapter(stateAdapter);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, typeArray);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mFilterType.setAdapter(typeAdapter);
    }
}
