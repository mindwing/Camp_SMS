package kr.mindwing.camp_sms;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import kr.mindwing.camp_sms.lib.ConversationInfo;
import kr.mindwing.camp_sms.lib.SmsUtil;

public class ContactsListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<ConversationInfo> conversationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent compositionIntent = new Intent(
                        ContactsListActivity.this,
                        StartCompositionActivity.class);
                startActivity(compositionIntent);

            }
        });

        conversationList = SmsUtil.getConversationInfoList(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new SmsConversationInfosAdapter(
                conversationList));

        RecyclerView.LayoutManager rvLayoutManager = new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(rvLayoutManager);

        SmsUtil.checkDefaultSmsApp(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contacts_list, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Snackbar.make(recyclerView, "Settings 기능을 구현해주세요.", Snackbar.LENGTH_LONG).setAction("Action", null).show();

        return true;
    }
}
