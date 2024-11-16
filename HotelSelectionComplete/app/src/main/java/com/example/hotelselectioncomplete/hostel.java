package com.example.hotelselectioncomplete;

public class hostel{
    String id;
    String name;
    String location;
    String contact;
    hostel(String id,String name,String location,String contact){
        this.id=id;
        this.location=location;
        this.name=name;
        this.contact=contact;
    }
    @Override
    public String toString(){
        return name+" \ncontact - "+location+" \nlocation - "+contact;
    }
}
