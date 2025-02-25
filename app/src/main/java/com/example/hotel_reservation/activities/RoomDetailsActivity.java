package com.example.hotel_reservation.activities;

import android.annotation.SuppressLint;
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


    Button bookRoom,back;
    TextView name,description,details;
    ImageView roomImage;
    RoomsService roomService;
    Room room;
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
                Snackbar.make(bookRoom,"Room booked",Snackbar.LENGTH_SHORT).show();
            }
        });

        try{
            room  = roomService.getRoomById(getIntent().getIntExtra("id",0));
            name.setText(room.getName());
            description.setText(room.getDescription());
            details.setText(room.getId()+" "+room.getBuildingName()+" "+room.getFloorNumber());
            roomImage.setImageBitmap(ImageUtils.convertByteIntoBitmap(room.getImage()));
        } catch (Error e) {
            Toast.makeText(this, "Room Not Found", Toast.LENGTH_SHORT).show();
            finish();
        }




    }
}