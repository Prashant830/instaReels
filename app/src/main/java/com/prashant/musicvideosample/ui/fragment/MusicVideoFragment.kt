package com.prashant.musicvideosample.ui.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.prashant.musicvideosample.R
import com.prashant.musicvideosample.base.MusicVideoApp
import com.prashant.musicvideosample.databinding.FragmentMusicVideoBinding
import com.prashant.musicvideosample.model.VideosModel
import com.prashant.musicvideosample.utils.*
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory
import com.google.android.exoplayer2.util.Util

private val TAG = MusicVideoFragment::class.java.simpleName
class MusicVideoFragment : Fragment() {


    private var videoUrl: String? = null
    private var simpleExoPlayer: SimpleExoPlayer? = null
    private var cacheDataSourceFactory: CacheDataSourceFactory? = null
    private val simpleCache = MusicVideoApp.simpleCache
    private var videosModel: VideosModel? = null
    private var videoPlayBackPosition: Int = -1
    private var loaderImage : ImageView? = null
    private var isVideoPlaying = false
    private var imageView : ImageView? = null

    companion object {
        fun newInstance(videoModel: VideosModel) = MusicVideoFragment().apply {
            arguments = Bundle().apply {
                putParcelable(Constants.PARCELABLE_KEY, videoModel)

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentMusicVideoBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.fragment_music_video,

            null,
            false

        )

        videosModel = arguments?.getParcelable(Constants.PARCELABLE_KEY)
        videosModel?.let {
            videoUrl = it.sources
            setUpData(binding, it)
        }
        setClicks(binding)

        loaderImage = binding.loaderImage
        setLoadingDrawable(loaderImage)

        return binding.root
    }


    private fun setClicks(binding: FragmentMusicVideoBinding){
        binding.playerControlContainer.setOnClickListener {
            isVideoPlaying = if (isVideoPlaying) {
                pauseVideo()
                binding.playVideoButton.show()
                false
            } else {
                playVideo()
                binding.playVideoButton.hide()
                true
            }

        }

    }



    private fun setUpData(binding: FragmentMusicVideoBinding, videoModel: VideosModel){
        binding.videoTitle.setTextOrHide(videoModel.title)
        binding.videoDescription.setTextOrHide(videoModel.description)
      //  binding.mText1.setTextOrHide(videoModel.text1)
      //  binding.mText2.setTextOrHide(videoModel.text2)
        val simplePlayer = getPlayer()
        binding.playerView.player = simplePlayer
        videoUrl?.let {
            prepareMedia(it)
        }
    }

    private fun prepareVideoPlayer() {
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(requireContext())
        cacheDataSourceFactory = CacheDataSourceFactory(
            simpleCache,
            DefaultHttpDataSourceFactory(
                Util.getUserAgent(
                    requireContext(),
                    resources.getString(R.string.app_name)
                )
            )
        )
    }

    private fun getPlayer() : SimpleExoPlayer? {
        if (simpleExoPlayer == null){
            prepareVideoPlayer()
        }
        return simpleExoPlayer
    }

    private fun prepareMedia(videoUrl: String){
        val mediaSource = ExoPlayerUtils.getMediaSource(cacheDataSourceFactory!!, videoUrl)
        simpleExoPlayer?.prepare(mediaSource)
        simpleExoPlayer?.repeatMode = Player.REPEAT_MODE_ONE
        simpleExoPlayer?.playWhenReady = true
        simpleExoPlayer?.addListener(playerCallBack)
        videoPlayBackPosition = -1
    }

    private val playerCallBack  : Player.EventListener = object : Player.EventListener{
        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            super.onPlayerStateChanged(playWhenReady, playbackState)
            when(playbackState){
                Player.STATE_BUFFERING -> {
//                    loaderImage?.show()
//                    isVideoPlaying = false
                }
                Player.STATE_READY -> {
//                    loaderImage?.hide()
//                    isVideoPlaying = true
                }
            }
            printDebugLog(TAG, playbackState.toString())
        }

        override fun onPlayerError(error: ExoPlaybackException) {
            super.onPlayerError(error)
        }

    }

    private fun setArtWork(drawable: Drawable, playerView: PlayerView){
        playerView.useArtwork = true
        playerView.defaultArtwork = drawable
    }

    private fun playVideo(){
        simpleExoPlayer?.playWhenReady = true
    }

    private fun resetVideo(){
        if (simpleExoPlayer == null){
            videoUrl?.let { prepareMedia(it) }
        }else{
            simpleExoPlayer?.seekToDefaultPosition()
            simpleExoPlayer?.playWhenReady = true
        }
    }

    private fun pauseVideo(){
        simpleExoPlayer?.playWhenReady = false
    }

    private fun releasePlayer() {
        simpleExoPlayer?.stop(true)
        simpleExoPlayer?.release()
    }


    override fun onPause() {
        pauseVideo()
        super.onPause()
    }

    override fun onResume() {
        resetVideo()
        super.onResume()
    }

    override fun onDestroy() {
        releasePlayer()
        super.onDestroy()
    }

}
