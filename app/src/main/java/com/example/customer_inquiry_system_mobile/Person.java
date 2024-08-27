package com.example.customer_inquiry_system_mobile;

public class Person {
    private int img;         // Drawable 파일 리소스 ID
    private String name;     // 이름
    private String phoneNumber; // 전화번호

    // 생성자
    public Person(int img, String name, String phoneNumber) {
        this.img = img;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    // Getter 및 Setter 메서드
    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

