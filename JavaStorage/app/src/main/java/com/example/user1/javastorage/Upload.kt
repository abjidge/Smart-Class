package com.example.user1.javastorage

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.OnProgressListener
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

class Upload : AppCompatActivity() {

    lateinit var selectFile: Button
   lateinit var upload: Button
    lateinit var notification: TextView
    lateinit var name: EditText
   lateinit var pdfUri: Uri

   lateinit var storage: FirebaseStorage
    lateinit var database: FirebaseDatabase
   lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.upload)

        storage = FirebaseStorage.getInstance()
        database = FirebaseDatabase.getInstance()


        selectFile = findViewById(R.id.selectFile)
        upload = findViewById(R.id.upload)
        notification = findViewById(R.id.notification)

        selectFile.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this@Upload, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                selectPdf()
            } else
                ActivityCompat.requestPermissions(this@Upload, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 9)
        }

        upload.setOnClickListener {
            if (pdfUri != null)
                uploadFile(pdfUri)
            else
                Toast.makeText(this@Upload, "Please select a file", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadFile(pdfUri: Uri?) {

        progressDialog = ProgressDialog(this)
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        progressDialog.setTitle("Uploading")
        progressDialog.progress = 0
        progressDialog.show()

       // val fileName = System.currentTimeMillis().toString() + ""
        //val fileName = findViewById<EditText>(R.id.name).toString().trim() + ""
        name = findViewById(R.id.name)
        val fileName = name.text.toString()
        val storageReference = storage.reference

        storageReference.child("Uploads").child(fileName).putFile(pdfUri!!).addOnSuccessListener { taskSnapshot ->
            val url = taskSnapshot.downloadUrl!!.toString()
            val reference = database.reference
            reference.child(fileName).setValue(url).addOnCompleteListener { task ->
                if (task.isSuccessful)
                    Toast.makeText(this@Upload, "File is successfully uploded", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(this@Upload, "file not uploded", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { Toast.makeText(this@Upload, "file not uploded failure", Toast.LENGTH_SHORT).show() }.addOnProgressListener { taskSnapshot ->
            val currentProgress = (100 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount).toInt()
            progressDialog.progress = currentProgress
        }


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        if (requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            selectPdf()
        } else
            Toast.makeText(this@Upload, "Please provoid permission", Toast.LENGTH_SHORT).show()

    }

    private fun selectPdf() {

        val intent = Intent()
        intent.type = "application/pdf"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 86)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 86 && resultCode == Activity.RESULT_OK && data != null) {
            pdfUri = data.data
            notification.text = "File is selected:" + data.data!!.lastPathSegment
        } else {
            Toast.makeText(this@Upload, "Please select a file", Toast.LENGTH_SHORT).show()
        }

    }
}

