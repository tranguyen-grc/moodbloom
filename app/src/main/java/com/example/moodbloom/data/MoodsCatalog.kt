package com.example.moodbloom.data

object MoodsCatalog {
    val moodsByFamily: Map<MoodFamily, List<Mood>> = mapOf(
        MoodFamily.LOVE to listOf(
            Mood("love-grateful", "grateful", MoodFamily.LOVE),
            Mood("love-sentimental", "sentimental", MoodFamily.LOVE),
            Mood("love-affectionate", "affectionate", MoodFamily.LOVE),
            Mood("love-romantic", "romantic", MoodFamily.LOVE),
            Mood("love-enchanted", "enchanted", MoodFamily.LOVE)
        ),
        MoodFamily.ANGER to listOf(
            Mood("anger-enraged", "enraged", MoodFamily.ANGER),
            Mood("anger-exasperated", "exasperated", MoodFamily.ANGER),
            Mood("anger-irritable", "irritable", MoodFamily.ANGER),
            Mood("anger-jealous", "jealous", MoodFamily.ANGER),
            Mood("anger-disgusted", "disgusted", MoodFamily.ANGER)
        ),
        MoodFamily.JOY to listOf(
            Mood("joy-excited", "excited", MoodFamily.JOY),
            Mood("joy-optimistic", "optimistic", MoodFamily.JOY),
            Mood("joy-proud", "proud", MoodFamily.JOY),
            Mood("joy-cheerful", "cheerful", MoodFamily.JOY),
            Mood("joy-content", "content", MoodFamily.JOY),
            Mood("joy-peaceful", "peaceful", MoodFamily.JOY),
        ),
        MoodFamily.SURPRISE to listOf(
            Mood("surprise-curious", "curious", MoodFamily.SURPRISE),
            Mood("surprise-astounded", "astounded", MoodFamily.SURPRISE),
            Mood("surprise-amazed", "amazed", MoodFamily.SURPRISE),
            Mood("surprise-confused", "confused", MoodFamily.SURPRISE),
            Mood("surprise-moved", "moved", MoodFamily.SURPRISE),
        ),
        MoodFamily.SADNESS to listOf(
            Mood("sad-gloomy", "gloomy", MoodFamily.SADNESS),
            Mood("sad-lonely", "lonely", MoodFamily.SADNESS),
            Mood("sad-shameful", "shameful", MoodFamily.SADNESS),
            Mood("sad-disappointed", "disappointed", MoodFamily.SADNESS),
            Mood("sad-depressed", "depressed", MoodFamily.SADNESS),
            Mood("sad-hurt", "hurt", MoodFamily.SADNESS)
        ),
        MoodFamily.FEAR to listOf(
            Mood("fear-anxious", "anxious",  MoodFamily.FEAR),
            Mood("fear-afraid", "afraid",   MoodFamily.FEAR),
            Mood("fear-insecure", "insecure", MoodFamily.FEAR),
            Mood("fear-horrified", "horrified",   MoodFamily.FEAR),
        )
    )
}
