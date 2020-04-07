class Server {
  static host = "localhost"
  static port = 8080
  static url = `http://${this.host}:${this.port}`
  static generateUrl = (value) => {
    return `${this.url}/${value}`
  }
}

class PostRequestParams {
  body
  contentType

  constructor({body, contentType, async}) {
    if (!body) {
      console.log(body, "empty body")
      throw new Error("body is empty")
    }

    this.body = body
    this.contentType = contentType || "application/json"
    this.async = async || true
  }

}

class ApiRequestHelper {
  static get = (url,
                {onSuccess, onError, async = true}) => {
    $.ajax({
      async: async,
      url: url,
      type: "GET",
      xhrFields: {
        withCredentials: true
      },
      success: function (result) {
        if (onSuccess) {
          onSuccess(result)
        }
      },
      error: function (e) {
        if (onError) {
          onError(e)
        }
      }
    })
  }

  static post = (url,
                 {
                   params,
                   onSuccess,
                   onError
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
        if (onSuccess) {
          onSuccess(result)
        }
      },
      error: function (e) {
        if (onError) {
          onError(e)
        }
      }
    })
  }
  static put = (url,
                {
                  params,
                  onSuccess,
                  onError
                }) => {
    validateType(params, PostRequestParams)
    $.ajax({
      async: params.async,
      url: url,
      data: JSON.stringify(params.body),
      contentType: params.contentType,
      dataType: "json",
      type: "PUT",
      xhrFields: {
        withCredentials: true
      },
      success: function (result) {
        if (onSuccess) {
          onSuccess(result)
        }
      },
      error: function (e) {
        if (onError) {
          onError(e)
        }
      }
    })
  }

}

class AdminApiRequestHelper {
  static #url = `${Server.url}/api/v1/admins`
  static #createUrl = `${this.#url}/create`
  static #searchUrl = `${this.#url}/search`

  static requestCreate = (params, onSuccess, onError) => {
    let prp = new PostRequestParams({body: params})

    ApiRequestHelper.post(this.#createUrl, {params: prp, onSuccess: onSuccess, onError: onError})
  }

  static search = ({params, onSuccess, onError}) => {
    let prp = new PostRequestParams({body: params})

    ApiRequestHelper.post(this.#searchUrl, {params: prp, onSuccess: onSuccess, onError: onError})
  }
}

class PlayerApiRequestHelper {

  static #url = `${Server.url}/api/v1/players`
  static #createUrl = `${this.#url}/create`
  static #searchUrl = `${this.#url}/search`

  static requestCreate = (params, onSuccess, onError) => {
    let prp = new PostRequestParams({body: params})

    ApiRequestHelper.post(this.#createUrl, {params: prp, onSuccess: onSuccess, onError: onError})
  }

  static search = ({params, onSuccess, onError}) => {
    let prp = new PostRequestParams({body: params})

    ApiRequestHelper.post(this.#searchUrl, {params: prp, onSuccess: onSuccess, onError: onError})
  }

}

class AccountApiRequestHelper {
  static currentAccount
  static #currentAccountUrl = `${Server.url}/api/v1/accounts/me`
  static requestCurrentAccount = () => {
    ApiRequestHelper.get(
      AccountApiRequestHelper.#currentAccountUrl,
      {
        onSuccess: AccountApiRequestHelper.setCurrentUser,
        async: false
      }
    )
  }

  static setCurrentUser = (data) => {
    AccountApiRequestHelper.currentAccount = new User(data)
  }
}

class AuthApiHelper {

  static #sigInUrl = Server.generateUrl("api/v1/login")
  static #sigOutUrl = Server.generateUrl("api/v1/logout")
  static auth = (params, onSuccess, onError) => {
    let data = new FormData()
    data.append("username", params.username)
    data.append("password", params.password)

    this.#signIn(data, onSuccess, onError)
  }

  static #signIn = (data, onSuccess, onError) => {

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

  static signOut = () => {
    ApiRequestHelper.get(this.#sigOutUrl, {async: true})
  }
}

class GuessWordApiRequestHelper {
  static #url = `${Server.url}/api/v1/words/guess`
  static #nextGuessWordUrl = `${this.#url}/next-guess-word`
  static #guessWordUrl = `${this.#url}/guess-word`

  static requestNextGuessWordUrl = (onSuccess, onError) => {
    ApiRequestHelper.get(this.#nextGuessWordUrl, {
      onSuccess: onSuccess,
      onError: onError,
      async: true
    })
  }

  static requestGuessWord = ({params, onSuccess, onError}) => {
    let prp = new PostRequestParams({body: params})
    ApiRequestHelper.post(this.#guessWordUrl, {params: prp, onSuccess: onSuccess, onError: onError})
  }
}

class StatisticsApiRequestHelper {
  static #url = `${Server.url}/api/v1/statistics`
  static #wordsStatistics = `${this.#url}/words/search`
  static #playersStatistics = `${this.#url}/players/search`
  static #currentPlayerStatistics = `${this.#url}/players/current`

  static requestWordsStatistics = ({params, onSuccess, onError}) => {
    let prp = new PostRequestParams({body: params})
    ApiRequestHelper.post(this.#wordsStatistics, {params: prp, onSuccess: onSuccess, onError: onError})
  }

  static requestPlayersStatistics = ({params, onSuccess, onError}) => {
    let prp = new PostRequestParams({body: params})
    ApiRequestHelper.post(this.#playersStatistics, {params: prp, onSuccess: onSuccess, onError: onError})
  }

  static requestCurrentPlayerStatistics = (onSuccess, onError) => {
    ApiRequestHelper.get(this.#currentPlayerStatistics, {
      onSuccess: onSuccess,
      async: true
    })
  }

}

class WordsApiRequestHelper {
  static #url = `${Server.url}/api/v1/words`
  static #createUrl = `${this.#url}`
  static #search = `${this.#url}/search`
  static #updateStatus = `${this.#url}`

  static searchWords = ({params, onSuccess, onError}) => {
    let prp = new PostRequestParams({body: params})
    ApiRequestHelper.post(this.#search, {params: prp, onSuccess: onSuccess, onError: onError})
  }

  static createWord = ({params, onSuccess, onError}) => {
    let prp = new PostRequestParams({body: params})
    ApiRequestHelper.post(this.#createUrl, {params: prp, onSuccess: onSuccess, onError: onError})
  }

  static updateStatus = ({wordId, params, onSuccess, onError}) => {
    let prp = new PostRequestParams({body: params})
    ApiRequestHelper.put(`${this.#url}/${wordId}/status`, {params: prp, onSuccess: onSuccess, onError: onError})
  }
}

function validateType(obj, type) {
  if (obj instanceof type) {
    return true
  }
  throw new Error("wrong instance")
}

AccountApiRequestHelper.requestCurrentAccount()