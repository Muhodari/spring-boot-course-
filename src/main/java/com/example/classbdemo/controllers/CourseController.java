package com.example.classbdemo.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.classbdemo.model.Course;
import com.example.classbdemo.repositories.CourseRepository;

@RestController
@RequestMapping("/courses")
public class CourseController {
	
	@Autowired
	private CourseRepository courseRepository;
	
	@GetMapping
	public List<Course> getAll() {
		
		return courseRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable(value="id") Long id) {
		
		Optional<Course> course = courseRepository.findById(id);
		if(course.isPresent()) {
			return ResponseEntity.ok(course.get()) ;
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Course());
		
	}
	
	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody Course course){
	
		if(courseRepository.existsByNameAndCode(course.getName(), course.getCode())) {
			return ResponseEntity.status(HttpStatus.CREATED).body(courseRepository.findByNameAndCode(course.getName(), course.getCode()).get());
		}
		courseRepository.save(course);
		 
		return ResponseEntity.status(HttpStatus.CREATED).body(course);
	}

}
