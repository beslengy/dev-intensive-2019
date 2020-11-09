package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*


//класс extensions содержит extension - функции,
// которые расширяют существующие библиотеки

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR
const val YEAR = 360 * DAY

    //создаем функцию для вывода даты в определенном формате
fun Date.format (pattern:String = "HH:mm:ss dd.MM.yy"):String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

    //добавляем возможность сдвигать время на определенный интервал
fun Date.add(value:Int, units: TimeUnits = TimeUnits.SECOND) : Date{
    var time = this.time
    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
        TimeUnits.YEAR -> value * YEAR
    }
    this.time = time
    return this
}

enum class TimeUnits (
    var for1 : String?,
    var for234 : String?,
    var forElse: String?
) {
    SECOND ("секунду", "секунды", "секунд"),
    MINUTE ("минуту", "минуты", "минут"),
    HOUR ("час", "часа", "часов"),
    DAY ("день", "дня", "дней"),
    YEAR ("год", "года", "лет");

    //наводим порядок в окончаниях едениц времени
    fun plural (num: Long) : String {
        val numString = num.toString()
        val lastChar : String = numString.substring(numString.lastIndex)
        //println(lastChar.toInt())
        var twoLastChars = ""
        if (numString.length > 1)
            twoLastChars = numString.substring(numString.length - 2)
            //println(twoLastChars)
        if (twoLastChars == "11")
            return "$num $forElse"

        return when (lastChar.toInt()) {
            1 -> "$num $for1"
            2, 3, 4 -> "$num $for234"
            else -> "$num $forElse"
        }
    }

}

//функция определяет, сколько времени назад пользователь был в сети
fun Date.humanizeDiff(date:Date = Date()) : String {

    val diff = date.time - this.time
    return when (diff) {
        in 0..SECOND -> "только что"
        in SECOND..(45 * SECOND) -> "несколько секунд назад"
        in (45 * SECOND)..(75 * SECOND) -> "минуту назад"
        in (75 * SECOND)..(45 * MINUTE) ->
            "${TimeUnits.MINUTE.plural((diff / MINUTE))} назад"
        in (45 * MINUTE)..(75 * MINUTE) -> "час назад"
        in (75 * MINUTE)..(22 * HOUR) ->
            "${TimeUnits.HOUR.plural((diff / HOUR))} назад"
        in (22 * HOUR)..(26 * HOUR) -> "день назад"
        in (26 * HOUR)..(361 * DAY) ->
            "${TimeUnits.DAY.plural((diff / DAY))} назад"
        else -> "более года назад"


    }


}

