package ru.yandex.architectureproject.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.yandex.architectureproject.data.db.TaskDao
import ru.yandex.architectureproject.domain.model.Task as DomainTask
import ru.yandex.architectureproject.data.model.Task
import ru.yandex.architectureproject.data.model.map
import ru.yandex.architectureproject.domain.repository.TaskRepository

class TaskRepositoryImpl(private val taskDao: TaskDao) : TaskRepository {
    override fun getAllTasks(): Flow<List<DomainTask>> = taskDao
        .getAllTasks()
        .map { dbTaskItems -> dbTaskItems.map { item -> item.map() } }

    override suspend fun addTask(task: String) {
        taskDao.addTask(Task(text = task))
    }

    override suspend fun completeTask(taskId: Int) {
        taskDao.updateTaskStatus(taskId, true)
    }

    override suspend fun incompleteTask(taskId: Int) {
        taskDao.updateTaskStatus(taskId, false)
    }

    override suspend fun deleteTask(taskId: Int) {
        taskDao.deleteTask(taskId)
    }
}
