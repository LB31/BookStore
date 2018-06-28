package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

import javax.inject.Inject;

import models.CalcRequest;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.*;

public class ClientController extends Controller {
	@Inject
	FormFactory formFactory;

	public Result calculate() {

		return ok(calculator.render());
	}

	public Result askCalcServer() throws IOException {
		DynamicForm dynamicForm = formFactory.form().bindFromRequest();
		String operand1 = dynamicForm.get("operand1");
		String operand2 = dynamicForm.get("operand2");
		String operation = dynamicForm.get("operation");

		StringBuilder result = new StringBuilder();
		String path = "http://localhost:8080/calculate/" + operation + "/" + operand1 + "/" + operand2;
		System.out.println(path);
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		rd.close();
		System.out.println(conn.getResponseMessage());
		return ok(result.toString()).as("text/html");
	}

	public Result sendSerializableObject() throws IOException, ClassNotFoundException {

		DynamicForm dynamicForm = formFactory.form().bindFromRequest();
		String operand1 = dynamicForm.get("operand1");
		String operand2 = dynamicForm.get("operand2");
		String operation = dynamicForm.get("operation");
		CalcRequest request = new CalcRequest(operand1, operand2, operation);

		System.out.println("Sender Start");
		ServerSocketChannel ssChannel = ServerSocketChannel.open();
		ssChannel.configureBlocking(true);
		int port = 9001;
		ssChannel.socket().bind(new InetSocketAddress(port));
		String obj = "testtext";
		while (true) {
			SocketChannel sChannel = ssChannel.accept();
			ObjectOutputStream oos = new ObjectOutputStream(sChannel.socket().getOutputStream());
			oos.writeObject(request);
			
			ObjectInputStream ois = new ObjectInputStream(sChannel.socket().getInputStream());
			Integer answer = (Integer) ois.readObject();
			oos.close();
			System.out.println(answer);
			System.out.println("Connection ended");
			ssChannel.close();
			return ok("The result of " + operand1 + operation + operand2 + " equals " + answer).as("text/html");

		}

	}

}
