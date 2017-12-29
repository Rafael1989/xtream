package br.com.caelum.xstream;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.SingleValueConverter;

public class PrecoSimplesConverter implements SingleValueConverter{

	@Override
	public boolean canConvert(Class type) {
		return type.isAssignableFrom(Double.class);
	}

	@Override
	public Object fromString(String valor) {
		try {
			return getFormatador().parse(valor);
		} catch (ParseException e) {
			throw new ConversionException(e);
		}
	}

	@Override
	public String toString(Object valor) {
		return getFormatador().format((Double)valor);
	}
	
	private NumberFormat getFormatador() {
		Locale brasil = new Locale("pt", "br");
		return NumberFormat.getCurrencyInstance(brasil);
	}

}
