package com.notes.app.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notes.app.data.DataSource;
import com.notes.app.data.dto.NoteDto;
import com.notes.app.data.model.Note;
import com.notes.app.data.dto.NoteUpsertDto;
import com.notes.app.service.NotesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
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
    public List<NoteDto> getAll() {
        return Arrays.stream(
                dataSource.getNotes()).filter(note -> note != null && !note.isDeleted())
                .map(note -> objectMapper.convertValue(note, NoteDto.class))
                .collect(Collectors.toList()
                );
    }

    @Override
    public NoteDto get(Integer id) {
        Note note = dataSource.getNotes()[id];

        if (note == null || note.isDeleted()) return null;

        return objectMapper.convertValue(note, NoteDto.class);
    }

    @Override
    public NoteDto create(NoteUpsertDto noteUpsertDto) {
        Note note = objectMapper.convertValue(noteUpsertDto, Note.class);

        Integer id = dataSource.getCurrentIdSequence();
        note.setId(id);
        dataSource.getNotes()[id] = note;

        dataSource.incrementIdSequence();

        return objectMapper.convertValue(dataSource.getNotes()[id], NoteDto.class);
    }

    @Override
    public NoteDto update(Integer id, NoteUpsertDto noteUpsertDto) {
        Note note = objectMapper.convertValue(noteUpsertDto, Note.class);
        note.setId(id);
        dataSource.getNotes()[id] = note;

        return objectMapper.convertValue(dataSource.getNotes()[id], NoteDto.class);
    }

    @Override
    public void delete(Integer id) {
        // soft delete only
        Note note = dataSource.getNotes()[id];
        note.setDeleted(true);
    }
}
