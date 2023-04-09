package com.adrpien.noteapp.feature_notes.domain.use_case

import com.adrpien.noteapp.feature_notes.domain.model.Note
import com.adrpien.noteapp.feature_notes.domain.repository.NoteRepository
import com.adrpien.noteapp.feature_notes.domain.util.OrderType
import com.adrpien.noteapp.feature_notes.domain.util.SortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetNotes(
    private val repository: NoteRepository
) {

    operator fun invoke (sortType: SortType): Flow<List<Note>>{
        return repository.getAllNotes().map { noteList ->
            when(sortType.orderType) {
                is OrderType.Ascending  -> {
                    when(sortType){
                        is SortType.Color -> { noteList.sortedBy { it.color }}
                        is SortType.Time -> { noteList.sortedBy { it.time }}
                        is SortType.Title -> { noteList.sortedBy { it.title }}
                    }
                }
                is OrderType.Descending -> {
                    when(sortType){
                        is SortType.Color -> { noteList.sortedByDescending { it.color }}
                        is SortType.Time -> { noteList.sortedByDescending { it.time }}
                        is SortType.Title -> { noteList.sortedByDescending { it.title }}
                    }
                }
            }
        }
    }

}