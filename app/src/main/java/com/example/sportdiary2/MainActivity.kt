package com.example.sportdiary2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val context = this
        val db = DataBaseHandler(context)

        // get all Fields
        val button: Button = this.findViewById(R.id.button)
        button.setOnClickListener {
            var editTextDate: EditText = findViewById(R.id.editTextDate)
            var editTextSport: EditText = findViewById(R.id.editTextSport)
            var editTextFeeling: EditText = findViewById(R.id.editTextFeeling)
            var editTextNotes: EditText = findViewById(R.id.editTextNotes)

            // Checking if all fields are filled
            if (editTextDate.text.toString().isNotEmpty() &&
                editTextSport.text.toString().isNotEmpty() &&
                editTextFeeling.text.toString().isNotEmpty() &&
                editTextNotes.text.toString().isNotEmpty()
            ) {
                //putting all fields in a list
                val entries = Entries(
                    editTextDate.text.toString(), editTextSport.text.toString(),
                    editTextFeeling.text.toString(), editTextNotes.text.toString()
                )
                //insert the list into the database
                db.insertData(entries)
                //clear all fields
                editTextDate.text.clear()
                editTextSport.text.clear()
                editTextFeeling.text.clear()
                editTextNotes.text.clear()

            } else {
                Toast.makeText(context, "Please Fill All Data", Toast.LENGTH_SHORT).show()
            }
        }
        //write out the database values to a scrollable text field
        val buttonRead: Button = this.findViewById(R.id.button_read)
        val tvResult: TextView = findViewById(R.id.tvResult)
        buttonRead.setOnClickListener {
            tvResult.text = ""
            val dbHandler = DataBaseHandler(this)
            val cursor = dbHandler.readData()
            cursor!!.moveToFirst()
            tvResult.append((cursor.getString(cursor.getColumnIndex(COL_DATE))))
            tvResult.append((cursor.getString(cursor.getColumnIndex(COL_SPORT))))
            tvResult.append((cursor.getString(cursor.getColumnIndex(COL_FEELING))))
            tvResult.append((cursor.getString(cursor.getColumnIndex(COL_NOTES))))
            while (cursor.moveToNext()) {
                tvResult.append((cursor.getString(cursor.getColumnIndex(COL_DATE))))
                tvResult.append("\t\t")
                tvResult.append((cursor.getString(cursor.getColumnIndex(COL_SPORT))))
                tvResult.append("\t\t")
                tvResult.append((cursor.getString(cursor.getColumnIndex(COL_FEELING))))
                tvResult.append("\t\t")
                tvResult.append((cursor.getString(cursor.getColumnIndex(COL_NOTES))))
                tvResult.append("\n\n")
            }
            cursor.close()
        }
    }
}



