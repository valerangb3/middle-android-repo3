package ru.yandex.architectureproject.domain.usecase

import ru.yandex.architectureproject.data.repository.TaskRepositoryImpl

class AddTaskUseCase(
    private val repository: TaskRepositoryImpl,
) {
    suspend operator fun invoke(task: String) {
        repository.addTask(task)
    }
}
