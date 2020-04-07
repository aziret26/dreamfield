class User {

  id
  name
  email
  type

  constructor(data) {
    if (!data) {
      throw new Error("Can not crete User from null")
    }

    this.id = data.id
    this.name = data.name
    this.email = data.email
    this.type = data.type
  }

  static fromResponse(value) {
    if (!value) {
      throw new Error("Can not crete User from null")
    }

  }

}

const userTypes = {
  admin: "ADMIN",
  player: "PLAYER"
}

const guessResultStatus = {
  progress: "PROGRESS",
  success: "SUCCESS",
  failed: "FAILED"
}

class PositionLetterPair {
  position
  letter

  constructor({position, letter}) {
    this.position = position
    this.letter = letter
  }

  compare = (object) => {
    if (this.position > object.position) {
      return 1
    }
    if (this.position < object.position) {
      return -1
    }

    return 0
  }
}

class GuessResult {
  lettersContained
  lettersNotContained
  status

  constructor({lettersContained, lettersNotContained, status}) {
    this.setLettersContained(lettersContained)
    this.lettersNotContained = lettersNotContained
    this.status = status
  }

  setLettersContained = (lettersContained) => {
    this.lettersContained = []
    if (!lettersContained) {
      return
    }
    lettersContained.forEach((item) => {
      this.lettersContained.push(new PositionLetterPair({position: item.position, letter: item.letter}))
    })
  }

}

class GuessWord {
  description
  length
  maxScores
  availableScore
  guessedLetters

  guessResult

  constructor({description, length, maxScores, availableScore, guessResult = {}}) {
    this.guessResult = new GuessResult(guessResult || {})
    this.guessedLetters = this.guessResult.lettersContained || []
    this.description = description
    this.length = length
    this.maxScores = maxScores
    this.availableScore = availableScore
  }

}
