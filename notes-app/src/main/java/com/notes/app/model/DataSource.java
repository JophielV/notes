package com.notes.app.model;

import org.springframework.stereotype.Component;

@Component
public class DataSource {

    private Note[] notes = new Note[99];
    private Integer idSequence = 0;

    public void incrementIdSequence() {
        this.idSequence++;
    }

    public Integer getCurrentIdSequence() {
        return this.idSequence;
    }

    public Note[] getNotes() {
        return this.notes;
    }

}
