package com.example.springboot.app.util.paginator;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

//se usan los genericos la T e es para especificar que será caulquier tipo de entidad
public class PageRender<T>{

    private String url;
    private Page<T> page;

    private int totoalPaginas;

    private int numElementosPorPagina;

    private int paginaActual;

    private List<PageItem> paginas;

    public String getUrl() {
        return url;
    }

    public int getTotoalPaginas() {
        return totoalPaginas;
    }

    public int getPaginaActual() {
        return paginaActual;
    }

    public List<PageItem> getPaginas() {
        return paginas;
    }

    public PageRender(String url, Page<T> page) {
        this.url = url;
        this.page = page;
        this.paginas = new ArrayList<PageItem>();

        numElementosPorPagina = page.getSize();
        totoalPaginas = page.getTotalPages();

        int desde,hasta;
        //Proceso para calcular rango de paginación en base a los registros a mostrar
        if(totoalPaginas<=numElementosPorPagina){
            desde = 1;
            hasta =totoalPaginas;
        }else {
            if(paginaActual<=numElementosPorPagina/2){
                desde =1;
                hasta = numElementosPorPagina;
            } else if (paginaActual>=totoalPaginas - numElementosPorPagina/2) {
                desde = totoalPaginas- numElementosPorPagina+1;
                hasta = numElementosPorPagina;
            }else {
                desde = paginaActual-numElementosPorPagina/2;
                hasta = numElementosPorPagina;
            }
        }
        //proceso para llenar las paginas con los rangos
        for (int i=0; i< hasta; i++){
            paginas.add(new PageItem(desde+i,paginaActual == desde+i));
        }
    }
    //Metodos para saber el estado de la paginación
    public boolean isFirst(){
        return page.isFirst();
    }
    public boolean isLast(){
        return page.isLast();
    }
    public boolean isHasNext(){
        return page.hasNext();
    }
    public boolean isHasPrevious(){
        return page.hasPrevious();
    }
}
