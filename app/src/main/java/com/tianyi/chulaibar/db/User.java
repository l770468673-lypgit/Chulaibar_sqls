package com.tianyi.chulaibar.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;


/**
 * @
 * @类名称: ${}
 * @类描述: ${type_name}
 * @创建人： Lyp
 * @创建时间：${date} ${time}
 * @备注：
 */

@Table(name = "User")
public class User extends Model {


    //    添加字段
    @Column(name = "telphone")
    String telphone;

    @Column(name = "email")
    String email;

    @Column(name = "password")
    String password;


    @Column(name = "userType")
    int userType;

    @Column(name = "logoen")
    int logoen;


    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getLogoen() {
        return logoen;
    }

    public void setLogoen(int logoen) {
        this.logoen = logoen;
    }
}
