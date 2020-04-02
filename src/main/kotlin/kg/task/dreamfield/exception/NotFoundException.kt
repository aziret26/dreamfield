package kg.task.dreamfield.exception

import kotlin.reflect.KClass

class NotFoundException(
        override val message: String?
) : DreamfieldException(message) {
    constructor(kclass: KClass<*>,
                attribute: Any,
                attributeType: String = "identity") : this("Couldn't find ${kclass.simpleName} with $attributeType='$attribute'")

    constructor(objectType: KClass<*>,
                vararg attributes: Any,
                attributeType: String = "identities") : this("Failed to search ${objectType.simpleName} with $attributeType='[${attributes.joinToString(separator = ", ")}]'")
}