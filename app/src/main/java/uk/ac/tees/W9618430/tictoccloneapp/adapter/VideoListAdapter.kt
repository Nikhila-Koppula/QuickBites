package uk.ac.tees.W9618430.tictoccloneapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import uk.ac.tees.W9618430.tictoccloneapp.ProfileActivity
import uk.ac.tees.W9618430.tictoccloneapp.R
import uk.ac.tees.W9618430.tictoccloneapp.databinding.ActivityVideoUploadBinding
import uk.ac.tees.W9618430.tictoccloneapp.databinding.VideoItemRowBinding
import uk.ac.tees.W9618430.tictoccloneapp.model.UserModel
import uk.ac.tees.W9618430.tictoccloneapp.model.VideoModel

class VideoListAdapter (
    options: FirestoreRecyclerOptions<VideoModel>
): FirestoreRecyclerAdapter<VideoModel,VideoListAdapter.VideoViewHolder>(options){



    inner class VideoViewHolder(private val binding: VideoItemRowBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindVideo(videoModel: VideoModel){
            //bindUserData

            Firebase.firestore.collection("users")
                .document(videoModel.UploaderId)
                .get().addOnSuccessListener {
                    val userModel = it?.toObject(UserModel::class.java)
                    userModel?.apply {
                        binding.usernameView.text = username
                        //bind profilepic
                        Glide.with(binding.profileIcon).load(profilePic)
                            .circleCrop()
                            .apply(
                                RequestOptions().placeholder(R.drawable.icon_account_circle)
                            )
                            .into(binding.profileIcon)

                        binding.userDetailLayout.setOnClickListener{
                            val intent = Intent(binding.userDetailLayout.context, ProfileActivity::class.java)
                            intent.putExtra("profile_user_id", id)
                            binding.userDetailLayout.context.startActivity(intent)
                        }

                    }
                }

            binding.captionView.text = videoModel.title
            binding.progressBar.visibility = View.VISIBLE

            //bindVideo
            binding.videoView.apply {
                setVideoPath(videoModel.url)
                setOnPreparedListener{
                    binding.progressBar.visibility = View.GONE
                    it.start()
                    it.isLooping = true
                }
                //play pause
                setOnClickListener{
                    if (isPlaying){
                        pause()
                        binding.pauseIcon.visibility = View.VISIBLE
                    }else{
                        start()
                        binding.pauseIcon.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = VideoItemRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int, model: VideoModel) {
        holder.bindVideo(model)
    }
}