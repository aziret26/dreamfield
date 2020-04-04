validateAuthenticated()
navigateToPlayerDir()

const menuItems = [
  new MenuItem("admin-main", "Главная", "/pages/admin"),
  new MenuItem("admin-users", "Пользователи", "/pages/admin/users"),
  new MenuItem("admin-statistics", "Статистика", "/pages/admin/statistics"),
  new MenuItem("admin-words", "Слова", "/pages/admin/words")
];


let header = new Header("#header")
header.setNavMenuContent(menuItems)
