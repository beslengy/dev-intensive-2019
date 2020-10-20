package ru.skilbranch.devintensive

import org.junit.Test

import org.junit.Assert.*
import ru.skilbranch.devintensive.extensions.TimeUnits
import ru.skilbranch.devintensive.extensions.add
import ru.skilbranch.devintensive.extensions.format
import ru.skilbranch.devintensive.extensions.toUserView
import ru.skilbranch.devintensive.models.*
import ru.skilbranch.devintensive.utils.Utils
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
        val user2 = User("2", "John", "Cena")
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
        val user1 = User.makeUser("John Wick")
        val user2 = User.makeUser(null) //null null
        val user3 = User.makeUser("") //null null
        val user4 = User.makeUser(" ") //null null
        val user5 = User.makeUser("John") //John null

        Utils.getUserInfo(user1)
        Utils.getUserInfo(user2)
        Utils.getUserInfo(user3)
        Utils.getUserInfo(user4)
        Utils.getUserInfo(user5)


    }

    @Test
    // При использовании метода copy создается идентичный объект, который проходит проверку
    // equals и имеет такой же HashCode. Отличаются только ссылки на объекты.
    fun test_copy() {
        val user = User.makeUser("John Wick")
        var user2 = user.copy(lastVisit = Date())
        var user3 = user.copy(lastVisit = Date().add(-2, TimeUnits.SECOND))
        var user4 = user.copy(lastName = "Cena", lastVisit = Date().add(2, TimeUnits.HOUR))

        println(
            """
            ${user.lastVisit?.format()}
            ${user2.lastVisit?.format()}
            ${user3.lastVisit?.format()}
            ${user4.lastVisit?.format()}
        """.trimIndent()
        )

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
        val user = User.makeUser("Ярослав Slengy")
        val newUser = user.copy(lastVisit = Date().add(-7, TimeUnits.SECOND))
        println(user)
        val userView = user.toUserView()
        userView.printMe()
    }

    @Test
    fun test_abstractFactory() {
        val user = User.makeUser("Ярослав Молчанов")
        val txtMessage =
            BaseMessage.makeMessage(user, Chat("0"), payload = "any text", type = "text")
        val imgMessage = BaseMessage.makeMessage(
            user,
            Chat("0"),
            date = Date().add(-21, TimeUnits.DAY),
            payload = "any image url",
            type = "image",
            isIncoming = true
        )

        println(txtMessage.formatMessage())
        println(imgMessage.formatMessage())

    }

    @Test
    fun test_initials() {
        //val user = User.makeUser()
        val a = Utils.toInitials("john", "doe") //JD
        val e = Utils.toInitials(null, "doe") //D
        val s = Utils.toInitials("John", null) //J
        val b = Utils.toInitials(null, null) //null
        val v = Utils.toInitials(" ", "") //null
        println(
            """
            $a
            $e
            $s
            $b
            $v
        """.trimIndent()
        )
    }

    @Test
    fun test_humanDiff() {
        val user = User.makeUser("peter parker")
        val newUser = user.copy(lastVisit = Date().add(-11, TimeUnits.HOUR))
        val userView = newUser.toUserView()
        userView.printMe()
        println(
            """
            ${user.lastVisit?.format()}
            ${newUser.lastVisit?.format()}
        """.trimIndent()
        )

    }

    @Test
    fun test_makeMessage() {
        val user = User.makeUser("peter parker")
        val chat = Chat("Pussies")
        BaseMessage.makeMessage(user, chat, date = Date(), payload = "some text")
        BaseMessage.makeMessage(
            user, chat, date = Date(), payload = "some text",
            type = "text", isIncoming = true
        )

    }

    @Test
    fun test_userBuilder() {
        val user = User.Builder().id("1")
            .firstName("Yaroslav")
            .lastName("Slengy")
            .avatar("avatar")
            .rating(10)
            .respect(6)
            .lastVisit(Date())
            .isOnline(true)
            .build()

        val user2 = User.Builder().avatar("img")
            .firstName("samara")
            .isOnline(false)
            .rating(8)
            .build()

//        user.toUserView().printMe()
        println(user)
        println(user2)
    }
}