package com.neuralgorithmic.rentathon;

public class ChatCardInfo {

    private String senderUID;
    private String messageTime;
    private String messageContent;

    private boolean UIDMatches;

    public ChatCardInfo(String senderUID, String messageTime, String messageContent, boolean UIDMatches) {
        this.senderUID = senderUID;
        this.messageTime = messageTime;
        this.messageContent = messageContent;
        this.UIDMatches = UIDMatches;
    }

    public String getSenderUID() {
        return senderUID;
    }

    public void setSenderUID(String senderUID) {
        this.senderUID = senderUID;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public boolean isUIDMatches() {
        return UIDMatches;
    }

    public void setUIDMatches(boolean UIDMatches) {
        this.UIDMatches = UIDMatches;
    }
}
