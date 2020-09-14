package com.pentagon.android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;


import com.pentagon.android.Object.Notification;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity implements OnChartGestureListener, OnChartValueSelectedListener {

    private static final String TAG = "TestActivity";
    private RequestQueue mQueue;
    private LineChart mChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mQueue= Volley.newRequestQueue(this);

        initChart();


        ArrayList<Entry> yValues = new ArrayList();

        yValues.add(new Entry(0,50));
        yValues.add(new Entry(1,60));
        yValues.add(new Entry(3,65));
        yValues.add(new Entry(4,40));
        yValues.add(new Entry(5,55));
        yValues.add(new Entry(6,35));
        yValues.add(new Entry(7,70));
        yValues.add(new Entry(8,70));


        LineDataSet set1 = new LineDataSet(yValues,"Total Sampled Cases(X axis) Vs Total Confirmed Cases(Y axis)" );

        set1.setFillAlpha(110);
        set1.setColor(Color.RED);
        set1.setLineWidth(3);
        set1.setValueTextSize(10);
        set1.setValueTextColor(Color.BLUE);


        ArrayList<ILineDataSet> dataSets = new ArrayList();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);
        mChart.setData(data);


//        jsonParseContact("https://api.rootnet.in/covid19-in/contacts");
//        jsonParseNotification("https://api.rootnet.in/covid19-in/notifications");
//        jsonParseHospitals("https://api.rootnet.in/covid19-in/hospitals/beds");
//        jsonParseColleges("https://api.rootnet.in/covid19-in/hospitals/medical-colleges");
//        jsonParseCases("https://api.jsonbin.io/b/5f5e5a4b7243cd7e823b7764/1");
    }

    private void initChart() {
        mChart = findViewById(R.id.line_chart);

        mChart.setOnChartGestureListener(TestActivity.this);
        mChart.setOnChartValueSelectedListener(TestActivity.this);

        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        LimitLine upper_limit = new LimitLine(65f,"Danger");
        upper_limit.setLineWidth(4f);
        upper_limit.enableDashedLine(10f,10f,0f);
        upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        upper_limit.setTextSize(15f);

        LimitLine lower_limit = new LimitLine(40f,"Safe");
        lower_limit.setLineWidth(2f);
        lower_limit.setLineColor(Color.GREEN);
        lower_limit.enableDashedLine(10f,10f,0f);
        lower_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        lower_limit.setTextSize(15f);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.addLimitLine(upper_limit);
        leftAxis.addLimitLine(lower_limit);

        leftAxis.setAxisMaximum(80f);
        leftAxis.setAxisMinimum(25f);
        leftAxis.enableGridDashedLine(10f,10f,20f);
        leftAxis.setDrawLimitLinesBehindData(true);


        mChart.getAxisRight().setEnabled(false);
    }

    private void jsonParseCases(String url) {
        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0; i<response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        Log.d(TAG, "onResponse: "+object.toString());
                    } catch (JSONException e) {
                        Log.d(TAG, "onResponse: "+e.getMessage());
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error.toString());
            }
        });
        mQueue.add(request);
    }

    private void jsonParseColleges(String url) {
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONObject("data").getJSONArray("medicalColleges");
//                    Log.d(TAG, "onResponse: "+jsonArray.toString());

                } catch (JSONException e) {
                    Log.d(TAG, "onResponse: "+e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error.toString());
            }
        });
        mQueue.add(request);
    }

    private void jsonParseHospitals(String url) {
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject summary = response.getJSONObject("data").getJSONObject("summary");
                    Log.d(TAG, "onResponse: summary:" +
                            "\nrural hospitals: " + summary.getString("ruralHospitals")+
                            "\nrural beds:" +summary.getString("ruralBeds")+
                            "\nurban hospitals:" +summary.getString("urbanHospitals")+
                            "\nurban beds:" +summary.getString("urbanBeds")+
                            "\ntotal hospitals:" +summary.getString("totalHospitals")+
                            "\ntotal beds:"+summary.getString("totalBeds"));
                    JSONArray jsonArray = response.getJSONObject("data").getJSONArray("regional");
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        Log.d(TAG, "onResponse: "+object);
                    }
                } catch (JSONException e) {
                    Log.d(TAG, "onResponse: "+e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error.toString());
            }
        });
        mQueue.add(request);
    }

    private void jsonParseNotification(String url) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray notifications = response.getJSONObject("data").getJSONArray("notifications");
                    for (int i=0; i<notifications.length(); i++){
                        JSONObject object = notifications.getJSONObject(i);
                        Notification notification = new Notification(object.getString("title"), object.getString("link"));
                        Log.d(TAG, "onResponse: \n\ttitle: " + notification.getTitle()+"\n\tlink: "+notification.getLink());
                    }
                } catch (JSONException e) {
                    Log.d(TAG, "onResponse: "+e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error.toString());
            }
        });
        mQueue.add(request);
    }

    private void jsonParseContact(String url) {
        Log.d(TAG, "jsonParse: Initialised");
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject contacts = response.getJSONObject("data").getJSONObject("contacts");
                    JSONObject primary = contacts.getJSONObject("primary");
                    JSONArray regional = contacts.getJSONArray("regional");
                    Log.d(TAG, "onResponse: \n" +
                            "Number:"+primary.getString("number")+
                            "Email:"+primary.getString("email"));
                    for (int i=0; i<regional.length(); i++){
                        JSONObject object = regional.getJSONObject(i);
                        Log.d(TAG, "onResponse: loc: " + object.getString("loc") + " number: " + object.getString("number"));
                    }
                } catch (JSONException e) {
                    Log.d(TAG, "onResponse: "+e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.toString());
            }
        });
        mQueue.add(request);
        Log.d(TAG, "jsonParse: finish");
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Toast.makeText(this, e.getX()+", "+e.getY(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected() {

    }
}
