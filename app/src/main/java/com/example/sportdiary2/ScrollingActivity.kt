package com.example.sportdiary2

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.sportdiary2.databinding.ActivityScrollingBinding
import com.google.android.material.snackbar.Snackbar

class ScrollingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScrollingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title=title
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        
        val dbContent: TextView= findViewById(R.id.dbContent)
        dbContent.text = " "
        val dbHandler = DataBaseHandler(this)
        val cursor = dbHandler.readData()
        cursor!!.moveToFirst()
        dbContent.append((cursor.getString(cursor.getColumnIndex(COL_DATE))))
        dbContent.append((cursor.getString(cursor.getColumnIndex(COL_SPORT))))
        dbContent.append((cursor.getString(cursor.getColumnIndex(COL_FEELING))))
        dbContent.append((cursor.getString(cursor.getColumnIndex(COL_NOTES))))
        while (cursor.moveToNext()) {
            dbContent.append((cursor.getString(cursor.getColumnIndex(COL_DATE))))
            dbContent.append("\t\t")
            dbContent.append((cursor.getString(cursor.getColumnIndex(COL_SPORT))))
            dbContent.append("\t\t")
            dbContent.append((cursor.getString(cursor.getColumnIndex(COL_FEELING))))
            dbContent.append("\t\t")
            dbContent.append((cursor.getString(cursor.getColumnIndex(COL_NOTES))))
            dbContent.append("\n\n")
        }
        cursor.close()


    }
}