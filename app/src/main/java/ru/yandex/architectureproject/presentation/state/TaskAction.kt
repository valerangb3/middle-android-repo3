package ru.yandex.architectureproject.presentation.state

sealed class TaskAction {
    object LoadTasks : TaskAction()
    data class AddTask(val name: String) : TaskAction()
    data class UpdateTaskStatus(val taskId: Int, val complete: Boolean) : TaskAction()
    data class DeleteTask(val taskId: Int) : TaskAction()

}
