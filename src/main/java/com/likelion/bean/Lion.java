package com.likelion.bean;


import org.springframework.stereotype.Component;

@Component
public class Lion {


    private String name = "babyLion";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Lion : " + name;
    }

}
