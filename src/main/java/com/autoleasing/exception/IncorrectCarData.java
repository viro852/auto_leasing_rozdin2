package com.autoleasing.exception;

import java.util.Objects;

public class IncorrectCarData {

    String info;

    public IncorrectCarData() {
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IncorrectCarData that = (IncorrectCarData) o;
        return Objects.equals(info, that.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(info);
    }

    @Override
    public String toString() {
        return "IncorrectCarData{" +
                "info='" + info + '\'' +
                '}';
    }
}
