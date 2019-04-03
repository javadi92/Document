package com.example.farhad_javadi.drawerlayout;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import adapter.PersonsAdapter;
import database.DBHelper;
import model.Persons;

public class MainActivity extends AppCompatActivity {

    public static int id;
    ImageView imgDrawerMenu,imgPersonsAdd,imgPersonSelect;
    DrawerLayout drawerLayout;
    RecyclerView recyclerViewPersons;
    PersonsAdapter personsAdapter;
    EditText etPersonName;
    DBHelper dbHelper;
    List<Persons> personsList=new ArrayList<>();
    Button btnImageSelectAlertDilog,btnConfirmAlertDialog;
    private static final int imagePickUp=100;
    private static Uri uri=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final AlertDialog dialog=new AlertDialog.Builder(MainActivity.this).create();

        final View view=LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_alert_dialog_persons_add,null,false);

        btnConfirmAlertDialog=(Button)view.findViewById(R.id.btn_confirm_alert_dialog);
        btnImageSelectAlertDilog=(Button)view.findViewById(R.id.btn_image_select_alert_dialog);
        etPersonName=(EditText)view.findViewById(R.id.et_person_name_alert_dialog);
        imgPersonSelect=(ImageView)view.findViewById(R.id.img_person_alert_dialog);

        btnImageSelectAlertDilog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
                imgPersonSelect.setImageURI(uri);
            }
        });

        btnConfirmAlertDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputStream iStream = null;
                byte[] inputData=null;
                try {
                    iStream = getContentResolver().openInputStream(uri);
                    inputData = getBytes(iStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                App.dbHelper.personsInsert(etPersonName.getText().toString(),"0",inputData);
                populatePersonsRecyclerView();
                dialog.dismiss();
            }
        });

        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);

        recyclerViewPersons=(RecyclerView)findViewById(R.id.recy_person);
        LinearLayoutManager llm=new LinearLayoutManager(MainActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewPersons.setLayoutManager(llm);
        //dbHelper=new DBHelper(MainActivity.this);
        populatePersonsRecyclerView();

        imgPersonsAdd=(ImageView)findViewById(R.id.img_persons_page_add);
        imgPersonsAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgPersonSelect.setImageResource(R.drawable.profile);
                dialog.setView(view);
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
        Cursor cursor=App.dbHelper.getPersons();
        if(cursor.moveToFirst()){
            do{
                Persons persons=new Persons();
                persons.setId(cursor.getInt(0));
                persons.setPersonName(cursor.getString(1));
                persons.setDocumentNumber(cursor.getInt(2));
                byte[] imgByte = cursor.getBlob(3);
                persons.setPersonImage(imgByte);
                //persons.setPersonImage(cursor.getInt(3));
                personsList.add(persons);
            }while (cursor.moveToNext());
        }
        personsAdapter=new PersonsAdapter(MainActivity.this,personsList);
        recyclerViewPersons.setAdapter(personsAdapter);
    }

    private void openGallery(){
        Intent gallery=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,imagePickUp);
        startActivity(gallery);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==imagePickUp && resultCode==RESULT_OK){
            uri=data.getData();
            imgPersonSelect.setImageURI(uri);
            //imgPersonSelect.setImageResource(Integer.parseInt(uri.toString()));
        }
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}
