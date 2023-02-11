package com.example.gamemaster;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSingleGame#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSingleGame extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentSingleGame() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSingleGame.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSingleGame newInstance(String param1, String param2) {
        FragmentSingleGame fragment = new FragmentSingleGame();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_single_game, container, false);
        TextView gameDescription = view.findViewById(R.id.TextViewChosenGameDescription);
        TextView gameName = view.findViewById(R.id.textViewGameName);
        TextView releaseDate = view.findViewById(R.id.textViewGameDate);
        TextView company = view.findViewById(R.id.textViewGameCompany);
        ImageView gameImage = view.findViewById(R.id.imageViewChosenGame);


        gameDescription.setText(getArguments().getString("description"));
        gameImage.setImageResource(getArguments().getInt("image"));
        gameName.setText(getArguments().getString("name"));
        releaseDate.setText(getArguments().getString("released"));
        company.setText(getArguments().getString("company"));


        VideoView videoView;


        int videoLocation ;
        videoLocation=(getArguments().getInt("video"));
        String videoPath = "android.resource://" + getActivity().getPackageName() + "/" + videoLocation;
        videoView = view.findViewById(R.id.videoView);

        Uri uri = Uri.parse(videoPath);
        videoView.setMediaController(new MediaController(this.getActivity()));
        videoView.setVideoURI(uri);
        videoView.requestFocus();




        return view;
    }


}