package com.example.springboot.app.view.xml;

import com.example.springboot.app.models.entity.Client;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "clientes")
public class ClienteList {
    @XmlElement(name = "cliente")
    public List<Client> clientes;

}
