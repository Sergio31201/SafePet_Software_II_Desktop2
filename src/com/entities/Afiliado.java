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
 * @author EQUIPO
 */
@Entity
@Table(name = "afiliado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Afiliado.findAll", query = "SELECT a FROM Afiliado a")
    , @NamedQuery(name = "Afiliado.findByCodigo", query = "SELECT a FROM Afiliado a WHERE a.codigo = :codigo")
    , @NamedQuery(name = "Afiliado.findByEmail", query = "SELECT a FROM Afiliado a WHERE a.email = :email")
    , @NamedQuery(name = "Afiliado.findByNombre", query = "SELECT a FROM Afiliado a WHERE a.nombre = :nombre")
    , @NamedQuery(name = "Afiliado.findByPassword", query = "SELECT a FROM Afiliado a WHERE a.password = :password")
    , @NamedQuery(name = "Afiliado.findByNumTelefono", query = "SELECT a FROM Afiliado a WHERE a.numTelefono = :numTelefono")})
public class Afiliado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "num_telefono")
    private String numTelefono;
    @OneToMany(mappedBy = "idAfiliado")
    private List<Comprobante> comprobanteList;
    @OneToMany(mappedBy = "clienteAsociadoCodigo")
    private List<Historial> historialList;
    @OneToMany(mappedBy = "beneficiarioCodigo")
    private List<Beneficiario> beneficiarioList;

    public Afiliado() {
    }

    public Afiliado(Integer codigo) {
        this.codigo = codigo;
    }

    public Afiliado(Integer codigo, String email, String nombre, String password, String numTelefono) {
        this.codigo = codigo;
        this.email = email;
        this.nombre = nombre;
        this.password = password;
        this.numTelefono = numTelefono;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getNumTelefono() {
        return numTelefono;
    }

    public void setNumTelefono(String numTelefono) {
        this.numTelefono = numTelefono;
    }

    @XmlTransient
    public List<Comprobante> getComprobanteList() {
        return comprobanteList;
    }

    public void setComprobanteList(List<Comprobante> comprobanteList) {
        this.comprobanteList = comprobanteList;
    }

    @XmlTransient
    public List<Historial> getHistorialList() {
        return historialList;
    }

    public void setHistorialList(List<Historial> historialList) {
        this.historialList = historialList;
    }

    @XmlTransient
    public List<Beneficiario> getBeneficiarioList() {
        return beneficiarioList;
    }

    public void setBeneficiarioList(List<Beneficiario> beneficiarioList) {
        this.beneficiarioList = beneficiarioList;
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
        if (!(object instanceof Afiliado)) {
            return false;
        }
        Afiliado other = (Afiliado) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Afiliado[ codigo=" + codigo + " ]";
    }
    
}
