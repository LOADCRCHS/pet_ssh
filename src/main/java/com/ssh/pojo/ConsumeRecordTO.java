package com.ssh.pojo;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "consume_record", schema = "pet_db", catalog = "")
public class ConsumeRecordTO {
    private int id;
    private Integer petId;
    private Timestamp consumeDate;
    private Double consumeMoney;
    private String remark;
    private Double balance;
    private Integer consumeType;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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
    @Column(name = "petId")
    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    @Basic
    @Column(name = "consumeDate")
    public Timestamp getConsumeDate() {
        return consumeDate;
    }

    public void setConsumeDate(Timestamp consumeDate) {
        this.consumeDate = consumeDate;
    }

    @Basic
    @Column(name = "consumeMoney")
    public Double getConsumeMoney() {
        return consumeMoney;
    }

    public void setConsumeMoney(Double consumeMoney) {
        this.consumeMoney = consumeMoney;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "balance")
    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Basic
    @Column(name = "consumeType")
    public Integer getConsumeType() {
        return consumeType;
    }

    public void setConsumeType(Integer consumeType) {
        this.consumeType = consumeType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsumeRecordTO that = (ConsumeRecordTO) o;
        return id == that.id &&
                Objects.equals(petId, that.petId) &&
                Objects.equals(consumeDate, that.consumeDate) &&
                Objects.equals(consumeMoney, that.consumeMoney) &&
                Objects.equals(remark, that.remark) &&
                Objects.equals(balance, that.balance) &&
                Objects.equals(consumeType, that.consumeType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, petId, consumeDate, consumeMoney, remark, balance, consumeType);
    }
}
