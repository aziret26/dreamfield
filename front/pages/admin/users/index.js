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
  $('#add-user-modal').modal('hide')
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
    console.log(id, e.defaultMessage)
    $(id).html(e.defaultMessage)
  })
}

function hiderErrors() {
  $(".field-message").html("")
  $(".server-message").html("")
}

let paging = new PaginationPages({
  divId: "pagination",
  maxDisplayPages: 5,
  onClick: (page) => {
    paging.setCurrentPage(parseInt(page))
  }
})

paging.setValues({
  currentPage: 15,
  maxPages: 20
})
paging.display()


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
<td><input type="text" class="form-control" value="${p.score}"></td>
<td></td>
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
adminFilter.addItem(new FilterText({name: "Имя", code: "NAME"}))
adminFilter.addItem(new FilterText({name: "Email", code: "EMAIL"}))

let playerFilter = new Filter()
playerFilter.addItem(new FilterText({name: "Имя", code: "NAME"}))
playerFilter.addItem(new FilterText({name: "Email", code: "EMAIL"}))


let playerReq = new RequestForList({
  sorting: playerSorting,
  filter: playerFilter,
  displayContent: displayPlayers,
  searcher: PlayerApiRequestHelper.search
})

playerReq.display()