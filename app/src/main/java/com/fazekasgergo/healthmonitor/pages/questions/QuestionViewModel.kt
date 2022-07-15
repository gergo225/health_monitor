package com.fazekasgergo.healthmonitor.pages.questions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.fazekasgergo.healthmonitor.R

class QuestionViewModel : ViewModel() {
    private val questions: Array<Question> = arrayOf(
        Question.ChooseQuestion(
            "Select a Gender",
            GenderOptions.values().map { gender -> gender }.toTypedArray(),
            arrayOf(R.drawable.ic_female, R.drawable.ic_male)
        ),
        Question.ChooseQuestion(
            "Select your Age Group",
            AgeGroups.values().map { ageGroup -> ageGroup }.toTypedArray(),
            arrayOf()
        ),
        Question.InputQuestion("Enter your weight (kg)", R.drawable.ic_scale),
        Question.InputQuestion("Enter you height (cm)", R.drawable.ic_measuring),
        Question.ChooseQuestion(
            "Are you a smoker?",
            TobaccoConsumption.values().map { consumption -> consumption }.toTypedArray(),
            arrayOf(
                R.drawable.ic_non_smoker,
                R.drawable.ic_passive_smoker,
                R.drawable.ic_ex_smoker,
                R.drawable.ic_smoker
            )
        ),
        Question.ChooseQuestion(
            "Do you drink alcohol?",
            AlcoholConsumption.values().map { consumption -> consumption }.toTypedArray(),
            arrayOf(
                R.drawable.ic_no_alcohol,
                R.drawable.ic_occasional_alcohol,
                R.drawable.ic_every_week_alcohol,
                R.drawable.ic_every_day_alcohol
            )
        ),
        Question.ChooseQuestion(
            "What type of food do you mostly eat?",
            Food.values().map { food -> food }.toTypedArray(),
            arrayOf(
                R.drawable.ic_nutrition_fruits_vegetables,
                R.drawable.ic_nutrition_meat,
                R.drawable.ic_nutrition_vegetarian,
                R.drawable.ic_nutrition_fast_food
            )
        ),
        Question.ChooseQuestion(
            "How is your work environment?",
            Work.values().map {environment -> environment }.toTypedArray(),
            arrayOf(
                R.drawable.ic_work1,
                R.drawable.ic_work2,
                R.drawable.ic_work3,
                R.drawable.ic_work4
            )
        ),
        Question.ChooseQuestion(
            "How is your blood pressure?",
            BloodPressure.values().map { pressure -> pressure }.toTypedArray(),
            emptyArray()
        ),
        Question.ChooseQuestion(
            "What diseases run in your family?",
            FamilyDiseases.values().map { disease -> disease }.toTypedArray(),
            emptyArray()
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
            6 -> _answers.food?.ordinal
            7 -> _answers.work?.ordinal
            8 -> _answers.bloodPressure?.ordinal
            9 -> _answers.familyDiseases?.ordinal
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
            6 -> _answers.food = Food.values()[currentAnswer]
            7 -> _answers.work = Work.values()[currentAnswer]
            8 -> _answers.bloodPressure = BloodPressure.values()[currentAnswer]
            9 -> _answers.familyDiseases = FamilyDiseases.values()[currentAnswer]
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