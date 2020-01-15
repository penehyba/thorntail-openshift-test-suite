package io.thorntail.openshift.ts.http.model;

public class Person {

    private int id;
    private String firstname;
    private String lastname;
    private String fullName;

    public Person(int id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.fullName = firstname + " " + lastname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "{\"id:\"" + id +
                ",\"firstname\":\"" + firstname + '\"' +
                ",\"lastname\":\"" + lastname + '\"' +
                ",\"fullName\":\"" + fullName + '\"' +
                '}';
    }
}
