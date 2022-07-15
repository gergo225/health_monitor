package com.fazekasgergo.healthmonitor.pages.results

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fazekasgergo.healthmonitor.R
import com.fazekasgergo.healthmonitor.pages.questions.*
import kotlin.math.pow

class ResultsViewModel(application: Application) : AndroidViewModel(application) {
    private val _diseaseTests = MutableLiveData<Array<DiseaseTests>>()
    val diseaseTests: LiveData<Array<DiseaseTests>> get() = _diseaseTests


    fun processAnswers(answers: Answers) {
        val tests = arrayListOf<DiseaseTests>()

        val gender = answers.gender!!
        val ageGroup = answers.ageGroup!!
        val weight = answers.weight!!
        val height = answers.height!!
        val tobacco = answers.tobacco!!
        val alcohol = answers.alcohol!!
        val food = answers.food!!
        val bloodPressure = answers.bloodPressure!!
        val work = answers.work!!
        val familyDiseases = answers.familyDiseases!!

        val bmiVal = weight / (height.toFloat() / 100).pow(2)
        val bmi =
            if (bmiVal < 18.5) BMI.UNDERWEIGHT
            else if (bmiVal < 25) BMI.NORMAL
            else if (bmiVal < 30) BMI.OVERWEIGHT
            else if (bmiVal < 35) BMI.OBESE
            else BMI.EXTREMELY_OBESE

        val diabetes = evaluateDisease(
            arrayOf(
                ageGroup == AgeGroups.NINETEEN_THIRTY,
                ageGroup == AgeGroups.FIFTY_PLUS,
                gender == GenderOptions.MALE,
                bmi == BMI.OVERWEIGHT,
                bmi == BMI.OBESE,
                bmi == BMI.EXTREMELY_OBESE,
                familyDiseases == FamilyDiseases.DIABETES,
                food == Food.FAST_FOOD,
                bloodPressure == BloodPressure.HIGH
            ),
            arrayOf(1, 2, 1, 1, 2, 3, 1, 1, 1),
            5
        )
        if (diabetes)
            tests.add(
                DiseaseTests(
                    R.string.diabetes,
                    R.array.diabetes_test_names,
                    R.array.diabetes_test_descriptions
                )
            )

        val cardiovascularDisease = evaluateDisease(
            arrayOf(
                ageGroup == AgeGroups.NINETEEN_THIRTY,
                ageGroup == AgeGroups.FIFTY_PLUS,
                gender == GenderOptions.MALE,
                bmi == BMI.OVERWEIGHT,
                bmi == BMI.OBESE,
                bmi == BMI.EXTREMELY_OBESE,
                familyDiseases == FamilyDiseases.CARDIOVASCULAR,
                tobacco == TobaccoConsumption.SMOKER,
                bloodPressure == BloodPressure.HIGH
            ),
            arrayOf(1, 2, 1, 1, 2, 3, 1, 1, 1),
            5
        )
        if (cardiovascularDisease)
            tests.add(DiseaseTests(R.string.cardio, R.array.cardio_test_names, R.array.cardio_test_descriptions))

        val liverDisease = evaluateDisease(
            arrayOf(
                alcohol == AlcoholConsumption.WEEKLY,
                alcohol == AlcoholConsumption.DAILY,
                bmi == BMI.OVERWEIGHT,
                bmi == BMI.OBESE,
                bmi == BMI.EXTREMELY_OBESE,
                familyDiseases == FamilyDiseases.LIVER,
                work == Work.TOXIC
            ),
            arrayOf(1, 2, 1, 2, 3, 1, 1),
            4
        )
        if (liverDisease)
            tests.add(DiseaseTests(R.string.liver, R.array.liver_test_names, R.array.liver_test_descriptions))

        val kidneyDisease = evaluateDisease(
            arrayOf(
                bmi == BMI.OVERWEIGHT,
                bmi == BMI.OBESE,
                bmi == BMI.EXTREMELY_OBESE,
                bloodPressure == BloodPressure.HIGH,
                tobacco == TobaccoConsumption.SMOKER,
                familyDiseases == FamilyDiseases.KIDNEY,
                ageGroup == AgeGroups.FIFTY_PLUS
            ),
            arrayOf(1, 2, 3, 1, 1, 1, 1),
            4
        )
        if (kidneyDisease)
            tests.add(DiseaseTests(R.string.kidney, R.array.kidney_test_names, R.array.kidney_test_descriptions))

        _diseaseTests.value = tests.toTypedArray()
    }

    private fun evaluateDisease(
        conditions: Array<Boolean>,
        points: Array<Int>,
        pointThreshold: Int
    ): Boolean {
        var accumulatedPoints = 0
        for (i in conditions.indices)
            if (conditions[i]) accumulatedPoints += points[i]

        return (accumulatedPoints >= pointThreshold)
    }
}