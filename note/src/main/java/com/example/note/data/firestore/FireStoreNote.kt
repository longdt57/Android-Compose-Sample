package com.example.note.data.firestore

import androidx.annotation.Keep
import com.example.note.data.models.NoteEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Keep
internal data class FireStoreNote(
    @SerialName(ID) val id: Long,
    @SerialName(CONTENT) val content: String,
    @SerialName(TIMESTAMP) val timestamp: Long,
) {

    /**
     * Don't remove this constructor. It causes exception when serializing.
     */
    constructor() : this(0, "", 0L)

    fun createHashMap(): HashMap<String, Any> {
        return hashMapOf(
            ID to id,
            CONTENT to content,
            TIMESTAMP to timestamp
        )
    }

    companion object {
        private const val ID = "id"
        private const val CONTENT = "content"
        private const val TIMESTAMP = "timestamp"
    }
}

internal fun FireStoreNote.mapToEntity() = NoteEntity(
    id = id,
    content = content,
    timestamp = timestamp,
    syncedCloud = true,
)

internal fun List<FireStoreNote>.mapToEntity() = map { it.mapToEntity() }

internal fun NoteEntity.mapToFireStoreNote() = FireStoreNote(
    id = id,
    content = content,
    timestamp = timestamp
)

internal fun List<NoteEntity>.mapToFireStoreNote() = map { it.mapToFireStoreNote() }
