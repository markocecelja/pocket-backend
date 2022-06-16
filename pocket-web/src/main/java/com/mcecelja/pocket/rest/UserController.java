package com.mcecelja.pocket.rest;

import com.mcecelja.pocket.common.dto.user.UserDTO;
import com.mcecelja.pocket.services.UserService;
import com.mcecelja.pocket.utils.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

	private final UserService userService;

	@GetMapping("/current")
	public ResponseEntity<ResponseMessage<UserDTO>> getCurrentUserInfo() {
		return ResponseEntity.ok(new ResponseMessage<>(
				userService.getCurrentUserInfo())
		);
	}
}
