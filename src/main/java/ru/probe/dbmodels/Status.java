package ru.probe.dbmodels;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import ru.probe.domain.StatusesEnum;

import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "statuses")
public class Status {

    @Id
    @JoinColumn(
            name = "status_guid",
            unique = true,
            nullable = false,
            updatable = false
    )
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
    private String statusGuid;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "status_name",
            unique = true,
            nullable = false
    )
    private StatusesEnum statusName;
    
    @Override
    public boolean equals(Object o) {
    	if (this == o)
    		return true;
    	
        if (o == null || getClass() != o.getClass())
        	return false;
        
        Status status = (Status) o;
        return Objects.equals(this.statusGuid, status.getStatusGuid());
    }
    
    @Override
    public int hashCode() {
    	return Objects.hashCode(this.statusGuid);
    }

    public String getStatusGuid() {
        return statusGuid;
    }

    public void setStatusGuid(String statusGuid) {
        this.statusGuid = statusGuid;
    }

    public StatusesEnum getStatusName() {
        return statusName;
    }

    public void setStatusName(StatusesEnum statusName) {
        this.statusName = statusName;
    }
}
