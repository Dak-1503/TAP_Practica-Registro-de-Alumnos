package com.ites.li.practicaregistrodealumnos;

public class Alumno {

    private String noControl;
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private String carrera;
    private String domicilio;
    private String fechaNac;

    public Alumno(){

    }

    // constructor
    public Alumno(String noControl, String nombre, String apellidoP, String apellidoM, String carrera, String domicilio, String fechaNac) {
        this.noControl = noControl;
        this.nombre = nombre;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.carrera = carrera;
        this.domicilio = domicilio;
        this.fechaNac = fechaNac;
    }

    // Getters
    public String getNoControl() {
        return noControl;
    }
    public String getNombre() {
        return nombre;
    }
    public String getApellidoP() {
        return apellidoP;
    }
    public String getApellidoM() {
        return apellidoM;
    }
    public String getCarrera() {
        return carrera;
    }
    public String getDomicilio(){
        return domicilio;
    }
    public String getFechaNac(){
        return fechaNac;
    }

    // Setters
    public void setNoControl(String noControl) {
        this.noControl = noControl;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }
    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }
    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }
    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }
    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }
}