package com.example.forumsapp.models;

import com.example.forumsapp.utils.Constansts;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Forum implements Serializable {
    String thread_id, title, created_at;
    String createdByFname, createdByLname, createdByUserId;

    public Forum() {
    }

    public Forum(JSONObject jsonObject) throws JSONException {

        this.thread_id = jsonObject.getString(Constansts.THREAD_ID);
        this.title = jsonObject.getString(Constansts.TITLE);
        this.created_at = jsonObject.getString(Constansts.CREATED_AT);

        this.createdByFname = jsonObject.getJSONObject(Constansts.CREATED_BY).getString(Constansts.FNAME);
        this.createdByLname = jsonObject.getJSONObject(Constansts.CREATED_BY).getString(Constansts.LNAME);
        this.createdByUserId = jsonObject.getJSONObject(Constansts.CREATED_BY).getString(Constansts.USER_ID);

    }

    public String getThread_id() {
        return thread_id;
    }

    public void setThread_id(String thread_id) {
        this.thread_id = thread_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCreatedByFname() {
        return createdByFname;
    }

    public void setCreatedByFname(String createdByFname) {
        this.createdByFname = createdByFname;
    }

    public String getCreatedByLname() {
        return createdByLname;
    }

    public void setCreatedByLname(String createdByLname) {
        this.createdByLname = createdByLname;
    }

    public String getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(String createdByUserId) {
        this.createdByUserId = createdByUserId;
    }
}
