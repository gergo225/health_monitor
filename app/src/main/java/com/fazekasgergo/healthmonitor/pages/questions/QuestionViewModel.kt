package com.fazekasgergo.healthmonitor.pages.questions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.fazekasgergo.healthmonitor.R

class QuestionViewModel : ViewModel() {
    private val questions: Array<Question> = arrayOf(
        Question.ChooseQuestion(
            "Select a Gender", arrayOf(GenderOptions.FEMALE, GenderOptions.MALE),
            arrayOf(R.drawable.ic_female, R.drawable.ic_male)
        ),
        Question.ChooseQuestion(
            "Select your Age Group",
            arrayOf(
                AgeGroups.TWO_FIVE,
                AgeGroups.SIX_THIRTEEN,
                AgeGroups.FOURTEEN_EIGHTEEN,
                AgeGroups.NINETEEN_THIRTY,
                AgeGroups.THIRTYONE_FIFTY,
                AgeGroups.FIFTY_PLUS
            ),
            arrayOf()
        ),
        Question.InputQuestion("Enter your weight (kg)", R.drawable.ic_scale),
        Question.InputQuestion("Enter you height (cm)", R.drawable.ic_measuring),
        Question.ChooseQuestion(
            "Are you a smoker?",
            arrayOf(
                TobaccoConsumption.NON_SMOKER,
                TobaccoConsumption.PASSIVE,
                TobaccoConsumption.EX_SMOKER,
                TobaccoConsumption.SMOKER
            ),
            arrayOf(
                R.drawable.ic_non_smoker,
                R.drawable.ic_passive_smoker,
                R.drawable.ic_ex_smoker,
                R.drawable.ic_smoker
            )
        ),
        Question.ChooseQuestion(
            "Do you drink alcohol?",
            arrayOf(
                AlcoholConsumption.NOT_AT_ALL,
                AlcoholConsumption.OCCASIONAL,
                AlcoholConsumption.WEEKLY,
                AlcoholConsumption.DAILY
            ),
            arrayOf(
                R.drawable.ic_no_alcohol,
                R.drawable.ic_occasional_alcohol,
                R.drawable.ic_every_week_alcohol,
                R.drawable.ic_every_day_alcohol
            )
        )
    )

    private val _answers = Answers()
    val answers get() = _answers

    val currentAnswer: Int?
        get() = when (questionNumber.value) {
            0 -> _answers.gender?.ordinal
            1 -> _answers.ageGroup?.ordinal
            2 -> _answers.weight
            3 -> _answers.height
            4 -> _answers.tobacco?.ordinal
            5 -> _answers.alcohol?.ordinal
            else -> null
        }

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

    private fun updateCurrentAnswer(currentAnswer: Int) {
        when (questionNumber.value) {
            0 -> _answers.gender = GenderOptions.values()[currentAnswer]
            1 -> _answers.ageGroup = AgeGroups.values()[currentAnswer]
            2 -> _answers.weight = currentAnswer
            3 -> _answers.height = currentAnswer
            4 -> _answers.tobacco = TobaccoConsumption.values()[currentAnswer]
            5 -> _answers.alcohol = AlcoholConsumption.values()[currentAnswer]
        }
    }

    private fun setAgeGroupIcons() {
        val iconResourceIds =
            if (_answers.gender == GenderOptions.FEMALE)
                arrayOf(
                    R.drawable.ic_female_age_group1,
                    R.drawable.ic_female_age_group2,
                    R.drawable.ic_female_age_group3,
                    R.drawable.ic_female_age_group4,
                    R.drawable.ic_female_age_group5,
                    R.drawable.ic_female_age_group6
                )
            else
                arrayOf(
                    R.drawable.ic_male_age_group1,
                    R.drawable.ic_male_age_group2,
                    R.drawable.ic_male_age_group3,
                    R.drawable.ic_male_age_group4,
                    R.drawable.ic_male_age_group5,
                    R.drawable.ic_male_age_group6
                )

        questions[1].resourceIds = iconResourceIds
    }

    fun nextQuestion(currentAnswer: Int) {
        updateCurrentAnswer(currentAnswer)

        val nextQuestionNumber = questionNumber.value?.plus(1)
        if (nextQuestionNumber != null) {
            if (nextQuestionNumber >= totalQuestions) onFinishedQuestionsEvent()
            else if (nextQuestionNumber < totalQuestions) {
                if (nextQuestionNumber == 1) setAgeGroupIcons()

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