package com.fazekasgergo.healthmonitor.pages.questions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuestionViewModel : ViewModel() {
    val totalQuestions = 6

    private val _questionNumber = MutableLiveData<Int>()
    val questionNumber: LiveData<Int> get() = _questionNumber

    private val _eventGoToNextQuestion = MutableLiveData<Boolean>()
    val eventGoToNextQuestion: LiveData<Boolean> get() = _eventGoToNextQuestion

    private val _eventFinishedQuestions = MutableLiveData<Boolean>()
    val eventFinishedQuestions: LiveData<Boolean> get() = _eventFinishedQuestions


    init {
        _questionNumber.value = 1
    }

    fun nextQuestion() {
        val nextQuestionNumber = questionNumber.value?.plus(1)
        if (nextQuestionNumber != null) {
            if (nextQuestionNumber == totalQuestions + 1) onFinishedQuestionsEvent()
            else if (nextQuestionNumber <= totalQuestions) {
                _questionNumber.value = nextQuestionNumber
                onGoToNextQuestionEvent()
            }
        }
    }

    fun previousQuestion() {
        val previousQuestionNumber = questionNumber.value?.minus(1)
        if (previousQuestionNumber != null && previousQuestionNumber >= 1)
            _questionNumber.value = previousQuestionNumber
    }

    private fun onGoToNextQuestionEvent() {
        _eventGoToNextQuestion.value = true
    }

    fun onGoToNextQuestionEventCompleted() {
        _eventGoToNextQuestion.value = false
    }

    private fun onFinishedQuestionsEvent() {
        _eventFinishedQuestions.value = true
    }

    fun onFinishedQuestionsEventCompleted() {
        _eventFinishedQuestions.value = false
    }

}