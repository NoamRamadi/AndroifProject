package com.example.gamemaster;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentLogin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLogin extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button searchBtn;
    EditText searchTxt ;
    Bundle bundle;
    String str;
    Hashtable<String,Integer> drawableHashTable ;
    Integer[] drawableArray = {R.drawable.callofduty, R.drawable.gta, R.drawable.leagueoflegend};
    private ArrayList<GameData> dataSet;
    public FragmentLogin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentLogin.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentLogin newInstance(String param1, String param2) {
        FragmentLogin fragment = new FragmentLogin();
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
        View view =  inflater.inflate(R.layout.fragment_login, container, false);
        dataSet = new ArrayList<GameData>();
        drawableHashTable = new Hashtable<String,Integer>();
        drawableHashTable.put("Call Of Duty",R.drawable.callofduty);
        drawableHashTable.put("Call Of Duty Modern Warfare",R.drawable.callofdutymw);
        drawableHashTable.put("Call Of Duty Modern Warfare 2",R.drawable.callofdutymw2);
        drawableHashTable.put("Call Of Duty Black Ops",R.drawable.callofdutyblackops);
        drawableHashTable.put("Call Of Duty Black Ops 2",R.drawable.callofduty2);
        drawableHashTable.put("Call Of Duty Black Ops 3",R.drawable.callofduty3);
        drawableHashTable.put("League Of Legend",R.drawable.leagueoflegend);
        drawableHashTable.put("Grand Theft Auto",R.drawable.gta);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("games");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        collectAllDataFromDB((Map<String,Object>) dataSnapshot.getValue());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });



        searchBtn = view.findViewById(R.id.LoggedInPageSearchButton);
        searchTxt = view.findViewById(R.id.LoggedInPageSearchText);
        str="";


        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str = searchTxt.getText().toString().trim();
                bundle = new Bundle();
                bundle.putString("searchedText",str);
                bundle.putSerializable("arr",dataSet);

                Navigation.findNavController(view).navigate(R.id.action_fragmentLogin_to_fragmentSearchResult,bundle);

            }
        });
    return view;
    }
    private void collectAllDataFromDB(Map<String,Object> users) {
          //iterate through each user, ignoring their UID

        for (Map.Entry<String, Object> entry : users.entrySet()){

            //Get user map
            Map singleUser = (Map) entry.getValue();
            String key = singleUser.get("name").toString();
            dataSet.add(new GameData(singleUser.get("name").toString(),singleUser.get("company").toString(),
                    singleUser.get("description").toString(),
                    singleUser.get("genre").toString(),singleUser.get("rate").toString(),drawableHashTable.get(key)
                    ));
        }

    }

    public void readDatabase(String name) {
        // Read from the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("games");

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