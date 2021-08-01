package com.neuralgorithmic.rentathon;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.neuralgorithmic.rentathon.Rent.RentProductMain;

import java.util.ArrayList;

public class MessageHomeAdapter extends RecyclerView.Adapter<MessageHomeAdapter.ContactHolder> {

    // List to store all the contact details
    private ArrayList<MessageHomeCardInfo> messageList;
    private Context mContext;
    StorageReference storageRef;
    public Uri puri;
    public static String chatTransactionID;


    // Counstructor for the Class
    public MessageHomeAdapter(ArrayList<MessageHomeCardInfo> messageList, Context context) {
        this.messageList = messageList;
        this.mContext = context;
    }

    // This method creates views for the RecyclerView by inflating the layout
    // Into the viewHolders which helps to display the items in the RecyclerView
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.message_user_card, parent, false);
        return new ContactHolder(view);
    }

    @Override
    public int getItemCount() {
        return messageList == null? 0: messageList.size();
    }

    // This method is called when binding the data to the views being created in RecyclerView
    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, final int position) {
        final MessageHomeCardInfo messageHomeCardInfo = messageList.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MessageChat.class);
                chatTransactionID = messageHomeCardInfo.getTransactionID();
                mContext.startActivity(intent);

            }
        });

        holder.setSenderName(messageHomeCardInfo.getSenderName());
        holder.setSenderLastMessage(messageHomeCardInfo.getSenderLastMessage());
        holder.setSenderLastMessageTime(messageHomeCardInfo.getSenderLastMessageTime());

        // Set the data to the views here
        storageRef = FirebaseStorage.getInstance().getReference("users");
        storageRef.child("users/" + messageHomeCardInfo.getSenderUID()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                puri = uri;

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });

        holder.setSenderImage(puri);



        // You can set click listners to indvidual items in the viewholder here
        // make sure you pass down the listner or make the Data members of the viewHolder public

    }

    // This is your ViewHolder class that helps to populate data to the view
    public class ContactHolder extends RecyclerView.ViewHolder {

        private TextView senderName, senderLastMessage, senderLastMessageTime;
        //set image view
        private ImageView senderImage;

        public ContactHolder(View itemView) {
            super(itemView);
            senderName = itemView.findViewById(R.id.user_name);
            senderLastMessage = itemView.findViewById(R.id.user_last_message);
            senderLastMessageTime = itemView.findViewById(R.id.user_last_message_time);
            senderImage = itemView.findViewById(R.id.user_image);
        }

        public void setSenderName(String senderNameString) {
            senderName.setText(senderNameString);
        }

        public void setSenderLastMessage(String senderLastMessageString) {
            senderLastMessage.setText(senderLastMessageString);
        }

        public void setSenderLastMessageTime(String senderLastMessageTimeString) {
            senderLastMessageTime.setText(senderLastMessageTimeString);
        }

        public void setSenderImage(Uri imageUri) {
            //Glide.with(mContext).load(imageUri).into(senderImage);
            senderImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

    }

}
