package com.notes.app.data;

import com.notes.app.data.model.Note;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DataSource {

    private Note[] notes;
    private Integer idSequence = 0;

    public DataSource(@Value("${data.max-records}") Integer maxRecord) {
        notes = new Note[maxRecord];
    }

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
