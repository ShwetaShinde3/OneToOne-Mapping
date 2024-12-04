package com.mapping.OneToOne;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
@Autowired
	UserRepository repository;
	
@GetMapping()
public List<User> getUser() {
	return repository.findAll();
}
@GetMapping("/{id}")
public User getUserById(@PathVariable int id) {
	return repository.findById(id).orElseThrow();
}

@PostMapping("")
public User addUser(@RequestBody User user) {
	return repository.save(user);
}

@DeleteMapping("/{id}")
public void removeUser(@PathVariable int id) {
	 repository.deleteById(id);
}

@PutMapping("/{id}")
public User updateUser(@PathVariable int id,@RequestBody User user) {
	User userById = repository.findById(id).orElseThrow();
	userById.setName(user.getName());
	userById.setAddress(user.getAddress());
 return	repository.save(userById);
}
@GetMapping("/paginate/{pageNo}/{pageSize}")
public List<User> getPaginated(@PathVariable int pageNo,@PathVariable int pageSize) {
	 Page<User> pages = repository.findAll(PageRequest.of(pageNo, pageSize));
	 return pages.hasContent()?pages.getContent():new ArrayList<>();
}

@GetMapping("/sort/{field}/{direction}")
public List<User> getAllSorted(@PathVariable String field,@PathVariable String direction) {
   Sort.Direction direction2= direction.equalsIgnoreCase("desc")?Sort.Direction.DESC:Sort.Direction.ASC;
	return repository.findAll(Sort.by(direction2,field));
}
@GetMapping("/{pageNo}/{pageSize}/{field}/{direction}")
public Page<User> getPaginatedAndSorted(@PathVariable int pageNo,@PathVariable int pageSize ,@PathVariable String field,@PathVariable String direction) {
	PageRequest pages = PageRequest.of(pageNo, pageSize);
	Sort.Direction direction2 = direction.equalsIgnoreCase("desc")?Sort.Direction.DESC:Sort.Direction.ASC;
	return repository.findAll(pages.withSort(Sort.by(direction2,field)));
}
}
