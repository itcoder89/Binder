package com.brosis.doubledate.Users;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by AQEEL on 10/24/2018.
 */


public class Nearby_User_Get_Set implements Serializable {

   public String fb_id,first_name,last_name,name,job_title,company,school,birthday,gender,about,location,super_like;
    ArrayList<String> imagesurl;
    private PartnerDetailsBean partner_details;

    public static class PartnerDetailsBean {
        /**
         * partner_code : SURESH6497813132
         * personal_code : RAM98465413131321
         * hobbies :
         * smoke : no
         * drink : no
         * about_me :
         * job_title :
         * gender :
         * birthday : 01/01/2000
         * age : 2020
         * company :
         * school :
         * first_name : Ram
         * last_name : Choudhary
         * image1 : images/3220684711312769_1255059775.jpg
         * image2 : images/3220684711312769_1084744042.jpg
         * image3 :
         * image4 :
         * image5 :
         * image6 :
         */

        private String partner_code;
        private String personal_code;
        private String hobbies;
        private String smoke;
        private String drink;
        private String about_me;
        private String job_title;
        private String gender;
        private String birthday;
        private int age;
        private String company;
        private String school;
        private String first_name;
        private String last_name;
        private String image1;
        private String image2;
        private String image3;
        private String image4;
        private String image5;
        private String image6;

        public String getPartner_code() {
            return partner_code;
        }

        public void setPartner_code(String partner_code) {
            this.partner_code = partner_code;
        }

        public String getPersonal_code() {
            return personal_code;
        }

        public void setPersonal_code(String personal_code) {
            this.personal_code = personal_code;
        }

        public String getHobbies() {
            return hobbies;
        }

        public void setHobbies(String hobbies) {
            this.hobbies = hobbies;
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

        public String getAbout_me() {
            return about_me;
        }

        public void setAbout_me(String about_me) {
            this.about_me = about_me;
        }

        public String getJob_title() {
            return job_title;
        }

        public void setJob_title(String job_title) {
            this.job_title = job_title;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getImage1() {
            return image1;
        }

        public void setImage1(String image1) {
            this.image1 = image1;
        }

        public String getImage2() {
            return image2;
        }

        public void setImage2(String image2) {
            this.image2 = image2;
        }

        public String getImage3() {
            return image3;
        }

        public void setImage3(String image3) {
            this.image3 = image3;
        }

        public String getImage4() {
            return image4;
        }

        public void setImage4(String image4) {
            this.image4 = image4;
        }

        public String getImage5() {
            return image5;
        }

        public void setImage5(String image5) {
            this.image5 = image5;
        }

        public String getImage6() {
            return image6;
        }

        public void setImage6(String image6) {
            this.image6 = image6;
        }
    }

    public String getHide_age() {
        return hide_age;
    }

    public void setHide_age(String hide_age) {
        this.hide_age = hide_age;
    }

    public String getHide_location() {
        return hide_location;
    }

    public void setHide_location(String hide_location) {
        this.hide_location = hide_location;
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

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getPartner_code() {
        return partner_code;
    }

    public void setPartner_code(String partner_code) {
        this.partner_code = partner_code;
    }

    public String getPersonal_code() {
        return personal_code;
    }

    public void setPersonal_code(String personal_code) {
        this.personal_code = personal_code;
    }

    public String hide_age,hide_location,smoke,drink,hobbies,partner_code,personal_code;
    String swipe;

    //public String partner_details,partner_code,personal_code,hobbies,smoke,drink,about_me,job_title,gender;
    public String getFb_id() {
        return fb_id;
    }

    public void setFb_id(String fb_id) {
        this.fb_id = fb_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getSwipe() {
        return swipe;
    }

    public void setSwipe(String swipe) {
        this.swipe = swipe;
    }

    public String getSuper_like() {
        return super_like;
    }

    public void setSuper_like(String super_like) {
        this.super_like = super_like;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ArrayList<String> getImagesurl() {
        return imagesurl;
    }

    public void setImagesurl(ArrayList<String> imagesurl) {
        this.imagesurl = imagesurl;
    }
}

