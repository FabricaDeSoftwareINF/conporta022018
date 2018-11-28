package br.ufg.inf.fabrica.conporta022018.modelo;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class Portaria extends ModeloAbstrato {
    private static final long serialVersionUID = 1L;

    @Column
    @NotBlank
    private int anoId;

    @Column
    @NotBlank
    private int seqId;

    @Column
    private String assunto;

    @Column
    @Temporal(TemporalType.DATE)
    @PastOrPresent
    private Date dtExped;

    @Column
    @Temporal(TemporalType.DATE)
    @FutureOrPresent
    private Date dtIniVig;

    @Column
    @Temporal(TemporalType.DATE)
    @FutureOrPresent
    private Date dtFimVig;

    @Column
    @Temporal(TemporalType.DATE)
    @FutureOrPresent
    private Date dtPublicDou;

    @Column
    private int horasDesig;

    @Column
    private String resumo;

    @Column
    private String textoCompleto;

    @Column
    @Lob
    private byte[] arqPdf;

    @Column
    @NotBlank
    private String siglaUndId;

    @Column
    private String assinatura;

    @Column
    @NotBlank
    private PortariaStatus status;

    @JoinColumn
    @OneToMany
    private List<Referencia> referencias;

    @JoinColumn
    @OneToMany
    private List<Recebedora> undRecebedora;

    @JoinColumn
    @OneToMany
    private List<Designado> designados;

    @JoinColumn
    @ManyToOne
    private Pessoa expedidor;

    @JoinColumn
    @ManyToOne
    private UndAdm unidadeExpedidora;

    public int getAnoId() {
        return anoId;
    }

    public void setAnoId(int anoId) {
        this.anoId = anoId;
    }

    public int getSeqId() {
        return seqId;
    }

    public void setSeqId(int seqId) {
        this.seqId = seqId;
    }

>>>>>>> dev
    public String getAssunto() {
        return assunto;
    }

<<<<<<< HEAD
    /**
     * @param assunto the assunto to set
     */
=======
>>>>>>> dev
    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

<<<<<<< HEAD
    /**
     * @return Date return the dtExped
     */
=======
>>>>>>> dev
    public Date getDtExped() {
        return dtExped;
    }

<<<<<<< HEAD
    /**
     * @param dtExped the dtExped to set
     */
=======
>>>>>>> dev
    public void setDtExped(Date dtExped) {
        this.dtExped = dtExped;
    }

<<<<<<< HEAD
    /**
     * @return String return the periodVig
     */
    public String getPeriodVig() {
        return periodVig;
    }

    /**
     * @param periodVig the periodVig to set
     */
    public void setPeriodVig(String periodVig) {
        this.periodVig = periodVig;
    }

    /**
     * @return Date return the dtIniVig
     */
=======
>>>>>>> dev
    public Date getDtIniVig() {
        return dtIniVig;
    }

<<<<<<< HEAD
    /**
     * @param dtIniVig the dtIniVig to set
     */
=======
>>>>>>> dev
    public void setDtIniVig(Date dtIniVig) {
        this.dtIniVig = dtIniVig;
    }

<<<<<<< HEAD
    /**
     * @return Date return the dtFimVig
     */
=======
>>>>>>> dev
    public Date getDtFimVig() {
        return dtFimVig;
    }

<<<<<<< HEAD
    /**
     * @param dtFimVig the dtFimVig to set
     */
=======
>>>>>>> dev
    public void setDtFimVig(Date dtFimVig) {
        this.dtFimVig = dtFimVig;
    }

<<<<<<< HEAD
    /**
     * @return Date return the dPublicDou
     */
    public Date getDPublicDou() {
        return dPublicDou;
    }

    /**
     * @param dPublicDou the dPublicDou to set
     */
    public void setDPublicDou(Date dPublicDou) {
        this.dPublicDou = dPublicDou;
    }

    /**
     * @return Integer return the horasDesig
     */
    public Integer getHorasDesig() {
        return horasDesig;
    }

    /**
     * @param horasDesig the horasDesig to set
     */
    public void setHorasDesig(Integer horasDesig) {
        this.horasDesig = horasDesig;
    }

    /**
     * @return String return the resumo
     */
=======
    public Date getDtPublicDou() {
        return dtPublicDou;
    }

    public void setDtPublicDou(Date dtPublicDou) {
        this.dtPublicDou = dtPublicDou;
    }

    public int getHorasDesig() {
        return horasDesig;
    }

    public void setHorasDesig(int horasDesig) {
        this.horasDesig = horasDesig;
    }

>>>>>>> dev
    public String getResumo() {
        return resumo;
    }

<<<<<<< HEAD
    /**
     * @param resumo the resumo to set
     */
=======
>>>>>>> dev
    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

<<<<<<< HEAD
    /**
     * @return String return the textoCompleto
     */
=======
>>>>>>> dev
    public String getTextoCompleto() {
        return textoCompleto;
    }

<<<<<<< HEAD
    /**
     * @param textoCompleto the textoCompleto to set
     */
=======
>>>>>>> dev
    public void setTextoCompleto(String textoCompleto) {
        this.textoCompleto = textoCompleto;
    }

<<<<<<< HEAD
    /**
     * @return Blob return the arqPdf
     */
    public Blob getArqPdf() {
        return arqPdf;
    }

    /**
     * @param arqPdf the arqPdf to set
     */
    public void setArqPdf(Blob arqPdf) {
        this.arqPdf = arqPdf;
    }

    /**
     * @return String return the assinatura
     */
=======
    public byte[] getArqPdf() {
        return arqPdf;
    }

    public void setArqPdf(byte[] arqPdf) {
        this.arqPdf = arqPdf;
    }

    public String getSiglaUndId() {
        return siglaUndId;
    }

    public void setSiglaUndId(String siglaUndId) {
        this.siglaUndId = siglaUndId;
    }

>>>>>>> dev
    public String getAssinatura() {
        return assinatura;
    }

<<<<<<< HEAD
    /**
     * @param assinatura the assinatura to set
     */
=======
>>>>>>> dev
    public void setAssinatura(String assinatura) {
        this.assinatura = assinatura;
    }

<<<<<<< HEAD
    /**
     * @return Enum<PortariaStatus> return the status
     */
    public Enum<PortariaStatus> getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Enum<PortariaStatus> status) {
        this.status = status;
    }

    /**
     * @return List<PortariaReferenciada> return the portariasReferenciadas
     */
    public List<PortariaReferenciada> getPortariasReferenciadas() {
        return portariasReferenciadas;
    }

    /**
     * @param portariasReferenciadas the portariasReferenciadas to set
     */
    public void setPortariasReferenciadas(List<PortariaReferenciada> portariasReferenciadas) {
        this.portariasReferenciadas = portariasReferenciadas;
    }

    /**
     * @return List<Designado> return the designados
     */
=======
    public PortariaStatus getStatus() {
        return status;
    }

    public void setStatus(PortariaStatus status) {
        this.status = status;
    }

    public List<Referencia> getReferencias() {
        return referencias;
    }

    public void setReferencias(List<Referencia> referencias) {
        this.referencias = referencias;
    }

    public List<Recebedora> getUndRecebedora() {
        return undRecebedora;
    }

    public void setUndRecebedora(List<Recebedora> undRecebedora) {
        this.undRecebedora = undRecebedora;
    }

>>>>>>> dev
    public List<Designado> getDesignados() {
        return designados;
    }

<<<<<<< HEAD
    /**
     * @param designados the designados to set
     */
=======
>>>>>>> dev
    public void setDesignados(List<Designado> designados) {
        this.designados = designados;
    }

<<<<<<< HEAD
    /**
     * @return List<Recebedora> return the recebedoras
     */
    public List<Recebedora> getRecebedoras() {
        return recebedoras;
    }

    /**
     * @param recebedoras the recebedoras to set
     */
    public void setRecebedoras(List<Recebedora> recebedoras) {
        this.recebedoras = recebedoras;
    }

    /**
     * @return UndAdm return the expedidora
     */
    public UndAdm getExpedidora() {
        return expedidora;
    }

    /**
     * @param expedidora the expedidora to set
     */
    public void setExpedidora(UndAdm expedidora) {
        this.expedidora = expedidora;
    }

}
=======
    public Pessoa getExpedidor() {
        return expedidor;
    }

    public void setExpedidor(Pessoa expedidor) {
        this.expedidor = expedidor;
    }

    public UndAdm getUnidadeExpedidora() {
        return unidadeExpedidora;
    }

    public void setUnidadeExpedidora(UndAdm unidadeExpedidora) {
        this.unidadeExpedidora = unidadeExpedidora;
    }
}
>>>>>>> dev
