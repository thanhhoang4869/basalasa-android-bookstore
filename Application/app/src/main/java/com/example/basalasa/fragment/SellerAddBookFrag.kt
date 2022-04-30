package com.example.basalasa.fragment


import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.PermissionChecker.checkSelfPermission
import com.example.basalasa.activity.Login
import com.example.basalasa.databinding.FragmentSellerAddBookBinding
import com.example.basalasa.model.reponse.AddBookResponse
import com.example.basalasa.utils.Cache
import com.example.basalasa.utils.MyAPI
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.lang.Integer.parseInt
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.Logger


class SellerAddBookFrag(private val user: String) : Fragment() {
    private lateinit var _binding: FragmentSellerAddBookBinding
    private val binding get() = _binding
    var requestcode:Int = 1
    private var MY_REQUEST_CODE:Int = 1
    var uri:Uri ?=null
    var file:File?=null
    private var mediaPath: String? = null
    private var postPath: String? = null
    var mImageFileLocation:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentSellerAddBookBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val token = context?.let { Cache.getToken(it) }
        if (token === null) {
            val intent_ = Intent(context, Login::class.java)
            startActivity(intent_)
        }
        binding.btnChoose.setOnClickListener(View.OnClickListener(){
            onClickPermission()
        })
        binding.addBook.setOnClickListener(({

            var etTitle:RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),binding.etTitle.text.toString())
            var etAuthor:RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),binding.etAuthor.text.toString())
            var etDistributor:RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),binding.etDistributor.text.toString())
            var user:RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),user)
            var etPrice:RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),binding.etPrice.text.toString())
            var etSaleprice:RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),binding.etSaleprice.text.toString())
            var etCategory:RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),binding.etCategory.text.toString())
            var etRelease:RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),binding.etRelease.text.toString())
            var etDescription:RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),binding.etDescription.text.toString())
            var etQuantity:RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),binding.etQuantity.text.toString())
            var etState:RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),"1")
            var etStar:RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),"0")


            var requestbodyImg:RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file)
            var multipart:MultipartBody.Part = MultipartBody.Part.createFormData("image",file?.name,requestbodyImg)
            val response = MyAPI.getAPI().addBook(token.toString(),
                etTitle,
                etAuthor,
                etDistributor,
                user,
                etPrice,
                etSaleprice,
                etCategory,
                etRelease,
                etDescription,
                etQuantity,
                etState,
                etStar,multipart)

            System.out.println("TEST")
            response.enqueue(object : Callback<AddBookResponse> {
                override fun onResponse(call: Call<AddBookResponse>, response: Response<AddBookResponse>) {

                    if (response.isSuccessful) {
                        System.out.println("SUCCESS")
                    }
                }

                override fun onFailure(call: Call<AddBookResponse>, t: Throwable) {
                    System.out.println("FAILED")
                    t.printStackTrace()
                }

            })
        }))
    }
    fun onClickPermission(){

        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            System.out.println("TEST")
            return;
        }
        if(context?.let { checkSelfPermission(it,Manifest.permission.READ_EXTERNAL_STORAGE) } ==PackageManager.PERMISSION_GRANTED){
            openGallery()
        }else{

            var permission = arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(permission,MY_REQUEST_CODE)
        }
    }
    fun openGallery(){

        var intent: Intent = Intent(Intent.ACTION_PICK)
        intent.type=("*/*")
        startActivityForResult(intent,MY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==requestcode&& resultCode==RESULT_OK){
            System.out.println("!@#$%")
            if(data!=null){

                binding.ivPicture.setImageURI(data.data)
                val selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val cursor = context?.applicationContext?.contentResolver?.query(selectedImage!!, filePathColumn, null, null, null)
                assert(cursor != null)
                cursor!!.moveToFirst()

                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                mediaPath = cursor.getString(columnIndex)
                cursor.close()


                postPath = mediaPath
                file = File(postPath!!)
                System.out.println("FILE "+file)
            }

        }
    }

}
