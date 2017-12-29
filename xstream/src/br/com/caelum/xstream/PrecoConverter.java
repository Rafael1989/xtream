package br.com.caelum.xstream;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class PrecoConverter implements Converter {

	@Override
	public boolean canConvert(Class type) {
		return type.isAssignableFrom(Double.class);
	}

	@Override
	public void marshal(Object object, HierarchicalStreamWriter writer, MarshallingContext context) {
		Double valor = (Double) object;
		String valorEmReais = getFormatador().format(valor);
		writer.setValue(valorEmReais);
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		String valor = reader.getValue();
		try {
			getFormatador().parse(valor);
		} catch (ParseException e) {
			throw new ConversionException(e);
		}
		return null;
	}

	private NumberFormat getFormatador() {
		Locale brasil = new Locale("pt", "br");
		NumberFormat formatador = NumberFormat.getCurrencyInstance(brasil);
		return formatador;
	}
	
}
