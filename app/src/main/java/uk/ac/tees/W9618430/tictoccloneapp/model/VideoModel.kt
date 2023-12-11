package uk.ac.tees.W9618430.tictoccloneapp.model

import android.icu.text.CaseMap.Title
import com.google.firebase.Timestamp
import java.net.URL

data class VideoModel(
    var videoId : String = "",
    var title: String = "",
    var url: String = "",
    var UploaderId : String = "",
    var createdTime : Timestamp = Timestamp.now()
)
