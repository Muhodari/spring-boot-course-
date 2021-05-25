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

import com.example.classbdemo.dto.CreateMarkDTO;
import com.example.classbdemo.model.Course;
import com.example.classbdemo.model.Mark;
import com.example.classbdemo.model.Student;
import com.example.classbdemo.repositories.CourseRepository;
import com.example.classbdemo.repositories.MarkRepository;
import com.example.classbdemo.repositories.StudentRepository;
import com.example.classbdemo.services.IMarkServices;
import com.example.classbdemo.utils.APIResponse;

@RestController
@RequestMapping("/marks")
public class MarkController {
	
	
	@Autowired
	private MarkRepository markRepository;
	
	@Autowired
	private StudentRepository StudentRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private IMarkServices markService;

	@GetMapping
	public List<Mark> getAll() {
		
		return markRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable(value="id") Long id) {
		
		Optional<Mark> mark = markRepository.findById(id);
		if(mark.isPresent()) {
			return ResponseEntity.ok(mark.get()) ;
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Course());
		
	}
	
	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody CreateMarkDTO dto){
	
		Optional<Course> course = courseRepository.findById(dto.getCourseId());
		if(!course.isPresent()) {
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIResponse("Course not found", false));
		}
		Optional<Student> student = StudentRepository.findById(dto.getStudentId());
		
		if(!student.isPresent()) {
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIResponse("Student not found", false));
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(markService.save(dto, student.get(), course.get()));
	}
}
