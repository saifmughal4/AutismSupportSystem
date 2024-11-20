package com.example.autismsupportsystem;
import java.io.Serializable;
public class User implements Serializable {
    public String name, email, kidsAge, gender, height, weight, history, lifestyle, image;

    public User() {}
    public User(String name, String email, String kidsAge, String gender, String height, String weight, String history, String lifestyle, String image) {
        this.name = name;
        this.email = email;
        this.kidsAge = kidsAge;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.history = history;
        this.lifestyle = lifestyle;
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setKidsAge(String kidsAge) {
        this.kidsAge = kidsAge;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public void setLifestyle(String lifestyle) {
        this.lifestyle = lifestyle;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getKidsAge() {
        return kidsAge;
    }

    public String getGender() {
        return gender;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getHistory() {
        return history;
    }

    public String getLifestyle() {
        return lifestyle;
    }

    public String getImage() {
        return image;
    }



}
