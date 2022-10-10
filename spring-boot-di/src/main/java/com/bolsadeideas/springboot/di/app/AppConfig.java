package com.bolsadeideas.springboot.di.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.bolsadeideas.springboot.di.app.models.domain.ItemFactura;
import com.bolsadeideas.springboot.di.app.models.domain.Producto;
import com.bolsadeideas.springboot.di.app.models.service.IServicio;
import com.bolsadeideas.springboot.di.app.models.service.MiServicio;
import com.bolsadeideas.springboot.di.app.models.service.MiServicioComplejo;
 
/**
 * 
 * @author Home B
 *Archivo de configuración se pueden definir los componentes a utilizar dentro del proyecto tambien, pero se 
 *usa más para cargar clases externas, API, clases de terceros
 */
@Configuration
public class AppConfig {
	

	@Bean("miServicioSimple")
	@Primary
	public IServicio registrarMiServicio() {
		return new MiServicio();
	}
	
	@Bean("miServicioComplejo")
	public IServicio registrarMiServicioComplejo() {
		return new MiServicioComplejo();
	}
	
	@Bean("itemsFactura")
	public List<ItemFactura> registrarItems(){
			Producto producto1 = new Producto("Camara Sony",100);
			Producto producto2 = new Producto("Bicicleta Venotto",300);
			
			ItemFactura item1 = new ItemFactura(producto1, 2);
			ItemFactura item2 = new ItemFactura(producto2, 3);
		
			return Arrays.asList(item1, item2);
	}
	
	@Bean("itemsFacturaOficina")
	@Primary
	public List<ItemFactura> registrarItemsOficina(){
			Producto producto1 = new Producto("Monitor",100);
			Producto producto2 = new Producto("CPU",300);
			Producto producto3 = new Producto("Hojas",200);
			Producto producto4 = new Producto("USB",100);
			
			ItemFactura item1 = new ItemFactura(producto1, 2);
			ItemFactura item2 = new ItemFactura(producto2, 3);
			ItemFactura item3 = new ItemFactura(producto3, 4);
			ItemFactura item4 = new ItemFactura(producto4, 3);
		
			return Arrays.asList(item1, item2, item3, item4);
	}

}
