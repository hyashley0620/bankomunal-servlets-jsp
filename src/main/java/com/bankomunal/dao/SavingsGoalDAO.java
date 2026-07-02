package com.bankomunal.dao;

import com.bankomunal.model.SavingsGoal;
import com.bankomunal.util.ConexionBD;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Acceso a datos para la entidad SavingsGoal (tabla `savings_goals`).
 * Implementa el CRUD completo usado por MetasAhorroServlet.
 */
public class SavingsGoalDAO {

    public List<SavingsGoal> listarPorUsuario(Long userId) throws SQLException {
        String sql = "SELECT id, user_id, nombre, meta, acumulado, fecha_limite, status "
                + "FROM savings_goals WHERE user_id = ? ORDER BY created_at DESC";
        List<SavingsGoal> lista = new ArrayList<>();
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        }
        return lista;
    }

    public SavingsGoal buscarPorId(Long id, Long userId) throws SQLException {
        String sql = "SELECT id, user_id, nombre, meta, acumulado, fecha_limite, status "
                + "FROM savings_goals WHERE id = ? AND user_id = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.setLong(2, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        }
        return null;
    }

    public void crear(SavingsGoal meta) throws SQLException {
        String sql = "INSERT INTO savings_goals (user_id, nombre, meta, acumulado, fecha_limite, status) "
                + "VALUES (?, ?, ?, 0.00, ?, 'active')";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, meta.getUserId());
            ps.setString(2, meta.getNombre());
            ps.setBigDecimal(3, meta.getMeta());
            ps.setDate(4, meta.getFechaLimite());
            ps.executeUpdate();
        }
    }

    public void actualizar(SavingsGoal meta) throws SQLException {
        String sql = "UPDATE savings_goals SET nombre = ?, meta = ?, acumulado = ?, "
                + "fecha_limite = ?, status = ? WHERE id = ? AND user_id = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, meta.getNombre());
            ps.setBigDecimal(2, meta.getMeta());
            ps.setBigDecimal(3, meta.getAcumulado());
            ps.setDate(4, meta.getFechaLimite());
            ps.setString(5, meta.getStatus());
            ps.setLong(6, meta.getId());
            ps.setLong(7, meta.getUserId());
            ps.executeUpdate();
        }
    }

    public void abonar(Long id, Long userId, BigDecimal monto) throws SQLException {
        String sql = "UPDATE savings_goals SET acumulado = acumulado + ? WHERE id = ? AND user_id = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBigDecimal(1, monto);
            ps.setLong(2, id);
            ps.setLong(3, userId);
            ps.executeUpdate();
        }
    }

    public void eliminar(Long id, Long userId) throws SQLException {
        String sql = "DELETE FROM savings_goals WHERE id = ? AND user_id = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.setLong(2, userId);
            ps.executeUpdate();
        }
    }

    private SavingsGoal mapear(ResultSet rs) throws SQLException {
        SavingsGoal m = new SavingsGoal();
        m.setId(rs.getLong("id"));
        m.setUserId(rs.getLong("user_id"));
        m.setNombre(rs.getString("nombre"));
        m.setMeta(rs.getBigDecimal("meta"));
        m.setAcumulado(rs.getBigDecimal("acumulado"));
        m.setFechaLimite(rs.getDate("fecha_limite"));
        m.setStatus(rs.getString("status"));
        return m;
    }
}
