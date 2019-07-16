package com.example.roompractice

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.arch.lifecycle.ViewModelProviders
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.Toast
import butterknife.ButterKnife
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_add_student.*


class AddStudentActivity : AppCompatActivity() {

    private lateinit var module : StudentViewModule
    var studentDatabase : StudentDatabase ?=null

    companion object{
        val IMAGE_PICK_CODE = 1000
        val IMAGE_CAPTURE_CODE = 1002
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)
        studentDatabase = StudentDatabase.getInsance(this)
        module = ViewModelProviders.of(this).get(StudentViewModule::class.java)

        ButterKnife.bind(this)
        saveBTN.setOnClickListener {save()}
        photoFromGallery.setOnClickListener { requestStoragePermissionForGallery() }
        photoFromCamera.setOnClickListener {requestStoragePermissionForCamera() }
    }

    private fun save(){
 //     val pp = (profilephoto.drawable as BitmapDrawable).bitmap
  //      val stream = ByteArrayOutputStream()
    //    bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
      //  val pp = stream.toByteArray()


            val bitmap = BitmapFactory.decodeResource(resources ,R.id.profilephoto2 )
          //  val profilePic = findViewById<ImageView>(R.id.profilephoto).setImageBitmap(bitmap)
            val name = findViewById<EditText>(R.id.nameET).text.toString()
            val address = findViewById<EditText>(R.id.addressET).text.toString()
            val course = findViewById<EditText>(R.id.courseET).text.toString()
            val id =  Integer.parseInt(findViewById<EditText>(R.id.idET).text.toString())

        insertToDb(StudentEntites(id , name , course , address  ))
        Toast.makeText(this , "new student have been added" , Toast.LENGTH_SHORT).show()
        nameET.setText("")
        addressET.setText("")
        courseET.setText("")
        idET.setText("")
    }

    fun insertToDb(studentEntites: StudentEntites){
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(Observable.fromCallable{studentDatabase?.studentDao()?.insert(studentEntites)}
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe())
    }


    @SuppressLint("ObsoleteSdkInt")
    private fun requestStoragePermissionForGallery() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            if (checkSelfPermission(READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                showSettingDialog()
            } else {
                loadImageFromGallery()
            }
        } else {
            loadImageFromGallery()
        }

    }

    @SuppressLint("ObsoleteSdkInt")
    private fun requestStoragePermissionForCamera() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            if (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_DENIED){
                showSettingDialog()
            }else {
                captureImage()
            }
        }else{
            captureImage()
        }

    }

    private fun showSettingDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Needs Permissions")
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
        builder.setPositiveButton("GOTO SETTINGS") { dialog, _->
            dialog.dismiss()
            openSetting()
        }
        builder.setNegativeButton("Canel"){ dialog, _->
            dialog.dismiss()

        }
        builder.show()
    }

    private fun openSetting(){
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package" , packageName , null)
        intent.data = uri
        startActivityForResult(intent , 1001)
    }

    private fun loadImageFromGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent , IMAGE_PICK_CODE)
    }

    private fun captureImage(){
        val values = ContentValues()
        values.put(MediaStore.Images.Media.MIME_TYPE , "image/jpg")
        val fileUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI , values)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null){
            intent.putExtra(MediaStore.EXTRA_OUTPUT , fileUri)
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivityForResult(intent , IMAGE_CAPTURE_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            profilephoto.setImageURI(data?.data)
        }
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_CAPTURE_CODE){
            profilephoto.setImageURI(data?.data)
        }
    }


}