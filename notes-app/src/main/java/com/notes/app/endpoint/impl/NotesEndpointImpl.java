package com.notes.app.endpoint.impl;

import com.notes.app.data.dto.NoteDto;
import com.notes.app.data.dto.NoteUpsertDto;
import com.notes.app.data.dto.ServiceResponseDto;
import com.notes.app.endpoint.validator.NotesEndpointValidator;
import com.notes.app.service.NotesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestControllerEndpoint(id="1")
@RequestMapping(value = "/notes")
public class NotesEndpointImpl {

    private final NotesEndpointValidator notesResourceValidator;
    private final NotesService notesService;

    @Autowired
    public NotesEndpointImpl(NotesEndpointValidator notesResourceValidator,
                             NotesService notesService) {
        this.notesResourceValidator = notesResourceValidator;
        this.notesService = notesService;
    }

    @RequestMapping(value="/", method=RequestMethod.GET)
    public ResponseEntity<ServiceResponseDto<List<NoteDto>>> getAll() {
        return new ResponseEntity<>(new ServiceResponseDto<>(notesService.getAll()), HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<ServiceResponseDto<NoteDto>> get(@PathVariable String id) {
        ServiceResponseDto<NoteDto> serviceResponse = notesResourceValidator.validateGetOrDelete(id);

        if (!serviceResponse.isValid()) {
            return null;
        }

        NoteDto note = notesService.get(Integer.valueOf(id));
        serviceResponse.setData(note);

        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @RequestMapping(value="/", method=RequestMethod.POST)
    public ResponseEntity<ServiceResponseDto<NoteDto>> create(@RequestBody NoteUpsertDto noteUpsertDto) {
        ServiceResponseDto<NoteDto> serviceResponse = notesResourceValidator.validateCreateOrUpdate(noteUpsertDto);

        if (!serviceResponse.isValid()) {
            return null;
        }

        NoteDto noteResponse = notesService.create(noteUpsertDto);
        serviceResponse.setData(noteResponse);

        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<ServiceResponseDto<NoteDto>> update(@PathVariable String id, @RequestBody NoteUpsertDto noteUpsertDto) {
        ServiceResponseDto<NoteDto> serviceResponse = notesResourceValidator.validateUpdate(noteUpsertDto, id);

        if (!serviceResponse.isValid()) {
            return null;
        }

        NoteDto noteResponse = notesService.update(Integer.valueOf(id), noteUpsertDto);
        serviceResponse.setData(noteResponse);

        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<ServiceResponseDto<NoteDto>> delete(@PathVariable String id) {
        ServiceResponseDto<NoteDto> serviceResponse = notesResourceValidator.validateGetOrDelete(id);

        if (!serviceResponse.isValid()) {
            return null;
        }

        notesService.delete(Integer.valueOf(id));
        serviceResponse.setMessage("Notes with id " + id + " deleted successfully!");

        return null;
    }

}
