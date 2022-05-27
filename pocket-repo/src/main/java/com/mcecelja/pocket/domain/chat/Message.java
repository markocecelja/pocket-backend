package com.mcecelja.pocket.domain.chat;

import com.mcecelja.pocket.domain.AbstractBaseEntity;
import com.mcecelja.pocket.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message extends AbstractBaseEntity {

	@Id
	@GeneratedValue(generator = "message_sequence", strategy = GenerationType.SEQUENCE)
	@GenericGenerator(
			name = "message_sequence",
			strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
			parameters = {
					@Parameter(name = "sequence_name", value = "message_id_seq"),
			}
	)
	private Long id;

	@Column(columnDefinition = "TEXT")
	private String text;

	@ManyToOne
	private User createdBy;

	@ManyToOne
	private Chat chat;
}
