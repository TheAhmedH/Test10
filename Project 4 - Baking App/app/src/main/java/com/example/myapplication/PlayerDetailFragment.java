package com.example.myapplication;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class PlayerDetailFragment extends Fragment {
    private ExoPlayer mExoplayer;
    private PlayerView mPlayerview;
    private Step mStep;
    private TextView mStepTextView;
    private ProgressBar mProgressBar;

    //Initializing the initial buffered state to zero
    private long mPlayerBufferPositionSaved = 0;
    //Boolean will confirm player state
    private boolean mPlayeWhenReady;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Checking if argument exists and passing in the step
        Bundle stepBundle = this.getArguments();
        if (stepBundle != null) {
            mStep = (Step) stepBundle.getSerializable("step");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        //save the state of buffered position and the player ready
        outState.putLong("PlayerBufferPositionSaved", mPlayerBufferPositionSaved);
        outState.putBoolean("PlayeWhenReady", mPlayeWhenReady);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.player_detail_fragment, container, false);
        mPlayerview = view.findViewById(R.id.player_exoPlayerView);
        mProgressBar = view.findViewById(R.id.player_progress_bar);
        mStepTextView = view.findViewById(R.id.player_step_description_TV);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        TrackSelector trackSelector = new DefaultTrackSelector();

        //Create instance of the Exoplayer
        mExoplayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
        mPlayerview.setPlayer(mExoplayer);

        String userAgent = Util.getUserAgent(getContext(), "myapplication");
        DefaultDataSourceFactory defaultDataSourceFactory = new DefaultDataSourceFactory(getContext(), userAgent);

        //Extracting media source from the Url
        ExtractorMediaSource extractorMediaSource = new ExtractorMediaSource.Factory(defaultDataSourceFactory).createMediaSource(Uri.parse(mStep.getVideoURL()));

        mExoplayer.prepare(extractorMediaSource);

        //Setting player to last buffered position saved
        mExoplayer.seekTo(mPlayerBufferPositionSaved);

        //Adding a listener to the player
        mExoplayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {
            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            }

            @Override
            public void onLoadingChanged(boolean isLoading) {
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                //Setting the progressbar view depending on video is buffering on not
                if (playbackState == Player.STATE_BUFFERING) {
                    mProgressBar.setVisibility(View.VISIBLE);
                } else {
                    mProgressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {
            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
            }

            @Override
            public void onPositionDiscontinuity(int reason) {
            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            }

            @Override
            public void onSeekProcessed() {
            }
        });
        mExoplayer.setPlayWhenReady(mPlayeWhenReady);
    }

    //Releasing the player method
    private void releasePlayer() {
        mExoplayer.stop();
        mPlayerview.setPlayer(null);
        mExoplayer.release();
        mExoplayer = null;
    }

    @Override
    public void onPause() {
        //saving the buffered position
        mPlayerBufferPositionSaved = mExoplayer.getBufferedPosition();
        mPlayeWhenReady = mExoplayer.getPlayWhenReady();
        //Releasing the player
        releasePlayer();
        super.onPause();
    }
}
