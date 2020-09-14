package com.pentagon.android.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pentagon.android.Object.Hospital;
import com.pentagon.android.R;

import java.util.List;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.HospitalViewHolder> {

    private static final String TAG = "HospitalAdapter";
    private Activity mContext;
    private List<Hospital> mList;

    public HospitalAdapter(Activity mContext, List<Hospital> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HospitalAdapter.HospitalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.hospital_layout, parent, false);
        return new HospitalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HospitalAdapter.HospitalViewHolder holder, int position) {
        Hospital hospital = mList.get(position);
        holder.mState.setText(hospital.getState());
        holder.mRH.setText(hospital.getRuralHospitals());
        holder.mRB.setText(hospital.getRuralBeds());
        holder.mUH.setText(hospital.getUrbanHospitals());
        holder.mUB.setText(hospital.getUrbanBeds());
        holder.mHT.setText(hospital.getTotalHospitals());
        holder.mBT.setText(hospital.getToralBeds());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class HospitalViewHolder extends RecyclerView.ViewHolder {

        private TextView mRH, mRB, mUH, mUB, mHT, mBT, mState;

        public HospitalViewHolder(@NonNull View itemView) {
            super(itemView);
            mRH = itemView.findViewById(R.id.hl_rh);
            mRB = itemView.findViewById(R.id.hl_rb);
            mUH = itemView.findViewById(R.id.hl_uh);
            mUB = itemView.findViewById(R.id.hl_ub);
            mHT = itemView.findViewById(R.id.hl_ht);
            mBT = itemView.findViewById(R.id.hl_bt);
            mState = itemView.findViewById(R.id.hl_state);
        }
    }
}
