package br.com.sd.models;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Cliente extends JFrame implements ActionListener, KeyListener{
	
	private static final long serialVersionUID = 1L;
	
	private JTextField textoDaMensagem;
	private JTextArea texto;
	private JButton botaoEnviar;
	private JButton botaoSair;
	private JLabel historico;
	private JLabel mensagem;
	private JPanel conteudoPainel;
	private Socket socket;
	private OutputStream ops;
	private Writer writer;
	private BufferedWriter bfw;
	private JTextField numeroIp;
	private JTextField numeroPorta;
	private JTextField nome;
	
	public Cliente() throws IOException{
		JLabel labelMensagem = new JLabel("Checar");
		numeroIp = new JTextField("127.0.1");
		numeroPorta = new JTextField("12345");
		nome = new JTextField("Cliente");
		Object[] atributos = {labelMensagem, numeroIp, numeroPorta, nome};
		JOptionPane.showMessageDialog(null, atributos);
		conteudoPainel = new JPanel();
		texto = new JTextArea(10, 20);
		texto.setEditable(false);
		texto.setBackground(new Color(240, 240, 240));
		textoDaMensagem = new JTextField(20);
		historico = new JLabel("Histórico");
		labelMensagem = new JLabel("Mensagem");
		botaoEnviar = new JButton("Enviar");
		botaoEnviar.setToolTipText("Enviar Mensagem");
		botaoSair = new JButton("Sair");
		botaoSair.setToolTipText("Encerrar Sessão");
		botaoEnviar.addActionListener(this);
		botaoSair.addActionListener(this);
		botaoEnviar.addKeyListener(this);
		textoDaMensagem.addKeyListener(this);
		JScrollPane rolar = new JScrollPane(texto);
		texto.setLineWrap(true);
		conteudoPainel.add(historico);
		conteudoPainel.add(rolar);
		conteudoPainel.add(labelMensagem);
		conteudoPainel.add(textoDaMensagem);
		conteudoPainel.add(botaoSair);
		conteudoPainel.add(botaoEnviar);
		conteudoPainel.setBackground(Color.WHITE);
		texto.setBorder(BorderFactory.createEtchedBorder(Color.gray, Color.BLUE));
		textoDaMensagem.setBorder(BorderFactory.createEtchedBorder(Color.BLUE, Color.BLUE));
		setTitle(nome.getText());
		setContentPane(conteudoPainel);
		setLocationRelativeTo(null);
		setResizable(false);
		setSize(250, 300);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void conectar() throws IOException{
		
		socket = new Socket(numeroIp.getText(), Integer.parseInt(numeroPorta.getText()));
		ops = socket.getOutputStream();
		writer = new OutputStreamWriter(ops);
		bfw = new BufferedWriter(writer);
		bfw.write(nome.getText()+ "\r\n");
		bfw.flush();	
	}
	
	public void enviarMensagem(String mensagemDeTexto) throws IOException{
		
		if(mensagemDeTexto.equalsIgnoreCase("sair")){
			bfw.write("Desconectado! \r\n");
			texto.append(nome.getText() + " está dizendo: " + mensagem.getText()+ "\r\n");
		}
		bfw.flush();
		mensagem.setText("");
	}
	
	public void receberMensagem() throws IOException{
		
		InputStream in = socket.getInputStream();
		InputStreamReader inr = new InputStreamReader(in);
		BufferedReader bfr = new BufferedReader(inr);
		String mensagem = "";
		
		while("sair".equalsIgnoreCase(mensagem) == false){
			
			if(bfr.ready()){
				mensagem = bfr.readLine();
				if(mensagem.equalsIgnoreCase("sair")){
					texto.append("Falha no servidor. \r\n");
				} else {
					texto.append(mensagem+"\r\n");
				}
			}
		}
	}
	
	public void sair() throws IOException{
		enviarMensagem("Sair");
		bfw.close();
		ops.close();
		socket.close();
	}
	
	public void clicar(ActionEvent e){
		
		try {
			if(e.getActionCommand().equals(botaoEnviar.getActionCommand())){
				enviarMensagem(mensagem.getText());
			}else{
				if(e.getActionCommand().equals(botaoSair.getActionCommand()))
				sair();
			}	
		} catch (Exception e2) {
			// TODO: handle exception
			e2.getMessage();
		}
	}
	
	public void pressionarEnter(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			try {
				enviarMensagem(mensagem.getText());
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
