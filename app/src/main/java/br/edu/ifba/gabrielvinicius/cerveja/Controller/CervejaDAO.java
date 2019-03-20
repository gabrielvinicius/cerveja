package br.edu.ifba.gabrielvinicius.cerveja.Controller;

import com.orm.SugarRecord;

import java.util.List;

import br.edu.ifba.gabrielvinicius.cerveja.Model.Cerveja;
import br.edu.ifba.gabrielvinicius.cerveja.Model.Marca;

public class CervejaDAO {
    private static CervejaDAO instance;
    private CervejaDAO(){}
    public static CervejaDAO getInstance(){
        if(instance == null){
            instance = new CervejaDAO();
        }
        return instance;
    }
    public Cerveja insert (Cerveja target){

       long id = SugarRecord.save(target);
        return getById(id);

    }
    public Cerveja update(Cerveja target){
        return getById(SugarRecord.update(target));
    }
    public boolean delete (Cerveja target){
        return SugarRecord.delete(target);
    }
    public Cerveja getById(Long id){
        return SugarRecord.findById(Cerveja.class,id);
    }
    public List<Cerveja> listPorMarca(Marca marca){
        return SugarRecord.find(Cerveja.class, " marca = ?",String.valueOf(marca.getId()));

    }
    public List<Cerveja> listAll(){
        return SugarRecord.listAll(Cerveja.class);
    }
    public List<Cerveja> listLike(String nome){
        return SugarRecord.find(Cerveja.class," nome LIKE ? ",nome);
    }
    public Cerveja getByNome(String nome, Marca marca){
        List<Cerveja> list = SugarRecord.find(Cerveja.class, " nome = ? and marca = ?", nome, String.valueOf(marca.getId()));
        if(!list.isEmpty()){
            return list.get(0);
        }
        return null;
        //return  SugarRecord.find(Marca.class," nome = ? ",nome);
    }
}
