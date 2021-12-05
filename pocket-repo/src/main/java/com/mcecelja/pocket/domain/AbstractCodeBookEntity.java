package com.mcecelja.pocket.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractCodeBookEntity extends AbstractBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AbstractCodeBookEntity entity = (AbstractCodeBookEntity) o;
		return Objects.equals(getId(), entity.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

}
