package ch.cs.eb.ipa.model;

// SERVES AS A DATA INPUT CONTAINER FOR THE REGISTER TEMPLATE; DATA IS STORED AS STRING
public class FormContainerUser {
    private String cts_id;
    private String prename;
    private String lastname;
    private String password;
    private String repeat_password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "FormContainerUser{" +
                "cts_id='" + cts_id + '\'' +
                ", prename='" + prename + '\'' +
                ", lastname='" + lastname + '\'' +
                ", password='" + password + '\'' +
                ", repeat_password='" + repeat_password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
