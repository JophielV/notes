package com.notes.app.service;

import com.notes.app.model.DataSource;
import com.notes.app.model.Note;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NotesServiceImpl implements NotesService {

    private final DataSource dataSource;
    
    public NotesServiceImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Note> getAll() {
        return Arrays.stream(dataSource.getNotes()).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public Note get(Integer id) {
        return dataSource.getNotes()[id];
    }

    @Override
    public Note create(Note note) {
        Integer id = dataSource.getCurrentIdSequence();
        note.setId(id);
        dataSource.getNotes()[id] = note;
        dataSource.incrementIdSequence();
        return dataSource.getNotes()[id];
    }

    @Override
    public Note update(Integer id, Note note) {
        note.setId(id);
        dataSource.getNotes()[id] = note;
        return dataSource.getNotes()[id];
    }

    @Override
    public void delete(Integer id) {
        dataSource.getNotes()[id] = null;
    }
}
