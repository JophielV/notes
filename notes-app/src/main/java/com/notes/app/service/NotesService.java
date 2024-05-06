package com.notes.app.service;

import com.notes.app.model.Note;

import java.util.List;

public interface NotesService {

    List<Note> getAll();

    Note get(Integer id);

    Note create(Note note);

    Note update(Integer id, Note note);

    void delete (Integer id);

}
