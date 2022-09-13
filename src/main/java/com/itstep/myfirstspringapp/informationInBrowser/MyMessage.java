package com.itstep.myfirstspringapp.informationInBrowser;

import java.util.Objects;

public class MyMessage {
    private String message;
    private Object obj;

    public MyMessage(String message, Object obj) {
        this.message = message;
        this.obj = obj;
    }
    public MyMessage() {}

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.obj);
        hash = 83 * hash + Objects.hashCode(this.message);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MyMessage other = (MyMessage) obj;
        if (!Objects.equals(this.message, other.message)) {
            return false;
        }
        return Objects.equals(this.obj, other.obj);
    }

    @Override
    public String toString() {
        return "MyMessage{" + "obj=" + obj + ", message=" + message + '}';
    }

}
