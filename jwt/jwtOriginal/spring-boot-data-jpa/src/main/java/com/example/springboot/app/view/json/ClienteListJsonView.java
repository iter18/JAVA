package com.example.springboot.app.view.json;

import com.example.springboot.app.models.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Map;

@Component("listar.json")
public class ClienteListJsonView extends MappingJackson2JsonView {

    @Override
    protected Object filterModel(Map<String, Object> model) {
        model.remove("titulo");
        model.remove("page");
        Page<Client> clientes = (Page<Client>) model.get("clientes");
        model.remove("clientes");
        model.put("clientes",clientes.getContent());

        return super.filterModel(model);
    }
}
