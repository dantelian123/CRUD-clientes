/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package web;

import datos.ClienteDaoJDBC;
import dominio.Cliente;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ServletControlador")
public class ServletControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion != null) {
            switch (accion) {               
                 case "editar":
                    this.editarCliente(request,response);
                    break;
                 case "eliminar":
                    this.eliminarCliente(request,response);
                    break;
                default:
                    this.accionDefault(request, response);
            }
        } else {
            this.accionDefault(request, response);
        }
        
    }

    private void accionDefault(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Cliente> clientes = new ClienteDaoJDBC().listar();
        System.out.println("clientes = " + clientes);
        HttpSession sesion = request.getSession();
        sesion.setAttribute("clientes", clientes);        
        sesion.setAttribute("totalClientes", clientes.size());
        sesion.setAttribute("saldoTotal", calcularSaldoTotal(clientes));
        //request.getRequestDispatcher("clientes.jsp").forward(request, response);
        response.sendRedirect("clientes.jsp");
    }

    private double calcularSaldoTotal(List<Cliente> clientes) {
        double saldoTotal = 0;
        for (Cliente cliente : clientes) {
            saldoTotal += cliente.getSaldo();
        }
        return saldoTotal;
    }
    
    private void editarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Recuperar el idCliente
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        System.out.println(idCliente);
        Cliente cliente = new ClienteDaoJDBC().buscar(new Cliente(idCliente));
        request.setAttribute("cliente", cliente);
        String JspEditar = "/WEB-INF/paginas/cliente/editarCliente.jsp";
        request.getRequestDispatcher(JspEditar).forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion != null) {
            switch (accion) {
                case "insertar":
                    this.insertarCliente(request, response);                    
                    break; 
                case "modificar":
                    this.modificarCliente(request, response);
                    break;
                default:
                    this.accionDefault(request, response);
            }
        } else {
            this.accionDefault(request, response);
        }
    }

    private void insertarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        String saldoString = request.getParameter("saldo");
        double saldo = 0;
        if(saldoString!=null && !"".equals(saldoString)){
            saldo = Double.parseDouble(saldoString);
        }
        Cliente cliente = new Cliente(nombre, apellido, email, telefono, saldo);
        int registrodModificados = new ClienteDaoJDBC().insertar(cliente);
        this.accionDefault(request, response);        
    }

    private void modificarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        String saldoString = request.getParameter("saldo");
        double saldo = 0;
        if(saldoString!=null && !"".equals(saldoString)){
            saldo = Double.parseDouble(saldoString);
        }
        Cliente cliente = new Cliente(idCliente, nombre, apellido, email, telefono, saldo);
        int registrodModificados = new ClienteDaoJDBC().actualizar(cliente);
        this.accionDefault(request, response); 
    }

    private void eliminarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        System.out.println(idCliente);
        new ClienteDaoJDBC().eliminar(new Cliente(idCliente));        
        this.accionDefault(request, response);        
    }

    
}
