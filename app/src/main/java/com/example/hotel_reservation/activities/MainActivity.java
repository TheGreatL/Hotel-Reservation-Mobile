package com.example.hotel_reservation.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.CursorWindow;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotel_reservation.R;
import com.example.hotel_reservation.activities.fragments.Account;
import com.example.hotel_reservation.activities.fragments.Home;
import com.example.hotel_reservation.activities.fragments.Rooms;
import com.example.hotel_reservation.adapter.RoomsAdapter;
import com.example.hotel_reservation.interfaces.RecyclerViewInterface;
import com.example.hotel_reservation.models.Room;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {




    Fragment  fragment;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
        } catch (Exception e) {
            e.printStackTrace();
        }
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);
        fragment = new Home();



        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {


                if(item.getItemId() == R.id.topToolBarSearch){
                    Log.d("TestNavigationView", "onClick: "+item.getItemId());
                    return true;
                }
                else  if(item.getItemId() == R.id.topToolBarBookHistory){

//                    Next Page
                    Log.d("TestNavigationView", "onClick: "+item.getItemId());
                    return true;
                }
                else  if(item.getItemId() == R.id.topToolBarAddRoom){

//                    Next Page
                    startActivity(new Intent(MainActivity.this,AddRoomActivity.class));
                    return true;
                }
                return false;
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                boolean isFound = false;


                if(itemId == R.id.homeBottomNavigation){
                    fragment = new Home();

                    isFound = true;
                }
               else if(itemId == R.id.roomsBottomNavigation){
                    fragment = new Rooms();
                    isFound =  true;
                }
                else if(itemId == R.id.accountsBottomNavigation){
                    fragment = new Account();
                    isFound =  true;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout,fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();

                return isFound;
            }
        });


        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout,fragment)
                .addToBackStack(null)
                .commit();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_toolbar,menu);

        MenuItem searchItem = menu.findItem(R.id.topToolBarSearch);
        SearchView searchView = (SearchView) searchItem.getActionView();
        assert searchView != null;
        searchView.setQueryHint("Search Room");
        searchView.setOnQueryTextListener(this);
        searchView.setIconified(false);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("TestNavigationView", "Query submitted: " + query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
//        Log.d("TestNavigationView", "Query Change: " + newText);
//        ArrayList<Room> sotredRoomArrayList = new ArrayList<>();
//
//        for(int index=0; index< roomArrayList.size(); index++){
//            if(roomArrayList.get(index).getName().toLowerCase().contains(newText.toLowerCase())){
//                sotredRoomArrayList.add(roomArrayList.get(index));
//            }
//        }
//        roomsAdapter.setFilteredList(sotredRoomArrayList);
//



        return true;
    }
}