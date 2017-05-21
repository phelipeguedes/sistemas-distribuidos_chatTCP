package br.com.sd.main;

import java.io.IOException;

import br.com.sd.models.Cliente;

public class Principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		
		Cliente cliente;
		try {
			cliente = new Cliente();
			Cliente Phelipe = new Cliente();
			Cliente garota = new Cliente();
			cliente.conectar();
			cliente.receberMensagem();
			
			Phelipe.conectar();
			Phelipe.receberMensagem();
			
			garota.conectar();
			garota.receberMensagem();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
