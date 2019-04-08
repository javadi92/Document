package activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.farhad_javadi.drawerlayout.MainActivity;
import com.example.farhad_javadi.drawerlayout.R;

import java.util.ArrayList;
import java.util.List;

import adapter.PersonsAdapter;
import model.PersonsModel;

public class PersonSearch extends AppCompatActivity {

    ImageView imgBack;
    RecyclerView recyPersonSearch;
    PersonsAdapter personsAdapterPersonSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_search_page);

        imgBack=(ImageView)findViewById(R.id.img_back_person_search_page);
        recyPersonSearch=(RecyclerView)findViewById(R.id.recy_person_search_page);
        LinearLayoutManager llm=new LinearLayoutManager(PersonSearch.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyPersonSearch.setLayoutManager(llm);
        List<PersonsModel> personAutocompleteList=new ArrayList<>();
        PersonsModel personsAutocomplete=new PersonsModel();
        do{
            personsAutocomplete.setId(MainActivity.cursorToPersonSearchPage.getInt(0));
            personsAutocomplete.setPersonName(MainActivity.cursorToPersonSearchPage.getString(1));
            personsAutocomplete.setDocumentNumber(MainActivity.cursorToPersonSearchPage.getInt(2));
            personsAutocomplete.setPersonImage(MainActivity.cursorToPersonSearchPage.getBlob(3));
            personAutocompleteList.add(personsAutocomplete);
        }while(MainActivity.cursorToPersonSearchPage.moveToNext());
        personsAdapterPersonSearch=new PersonsAdapter(PersonSearch.this,personAutocompleteList);
        recyPersonSearch.setAdapter(personsAdapterPersonSearch);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
