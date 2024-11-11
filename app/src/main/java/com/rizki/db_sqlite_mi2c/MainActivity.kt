package com.rizki.db_sqlite_mi2c

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rizki.db_sqlite_mi2c.helper.DbHelper
import com.rizki.db_sqlite_mi2c.helper.DbHelper.Companion.NAMA_KAMPUS

class MainActivity : AppCompatActivity() {

    private lateinit var etNama : EditText
    private lateinit var etKampus : EditText
    private lateinit var etEmail : EditText
    private lateinit var etPhone : EditText
    private lateinit var btnSubmit : Button
    private lateinit var btnShowData : Button
    private lateinit var txtNama : TextView
    private lateinit var txtKampus : TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        etNama = findViewById(R.id.etNama)
        etKampus = findViewById(R.id.etNamaKampus)
        etEmail = findViewById(R.id.etEmail)
        etPhone = findViewById(R.id.etPhone)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnShowData = findViewById(R.id.btnShowData)
        txtNama = findViewById(R.id.txtNama)
        txtKampus = findViewById(R.id.txtKampus)

        btnSubmit.setOnClickListener(){
            val db = DbHelper(this, null)

            //get data
            val name = etNama.text.toString()
            val kampus = etKampus.text.toString()
            val email = etEmail.text.toString()
            val phone = etPhone.text.toString()

            // calling method to add
            // name to our database
            db.addName(name, kampus, email, phone)

            // Toast to message on the screen
            Toast.makeText(this, name + " Berhasil input ke database", Toast.LENGTH_LONG).show()

            // at last, clearing edit texts
            etNama.text.clear()
            etKampus.text.clear()
            etEmail.text.clear()
            etPhone.text.clear()
        }

        btnSubmit.setOnClickListener(){
            // creating a DBHelper class
            // and passing context to it
            val db = DbHelper(this, null)

            // below is the variable for cursor
            // we have called method to get
            // all names from our database
            // and add to name text view
            val cursor = db.getName()

            // moving the cursor to first position and
            // appending value in the text view
            cursor!!.moveToFirst()
            txtNama.append(cursor.getString(cursor.getColumnIndex(DbHelper.NAMA_LENGKAP)) + "\n")
            txtKampus.append(cursor.getString(cursor.getColumnIndex(NAMA_KAMPUS)) + "\n")
//
//            // moving our cursor to next
//            // position and appending values
            while (cursor.moveToNext()) {
                txtNama.append(cursor.getString(cursor.getColumnIndex(DbHelper.NAMA_LENGKAP)) + "\n")
                txtKampus.append(cursor.getString(cursor.getColumnIndex(NAMA_KAMPUS)) + "\n")
            }

            // at last we close our cursor
            cursor.close()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}