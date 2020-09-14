package com.pentagon.android.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pentagon.android.Object.Contact;
import com.pentagon.android.R;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private static final String TAG = "ContactAdapter";
    private Activity mContext;
    private List<Contact> mList;

    public ContactAdapter(Activity mContext, List mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ContactAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.contact_layout, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ContactViewHolder holder, int position) {
        final Contact contact = mList.get(position);
        holder.mLocation.setText(contact.getLoc());
        holder.mNumber.setText(contact.getNumber());
        holder.mCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", contact.getNumber(), null));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {

        private TextView mLocation;
        private TextView mNumber;
        private ImageView mCall;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            mLocation = itemView.findViewById(R.id.cl_location);
            mNumber = itemView.findViewById(R.id.cl_number);
            mCall = itemView.findViewById(R.id.cl_call);
        }
    }
}
