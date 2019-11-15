package sv.edu.udb.www.entities;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;


public class Horarios {

     /**
     * @return the Idioma
     */
    public String getIdioma() {
        return Idioma;
    }

    /**
     * @param Idioma the Idioma to set
     */
    public void setIdioma(String Idioma) {
        this.Idioma = Idioma;
    }
    /**
     * @return the funcion3d
     */
    public boolean isFuncion3d() {
        return funcion3d;
    }

    /**
     * @param funcion3d the funcion3d to set
     */
    public void setFuncion3d(boolean funcion3d) {
        this.funcion3d = funcion3d;
    }

    /**
     * @return the horaInicio
     */
    public Date getHoraInicio() {
        return horaInicio;
    }

    /**
     * @param horaInicio the horaInicio to set
     */
    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    /**
     * @return the horaFinal
     */
    public Date getHoraFinal() {
        return horaFinal;
    }

    /**
     * @param horaFinal the horaFinal to set
     */
    public void setHoraFinal(Date horaFinal) {
        this.horaFinal = horaFinal;
    }
    
    
    private String Idioma;
    private boolean funcion3d;
    private Date horaInicio;
    private Date horaFinal;
    private String codigo;
    
      @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Horarios)) {
            return false;
        }
        return true;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
