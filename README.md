# how to run notes app

1. Make sure Java 11 is installed on your machine and environment variables are set
2. Pull/clone this repository on your preferred directory
3. Navigate to the directory in command line/terminal where the file is cloned/pull
4. Enter 'ls' (for linux) or 'dir' (for windows) and see if you can see this file name: **notes-app-1.0-SNAPSHOT.jar**
5. Run command: java -jar notes-app-1.0-SNAPSHOT.jar
6. If you are able to find message 'Started Main' in the logs then the app is running
7. You can access the UI for using API in: http://localhost:8080/swagger-ui/index.html#
8. Click **notes-endpoint-impl** to toggle API operations. Documentations are also found in the swagger UI but you can find the same documentation below.


| Operation         | Description                     | PathVariable parameter                           | RequestBody                                      | Response Code                                                                                                          
|-------------------|---------------------------------|--------------------------------------------------|--------------------------------------------------|------------------------------------------------------------------------------------------------------------------------|
| `getAll (GET)`    | Gets all notes                  |                                                  |                                                  | Response 200 (Sucess)                                                                                                  |
| `get (GET)`       | Gets a specific note by id      | `"id" - id of the note to be retrieved`          |                                                  | Response 200 (Sucess), 400 (Bad Request) - Invalid ID, 404 (Not Found) - No note with the id found                     |
| `create (POST)`   | Creates a new note              |                                                  | Note object - fields are 'title' and 'body'      | Response 200 (Sucess), 400 (Bad Request) - Title is missing                                                            |
| `update (PUT)`    | Updates an existing note by id  | `"id" - id of the note that needs to be updated` | Note object - fields are 'title' and 'body'      | Response 200 (Sucess), 400 (Bad Request) - Title is missing or Invalid ID, 404 (Not Found) - No note with the id found |
| `delete (DELETE)` | Deletes a note by id            | `"id" - id of the note that needs to be deleted` |                                                  | Response 200 (Sucess), 400 (Bad Request) - Invalid ID, 404 (Not Found) - No note with the id found                     |


Note object: 
Note {
String title;
String body;
}

Sample CREATE request:
{
"body": "string",
"title": "string"
}