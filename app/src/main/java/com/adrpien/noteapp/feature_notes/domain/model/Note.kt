package com.adrpien.noteapp.feature_notes.domain.model

import androidx.room.Entity

@Entity
data class Note(
    var title: String,
    var description: String
)
