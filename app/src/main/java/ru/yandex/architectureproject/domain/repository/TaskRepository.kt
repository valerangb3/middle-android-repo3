package ru.yandex.architectureproject.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.yandex.architectureproject.domain.model.Task

interface TaskRepository {
    fun getAllTasks(): Flow<List<Task>>

    suspend fun addTask(task: String)

    suspend fun completeTask(taskId: Int)

    suspend fun incompleteTask(taskId: Int)

    suspend fun deleteTask(taskId: Int)
}