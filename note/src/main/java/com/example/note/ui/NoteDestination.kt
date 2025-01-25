package com.example.note.ui

import leegroup.module.compose.ui.models.BaseDestination

sealed class NoteDestination {
    object NoteScreen : BaseDestination("noteScreen")
}
