package com.duongkk.sqlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.duongkk.sqlite.adapter.Adapter;
import com.duongkk.sqlite.database.DatabaseUtils;
import com.duongkk.sqlite.models.Student;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements Adapter.CallBack {

    @BindView(R.id.edt)
    EditText edt;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    @BindView(R.id.rcv)
    RecyclerView rcv;
    private Adapter adapter;
    private List<Student> students;
    private DatabaseUtils databaseUtils;
    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        databaseUtils = new DatabaseUtils(this);
        students = new ArrayList<>();
        students.addAll(databaseUtils.getAllContacts());
        adapter = new Adapter(students, this, this);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        rcv.setHasFixedSize(true);
        rcv.setAdapter(adapter);
    }

    private void refreshList() {
        students.clear();
        students.addAll(databaseUtils.getAllContacts());
        adapter.notifyDataSetChanged();
        edt.setText("");
    }

    @OnClick({R.id.btn_add, R.id.btn_delete, R.id.btn_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                if (!edt.getText().toString().isEmpty()) {
                    databaseUtils.add(edt.getText().toString());
                } else {
                    Toast.makeText(this, "Please input your data first!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_delete:
                if(position ==-1 || position>= students.size()) return;
                databaseUtils.delete(students.get(position).getId());
                position =-1;
                break;
            case R.id.btn_update:
                if(position ==-1 || position>= students.size()) return;
                Student student = students.get(position);
                student.setText(edt.getText().toString());
                databaseUtils.updateContact(student);
                break;
        }
        refreshList();
    }

    @Override
    public void onCallBack(int position) {
        edt.setText(students.get(position).getText());
        this.position = position;
    }
}
