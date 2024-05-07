package com.notes.app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notes.app.data.DataSource;
import com.notes.app.data.model.Note;
import com.notes.app.data.dto.NoteUpsertDto;
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
    private final ObjectMapper objectMapper;
    
    public NotesServiceImpl(DataSource dataSource,
                            ObjectMapper objectMapper) {
        this.dataSource = dataSource;
        this.objectMapper = objectMapper;
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
    public NoteUpsertDto create(NoteUpsertDto noteUpsertDto) {
        Note note = objectMapper.convertValue(noteUpsertDto, Note.class);

        Integer id = dataSource.getCurrentIdSequence();
        note.setId(id);
        dataSource.getNotes()[id] = note;

        dataSource.incrementIdSequence();

        return dataSource.getNotes()[id];
    }

    @Override
    public NoteUpsertDto update(Integer id, NoteUpsertDto noteUpsertDto) {
        Note note = objectMapper.convertValue(noteUpsertDto, Note.class);
        note.setId(id);
        dataSource.getNotes()[id] = note;

        return dataSource.getNotes()[id];
    }

    @Override
    public void delete(Integer id) {
        dataSource.getNotes()[id] = null;
    }
}
