package io.thorntail.openshift.ts.http.model;

import java.util.HashSet;
import java.util.Set;

//@Entity
//@Table(name = "company")
//@NamedQueries({
//        @NamedQuery(name = "Company.findAll", query = "select c from Company c")
//})
public class Company {

    public Company(int id, String name) {

        this.id = id;
        this.name = name;
    }
    //    @Id
//    @GeneratedValue
//    @Column(name = "id")
    private int id;

    //    @Column(name = "name")
    private String name;

//    @ManyToMany(cascade = {CascadeType.ALL})
//    @JoinTable(
//            name = "employee",
//            joinColumns = @JoinColumn(name = "companyId", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "personId", referencedColumnName = "id")
//    )
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
