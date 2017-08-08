package stock;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

public class Main {

	public static void main(String[] args) {
		Currency currency = new Currency();
		try {
			URL url = new URL("http://api.fixer.io/latest?symbols=USD,GBP,SEK");
			// http://api.fixer.io/latest?base=USD
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type","application/json");
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String result;
			StringBuffer content = new StringBuffer();
			Gson gson = new Gson();
			while ((result = reader.readLine()) != null) {
				content.append(result);
			}
			conn.disconnect();
			currency = gson.fromJson(content.toString(), Currency.class);
			
			System.out.println("Result: " + content.toString());
			System.out.println("Currency name: " + currency.base + " date: " + currency.date);
			System.out.println(currency.base + " rates:");
			currency.rates.forEach((rn, rv) -> System.out.println(rn + " rate:" + rv));
			
		} catch (Exception e)  {
			System.out.println("Error: " + e);
		}

	}

}
