package ru.yandex.architectureproject.domain.usecase

import kotlinx.coroutines.delay
import ru.yandex.architectureproject.data.repository.TaskRepositoryImpl

private const val DELAY_DELETE = 10_000L

class CompleteTaskUseCase(
    private val repository: TaskRepositoryImpl,
) {

    suspend operator fun invoke(taskId: Int) {
        repository.completeTask(taskId)
        delay(DELAY_DELETE)
        repository.deleteTask(taskId = taskId)
    }
}
