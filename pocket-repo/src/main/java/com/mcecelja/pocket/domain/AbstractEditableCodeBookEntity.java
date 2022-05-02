package com.mcecelja.pocket.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractEditableCodeBookEntity extends AbstractCodeBookEntity {

	private boolean active;
}
