package hu.ait.studybuddy;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import hu.ait.studybuddy.touch.ClassTouchHelperCallback;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private ClassRecyclerAdapter classRecyclerAdapter;
    RecyclerView recyclerClass;
    Button btnAdd;
    EditText etSearch;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    //search
                    btnAdd.setVisibility(View.GONE);
                    etSearch.setVisibility(View.GONE);
                    //Classes
                    recyclerClass.setVisibility(View.VISIBLE);
                    return true;

                case R.id.navigation_dashboard:

                    //class
                    recyclerClass.setVisibility(View.GONE);
                    //search
                    btnAdd.setVisibility(View.VISIBLE);
                    etSearch.setVisibility(View.VISIBLE);

                    btnAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showNewClassDialog();
                        }
                    });

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ((MainApplication)getApplication()).openRealm();

        mTextMessage = (TextView) findViewById(R.id.message);

        recyclerClass = (RecyclerView) findViewById(R.id.recylerView);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        etSearch = (EditText) findViewById(R.id.etSearch);

        // recycler
        recyclerClass.setHasFixedSize(true);
        final LinearLayoutManager layoutManager =
                new LinearLayoutManager(MainActivity.this);
        recyclerClass.setLayoutManager(layoutManager);
        classRecyclerAdapter = new ClassRecyclerAdapter(MainActivity.this,((MainApplication)getApplication()).getRealm());
        recyclerClass.setAdapter(classRecyclerAdapter);

        ItemTouchHelper.Callback callback = new ClassTouchHelperCallback(classRecyclerAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerClass);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }


    private void showNewClassDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter class here");

        final EditText etClass = new EditText(this);
        builder.setView(etClass);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                classRecyclerAdapter.addCity(etClass.getText().toString());
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();

    }

}
