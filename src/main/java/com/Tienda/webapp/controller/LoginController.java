package com.Tienda.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Tienda.webapp.model.Producto;
import com.Tienda.webapp.model.Proveedor;
import com.Tienda.webapp.model.RepLegal;
import com.Tienda.webapp.model.Usuario;
import com.Tienda.webapp.model.Venta;
import com.Tienda.webapp.repository.ProductoRepository;
import com.Tienda.webapp.repository.VentaRepository;
import com.Tienda.webapp.service.ProveedorService;
import com.Tienda.webapp.service.RepLegalService;
import com.Tienda.webapp.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService service;

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private RepLegalService repLegalService;

    @GetMapping("/")
    public String login() {
        return "login";
    }

    @GetMapping("/mis-compras")
    public String misCompras(HttpSession session, Model model) {
        Usuario u = (Usuario) session.getAttribute("usuarioLogueado");
        if (u == null) {
            return "redirect:/";
        }
        List<Venta> ventas = ventaRepository.findByUsuarioOrderByFechaDesc(u);
        model.addAttribute("ventas", ventas);
        return "mis-compras";
    }

    @GetMapping("/facturas")
    public String listarFacturas(HttpSession session, Model model) {
        Usuario u = (Usuario) session.getAttribute("usuarioLogueado");
        if (u == null) {
            return "redirect:/";
        }
        List<Venta> ventas = ventaRepository.findByUsuarioOrderByFechaDesc(u);
        model.addAttribute("ventas", ventas);
        return "facturas-lista";
    }

    @GetMapping("/factura-electronica/{id}")
    public String verFacturaElectronica(@PathVariable String id, HttpSession session, Model model) {
        Usuario u = (Usuario) session.getAttribute("usuarioLogueado");
        if (u == null) {
            return "redirect:/";
        }
        Venta venta = ventaRepository.findById(id).orElse(null);
        if (venta == null || !venta.getUsuario().getIdUsuario().equals(u.getIdUsuario())) {
            return "redirect:/facturas";
        }
        model.addAttribute("venta", venta);
        return "factura-electronica";
    }

    @PostMapping("/login")
    public String validar(@RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        Usuario u = service.login(username, password);

        if (u != null) {
            // Guardar usuario en sesión
            session.setAttribute("usuarioLogueado", u);

            if (u.getIdRol() == 1) {
                return "redirect:/admin";
            } else {
                return "redirect:/cliente";
            }

        }

        model.addAttribute("error", "Usuario o contraseña incorrectos");
        return "login";
    }

    @GetMapping("/productos")
    public String productos() {
        return "productos";
    }

    @GetMapping("/cliente")
    public String cliente() {
        return "cliente";
    }
    
    @GetMapping("/admin")
    public String admin(Model model) {
        // Calcular alertas para el dashboard
        long stockBajoCount = productoRepository.findByStockLessThan(10).size();
        long inactivosCount = productoRepository.findByEstadoProducto("I").size();
        
        model.addAttribute("alertCount", stockBajoCount + inactivosCount);
        model.addAttribute("stockBajoCount", stockBajoCount);
        return "admin";
    }

    @GetMapping("/perfil")
    public String verPerfil(HttpSession session, Model model) {
        Usuario u = (Usuario) session.getAttribute("usuarioLogueado");
        if (u == null) {
            return "redirect:/";
        }
        model.addAttribute("usuario", u);
        return "perfil";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
    
    @GetMapping("/gestionar-productos")
    public String gestionarProductos() {
        return "gestionar-productos";
    }

    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "registro";
    }

    @GetMapping("/admin/usuarios")
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = service.listarTodos();
        model.addAttribute("usuarios", usuarios);
        return "usuarios";
    }

    // ===== PROVEEDORES =====

    @GetMapping("/admin/proveedores")
    public String listarProveedores(Model model) {
        model.addAttribute("proveedores", proveedorService.listarTodos());
        return "proveedores";
    }

    @PostMapping("/admin/proveedores")
    public String guardarProveedor(
            @RequestParam String idProveedor,
            @RequestParam String nombre,
            @RequestParam(required = false) String idRepLegal,
            RedirectAttributes redirectAttributes) {

        try {
            Proveedor proveedor = new Proveedor();
            proveedor.setIdProveedor(idProveedor);
            proveedor.setNombreProveedor(nombre);
            
            if (idRepLegal != null && !idRepLegal.isEmpty()) {
                RepLegal rl = repLegalService.buscarPorId(idRepLegal);
                if (rl == null) {
                    redirectAttributes.addFlashAttribute("error", "Error: El ID del Representante Legal (" + idRepLegal + ") no existe. Créalo primero en la sección de Representantes.");
                    return "redirect:/admin/proveedores";
                }
                proveedor.setRepLegal(rl);
            }

            proveedorService.guardar(proveedor);
            redirectAttributes.addFlashAttribute("success", "Proveedor registrado correctamente");
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al registrar proveedor: " + e.getMessage());
        }
        
        return "redirect:/admin/proveedores";
    }

    @GetMapping("/admin/proveedores/eliminar/{id}")
    public String eliminarProveedor(@PathVariable String id, RedirectAttributes redirectAttributes) {
        proveedorService.eliminar(id);
        redirectAttributes.addFlashAttribute("success", "Proveedor eliminado correctamente");
        return "redirect:/admin/proveedores";
    }

    // ===== REPRESENTANTES LEGALES =====

    @GetMapping("/admin/replegal")
    public String listarRepLegal(Model model) {
        model.addAttribute("representantes", repLegalService.listarTodos());
        return "replegal";
    }

    @PostMapping("/admin/replegal")
    public String guardarRepLegal(
            @RequestParam String id,
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String telefono,
            @RequestParam String correo,
            @RequestParam String direccion,
            RedirectAttributes redirectAttributes) {

        RepLegal rl = new RepLegal();
        rl.setIdRepLegal(id);
        rl.setNombre(nombre);
        rl.setApellido(apellido);
        rl.setNumeroTelefono(telefono);
        rl.setCorreo(correo);
        rl.setDireccion(direccion);
        repLegalService.guardar(rl);

        redirectAttributes.addFlashAttribute("success", "Representante legal registrado correctamente");
        return "redirect:/admin/replegal";
    }

    @GetMapping("/admin/replegal/eliminar/{id}")
    public String eliminarRepLegal(@PathVariable String id, RedirectAttributes redirectAttributes) {
        repLegalService.eliminar(id);
        redirectAttributes.addFlashAttribute("success", "Representante legal eliminado correctamente");
        return "redirect:/admin/replegal";
    }

    // ===== ALERTAS =====

    @GetMapping("/admin/alertas")
    public String alertas(Model model) {
        List<Producto> stockBajo = productoRepository.findByStockLessThan(10);
        List<Producto> inactivos = productoRepository.findByEstadoProducto("I");
        model.addAttribute("stockBajo", stockBajo);
        model.addAttribute("inactivos", inactivos);
        return "alertas";
    }

    @PostMapping("/registro")
    public String procesarRegistro(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String correo,
            Model model) {

        try {
            // Validar que las contraseñas coincidan
            if (!password.equals(confirmPassword)) {
                model.addAttribute("error", "Las contraseñas no coinciden");
                model.addAttribute("username", username);
                model.addAttribute("nombre", nombre);
                model.addAttribute("apellido", apellido);
                model.addAttribute("correo", correo);
                return "registro";
            }

            // Validar que el usuario no exista
            if (service.existeUsuario(username)) {
                model.addAttribute("error", "El nombre de usuario ya está en uso");
                model.addAttribute("nombre", nombre);
                model.addAttribute("apellido", apellido);
                model.addAttribute("correo", correo);
                return "registro";
            }

            // Crear nuevo usuario
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setIdUsuario(username);
            nuevoUsuario.setPassword(password);
            nuevoUsuario.setNombre(nombre);
            nuevoUsuario.setApellido(apellido);
            nuevoUsuario.setCorreo(correo);

            // Registrar usuario
            service.registrarUsuario(nuevoUsuario);

            // Redirigir al login con mensaje de éxito
            model.addAttribute("success", "¡Registro exitoso! Ahora puedes iniciar sesión");
            return "login";
            
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al registrar usuario: " + e.getMessage());
            model.addAttribute("username", username);
            model.addAttribute("nombre", nombre);
            model.addAttribute("apellido", apellido);
            model.addAttribute("correo", correo);
            return "registro";
        }
    }
}
