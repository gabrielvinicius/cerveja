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

import br.edu.ifba.gabrielvinicius.cerveja.Model.Volume;
import br.edu.ifba.gabrielvinicius.cerveja.R;
import br.edu.ifba.gabrielvinicius.cerveja.View.Adapter.VolumeAdapter;

public class VolumeFragment extends Fragment {
    private Volume target;
    private EditText editTextNome;
    private EditText editTextML;
    private Button buttonSalve;
    private Button buttonCancel;
    private RecyclerView.Adapter adapter;

    public VolumeFragment() {
        super();
    }

    @SuppressLint("ValidFragment")
    public VolumeFragment(Volume target) {
        this.target = target;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.content_volume_save, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        buttonSalve = ((Button) getView().findViewById(R.id.btnSalvar_Volume));
        buttonCancel = ((Button) getView().findViewById(R.id.btnCancelar_Volume));
        editTextNome = ((EditText) getView().findViewById(R.id.nomeVolume));
        editTextML = ((EditText) getView().findViewById(R.id.txtVolume));
        adapter =  ((RecyclerView) getActivity().findViewById(R.id.recyclerView)).getAdapter();

        configureButtonSalve();
        configureButtonCancel();

        if(target == null){
            target = new Volume();
        }
        editTextNome.setText(target.getNome());
        editTextML.setText(String.valueOf(target.getVolume()));

    }


    private void configureButtonSalve(){


        buttonSalve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editTextNome.getText().toString().isEmpty()||!editTextML.getText().toString().isEmpty())
                    target.setNome(editTextNome.getText().toString());
                    target.setVolume(Integer.parseInt(editTextML.getText().toString()));
                ((VolumeAdapter)adapter).adiciona(target);



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
        editTextML.setText("");
    }
}
