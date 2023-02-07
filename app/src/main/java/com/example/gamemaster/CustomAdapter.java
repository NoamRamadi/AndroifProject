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

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>  {

    private ArrayList<GameData> dataSet;
    Bundle bundle = new Bundle();
    public CustomAdapter(ArrayList<GameData> dataSet) {

        this.dataSet = dataSet;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder  {
       CardView cardView;
       TextView textViewName;
       TextView textViewDetail;
       TextView textViewData;
       ImageView imageViewIcon;

       public MyViewHolder (View itemView)
       {
           super(itemView);

           cardView = (CardView) itemView.findViewById(R.id.card_view);
           textViewName = ( TextView) itemView.findViewById(R.id.textViewName);
           textViewDetail = ( TextView) itemView.findViewById(R.id.textViewDetail);
           //imageViewIcon = (ImageView) itemView.findViewById(R.id.imageViewHero);

           //ImageView textName = imageViewIcon.get
           String textDetail = textViewDetail.getText().toString();

           //Bundle bundle = new Bundle();
           //bundle.putString("Name", textName);
           //bundle.putString("Detail", textDetail);


           cardView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   String clickedItem = textViewName.getText().toString();
                   String data = "";
                   Bundle bundle = new Bundle();


                   Navigation.findNavController(view).navigate(R.id.action_fragmentSearchResult_to_fragmentSingleGame2);
               }
           });

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
        TextView textViewDetail = viewHolder.textViewDetail;
        TextView textViewData = viewHolder.textViewData;
        ImageView imageView = viewHolder.imageViewIcon;
        CardView cardView = viewHolder.cardView;

        textViewName.setText(dataSet.get(listPosition).getName());
        textViewDetail.setText(dataSet.get(listPosition).getDescription());
        //imageView.setImageResource(dataSet.get(listPosition).getImage());



        
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}


