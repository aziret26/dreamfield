validateAuthenticated()
navigateToAdminDir()

const menuItems = [
  new MenuItem("player-main", "Главная", "/pages/player")
];

let header = new Header("#header")
header.setNavMenuContent(menuItems)

let currentGuessWord = new GuessWord({})

function setCurrentGuessWord(result) {
  let guessedLetters = currentGuessWord.guessedLetters || []
  currentGuessWord = new GuessWord(result)

  if (currentGuessWord.guessResult
    && currentGuessWord.guessResult.status !== guessResultStatus.success) {
    currentGuessWord.guessedLetters.concat(guessedLetters)
  }
}

function getGuessWordAsPlainText(guessWord) {
  let letters = currentGuessWord
    .guessResult
    .lettersContained
    .sort((a, b) => {
      return a.compare(b)
    })


  return letters.map((item) => {
    return item.letter
  }).join("").toLocaleUpperCase()
}

function showWordGuessAndHideRequestNext() {
  $("#guess-word").removeClass("hidden")
  $("#guess-word-request-section").addClass("hidden")
}

function hideWordGuessAndShowRequestNext() {
  $("#guess-word").addClass("hidden")
  $("#guess-word-request-section").removeClass("hidden")
}

function playerGuessedSuccess(guessWord) {
  console.log("succ")
  hideWordGuessAndShowRequestNext()
  let resultText = `
Поздравляем у вас получилось отгадать слово.</br>
Вы заработали ${guessWord.availableScore} баллов отгадав слово "${getGuessWordAsPlainText(guessWord)}"`
  $("#request-message").html(resultText)
  playerGuessedProgress(guessWord)
}

function playerGuessedFailed() {
  console.log("fail")
  hideWordGuessAndShowRequestNext()
  $("#request-message").html("У вас не получилось отгадать слово за выданное колличество попыток.")
}

function displayScore(guessWord) {
  $("#guess-word-score-value").html(`${guessWord.availableScore} / ${guessWord.maxScores}`)
}

function displayEmptyLetterCells(guessWord) {
  let html = ""

  for (let i = 0; i < guessWord.length; i++) {
    html += `<td id="letter-cell-${i}" class="letter-cell text-center">&nbsp;</td>`
  }

  $("#guess-word-show-row").html(html)
}

function playerGuessedProgress(guessWord) {
  displayEmptyLetterCells(guessWord)

  if (guessWord.guessedLetters) {
    let letters = guessWord.guessedLetters

    letters.forEach((item) => {
      $(`#letter-cell-${item.position}`).html(item.letter)
    })


  }

  if (guessWord.guessResult && guessWord.guessResult.status !== undefined && guessWord.guessResult.lettersContained.length === 0) {
    $("#guess-word-response-help").html("Ничего не найдено")
  } else {
    $("#guess-word-response-help").html("")
  }

  $("#guess-word-description").html(guessWord.description)
  displayScore(guessWord)
}

function processResponse(guessWord) {
  if (!guessWord.guessResult) {
    return
  }

  switch (guessWord.guessResult.status) {
    case guessResultStatus.failed:
      playerGuessedFailed(guessWord)
      break
    case guessResultStatus.success:
      playerGuessedSuccess(guessWord)
      break
    case guessResultStatus.progress:
      playerGuessedProgress(guessWord)
      break
    default:
      playerGuessedProgress(guessWord)
      break
  }
}

function onRequestSuccess(result) {
  setCurrentGuessWord(result)
  showWordGuessAndHideRequestNext()
  processResponse(currentGuessWord)
}

function getErrors(responseJSON) {
  let result = ""
  if (responseJSON.errors) {
    responseJSON.errors.forEach((e) => {
      result += `${e.defaultMessage}</br>`
    })
  }

  if (result === "") {
    result += `${responseJSON.message}</br>`
  }
  return result
}

function onRequestError(e) {
  $("#guess-word-result-failed").html(getErrors(e.responseJSON))
}

function requestNewNextGuessWord() {
  $("#guess-word-result-failed").html("")
  GuessWordApiRequestHelper.requestNextGuessWordUrl(onRequestSuccess, onRequestError)
}

function guessWord() {
  $("#guess-word-result-failed").html("")
  let guessedLetters = []
  currentGuessWord.guessedLetters.forEach((item) => {
    guessedLetters.push({letter: item.letter[0]})
  })

  let body = {
    word: $("#guess-word-input").val(),
    foundLetters: guessedLetters
  }
  $("#guess-word-input").val("")

  GuessWordApiRequestHelper.requestGuessWord({
    params: body,
    onSuccess: onRequestSuccess,
    onError: onRequestError
  })
}


$("body").on("keypress", "#guess-word-input", (e) => {
  if (e.which == 13) {
    guessWord()
  }
})

$("body").on("click", "#guess-word-btn", () => {
  guessWord()
})

$("body").on("click", "#request-next-word-btn", () => {
  requestNewNextGuessWord()
})

showWordGuessAndHideRequestNext()

requestNewNextGuessWord()