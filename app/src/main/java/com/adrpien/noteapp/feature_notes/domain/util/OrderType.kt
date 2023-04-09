package com.adrpien.noteapp.feature_notes.domain.util

sealed class OrderType(){
    object Descending: OrderType()
    object Ascending: OrderType()
}
