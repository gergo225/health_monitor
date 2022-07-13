package com.fazekasgergo.healthmonitor.pages.questions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class QuestionViewModel : ViewModel() {
    private val questions: Array<Question> = arrayOf(
        Question.ChooseQuestion("Select a Gender", arrayOf(GenderOption.FEMALE, GenderOption.MALE)),
        Question.ChooseQuestion(
            "Select your Age Group",
            arrayOf(
                AgeGroups.TWO_FIVE,
                AgeGroups.SIX_THIRTEEN,
                AgeGroups.FOURTEEN_EIGHTEEN,
                AgeGroups.NINETEEN_THIRTY,
                AgeGroups.THIRTYONE_FIFTY,
                AgeGroups.FIFTY_PLUS
            )
        ),
        Question.InputQuestion("Enter your weight (kg)"),
        Question.InputQuestion("Enter you height (cm)"),
        Question.ChooseQuestion(
            "Are you a smoker?",
            arrayOf(
                TobaccoConsumption.NON_SMOKER,
                TobaccoConsumption.PASSIVE,
                TobaccoConsumption.EX_SMOKER,
                TobaccoConsumption.SMOKER
            )
        ),
        Question.ChooseQuestion(
            "Do you drink alcohol?",
            arrayOf(
                AlcoholConsumption.NOT_AT_ALL,
                AlcoholConsumption.OCCASIONAL,
                AlcoholConsumption.WEEKLY,
                AlcoholConsumption.DAILY
            )
        )
    )

    val totalQuestions = questions.size

    private val _questionNumber = MutableLiveData<Int>()
    val questionNumber: LiveData<Int> get() = _questionNumber

    val currentQuestion = Transformations.map(questionNumber) { index ->
        questions[index]
    }

    private val _eventGoToNextQuestion = MutableLiveData<Boolean>()
    val eventGoToNextQuestion: LiveData<Boolean> get() = _eventGoToNextQuestion

    private val _eventFinishedQuestions = MutableLiveData<Boolean>()
    val eventFinishedQuestions: LiveData<Boolean> get() = _eventFinishedQuestions

    init {
        _questionNumber.value = 0
    }

    fun nextQuestion() {
        val nextQuestionNumber = questionNumber.value?.plus(1)
        if (nextQuestionNumber != null) {
            if (nextQuestionNumber >= totalQuestions) onFinishedQuestionsEvent()
            else if (nextQuestionNumber < totalQuestions) {
                _questionNumber.value = nextQuestionNumber
                onGoToNextQuestionEvent()
            }
        }
    }

    fun previousQuestion() {
        val previousQuestionNumber = questionNumber.value?.minus(1)
        if (previousQuestionNumber != null && previousQuestionNumber >= 0)
            _questionNumber.value = previousQuestionNumber
    }

    private fun onGoToNextQuestionEvent() {
        _eventGoToNextQuestion.value = true
        _eventGoToNextQuestion.value = null
    }


    private fun onFinishedQuestionsEvent() {
        _eventFinishedQuestions.value = true
        _eventFinishedQuestions.value = null
    }

}