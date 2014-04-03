package com.example.startme.app.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;

/**
 * Created by Tian on 4/3/14.
 */
public class User {

    String user_id;
    String name;
    String surname;
    Bitmap imgProfile;
    String email;
    String count_p;
    String count_fwers;
    String count_fwing;
    boolean is_followed;
    String nickname;
    String facebook;

    User(String user_id, String is_followed, String name, String surname, String email, String img_profile, String count_p, String count_fwers,
         String count_fwing ){

        this.user_id = user_id != null ? user_id : "";
        this.is_followed = is_followed.compareTo("") == 0 || is_followed.compareTo("0") == 0 ? false : true;
        this.name = name != null ? name : "";
        this.surname = surname != null ? surname : "";
        this.email = email != null ? email : "";
        this.imgProfile = BitmapFactory.decodeFile(img_profile);
        this.count_p = count_p.compareTo("") == 0|| count_p == null ? "0" : count_p;
        this.count_fwers = count_fwers.compareTo("") == 0|| count_fwers == null ? "0" : count_fwers;
        this.count_fwing = count_fwing.compareTo("") == 0|| count_fwing == null ? "0" : count_fwing;
    }



}
