package com.zespr.labq_merged;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.LinkedHashSet;
import java.util.Set;

public class LoginActivity {
    private SharedPreferences preference;

    LoginActivity(Context context) {
        preference = context.getSharedPreferences("Register",Context.MODE_PRIVATE);
    }

    public boolean setStatus() {
        preference.edit().putBoolean("Status",true).apply();
        return true;
    }
    public boolean setEmail(String s) {
        preference.edit().putString("EMAIL",s).apply();
        return true;
    }
    public boolean setMobile(String s) {
        preference.edit().putString("MOBILE",s).apply();
        return true;
    }

    public Boolean checkHash(String h) {
        return preference.getStringSet(h, null) == null;
    }
    public void setValues(String e,String m,String p) {
        Set<String> set = new LinkedHashSet<>();
        set.add(e);
        set.add(m);
        String hash = p.hashCode() + "";
        preference.edit().putBoolean("Status",true).apply();
        preference.edit().putStringSet(hash,set).apply();
    }

    public String[] getValues(String h) {
        Set<String> set = preference.getStringSet(h,null);
        String a[] = new String[3];
        a = (String[]) set.toArray();
        return a;
    }

    public boolean getStatus() {
        return preference.getBoolean("Status",false);
    }
    public String getEmail() {
        return preference.getString("EMAIL","");
    }
    public String getMobile() {
        return preference.getString("MOBILE","");
    }

    public boolean removeStatus() {
        preference.edit().remove("Status").apply();
        return true;
    }
}
