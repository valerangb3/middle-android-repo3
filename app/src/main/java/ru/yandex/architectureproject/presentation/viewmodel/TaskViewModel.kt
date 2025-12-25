package ru.yandex.architectureproject.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.yandex.architectureproject.domain.usecase.AddTaskUseCase
import ru.yandex.architectureproject.domain.usecase.CompleteTaskUseCase
import ru.yandex.architectureproject.domain.usecase.DeleteTaskUseCase
import ru.yandex.architectureproject.domain.usecase.GetAllTasksUseCase
import ru.yandex.architectureproject.domain.usecase.IncompleteTaskUseCase
import ru.yandex.architectureproject.presentation.state.TaskAction
import ru.yandex.architectureproject.presentation.state.TaskState

class TaskViewModel(
    private val addTaskUseCase: AddTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val getAllTasksUseCase: GetAllTasksUseCase,
    private val completeTaskUseCase: CompleteTaskUseCase,
    private val incompleteTaskUseCase: IncompleteTaskUseCase,
    private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _state = MutableStateFlow<TaskState>(TaskState.Loading)
    private val taskForDelete = mutableMapOf<Int, Job?>()
    val state: StateFlow<TaskState> = _state.asStateFlow()

    init {
        reduce(TaskAction.LoadTasks)
    }

    fun reduce(action: TaskAction) {
        viewModelScope.launch(context = ioDispatcher) {
            when (action) {
                is TaskAction.LoadTasks -> loadTasks()
                is TaskAction.AddTask -> addTaskUseCase(action.name)
                is TaskAction.DeleteTask -> {
                    deleteTaskUseCase(action.taskId)
                    taskForDelete.remove(action.taskId)
                }
                is TaskAction.UpdateTaskStatus -> {
                    if (action.complete) {
                        taskForDelete[action.taskId] = this.coroutineContext.job
                        completeTaskUseCase(action.taskId)
                    } else {
                        taskForDelete[action.taskId]?.cancel()
                        incompleteTaskUseCase(action.taskId)
                    }
                }
            }
        }
    }

    private suspend fun loadTasks() {
        withContext(context = ioDispatcher) {
            getAllTasksUseCase()
                .distinctUntilChanged()
                .onStart { _state.value = TaskState.Loading }
                .catch { e -> _state.value = TaskState.Error(e.message ?: "Ошибка загрузки") }
                .collect { tasks -> _state.value = TaskState.Loaded(tasks) }
        }
    }
}
