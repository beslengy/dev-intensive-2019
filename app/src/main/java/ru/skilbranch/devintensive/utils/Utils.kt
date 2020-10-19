package ru.skilbranch.devintensive.utils

import ru.skilbranch.devintensive.models.User
import kotlin.collections.mapOf as mapOf1

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        return if (fullName == "" || fullName == " ") {
            val firstName = null
            val lastName = null
            firstName to lastName
        } else {
            val parts: List<String>? = fullName?.split(" ")

            val firstName = parts?.getOrNull(0)
            val lastName = parts?.getOrNull(1)
            firstName to lastName
        }
//        return Pair(firstName, lastName)
    }

    fun getUserInfo(user: User) {
        val (id, firstName, lastName) = user
        println("$id, $firstName, $lastName")
        //println("${user.component1()}, ${user.component2()}, ${user.component3()}")
    }

    //Преобразует кириллицу в латиницу
    fun transliteration(payload: String, divider: String = " "): String {
        val symbolsMap = mapOf1(
            'а' to "a",
            'б' to "b",
            'в' to "v",
            'г' to "g",
            'д' to "d",
            'е' to "e",
            'ё' to "e",
            'ж' to "zh",
            'з' to "z",
            'и' to "i",
            'й' to "i",
            'к' to "k",
            'л' to "l",
            'м' to "m",
            'н' to "n",
            'о' to "o",
            'п' to "p",
            'р' to "r",
            'с' to "s",
            'т' to "t",
            'у' to "u",
            'ф' to "f",
            'х' to "h",
            'ц' to "c",
            'ч' to "ch",
            'ш' to "sh",
            'щ' to "sh'",
            'ъ' to "",
            'ы' to "i",
            'ь' to "",
            'э' to "e",
            'ю' to "yu",
            'я' to "ya"
        )

        fun isUpperCase(c: Char): Boolean {
            return c.toUpperCase() == c
        }

        var newString = ""
        for (i in payload.indices) {
            newString = when {
                symbolsMap.containsKey(payload[i].toLowerCase()) ->
                    if (isUpperCase(payload[i]))
                        "$newString${symbolsMap.getValue(payload[i].toLowerCase()).toUpperCase()}"
                    else "$newString${symbolsMap.getValue(payload[i].toLowerCase())}"
                payload[i] == ' ' -> "$newString$divider"
                else -> "$newString${payload[i]}"
            }

        }
        return newString
    }

    //Функция возвращает инициалы из полного имени
    fun toInitials(firstName: String?, lastName: String?): String? {

        val firstInitial =
            if (firstName.equals(null) || firstName!!.isEmpty()) null else firstName[0].toUpperCase()
        val lastInitial =
            if (lastName.equals(null) || lastName!!.isEmpty()) null else lastName[0].toUpperCase()

        fun isValid(char: Char?): Boolean {
            return char != null && char != ' '
        }

        return if (isValid(firstInitial) && isValid(lastInitial)) "$firstInitial$lastInitial"
        else if (isValid(firstInitial)) "$firstInitial"
        else if (isValid(lastInitial)) "$lastInitial"
        else null
    }

}