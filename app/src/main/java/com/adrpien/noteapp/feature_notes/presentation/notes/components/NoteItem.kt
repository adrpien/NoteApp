package com.adrpien.noteapp.feature_notes.presentation.notes.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.adrpien.noteapp.feature_notes.domain.model.Note

@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier,
    cornerRadius: Dp = 10.dp,
    cornerCutSize: Dp = 10.dp,
    onDeleteClick: () -> Unit
){
    Box(modifier = modifier){
        Canvas(modifier = Modifier.matchParentSize()) {
            val path = Path().apply {
                lineTo(size.width - cornerCutSize.toPx(), 0f)
                lineTo(size.width, cornerCutSize.toPx())
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }

            clipPath(path){
                drawRoundRect(
                    color = Color(note.color),
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
                drawRoundRect(
                    color = Color(ColorUtils.blendARGB(note.color, 0x000000, 0.2f)),
                    topLeft = Offset(size.width - cornerCutSize.toPx(), -100f),
                    size = Size(cornerCutSize.toPx() + 100f, cornerCutSize.toPx() + 100f),
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
            }
        }
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(end = 32.dp)
        ) {
            Text(
                text = note.title,
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.h6,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = note.description,
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.h6,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        IconButton(
            modifier = Modifier.align(Alignment.BottomEnd),
            onClick =  onDeleteClick ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete")
        }
        
        
        
    }
}