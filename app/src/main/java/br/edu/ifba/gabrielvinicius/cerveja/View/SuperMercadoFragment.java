package br.edu.ifba.gabrielvinicius.cerveja.View;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import br.edu.ifba.gabrielvinicius.cerveja.Model.SuperMercado;
import br.edu.ifba.gabrielvinicius.cerveja.R;
import br.edu.ifba.gabrielvinicius.cerveja.View.Adapter.SuperMercadoAdapter;

public class SuperMercadoFragment extends Fragment {
    private SuperMercado target;
    private EditText editTextNome;
    private Button buttonSalve;
    private Button buttonCancel;
    private RecyclerView.Adapter adapter;

    @SuppressLint("ValidFragment")
    public SuperMercadoFragment(SuperMercado target) {
        super();
        this.target = target;
    }

    public SuperMercadoFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.content_supermercado_save, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        buttonSalve = ((Button) getView().findViewById(R.id.btnSalvar_SuperMercado));
        buttonCancel = ((Button) getView().findViewById(R.id.btnCancelar_SuperMercado));
        editTextNome = ((EditText) getView().findViewById(R.id.supermercado));
        adapter =  ((RecyclerView) getActivity().findViewById(R.id.recyclerView)).getAdapter();
        configureButtonSalve();
        configureButtonCancel();


        if (target == null) {
            target = new SuperMercado();
        }
        editTextNome.setText(target.getNome());

    }

    private void configureButtonSalve(){


        buttonSalve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editTextNome.getText().toString().isEmpty())
                    target.setNome(editTextNome.getText().toString());

                ((SuperMercadoAdapter)adapter).adiciona(target);



                callback();

            }
        });
    }


    private void configureButtonCancel(){
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        editTextNome.setText("");
    }
}
