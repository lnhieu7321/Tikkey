package Model;

public class User {
    private  String id ;
    private  String username ;
    private  String fullname ;
    private  String imageurl ;
    private String imagecover;
    private  String bio ;
    private  String birth ;
    private  String gender ;
    private  String occupation ;


    public User(String id, String username, String fullname, String imageurl,String imagecover, String bio, String birth, String gender, String occupation) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.imageurl = imageurl;
        this.imagecover = imagecover;
        this.bio = bio;
        this.birth = birth;
        this.gender = gender;
        this.occupation = occupation;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBirth() {
        return birth;
    }

    public String getImagecover() {
        return imagecover;
    }

    public void setImagecover(String imagecover) {
        this.imagecover = imagecover;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
