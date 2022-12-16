package com.ejer.pcip6spy;

import java.util.Formatter;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

/**
 * Clase encargada del formato de mensajes de P6SPY
 */
public class PCIP6SpyMessageFormat implements MessageFormattingStrategy {

	@Override
	public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
		StringBuilder sb = new StringBuilder();
		Formatter fmt = new Formatter(sb);
		
		//
		fmt.format("%,1d", elapsed);
		fmt.format(" | %-10s", category);
		fmt.format(" | %s", sql);
		
		// Ejemplo de como se emplearía el formateador de SQL de Hibernate
		//fmt.format("|%s", new BasicFormatterImpl().format(sql));
		
		//Cerramos el formateador
		fmt.close();
		
		return sb.toString();
	}
}
