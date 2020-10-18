package ru.skilbranch.devintensive.extensions

import ru.skilbranch.devintensive.models.User
import ru.skilbranch.devintensive.models.UserView
import ru.skilbranch.devintensive.utils.Utils



fun User.toUserView() : UserView {

    val nickname = Utils.transliteration("$firstName $lastName") //Преобразует кириллицу в латиницу
    val initials = Utils.initials(firstName,lastName)
    val status = if (lastVisit == null) "Еще не был" else if (isOnline) "Online" else "Последний раз был ${lastVisit.humanizeDiff()}"

    return UserView(
        id,
        fullName = "$firstName $lastName",
        nickName = nickname,
        initials = initials,
        avatar = avatar,
        status = status)
}



