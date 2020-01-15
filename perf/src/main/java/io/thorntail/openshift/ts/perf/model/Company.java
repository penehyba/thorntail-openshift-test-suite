package io.thorntail.openshift.ts.perf.model;

import java.util.HashSet;
import java.util.Set;

public class Company {

    public Company(int id, String name) {

        this.id = id;
        this.name = name;
    }

    private int id;

    private String name;

    private Set<Person> employees = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Person> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Person> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ",\"name\":\"" + name + '\"' +
                ",\"employees\":" + employees +
                '}';
    }
}
