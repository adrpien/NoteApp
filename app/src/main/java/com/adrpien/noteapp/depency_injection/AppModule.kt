package com.adrpien.noteapp.depency_injection

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.adrpien.noteapp.feature_notes.data.data_source.local.NoteDatabase
import com.adrpien.noteapp.feature_notes.data.data_source.local.NoteDatabase.Companion.DATABASE_NAME
import com.adrpien.noteapp.feature_notes.data.repository.NoteRepositoryImplementation
import com.adrpien.noteapp.feature_notes.domain.repository.NoteRepository
import com.adrpien.noteapp.feature_notes.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase{
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            DATABASE_NAME
        ).build()
    }


    @Provides
    @Singleton
    fun provideRepository(noteDatabase: NoteDatabase): NoteRepository {
        return NoteRepositoryImplementation(noteDatabase.dao )
    }


    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository)
        )
    }



}