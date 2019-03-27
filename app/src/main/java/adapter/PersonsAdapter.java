package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.farhad_javadi.drawerlayout.R;
import java.util.List;
import model.Persons;

public class PersonsAdapter extends RecyclerView.Adapter<PersonsAdapter.viewHolder> {

    private List<Persons> list;
    private Context mContext;

    public PersonsAdapter(Context context,List<Persons> list){
        this.mContext=context;
        this.list=list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.card_person,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.tvPersonName.setText(list.get(position).getPersonName());
        holder.tvDocumentNumber.setText(list.get(position).getDocumentNumber()+"");
        byte[] bitmapdata=list.get(position).getPersonImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
        holder.imgPerson.setImageBitmap(bitmap);
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
}
