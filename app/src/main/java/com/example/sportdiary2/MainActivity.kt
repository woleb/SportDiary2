package com.example.sportdiary2

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    var button_date: Button? = null
    //var textview_date: TextView? = null
    var cal = Calendar.getInstance()
    //var editTextDate: TextView = findViewById(R.id.editTextDate)
    lateinit var editTextDate: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val context = this
        val db = DataBaseHandler(context)

        // get the references from layout file
        //textview_date = this.findViewById(R.id.text_view_date_1)
        button_date = this.findViewById(R.id.button_date_1)
        editTextDate = findViewById(R.id.editTextDate) as TextView
        editTextDate!!.text = "--/--/----"

        // create an OnDateSetListener
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
                /*
                val myFormat = "dd/MM/yyyy" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.GERMAN)
                editTextDate!!.text = sdf.format(cal.getTime())
                */

            }
        }

        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        button_date!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@MainActivity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }

        })


        // get all Fields
        val button: Button = this.findViewById(R.id.button)
        button.setOnClickListener {

            //var editTextDate: TextView = findViewById(R.id.editTextDate)
            var editTextSport: EditText = findViewById(R.id.editTextSport)
            var editTextFeeling: EditText = findViewById(R.id.editTextFeeling)
            var editTextNotes: EditText = findViewById(R.id.editTextNotes)

            //editTextDate!!.text = "--/--/----"


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
                //editTextDate.text.clear()
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

    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.GERMAN)
        editTextDate!!.text = sdf.format(cal.getTime())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

}



