package com.example.myEcom.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;


@Table(name = "Pushnotifications")
public class Notification extends Model {

    @Column(name = "title")
    public String title;

    @Column(name = "body")
    public String body;

}
