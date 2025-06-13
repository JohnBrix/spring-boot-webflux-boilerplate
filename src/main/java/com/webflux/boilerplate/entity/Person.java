package com.webflux.boilerplate.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * package com.webflux.boilerplate.entity; /**
 *
 * @author <John Brix Pomoy>
 * @version $Id: Person.java, v 0.1 2025-05-09 9:49 PM John Brix Pomoy Exp $$
 */
@Table
public class Person {

    @Id
    private Long id;

    @Column/*(name = "first_name")*/
    private String firstName;

    @Column/*(name = "last_name")*/
    private String lastName;

    @Column/*(name = "middle_name")*/
    private String middleName;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
}
