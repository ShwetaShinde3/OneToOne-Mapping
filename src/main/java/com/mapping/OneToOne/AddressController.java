package com.mapping.OneToOne;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
public class AddressController {
	@Autowired
	private AddressRepository repository;
	
	@PostMapping("")
	public Address createAddress(@RequestBody Address address) {
		return repository.save(address);
	}
	
	@GetMapping()
	public List<Address> getAllAdresses() {
	
		return repository.findAll();
	}
	
	@GetMapping("/{id}")
	public Address getAdressById(@PathVariable int id) {
		return repository.findById(id).orElse(null);
	}
	@PutMapping("/{id}")
	public Address updateAddress(@PathVariable int id,@RequestBody Address address) {
	  Address address2 = repository.findById(id).orElseThrow();
	  
	  if (address.getStreet()!=null) {
		address2.setStreet(address.getStreet());
	}
	  if(address.getCity()!=null) {
		  address2.setCity(address.getCity());
	  }
	  return repository.save(address2);
	}
	
	@DeleteMapping("/{id}")
	public void deleteAddress(@PathVariable int id) {
		repository.deleteById(id);
	}
	
	@GetMapping("/paginate/{pageNo}/{pageSize}")
	public List<Address> getPeginated(@PathVariable int pageNo,@PathVariable int pageSize) {
		 Page<Address> pages = repository.findAll(PageRequest.of(pageNo, pageSize));
		 return pages.hasContent()?pages.getContent():new ArrayList<>();
	}
	
	@GetMapping("/sort/{field}/{direction}")
	public List<Address> getAllSorted(@PathVariable String field,@PathVariable String direction) {
	   Sort.Direction direction2= direction.equalsIgnoreCase("desc")?Sort.Direction.DESC:Sort.Direction.ASC;
		return repository.findAll(Sort.by(direction2,field));
	}
	@GetMapping("/{pageNo}/{pageSize}/{field}/{direction}")
	public Page<Address> getPaginatedAndSorted(@PathVariable int pageNo,@PathVariable int pageSize ,@PathVariable String field,@PathVariable String direction) {
		PageRequest pages = PageRequest.of(pageNo, pageSize);
		Sort.Direction direction2 = direction.equalsIgnoreCase("desc")?Sort.Direction.DESC:Sort.Direction.ASC;
		return repository.findAll(pages.withSort(Sort.by(direction2,field)));
	}
	
	
	
}
