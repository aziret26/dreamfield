header.setActive("admin-users")

function createUser() {
  hiderErrors()
  let data = {
    name: $("#name").val(),
    email: $("#email").val(),
    password: $("#password").val()
  }
  let role = $("#role").val()


  if (role === "PLAYER") {
    PlayerApiRequestHelper.requestCreate(data, onUserCreateSuccess, onUserCreateError)
  } else if (role === "ADMIN") {
    AdminApiRequestHelper.requestCreate(data, onUserCreateSuccess, onUserCreateError)
  } else {
    $("#role-error").html("Пожалуйста вы берите роль из списка")
  }
}

function onUserCreateSuccess() {
  $("#add-user-modal").modal('hide')
  $("#name").val("")
  $("#email").val("")
  $("#password").val("")
}

function onUserCreateError(e) {
  displayErrors(e.responseJSON.errors)
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

function displayPlayers(players, id) {
  if (!players || players.length === 0) {
    return "<h3>Ничего не найдено</h3>"
  }

  let result = `
<table class="table">
    <tbody>
    <tr>
        <th>Id</th>
        <th>Имя</th>
        <th>Email</th>
        <th>Баллы</th>
        <th>Сохранить</th>
    </tr>
`
  players.forEach((p) => {
    result += `
<tr>
<td class="align-middle">${p.id}</td>
<td><input type="text" class="form-control" value="${p.name}"></td>
<td><input type="text" class="form-control" value="${p.email}"></td>
<td class="align-middle">${p.score}</td>
<td> <button class="btn btn-outline-success btn-sm edit-player" player-id="${p.id}">Сохранить</button> </td>
</tr>
`
  })

  result += `</tbody></table>`;

  return $(`#${id}`).html(result)
}

function displayAdmins(admins, id) {
  if (!admins || admins.length === 0) {
    return "<h3>Ничего не найдено</h3>"
  }

  let result = `
<table class="table">
    <tbody>
    <tr>
        <th>Id</th>
        <th>Имя</th>
        <th>Email</th>
        <th>Сохранить</th>
    </tr>
`
  admins.forEach((p) => {
    result += `
<tr>
<td class="align-middle">${p.id}</td>
<td><input type="text" class="form-control" value="${p.name}"></td>
<td><input type="text" class="form-control" value="${p.email}"></td>
<td> <button class="btn btn-outline-success btn-sm edit-user" user-id="${p.id}">Сохранить</button> </td>

</tr>
`
  })

  result += `</tbody></table>`;

  return $(`#${id}`).html(result)
}

let playerSorting = new Sorting()
playerSorting.addItem(new SortItem({name: "Id", code: "ID"}))
playerSorting.addItem(new SortItem({name: "Имя", code: "NAME"}))
playerSorting.addItem(new SortItem({name: "Email", code: "EMAIL"}))
playerSorting.addItem(new SortItem({name: "Баллы", code: "SCORE"}))

let adminSorting = new Sorting()
adminSorting.addItem(new SortItem({name: "Id", code: "ID"}))
adminSorting.addItem(new SortItem({name: "Имя", code: "NAME"}))
adminSorting.addItem(new SortItem({name: "Email", code: "EMAIL"}))

let adminFilter = new Filter()
adminFilter.addItem(new FilterText({name: "Имя", code: "name"}))
adminFilter.addItem(new FilterText({name: "Email", code: "email"}))

let playerFilter = new Filter()
playerFilter.addItem(new FilterText({name: "Имя", code: "name"}))
playerFilter.addItem(new FilterText({name: "Email", code: "email"}))

function searchPlayers() {
  $(".search-users-btn").removeClass("btn-info")
  $("#search-players-btn").addClass("btn-info")
  $("#search-players-btn").removeClass("btn-outline-info")

  let newDivId = `search-players-content`
  $("#searcher-content").html(`<div id="${newDivId}"/>`)

  let searchRequest = new RequestForList({
    sorting: playerSorting,
    filter: playerFilter,
    displayContent: displayPlayers,
    searcher: PlayerApiRequestHelper.search,
    id: newDivId
  })

  searchRequest.display()
}

function searchAdmins() {
  $(".search-users-btn").removeClass("btn-info")
  $("#search-admins-btn").addClass("btn-info")
  $("#search-admins-btn").removeClass("btn-outline-info")

  let newDivId = `search-admins-content`
  $("#searcher-content").html(`<div id="${newDivId}"/>`)

  let searchRequest = new RequestForList({
    sorting: adminSorting,
    filter: adminFilter,
    displayContent: displayAdmins,
    searcher: AdminApiRequestHelper.search,
    id: newDivId
  })

  searchRequest.display()
}

$("body").on("click", ".tab-clicker", (e) => {
  let value = e.currentTarget.getAttribute("tab-value")

  if (value === "players") {
    searchPlayers()
  }
  if (value === "admins") {
    searchAdmins()
  }
})

searchPlayers()