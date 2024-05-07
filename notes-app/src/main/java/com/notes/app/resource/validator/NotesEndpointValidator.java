package com.notes.app.resource.validator;

import com.notes.app.data.dto.NoteDto;
import com.notes.app.data.dto.NoteUpsertDto;
import com.notes.app.data.dto.ServiceResponseDto;
import com.notes.app.service.NotesService;
import com.notes.app.utils.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class NotesEndpointValidator {

    private final Integer maxRecord;
    private final NotesService noteService;
    private final StringUtils stringUtils;

    public NotesEndpointValidator(@Value("${data.max-records}") Integer maxRecord,
                                  NotesService notesService,
                                  StringUtils stringUtils) {
        this.maxRecord = maxRecord;
        this.noteService = notesService;
        this.stringUtils = stringUtils;
    }

    public ServiceResponseDto<NoteDto> validateCreateOrUpdate(NoteUpsertDto noteUpsertDto) {
        ServiceResponseDto<NoteDto> serviceResponse = new ServiceResponseDto<>();

        if (stringUtils.isNullOrEmpty(noteUpsertDto.getTitle())) {
            serviceResponse.setValid(false);
            serviceResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            serviceResponse.setMessage("Title cannot be empty!");
        }

        return serviceResponse;
    }

    public ServiceResponseDto<NoteDto> validateUpdate(NoteUpsertDto noteUpsertDto, String id) {
        ServiceResponseDto<NoteDto> serviceResponse = this.validateId(id);

        if (!serviceResponse.isValid()) {
            return serviceResponse;
        }

        serviceResponse = this.validateCreateOrUpdate(noteUpsertDto);

        if (!serviceResponse.isValid()) {
            return serviceResponse;
        }

        NoteDto note = noteService.get(Integer.valueOf(id));
        if (note == null) {
            serviceResponse.setValid(false);
            serviceResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            serviceResponse.setMessage("No note found with id: " + id);
        }

        return serviceResponse;
    }

    public ServiceResponseDto<NoteDto> validateGetOrDelete(String id) {
        ServiceResponseDto<NoteDto> serviceResponse = this.validateId(id);

        if (!serviceResponse.isValid()) {
            return serviceResponse;
        }

        NoteDto note = noteService.get(Integer.valueOf(id));

        if (note == null) {
            serviceResponse.setValid(false);
            serviceResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            serviceResponse.setMessage("No note found with id: " + id);
        }

        return serviceResponse;
    }


    private ServiceResponseDto<NoteDto> validateId(String id) {
        ServiceResponseDto<NoteDto> serviceResponse = new ServiceResponseDto<>();
        if (!stringUtils.isNumeric(id)) {
            serviceResponse.setValid(false);
            serviceResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            serviceResponse.setMessage("Only numeric id accepted!");
        }

        if (Integer.valueOf(id) > maxRecord) {
            serviceResponse.setValid(false);
            serviceResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            serviceResponse.setMessage("No note found with id: " + id);
        }

        return serviceResponse;
    }

}
