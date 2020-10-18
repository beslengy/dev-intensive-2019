package ru.skilbranch.devintensive

import org.junit.Test

import org.junit.Assert.*
import ru.skilbranch.devintensive.extensions.TimeUnits
import ru.skilbranch.devintensive.extensions.add
import ru.skilbranch.devintensive.extensions.format
import ru.skilbranch.devintensive.extensions.toUserView
import ru.skilbranch.devintensive.models.*
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test_instance() {
//        val user = User ("1")
        val user2 = User ("2", "John", "Cena")
//        val user3 = User ("3", "John", "Silverhand", null, lastVisit = Date(), isOnline = true)

//        user.printMe()
//        user2.printMe()
//        user3.printMe()

//        println("$user")

    }

    @Test
    fun test_factory() {
//        val user = User.makeUser("John Cena")
//        val user2 = User.makeUser("John Wick")
        val user = User.makeUser("John Wick")
        val user2 = user.copy(id = "2", lastName = "Cena", lastVisit = Date())
        print("$user \n$user2")
    }

    @Test
    fun test_decomposition() {
        val user = User.makeUser("John Wick")

        fun getUserInfo() = user
        val (id, firstName, lastName) = getUserInfo()
        println("$id, $firstName, $lastName")
        println("${user.component1()}, ${user.component2()}, ${user.component3()}")
    }

    @Test
    // При использовании метода copy создается идентичный объект, который проходит проверку
    // equals и имеет такой же HashCode. Отличаются только ссылки на объекты.
    fun test_copy() {
        val user = User.makeUser("John Wick")
        var user2 = user.copy(lastVisit = Date())
        var user3 = user.copy(lastVisit = Date().add(-2, TimeUnits.SECOND))
        var user4 = user.copy(lastName = "Cena",  lastVisit = Date().add(2, TimeUnits.HOUR))

        println("""
            ${user.lastVisit?.format()}
            ${user2.lastVisit?.format()}
            ${user3.lastVisit?.format()}
            ${user4.lastVisit?.format()}
        """.trimIndent())

        /*if(user.equals(user2)) {
            println("equals data and hash \n${user.hashCode()} $user \n${user2.hashCode()} $user2")
        } else {
            println("not equals data and hash \n${user.hashCode()} $user \n${user2.hashCode()} $user2")
        }

    // Сравнить объекты ==
    // Сравнить ссылки ===

        if(user === user2) {
            // Команда System.identityHashCode - выводит хэш-код объекта
            println("equals address ${System.identityHashCode(user)} ${System.identityHashCode(user2)}")
        } else {
            println("not equals address ${System.identityHashCode(user)} ${System.identityHashCode(user2)}")
        }
*/
    }

    @Test
    //Суть - преобразование дата класса к конкретному объекту
    fun test_data_maping() {
        val user = User.makeUser("Молчанов Ярослав")
        val newUser = user.copy(lastVisit = Date().add(-7, TimeUnits.SECOND))
        println(user)
        val userView = user.toUserView()
        userView.printMe()
    }

    @Test
    fun test_abstractFactory() {
        val user = User.makeUser("Молчанов Ярослав")
        val txtMessage = BaseMessage.makeMessage(user, Chat("0"), payload = "any text", type = "text")
        val imgMessage = BaseMessage.makeMessage(user, Chat("0"), payload = "any image url", type = "image")

        println(txtMessage.formatMessage())
        println(imgMessage.formatMessage())

    }
}