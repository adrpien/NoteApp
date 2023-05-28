package com.adrpien.noteapp.feature_notes.presentation.notes.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adrpien.noteapp.feature_notes.domain.util.OrderType
import com.adrpien.noteapp.feature_notes.domain.util.SortType

@Composable
fun SortSection(
    modifier: Modifier = Modifier,
    sortType: SortType = SortType.Time(OrderType.Descending),
    onSortChange: (SortType) -> Unit,
) {

    Column(
        modifier = modifier
    ) {
        Row(Modifier.fillMaxWidth()) {
            SortRadioButton(
                title = "Title",
                selected = sortType is SortType.Title,
                onClick = { onSortChange(SortType.Title(sortType.orderType)) })
            SortRadioButton(
                title = "Time",
                selected = sortType is SortType.Time,
                onClick = { onSortChange(SortType.Time(sortType.orderType)) })
            SortRadioButton(
                title = "Color",
                selected = sortType is SortType.Color,
                onClick = { onSortChange(SortType.Color(sortType.orderType)) })
        }
        Spacer(modifier = Modifier.width(8.dp))
        Row(Modifier.fillMaxWidth()) {
            SortRadioButton(
                title = "Descending",
                selected = sortType.orderType is OrderType.Descending,
                onClick = { onSortChange(sortType.changeOrderType(OrderType.Descending)) })
            SortRadioButton(
                title = "Ascending",
                selected = sortType.orderType is OrderType.Ascending,
                onClick = { onSortChange(sortType.changeOrderType(OrderType.Ascending)) })
        }
    }

}