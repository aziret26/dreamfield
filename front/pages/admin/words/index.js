header.setActive("admin-words")

function displaySearchWordsResult(words, divId) {
  if (!words || words.length === 0) {
    return "<h3>Ничего не найдено</h3>"
  }

  let result = `
<table class="table">
    <tbody>
    <tr>
        <th>Id</th>
        <th>Слово</th>
        <th>Описание</th>
        <th>Баллы</th>
        <th>Статус видимости</th>
    </tr>
`
  words.forEach((p) => {
    let [selectedVisible, s] = p.status == "VISIBLE" ? ["selected"] : [null, "selected"]
    let statusId = `word-visibility-status-${p.id}`;
    result += `
<tr>
<td class="align-middle">${p.id}</td>
<td>${p.value}</td>
<td>${p.description}</td>
<td>${p.maxScores}</td>
<td class="align-middle">
  <div class="form-group">
    <select class="form-control word-visibility-status" word-id="${p.id}" id="${statusId}">
      <option value="VISIBLE" ${selectedVisible}>Показывать</option>
      <option value="HIDDEN" ${s}>Скрыть</option>
    </select>
  </div>
</td>
</tr>
`
  })

  result += `</tbody></table>`;

  return $(`#${divId}`).html(result)
}


function displayErrors(errors) {
  if (!(errors instanceof Array) || errors.length === 0) {
    return
  }
  errors.forEach((e) => {
    let id = `#${e.field}-error`
    $(id).html(e.defaultMessage)
  })
}

function hiderErrors() {
  $(".field-message").html("")
  $(".server-message").html("")
}

function onWordCreateSuccess() {
  $("#add-user-modal").modal('hide')
  $("#create-word-value").val("")
  $("#create-word-description").val("")
  $("#create-word-score").val("")
}

function onWordCreateError(e) {
  displayErrors(e.responseJSON.errors)
}

function validateCreateWordFormFields() {
  if ($("#create-word-value").val().trim() === ""
    || $("#create-word-description").val().trim() === ""
    || $("#create-word-score").val().trim() === "") {
    $("#server-error-response").html("Все поля должны быть заполнены")
  }
}

function createWord() {
  hiderErrors()
  validateCreateWordFormFields()
  let data = {
    value: $("#create-word-value").val(),
    description: $("#create-word-description").val(),
    maxScores: $("#create-word-score").val()
  }

  WordsApiRequestHelper.createWord({params: data, onSuccess: onWordCreateSuccess, onError: onWordCreateError})
}

let searchWordSorting = new Sorting()
searchWordSorting.addItem(new SortItem({name: "Id", code: "ID"}))
searchWordSorting.addItem(new SortItem({name: "Слово", code: "VALUE"}))

let searchWordFilter = new Filter()
searchWordFilter.addItem(new FilterText({name: "Слово", code: "value"}))

let searchWordRequest = new RequestForList({
  sorting: searchWordSorting,
  filter: searchWordFilter,
  displayContent: displaySearchWordsResult,
  searcher: WordsApiRequestHelper.searchWords,
  id: "word-searcher-content"
})


searchWordRequest.display()

$("body").on("change", ".word-visibility-status", (e) => {
  let wordId = e.currentTarget.getAttribute("word-id")

  let body = {
    status: e.target.value
  }

  WordsApiRequestHelper.updateStatus({
    wordId: wordId,
    params: body
  })
})