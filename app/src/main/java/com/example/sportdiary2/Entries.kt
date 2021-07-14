package com.example.sportdiary2

class Entries {
        //(var editTextDate: String, var editTextSport: String, var editTextFeeling: String,
        //var editTextNotes: String){
        //var id: Int = 0
        var date: String = ""
        var sport: String = ""
        var feeling: String = ""
        var notes: String = ""
        constructor(date: String, sport: String, feeling: String, notes: String) {
                this.date = date
                this.sport = sport
                this.feeling = feeling
                this.notes = notes
        }
        constructor(userName: String) {
                this.date = date
                this.sport = sport
                this.feeling = feeling
                this.notes = notes
        }
}
