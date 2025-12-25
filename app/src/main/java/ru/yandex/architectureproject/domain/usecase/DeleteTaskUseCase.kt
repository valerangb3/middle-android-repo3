package ru.yandex.architectureproject.domain.usecase

import ru.yandex.architectureproject.data.repository.TaskRepositoryImpl

class DeleteTaskUseCase(
    private val repository: TaskRepositoryImpl,
) {
    suspend operator fun invoke(taskId: Int) {
        repository.deleteTask(taskId)
    }
}
