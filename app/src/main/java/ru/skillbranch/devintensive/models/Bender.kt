package ru.skillbranch.devintensive.models

import android.util.Log

class Bender (
    var status: Status = Status.NORMAL,
    var question: Question = Question.NAME,
    private var negAnswersCounter: Int = 0
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
        Log.d("M_Bender", "Listen Answer on")
        fun validation (question: Question, text : String) : Boolean {
            Log.d("M_Bender", "Validation on, answer ${text.toCharArray().contentToString()}")
            return when (question) {
                Question.NAME -> text[0] == text[0].toUpperCase()
                Question.PROFESSION -> text[0] == text[0].toLowerCase()
                Question.MATERIAL -> !text.toCharArray().any { it in listOf('1', '2', '3', '4', '5', '6', '7', '8', '9', '0') }
                Question.BDAY -> text.toIntOrNull() is Int
                Question.SERIAL -> (text.toIntOrNull() is Int && text.length == 7)
                Question.IDLE -> true
            }

        }
        return if (validation(question, answer)) {
            if (question == Question.IDLE) "${question.question}" to status.color
            else {
                if (question.answers.contains(answer.toLowerCase())) {
                    negAnswersCounter = 0
                    if (question == Question.IDLE) {
                        "${question.question}" to status.color
                    } else {
                        question = question.nextQuestion()
                        "Отлично - ты справился\n${question.question}" to status.color
                    }
                } else {
                    if (status.ordinal < Status.values().lastIndex) {
                        Log.d("M_Bender", "$negAnswersCounter")
                        status = status.nextStatus()
                        "Это неправильный ответ\n${question.question}" to status.color
                    } else {
                        status = Status.NORMAL
                        question = Question.NAME
                        "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
                    }
                }
            }
        } else {
                when (question) {
                    Question.NAME -> "Имя должно начинаться с заглавной буквы\n${question.question}" to status.color
                    Question.PROFESSION -> "Профессия должна начинаться со строчной буквы\n${question.question}" to status.color
                    Question.MATERIAL -> "Материал не должен содержать цифр\n${question.question}" to status.color
                    Question.BDAY -> "Год моего рождения должен содержать только цифры\n${question.question}" to status.color
                    Question.SERIAL -> "Серийный номер содержит только цифры, и их 7\n${question.question}" to status.color
                    Question.IDLE -> " " to status.color
                }
        }
    }

    enum class Status (val color:Triple<Int, Int, Int>) { //содержит список статусов и конструктор с определением цвета по ргб
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

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
        MATERIAL ("Из чего я сделан?", listOf("металл", "дерево", "metall", "iron", "wood")) {
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