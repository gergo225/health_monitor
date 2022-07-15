package com.fazekasgergo.healthmonitor.pages.questions

interface ChooseOption {
    fun text(): String
}

enum class GenderOptions(private val gender: String) : ChooseOption {
    FEMALE("Female"),
    MALE("Male");

    override fun text(): String {
        return gender
    }
}

enum class AgeGroups(private val group: String): ChooseOption {
    TWO_FIVE("2-5"),
    SIX_THIRTEEN("6-13"),
    FOURTEEN_EIGHTEEN("14-18"),
    NINETEEN_THIRTY("19-30"),
    THIRTYONE_FIFTY("31-50"),
    FIFTY_PLUS("50+");

    override fun text(): String {
        return this.group
    }
}

enum class TobaccoConsumption(private val consumption: String) : ChooseOption {
    NON_SMOKER("Non-smoker"),
    PASSIVE("Passive smoker"),
    EX_SMOKER("Ex-smoker"),
    SMOKER("Smoker");

    override fun text(): String {
        return this.consumption
    }
}

enum class AlcoholConsumption(private val consumption: String) : ChooseOption {
    NOT_AT_ALL("Not at all"),
    OCCASIONAL("Occasional"),
    WEEKLY("Every week"),
    DAILY("Every day");

    override fun text(): String {
        return this.consumption
    }
}

enum class Food(private val food: String) : ChooseOption {
    FRUITS_VEGETABLES("Fruits & vegetables"),
    MEAT("Meat"),
    VEGETARIAN("Vegetarian"),
    FAST_FOOD("Fast Food");

    override fun text(): String {
        return this.food
    }
}

enum class Work(private val environment: String) : ChooseOption {
    TOXIC("Toxi"),
    STRESSFUL("Stressful"),
    SEDENTARY("Sedentary"),
    COMMON_LABOR("Common labor");

    override fun text(): String {
        return environment
    }
}

enum class BloodPressure(private val pressure: String) : ChooseOption {
    LOW("Low"),
    NORMAL("Normal"),
    HIGH("High"),
    DONT_KNOW("I don't know");

    override fun text(): String {
        return pressure
    }
}

enum class FamilyDiseases(private val disease: String) : ChooseOption {
    DIABETES("Diabetes"),
    CARDIOVASCULAR("Cardiovascular diseases"),
    LIVER("Liver diseases"),
    KIDNEY("Kidney diseases");

    override fun text(): String {
        return disease
    }
}


