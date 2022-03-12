package com.mcecelja.pocket.domain.user;

import com.mcecelja.pocket.domain.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLogin extends AbstractBaseEntity implements Serializable {

	@Id
	@GeneratedValue(generator = "user_login_sequence", strategy = GenerationType.SEQUENCE)
	@GenericGenerator(
			name = "user_login_sequence",
			strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
			parameters = {
					@Parameter(name = "sequence_name", value = "user_login_id_seq"),
			}
	)
	private Long id;

	private String username;

	private String password;

	@OneToOne
	private User user;
}
