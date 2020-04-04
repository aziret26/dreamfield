class Server {
  static host = "localhost"
  static port = 8080
  static url = `http://${this.host}:${this.port}`
  static generateUrl = (value) => {
    return `${this.url}/${value}`
  }
}

class ApiRequestHelper {
  static get = (url,
                onSuccess = (r) => {
                },
                onError = (e) => {
                },
                async = true) => {
    $.ajax({
      async: async,
      url: url,
      type: "GET",
      xhrFields: {
        withCredentials: true
      },
      success: function (result) {
        onSuccess(result)
      },
      error: function (e) {
        onError(e)
      }
    })
  }

  static post = (url,
                 params,
                 onSuccess = () => {
                 },
                 onError = () => {
                 }) => {
    validateType(params, PostRequestParams)
    $.ajax({
      async: params.async,
      url: url,
      data: JSON.stringify(params.body),
      contentType: params.contentType,
      dataType: "json",
      type: "POST",
      xhrFields: {
        withCredentials: true
      },
      success: function (result) {
        onSuccess(result)
      },
      error: function (e) {
        onError(e)
      }
    })
  }

}

class AdminApiRequestHelper {
  static #url = `${Server.url}/api/v1/admins`
  static #createUrl = `${this.#url}/create`
  static requestCreate = (params, onSuccess, onError) => {
    let prp = new PostRequestParams({body: params})

    ApiRequestHelper.post(this.#createUrl, prp, onSuccess, onError)
  }
}

class PlayerApiRequestHelper {

  static #url = `${Server.url}/api/v1/players`
  static #createUrl = `${this.#url}/create`
  static #searchUrl = `${this.#url}/search`

  static requestCreate = (params, onSuccess, onError) => {
    let prp = new PostRequestParams({body: params})

    ApiRequestHelper.post(this.#createUrl, prp, onSuccess, onError)
  }

  static search = (params, onSuccess, onError) => {
    let prp = new PostRequestParams({body: params})

    ApiRequestHelper.post(this.#searchUrl, prp, onSuccess, onError)
  }

}

class AccountApiRequestHelper {
  static currentAccount
  static #currentAccountUrl = `${Server.url}/api/v1/accounts/me`
  static requestCurrentAccount = () => {
    ApiRequestHelper.get(
      AccountApiRequestHelper.#currentAccountUrl,
      AccountApiRequestHelper.setCurrentUser,
      () => {
      },
      false
    )
  }

  static setCurrentUser = (data) => {
    AccountApiRequestHelper.currentAccount = new User(data)
  }
}

class AuthApiHelper {

  static #sigInUrl = Server.generateUrl("api/v1/login")
  static auth = (params, onSuccess, onError) => {
    let data = new FormData()

    data.append("username", params.username)
    data.append("password", params.password)

    this.#sign(params, onSuccess, onError)
  }

  static #sign = (params, onSuccess, onError) => {
    let data = new FormData()
    data.append("username", params.username)
    data.append("password", params.password)

    $.ajax({
      url: this.#sigInUrl,
      type: "POST",
      data: data,
      crossDomain: true,
      xhrFields: {
        withCredentials: true
      },
      processData: false,
      contentType: false,
      success: () => {
        onSuccess()
      },
      error: function () {
        onError()
      }
    })
  }
}

class PostRequestParams {
  body
  contentType

  constructor(data) {
    if (!data.body) {
      throw new Error("body is empty")
    }

    this.body = data.body
    this.contentType = data.contentType || "application/json"
    this.async = data.async || true
  }

}

function validateType(obj, type) {
  if (obj instanceof type) {
    return true
  }
  throw new Error("wrong instance")
}

AccountApiRequestHelper.requestCurrentAccount()