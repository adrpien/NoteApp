package com.adrpien.noteapp.feature_notes.domain.util

sealed class SortType(val orderType: OrderType) {
    class Title(orderType: OrderType): SortType(orderType)
    class Color(orderType: OrderType): SortType(orderType)
    class Time(orderType: OrderType): SortType(orderType)

    fun changeOrderType(orderType: OrderType) : SortType {
        return when(this) {
            is SortType.Title -> SortType.Title(orderType)
            is SortType.Time -> SortType.Time(orderType)
            is SortType.Color -> SortType.Color(orderType)
        }
    }
}
