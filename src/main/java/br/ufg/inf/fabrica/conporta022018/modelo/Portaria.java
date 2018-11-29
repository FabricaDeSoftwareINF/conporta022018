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

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public Date getDtExped() {
        return dtExped;
    }

    public void setDtExped(Date dtExped) {
        this.dtExped = dtExped;
    }

    public Date getDtIniVig() {
        return dtIniVig;
    }

    public void setDtIniVig(Date dtIniVig) {
        this.dtIniVig = dtIniVig;
    }

    public Date getDtFimVig() {
        return dtFimVig;
    }

    public void setDtFimVig(Date dtFimVig) {
        this.dtFimVig = dtFimVig;
    }

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

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public String getTextoCompleto() {
        return textoCompleto;
    }

    public void setTextoCompleto(String textoCompleto) {
        this.textoCompleto = textoCompleto;
    }

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

    public String getAssinatura() {
        return assinatura;
    }

    public void setAssinatura(String assinatura) {
        this.assinatura = assinatura;
    }

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

    public List<Designado> getDesignados() {
        return designados;
    }

    public void setDesignados(List<Designado> designados) {
        this.designados = designados;
    }

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