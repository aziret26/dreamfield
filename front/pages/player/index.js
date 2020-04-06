validateAuthenticated()
navigateToAdminDir()

const menuItems = [
  new MenuItem("player-main", "Главная", "/pages/player")
];

let header = new Header("#header")
header.setNavMenuContent(menuItems)


