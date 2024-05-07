package com.notes.app.endpoint;

import com.notes.app.data.dto.NoteDto;
import com.notes.app.data.dto.NoteUpsertDto;
import com.notes.app.data.dto.ServiceResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@Api(value = "/notes", description = "Operations for notes")
public interface NotesEndpoint {

    @Operation(summary = "Gets all notes")
    @GetMapping
    ResponseEntity<ServiceResponseDto<List<NoteDto>>> getAll();

    @Operation(summary = "Gets a specific note by id")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "No note found with the ID supplied") })
    @GetMapping("/{id}")
    ResponseEntity<ServiceResponseDto<NoteDto>> get(@ApiParam(value = "ID of the note to be retrieved", required = true) @PathParam("id") String id);

    @Operation(summary = "Creates a new note")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Title is missing") })
    @PostMapping
    ResponseEntity<ServiceResponseDto<NoteDto>> create(@ApiParam(value = "Note object to be created", required = true) NoteUpsertDto note);

    @Operation(summary = "Updates an existing note by id")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID supplied or Title is missing"),
            @ApiResponse(code = 404, message = "No note found with the ID supplied")})
    @PutMapping("/{id}")
    ResponseEntity<ServiceResponseDto<NoteDto>> update(@ApiParam(value = "ID of the note that needs to be updated", required = true) @PathParam("id") String id,
                                                             @ApiParam(value = "Note object with updated values", required = true) NoteUpsertDto note);

    @Operation(summary = "Deletes a note by id")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "No note found with the ID supplied") })
    @DeleteMapping ("/{id}")
    ResponseEntity<ServiceResponseDto<NoteDto>> delete(@ApiParam(value = "ID of the note that needs to be deleted", required = true) @PathParam("id") String id);

}
