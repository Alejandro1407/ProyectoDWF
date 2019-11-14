/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.udb.www.managedbeans;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.commons.codec.binary.Base64;
import sun.misc.IOUtils;
import sv.edu.udb.www.entities.Genero;
import sv.edu.udb.www.entities.Idioma;



import sv.edu.udb.www.facades.PeliculaFacade;
import sv.edu.udb.www.entities.Pelicula;
import sv.edu.udb.www.entities.Produccion;
import sv.edu.udb.www.facades.ClasificacionFacade;
import sv.edu.udb.www.facades.GeneroFacade;
import sv.edu.udb.www.facades.IdiomaFacade;
import sv.edu.udb.www.facades.ProduccionFacade;


@ManagedBean
@ViewScoped
public class PeliculasController implements Serializable {

    @EJB
    private PeliculaFacade peliculaFacade = new PeliculaFacade();
    @EJB
    private ClasificacionFacade clasificacionFacade = new ClasificacionFacade();
    @EJB
    private GeneroFacade generoFacade = new GeneroFacade();
    @EJB
    private IdiomaFacade idiomaFacade = new IdiomaFacade();
    @EJB
    private ProduccionFacade produccionFacade = new ProduccionFacade();
    
    private Pelicula pelicula;
    private int clasificacion;
    private Part fileUpload;
    private List<String> generos;
    private List<String> idiomas;
    private List<String> actores;
    private List<String> directores;
    
    Date date = new Date();
     
    public PeliculasController() {
    }
    @PostConstruct
    public void init(){
        pelicula = new Pelicula();
    }

    /**
     * Retorna el listado de peliculas
     *
     * @return lista
     */
    public List<Pelicula> getPeliculas() {
        List<Pelicula> lista = peliculaFacade.findAll();
        return lista;
    }

    public String displayImagen(byte[] data) throws IOException {

        String imageString = new String(Base64.encodeBase64(data));
        return imageString;
    }

    public String displaySubtitulos(short data) {
        String sub;
        if (data == 1) {
            sub = "Si";
        } else {
            sub = "No";
        }
        return sub;
    }

    /**
     * Método que permite insertar pelicula
     *
     * @return "index"
     * @throws java.io.IOException
     */
    public String insertarPelicula() throws IOException{
            byte[] bytes; 
        if(fileUpload != null){
        bytes = IOUtils.readFully(fileUpload.getInputStream(), -1, false);
        }
        else{
                BufferedImage bImage = ImageIO.read(getClass().getResource("/resources/default-image.jpg"));
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bImage, "jpg", bos );
                bytes = bos.toByteArray(); 
        }
        pelicula.setImagen(bytes);
        pelicula.setClasificacion(clasificacionFacade.find(clasificacion));
        List<Genero> gl = new ArrayList<>();
        generos.forEach((g) -> {
            gl.add(generoFacade.find(Integer.parseInt(g)));
        });
        pelicula.setGeneroList(gl);
        List<Idioma> il = new ArrayList<>();
        idiomas.forEach((i) -> {
            il.add(idiomaFacade.find(Integer.parseInt(i)));
        });
        pelicula.setIdiomaList(il);
        List<Produccion> dl = new ArrayList<>();
        directores.forEach((d) -> {
            dl.add(produccionFacade.find(Integer.parseInt(d)));
        });
        pelicula.setDirectoresList(dl);
        List<Produccion> al = new ArrayList<>();
        actores.forEach((a) -> {
            al.add(produccionFacade.find(Integer.parseInt(a)));
        });
        pelicula.setActoresList(al);
        peliculaFacade.create(getPelicula());
        return "index";
    }

    public void eliminarPelicula(int id){
        
        peliculaFacade.remove(peliculaFacade.find(id));
        
    }
     /**
     * Método que permite cargar la pelicula
     *
     * 
     */
    public void loadPelicula(){
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        int id;
        try{ 
            id= Integer.parseInt(request.getParameter("id"));
        }
        catch(Exception e){
            return ;
        }
        this.pelicula = peliculaFacade.find(id);
    }
    public List<Pelicula> obtenerPeliculas(){
        try{
            return peliculaFacade.findAll();
        }catch(Exception e){
            return null;
        }
    }
    
    /**
     * Método que permite modificar pelicula
     *
     * 
     * @return 
     * 
     */
    public String editarPelicula() throws IOException{
        if(fileUpload != null){
        byte[] bytes = IOUtils.readFully(fileUpload.getInputStream(), -1, false);
        pelicula.setImagen(bytes);
        }
        pelicula.setClasificacion(clasificacionFacade.find(clasificacion));
        List<Genero> gl = new ArrayList<>();
        generos.forEach((g) -> {
            gl.add(generoFacade.find(Integer.parseInt(g)));
        });
        if(!gl.isEmpty()){
        pelicula.setGeneroList(gl);
        }
        List<Idioma> il = new ArrayList<>();
        idiomas.forEach((i) -> {
            il.add(idiomaFacade.find(Integer.parseInt(i)));
        });
        if(!il.isEmpty()){
        pelicula.setIdiomaList(il);
        }
        List<Produccion> dl = new ArrayList<>();
        directores.forEach((d) -> {
            dl.add(produccionFacade.find(Integer.parseInt(d)));
        });
        if(!dl.isEmpty()){
        pelicula.setDirectoresList(dl);
        }
        List<Produccion> al = new ArrayList<>();
        actores.forEach((a) -> {
            al.add(produccionFacade.find(Integer.parseInt(a)));
        });
        if(!al.isEmpty()){
        pelicula.setActoresList(al);
        }
        peliculaFacade.edit(getPelicula());
        return "index";
    }

    /**
     * @return the pelicula
     */
    public Pelicula getPelicula() {
        return pelicula;
    }

    /**
     * @param pelicula the pelicula to set
     */
    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

     public Date getCurrentDate() {
        return date;
    }

    /**
     * @return the fileUpload
     */
    public Part getFileUpload() {
        return fileUpload;
    }

    /**
     * @param fileUpload the fileUpload to set
     */
    public void setFileUpload(Part fileUpload) {
        this.fileUpload = fileUpload;
    }

    /**
     * @return the clasificacion
     */
    public int getClasificacion() {
        return clasificacion;
    }

    /**
     * @param clasificacion the clasificiacion to set
     */
    public void setClasificacion(int clasificacion) {
        this.clasificacion = clasificacion;
    }

    /**
     * @return the generos
     */
    public List<String> getGeneros() {
        return generos;
    }

    /**
     * @param generos the generos to set
     */
    public void setGeneros(List<String> generos) {
        this.generos = generos;
    }

    /**
     * @return the idiomas
     */
    public List<String> getIdiomas() {
        return idiomas;
    }

    /**
     * @param idiomas the idiomas to set
     */
    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    /**
     * @return the actores
     */
    public List<String> getActores() {
        return actores;
    }

    /**
     * @param actores the actores to set
     */
    public void setActores(List<String> actores) {
        this.actores = actores;
    }

    /**
     * @return the directores
     */
    public List<String> getDirectores() {
        return directores;
    }

    /**
     * @param directores the directores to set
     */
    public void setDirectores(List<String> directores) {
        this.directores = directores;
    }

    
    

}
