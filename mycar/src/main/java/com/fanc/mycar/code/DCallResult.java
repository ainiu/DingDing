package com.fanc.mycar.code;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DCallResult {

    private JSONObject response;

    private boolean isCache=false;//是否缓存数据

    public String getMessage() throws JSONException {
        return response.has("message") ? response.getString("message") : null;
    }
    public int getResult() throws JSONException {
        return response.has("result") ? response.getInt("result") : 0;
    }

    public String getContentString()throws JSONException {
        if(!response.has("content")){
            return null;
        }
        if(response.isNull("content")){
            return null;
        }
        String object=response.getString("content");
        if(object.toLowerCase().equals("null")){
            return null;
        }
        return object;
    }
    public JSONObject getContentObject()throws JSONException {
        if(!response.has("content")){
            return null;
        }
        if(response.isNull("content")){
            return null;
        }
        JSONObject object=response.getJSONObject("content");
        if(object.toString().toLowerCase().equals("null")){
            return null;
        }
        return object;
    }
    public JSONArray getContentArray()throws JSONException {
        if(!response.has("content")){
            return null;
        }
        if(response.isNull("content")){
            return null;
        }
        JSONArray object=response.getJSONArray("content");
        if(object.toString().toLowerCase().equals("null")){
            return null;
        }
        return object;
    }
    public JSONObject getResponse(){
        return response;
    }
    public void setResponse(String response) throws JSONException {
        this.response = new JSONObject(response);
    }
    public boolean isCache() {
        return isCache;
    }
    public void setCache(boolean isCache) {
        this.isCache = isCache;
    }
}
