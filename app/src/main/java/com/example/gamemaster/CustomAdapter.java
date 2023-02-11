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
    Hashtable<String,Integer> videoHashTable ;


    public CustomAdapter(ArrayList<GameData> dataSet) {

        this.dataSet = dataSet;
        drawableHashTable = new Hashtable<String,Integer>();
        drawableHashTable.put("Call Of Duty",R.drawable.callofduty);
        drawableHashTable.put("Call Of Duty Modern Warfare",R.drawable.callofdutymw);
        drawableHashTable.put("Call Of Duty Modern Warfare 2",R.drawable.callofdutymw2);
        drawableHashTable.put("Call Of Duty Black Ops",R.drawable.callofdutyblackops);
        drawableHashTable.put("Call Of Duty Black Ops 2",R.drawable.callofduty2);
        drawableHashTable.put("Call Of Duty Black Ops 3",R.drawable.callofduty3);
        drawableHashTable.put("League Of Legends",R.drawable.leagueoflegends);
        drawableHashTable.put("Grand Theft Auto",R.drawable.gta);

        drawableHashTable.put("Dota",R.drawable.dota);
        drawableHashTable.put("Dota 2",R.drawable.dota2);
        drawableHashTable.put("Grand Theft Auto III",R.drawable.gta3);
        drawableHashTable.put("Grand Theft Auto IV",R.drawable.gta4);
        drawableHashTable.put("Grand Theft Auto V",R.drawable.gta5);
        drawableHashTable.put("Grand Theft Auto San Andreas",R.drawable.gtasa);
        drawableHashTable.put("Grand Theft Auto Vice City",R.drawable.gtavc);
        drawableHashTable.put("Need For Speed II",R.drawable.needforspeed2);
        drawableHashTable.put("Need For Speed III",R.drawable.needforspeed3);
        drawableHashTable.put("Need For Speed",R.drawable.nfs);
        drawableHashTable.put("Portal",R.drawable.portal);
        drawableHashTable.put("Portal 2",R.drawable.portal2);
        drawableHashTable.put("The Sims",R.drawable.sims);

        videoHashTable = new Hashtable<String,Integer>();
        videoHashTable.put("Call Of Duty",R.raw.csgo);
        videoHashTable.put("Call Of Duty Modern Warfare",R.raw.csgo);
        videoHashTable.put("Call Of Duty Modern Warfare 2",R.raw.csgo);
        videoHashTable.put("Call Of Duty Black Ops",R.raw.csgo);
        videoHashTable.put("Call Of Duty Black Ops 2",R.raw.csgo);
        videoHashTable.put("Call Of Duty Black Ops 3",R.raw.csgo);
        videoHashTable.put("League Of Legends",R.raw.leagueoflegends);
        videoHashTable.put("Grand Theft Auto",R.raw.gtasa);

        videoHashTable.put("Dota",R.raw.dota2);
        videoHashTable.put("Dota 2",R.raw.dota2);
        videoHashTable.put("Grand Theft Auto III",R.raw.gtasa);
        videoHashTable.put("Grand Theft Auto IV",R.raw.gtasa);
        videoHashTable.put("Grand Theft Auto V",R.raw.gtav);
        videoHashTable.put("Grand Theft Auto San Andreas",R.raw.gtasa);
        videoHashTable.put("Grand Theft Auto Vice City",R.raw.gtasa);
        videoHashTable.put("Need For Speed II",R.raw.nfs);
        videoHashTable.put("Need For Speed III",R.raw.nfs);
        videoHashTable.put("Need For Speed",R.raw.nfs);
        videoHashTable.put("Portal",R.raw.portal2);
        videoHashTable.put("Portal 2",R.raw.portal2);
        videoHashTable.put("The Sims",R.raw.sims);


    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {
       CardView cardView;
       TextView textViewName;
       TextView textViewCompany;
       ImageView imageViewIcon;


       public MyViewHolder (View itemView)
       {
           super(itemView);

           cardView = (CardView) itemView.findViewById(R.id.card_view);
           textViewName = ( TextView) itemView.findViewById(R.id.textViewName);
           textViewCompany = ( TextView) itemView.findViewById(R.id.textViewCompany);
           imageViewIcon = (ImageView) itemView.findViewById(R.id.imageViewHero);

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




           cardView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

                   String clickedGameName=textViewName.getText().toString();


                    Bundle bundle = new Bundle();

                  for(GameData gm: dataSet) {
                      {
                          if(gm.getName().equals(clickedGameName))
                          {
                              bundle.putString("description",gm.getDescription());
                              bundle.putString("name",  clickedGameName);
                              bundle.putInt("image",  gm.getImage());
                              bundle.putString("company",gm.getCompany());
                              bundle.putString("released",gm.getReleaseDate());
                              bundle.putInt("video",gm.getVideo());

                          }
                      }
                  }

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
                    ,singleUser.get("released").toString(),videoHashTable.get(key)
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


