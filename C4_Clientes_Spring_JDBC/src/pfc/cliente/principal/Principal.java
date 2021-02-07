package pfc.cliente.principal;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import pfc.cliente.dao.ClienteDAOInterface;
import pfc.cliente.dao.ClienteDAOSpringJdbc;
import pfc.cliente.dominio.Cliente;

public class Principal {

	public static void main(String[] args) {
	
		ApplicationContext contexto=new ClassPathXmlApplicationContext("applicationContext.xml");
		ClienteDAOInterface clientes= (ClienteDAOInterface) contexto.getBean("clienteDAO");
		
		
		Cliente cliente;



		Cliente cliente1 = new Cliente("juan", "pwjuan", "Juan Alcaraz");
		Cliente cliente2 = new Cliente("pedro", "pwpedro", "Pedro Zamora");
		Cliente cliente3 = new Cliente("luis", "pwluis", "Luis Valera");
		
		// Borramos todo, para no tener que ejecutar el script de la base cada vez
		for (Cliente c : clientes.consulta()) clientes.baja(c.getIdCliente());
		
		System.out.println();
		System.out.println("Alta de clientes en proceso");		
		clientes.alta(cliente1);
		clientes.alta(cliente2);
		clientes.alta(cliente3);
		
		System.out.println();
		System.out.println("Lista de clientes");
		for (Cliente c : clientes.consulta())
		System.out.println(c.getIdCliente() + "\t" + c.getPassword() + "\t\t" + c.getNombre());

		System.out.println();
		System.out.println("Baja de juan en proceso");	
		clientes.baja("juan");
		
		System.out.println();
		System.out.println("Lista de clientes");
		for (Cliente c : clientes.consulta()) 
		System.out.println(c.getIdCliente() + "\t" + c.getPassword() + "\t\t" + c.getNombre());
	
		System.out.println();
		System.out.println("Modificación de pedro en proceso");		
		cliente = clientes.consulta("pedro");
		cliente.setNombre("Pedro Zamóra Zamora");
		clientes.modificacion(cliente);
			
		System.out.println();
		System.out.println("Lista de clientes");
		for (Cliente c : clientes.consulta()) 
		System.out.println(c.getIdCliente() + "\t" + c.getPassword() + "\t\t" + c.getNombre());
		
		clientes.baja("pedro");
		clientes.baja("luis");
	}

}
