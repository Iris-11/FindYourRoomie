package com.task.miniproject_findyourroomie;

public class User {
    private int id;
    private String username;
    private String email;
    private byte[] password;
    private byte[] salt;

    private String name;
    private String gender;
    private String dob;
    private String contact;
    private int age;
    private String city;
    private String area;
    private String job;
    private String native_place;
    private String mother_tongue;
    private String smoke;
    private String drink;
    private String diet;

    public User(String username, String email, byte[] password){
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public User(String username, String email, byte[] password, byte[] salt){
        this.username = username;
        this.email = email;
        this.password = password;
        this.salt = salt;
    }
    public User(String email, String name, String gender, String dob, String contact, int age, String city, String area, String job, String native_place, String mother_tongue, String smoke, String drink, String diet) {
        this.email=email;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.contact = contact;
        this.age = age;
        this.city = city;
        this.area = area;
        this.job = job;
        this.native_place = native_place;
        this.mother_tongue = mother_tongue;
        this.smoke = smoke;
        this.drink = drink;
        this.diet = diet;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password.getBytes();
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getNative_place() {
        return native_place;
    }

    public void setNative_place(String native_place) {
        this.native_place = native_place;
    }

    public String getMother_tongue() {
        return mother_tongue;
    }

    public void setMother_tongue(String mother_tongue) {
        this.mother_tongue = mother_tongue;
    }

    public String getSmoke() {
        return smoke;
    }

    public void setSmoke(String smoke) {
        this.smoke = smoke;
    }

    public String getDrink() {
        return drink;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

}
