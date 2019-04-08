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


public class Person extends AppCompatActivity {

    ImageView imgPerson;
    TextView tvPersonName,tvDocumentNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_page);

        //initialize views
        imgPerson=(ImageView)findViewById(R.id.img_person);
        tvPersonName=(TextView)findViewById(R.id.tv_person);
        tvDocumentNumber=(TextView)findViewById(R.id.tv_document_number);

        //get image,name and document number from selected item in persons page
        Cursor cursor=App.dbHelper.personsGetDataById(MainActivity.id+"");
        if(cursor.moveToFirst()){
            do{
                tvPersonName.setText(cursor.getString(1));
                tvDocumentNumber.setText("تعداد پرونده: "+cursor.getInt(2));
                byte[] bitmapdata=cursor.getBlob(3);
                Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
                imgPerson.setImageBitmap(bitmap);
            }while (cursor.moveToNext());
        }
    }
}
