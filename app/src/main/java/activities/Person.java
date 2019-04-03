package activities;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.farhad_javadi.drawerlayout.App;
import com.example.farhad_javadi.drawerlayout.MainActivity;
import com.example.farhad_javadi.drawerlayout.R;

import adapter.PersonsAdapter;

public class Person extends AppCompatActivity {

    ImageView imgPerson;
    TextView tvPersonName,tvDocumentNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_page);

        imgPerson=(ImageView)findViewById(R.id.img_person);
        tvPersonName=(TextView)findViewById(R.id.tv_person);
        tvDocumentNumber=(TextView)findViewById(R.id.tv_document_number);

        Cursor cursor=App.dbHelper.personsGetData(MainActivity.id+"");
        if(cursor.moveToFirst()){
            do{
                tvPersonName.setText(cursor.getString(1));
                tvDocumentNumber.setText(cursor.getInt(2)+"");
                byte[] bitmapdata=cursor.getBlob(3);
                Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
                imgPerson.setImageBitmap(bitmap);
            }while (cursor.moveToNext());
        }
    }
}
