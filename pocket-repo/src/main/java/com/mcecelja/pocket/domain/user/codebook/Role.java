package com.mcecelja.pocket.domain.user.codebook;

import com.mcecelja.pocket.domain.AbstractCodeBookEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Role extends AbstractCodeBookEntity implements Serializable {
}
