package com.iter.springboot.apirest.service.impl;

import com.iter.springboot.apirest.dtos.*;
import com.iter.springboot.apirest.genericos.negocio.impl.AbstractQueryAvanzadoService;
import com.iter.springboot.apirest.mappers.AutorLibroMapper;
import com.iter.springboot.apirest.mappers.AutorMapper;
import com.iter.springboot.apirest.mappers.HistoricoProductoMapper;
import com.iter.springboot.apirest.mappers.LibroMapper;
import com.iter.springboot.apirest.modelo.*;
import com.iter.springboot.apirest.repository.LibroRepository;
import com.iter.springboot.apirest.repository.specification.LibroSpecification;
import com.iter.springboot.apirest.service.*;
import com.sun.istack.NotNull;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.persistence.Tuple;
import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
@Service
@Slf4j
@Transactional(readOnly = true)
public class LibroServiceImpl extends AbstractQueryAvanzadoService<Libro,Long> implements LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private UploadFileService uploadFileService;

    @Autowired
    private AutorService autorService;

    @Autowired
    private LibroMapper libroMapper;

    @Autowired
    private AutorMapper autorMapper;

    @Autowired
    private AutorLibroService autorLibroService;

    @Autowired
    private AutorLibroMapper autorLibroMapper;

    @Autowired
    private HistoricoProductoMapper historicoProductoMapper;

    @Autowired
    private HistoricoLibroService historicoLibroService;

    @Override
    public JpaSpecificationExecutor<Libro> getJpaSpecificationExecutor() {
        return libroRepository;
    }

    @Override
    public JpaRepository<Libro, Long> getJpaRepository() {
        return libroRepository;
    }

    @Override
    public Sort getOrdenamiento() {
        return Sort.by(Libro_.titulo.getName()).ascending();
    }

    @Override
    @Transactional
    public AutorLibroDto alta(LibroDto libroDto, MultipartFile imagen) {
        Assert.hasText(libroDto.getIsbn(),"Campo ISBN es requerido");
        Assert.hasText(libroDto.getTitulo(),"Campo TITULO es requerido");
        Assert.hasText(libroDto.getCategoria(),"Campo ISBN es requerido");
        Assert.hasText(libroDto.getAutor(),"Campo Autor es requerido");

        Specification<Libro> filtro = LibroSpecification.isbn(libroDto.getIsbn());
        Optional<Libro> existeIsbn = libroRepository.findOne(filtro);

        if(existeIsbn.isPresent()){
            throw new IllegalArgumentException("Este Isbn se encuentra registrado");
        }

        Autor autor = autorMapper.toEntity(autorService.buscar(Long.parseLong(libroDto.getAutor())));

        String rutaImagen = uploadFileService.copy(imagen);

        if(rutaImagen == null){
            throw new IllegalArgumentException("La imagen no se subio al directorio");
        }


        Libro libro = libroMapper.toEntity(libroDto);
        libro.setRutaFoto(rutaImagen);
        libro.setFechaRegistro(new Date());

        AutorLibro autorLibro = AutorLibro.builder()
                .autor(autor)
                .libro(libro)
                .build();
       AutorLibroDto autorLibroDto = autorLibroMapper.toDto(autorLibroService.alta(autorLibro));

        autorLibroDto.setLibro(libroMapper.toDto(libroRepository.save(libro)));
        return autorLibroDto;
    }

    @Override
    public List<AutorLibroDto> buscar(String isbn, String titulo, Long autorCode) {

        List<AutorLibro> lista = autorLibroService.buscar(isbn,titulo,autorCode);
        log.info(lista.toString());
        List<AutorLibroDto> autorLibroDto = autorLibroMapper.toListDto(autorLibroService.buscar(isbn, titulo, autorCode));

        return autorLibroDto;
    }

    @Override
    @Transactional
    public AutorLibroDto modificar(Long id, LibroDto libroDto, MultipartFile imagen) {
        Assert.hasText(libroDto.getIsbn(),"Campo ISBN es requerido");
        Assert.hasText(libroDto.getTitulo(),"Campo TITULO es requerido");
        Assert.hasText(libroDto.getCategoria(),"Campo ISBN es requerido");
        Assert.hasText(libroDto.getAutor(),"Campo Autor es requerido");

        Autor autor = autorMapper.toEntity(autorService.buscar(Long.parseLong(libroDto.getAutor())));
        AutorLibro autorLibro = autorLibroService.buscarPorId(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el registro a modificar"));
        Libro libro = libroRepository.findById(Long.parseLong(libroDto.getIdLibro()))
                .orElseThrow(() -> new EntityNotFoundException("El registro del libro no fue encontrado"));

        //Validación para que no se duplique el ISBN
        Specification<Libro> filtro = LibroSpecification.idNotEqual(libro.getId())
                .and(LibroSpecification.isbn(libroDto.getIsbn()));
        Optional<Libro> isbnDuplicado = libroRepository.findOne(filtro);
        if(isbnDuplicado.isPresent()){
            throw new IllegalArgumentException("El ISBN ya existe");
        }
       /* if(libro.getTitulo().equalsIgnoreCase(libroDto.getTitulo())){
            throw new IllegalArgumentException("El Titulo del libro ya existe");
        }*/
        //Eliminamos imagen anterior
        log.info("Nombre imagen"+ imagen.getOriginalFilename());
        if(!imagen.getOriginalFilename().equals("") ||
                !imagen.getOriginalFilename().isEmpty()||
                !imagen.getOriginalFilename().isBlank()){
            boolean eliminoFoto = uploadFileService.delete(libro.getRutaFoto());
            if (!eliminoFoto){
                throw new IllegalArgumentException("La imagen anterior no fue eliminada");
            }
            String rutaImagen = uploadFileService.copy(imagen);
            if(rutaImagen == null){
                throw new IllegalArgumentException("La imagen no se subio al directorio");
            }
            libro.setRutaFoto(rutaImagen);
        }

        libro.setIsbn(libroDto.getIsbn());
        libro.setTitulo(libroDto.getTitulo());
        libro.setEditorial(libroDto.getEditorial());
        libro.setCategoria(libroDto.getCategoria());
        libro.setFechaRegistro(new Date());

        autorLibro.setAutor(autor);
        autorLibro.setLibro(libro);
        return  autorLibroMapper.toDto(autorLibroService.modificar(autorLibro));

    }

    @Override
    public List<ComboDto> buscarC() {
        List<Tuple> registros = libroRepository.buscarC();

        return registros.stream()
                .map(reg -> new ComboDto(Integer.parseInt(reg.get("code").toString()),reg.get("valor").toString()))
                .collect(Collectors.toList());
    }

    @Override
    public AutorLibroDto buscar(Long id) {
        return autorLibroMapper.toDto(autorLibroService.buscarByIdLibro(id));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {

        AutorLibro autorLibro = autorLibroService.buscarPorId(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el registro a eliminar"));

        boolean eliminoFoto = uploadFileService.delete(autorLibro.getLibro().getRutaFoto());
        if (!eliminoFoto){
            throw new IllegalArgumentException("La imagen del libro no fue eliminada");
        }
           autorLibroService.eliminar(autorLibro);
           libroRepository.delete(autorLibro.getLibro());

    }

    @Override
    public List<HistoricoProductoDto> consulta(Long id) {

        List<HistoricoLibro> historicoLibroList = historicoLibroService.buscar(id);

        return historicoProductoMapper.toListDto(historicoLibroList);
    }

    public Map<String, Object> getReportParams(Long libroId) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<DtoReporte> historicoLibroList = consulta(libroId).stream()
                .map(historicoProductoDto -> {
                    DtoReporte dtoReporte = new DtoReporte();
                    dtoReporte.setCantidad(historicoProductoDto.getCantidad());
                    dtoReporte.setMinimo(historicoProductoDto.getMinimo());
                    dtoReporte.setPrecioCompra(historicoProductoDto.getPrecioCompra());
                    dtoReporte.setPrecioVenta(historicoProductoDto.getPrecioVenta());
                    dtoReporte.setMovimiento( historicoProductoDto.getMovimiento());

                    return dtoReporte;
                })
                .collect(Collectors.toList());

        DtoReporte[] DtoReporte = historicoLibroList.toArray(new DtoReporte[0]);

        Map<String, Object> params = new HashMap<>();
        params.put("ds", new JRBeanArrayDataSource(DtoReporte));
        params.put("libroId", libroId);

        return params;
    }

    @Override
    @NotNull
    public ResponseEntity<Resource> exportInvoice(Long id) {
        final HashMap<String, Object> parameters = new HashMap<>();
        List<DtoReporte> historicoLibroList = consulta(id).stream()
                .map(historicoProductoDto -> {
                    DtoReporte dtoReporte = new DtoReporte();
                    dtoReporte.setCantidad(historicoProductoDto.getCantidad());
                    dtoReporte.setMinimo(historicoProductoDto.getMinimo());
                    dtoReporte.setLibroDto(historicoProductoDto.getLibro());
                    dtoReporte.setPrecioCompra(historicoProductoDto.getPrecioCompra());
                    dtoReporte.setPrecioVenta(historicoProductoDto.getPrecioVenta());
                    dtoReporte.setMovimiento( historicoProductoDto.getMovimiento());
                    return dtoReporte;
                })
                .collect(Collectors.toList());
        try {

            final File file = ResourceUtils.getFile("classpath:reportes/Historico_Producto.jasper");
             File imgLogo = ResourceUtils.getFile("C:/Spring5/frontend/angular/angular/clientes-app/src/assets/uplodas/80bd547e-4665-498d-99c7-975b93cd67d2_bajoEstrella.jpg");
            final JasperReport report = (JasperReport) JRLoader.loadObject(file);
            parameters.put("ds", new JRBeanCollectionDataSource(historicoLibroList));
            parameters.put("productoNombre",historicoLibroList.get(0).getLibroDto().getTitulo());

            parameters.put("imgProducto", new FileInputStream(imgLogo));

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
            byte[] reporte = JasperExportManager.exportReportToPdf(jasperPrint);

            String sdf = (new SimpleDateFormat("dd/MM/yyyy")).format(new Date());
            StringBuilder stringBuilder = new StringBuilder().append("InvoicePDF:");
            ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                    .filename(stringBuilder.append(id)
                            .append("generateDate:")
                            .append(sdf)
                            .append(".pdf")
                            .toString())
                    .build();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(contentDisposition);
            return ResponseEntity.ok().contentLength((long) reporte.length)
                    .contentType(MediaType.APPLICATION_PDF)
                    .headers(headers).body(new ByteArrayResource(reporte));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
