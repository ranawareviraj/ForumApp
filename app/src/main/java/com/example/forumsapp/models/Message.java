package com.example.forumsapp.models;

import com.example.forumsapp.utils.Constansts;
import org.json.JSONException;
import org.json.JSONObject;

public class Message {
    String message_id, message, created_at;
    String createdByFname, createdByLname, createdByUserId;

    public Message() {
    }

    public Message(JSONObject jsonObject) throws JSONException {
        this.message_id = jsonObject.getString(Constansts.MESSAGE_ID);
        this.message = jsonObject.getString(Constansts.MESSAGE);
        this.created_at = jsonObject.getString(Constansts.CREATED_AT);

        this.createdByFname = jsonObject.getJSONObject(Constansts.CREATED_BY).getString(Constansts.FNAME);
        this.createdByLname = jsonObject.getJSONObject(Constansts.CREATED_BY).getString(Constansts.LNAME);
        this.createdByUserId = jsonObject.getJSONObject(Constansts.CREATED_BY).getString(Constansts.USER_ID);

    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
