package com.notes.app.data.model;

import com.notes.app.data.dto.NoteUpsertDto;

public class Note extends NoteUpsertDto {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
