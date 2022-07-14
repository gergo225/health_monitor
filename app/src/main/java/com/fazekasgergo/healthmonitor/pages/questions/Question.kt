package com.fazekasgergo.healthmonitor.pages.questions

sealed class Question(val questionText: String, val resourceIds: Array<Int>) {

    class ChooseQuestion(question: String, val options: Array<ChooseOption>, imageResourceIds: Array<Int>) :
        Question(question, imageResourceIds)

    class InputQuestion(question: String, imageResourceId: Int) : Question(question, arrayOf(imageResourceId))
}
