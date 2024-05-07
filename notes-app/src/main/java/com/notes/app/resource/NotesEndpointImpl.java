package com.notes.app.resource;

import com.notes.app.model.Note;
import com.notes.app.model.NoteUpsertDto;
import com.notes.app.model.ServiceResponse;
import com.notes.app.resource.validator.NotesResourceValidator;
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
public class NotesEndpointImpl implements NotesEndpoint {

    private final NotesResourceValidator notesResourceValidator;
    private final NotesService notesService;

    @Autowired
    public NotesEndpointImpl(NotesResourceValidator notesResourceValidator,
                             NotesService notesService) {
        this.notesResourceValidator = notesResourceValidator;
        this.notesService = notesService;
    }

    @GetMapping
    public ResponseEntity<List<Note>> getAll() {
        return new ResponseEntity<>(notesService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse<Note>> get(@PathVariable String id) {
        ServiceResponse<Note> serviceResponse = notesResourceValidator.validateId(new ServiceResponse<Note>(), id);

        if (!serviceResponse.isValid()) {
            return ResponseEntity.status(serviceResponse.getHttpStatus()).body(serviceResponse);
        }

        Note note = notesService.get(Integer.valueOf(id));
        serviceResponse.setData(note);

        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ServiceResponse<NoteUpsertDto>> create(@RequestBody NoteUpsertDto noteUpsertDto) {
        ServiceResponse<NoteUpsertDto> serviceResponse = notesResourceValidator.validateCreateorUpdate(noteUpsertDto);

        if (!serviceResponse.isValid()) {
            return ResponseEntity.status(serviceResponse.getHttpStatus()).body(serviceResponse);
        }

        NoteUpsertDto noteResponse = notesService.create(noteUpsertDto);
        serviceResponse.setData(noteResponse);

        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponse<NoteUpsertDto>> update(@PathVariable String id, @RequestBody NoteUpsertDto noteUpsertDto) {
        ServiceResponse<NoteUpsertDto> serviceResponse = notesResourceValidator.validateUpdate(noteUpsertDto, id);

        if (!serviceResponse.isValid()) {
            return ResponseEntity.status(serviceResponse.getHttpStatus()).body(serviceResponse);
        }

        NoteUpsertDto noteResponse = notesService.update(Integer.valueOf(id), noteUpsertDto);
        serviceResponse.setData(noteResponse);

        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<ServiceResponse<String>> delete(@PathVariable String id) {
        ServiceResponse<String> serviceResponse = notesResourceValidator.validateDelete(id);

        if (!serviceResponse.isValid()) {
            return ResponseEntity.status(serviceResponse.getHttpStatus()).body(serviceResponse);
        }

        notesService.delete(Integer.valueOf(id));
        serviceResponse.setMessage("Notes with id " + id + " deleted successfully!");

        return ResponseEntity.ok(serviceResponse);
    }

}
