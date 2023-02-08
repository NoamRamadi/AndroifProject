package com.example.gamemaster;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSearchResult#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSearchResult extends Fragment implements Serializable {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private ArrayList<GameData> dataSetAfterFilter;
    private String mParam1;
    private String mParam2;
    private RecyclerView recycleView;
    private CustomAdapter addapter;
    private ArrayList<GameData> recievedDataSet;
    public FragmentSearchResult() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSearchResult.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSearchResult newInstance(String param1, String param2) {
        FragmentSearchResult fragment = new FragmentSearchResult();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);

        TextView searchedValue = view.findViewById(R.id.searchedText);
        searchedValue.setText(getArguments().getString("searchedText"));
        recievedDataSet=(ArrayList<GameData>)getArguments().getSerializable("arr");

        recycleView = view.findViewById(R.id.my_recycler_view);


        recycleView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        recycleView.setItemAnimator(new DefaultItemAnimator());

        dataSetAfterFilter = new ArrayList<GameData>();

        String searchValueStr = searchedValue.getText().toString();
        String nm="";

        for(GameData gm : recievedDataSet){
            nm=gm.getName();

            if(nm.contains(searchValueStr) ){
                dataSetAfterFilter.add(gm);
            }
        }



        addapter = new CustomAdapter(dataSetAfterFilter);
        recycleView.setAdapter(addapter);


        return view;
    }

    public void readDatabaseByName(String name) {
        // Read from the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("games").child(name);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                GameData value = dataSnapshot.getValue(GameData.class);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }
}