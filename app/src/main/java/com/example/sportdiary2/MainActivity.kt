package com.example.sportdiary2

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
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

        /*
         Sport Spinner
         Find View by id for editTextSport
         */
        val spinner_sport=findViewById<Spinner>(R.id.editTextSport) as Spinner
        //Create An Array which Contain race_minute name
        val sport = arrayOf("Swimming","Cycling","Running","Strength","Yoga","Stretching")
            //(0..59).toList().toTypedArray()
        //val race_minute = arrayOf("0","1")

        //Set Array in Adapter for duration
        val adapter_sport=ArrayAdapter(this@MainActivity,android.R.layout.simple_spinner_dropdown_item,sport)
        spinner_sport.adapter=adapter_sport

        spinner_sport.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val customPositionToast =
                    Toast.makeText(applicationContext, "Toast message at the top", Toast.LENGTH_SHORT)
                customPositionToast.setGravity(Gravity.BOTTOM, 0, 0)
                customPositionToast.show()
                //TODO("Is it really needed here?")
            }

        }


        // get all Fields
        val button: Button = this.findViewById(R.id.button)
        button.setOnClickListener {

            //var editTextDate: TextView = findViewById(R.id.editTextDate)
            var editTextSport: Spinner = findViewById(R.id.editTextSport)
            var editTextFeeling: EditText = findViewById(R.id.editTextFeeling)
            var editTextNotes: EditText = findViewById(R.id.editTextNotes)

            //editTextDate!!.text = "--/--/----"


            // Checking if all fields are filled
            if (editTextDate.text.toString().isNotEmpty() &&
                editTextSport.selectedItem.toString().isNotEmpty() &&
                editTextFeeling.text.toString().isNotEmpty() &&
                editTextNotes.text.toString().isNotEmpty()
            ) {
                //putting all fields in a list
                val entries = Entries(
                    editTextDate.text.toString(), editTextSport.selectedItem.toString(),
                    editTextFeeling.text.toString(), editTextNotes.text.toString()
                )
                //insert the list into the database
                db.insertData(entries)
                //clear all fields
                //editTextDate.text.clear()
                //editTextSport.text.clear()
                editTextFeeling.text.clear()
                editTextNotes.text.clear()

            } else {
                Toast.makeText(context, "Please Fill All Data", Toast.LENGTH_SHORT).show()
            }
        }

        //write out the database values to a scrollable text field
        val buttonRead: Button = this.findViewById(R.id.button_read)
        //val tvResult: TextView = findViewById(R.id.tvResult)
        buttonRead.setOnClickListener {
        /*    tvResult.text = ""
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
            cursor.close() */
            this.launchIntent()
        }
    }

    private fun launchIntent() {
        val i = Intent(this, ScrollingActivity::class.java)
        startActivity(i)
    }

    //update the Date Field
    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.GERMAN)
        editTextDate!!.text = sdf.format(cal.getTime())
    }

    //Menu creation
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    //What happens when a menu item is selected
    //@RequiresApi(Build.VERSION_CODES.R)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val fh = FileExportImportHelper(this)

        when (item.itemId) {
            R.id.export_csv -> {
                ///storage/emulated/0/
                fh.ExportToFile()
                Log.d("export_csv", "done")
                return true
            }
            R.id.import_csv -> {
                Log.d("import_csv", "done")
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }

    }


}



