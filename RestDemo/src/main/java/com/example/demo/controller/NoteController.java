package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Note;
import com.example.demo.repository.NoteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class NoteController {
	
	@Autowired
	NoteRepository noteRepository;

	/*
	 * @GetMapping("/notes") annotation is a short form of @RequestMapping(value="/notes", method=RequestMethod.GET).
	 * 
	 */
	//Get All Notes
	@GetMapping("/notes")
	public List<Note> getAllNotes(){
		return noteRepository.findAll();
	}
	
	
	//Create new Note
	@PostMapping ("/notes")
	public Note createNote(@Valid @RequestBody Note note){
		return noteRepository.save(note);
	}
	
	
	//Get a single note
	@GetMapping("/notes/{id}")
	public Note getNoteById(@PathVariable(value="id") Long noteId){
		return noteRepository.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
	}
	
	
	//Update a Note
	@PutMapping("/notes/{id}")
	public Note updateNote(@PathVariable(value ="id") Long noteId, @Valid @RequestBody Note noteDetails){
		Note note = noteRepository.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
		
		note.setTitle(noteDetails.getTitle());
		note.setContent(noteDetails.getContent());
		
		Note updatedNote = noteRepository.save(note);
		return updatedNote;
	}
	
	//Delete a note
	@DeleteMapping(value="notes/{id}")
	public String deleteNote(@PathVariable(value="id") Long noteId){
		Note note = noteRepository.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
		noteRepository.delete(note);
		return "note Id " + noteId + ": Note has been deleted";
	}
	
	/*public ResponseEntity<?> deleteNote(@PathVariable(value="id") Long noteId){
		Note note = noteRepository.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
		noteRepository.delete(note);
		return ResponseEntity.ok().build();
	}
	*/

}
