package com.ajhavery.jettriviaapp.repository

import android.util.Log
import com.ajhavery.jettriviaapp.data.DataOrException
import com.ajhavery.jettriviaapp.model.QuestionItem
import com.ajhavery.jettriviaapp.network.QuestionApi
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val api: QuestionApi) {
    // listOfQuestions is of type DataOrException
    private val dataOrException
        = DataOrException<ArrayList<QuestionItem>, Boolean, Exception>()

    suspend fun getAllQuestions(): DataOrException<ArrayList<QuestionItem>, Boolean, Exception> {
        try {
            dataOrException.loading = true
            dataOrException.data = api.getAllQuestions()
            if(dataOrException.data.toString().isNotEmpty()) dataOrException.loading = false
        } catch (exception: Exception) {
            dataOrException.e = exception
            Log.d("Exception: ", "getAllQuestions: ${dataOrException.e!!.localizedMessage}")
        }
        return dataOrException
    }
}