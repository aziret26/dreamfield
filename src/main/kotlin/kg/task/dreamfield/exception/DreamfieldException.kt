package kg.task.dreamfield.exception

open class DreamfieldException(
        override val message: String?,
        override val cause: Throwable? = null
) : Exception(message)