package com.sample.todo.data.room.mapper

import com.sample.todo.data.Mapper
import com.sample.todo.data.room.entity.TaskEntity
import com.sample.todo.data.core.DataScope
import com.sample.todo.domain.model.Task
import javax.inject.Inject

@DataScope
class TaskMapper @Inject constructor() : Mapper<Task, TaskEntity> {
    override fun map(from: Task): TaskEntity {
        return TaskEntity(
            id = from.id,
            title = from.title,
            description = from.description,
            isCompleted = from.isCompleted
        )
    }
}