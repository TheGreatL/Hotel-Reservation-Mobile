package com.example.hotel_reservation.activities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.example.hotel_reservation.R;
import com.example.hotel_reservation.models.Room;
import com.example.hotel_reservation.services.RoomsService;
import com.example.hotel_reservation.utils.ImageUtils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;


public class AddRoomActivity extends AppCompatActivity {

    ActivityResultLauncher<Intent> resultLauncher;
    ImageView cameraImage;
    EditText roomNameEditText,roomDescriptionEditText,roomBuildingFloorEditText,roomBuildingNameEditText,roomPriceEditText;
    TextInputLayout roomNameEditTextLayout,roomDescriptionEditTextLayout,roomBuildingNameEditTextLayout,roomBuildingFloorEditTextLayout,roomPriceEditTextLayout;
    byte[] imageData;
    RoomsService roomsService;
    Button addImageButton,back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        roomsService = new RoomsService(this);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_room);
        cameraImage = findViewById(R.id.cameraImage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        back = findViewById(R.id.backButton);
        back.setOnClickListener(view -> finish());
        addImageButton = findViewById(R.id.addImageButton);
        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCameraPermissionAndOpenCamera();
            }
        });
        cameraImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCameraPermissionAndOpenCamera();
            }
        });
        registerResult();
        roomNameEditText = findViewById(R.id.roomNameEditText);
        roomDescriptionEditText = findViewById(R.id.roomDescriptionEditText);
        roomBuildingNameEditText = findViewById(R.id.roomBuildingNameEditText);
        roomBuildingFloorEditText = findViewById(R.id.roomBuildingFloorEditText);
        roomPriceEditText = findViewById(R.id.roomPriceEditText);




        roomNameEditTextLayout = findViewById(R.id.roomNameEditTextLayout);
        roomDescriptionEditTextLayout = findViewById(R.id.roomDescriptionEditTextLayout);
        roomBuildingNameEditTextLayout = findViewById(R.id.roomBuildingNameEditTextLayout);
        roomBuildingFloorEditTextLayout = findViewById(R.id.roomBuildingFloorEditTextLayout);
        roomPriceEditTextLayout = findViewById(R.id.roomPriceEditTextLayout);

        Button addRoomButton = findViewById(R.id.saveAddRoomButton);
        addRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String roomName = String.valueOf(roomNameEditText.getText());
                String roomDescription = String.valueOf(roomDescriptionEditText.getText());
                String roomBuildingName = String.valueOf(roomBuildingNameEditText.getText());
                String roomBuildingFloor = String.valueOf(roomBuildingFloorEditText.getText());
                String roomPrice = String.valueOf(roomPriceEditText.getText());

                if(roomName.isBlank() || roomName.isEmpty()){
                    roomNameEditTextLayout.setError("Fill out the room name first");
                    return ;
                }
                roomNameEditTextLayout.setError(null);
                if(roomDescription.isBlank() || roomDescription.isEmpty()){
                    roomDescriptionEditTextLayout.setError("Fill out the room description first");
                    return ;
                }
                roomDescriptionEditTextLayout.setError(null);
                if(roomBuildingName.isBlank() || roomBuildingName.isEmpty()){
                    roomBuildingNameEditTextLayout.setError("Fill out the room building name first");
                    return ;
                }
                roomBuildingNameEditTextLayout.setError(null);
                if(roomBuildingFloor.isBlank() || roomBuildingFloor.isEmpty()){
                    roomBuildingFloorEditTextLayout.setError("Fill out the room floor name first");
                    return ;
                }
                roomBuildingFloorEditTextLayout.setError(null);
                if(imageData ==null){
                    Toast.makeText(AddRoomActivity.this, "Add Image First", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(roomPrice.isBlank() || roomPrice.isEmpty()){
                    roomPriceEditTextLayout.setError("Fill out the room price first");
                    return ;
                }
                roomPriceEditTextLayout.setError(null);






                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(AddRoomActivity.this);
                materialAlertDialogBuilder.setTitle("Adding Room")
                        .setMessage("Are you sure you want to add this room?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if( roomsService.createRoom(new Room(roomName,roomDescription,imageData,roomBuildingName,Integer.parseInt(roomBuildingFloor),true,Double.parseDouble(roomPrice))))  {
                                    Toast.makeText(AddRoomActivity.this,"Room Added",Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else{
                                    Toast.makeText(AddRoomActivity.this, "Adding Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("No",null).show();

            }
        });
    }
    private void registerResult(){
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        try{
                           Intent data = o.getData();
                           if(data ==null){
                               throw  new Exception();
                           }
                            Uri imageUri = data.getData();
                            imageData =  ImageUtils.getImageDataFromUri(AddRoomActivity.this, imageUri);
                            addImageButton.setVisibility(GONE);
                            cameraImage.setImageURI(imageUri);
                            cameraImage.setVisibility(VISIBLE);
                        } catch (Exception e) {
                            Toast.makeText(AddRoomActivity.this, "No image Selected", Toast.LENGTH_SHORT).show();
                            addImageButton.setVisibility(VISIBLE);
                            cameraImage.setVisibility(GONE);
                        }
                    }
                }
        );
    }
    private void checkCameraPermissionAndOpenCamera(){
        if(ActivityCompat.checkSelfPermission(AddRoomActivity.this,
                Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
          ActivityCompat.requestPermissions(AddRoomActivity.this,
                  new String[]{Manifest.permission.CAMERA},1);
        }
        else{
            launch();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, int deviceId) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId);
        if(requestCode!=1){
            return;
        }
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            launch();
        }else{
            Toast.makeText(this, "Camera permission denied.", Toast.LENGTH_SHORT).show();
        }

    }
    private void launch(){
        try{
            Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
            resultLauncher.launch(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
        }

    }
}