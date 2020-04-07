function authorize(params, onAuthSuccess, onAuthError) {
  AuthApiHelper.auth(params, onAuthSuccess, onAuthError)
}

function setWcm(value) {
  $("#credentials-error-message").html(value)
}


function onAuthStart() {
  $("#sign-in-btn").attr("disabled", true)
  setWcm("")
}

function onAuthSuccess() {
  Navigate.home()
}

function onAuthError() {
  $("#sign-in-btn").attr("disabled", false)
  setWcm("Неправильный логин пароль")
}

function validateFields(params) {
  if (params.username.trim().length === 0 || params.password.trim().length === 0) {
    setWcm("Пожалуйста заполните все поля")
    return false
  }
  return true
}


$("body").on("click", "#sign-in-btn", () => {

  signIn()

})

function signIn() {
  let params = {
    username: $("#username").val(),
    password: $("#password").val()
  }

  if (!validateFields(params)) {
    return
  }

  onAuthStart()
  authorize(params, onAuthSuccess, onAuthError)
}

$("body").on("keypress", ".sign-in-form", (e) => {

  if (e.which == 13) {
    signIn()
  }

})

navigateUserPage();