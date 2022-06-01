package de.weconvert.restspringbootexample.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import de.weconvert.restspringbootexample.config.TrackExecutionTime;

@RestController
public class UserResource {

	@Autowired
	MessageSource messageSource;

	@Autowired
	private UserDaoService service;

	@GetMapping("/users")
	@TrackExecutionTime
	public List<User> retrieveAllUsers() {
		return service.findAll();
	}
	
	@GetMapping("/users/with-delay")
	@TrackExecutionTime
	public List<User> longRunRetrieveAllUsers() {
		try {
			Thread.sleep(new Random().nextInt(100) * 10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return service.findAll();
	}
	

	@GetMapping("/users/{id}")
	@TrackExecutionTime
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		User user = service.findOne(id);
		if (user == null) {
			throw new UserNotFoundException("id-" + id);
		}

		EntityModel<User> model = EntityModel.of(user);

		WebMvcLinkBuilder linkToUsers = linkTo(methodOn(this.getClass()).retrieveAllUsers());

		model.add(linkToUsers.withRel("all-users"));

		return model;
	}

	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = service.save(user);

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedUser.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		User user = service.deleteById(id);

		if (user == null) {
			throw new UserNotFoundException("id-" + id);
		}
	}

	@GetMapping("/users/internationalized-test")
	public String internationalizedTest() {
		return messageSource.getMessage("good.morning.message", null,
				LocaleContextHolder.getLocale());
	}

}
