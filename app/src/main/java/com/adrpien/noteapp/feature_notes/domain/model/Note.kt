package com.adrpien.noteapp.feature_notes.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.adrpien.noteapp.ui.theme.Purple200
import com.adrpien.noteapp.ui.theme.Purple500
import com.adrpien.noteapp.ui.theme.Purple700
import com.adrpien.noteapp.ui.theme.Teal200

@Entity
data class Note(
    val title: String,
    val description: String,
    val time: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val colorList = listOf(Purple200, Purple500, Purple700, Teal200)
    }
}

class InvalidNoteException(messege: String): Exception(messege){

}
