package com.fazekasgergo.healthmonitor.pages.questions

sealed class Question(val questionText: String) {

    class ChooseQuestion(private val question: String, private val options: Array<ChooseOption>) :
        Question(question)

    class InputQuestion(private val question: String) : Question(question)
}
