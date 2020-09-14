package com.pentagon.android.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pentagon.android.Object.College;
import com.pentagon.android.R;

import java.util.List;

public class CollegeAdapter extends RecyclerView.Adapter<CollegeAdapter.CollegeViewHolder> {

    private static final String TAG = "CollegeAdapter";
    private Activity mContext;
    private List<College> mList;

    public CollegeAdapter(Activity mContext, List<College> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public CollegeAdapter.CollegeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.college_layout, parent, false);
        return new CollegeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollegeAdapter.CollegeViewHolder holder, int position) {
        College college = mList.get(position);
        holder.mName.setText(college.getName());
        holder.mCity.setText(college.getCity());
        holder.mState.setText(college.getState());
        holder.mCap.setText(college.getAdmissionCapacity());
        holder.mOwn.setText(college.getOwnership());
        holder.mBeds.setText(college.getHospitalBeds());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class CollegeViewHolder extends RecyclerView.ViewHolder {

        private TextView mName, mCity, mState, mCap, mOwn, mBeds;

        public CollegeViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.clg_name);
            mCity = itemView.findViewById(R.id.clg_city);
            mState = itemView.findViewById(R.id.clg_state);
            mCap = itemView.findViewById(R.id.clg_cap);
            mBeds = itemView.findViewById(R.id.clg_beds);
            mOwn = itemView.findViewById(R.id.clg_own);
        }
    }
}
