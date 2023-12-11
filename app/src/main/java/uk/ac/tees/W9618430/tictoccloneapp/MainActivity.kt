package uk.ac.tees.W9618430.tictoccloneapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import uk.ac.tees.W9618430.tictoccloneapp.adapter.VideoListAdapter
import uk.ac.tees.W9618430.tictoccloneapp.databinding.ActivityMainBinding
import uk.ac.tees.W9618430.tictoccloneapp.model.VideoModel
import uk.ac.tees.W9618430.tictoccloneapp.util.UiUtil

class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding
    lateinit var adapter: VideoListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavBar.setOnItemSelectedListener {menuItem->
            when(menuItem.itemId){
                R.id.bottom_menu_home->{
                    UiUtil.showToast(this,"Home")
                }
                R.id.bottom_menu_add_video->{
                    startActivity(Intent(this,VideoUploadActivity::class.java))
                }
                R.id.bottom_menu_profile->{
                    //Goto ProfileActivity
                    val intent = Intent( this,ProfileActivity::class.java)
                    intent.putExtra("profile_user_id", FirebaseAuth.getInstance().currentUser?.uid)
                    startActivity(intent)
                }
            }
            false


        }
        setupViewPager()

    }

    private fun setupViewPager(){
        val options = FirestoreRecyclerOptions.Builder<VideoModel>()
            .setQuery(
                Firebase.firestore.collection("videos"),
                VideoModel::class.java
            ).build()
        adapter = VideoListAdapter(options)
        binding.viewPager.adapter = adapter

    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.startListening()
    }

}