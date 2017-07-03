package hu.ait.studybuddy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import hu.ait.studybuddy.data.Class;
import hu.ait.studybuddy.touch.ClassTouchHelperAdapter;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Jana on 7/1/2017.
 */

public class ClassRecyclerAdapter extends RecyclerView.Adapter<ClassRecyclerAdapter.ViewHolder>
        implements ClassTouchHelperAdapter {

    private List<Class> listClasses;
    private Realm realmClasses;
    private Context context;

    public ClassRecyclerAdapter(Context context, Realm realmClasses) {
        this.realmClasses = realmClasses;
        this.context = context;
        listClasses = new ArrayList<Class>();

        RealmResults<Class> classResults =
                realmClasses.where(Class.class).findAll();

        for (int i = 0; i < classResults.size(); i++) {
            listClasses.add(classResults.get(i));
        }
    }

    @Override
    public ClassRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View classRow = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.class_row, parent, false);
        return new ViewHolder(classRow);
    }

    @Override
    public void onBindViewHolder(ClassRecyclerAdapter.ViewHolder holder, int position) {
        holder.tvCity.setText(listClasses.get(position).getClassName());
        holder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });
    }

    @Override
    public int getItemCount() {
        return listClasses.size();
    }

    @Override
    public void onItemDismiss(int position) {
        realmClasses.beginTransaction();
        listClasses.get(position).deleteFromRealm();
        realmClasses.commitTransaction();
        listClasses.remove(position);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(listClasses, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(listClasses, i, i - 1);
            }
        }

        notifyItemMoved(fromPosition, toPosition);
    }

    public void addCity(String className){
        realmClasses.beginTransaction();
        Class newClass =  realmClasses.createObject(Class.class, UUID.randomUUID().toString());
        newClass.setClassName(className);
        realmClasses.commitTransaction();
        listClasses.add(newClass);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCity;
        private RelativeLayout click;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCity = (TextView) itemView.findViewById(R.id.tvClass);
            click = itemView.findViewById(R.id.relativeLayout);
        }
    }
}
