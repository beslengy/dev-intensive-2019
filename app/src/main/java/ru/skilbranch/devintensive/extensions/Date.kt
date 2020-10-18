package ru.skilbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.IllegalStateException


//класс extensions содержит extension - функции,
// которые расширяют существующие библиотеки

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

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
    }
    this.time = time
    return this
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY
}

//функция определяет, сколько времени назад пользователь был в сети
fun Date.humanizeDiff(date:Date = Date()) : String {
    TODO("not implemented") //to change body of created function use File | Settings | File Templates.
}