package com.bloodbank.app.bloodbankapp;

import java.util.Date;

public class RequesterRequest {
    private String email_id;
    private String blood_group;
    private int quantity;
    private String location;
    private String deadline;
    private String story;
    private boolean provideCab;

    public RequesterRequest(String email_id,String blood_group, int quantity,String location, String deadline, String story, boolean provideCab){
        this.email_id = email_id;
        this.blood_group = blood_group;
        this.quantity = quantity;
        this.location = location;
        this.deadline = deadline;
        this.story = story;
        this.provideCab = provideCab;
    }

}
