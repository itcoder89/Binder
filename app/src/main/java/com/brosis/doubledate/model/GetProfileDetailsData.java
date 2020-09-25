package com.brosis.doubledate.model;

import java.util.List;

public class GetProfileDetailsData {


    /**
     * code : 200
     * msg : [{"partner_code":"RAM789466464755","partner_details":{"fb_id":"919785364067","partner_code":"SANJAY9553132132","personal_code":"RAM789466464755","hobbies":"","smoke":"no","drink":"no","about_me":"","job_title":"","gender":"male","birthday":"15/09/2027","age":2020,"company":"","school":"","first_name":"ramniwas","last_name":"chaudhary ","image1":"","image2":"","image3":"","image4":"","image5":"","image6":""},"personal_code":"SANJAY9553132132","hobbies":"","smoke":"no","drink":"no","about_me":"","job_title":"","gender":"","birthday":"01/01/2000","age":20,"company":"","school":"","first_name":"Sanjay","last_name":"Chourasia","image1":"images/3591901744167046_460651719.jpg","image2":"images/3591901744167046_2109297429.jpg","image3":"images/3591901744167046_415436813.jpg","image4":"images/3591901744167046_487099570.jpg","image5":"","image6":""}]
     * error :
     */

    private String code;
    private String error;
    private List<MsgBean> msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<MsgBean> getMsg() {
        return msg;
    }

    public void setMsg(List<MsgBean> msg) {
        this.msg = msg;
    }

    public static class MsgBean {
        /**
         * partner_code : RAM789466464755
         * partner_details : {"fb_id":"919785364067","partner_code":"SANJAY9553132132","personal_code":"RAM789466464755","hobbies":"","smoke":"no","drink":"no","about_me":"","job_title":"","gender":"male","birthday":"15/09/2027","age":2020,"company":"","school":"","first_name":"ramniwas","last_name":"chaudhary ","image1":"","image2":"","image3":"","image4":"","image5":"","image6":""}
         * personal_code : SANJAY9553132132
         * hobbies :
         * smoke : no
         * drink : no
         * about_me :
         * job_title :
         * gender :
         * birthday : 01/01/2000
         * age : 20
         * company :
         * school :
         * first_name : Sanjay
         * last_name : Chourasia
         * image1 : images/3591901744167046_460651719.jpg
         * image2 : images/3591901744167046_2109297429.jpg
         * image3 : images/3591901744167046_415436813.jpg
         * image4 : images/3591901744167046_487099570.jpg
         * image5 :
         * image6 :
         */

        private String partner_code;
        private PartnerDetailsBean partner_details;
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

        public PartnerDetailsBean getPartner_details() {
            return partner_details;
        }

        public void setPartner_details(PartnerDetailsBean partner_details) {
            this.partner_details = partner_details;
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

        public static class PartnerDetailsBean {
            /**
             * fb_id : 919785364067
             * partner_code : SANJAY9553132132
             * personal_code : RAM789466464755
             * hobbies :
             * smoke : no
             * drink : no
             * about_me :
             * job_title :
             * gender : male
             * birthday : 15/09/2027
             * age : 2020
             * company :
             * school :
             * first_name : ramniwas
             * last_name : chaudhary
             * image1 :
             * image2 :
             * image3 :
             * image4 :
             * image5 :
             * image6 :
             */

            private String fb_id;
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

            public String getFb_id() {
                return fb_id;
            }

            public void setFb_id(String fb_id) {
                this.fb_id = fb_id;
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
    }
}
