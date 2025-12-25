package ru.yandex.architectureproject.domain.usecase

import ru.yandex.architectureproject.data.repository.TaskRepositoryImpl

class IncompleteTaskUseCase(
    private val repository: TaskRepositoryImpl,
) {
    suspend operator fun invoke(taskId: Int) {
        repository.incompleteTask(taskId)
    }
}