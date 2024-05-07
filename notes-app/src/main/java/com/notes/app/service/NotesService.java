package com.notes.app.service;

import com.notes.app.data.model.Note;
import com.notes.app.data.dto.NoteUpsertDto;

import java.util.List;

public interface NotesService {

    List<Note> getAll();

    Note get(Integer id);

    NoteUpsertDto create(NoteUpsertDto noteUpsertDto);

    NoteUpsertDto update(Integer id, NoteUpsertDto noteUpsertDto);

    void delete (Integer id);

}
