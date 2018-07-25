package com.bloodbank.app.bloodbankapp;

public class SignupRequest {
    public long emp_id;
    public String email_id;
    public String name;
    public long phone;
    public String gender;
    public String blood_group;
    public String address;
    public String city;
    public boolean fit_for_donation;
    public String password;


    public SignupRequest(long emp_id,String email_id, String full_name,long phone,String gender, String blood_group, String address, String city, boolean fit_for_donation, String password){
        this.email_id = email_id;
        this.emp_id = emp_id;
        this.address=address;
        this.city = city;
        this.blood_group = blood_group;
        this.password = password;
        this.gender = gender;
        this.fit_for_donation = fit_for_donation;
        this.name = full_name;
        this.phone = phone;
    }

}
