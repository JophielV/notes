package com.notes.app.resource.cache;

import com.notes.app.model.Note;
import com.notes.app.model.NoteUpsertDto;
import com.notes.app.service.NotesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/notes")
public class NotesResource {

    private final NotesService notesService;

    @Autowired
    public NotesResource(NotesService notesService) {
        this.notesService = notesService;
    }

    @GetMapping
    public ResponseEntity<List<Note>> getAll() {
        return new ResponseEntity<>(notesService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> get(@PathVariable String id) {
        return new ResponseEntity<>(notesService.get(Integer.valueOf(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<NoteUpsertDto> create(@RequestBody NoteUpsertDto noteUpsertDto) {
        return new ResponseEntity<>(notesService.create(noteUpsertDto), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteUpsertDto> update(@PathVariable String id, @RequestBody NoteUpsertDto noteUpsertDto) {
        return new ResponseEntity<>(notesService.update(Integer.valueOf(id), noteUpsertDto), HttpStatus.OK);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        notesService.delete(Integer.valueOf(id));
        return ResponseEntity.ok("Notes with id " + id + " deleted successfully!");
    }

}
