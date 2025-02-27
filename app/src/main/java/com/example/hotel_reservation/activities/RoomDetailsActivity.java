package com.example.hotel_reservation.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hotel_reservation.R;
import com.example.hotel_reservation.models.Room;
import com.example.hotel_reservation.services.RoomsService;
import com.example.hotel_reservation.utils.ImageUtils;
import com.google.android.material.snackbar.Snackbar;

public class RoomDetailsActivity extends AppCompatActivity {


    Button bookRoom,back,delete,edit;
    TextView name,description,details;
    ImageView roomImage;
    RoomsService roomService;
    Room room;
    int ROOM_ID;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_room_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ROOM_ID = getIntent().getIntExtra("id",0);
        roomService = new RoomsService(this);
        bookRoom = findViewById(R.id.bookRoomButton);
        back = findViewById(R.id.backButton);
        back.setOnClickListener(view -> finish());
        name = findViewById(R.id.roomName);
        description = findViewById(R.id.roomDescription);
        details = findViewById(R.id.roomDetails);
        roomImage = findViewById(R.id.roomImage);
        bookRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(RoomDetailsActivity.this, BookingPage.class);
                intent.putExtra("id",ROOM_ID);
                startActivity(intent);
            }
        });
        delete = findViewById(R.id.deleteButton);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(roomService.deleteRoom(ROOM_ID)){
                    Toast.makeText(RoomDetailsActivity.this, "Room Deleted", Toast.LENGTH_SHORT).show();
                    finish();
                }else{

                    Snackbar.make(delete,"Not Deleted",Snackbar.LENGTH_SHORT).show();
                }
            }
        });


        try{
            room  = roomService.getRoomById(ROOM_ID);
            name.setText(room.getName());
            description.setText(room.getDescription());
            details.setText(room.getId()+" "+room.getBuildingName()+" "+room.getFloorNumber());
            roomImage.setImageBitmap(ImageUtils.convertByteIntoBitmap(room.getImage()));

            edit = findViewById(R.id.editButton);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Room newRoom = roomService.editRoom(new Room(ROOM_ID,"Update","update",room.getImage(),"update",1,true,room.getPrice()));
                    if( newRoom!= null){
                        Toast.makeText(RoomDetailsActivity.this, "Room Edited", Toast.LENGTH_SHORT).show();
                        room = newRoom;
                        name.setText(room.getName());
                        description.setText(room.getDescription());
                        details.setText(room.getId()+" "+room.getBuildingName()+" "+room.getFloorNumber());
                        roomImage.setImageBitmap(ImageUtils.convertByteIntoBitmap(room.getImage()));
                    }else{

                        Snackbar.make(delete,"Room not edit",Snackbar.LENGTH_SHORT).show();
                    }
                }
            });

        } catch (Error e) {
            Toast.makeText(this, "Room Not Found", Toast.LENGTH_SHORT).show();
            finish();
        }




    }
}