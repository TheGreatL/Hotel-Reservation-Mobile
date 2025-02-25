package com.example.hotel_reservation.activities.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hotel_reservation.R;
import com.example.hotel_reservation.activities.RoomDetailsActivity;
import com.example.hotel_reservation.adapter.RoomsAdapter;
import com.example.hotel_reservation.interfaces.RecyclerViewInterface;
import com.example.hotel_reservation.models.Room;
import com.example.hotel_reservation.services.RoomsService;
import com.google.android.material.divider.MaterialDividerItemDecoration;

import java.util.ArrayList;
import java.util.Objects;


public class Home extends Fragment {

    public Home() {
        // Required empty public constructor
    }



    RoomsAdapter roomsAdapter;

    ArrayList<Room> roomArrayList = new ArrayList<>();

    RecyclerView recyclerView;

    RoomsService roomsService;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        roomsService = new RoomsService(getContext());

        roomArrayList = roomsService.getRooms();

    }
    public void onSearchQueryChanged(String query) {
        ArrayList<Room> sotredRoomArrayList = new ArrayList<>();

        for(int index=0; index< roomArrayList.size(); index++){
            if(roomArrayList.get(index).getName().toLowerCase().contains(query.toLowerCase())){
                sotredRoomArrayList.add(roomArrayList.get(index));
            }
        }
        roomsAdapter.setFilteredList(sotredRoomArrayList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home,container,false);

        recyclerView   =    rootView.findViewById(R.id.roomsDisplayRecyclerView);
        roomsAdapter = new RoomsAdapter(roomArrayList, new RecyclerViewInterface() {
            @Override
            public void onItemClicked(int position, int type) {
                Intent intent = new Intent(getContext(), RoomDetailsActivity.class);
                intent.putExtra("id",position);
                startActivity(intent);
            }

            @Override
            public void onItemLongClicked(int position, int type) {

            }
        });
        recyclerView.setAdapter(roomsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        MaterialDividerItemDecoration materialDividerItemDecoration = new MaterialDividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL);
        materialDividerItemDecoration.setLastItemDecorated(false);
        materialDividerItemDecoration.setDividerInsetEnd(20);
        materialDividerItemDecoration.setDividerInsetStart(20);
        recyclerView.addItemDecoration(materialDividerItemDecoration);

        // Inflate the layout for this fragment
        return rootView;
    }
}