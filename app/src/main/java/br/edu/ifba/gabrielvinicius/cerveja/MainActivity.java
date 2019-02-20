package br.edu.ifba.gabrielvinicius.cerveja;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import com.orm.SugarContext;

import br.edu.ifba.gabrielvinicius.cerveja.Controller.CervejaDAO;
import br.edu.ifba.gabrielvinicius.cerveja.Controller.CestaDAO;
import br.edu.ifba.gabrielvinicius.cerveja.Controller.MarcaDAO;
import br.edu.ifba.gabrielvinicius.cerveja.Controller.SuperMercadoDAO;
import br.edu.ifba.gabrielvinicius.cerveja.Controller.VolumeDAO;
import br.edu.ifba.gabrielvinicius.cerveja.Model.Cesta;
import br.edu.ifba.gabrielvinicius.cerveja.View.Adapter.CervejaAdapter;
import br.edu.ifba.gabrielvinicius.cerveja.View.Adapter.CestaAdapter;
import br.edu.ifba.gabrielvinicius.cerveja.View.Adapter.MarcaAdapter;
import br.edu.ifba.gabrielvinicius.cerveja.View.Adapter.SuperMercadoAdapter;
import br.edu.ifba.gabrielvinicius.cerveja.View.Adapter.VolumeAdapter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
private RecyclerView mRecyclerView;
private RecyclerView.Adapter adapter = null;
private Toolbar toolbar;
private FragmentManager fragmentManager;
private FragmentTransaction fragmentTransaction;
private ArrayAdapter adapterArray;
private Fragment currentFragment;
private Intent intent;
private  Long idTagert;
private  FloatingActionButton fab;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SugarContext.init(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


       fab = (FloatingActionButton) findViewById(R.id.fab);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        configurarRecycler();
        fragmentManager = getSupportFragmentManager();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        findViewById(R.id.recyclerView).setVisibility(View.VISIBLE);

        if(currentFragment != null){
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(currentFragment);
            fragmentTransaction.commit();
        }else if(!fragmentManager.getFragments().isEmpty()){
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragmentManager.getFragments().get(0));
            fragmentTransaction.commit();
        }
        // Handle navigation view item clicks here.
        mRecyclerView.removeAllViews();
        int id = item.getItemId();

        if (id == R.id.nav_marca) {
            toolbar.setTitle("Marca");
            if(adapter != null && adapter instanceof MarcaAdapter ){
                ((MarcaAdapter) adapter).setList(MarcaDAO.getInstance().listAll());

            }else{
                adapter = new MarcaAdapter(MarcaDAO.getInstance().listAll());

            }

        } else if (id == R.id.nav_cerveja) {
            toolbar.setTitle("Cerveja");
            if(adapter != null && adapter instanceof CervejaAdapter){
                ((CervejaAdapter) adapter).setList(CervejaDAO.getInstance().listAll()); ;
            }else{
                adapter = new CervejaAdapter(CervejaDAO.getInstance().listAll());
            }

        } else if (id == R.id.nav_superMercado) {
            toolbar.setTitle("SuperMercado");

            if(adapter != null && adapter instanceof SuperMercadoAdapter){
                ((SuperMercadoAdapter) adapter).setList(SuperMercadoDAO.getInstance().listAll()); ;
            }else{
                adapter = new SuperMercadoAdapter(SuperMercadoDAO.getInstance().listAll());
            }

        } else if (id == R.id.nav_volume) {
            toolbar.setTitle("Volume");
            if(adapter != null && adapter instanceof VolumeAdapter){
                ((VolumeAdapter) adapter).setList(VolumeDAO.getInstance().listAll()); ;
            }else{
                adapter = new VolumeAdapter(VolumeDAO.getInstance().listAll());
            }

        } else if (id == R.id.nav_cesta) {
            toolbar.setTitle("Cesta");

            if(adapter != null && adapter instanceof CestaAdapter){
                ((CestaAdapter) adapter).setList(CestaDAO.getInstance().listAll()); ;
            }else{
                adapter = new CestaAdapter(CestaDAO.getInstance().listAll());
            }

        } else if (id == R.id.nav_send) {

        }


        mRecyclerView.removeAllViews();
        mRecyclerView.setAdapter(adapter);
        findViewById(R.id.recyclerView).setVisibility(View.VISIBLE);
        //mRecyclerView.swapAdapter(adapter,true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void configurarRecycler() {
        // Configurando o gerenciador de layout para ser uma lista.
        this.mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
          // Adiciona o adapter que irá anexar os objetos à lista.

       adapter = new CestaAdapter(CestaDAO.getInstance().listAll());


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cesta cesta = CestaDAO.getInstance().insert(new Cesta());

               ((CestaAdapter) mRecyclerView.getAdapter()).adiciona(cesta);

            }
        });


        mRecyclerView.setAdapter(adapter);
            mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
              }

    }

