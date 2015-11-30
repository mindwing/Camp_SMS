package kr.mindwing.camp_sms;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import kr.mindwing.camp_sms.lib.ConversationInfo;
import kr.mindwing.camp_sms.lib.SmsUtil;

public class ContactsListActivity extends AppCompatActivity {

    private static final int REQUEST_READ_SMS_CONTACTS = 0x26;

    private RecyclerView recyclerView;
    private ArrayList<ConversationInfo> conversationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (hasGrantedSMS() && hasGrantedContacts()) {
            setupUI();
        } else {
            requestGrantingPermission();
        }
    }

    private void setupUI() {
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

    private void requestGrantingPermission() {
        if (!hasGrantedSMS() || !hasGrantedContacts()) {

            // Should we show an explanation?
            // This method returns true if the app has requested this permission previously
            // and the user denied the request.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_SMS) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                Toast.makeText(this, "설정에서 'SMS 읽기' 와 '주소록 읽기' 권한을 모두 승인하신 후 재실행해주세요.", Toast.LENGTH_SHORT).show();

                finish();
            } else {
                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_SMS, Manifest.permission.READ_CONTACTS},
                        REQUEST_READ_SMS_CONTACTS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_SMS_CONTACTS:

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length >= 2
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    if (hasGrantedSMS() && hasGrantedContacts()) {
                        setupUI();
                    }
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    Toast.makeText(this, "'SMS 읽기' 와 '주소록 읽기' 권한이 있어야 실행가능합니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }

                break;

            default:

                break;
        }
    }

    private boolean hasGrantedSMS() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS)
                == PackageManager.PERMISSION_GRANTED;
    }

    private boolean hasGrantedContacts() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contacts_list, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int count = 0;

        // TODO 대화상대수

        Snackbar.make(recyclerView, String.format("대화상대는 총 %d 명입니다.", count), Snackbar.LENGTH_LONG).show();

        return true;
    }
}
