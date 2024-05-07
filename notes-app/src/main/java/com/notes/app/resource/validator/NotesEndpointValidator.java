package com.notes.app.resource.validator;

import com.notes.app.data.model.Note;
import com.notes.app.data.dto.NoteUpsertDto;
import com.notes.app.data.dto.ServiceResponseDto;
import com.notes.app.service.NotesService;
import com.notes.app.utils.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class NotesEndpointValidator {

    private final NotesService noteService;
    private final StringUtils stringUtils;

    public NotesEndpointValidator(NotesService notesService,
                                  StringUtils stringUtils) {
        this.noteService = notesService;
        this.stringUtils = stringUtils;
    }

    public ServiceResponseDto<NoteUpsertDto> validateCreateorUpdate(NoteUpsertDto noteUpsertDto) {
        ServiceResponseDto<NoteUpsertDto> serviceResponse = new ServiceResponseDto<>();

        if (stringUtils.isNullOrEmpty(noteUpsertDto.getTitle())) {
            serviceResponse.setValid(false);
            serviceResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            serviceResponse.setMessage("Title cannot be empty!");
        }

        return serviceResponse;
    }

    public ServiceResponseDto<NoteUpsertDto> validateUpdate(NoteUpsertDto noteUpsertDto, String id) {
        ServiceResponseDto<NoteUpsertDto> serviceResponse = new ServiceResponseDto<>();

        serviceResponse = this.validateId(serviceResponse, id);

        if (!serviceResponse.isValid()) {
            return serviceResponse;
        }

        serviceResponse = this.validateCreateorUpdate(noteUpsertDto);

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

    public ServiceResponseDto<String> validateDelete(String id) {
        ServiceResponseDto<String> serviceResponse = new ServiceResponseDto<>();

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


    public ServiceResponseDto validateId(ServiceResponseDto serviceResponse, String id) {
        if (!stringUtils.isNumeric(id)) {
            serviceResponse.setValid(false);
            serviceResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            serviceResponse.setMessage("Only numeric id accepted!");
        }

        return serviceResponse;
    }

}
