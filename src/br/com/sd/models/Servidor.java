package br.com.sd.models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Servidor extends Thread{
	
	private static ArrayList<BufferedWriter>cliente;
	private static ServerSocket servidor;
	private String seuNome;
	private Socket conexao;
	private InputStream is;
	private InputStreamReader isr;
	private BufferedReader bfr;
	
	public Servidor(Socket conexao){
		this.conexao = conexao;
	
	 try {
		 is = conexao.getInputStream();
		 isr = new InputStreamReader(is);
		 bfr = new BufferedReader(isr);
	 } catch(IOException e) {
		 e.printStackTrace();
	 }
	
	}
	
	public void executar(){
		try {
			
			String mensagem;
			OutputStream out = this.conexao.getOutputStream();
			Writer writer = new OutputStreamWriter(out);
			BufferedWriter bfw = new BufferedWriter(writer);
			cliente.add(bfw);
			seuNome = mensagem = bfr.readLine();
			
			while (!"Fechar".equalsIgnoreCase(mensagem) && mensagem != null){
				mensagem = bfr.readLine();
				enviaPraTodos(bfw, mensagem);
				System.out.println(mensagem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		
		try {
					
				JLabel labelMensagem = new JLabel("Porta do servidor: ");
				JTextField numeroDaPorta = new JTextField("12345");
				Object[] textos = {labelMensagem, numeroDaPorta};
				JOptionPane.showMessageDialog(null, textos);
				servidor = new ServerSocket(Integer.parseInt(numeroDaPorta.getText()));
				cliente = new ArrayList<BufferedWriter>();
				JOptionPane.showMessageDialog(null, "Servidor funcionando na porta: " + numeroDaPorta.getText());
					
				while(true){
					System.out.println("Carregando...");
					Socket conexao = servidor.accept();
					System.out.println("Conectado com sucesso.");
					Thread t = new Servidor(conexao);
					t.start();
				}
			} catch (Exception e) {
					e.getMessage();
			}
	}
	
	public void enviaPraTodos(BufferedWriter saida, String mensagemEnviada)throws IOException{
		BufferedWriter bufferedWriter;
		
		for (BufferedWriter bfw : cliente) {
			bufferedWriter = (BufferedWriter)bfw;
			if(!(saida == bufferedWriter)){
				bfw.write(seuNome + " - " + mensagemEnviada + "\r\n");
				bfw.flush();
			}
		}
	}
	
}
