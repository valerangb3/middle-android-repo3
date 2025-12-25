package ru.yandex.architectureproject.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers
import ru.yandex.architectureproject.App
import ru.yandex.architectureproject.data.db.TaskDatabase
import ru.yandex.architectureproject.data.repository.TaskRepositoryImpl
import ru.yandex.architectureproject.domain.usecase.AddTaskUseCase
import ru.yandex.architectureproject.domain.usecase.DeleteTaskUseCase
import ru.yandex.architectureproject.domain.usecase.GetAllTasksUseCase
import ru.yandex.architectureproject.domain.usecase.CompleteTaskUseCase
import ru.yandex.architectureproject.domain.usecase.IncompleteTaskUseCase

class TaskViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val taskDao = TaskDatabase.getInstance(App.context).taskDao()
        val repository = TaskRepositoryImpl(taskDao)
        val addTaskUseCase = AddTaskUseCase(repository)
        val deleteTaskUseCase = DeleteTaskUseCase(repository)
        val completeTaskUseCase = CompleteTaskUseCase(repository)
        val incompleteTaskUseCase = IncompleteTaskUseCase(repository)
        val getAllTasksUseCase = GetAllTasksUseCase(repository)
        val ioDispatcher = Dispatchers.IO
        return TaskViewModel(
            addTaskUseCase,
            deleteTaskUseCase,
            getAllTasksUseCase,
            completeTaskUseCase,
            incompleteTaskUseCase,
            ioDispatcher,
        ) as T
    }
}
