package com.ejer.pcip6spy;

import java.util.Formatter;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

public class PCIP6SpyMessageFormat implements MessageFormattingStrategy {

	@Override
	public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared,
			String sql, String url) {
		StringBuilder sb = new StringBuilder();

		Formatter fmt = new Formatter(sb);
		
		// mostrar� el tiempo en la precisi�n que lo env�e P6LogQuery.doLog (a d�a 15/12/2018 la tenemos sobrescrita para que la pase en microsegundos)
		fmt.format("%,12d", elapsed);
		fmt.format(" | %-10s", category);
		fmt.format(" | %s", sql);
		
		// Ejemplo de como se emplear�a el formateador de SQL de Hibernate
		//fmt.format("|%s", new BasicFormatterImpl().format(sql));
		
		// cerramos el formateador
		fmt.close();
		
		return sb.toString();
	}
}
