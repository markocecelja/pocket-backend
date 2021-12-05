package com.mcecelja.pocket;

import com.mcecelja.pocket.common.dto.authentication.LoginRequestDTO;
import com.mcecelja.pocket.common.exceptions.PocketException;
import com.mcecelja.pocket.common.utils.JsonSimpleHelper;
import com.mcecelja.pocket.security.jwt.JwtProperties;
import com.mcecelja.pocket.utils.ResponseMessage;
import org.codehaus.jackson.JsonNode;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PocketApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-IT.properties")
@Transactional
public abstract class PocketContextAwareIT {

	@Autowired
	protected JwtProperties jwtProperties;
	protected TestRestTemplate restTemplate = new TestRestTemplate();
	protected HttpHeaders headers = new HttpHeaders();
	protected DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
	@LocalServerPort
	private int port;

	public static Resource getUserFileResource(int mbSize) throws IOException {
		Path tempFile = Files.createTempFile("upload-test-file", ".txt");
		String content = "1";
		byte[] emptyFilePart = new byte[1024 * 1024 * mbSize];

		byte[] c = new byte[content.getBytes().length + emptyFilePart.length];
		System.arraycopy(content.getBytes(), 0, c, 0, content.getBytes().length);
		System.arraycopy(emptyFilePart, 0, c, content.getBytes().length, emptyFilePart.length);

		Files.write(tempFile, c);
		File file = tempFile.toFile();
		return new FileSystemResource(file);
	}

	protected String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

	protected void authenticateUser(String username, String password) {
		LoginRequestDTO body = LoginRequestDTO.builder().username(username).password(password).build();

		HttpEntity<LoginRequestDTO> entity = new HttpEntity<>(body, headers);

		ResponseEntity<ResponseMessage> response = restTemplate.exchange(
				createURLWithPort("/api/authentication/login"),
				HttpMethod.POST, entity, ResponseMessage.class);

		LinkedHashMap loginResponse = (LinkedHashMap) response.getBody().getPayload();
		headers.clear();
		headers.add(jwtProperties.getHeader(),"Bearer " + (String) loginResponse.get("jwt"));

	}

	protected <T> T getDTOObjectFromBody(ResponseEntity<String> response, Class<T> dTOClass) throws PocketException {
		ResponseMessage message = JsonSimpleHelper.deserialise(response.getBody(), ResponseMessage.class);


		JsonNode tree = JsonSimpleHelper.turnIntoJsonTree(response.getBody());

		T dTO = JsonSimpleHelper.deserialise(tree.get("payload"), dTOClass);
		return dTO;
	}

	protected <T> List<T> getListFromBody(ResponseEntity<String> response, Class<T> dTOClass) throws PocketException {
		JsonNode tree = JsonSimpleHelper.turnIntoJsonTree(response.getBody());

		JsonNode content = tree.get("payload");


		List<T> resultList = new ArrayList<>();
		for (int i = 0; i < content.size(); i++) {
			JsonNode node = content.get(i);
			resultList.add(JsonSimpleHelper.deserialise(node, dTOClass));
		}

		return resultList;

	}
}
