package br.edu.ifba.gabrielvinicius.cerveja.View;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import br.edu.ifba.gabrielvinicius.cerveja.Controller.CervejaDAO;
import br.edu.ifba.gabrielvinicius.cerveja.Controller.MarcaDAO;
import br.edu.ifba.gabrielvinicius.cerveja.Controller.SuperMercadoDAO;
import br.edu.ifba.gabrielvinicius.cerveja.Controller.VolumeDAO;
import br.edu.ifba.gabrielvinicius.cerveja.Model.Cerveja;
import br.edu.ifba.gabrielvinicius.cerveja.Model.Item;
import br.edu.ifba.gabrielvinicius.cerveja.Model.Marca;
import br.edu.ifba.gabrielvinicius.cerveja.Model.SuperMercado;
import br.edu.ifba.gabrielvinicius.cerveja.Model.Volume;
import br.edu.ifba.gabrielvinicius.cerveja.R;
import br.edu.ifba.gabrielvinicius.cerveja.View.Adapter.ItemAdapter;

public class ItemFragment extends Fragment {
    private Item target;
    private AutoCompleteTextView editTextNomeCerveja;
    private AutoCompleteTextView editTextNomeVolume;
    private AutoCompleteTextView editTextNomeMarca;
    private EditText editTextNomeVolumeML;
    private AutoCompleteTextView editTextNomeSuperMercado;
    private EditText editTextNomePreco;
    private Button buttonSalve;
    private Button buttonCancel;
    private RecyclerView.Adapter adapter;
    private FloatingActionButton fab;

    @SuppressLint("ValidFragment")
    public ItemFragment(Item target) {
        this.target = target;

    }

    public ItemFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.content_item_save, container, false);
    }

    private void configureButtonSalve(){


        buttonSalve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editTextNomeCerveja.getText().toString().isEmpty()||!editTextNomeMarca.getText().toString().isEmpty()||!editTextNomePreco.getText().toString().isEmpty()||!editTextNomeSuperMercado.getText().toString().isEmpty()||!editTextNomeVolume.getText().toString().isEmpty()||!editTextNomeVolumeML.getText().toString().isEmpty()) {

                    Marca marcaTMP = MarcaDAO.getInstance().getByNome(editTextNomeMarca.getText().toString());
                    if(marcaTMP == null ){

                        marcaTMP = MarcaDAO.getInstance().insert(new Marca(editTextNomeMarca.getText().toString()));
                    }

                    Cerveja cervejaTMP = CervejaDAO.getInstance().getByNome(editTextNomeCerveja.getText().toString(),marcaTMP);

                    if (cervejaTMP == null){
                        cervejaTMP = CervejaDAO.getInstance().insert(new Cerveja(editTextNomeCerveja.getText().toString(),marcaTMP));
                    }

                    SuperMercado superMercado = SuperMercadoDAO.getInstance().getByNome(editTextNomeSuperMercado.getText().toString());
                    if(superMercado == null){

                        superMercado = SuperMercadoDAO.getInstance().insert(new SuperMercado(editTextNomeSuperMercado.getText().toString()));
                    }

                    Volume volume = VolumeDAO.getInstance().getByNome(editTextNomeVolume.getText().toString());
                        if (volume == null){
                            volume = VolumeDAO.getInstance().insert(new Volume(Integer.valueOf(editTextNomeVolumeML.getText().toString()), editTextNomeVolume.getText().toString()));
                        }

                        Float preco = Float.valueOf(editTextNomePreco.getText().toString());


                    target.setCerveja(cervejaTMP);
                    target.setPreco(preco);
                    target.setSuperMercado(superMercado);
                    target.setVolume(volume);

                    ((ItemAdapter)adapter).adiciona(target);

                    editTextNomePreco.setText("");
                    editTextNomeCerveja.setText("");
                    editTextNomeVolume.setText("");
                    editTextNomeVolumeML.setText("");
                    editTextNomeMarca.setText("");



                }
            }
        });
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        buttonSalve = ((Button) getView().findViewById(R.id.btnSalvar_Item));
        buttonCancel = ((Button) getView().findViewById(R.id.btnCancelar_Item));
        editTextNomeVolume = ((AutoCompleteTextView) getView().findViewById(R.id.itemVolume));
        editTextNomeVolumeML = ((EditText) getView().findViewById(R.id.itemVolumeMl));
        editTextNomeCerveja = ((AutoCompleteTextView) getView().findViewById(R.id.itemCerveja));
        editTextNomeSuperMercado = ((AutoCompleteTextView) getView().findViewById(R.id.itemSuperMercado));
        editTextNomeMarca = ((AutoCompleteTextView) getView().findViewById(R.id.itemMarca));
        editTextNomePreco = ((EditText) getView().findViewById(R.id.itemPreço));
        adapter =  ((RecyclerView) getActivity().findViewById(R.id.recyclerView)).getAdapter();
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        configureAutoComplete();
        configureButtonCancel();
        configureButtonSalve();
        editTextNomePreco.setText(String.valueOf(target.getPreco()));
        editTextNomeSuperMercado.setText(target.getSuperMercado().getNome());
        editTextNomeCerveja.setText(target.getCerveja().getNome());
        editTextNomeVolume.setText(target.getVolume().getNome());
        editTextNomeVolumeML.setText(String.valueOf(target.getVolume().getVolume()));
        editTextNomeMarca.setText(target.getCerveja().getMarca().getNome());

    }



    private void configureButtonCancel(){
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback();
            }
        });
    }


    private void configureAutoComplete(){
        editTextNomeVolumeML.setFocusable(false);
        editTextNomeCerveja.setFocusable(false);
        editTextNomeSuperMercado.setAdapter(new ArrayAdapter<SuperMercado>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,SuperMercadoDAO.getInstance().listAll()));
        editTextNomeVolume.setAdapter(new ArrayAdapter<Volume>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,VolumeDAO.getInstance().listAll()));
        ArrayAdapter<Marca> adapterArray = new ArrayAdapter<Marca>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,MarcaDAO.getInstance().listAll());
        editTextNomeMarca.setAdapter(adapterArray);

        editTextNomeCerveja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextNomeMarca.getText().toString().isEmpty()){
                    editTextNomeCerveja.setFocusable(false);
                    editTextNomeCerveja.setText("");
                }else{
                    editTextNomeCerveja.setFocusableInTouchMode(true);
                    Marca marca = MarcaDAO.getInstance().getByNome(editTextNomeMarca.getText().toString());
                    if(marca  != null){
                        editTextNomeCerveja.setAdapter(new ArrayAdapter<Cerveja>(getActivity(),
                                android.R.layout.simple_dropdown_item_1line,marca.getList()));
                    }

                }
            }
        });

        editTextNomeVolumeML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextNomeVolume.getText().toString().isEmpty()){
                    editTextNomeVolumeML.setText("");
                    editTextNomeVolumeML.setFocusable(false);
                }else{
                    Volume volume = VolumeDAO.getInstance().getByNome(editTextNomeVolume.getText().toString());
                    if(volume != null){
                        editTextNomeVolumeML.setText(String.valueOf(volume.getVolume()));
                    }else{
                        editTextNomeVolumeML.setText("");
                        editTextNomeVolumeML.setFocusableInTouchMode(true);
                    }
                }
            }
        });
    }





    private void callback(){
        RecyclerView mRecyclerView = getActivity().findViewById(R.id.recyclerView);
        clean();
        mRecyclerView.setVisibility(View.VISIBLE);
        onDestroy();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.remove(this);
        fragmentTransaction.commit();

    }

    private void clean(){

        editTextNomePreco.setText("");
        editTextNomeSuperMercado.setText("");
        editTextNomeCerveja.setText("");
        editTextNomeVolume.setText("");
        editTextNomeVolumeML.setText("");
        editTextNomeMarca.setText("");
    }
}
