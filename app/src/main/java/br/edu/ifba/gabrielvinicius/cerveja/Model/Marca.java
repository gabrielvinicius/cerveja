package br.edu.ifba.gabrielvinicius.cerveja.Model;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;
import com.orm.dsl.NotNull;
import com.orm.dsl.Unique;

import java.util.ArrayList;
import java.util.List;

public class Marca extends SugarRecord {
    @NotNull
    @Unique
    private String nome;
    @Ignore
    private List<Cerveja> list;

    public Marca(){
        nome ="";
        list = new ArrayList<Cerveja>();

    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public Marca(String nome) {
        this.nome = nome;
    }

    public Marca(String nome, List<Cerveja> list) {
        this.nome = nome;
        this.list = list;
    }

    public String getNome() {
        return nome;
    }

    public List<Cerveja> getList() {
        return list;
    }

    public void setList(List<Cerveja> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return nome;
    }
}
