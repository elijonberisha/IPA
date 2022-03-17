package ch.cs.eb.ipa.model;

import ch.cs.eb.ipa.entity.CUser;

/**
 * author: Elijon Berisha
 * date: 14.03.2022
 * class: FormContainerUser.java
 */

// SERVES AS A DATA INPUT CONTAINER FOR THE REGISTER TEMPLATE; DATA IS STORED AS STRING TO AVOID EXCEPTIONS
public class FormContainerUser {
    // 6 DIGIT CTS ID
    private String cts_id;
    // FIRST NAME
    private String prename;
    // LAST NAME
    private String lastname;
    // PASSWORD (CURRENT)
    private String password;
    // REPEATED PASSWORD (PASSWORD CONFIRMATION)
    private String repeat_password;
    // NEW PASSWORD; IS ONLY USED IN THE CHANGE PASSWORD FUNCTIONALITY
    private String new_password;
    // COGNIZANT MAIL
    private String email;

    public FormContainerUser() {
    }

    public String getCts_id() {
        return cts_id;
    }

    public void setCts_id(String cts_id) {
        this.cts_id = cts_id;
    }

    public String getPrename() {
        return prename;
    }

    public void setPrename(String prename) {
        this.prename = prename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeat_password() {
        return repeat_password;
    }

    public void setRepeat_password(String repeat_password) {
        this.repeat_password = repeat_password;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // GENERATES CUser INSTANCE WITH THE PROPERTIES OF THIS CLASS; ID IS IGNORED AS IT IS CREATED UPON DB ENTRY
    public CUser generateCUser() {
        CUser user = new CUser();
        user.setCts_id(Integer.parseInt(getCts_id()));
        user.setEmail(getEmail());
        user.setLastname(getLastname());
        user.setPrename(getPrename());
        user.setPassword(getPassword());
        return user;
    }

    @Override
    public String toString() {
        return "FormContainerUser{" +
                "cts_id='" + cts_id + '\'' +
                ", prename='" + prename + '\'' +
                ", lastname='" + lastname + '\'' +
                ", password='" + password + '\'' +
                ", repeat_password='" + repeat_password + '\'' +
                ", new_password='" + new_password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
