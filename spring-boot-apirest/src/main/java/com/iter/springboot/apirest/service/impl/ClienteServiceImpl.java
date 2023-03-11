package com.iter.springboot.apirest.service.impl;

import antlr.StringUtils;
import com.iter.springboot.apirest.dtos.ClienteDto;
import com.iter.springboot.apirest.mappers.ClienteMapper;
import com.iter.springboot.apirest.modelo.Cliente;
import com.iter.springboot.apirest.repository.ClienteRepository;
import com.iter.springboot.apirest.service.ClienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@Slf4j
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    ClienteMapper clienteMapper;

    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public List<ClienteDto> buscar() {


        List<Cliente> clienteList = clienteRepository.findAll();
        /*Forma de llenar un DTO de forma Artesanal o manual y crear lista */
       /* List<ClienteDto> listaClienteDto = clienteList.stream().map(cliente -> {
            ClienteDto clienteDto = ClienteDto.builder()
                    .id(cliente.getId())
                    .nombre(cliente.getNombre())
                    .apellido(cliente.getApellido())
                    .email(cliente.getEmail())
                    .fechaCreacion(cliente.getCreateAt())
                    .build();
            return clienteDto;
        }).collect(Collectors.toList());*/

        //Forma de hacerlo con dataMapper -> Mapstruct
        return clienteMapper.tolistDto(clienteList);
    }

    @Override
    public ClienteDto buscar(Long id) {

        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("El registro no existe, verifique e intente nuevamente"));
        return clienteMapper.toDto(cliente);
    }

    @Override
    @Transactional
    public ClienteDto alta(ClienteDto clienteDto) {
       /* Cliente clienteA = Cliente.builder()
                .nombre(cliente.getNombre())
                .email(cliente.getEmail())
                .apellido(cliente.getApellido())
                .createAt(new Date())
                .build();*/
        Cliente cliente = clienteMapper.toEntity(clienteDto);
        cliente.setCreateAt(new Date());
        cliente = clienteRepository.save(cliente);

        return clienteMapper.toDto(cliente);
    }

    @Override
    @Transactional
    public Cliente altaHM(HashMap<String, ?> cliente) {

        Cliente clienteA = Cliente.builder()
                .nombre((String) cliente.get("nombre"))
                .email((String) cliente.get("email"))
                .apellido((String)cliente.get("apellido"))
                .createAt(new Date())
                .build();
        return clienteRepository.save(clienteA);
    }

    @Override
    @Transactional
    public ClienteDto modificar(ClienteDto cliente, Long id) {

        Cliente cli = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("EL registro no existe, verifique e intente nuevamente"));

            cli.setApellido(cliente.getApellido());
            cli.setNombre(cliente.getNombre());
            cli.setEmail(cliente.getEmail());
          Cliente client = clienteRepository.saveAndFlush(cli);
          //Forma manual de convertir un entity a DTO
            /*ClienteDto clienteDto = ClienteDto.builder()
                    .id(client.getId())
                    .nombre(client.getNombre())
                    .apellido(client.getApellido())
                    .email(client.getEmail())
                    .fechaCreacion(client.getCreateAt())
                    .build();*/

            //Forma de hacerlo automatico con Mapstruct
            ClienteDto clienteDto = clienteMapper.toDto(client);
            return clienteDto;


    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        clienteRepository.deleteById(id);
    }
}
