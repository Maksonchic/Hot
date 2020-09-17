package ru.probe.dbmodels;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.Objects;

import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "units")
public class Unit {
	
    @Id
    @Column(name = "unit_guid", unique = true, nullable = false)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    private String unitGuid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "unit_status", nullable = false)
    private Status unitStatus;

    @Column(name = "book_from", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date bookFrom;

    @Column(name = "book_to", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date bookTo;

    @Column(name = "room_num", nullable = false)
    @Min(value = 1, message = "so small number")
    private int number;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unit unit = (Unit) o;
        return Objects.equals(unitGuid, unit.unitGuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unitGuid);
    }


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getBookFrom() {
        return bookFrom;
    }

    public void setBookFrom(Date bookFrom) {
        this.bookFrom = bookFrom;
    }

    public Date getBookTo() {
        return bookTo;
    }

    public void setBookTo(Date bookTo) {
        this.bookTo = bookTo;
    }

    public String getUnitGuid() {
        return unitGuid;
    }

    public void setUnitGuid(String unitGuid) {
        this.unitGuid = unitGuid;
    }

    public Status getUnitStatus() {
        return unitStatus;
    }

    public void setUnitStatus(Status unitStatus) {
        this.unitStatus = unitStatus;
    }
}
