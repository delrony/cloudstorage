package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES")
    Note[] getAllNotes();

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteid}")
    Note getNote(Integer noteid);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES (#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    Integer insert(Note note);

    @Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription=#{notedescription} WHERE noteid = #{noteid}")
    void update(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    void delete(Integer noteid);
}
