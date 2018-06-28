package controllers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

import models.CalcRequest;
import play.mvc.Controller;
import play.mvc.Result;

public class CalculationController extends Controller {

	public int calculate(String operation, int operand1, int operand2) {
		int result = 0;
		switch (operation) {
		case "+":
			result = operand1 + operand2;
			break;
		case "-":
			result = operand1 - operand2;
			break;
		case "*":
			result = operand1 * operand2;
			break;
		case ":":
			result = operand1 / operand2;
			break;
		default:
			break;
		}


		return result;
	}

	public Result receiver() throws IOException, ClassNotFoundException {
		System.out.println("Receiver Start");

		SocketChannel sChannel = SocketChannel.open();
		sChannel.configureBlocking(true);
		if (sChannel.connect(new InetSocketAddress("localhost", 9001))) {

			ObjectInputStream ois = new ObjectInputStream(sChannel.socket().getInputStream());
			CalcRequest receivedObject = (CalcRequest) ois.readObject();
			int operand1 = Integer.parseInt(receivedObject.getOperand1());
			int operand2 = Integer.parseInt(receivedObject.getOperand2());
			String operation = receivedObject.getOperation();
			
			ObjectOutputStream oos = new ObjectOutputStream(sChannel.socket().getOutputStream());
			oos.writeObject(calculate(operation, operand1, operand2));
			oos.close();
		}

		System.out.println("End Receiver");
		return ok("ha").as("text/html");
	}

}
