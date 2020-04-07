validateAuthenticated()
navigateToPlayerDir()

const menuItems = [
  new MenuItem("admin-main", "Главная", "/pages/admin"),
  new MenuItem("admin-users", "Пользователи", "/pages/admin/users"),
  new MenuItem("admin-words", "Слова", "/pages/admin/words")
];

let header = new Header("#header")
header.setNavMenuContent(menuItems)

let wordStatisticsSorting = new Sorting()
wordStatisticsSorting.addItem(new SortItem({name: "Id", code: "ID"}))
wordStatisticsSorting.addItem(new SortItem({name: "Попытки: успех", code: "ATTEMPTS_SUCCESS"}))
wordStatisticsSorting.addItem(new SortItem({name: "Попытки: провал", code: "ATTEMPTS_FAILED"}))
wordStatisticsSorting.addItem(new SortItem({name: "Попытки: всего", code: "ATTEMPTS_TOTAL"}))
wordStatisticsSorting.addItem(new SortItem({name: "Баллы", code: "MAX_SCORE_ACHIEVED"}))
wordStatisticsSorting.addItem(new SortItem({name: "Уникальные пользователи", code: "UNIQUE_PLAYERS"}))


let playerStatisticsSorting = new Sorting()
playerStatisticsSorting.addItem(new SortItem({name: "Id", code: "ID"}))
playerStatisticsSorting.addItem(new SortItem({name: "Попытки: успех", code: "ATTEMPTS_SUCCESS"}))
playerStatisticsSorting.addItem(new SortItem({name: "Попытки: провал", code: "ATTEMPTS_FAILED"}))
playerStatisticsSorting.addItem(new SortItem({name: "Попытки: всего", code: "ATTEMPTS_TOTAL"}))
playerStatisticsSorting.addItem(new SortItem({name: "Баллы", code: "MAX_SCORE_ACHIEVED"}))
playerStatisticsSorting.addItem(new SortItem({name: "Уникальные слова", code: "UNIQUE_WORDS"}))

function displayWordStatisticsResult(words, id) {
  if (!words || words.length === 0) {
    return "<h3>Ничего не найдено</h3>"
  }

  let result = `
<table class="table">
    <tbody>
    <tr>
        <th>Id</th>
        <th>Слово</th>
        <th>Попытки: успех</th>
        <th>Попытки: провал</th>
        <th>Попытки: всего</th>
        <th>Макс. балл среди игроков</th>
        <th>Уникальные игроки</th>
    </tr>
`
  words.forEach((p) => {
    result += `
<tr>
  <td class="text-center">${p.id}</td>
  <td>${p.word.value}</td>
  <td class="text-center">${p.attemptsSuccess}</td>
  <td class="text-center">${p.attemptsTotal}</td>
  <td class="text-center">${p.attemptsSuccess}</td>
  <td class="text-center">${p.maxScoreAchieved}</td>
  <td class="text-center">${p.uniquePlayers}</td>
</tr>
`
  })
  result += `</tbody></table>`;

  return $(`#${id}`).html(result)
}

function displayPlayerStatisticsResult(players, id) {
  if (!players || players.length === 0) {
    return "<h3>Ничего не найдено</h3>"
  }

  let result = `
<table class="table">
    <tbody>
    <tr>
        <th>Id</th>
        <th>Имя</th>
        <th>почта</th>
        <th>Попытки: успех</th>
        <th>Попытки: провал</th>
        <th>Попытки: всего</th>
        <th>Баллы</th>
        <th>Уникальные слова</th>
    </tr>
`
  players.forEach((p) => {
    result += `
<tr>
  <td class="text-center">${p.id}</td>
  <td>${p.player.name}</td>
  <td>${p.player.email}</td>
  <td class="text-center">${p.attemptsSuccess}</td>
  <td class="text-center">${p.attemptsTotal}</td>
  <td class="text-center">${p.attemptsSuccess}</td>
  <td class="text-center">${p.scoresAchieved}</td>
  <td class="text-center">${p.uniqueWords}</td>
</tr>
`
  })

  result += `</tbody></table>`;

  return $(`#${id}`).html(result)
}

function showPlayersStatistics() {
  $(".search-statistics-btn").removeClass("btn-info")
  $("#search-statistics-players-btn").addClass("btn-info")
  $("#search-statistics-players-btn").removeClass("btn-outline-info")

  let newDivId = `search-players-content`
  $("#searcher-content").html(`<div id="${newDivId}"/>`)

  let searchRequest = new RequestForList({
    sorting: playerStatisticsSorting,
    displayContent: displayPlayerStatisticsResult,
    searcher: StatisticsApiRequestHelper.requestPlayersStatistics,
    id: newDivId
  })

  searchRequest.display()
}

function showWordsStatistics() {
  $(".search-statistics-btn").removeClass("btn-info")
  $("#search-statistics-words-btn").addClass("btn-info")
  $("#search-statistics-words-btn").removeClass("btn-outline-info")

  let newDivId = `search-players-content`
  $("#searcher-content").html(`<div id="${newDivId}"/>`)

  let searchRequest = new RequestForList({
    sorting: wordStatisticsSorting,
    displayContent: displayWordStatisticsResult,
    searcher: StatisticsApiRequestHelper. requestWordsStatistics,
    id: newDivId
  })

  searchRequest.display()
}
