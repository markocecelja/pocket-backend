package com.mcecelja.pocket.domain.chat;

import com.mcecelja.pocket.domain.AbstractBaseEntity;
import com.mcecelja.pocket.domain.post.Post;
import com.mcecelja.pocket.domain.user.User;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Chat extends AbstractBaseEntity {

	@Id
	@GeneratedValue(generator = "chat_sequence", strategy = GenerationType.SEQUENCE)
	@GenericGenerator(
			name = "chat_sequence",
			strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
			parameters = {
					@Parameter(name = "sequence_name", value = "chat_id_seq"),
			}
	)
	private Long id;

	@ManyToOne
	private User user;

	@ManyToOne
	private Post post;

	@OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Message> messages;
}
