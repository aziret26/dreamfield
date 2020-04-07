class Displayable {
  display() {

  }
}

class Navigate {
  static  home = () => {
    this.#doNavigate("/")
  }

  static sigIn = () => {
    this.#doNavigate("/pages/sign-in")
  }

  static playerIndex = () => {
    this.#doNavigate("/pages/player")
  }

  static adminIndex = () => {
    this.#doNavigate("/pages/admin")
  }

  static #doNavigate = (value) => {
    window
      .location = value
  }
}

function validateAuthenticated() {
  if (!AccountApiRequestHelper.currentAccount) {
    Navigate.sigIn()
  }
}

function navigateUserPage() {
  if (!AccountApiRequestHelper.currentAccount) {
    return
  }
  navigateToAdminDir()
  navigateToPlayerDir()
}

function navigateToPlayerDir() {
  if (AccountApiRequestHelper.currentAccount.type === userTypes.player) {
    Navigate.playerIndex()
  }
}

function navigateToAdminDir() {
  if (AccountApiRequestHelper.currentAccount.type === userTypes.admin) {
    Navigate.adminIndex()
  }
}

class MenuItem extends Displayable {
  id
  name
  link

  constructor(id, name, link) {
    super()
    this.id = id
    this.name = name
    this.link = link
  }

  display = () => {
    return `
<li class="nav-item" id="nav-item-${this.id}">
    <a class="nav-link" href="${this.link}">${this.name}</span></a>
</li>
`
  }
}

class Header extends Displayable {
  constructor(id = "#header") {
    super()
    $(id).html(this.headerDefaultContent)
    $("body").on("click", "#sign-out", () => {
      AuthApiHelper.signOut(Navigate.sigIn())
    })
  }

  headerDefaultContent = () => {
    return `
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand" href="#">Поле Чудес</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto" id="nav-menu"></ul>
    <div class="form-inline my-2 my-lg-0">
      <span class="mr-4">Привет, ${AccountApiRequestHelper.currentAccount.name}</span>
      <button class="btn btn-outline-success my-2 my-sm-0" id="sign-out">Выйти</button>
    </div>
  </div>
</nav>
    `
  }

  setNavMenuContent(items) {
    items.forEach((item) => {
      this.addMenuItem(item)
    })
  }

  addMenuItem(item) {
    if (!(item instanceof MenuItem)) {
      return
    }
    $("#nav-menu").append(item.display())
  }

  setActive(id) {
    $(`.nav-item`).removeClass("active")
    $(`#nav-item-${id}`).addClass("active")
  }
}

class PaginationPages extends Displayable {
  currentPage
  maxPages
  maxDisplayPages
  divId
  onPageClick
  pagingCode

  constructor({divId, onClick, maxDisplayPages = 3, pagingCode}) {
    super()

    this.setValues({divId: divId, maxDisplayPages: maxDisplayPages})
    this.divId = divId || this.divId || "paging"
    this.pagingCode = `${pagingCode}-page`
    this.onPageClick = onClick

    $("body").on("click", `.${this.pagingCode}`, (item) => {
      this.setCurrentPage(parseInt(item.currentTarget.getAttribute("attr-page")))
      this.onPageClick()
    })
  }

  setValues = ({currentPage, maxPages, maxDisplayPages}) => {
    this.currentPage = currentPage || this.currentPage
    this.maxPages = maxPages || this.maxPages
    this.maxDisplayPages = maxDisplayPages || this.maxDisplayPages || 3

    this.display()
  }

  display = () => {
    if (!this.currentPage || !this.maxPages) {
      return "No pages"
    }
    $(`#${this.divId}`).html(`
<nav aria-label="Page navigation example">
  <ul class="pagination justify-content-center">
     ${this.#getPagingItemsAsHtml()}
  </ul>
</nav>
`)
  }

  #getPagingItemsAsHtml = () => {
    let pages = this.#getDisplayPages()

    let result = ``

    // add prev btn
    if (this.currentPage > 1) {
      result += `
<li class="page-item ${this.pagingCode} " attr-page="${this.currentPage - 1}">
    <p class="page-link">Пред</p>
</li>
      `
    }

    // add pages
    pages.forEach((page) => {
      let className = page === this.currentPage ? 'active disabled' : 'clickable'
      result += `
<li class="page-item ${this.pagingCode} ${className} " attr-page="${page}">
    <p class="page-link">${page}</p>
</li>
      `
    })

    // add next btn
    if (this.currentPage < this.maxPages) {
      result += `
<li class="page-item ${this.pagingCode}" attr-page="${this.currentPage + 1}">
    <p class="page-link">След.</p>
</li>
      `
    }

    return result
  }

  #getDisplayPages = () => {
    let result = []
    let i = Math.min(this.maxPages + 1 - this.maxDisplayPages, Math.max(1, this.currentPage - (this.maxDisplayPages / 2 | 0)));
    const end = i + this.maxDisplayPages;
    while (i < end) {
      if (i < 1) {
        i++
        continue
      }
      result.push(i++)
    }
    return result;
  }

  setCurrentPage(value) {
    this.currentPage = value
    this.display()
  }

}

class SortItem extends Displayable {
  name
  code

  constructor({name, code}) {
    super()
    this.name = name
    this.code = code
  }

  display() {
    return `<option value="${this.code}">${this.name}</option>`
  }
}

class Sorting extends Displayable {
  sortItems
  sortByValue
  sortDirection

  constructor() {
    super()
    this.sortItems = []
    this.sortByValue = ""
  }

  addItem = (item) => {
    if (!(item instanceof SortItem)) {
      return
    }
    this.sortItems.push(item)
  }

  display = () => {
    let result = `
<!--<div class=" mb-3">-->

<div class="d-flex mr-2 mb-2">
  <label for="role" class="mr-2">Сортировать</label>
  <select class="custom-select width-fit-content" id="sort-by">
`
    this.sortItems.forEach((item) => {
      result += item.display()
    })
    result += "</select>"
    result += this.getSortDirectionAsHtml()
    result += "</div>"
    return result
  }

  getSortDirectionAsHtml = () => {
    return `
        <select class="custom-select width-fit-content" id="sort-direction" required>
            <option value="ASC">по возрастанию</option>
            <option value="DESC">по убыванию</option>
        </select>
    `
  }

  getRequestObject = () => {
    return {
      sortBy: $("#sort-by").val(),
      sortDirection: $("#sort-direction").val()
    }
  }
}

class FilterText extends Displayable {
  name
  code
  value

  constructor({name, code}) {
    super();
    this.name = name
    this.code = code
  }

  display() {
    return `
<div class="input-group mr-3">
    <div class="input-group-prepend">
    <span class="input-group-text height-fit-content" id="${this.code}">${this.name}</span>
  </div>
  <input type="text" class="form-control" id="search-${this.code}" aria-label="${this.name}" aria-describedby="${this.code}">
</div>
`
  }

  getValue = () => {
    let result = $(`#search-${this.code}`).val()
    return result && result.trim().length > 0 ? result : null
  }
}

class Filter extends Displayable {
  filters

  constructor() {
    super();
    this.filters = []
  }

  addItem(item) {
    if (!(item instanceof Displayable)) {
      return
    }
    this.filters.push(item)
  }

  display() {
    let result = `<div class="d-flex">
  <label for="role" class="mr-2">Фильтровать</label>`
    this.filters.forEach((item) => {
      result += item.display()
    })
    result += `</div>`
    return result
  }

  getRequestObject = () => {
    let result = {}
    this.filters.forEach((item) => {
      result[item.code] = item.getValue()
    })
    return result
  }
}

class RequestForList extends Displayable {

  id
  sorting
  filter
  paging
  searcher
  contentDisplayer
  content

  constructor({id, sorting, filter, searcher, displayContent}) {
    super()
    this.id = id

    if (filter && !(filter instanceof Filter)) {
      throw new DOMException("Not instance of Filter")
    }
    this.filter = filter

    if (sorting && !(sorting instanceof Sorting)) {
      throw new DOMException("Not instance of Sorting")
    }
    this.sorting = sorting

    this.searcher = searcher
    this.contentDisplayer = displayContent

    this.paging = new PaginationPages({
      onClick: this.search,
      divId: `${this.id}-paging`,
      pagingCode: this.id,
      maxDisplayPages: 5
    })

    this.searchBtnId = `${this.id}-search-btn`

    this.search()
  }

  addClickListener = () => {
    let btn = document.getElementById(this.searchBtnId)
    btn.removeEventListener("click", this.search, false)
    btn.addEventListener("click", this.search, false)
  }

  display = () => {
    let result = `
<div>
  <div class="d-flex searcher justify-content-between mb-3 align-items-center">
    <div class="d-flex flex-column">
        ${this.displaySoring()}
        ${this.displayFilter()}
    </div>
    <div>
        ${this.displaySearchBtn()}
    </div>
  </div>
  <div id="${this.id}-content"/>
  <div id="${this.id}-paging"/>
</div> 
    `
    $(`#${this.id}`).html(result)
    this.addClickListener()
  }

  search = () => {
    this.searcher({params: this.getRequestObject(), onSuccess: this.onSearchSuccess})
  }

  displayFilter() {
    if (!this.filter) {
      return ""
    }
    return this.filter.display()
  }

  displaySoring() {
    if (!this.sorting) {
      return ""
    }
    return this.sorting.display()
  }

  getRequestObject = () => {
    let result = {}
    result["sorting"] = this.getSortingRequestObject()
    result["filters"] = this.getFilterRequestObject()
    result["pageRequest"] = {
      page: this.paging.currentPage || 1,
      limit: 15
    }
    return result
  }

  getFilterRequestObject = () => {
    if (!this.filter) {
      return
    }
    return this.filter.getRequestObject()
  }
  getSortingRequestObject = () => {
    if (!this.sorting) {
      return
    }
    return this.sorting.getRequestObject()
  }


  displaySearchBtn = () => {
    return `<button class="btn btn-outline-primary height-fit-content search-btn" id="${this.searchBtnId}">Поиск</button>`
  }

  onSearchSuccess = (result) => {
    //set values
    this.content = result.content
    this.paging.setValues({
      currentPage: result.page,
      maxPages: result.totalPages
    })

    //display
    this.contentDisplayer(this.content, `${this.id}-content`)
    this.paging.display()
  }

}