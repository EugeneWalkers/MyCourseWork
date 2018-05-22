package ew.mycoursework;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String PASSWORD = "password";
    public static final String LOGIN = "login";
    public static final String TYPE = "type";
    public static final String RESULT = "result";
    public static final String TEST_NAME = "test_name";
    public static final String QUESTIONS = "questions";
    public static final String RESULTS = "results";
    public static final String USER_BUNDLE = "user_bundle";


    public static final int REQUEST_OK = 1;
    public static final int REQUEST_RESULT = 2;

    String result;
    FragmentTransaction fTrans;
    FragmentManager fManager;
    ProfileFragment profile;
    TestsFragment tests;
    int selectedItem;
    User user;

    Bundle profileBundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setNameAndType();
        setNavigationView();
        profile = new ProfileFragment();
        tests = new TestsFragment();
        fManager = getSupportFragmentManager();
        fTrans = fManager.beginTransaction();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setToggle(toolbar);
        profileBundle = new Bundle();
        profileBundle.putString(LOGIN, user.getLogin());
        profileBundle.putString(PASSWORD, user.getPassword());
        profileBundle.putString(NAME, user.getName());
        profileBundle.putString(TYPE, user.getType());
        profileBundle.putString(ID, user.getId());
        profileBundle.putStringArrayList(RESULTS, user.getResults());
        profile.setArguments(profileBundle);
        if (savedInstanceState == null){
            fTrans.add(R.id.content_frame, profile);
            fTrans.commit();
            toolbar.setTitle(getResources().getString(R.string.profile));
        }
    }

    private void setToggle(Toolbar toolbar){
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setNameAndType(){
        Intent intent = getIntent();
        String login = intent.getStringExtra(LOGIN);
        String password = intent.getStringExtra(PASSWORD);
        String name = intent.getStringExtra(NAME);
        String type = intent.getStringExtra(TYPE);
        String id = intent.getStringExtra(ID);
        ArrayList<String> results;
        if (intent.hasExtra(RESULTS)){
            results = intent.getStringArrayListExtra(RESULTS);
        }
        else{
            results = new ArrayList<>();
        }
        user = new User(login, password, name, type, id, results);
    }

    private void setNavigationView(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navText = headerView.findViewById(R.id.header_text);
        navText.setText("Welcome, " + user.getName() + "!");
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Toolbar toolbar = findViewById(R.id.toolbar);
        switch (selectedItem) {
            default:
            case R.id.item_1:
                Bundle bundle = new Bundle();
                bundle.putString(NAME, user.getName());
                bundle.putString(TYPE, user.getType());
                bundle.putString(ID, user.getId());
                profile.setArguments(bundle);
                fTrans.add(R.id.content_frame, profile);
                toolbar.setTitle(getResources().getString(R.string.profile));
                break;
            case R.id.item_2:
                tests.setArguments(profileBundle);
                fTrans.add(R.id.content_frame, tests);
                toolbar.setTitle(getResources().getString(R.string.tests));
                break;
        }
        fTrans.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;
        Bundle bundle = new Bundle();

        switch (id) {
            default:
            case R.id.item_1:
                fragmentClass = ProfileFragment.class;
                bundle.putString(NAME, user.getName());
                bundle.putString(TYPE, user.getType());
                bundle.putString(ID, user.getId());
                break;

            case R.id.item_2:
                bundle = profileBundle;
                fragmentClass = TestsFragment.class;

                break;

            case R.id.item_3:

                break;

            case R.id.item_4:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }

        if (fragmentClass != null){
            try {
                fragment = (Fragment) fragmentClass.newInstance();
                fragment.setArguments(bundle);
            } catch (Exception e) {
                e.printStackTrace();
            }

            fManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            item.setChecked(true);
            ((Toolbar)findViewById(R.id.toolbar)).setTitle(item.getTitle());
            selectedItem = id;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == REQUEST_OK){
           switch (requestCode){
               case REQUEST_RESULT:
                   result = data.getStringExtra(RESULT);
                   user.setResults(data.getStringArrayListExtra(RESULTS));
                   profileBundle.remove(RESULTS);
                   profileBundle.putStringArrayList(RESULTS, user.getResults());
                   Log.i(RESULT, String.valueOf(result));
                   Toast.makeText(this, "Your result: " + Double.valueOf(result)*100 + "%", Toast.LENGTH_SHORT).show();
                   break;
           }
        }
       else {
            Toast.makeText(this, "Wrong result!", Toast.LENGTH_SHORT).show();
        }
    }
}
