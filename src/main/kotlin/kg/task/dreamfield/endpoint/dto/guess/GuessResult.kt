package kg.task.dreamfield.endpoint.dto.guess


sealed class GuessResult {
    abstract val status: GuessResultStatus

}

data class GuessResultFailed(
        override val status: GuessResultStatus = GuessResultStatus.FAILED
) : GuessResult()

data class GuessResultInProgress(
        val lettersContained: MutableList<PositionLetterPair> = mutableListOf(),
        val lettersNotContained: MutableList<Char> = mutableListOf(),
        override var status: GuessResultStatus = GuessResultStatus.PROGRESS
) : GuessResult()


data class GuessResultFinished(
        val lettersContained: MutableList<PositionLetterPair> = mutableListOf(),
        val lettersNotContained: MutableList<Char> = mutableListOf(),
        override var status: GuessResultStatus = GuessResultStatus.FINISHED
) : GuessResult()
