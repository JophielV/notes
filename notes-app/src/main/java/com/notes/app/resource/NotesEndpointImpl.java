package com.notes.app.resource;

import com.notes.app.data.dto.NoteDto;
import com.notes.app.data.model.Note;
import com.notes.app.data.dto.NoteUpsertDto;
import com.notes.app.data.dto.ServiceResponseDto;
import com.notes.app.resource.validator.NotesEndpointValidator;
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

    private final NotesEndpointValidator notesResourceValidator;
    private final NotesService notesService;

    @Autowired
    public NotesEndpointImpl(NotesEndpointValidator notesResourceValidator,
                             NotesService notesService) {
        this.notesResourceValidator = notesResourceValidator;
        this.notesService = notesService;
    }

    @GetMapping
    public ResponseEntity<ServiceResponseDto<List<NoteDto>>> getAll() {
        return new ResponseEntity<>(new ServiceResponseDto<>(notesService.getAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponseDto<NoteDto>> get(@PathVariable String id) {
        ServiceResponseDto<NoteDto> serviceResponse = notesResourceValidator.validateGetOrDelete(id);

        if (!serviceResponse.isValid()) {
            return ResponseEntity.status(serviceResponse.getHttpStatus()).body(serviceResponse);
        }

        NoteDto note = notesService.get(Integer.valueOf(id));
        serviceResponse.setData(note);

        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ServiceResponseDto<NoteDto>> create(@RequestBody NoteUpsertDto noteUpsertDto) {
        ServiceResponseDto<NoteDto> serviceResponse = notesResourceValidator.validateCreateOrUpdate(noteUpsertDto);

        if (!serviceResponse.isValid()) {
            return ResponseEntity.status(serviceResponse.getHttpStatus()).body(serviceResponse);
        }

        NoteDto noteResponse = notesService.create(noteUpsertDto);
        serviceResponse.setData(noteResponse);

        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponseDto<NoteDto>> update(@PathVariable String id, @RequestBody NoteUpsertDto noteUpsertDto) {
        ServiceResponseDto<NoteDto> serviceResponse = notesResourceValidator.validateUpdate(noteUpsertDto, id);

        if (!serviceResponse.isValid()) {
            return ResponseEntity.status(serviceResponse.getHttpStatus()).body(serviceResponse);
        }

        NoteDto noteResponse = notesService.update(Integer.valueOf(id), noteUpsertDto);
        serviceResponse.setData(noteResponse);

        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<ServiceResponseDto<NoteDto>> delete(@PathVariable String id) {
        ServiceResponseDto<NoteDto> serviceResponse = notesResourceValidator.validateGetOrDelete(id);

        if (!serviceResponse.isValid()) {
            return ResponseEntity.status(serviceResponse.getHttpStatus()).body(serviceResponse);
        }

        notesService.delete(Integer.valueOf(id));
        serviceResponse.setMessage("Notes with id " + id + " deleted successfully!");

        return ResponseEntity.ok(serviceResponse);
    }

}
