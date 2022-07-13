package com.fazekasgergo.healthmonitor.pages.questions

sealed class Question(val questionText: String, val resourceIds: Array<Int>) {

    class ChooseQuestion(private val question: String, private val options: Array<ChooseOption>, private val imageResourceIds: Array<Int>) :
        Question(question, imageResourceIds)

    class InputQuestion(private val question: String, private val imageResourceId: Int) : Question(question, arrayOf(imageResourceId))
}
