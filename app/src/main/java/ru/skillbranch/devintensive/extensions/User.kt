package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.models.User
import ru.skillbranch.devintensive.models.UserView


fun User.toUserView() : UserView {

    val nickname = ru.skillbranch.devintensive.utils.Utils.transliteration("$firstName $lastName") //Преобразует кириллицу в латиницу
    val initials = ru.skillbranch.devintensive.utils.Utils.toInitials(firstName,lastName)
    val status = if (lastVisit == null) "Еще не был" else if (isOnline) "Online" else "Последний раз был ${lastVisit.humanizeDiff()}"

    return ru.skillbranch.devintensive.models.UserView(
        id,
        fullName = "$firstName $lastName",
        nickName = nickname,
        initials = initials,
        avatar = avatar,
        status = status
    )
}



