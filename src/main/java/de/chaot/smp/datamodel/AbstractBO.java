package de.chaot.smp.datamodel;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@MappedSuperclass
public abstract class AbstractBO {
	@Id
    @GeneratedValue
    private Long id;
}
