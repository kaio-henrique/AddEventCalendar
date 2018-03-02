package br.com.test.kaioh.addeventcalendar;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import br.com.test.kaioh.addeventcalendar.util.CalendarUtil;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private PermissionManager permissionManager;
    private CalendarUtil calendarUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        permissionManager = new PermissionManager();
        calendarUtil = new CalendarUtil(MainActivity.this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (permissionManager.userHasPermission(MainActivity.this)) {
                        calendarUtil.addEventToCalendar();

                        Snackbar.make(view, R.string.event_add_success, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {
                        permissionManager.requestPermission(MainActivity.this);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Snackbar.make(view, R.string.event_not_add, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
