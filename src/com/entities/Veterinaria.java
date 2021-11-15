/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Equipo Malware
 */
@Entity
@Table(name = "veterinaria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Veterinaria.findAll", query = "SELECT v FROM Veterinaria v")
    , @NamedQuery(name = "Veterinaria.findByCodigo", query = "SELECT v FROM Veterinaria v WHERE v.codigo = :codigo")
    , @NamedQuery(name = "Veterinaria.findByDireccion", query = "SELECT v FROM Veterinaria v WHERE v.direccion = :direccion")
    , @NamedQuery(name = "Veterinaria.findByHorarioAtencion", query = "SELECT v FROM Veterinaria v WHERE v.horarioAtencion = :horarioAtencion")
    , @NamedQuery(name = "Veterinaria.findByNombre", query = "SELECT v FROM Veterinaria v WHERE v.nombre = :nombre")
    , @NamedQuery(name = "Veterinaria.findByPassword", query = "SELECT v FROM Veterinaria v WHERE v.password = :password")
    , @NamedQuery(name = "Veterinaria.findByNit", query = "SELECT v FROM Veterinaria v WHERE v.nit = :nit")})
public class Veterinaria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "direccion")
    private String direccion;
    @Basic(optional = false)
    @Column(name = "horario_atencion")
    private String horarioAtencion;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "nit")
    private String nit;
    @OneToMany(mappedBy = "veterinariaAsociadaCodigo")
    private List<Historial> historialList;

    public Veterinaria() {
    }

    public Veterinaria(Integer codigo) {
        this.codigo = codigo;
    }

    public Veterinaria(Integer codigo, String direccion, String horarioAtencion, String nombre, String password, String nit) {
        this.codigo = codigo;
        this.direccion = direccion;
        this.horarioAtencion = horarioAtencion;
        this.nombre = nombre;
        this.password = password;
        this.nit = nit;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getHorarioAtencion() {
        return horarioAtencion;
    }

    public void setHorarioAtencion(String horarioAtencion) {
        this.horarioAtencion = horarioAtencion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    @XmlTransient
    public List<Historial> getHistorialList() {
        return historialList;
    }

    public void setHistorialList(List<Historial> historialList) {
        this.historialList = historialList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Veterinaria)) {
            return false;
        }
        Veterinaria other = (Veterinaria) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Veterinaria[ codigo=" + codigo + " ]";
    }
    
}
