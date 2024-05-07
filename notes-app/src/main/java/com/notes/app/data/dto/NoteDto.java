package com.notes.app.data.dto;

public class NoteDto extends NoteUpsertDto {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
