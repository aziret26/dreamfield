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