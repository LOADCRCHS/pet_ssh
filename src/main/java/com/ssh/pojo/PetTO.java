package com.ssh.pojo;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "pet", schema = "pet_db", catalog = "")
public class PetTO {
    private int id;
    private String name;
    private Integer breed;
    private Date birthDay;
    private String breeder;
    private String breederPhone;
    private Double balance;

    @Override
    public String toString() {
        return "PetTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", breed=" + breed +
                ", birthDay=" + birthDay +
                ", breeder='" + breeder + '\'' +
                ", breederPhone='" + breederPhone + '\'' +
                ", balance=" + balance +
                '}';
    }

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "breed")
    public Integer getBreed() {
        return breed;
    }

    public void setBreed(Integer breed) {
        this.breed = breed;
    }

    @Basic
    @Column(name = "birthDay")
    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    @Basic
    @Column(name = "breeder")
    public String getBreeder() {
        return breeder;
    }

    public void setBreeder(String breeder) {
        this.breeder = breeder;
    }

    @Basic
    @Column(name = "breederPhone")
    public String getBreederPhone() {
        return breederPhone;
    }

    public void setBreederPhone(String breederPhone) {
        this.breederPhone = breederPhone;
    }

    @Basic
    @Column(name = "balance")
    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PetTO petTO = (PetTO) o;
        return id == petTO.id &&
                Objects.equals(name, petTO.name) &&
                Objects.equals(breed, petTO.breed) &&
                Objects.equals(birthDay, petTO.birthDay) &&
                Objects.equals(breeder, petTO.breeder) &&
                Objects.equals(breederPhone, petTO.breederPhone) &&
                Objects.equals(balance, petTO.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, breed, birthDay, breeder, breederPhone, balance);
    }
}
