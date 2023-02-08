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

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>  {

    public ArrayList<GameData> dataSet;

    public CustomAdapter(ArrayList<GameData> dataSet) {

        this.dataSet = dataSet;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder  {
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

                  // data=clickedItem.

                    bundle.putString("Name",  check);

                   //bundle.putString("Image",  MyData.drawableArray[i]);

                   Navigation.findNavController(view).navigate(R.id.action_fragmentSearchResult_to_fragmentSingleGame2,bundle);
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


