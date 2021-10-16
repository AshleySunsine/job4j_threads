package ru.job4j.executorservice;

public class User {
    String userName;
    String eMail;

    public User(String userName, String eMail) {
        this.userName = userName;
        this.eMail = eMail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }
}
