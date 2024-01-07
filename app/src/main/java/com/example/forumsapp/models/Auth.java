package com.example.forumsapp.models;

import com.example.forumsapp.utils.Constansts;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Auth implements Serializable {

    private String status, token, user_id, user_fname, user_lname;

    public Auth() {
    }

    public Auth(JSONObject jsonObject) throws JSONException {
        this.status = jsonObject.getString(Constansts.STATUS);
        this.token = jsonObject.getString(Constansts.TOKEN);
        this.user_id = jsonObject.getString(Constansts.USER_ID);
        this.user_fname = jsonObject.getString(Constansts.USER_FNAME);
        this.user_lname = jsonObject.getString(Constansts.USER_LNAME);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_fname() {
        return user_fname;
    }

    public void setUser_fname(String user_fname) {
        this.user_fname = user_fname;
    }

    public String getUser_lname() {
        return user_lname;
    }

    public void setUser_lname(String user_lname) {
        this.user_lname = user_lname;
    }
}
