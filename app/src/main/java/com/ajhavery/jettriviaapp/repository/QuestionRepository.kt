package com.ajhavery.jettriviaapp.repository

import com.ajhavery.jettriviaapp.model.QuestionItem
import com.ajhavery.jettriviaapp.network.QuestionApi
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val api: QuestionApi) {
    private val listOfQuestions = ArrayList<QuestionItem>(emptyList())
}