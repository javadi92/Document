package com.example.farhad_javadi.drawerlayout;

import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import adapter.PersonsAdapter;
import database.DBHelper;
import model.Persons;

public class MainActivity extends AppCompatActivity {

    ImageView imgDrawerMenu,imgPersonsAdd;
    DrawerLayout drawerLayout;
    RecyclerView recyclerViewPersons;
    PersonsAdapter personsAdapter;
    DBHelper dbHelper;
    List<Persons> personsList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);

        recyclerViewPersons=(RecyclerView)findViewById(R.id.recy_person);
        LinearLayoutManager llm=new LinearLayoutManager(MainActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewPersons.setLayoutManager(llm);
        dbHelper=new DBHelper(MainActivity.this);
        populatePersonsRecyclerView();

        imgPersonsAdd=(ImageView)findViewById(R.id.img_persons_page_add);
        imgPersonsAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Add button in persons clicked",Toast.LENGTH_LONG).show();
                AlertDialog dialog;
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                final EditText editText=new EditText(MainActivity.this);
                builder.setTitle("انتخاب نام");
                builder.setPositiveButton("تایید", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(dbHelper.personsInsert(editText.getText().toString(),"0","0")!=-1){
                            populatePersonsRecyclerView();
                        }
                    }
                    });
                builder.setView(editText);
                dialog=builder.create();
                dialog.show();

            }
        });

        imgDrawerMenu=(ImageView) findViewById(R.id.img_menu);
        imgDrawerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawerLayout.isDrawerOpen(Gravity.RIGHT)){
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                }
                else {
                    drawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
        });

    }

    private void populatePersonsRecyclerView(){
        personsList.clear();
        Cursor cursor=dbHelper.getPersons();
        if(cursor.moveToFirst()){
            do{
                Persons persons=new Persons();
                persons.setPersonName(cursor.getString(1));
                persons.setDocumentNumber(cursor.getInt(2));
                //persons.setPersonImage(cursor.getInt(3));
                personsList.add(persons);
            }while (cursor.moveToNext());
        }
        personsAdapter=new PersonsAdapter(MainActivity.this,personsList);
        recyclerViewPersons.setAdapter(personsAdapter);
    }
}
