package com.ajhavery.jetnoteapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
// this creates dependency layer at the top Application level
class NoteApplication: Application() {
}