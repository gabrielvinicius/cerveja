package br.edu.ifba.gabrielvinicius.cerveja.Controller;

import com.orm.SugarRecord;

import java.util.List;

import br.edu.ifba.gabrielvinicius.cerveja.Model.Volume;

public class VolumeDAO {
    private static VolumeDAO instance;
    private VolumeDAO(){}
    public static VolumeDAO getInstance(){
        if(instance == null){
            instance = new VolumeDAO();
        }
        return instance;
    }
    public Volume insert (Volume target){
        return getById(target.save());
    }
    public Volume update(Volume target){
        return getById(target.update());
    }
    public boolean delete (Volume target){
        return target.delete();
    }
    public Volume getById(Long id){
        return SugarRecord.findById(Volume.class,id);
    }
    public List<Volume> listAll(){
        return SugarRecord.listAll(Volume.class);
    }
    public List<Volume> listLike(String nome){
        return SugarRecord.find(Volume.class," nome LIKE ? ",nome);
    }
    public Volume getByNome(String nome){
        List<Volume> list = SugarRecord.find(Volume.class, " nome = ? ", nome);
        if(!list.isEmpty()){
            return list.get(0);
        }
        return null;
        //return  SugarRecord.find(Volume.class," nome = ? ",nome);
    }

}
