package ru.skilbranch.devintensive.models

import android.util.Log

class Bender (
    var status:Status = Status.NORMAL,
    var question: Question = Question.NAME,
    var negAnswersCounter: Int = 1
) {
    fun askQuestion():String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer:String) : Pair<String, Triple<Int, Int, Int>> {

        return if (question.answers.contains(answer)) {
            negAnswersCounter = 1
            if (question == Question.IDLE) {
                "Отлично - ты справился\nНа этом все, вопросов больше нет" to status.color
            } else {
                question = question.nextQuestion()
                "Отлично - ты справился\n${question.question}" to status.color
            }
        } else {
            if (negAnswersCounter < 3) {
                negAnswersCounter++
                Log.d("M_Bender", "$negAnswersCounter")
                status = status.nextStatus()
                "Это неправильный ответ\n${question.question}" to status.color
            } else {
                negAnswersCounter = 1
                status = Status.NORMAL
                question = Question.NAME
                "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
            }
        }

    }

    enum class Status (val color:Triple<Int, Int, Int>) { //содержит список статусов и конструктор с определением цвета по ргб
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 255, 0));

        fun nextStatus () : Status { //функция смены статуса
            return if (this.ordinal < values().lastIndex) { //если порядковый номер меньше индекса последнего элемента
                values() [this.ordinal + 1]                 //возвращает статус (элемент) со следующим порядковым номером
            } else {
                values() [0]                                //в противном случае возвращает первый элемент
            }
        }
    }

    enum class Question (val question: String, val answers:List<String>) { //Енам класс содержит список вопросов (константы) и базовый конструктор со свойствами вопрос (строка) и ответы (список)
        NAME ("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion(): Question = PROFESSION
        },
        PROFESSION ("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL
        },
        MATERIAL ("Из чего я сделан", listOf("металл", "дерево", "metall", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY
        },
        BDAY ("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
        },
        SERIAL ("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
        },
        IDLE ("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE
        };

        abstract fun nextQuestion() : Question //Абстрактный метод, переопределяется для каждого элемента перечисления
    }
}