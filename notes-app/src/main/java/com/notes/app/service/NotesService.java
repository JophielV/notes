package com.notes.app.service;

import com.notes.app.data.dto.NoteDto;
import com.notes.app.data.dto.NoteUpsertDto;

import java.util.List;

public interface NotesService {

    List<NoteDto> getAll();

    NoteDto get(Integer id);

    NoteDto create(NoteUpsertDto noteUpsertDto);

    NoteDto update(Integer id, NoteUpsertDto noteUpsertDto);

    void delete (Integer id);

}
