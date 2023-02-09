package com.example.gamemaster;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentRegister#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentRegister extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button buttonRegister;
    private FirebaseAuth mAuth;
    private EditText emailText ;
    private EditText passwordText ;
    private EditText IDText;
    private EditText nameText;
    DatabaseReference myRef;
    FirebaseDatabase database;

    public FragmentRegister() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentRegister.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentRegister newInstance(String param1, String param2) {
        FragmentRegister fragment = new FragmentRegister();
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
        View view =  inflater.inflate(R.layout.fragment_register, container, false);
        mAuth = FirebaseAuth.getInstance();
        //database=FirebaseDatabase.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();


        emailText = view.findViewById(R.id.editTextRegisterEmail);
        passwordText = view.findViewById(R.id.editTextRegisterPassword);
        nameText = view.findViewById(R.id.editTextRegisterName);
        IDText = view.findViewById(R.id.editTextRegisterID);

        buttonRegister = view.findViewById(R.id.buttonRegisterApply);


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                funcRegister(view);
            }
        });


    return view;
    }

    public void funcRegister(View view) {
        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();

        if (email.equals("") || password.equals("")){
            email = "none";
            password = "none";
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            writeDB();





                            Toast.makeText(getContext(), "Register Succeed", Toast.LENGTH_LONG).show();
                            Navigation.findNavController(view).navigate(R.id.action_fragmentRegister_to_fragmentHome);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getContext(), "register failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void writeDB(){
        // Write a message to the database
        String email=emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        String name = nameText.getText().toString().trim();
        String ID = IDText.getText().toString().trim();


        Person p = new Person(email ,password , name , ID);
       // myRef = database.getReference("users").child(p.ID);
   //     myRef.setValue(p);
        //myRef.child("users").setValue(p);
        String modifiedEmail = p.email.replace(".","");
        myRef.child("users").child(modifiedEmail).setValue(p);
    }
}