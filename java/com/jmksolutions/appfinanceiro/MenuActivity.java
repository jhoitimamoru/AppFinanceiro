package com.jmksolutions.appfinanceiro;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import com.jmksolutions.appfinanceiro.Model.ReceitaModel;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    FragmentTransaction fragmentTransaction;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,toolbar,R.string.abrir_drawer_navegacao,R.string.fechar_drawer_navegacao);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
           // super.onBackPressed();
            Intent intentMainActivity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intentMainActivity);
            finish();
        }
    }


    protected void ChamarCadastro (View view){
        Intent intentRedirecionar;
        intentRedirecionar = new Intent(this, DespesaActivity.class);
        startActivity(intentRedirecionar);
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_despesa:
                Intent intent = new Intent(MenuActivity.this, DespesaActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_receita:
                Intent intent1 = new Intent(MenuActivity.this, ReceitaActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_conta:
                Intent intent2 = new Intent(MenuActivity.this, ContaActivity.class);
                startActivity(intent2);
                break;
            case R.id.nav_config:
                Intent intent3 = new Intent(MenuActivity.this, ConfigActivity.class);
                startActivity(intent3);
                break;
        }
        return  false;
        }

}
