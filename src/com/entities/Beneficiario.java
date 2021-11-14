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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author EQUIPO
 */
@Entity
@Table(name = "beneficiario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Beneficiario.findAll", query = "SELECT b FROM Beneficiario b")
    , @NamedQuery(name = "Beneficiario.findByCodigo", query = "SELECT b FROM Beneficiario b WHERE b.codigo = :codigo")
    , @NamedQuery(name = "Beneficiario.findByEspecie", query = "SELECT b FROM Beneficiario b WHERE b.especie = :especie")
    , @NamedQuery(name = "Beneficiario.findByNombre", query = "SELECT b FROM Beneficiario b WHERE b.nombre = :nombre")
    , @NamedQuery(name = "Beneficiario.findByRaza", query = "SELECT b FROM Beneficiario b WHERE b.raza = :raza")})
public class Beneficiario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "especie")
    private String especie;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "raza")
    private String raza;
    @OneToMany(mappedBy = "mascotaConsultaCodigo")
    private List<Historial> historialList;
    @JoinColumn(name = "beneficiario_codigo", referencedColumnName = "codigo")
    @ManyToOne
    private Afiliado beneficiarioCodigo;

    public Beneficiario() {
    }

    public Beneficiario(Integer codigo) {
        this.codigo = codigo;
    }

    public Beneficiario(Integer codigo, String especie, String nombre, String raza) {
        this.codigo = codigo;
        this.especie = especie;
        this.nombre = nombre;
        this.raza = raza;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    @XmlTransient
    public List<Historial> getHistorialList() {
        return historialList;
    }

    public void setHistorialList(List<Historial> historialList) {
        this.historialList = historialList;
    }

    public Afiliado getBeneficiarioCodigo() {
        return beneficiarioCodigo;
    }

    public void setBeneficiarioCodigo(Afiliado beneficiarioCodigo) {
        this.beneficiarioCodigo = beneficiarioCodigo;
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
        if (!(object instanceof Beneficiario)) {
            return false;
        }
        Beneficiario other = (Beneficiario) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Beneficiario[ codigo=" + codigo + " ]";
    }
    
}
