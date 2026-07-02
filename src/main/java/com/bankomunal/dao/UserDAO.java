package com.bankomunal.dao;

import com.bankomunal.model.User;
import com.bankomunal.util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Acceso a datos para la entidad User (tabla `users`).
 * Implementa las operaciones necesarias para el modulo de Login / Registro.
 */
public class UserDAO {

    public boolean existeEmail(String email) throws SQLException {
        String sql = "SELECT id FROM users WHERE email = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    /** Inserta un nuevo usuario. El rol por defecto y demas columnas
     *  toman los valores DEFAULT definidos en el esquema bankomunal.sql. */
    public Long registrar(User user) throws SQLException {
        String sql = "INSERT INTO users (first_name, last_name, email, password_hash, "
                + "tipo_documento, identification_number, phone, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, 'active')";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPasswordHash());
            ps.setString(5, user.getTipoDocumento());
            ps.setString(6, user.getIdentificationNumber());
            ps.setString(7, user.getPhone());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getLong(1);
                }
            }
        }
        return null;
    }

    public User buscarPorEmail(String email) throws SQLException {
        String sql = "SELECT id, first_name, last_name, email, password_hash, status "
                + "FROM users WHERE email = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        }
        return null;
    }

    private User mapear(ResultSet rs) throws SQLException {
        User u = new User();
        u.setId(rs.getLong("id"));
        u.setFirstName(rs.getString("first_name"));
        u.setLastName(rs.getString("last_name"));
        u.setEmail(rs.getString("email"));
        u.setPasswordHash(rs.getString("password_hash"));
        u.setStatus(rs.getString("status"));
        return u;
    }
}
