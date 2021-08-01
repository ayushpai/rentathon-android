package com.neuralgorithmic.rentathon;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ContactHolder> {

    // List to store all the contact details
    private ArrayList<ChatCardInfo> messageList;
    private Context mContext;
    StorageReference storageRef;
    public Uri puri;
    public static String chatTransactionID;
    public boolean UIDMatches = false;


    // Counstructor for the Class
    public ChatAdapter(ArrayList<ChatCardInfo> messageList, Context context) {
        this.messageList = messageList;
        this.mContext = context;
    }

    // This method creates views for the RecyclerView by inflating the layout
    // Into the viewHolders which helps to display the items in the RecyclerView
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.message_sent_message, parent, false);;


        return new ContactHolder(view);
    }



    @Override
    public int getItemCount() {
        return messageList == null? 0: messageList.size();
    }

    // This method is called when binding the data to the views being created in RecyclerView
    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, final int position) {
        final ChatCardInfo chatCardInfo = messageList.get(position);

        UIDMatches = chatCardInfo.isUIDMatches();
        holder.setMessageTime(chatCardInfo.getMessageContent(), chatCardInfo.getMessageTime(), UIDMatches);


        // You can set click listners to indvidual items in the viewholder here
        // make sure you pass down the listner or make the Data members of the viewHolder public

    }

    // This is your ViewHolder class that helps to populate data to the view
    public class ContactHolder extends RecyclerView.ViewHolder {

        private TextView messageSent, timeSent, messageReceived, timeReceived;
        private ConstraintLayout constraintLayout;

        public ContactHolder(View itemView) {
            super(itemView);
            messageSent = itemView.findViewById(R.id.messageSent);
            timeSent = itemView.findViewById(R.id.time_sent_sent);

            messageReceived = itemView.findViewById(R.id.messageRecieved);
            timeReceived = itemView.findViewById(R.id.time_sent_recieved);

            constraintLayout = itemView.findViewById(R.id.layoutmain);


        }


        
        public void setMessageTime(String messageString, String timeString, boolean UIDMatch) {
            timeReceived.setVisibility(View.VISIBLE);
            messageReceived.setVisibility(View.VISIBLE);
            messageSent.setVisibility(View.VISIBLE);
            timeSent.setVisibility(View.VISIBLE);
            if(UIDMatch){
                messageSent.setText(messageString);
                messageReceived.setVisibility(View.GONE);

                timeSent.setText(timeString);
                timeReceived.setVisibility(View.GONE);
            }
            else{
                messageReceived.setText(messageString);
                messageSent.setVisibility(View.GONE);

                timeReceived.setText(timeString);
                timeSent.setVisibility(View.GONE);
            }

        }

        

    }
    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

}
