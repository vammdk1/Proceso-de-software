package es.deusto.spq.server.logic;

import java.util.ArrayList;

import es.deusto.spq.server.data.Room;
import es.deusto.spq.server.jdo.User;

public class RoomManager{
   static ArrayList<Room> salasActivas = new ArrayList<>();
   static ArrayList<User> usuariosConectados = new ArrayList<>();

   
   public static ArrayList<Room> getSalasActivas() {
	   return salasActivas;
   }
   
   public static ArrayList<User> getUsuariosConectados() {
		return usuariosConectados;
   }

   public void CreaSala(String nombreSala,User usuario){
	    salasActivas.add(new Room(usuario,nombreSala));
   }
	
   //TODO crear las slas con token o con usuario
   public void AnadirUsuarioSala(User usuario,String nombreSala ){
    if(salasActivas.contains(nombreSala)){
        salasActivas.get(salasActivas.indexOf(nombreSala)).joinUser(usuario);
    }else{
        System.out.println("La sala no existe");
    }
   }

   public void BorrarSala(String nombreSala){
        if(salasActivas.contains(nombreSala)){
        	salasActivas.remove(salasActivas.get((salasActivas.indexOf(nombreSala))));
        }else{
            System.out.println("La sala no existe");
        }
   }
   
   public void EliminarUsuarioSala(User usuario,String nombreSala ){
	    if(salasActivas.contains(nombreSala)){
	        salasActivas.get(salasActivas.indexOf(nombreSala)).deleteUser(usuario);;
	    }else{
	        System.out.println("La usuario no esta en esta sala");
	    }
   }

}