package br.edu.ifba.gabrielvinicius.cerveja.Model;


import com.orm.SugarRecord;
import com.orm.dsl.Ignore;
import com.orm.dsl.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Cesta extends SugarRecord {
    @NotNull
    private Date data ;

    @Ignore
    private List<Item> listItens;

    public Cesta() {
        this.data = new Date();
        this.listItens = new ArrayList<>();
    }

    public Cesta(Date data, List<Item> listItens) {
        this.data = data;
        this.listItens = listItens;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public List<Item> getListItens() {
        if(listItens == null){
            listItens = new ArrayList<>();
        }
        return listItens;
    }

    public void setListItens(List<Item> listItens) {
        this.listItens = listItens;
    }


}
