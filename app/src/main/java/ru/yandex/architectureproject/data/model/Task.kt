package ru.yandex.architectureproject.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.yandex.architectureproject.domain.model.Task as DomainTask


@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val text: String,
    val isDone: Boolean = false
)

fun Task.map() = DomainTask(
    id = this.id,
    name = this.text,
    complete = this.isDone
)
