package com.example.hotel_reservation.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotel_reservation.interfaces.RecyclerViewInterface;
import com.example.hotel_reservation.models.Room;

import java.util.ArrayList;
import com.example.hotel_reservation.R;
import com.example.hotel_reservation.utils.ImageUtils;
import com.google.android.material.card.MaterialCardView;

public class RoomsAdapter  extends RecyclerView.Adapter<RoomsAdapter.ViewHolder> {
    private  ArrayList<Room> roomArrayList;
    RecyclerViewInterface recyclerViewInterface;

    public RoomsAdapter(ArrayList<Room> typeArrayList, RecyclerViewInterface recyclerViewInterface) {
        this.roomArrayList = typeArrayList;
        this.recyclerViewInterface = recyclerViewInterface;
    }
    @NonNull
    @Override
    public RoomsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rooms_recyclerview_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RoomsAdapter.ViewHolder holder, int position) {
            holder.bind(roomArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return roomArrayList.size();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setFilteredList(ArrayList<Room> roomList) {
        roomArrayList = roomList;
        notifyDataSetChanged();

    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
  TextView name,description,roomDetails,roomCardId,roomPrice;
        ImageView image;
        MaterialCardView roomCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.roomName);
            description = itemView.findViewById(R.id.roomDescription);
            roomPrice = itemView.findViewById(R.id.roomPrice);
            roomDetails = itemView.findViewById(R.id.roomDetails);
            roomCardId = itemView.findViewById(R.id.roomCardId);
            image = itemView.findViewById(R.id.roomImage);
            roomCard = itemView.findViewById(R.id.roomCard);


        }
        @SuppressLint("SetTextI18n")
        public void bind(Room room) {

            name.setText(room.getName());
            description.setText(room.getDescription());
            roomDetails.setText(room.getBuildingName()+" "+Integer.toString(room.getFloorNumber()));
            roomPrice.setText(Double.toString(room.getPrice()));
            roomCardId.setText(Integer.toString(room.getId()));
            image.setImageBitmap(ImageUtils.convertByteIntoBitmap(room.getImage()));
            roomCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if(recyclerViewInterface ==null){
                        return;
                    }
                    int clickedPosition = getAdapterPosition();
                    if(clickedPosition == RecyclerView.NO_POSITION){
                        return;
                    }
                    recyclerViewInterface.onItemClicked(room.getId(),1);

                }
            });
        }
    }
}
