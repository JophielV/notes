package com.notes.app.resource.validator;

import com.notes.app.model.Note;
import com.notes.app.model.NoteUpsertDto;
import com.notes.app.model.ServiceResponse;
import com.notes.app.service.NotesService;
import com.notes.app.utils.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class NotesResourceValidator {

    private final NotesService noteService;
    private final StringUtils stringUtils;

    public NotesResourceValidator(NotesService notesService,
                                  StringUtils stringUtils) {
        this.noteService = notesService;
        this.stringUtils = stringUtils;
    }

    public ServiceResponse<NoteUpsertDto> validateCreateorUpdate(NoteUpsertDto noteUpsertDto) {
        ServiceResponse<NoteUpsertDto> serviceResponse = new ServiceResponse<>();

        if (stringUtils.isNullOrEmpty(noteUpsertDto.getTitle())) {
            serviceResponse.setValid(false);
            serviceResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            serviceResponse.setMessage("Title cannot be empty!");
        }

        return serviceResponse;
    }

    public ServiceResponse<NoteUpsertDto> validateUpdate(NoteUpsertDto noteUpsertDto, String id) {
        ServiceResponse<NoteUpsertDto> serviceResponse = new ServiceResponse<>();

        serviceResponse = this.validateId(serviceResponse, id);

        if (!serviceResponse.isValid()) {
            return serviceResponse;
        }

        serviceResponse = this.validateCreateorUpdate(noteUpsertDto);

        return serviceResponse;
    }

    public ServiceResponse<String> validateDelete(String id) {
        ServiceResponse<String> serviceResponse = new ServiceResponse<>();

        serviceResponse = this.validateId(serviceResponse, id);

        if (!serviceResponse.isValid()) {
            return serviceResponse;
        }

        Note note = noteService.get(Integer.valueOf(id));

        if (note == null) {
            serviceResponse.setValid(false);
            serviceResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            serviceResponse.setMessage("No note found with id: " + id);
        }

        return serviceResponse;
    }


    public ServiceResponse validateId(ServiceResponse serviceResponse, String id) {
        if (!stringUtils.isNumeric(id)) {
            serviceResponse.setValid(false);
            serviceResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            serviceResponse.setMessage("Only numeric id accepted!");
        }

        return serviceResponse;
    }

}
