package uk.ac.tees.W9618430.tictoccloneapp.model

data class UserModel(
    var id : String = "",
    var email : String ="",
    var username : String ="",
    var profilePic : String ="",
    var follwerlist : MutableList<String> = mutableListOf(),
    var follwingList : MutableList<String> = mutableListOf()
)