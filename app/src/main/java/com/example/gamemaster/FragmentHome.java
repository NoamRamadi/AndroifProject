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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHome extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Bundle bundle;
    String loginID,email,password;
    private FirebaseAuth mAuth;
    private EditText emailText;
    private EditText passwordText;
    public FragmentHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentHome.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHome newInstance(String param1, String param2) {
        FragmentHome fragment = new FragmentHome();
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
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        email="";
        password="";


        emailText = view.findViewById(R.id.editTextEmail);
        passwordText=view.findViewById(R.id.editTextPassword);
        Button buttonLogin = view.findViewById(R.id.buttonHomeLogin);

        Button buttonRegister = view.findViewById(R.id.buttonHomeRegister);
        //  mAuth = FirebaseAuth.getInstance();


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigation.findNavController(view).navigate(R.id.action_fragmentHome_to_fragmentLogin);

                //String email = emailText.getText().toString().trim();

                //readDBID(email);

                //bundle.putString("ID",loginID);
                //mAuth = FirebaseAuth.getInstance();
                funcLogin(view);
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //String emailTxt = editTextEmail.getText().toString();
                //String passwordTxt = editTextPassword.getText().toString();

                //Bundle bundle = new Bundle();
                //bundle.putString("Email", emailTxt);
                //bundle.putString("Password", passwordTxt);
                Navigation.findNavController(view).navigate(R.id.action_fragmentHome_to_fragmentRegister);
                //Navigation.findNavController(view).navigate(R.id.action_fragmentHome_to_fragmentRegister , bundle);
            }
        });


    return view;
    }

    public void readDBID(String email) {
        // Read from the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(email);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Person value = dataSnapshot.getValue(Person.class);

                loginID = value.ID;
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }

    public void funcLogin(View view) {
        // Toast.makeText(getContext(), "login zi", Toast.LENGTH_LONG).show();


        email = emailText.getText().toString().trim();
        password = passwordText.getText().toString().trim();

        if (email.equals("") || password.equals("")) {
            email = "none";
            password = "none";
        }

        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            // Navigation.findNavController(view).navigate(R.id.action_fragmentHome_to_fragmentLogin , bundle);
                            bundle=new Bundle();
                            bundle.putString("email",email);
                            Navigation.findNavController(view).navigate(R.id.action_fragmentHome_to_fragmentLogin,bundle);


                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getContext(), "login failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}