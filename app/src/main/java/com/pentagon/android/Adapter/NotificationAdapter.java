package com.pentagon.android.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.pentagon.android.Object.Contact;
import com.pentagon.android.Object.Notification;
import com.pentagon.android.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private static final String TAG = "NotificationAdapter";
    private Activity mContext;
    private List<Notification> mList;

    public NotificationAdapter(Activity mContext, List mList) {
        this.mContext = mContext;
        this.mList = mList;
    }


    @NonNull
    @Override
    public NotificationAdapter.NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.notification_layout, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.NotificationViewHolder holder, int position) {
        final Notification notification = mList.get(position);
        String str = notification.getTitle();
        if (str.length() > 11 && (isDate_1(str.substring(0, 11)) || isDate_2(str.substring(0, 11)))){
            String date = str.substring(0, 11);
            String title = str.substring(11);
            holder.mTitle.setText(title);
            holder.mDate.setText(date);
        }else {
            holder.mTitle.setText(str);
            holder.mDate.setText("NA");
        }
        holder.mLink.setText(notification.getLink());
        holder.mCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage(notification.getLink())
                        .setPositiveButton("OPEN", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(notification.getLink())));
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });
    }

    private boolean isDate_1(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date.trim());
        }catch (ParseException e){
            return false;
        }
        return true;
    }

    private boolean isDate_2(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date.trim());
        }catch (ParseException e){
            return false;
        }
        return true;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitle, mLink, mDate;
        private CardView mCard;
        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.nl_title);
            mLink = itemView.findViewById(R.id.nl_link);
            mDate = itemView.findViewById(R.id.nl_date);
            mCard = itemView.findViewById(R.id.nl_card);
        }
    }
}
