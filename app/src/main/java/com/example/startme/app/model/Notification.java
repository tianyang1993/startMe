package com.example.startme.app.model;

import android.graphics.Bitmap;

/**
 * Created by Tian on 4/3/14.
 */
public class Notification {

    public static final int kNotificationTypeLike = 0;
    public static final int kNotificationTypeComment = 1;
    public static final int kNotificationTypeFollowed = 2;
    public static final int kNotificationTypeTaggedPost = 3;
    public static final int kNotificationTypeTaggedComment = 4;

    String id_notific;
    int type;
    String id_sender;
    String sender_name;
    String sender_surname;
    String sender_nickname;
    Bitmap sender_image;
    String sender_image_name;
    String id_receiver;
    String post_id;
    String comment_id;
    String title_post;
    String descr_comment;
    boolean is_new;


}
