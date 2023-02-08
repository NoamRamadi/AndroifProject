package com.example.gamemaster;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>  {

    public ArrayList<GameData> dataSet;
    Hashtable<String,Integer> drawableHashTable ;



    public CustomAdapter(ArrayList<GameData> dataSet) {

        this.dataSet = dataSet;
        drawableHashTable = new Hashtable<String,Integer>();
        drawableHashTable.put("Call Of Duty",R.drawable.callofduty);
        drawableHashTable.put("League Of Legend",R.drawable.leagueoflegend);
        drawableHashTable.put("Grand Theft Auto",R.drawable.gta);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {
       CardView cardView;
       TextView textViewName;
       TextView textViewCompany;
       TextView textViewData;
       ImageView imageViewIcon;


       public MyViewHolder (View itemView)
       {
           super(itemView);

           cardView = (CardView) itemView.findViewById(R.id.card_view);
           textViewName = ( TextView) itemView.findViewById(R.id.textViewName);
           textViewCompany = ( TextView) itemView.findViewById(R.id.textViewCompany);
           imageViewIcon = (ImageView) itemView.findViewById(R.id.imageViewHero);
           //textViewData= ()





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




           //ImageView textName = imageViewIcon.get
           String textDetail = textViewCompany.getText().toString();

           //Bundle bundle = new Bundle();
           //bundle.putString("Name", textName);
           //bundle.putString("Detail", textDetail);


           cardView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

                   String check=textViewName.getText().toString();


                    Bundle bundle = new Bundle();

                  for(GameData gm: dataSet) {
                      {
                          if(gm.getName().equals(check))
                          {
                              bundle.putString("description",gm.getDescription());
                              bundle.putString("name",  check);
                              bundle.putInt("image",  gm.getImage());

                          }
                      }
                  }

                   //bundle.putString("Image",  MyData.drawableArray[i]);

                   Navigation.findNavController(view).navigate(R.id.action_fragmentSearchResult_to_fragmentSingleGame2,bundle);
               }
           });

       }

    }
    private void collectAllDataFromDB(Map<String,Object> games) {
        //iterate through each user, ignoring their UID

        for (Map.Entry<String, Object> entry : games.entrySet()) {

            //Get user map
            Map singleUser = (Map) entry.getValue();
            String key = singleUser.get("name").toString();
            dataSet.add(new GameData(singleUser.get("name").toString(), singleUser.get("company").toString(),
                    singleUser.get("description").toString(),
                    singleUser.get("genre").toString(), singleUser.get("rate").toString(), drawableHashTable.get(key)
            ));
        }
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext() ).inflate(R.layout.cards_layout , parent ,false);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder,  int listPosition) {

        TextView textViewName = viewHolder.textViewName;
        TextView textViewCompany = viewHolder.textViewCompany;
       // TextView textViewData = viewHolder.textViewData;
        ImageView imageView = viewHolder.imageViewIcon;
        CardView cardView = viewHolder.cardView;

        textViewName.setText(dataSet.get(listPosition).getName());
        textViewCompany.setText(dataSet.get(listPosition).getCompany());
        imageView.setImageResource(dataSet.get(listPosition).getImage());



        
    }

    public ArrayList<GameData> getDataSet() {
        return dataSet;
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}


