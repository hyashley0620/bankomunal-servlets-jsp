package com.bankomunal.model;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * Modelo que representa una meta de ahorro.
 * Se mapea a la tabla `savings_goals` del esquema bankomunal.sql.
 */
public class SavingsGoal {

    private Long id;
    private Long userId;
    private String nombre;
    private BigDecimal meta;
    private BigDecimal acumulado;
    private Date fechaLimite;
    private String status;

    public SavingsGoal() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getMeta() {
        return meta;
    }

    public void setMeta(BigDecimal meta) {
        this.meta = meta;
    }

    public BigDecimal getAcumulado() {
        return acumulado;
    }

    public void setAcumulado(BigDecimal acumulado) {
        this.acumulado = acumulado;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /** Porcentaje de avance de la meta, usado en la vista JSP. */
    public int getPorcentajeAvance() {
        if (meta == null || meta.compareTo(BigDecimal.ZERO) == 0 || acumulado == null) {
            return 0;
        }
        BigDecimal pct = acumulado.multiply(BigDecimal.valueOf(100)).divide(meta, 0, BigDecimal.ROUND_HALF_UP);
        return Math.min(pct.intValue(), 100);
    }
}
