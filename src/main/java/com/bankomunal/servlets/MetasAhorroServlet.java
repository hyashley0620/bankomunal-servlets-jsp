package com.bankomunal.servlets;

import com.bankomunal.dao.SavingsGoalDAO;
import com.bankomunal.model.SavingsGoal;
import com.bankomunal.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet CRUD para el modulo de Metas de Ahorro (tabla `savings_goals`).
 *
 * GET  /metas                     -> lista las metas del usuario en sesion
 * GET  /metas?action=nueva        -> muestra el formulario de creacion
 * GET  /metas?action=editar&id=.. -> muestra el formulario de edicion
 * GET  /metas?action=eliminar&id=..-> elimina una meta
 * POST /metas                     -> procesa creacion / actualizacion / abono
 *                                     segun el parametro "action"
 */
public class MetasAhorroServlet extends HttpServlet {

    private final SavingsGoalDAO metaDAO = new SavingsGoalDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User usuario = usuarioEnSesion(req);
        String action = req.getParameter("action");

        try {
            if ("nueva".equals(action)) {
                req.getRequestDispatcher("/WEB-INF/views/metaForm.jsp").forward(req, resp);
                return;
            }

            if ("editar".equals(action)) {
                Long id = Long.valueOf(req.getParameter("id"));
                SavingsGoal meta = metaDAO.buscarPorId(id, usuario.getId());
                if (meta == null) {
                    resp.sendRedirect(req.getContextPath() + "/metas");
                    return;
                }
                req.setAttribute("meta", meta);
                req.getRequestDispatcher("/WEB-INF/views/metaForm.jsp").forward(req, resp);
                return;
            }

            if ("eliminar".equals(action)) {
                Long id = Long.valueOf(req.getParameter("id"));
                metaDAO.eliminar(id, usuario.getId());
                resp.sendRedirect(req.getContextPath() + "/metas");
                return;
            }

            // Listado por defecto
            List<SavingsGoal> metas = metaDAO.listarPorUsuario(usuario.getId());
            req.setAttribute("metas", metas);
            req.getRequestDispatcher("/WEB-INF/views/metas.jsp").forward(req, resp);

        } catch (SQLException e) {
            throw new ServletException("Error consultando metas de ahorro", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User usuario = usuarioEnSesion(req);
        String action = req.getParameter("action");

        try {
            switch (action == null ? "" : action) {
                case "crear":
                    crear(req, usuario);
                    break;
                case "actualizar":
                    actualizar(req, usuario);
                    break;
                case "abonar":
                    abonar(req, usuario);
                    break;
                default:
                    // accion no reconocida, no se hace nada
            }
            resp.sendRedirect(req.getContextPath() + "/metas");

        } catch (SQLException e) {
            throw new ServletException("Error procesando la meta de ahorro", e);
        }
    }

    private void crear(HttpServletRequest req, User usuario) throws SQLException {
        SavingsGoal meta = new SavingsGoal();
        meta.setUserId(usuario.getId());
        meta.setNombre(req.getParameter("nombre"));
        meta.setMeta(new BigDecimal(req.getParameter("meta")));
        String fecha = req.getParameter("fechaLimite");
        if (fecha != null && !fecha.trim().isEmpty()) {
            meta.setFechaLimite(Date.valueOf(fecha));
        }
        metaDAO.crear(meta);
    }

    private void actualizar(HttpServletRequest req, User usuario) throws SQLException {
        SavingsGoal meta = new SavingsGoal();
        meta.setId(Long.valueOf(req.getParameter("id")));
        meta.setUserId(usuario.getId());
        meta.setNombre(req.getParameter("nombre"));
        meta.setMeta(new BigDecimal(req.getParameter("meta")));
        meta.setAcumulado(new BigDecimal(req.getParameter("acumulado")));
        meta.setStatus(req.getParameter("status"));
        String fecha = req.getParameter("fechaLimite");
        if (fecha != null && !fecha.trim().isEmpty()) {
            meta.setFechaLimite(Date.valueOf(fecha));
        }
        metaDAO.actualizar(meta);
    }

    private void abonar(HttpServletRequest req, User usuario) throws SQLException {
        Long id = Long.valueOf(req.getParameter("id"));
        BigDecimal monto = new BigDecimal(req.getParameter("monto"));
        metaDAO.abonar(id, usuario.getId(), monto);
    }

    private User usuarioEnSesion(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return session != null ? (User) session.getAttribute("usuario") : null;
    }
}
