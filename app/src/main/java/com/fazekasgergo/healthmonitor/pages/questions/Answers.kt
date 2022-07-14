package com.fazekasgergo.healthmonitor.pages.questions

data class Answers(
    var gender: GenderOptions? = null,
    var ageGroup: AgeGroups? = null,
    var weight: Int? = null,
    var height: Int? = null,
    var tobacco: TobaccoConsumption? = null,
    var alcohol: AlcoholConsumption? = null
)
