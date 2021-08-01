package com.neuralgorithmic.rentathon;

import android.media.Image;
import android.net.Uri;
import android.widget.ImageView;

public class MessageHomeCardInfo {

    private String senderName;
    private String senderLastMessage;
    private String senderLastMessageTime;
    private String senderUID;
    private Uri imageUri;
    private String transactionID;

    public MessageHomeCardInfo(String senderName, String senderLastMessage, String senderLastMessageTime, String senderUID, String transactionID) {
        this.senderName = senderName;
        this.senderLastMessage = senderLastMessage;
        this.senderLastMessageTime = senderLastMessageTime;
        this.senderUID = senderUID;
        this.transactionID = transactionID;
        //this.imageUri = imageUri;
    }


    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderLastMessage() {
        return senderLastMessage;
    }

    public void setSenderLastMessage(String senderLastMessage) {
        this.senderLastMessage = senderLastMessage;
    }

    public String getSenderLastMessageTime() {
        return senderLastMessageTime;
    }

    public void setSenderLastMessageTime(String senderLastMessageTime) {
        this.senderLastMessageTime = senderLastMessageTime;
    }

    public String getSenderUID() {
        return senderUID;
    }

    public void setSenderUID(String senderUID) {
        this.senderUID = senderUID;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

}
