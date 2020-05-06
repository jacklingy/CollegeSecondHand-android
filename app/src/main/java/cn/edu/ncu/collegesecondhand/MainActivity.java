package cn.edu.ncu.collegesecondhand;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

import cn.edu.ncu.collegesecondhand.ui.cart.CartFragment;
import cn.edu.ncu.collegesecondhand.ui.category.CategoryFragment;
import cn.edu.ncu.collegesecondhand.ui.home.HomeFragment;
import cn.edu.ncu.collegesecondhand.ui.home.SearchActivity;
import cn.edu.ncu.collegesecondhand.ui.my.MyFragment;


/**
 * Created by ren lingyun on 2020/4/14
 */
public class MainActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private int index;
    private int currentIndex = 0;
    private Fragment[] fragments;
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            index = 0;
                            actionBar.show();
                            break;
                        case R.id.nav_category:
                            index = 1;
                            actionBar.show();
                            break;
                        case R.id.nav_cart:
                            index = 2;
                            actionBar.hide();
                            break;
                        case R.id.nav_my:
                            index = 3;
                            actionBar.hide();
                            break;

                    }

                    if (currentIndex != index) {//switched fragment
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.hide(fragments[currentIndex]);// hide current fragment;
                        if (!fragments[index].isAdded()) {//if not added the target fragment
                            fragmentTransaction.add(R.id.fragment_container, fragments[index]);//add this fragment
                        }
                        fragmentTransaction.show(fragments[index]).commit();//show target fragment
                    }
                    currentIndex = index;
                    return true;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.main_activity_toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        LinearLayout linearLayout_search = findViewById(R.id.main_activity_layout);

        //toolbar click listener
        linearLayout_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        //instantiate fragments
        HomeFragment homeFragment = new HomeFragment();
        CartFragment cartFragment = new CartFragment();
        CategoryFragment categoryFragment = new CategoryFragment();
        MyFragment myFragment = new MyFragment();
        fragments = new Fragment[]{homeFragment, categoryFragment, cartFragment, myFragment};

        //init view, add home to fragment manager
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, homeFragment)
                .add(R.id.fragment_container, categoryFragment)
                .add(R.id.fragment_container, cartFragment)
                .add(R.id.fragment_container, myFragment)
                .hide(categoryFragment)
                .hide(cartFragment)
                .hide(myFragment)
                .show(homeFragment)
                .commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //set label always visible
        bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

     /*   BottomNavigationView navView = findViewById(R.id.bottom_navigation);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_category, R.id.nav_cart,R.id.nav_my)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.fragment_container);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);*/
    }
}
