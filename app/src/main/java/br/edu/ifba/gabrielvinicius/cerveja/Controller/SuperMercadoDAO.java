package br.edu.ifba.gabrielvinicius.cerveja.Controller;

import com.orm.SugarRecord;

import java.util.List;

import br.edu.ifba.gabrielvinicius.cerveja.Model.SuperMercado;

public class SuperMercadoDAO {
    private static SuperMercadoDAO instance;
    private SuperMercadoDAO(){}
    public static SuperMercadoDAO getInstance(){
        if(instance == null){
            instance = new SuperMercadoDAO();
        }
        return instance;
    }
    public SuperMercado insert (SuperMercado target){
        return getById(target.save());
    }
    public SuperMercado update(SuperMercado target){
        return getById(target.update());
    }
    public boolean delete (SuperMercado target){
        return target.delete();
    }
    public SuperMercado getById(Long id){
        return SugarRecord.findById(SuperMercado.class,id);
    }
    public List<SuperMercado> listAll(){
        return SugarRecord.listAll(SuperMercado.class);
    }
    public List<SuperMercado> listLike(String nome){
        return SugarRecord.find(SuperMercado.class," nome LIKE ? ",nome);
    }
    public SuperMercado getByNome(String nome){
        List<SuperMercado> list = SugarRecord.find(SuperMercado.class, " nome = ? ", nome);
        if(!list.isEmpty()){
            return list.get(0);
        }
        return null;
        //return  SugarRecord.find(SuperMercado.class," nome = ? ",nome);
    }
}
