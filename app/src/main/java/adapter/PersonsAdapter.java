package adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farhad_javadi.drawerlayout.App;
import com.example.farhad_javadi.drawerlayout.MainActivity;
import com.example.farhad_javadi.drawerlayout.R;
import java.util.ArrayList;
import java.util.List;
import activities.Person;
import model.PersonsModel;


public class PersonsAdapter extends RecyclerView.Adapter<PersonsAdapter.viewHolder> {

    private List<PersonsModel> list;
    private Context mContext;
    public static int id;
    private boolean multiSelect;
    private List<PersonsModel> selectedItem;
    private ActionMode actionMode;

    public PersonsAdapter(Context context,List<PersonsModel> list){
        this.mContext=context;
        this.list=list;
        multiSelect=false;
        selectedItem=new ArrayList<>();
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.card_person,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, final int position) {
        if (!multiSelect){
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
        holder.tvPersonName.setText(list.get(position).getPersonName());
        holder.tvDocumentNumber.setText(list.get(position).getDocumentNumber()+"");
        byte[] bitmapdata=list.get(position).getPersonImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
        holder.imgPerson.setImageBitmap(bitmap);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(multiSelect){
                    selectItem(position, holder);
                }
                else{
                    Intent intent = new Intent(mContext, Person.class);
                    MainActivity.id =list.get(position).getId();
                    mContext.startActivity(intent);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                actionMode=((MainActivity)holder.itemView.getContext()).startSupportActionMode(callback);
                MainActivity.toolbar.setVisibility(View.GONE);
                selectItem(position, holder);
                return true;
            }
        });
    }

    private void selectItem(int position, @NonNull viewHolder holder) {
        if(selectedItem.contains(list.get(position))){
            selectedItem.remove(list.get(position));
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
        else {
            selectedItem.add(list.get(position));
            holder.itemView.setBackgroundColor(Color.LTGRAY);
        }
        actionMode.setTitle(selectedItem.size()+" انتخاب شده ");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{

        TextView tvPersonName,tvDocumentNumber;
        ImageView imgPerson;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvPersonName=(TextView)itemView.findViewById(R.id.tv_card_person_name);
            tvDocumentNumber=(TextView)itemView.findViewById(R.id.tv_card_document_number);
            imgPerson=(ImageView)itemView.findViewById(R.id.img_card_person);
        }
    }

    private ActionMode.Callback callback=new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            menu.add("حذف");
            multiSelect=true;
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
            if(selectedItem.size()>0){
                AlertDialog dialog;
                AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                if(selectedItem.size()==1){
                    builder.setMessage("آیا از حدف این مورد اطمینان دارید؟");
                }
                else {
                    builder.setMessage("آیا از حدف این موارد اطمینان دارید؟");
                }
                builder.setPositiveButton("بله", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(PersonsModel personsModel:selectedItem){
                            App.dbHelper.deleteData(personsModel.getId());
                            list.remove(personsModel);
                        }
                        mode.finish();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog=builder.create();
                dialog.show();
            }
            //mode.finish();
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            selectedItem.clear();
            multiSelect=false;
            notifyDataSetChanged();
            MainActivity.toolbar.setVisibility(View.VISIBLE);
        }
    };
}
