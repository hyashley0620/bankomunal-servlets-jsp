package com.bankomunal.servlets;

import com.bankomunal.dao.UserDAO;
import com.bankomunal.model.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Servlet de Registro de usuarios.
 *
 * GET  /registro -> muestra el formulario HTML (registro.jsp)
 * POST /registro -> valida los datos y crea el usuario en la BD
 */
public class RegistroServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/registro.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String tipoDocumento = req.getParameter("tipoDocumento");
        String identificationNumber = req.getParameter("identificationNumber");
        String phone = req.getParameter("phone");

        // --- Validaciones basicas del lado servidor ---
        if (isBlank(firstName) || isBlank(lastName) || isBlank(email) || isBlank(password)) {
            req.setAttribute("error", "Todos los campos obligatorios deben diligenciarse.");
            req.getRequestDispatcher("/WEB-INF/views/registro.jsp").forward(req, resp);
            return;
        }
        if (!password.equals(confirmPassword)) {
            req.setAttribute("error", "Las contrasenas no coinciden.");
            req.getRequestDispatcher("/WEB-INF/views/registro.jsp").forward(req, resp);
            return;
        }
        if (password.length() < 8) {
            req.setAttribute("error", "La contrasena debe tener minimo 8 caracteres.");
            req.getRequestDispatcher("/WEB-INF/views/registro.jsp").forward(req, resp);
            return;
        }

        try {
            if (userDAO.existeEmail(email)) {
                req.setAttribute("error", "Ya existe una cuenta registrada con ese correo.");
                req.getRequestDispatcher("/WEB-INF/views/registro.jsp").forward(req, resp);
                return;
            }

            String hash = BCrypt.hashpw(password, BCrypt.gensalt());
            User nuevo = new User(firstName, lastName, email, hash,
                    tipoDocumento, identificationNumber, phone);
            userDAO.registrar(nuevo);

            resp.sendRedirect(req.getContextPath() + "/login?registrado=1");

        } catch (SQLException e) {
            req.setAttribute("error", "Error al registrar el usuario: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/registro.jsp").forward(req, resp);
        }
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
