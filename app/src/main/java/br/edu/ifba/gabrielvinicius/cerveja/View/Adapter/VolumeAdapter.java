package br.edu.ifba.gabrielvinicius.cerveja.View.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.orm.SugarRecord;

import java.util.List;

import br.edu.ifba.gabrielvinicius.cerveja.Controller.VolumeDAO;
import br.edu.ifba.gabrielvinicius.cerveja.Model.Volume;
import br.edu.ifba.gabrielvinicius.cerveja.R;
import br.edu.ifba.gabrielvinicius.cerveja.View.Holder.VolumeHolder;
import br.edu.ifba.gabrielvinicius.cerveja.View.VolumeFragment;

public class VolumeAdapter extends RecyclerView.Adapter<VolumeHolder> {
    private List<Volume> list;
    private int position_;

    public VolumeAdapter(List<Volume> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public VolumeHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        final VolumeHolder holder = new VolumeHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_line_view, parent, false));
        holder.btnEditar.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentTransaction fragmentTransaction = ((FragmentActivity) getActivity(v)).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.fragment_content,new VolumeFragment(list.get(holder.getAdapterPosition())));
                getActivity(v).findViewById(R.id.recyclerView).setVisibility(View.INVISIBLE);
                fragmentTransaction.commit();
            }
        });


        holder.btnExcluir.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View v) {
                final View view = v;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Confirmação")
                        .setMessage("Tem certeza que deseja excluir?")
                        .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                remove(list.get(holder.getAdapterPosition()));
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .create()
                        .show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VolumeHolder holder, int position) {
        holder.nome.setText(list.get(position).getNome());
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        FloatingActionButton fab = (FloatingActionButton) getActivity(recyclerView).findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentTransaction fragmentTransaction = ((FragmentActivity) getActivity(v)).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.fragment_content,new VolumeFragment());
                getActivity(v).findViewById(R.id.recyclerView).setVisibility(View.INVISIBLE);
                fragmentTransaction.commit();
            }
        });

        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setList(List<Volume> list) {
        this.list = list;
    }

    @Override
    public int getItemCount() {

        return list != null ? list.size() : 0;
    }

    private Activity getActivity(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    public void remove(SugarRecord target) {
        VolumeDAO.getInstance().delete((Volume) target);
        int position = list.indexOf((Volume) target);
        list.remove(position);
        notifyItemRemoved(position);

    }
    public void adiciona(SugarRecord target){
        list.add(VolumeDAO.getInstance().insert((Volume) target));
        notifyItemInserted(getItemCount());
    }
    public void atualiza(SugarRecord target){
        list.set(list.indexOf((Volume) target), VolumeDAO.getInstance().update((Volume) target));
        notifyItemChanged(list.indexOf((Volume) target));
    }
}
