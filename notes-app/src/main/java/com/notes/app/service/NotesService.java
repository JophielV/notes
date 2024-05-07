package com.notes.app.service;

import com.notes.app.data.dto.NoteDto;
import com.notes.app.data.dto.NoteUpsertDto;

import java.util.List;

public interface NotesService {

    List<NoteDto> getAll();

    NoteDto get(Integer id);

    NoteUpsertDto create(NoteUpsertDto noteUpsertDto);

    NoteUpsertDto update(Integer id, NoteUpsertDto noteUpsertDto);

    void delete (Integer id);

}
