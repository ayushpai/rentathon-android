package com.neuralgorithmic.rentathon;

import android.app.Application;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class ProfileViewholder extends RecyclerView.ViewHolder {

    TextView textViewName,textViewProfession,viewUserprofile,sendmessagebtn;
    TextView namell,vp_ll,namefollower,vpfollower,textViewLastSentTime, textViewLastSent;

    ImageView imageView,iv_ll,iv_follower;
    CardView cardView;

    public ProfileViewholder(@NonNull View itemView) {
        super(itemView);
    }

    public void setProfile(FragmentActivity fragmentActivity, String name, String uid
                           ,String url){

        cardView = itemView.findViewById(R.id.user_card);
        textViewName = itemView.findViewById(R.id.user_name);
        textViewLastSent = itemView.findViewById(R.id.user_last_message);
        textViewLastSentTime = itemView.findViewById(R.id.user_last_message_time);
        imageView = itemView.findViewById(R.id.user_image);


        Picasso.get().load(url).into(imageView);
        textViewName.setText(name);

    }


    public void setProfileInchat(Application fragmentActivity, String name, String uid,
                                 String url){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();

        /*ImageView imageView = itemView.findViewById(R.id.user_image);
        TextView nametv = itemView.findViewById(R.id.name_ch_item_tv);
        TextView proftv = itemView.findViewById(R.id.ch_itemprof_tv);
        sendmessagebtn = itemView.findViewById(R.id.send_messagech_item_btn);

        if (userid.equals(uid)){
            Picasso.get().load(url).into(imageView);
            nametv.setText(name);
            proftv.setText(prof);
            sendmessagebtn.setVisibility(View.INVISIBLE);
        }else {
            Picasso.get().load(url).into(imageView);
            nametv.setText(name);
            proftv.setText(prof);
        }

         */


    }





}
