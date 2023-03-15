package com.example.realtime1

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.get
import com.example.realtime1.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    var count : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Write a message to the database
        val database = Firebase.database
        val myRef = database.getReference()
        binding.btnSave.setOnClickListener {
            val name = binding.txtName.text.toString()
            val id = binding.txtId.text.toString()
            val age = binding.txtAge.text.toString()
            val person = hashMapOf(
                "name" to name,
                "id" to id,
                "age" to age
            )
            myRef.child("person").child("$count").setValue(person)
            count++
            Toast.makeText(applicationContext,"Done",Toast.LENGTH_LONG).show()
        }
        binding.btnGet.setOnClickListener {
            myRef.addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val value = snapshot.getValue()
                        binding.txtData.text = value.toString()
                        Toast.makeText(applicationContext,"Done",Toast.LENGTH_LONG).show()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(applicationContext,"Fail",Toast.LENGTH_LONG).show()
                }

            })
        }

    }
}