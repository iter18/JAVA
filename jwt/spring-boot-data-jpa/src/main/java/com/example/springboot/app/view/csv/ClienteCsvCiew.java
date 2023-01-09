package com.example.springboot.app.view.csv;

import com.example.springboot.app.models.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component("listar")
public class ClienteCsvCiew extends AbstractView {
    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }

    public ClienteCsvCiew() {
        setContentType("text/csv");
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
                                           HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        //Asignar nombre al archivo
        response.setHeader("Content-Disposition","attachment; filename=\"clientes.csv\"");
        response.setContentType(getContentType());

        Page<Client> clientes = (Page<Client>) model.get("clientes");

        ICsvBeanWriter beanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        //Para convertir un entity a un archivo plano es necesario escribir los nombres como se tienen en el entity
        String[] header = {"id","nombre","apellido","email","createAt"};
        beanWriter.writeHeader(header);

        for(Client cliente : clientes){
            beanWriter.write(cliente,header);
        }
        beanWriter.close();
    }
}
