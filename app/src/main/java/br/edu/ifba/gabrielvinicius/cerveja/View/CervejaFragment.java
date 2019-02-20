package br.edu.ifba.gabrielvinicius.cerveja.View;

import android.annotation.SuppressLint;
import android.os.Bundle;
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

import br.edu.ifba.gabrielvinicius.cerveja.Controller.MarcaDAO;
import br.edu.ifba.gabrielvinicius.cerveja.Model.Cerveja;
import br.edu.ifba.gabrielvinicius.cerveja.Model.Marca;
import br.edu.ifba.gabrielvinicius.cerveja.R;
import br.edu.ifba.gabrielvinicius.cerveja.View.Adapter.CervejaAdapter;

public class CervejaFragment extends Fragment {
    private Cerveja target;
    private EditText editTextNome;
    private Button buttonSalve;
    private Button buttonCancel;
    private RecyclerView.Adapter adapter;
    private AutoCompleteTextView autoCompleteTextViewCerveja;

    @SuppressLint("ValidFragment")
    public CervejaFragment(Cerveja target) {
        super();
        this.target = target;
    }
    public CervejaFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.content_cerveja_save, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        buttonSalve = ((Button) getView().findViewById(R.id.btnSalvar_Cerveja));
        buttonCancel = ((Button) getView().findViewById(R.id.btnCancelar_Cerveja));
        editTextNome = ((EditText) getView().findViewById(R.id.cervejaNome));
        adapter =  ((RecyclerView) getActivity().findViewById(R.id.recyclerView)).getAdapter();
        autoCompleteTextViewCerveja = ((AutoCompleteTextView) getView().findViewById(R.id.cervejaMarca));
        configureButtonSalve();
        configureButtonCancel();
        configureAutoCompleteMarca();

        if (target == null) {
            target = new Cerveja();
        }
             editTextNome.setText(target.getNome());
             autoCompleteTextViewCerveja.setText(target.getMarca().getNome());
    }
    private void configureAutoCompleteMarca(){
       ArrayAdapter<Marca> adapterArray = new ArrayAdapter<Marca>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,MarcaDAO.getInstance().listAll());

        autoCompleteTextViewCerveja.setAdapter(adapterArray);

    }
    private void configureButtonCancel(){
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback();
            }
        });
    }

    private void configureButtonSalve(){


        buttonSalve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!autoCompleteTextViewCerveja.getText().toString().isEmpty()||!editTextNome.getText().toString().isEmpty())
                target.setNome(editTextNome.getText().toString());

                Marca selectedItem = MarcaDAO.getInstance().getByNome(autoCompleteTextViewCerveja.getText().toString());

                if(selectedItem == null){

                    selectedItem = MarcaDAO.getInstance().insert(new Marca(autoCompleteTextViewCerveja.getText().toString()));
                }
                    target.setMarca(selectedItem);


                ((CervejaAdapter)adapter).adiciona(target);



               callback();

            }
        });
    }

    private void callback(){
        target = null;
        clean();
        getActivity().findViewById(R.id.recyclerView).setVisibility(View.VISIBLE);
        onDestroy();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.remove(this);
        fragmentTransaction.commit();
    }

    private void clean(){
        autoCompleteTextViewCerveja.setText("");
        editTextNome.setText("");
    }

}
