package com.fazekasgergo.healthmonitor.pages.questions

import java.io.Serializable

data class Answers(
    var gender: GenderOptions? = null,
    var ageGroup: AgeGroups? = null,
    var weight: Int? = null,
    var height: Int? = null,
    var tobacco: TobaccoConsumption? = null,
    var alcohol: AlcoholConsumption? = null,
    var food: Food? = null,
    var work: Work? = null,
    var bloodPressure: BloodPressure? = null,
    var familyDiseases: FamilyDiseases? = null
) : Serializable
