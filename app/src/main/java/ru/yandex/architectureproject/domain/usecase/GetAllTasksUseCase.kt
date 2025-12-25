package ru.yandex.architectureproject.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.yandex.architectureproject.domain.model.Task
import ru.yandex.architectureproject.data.repository.TaskRepositoryImpl

class GetAllTasksUseCase(
    private val repository: TaskRepositoryImpl,
) {
    operator fun invoke(): Flow<List<Task>> {
        return repository.getAllTasks()
    }
}
