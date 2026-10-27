package com.example;

import com.example.model.Contacto;
import com.example.model.Libro;
import com.example.model.Prestamo;
import com.example.model.Usuario;
import com.example.service.CrudService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Sistema Prestamo de Libros CESDE - CRUD")
@Route("")
public class MainView extends VerticalLayout {

    private final CrudService crudService;

    public MainView(CrudService crudService) {
        this.crudService = crudService;
        setSizeFull();
        setPadding(true);
        setSpacing(true);

        TabSheet tabSheet = new TabSheet();
        tabSheet.setWidthFull();
        tabSheet.add("Usuario", crearSeccionUsuario());
        tabSheet.add("Contacto", crearSeccionContacto());
        tabSheet.add("Libros", crearSeccionLibro());
        tabSheet.add("Prestamo", crearSeccionPrestamo());

        add(new H2("Sistema Prestamo de Libros CESDE - CRUD"), tabSheet);
    }

    private Component crearSeccionUsuario() {
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(false);

        TextField idField = new TextField("ID Usuario");
        TextField nombreField = new TextField("Nombre");
        TextField tipoDocumentoField = new TextField("Tipo de documento");
        TextField documentoField = new TextField("Documento");
        TextField rolField = new TextField("Rol");
        TextField idContactoField = new TextField("ID Contacto");

        FormLayout form = new FormLayout(idField, nombreField, tipoDocumentoField,
                documentoField, rolField, idContactoField);

        Grid<Usuario> grid = new Grid<>(Usuario.class, false);
        grid.addColumn(Usuario::getIdUsuario).setHeader("ID Usuario").setAutoWidth(true);
        grid.addColumn(Usuario::getNombre).setHeader("Nombre").setAutoWidth(true);
        grid.addColumn(Usuario::getTipoDocumento).setHeader("Tipo de documento").setAutoWidth(true);
        grid.addColumn(Usuario::getDocumento).setHeader("Documento").setAutoWidth(true);
        grid.addColumn(Usuario::getRol).setHeader("Rol").setAutoWidth(true);
        grid.addColumn(Usuario::getIdContacto).setHeader("ID Contacto").setAutoWidth(true);

        Runnable refrescar = () -> grid.setItems(crudService.listarTodos(Usuario.class));
        refrescar.run();

        grid.asSingleSelect().addValueChangeListener(e -> {
            Usuario u = e.getValue();
            if (u != null) {
                idField.setValue(texto(u.getIdUsuario()));
                nombreField.setValue(texto(u.getNombre()));
                tipoDocumentoField.setValue(texto(u.getTipoDocumento()));
                documentoField.setValue(texto(u.getDocumento()));
                rolField.setValue(texto(u.getRol()));
                idContactoField.setValue(texto(u.getIdContacto()));
            }
        });

        Button btnCrear = new Button("Crear", e -> ejecutar(() -> {
            Usuario u = new Usuario();
            u.setNombre(nombreField.getValue());
            u.setTipoDocumento(tipoDocumentoField.getValue());
            u.setDocumento(parseInt(documentoField.getValue()));
            u.setRol(rolField.getValue());
            u.setIdContacto(parseId(idContactoField));
            crudService.guardar(u);
            refrescar.run();
            idField.setValue(texto(u.getIdUsuario()));
            Notification.show("Usuario guardado con ID: " + u.getIdUsuario());
        }));
        btnCrear.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button btnConsultar = new Button("Consultar", e -> ejecutar(() -> {
            Long id = idObligatorio(idField);
            if (id == null) return;
            Usuario u = crudService.buscarPorId(Usuario.class, id);
            if (u != null) {
                grid.asSingleSelect().setValue(u);
                Notification.show("Encontrado: " + u.getNombre());
            } else {
                Notification.show("No encontrado");
            }
        }));

        Button btnActualizar = new Button("Actualizar", e -> ejecutar(() -> {
            Long id = idObligatorio(idField);
            if (id == null) return;
            Usuario u = crudService.buscarPorId(Usuario.class, id);
            if (u != null) {
                u.setNombre(nombreField.getValue());
                u.setTipoDocumento(tipoDocumentoField.getValue());
                u.setDocumento(parseInt(documentoField.getValue()));
                u.setRol(rolField.getValue());
                u.setIdContacto(parseId(idContactoField));
                crudService.actualizar(u);
                refrescar.run();
                Notification.show("Usuario actualizado");
            } else {
                Notification.show("ID no encontrado");
            }
        }));

        Button btnEliminar = new Button("Eliminar", e -> ejecutar(() -> {
            Long id = idObligatorio(idField);
            if (id == null) return;
            crudService.eliminar(Usuario.class, id);
            refrescar.run();
            limpiar(idField, nombreField, tipoDocumentoField, documentoField, rolField, idContactoField);
            Notification.show("Usuario eliminado");
        }));
        btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button btnLimpiar = new Button("Limpiar", e -> {
            limpiar(idField, nombreField, tipoDocumentoField, documentoField, rolField, idContactoField);
            grid.asSingleSelect().clear();
        });

        layout.add(form, crearAcciones(btnCrear, btnConsultar, btnActualizar, btnEliminar, btnLimpiar), grid);
        return layout;
    }

    private Component crearSeccionContacto() {
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(false);

        TextField idField = new TextField("ID Contacto");
        TextField correoField = new TextField("Correo");
        TextField telefonoField = new TextField("Teléfono");
        TextField direccionField = new TextField("Dirección");

        FormLayout form = new FormLayout(idField, correoField, telefonoField, direccionField);

        Grid<Contacto> grid = new Grid<>(Contacto.class, false);
        grid.addColumn(Contacto::getIdContacto).setHeader("ID Contacto").setAutoWidth(true);
        grid.addColumn(Contacto::getCorreo).setHeader("Correo").setAutoWidth(true);
        grid.addColumn(Contacto::getTelefono).setHeader("Teléfono").setAutoWidth(true);
        grid.addColumn(Contacto::getDireccion).setHeader("Dirección").setAutoWidth(true);

        Runnable refrescar = () -> grid.setItems(crudService.listarTodos(Contacto.class));
        refrescar.run();

        grid.asSingleSelect().addValueChangeListener(e -> {
            Contacto c = e.getValue();
            if (c != null) {
                idField.setValue(texto(c.getIdContacto()));
                correoField.setValue(texto(c.getCorreo()));
                telefonoField.setValue(texto(c.getTelefono()));
                direccionField.setValue(texto(c.getDireccion()));
            }
        });

        Button btnCrear = new Button("Crear", e -> ejecutar(() -> {
            Contacto c = new Contacto(
                    correoField.getValue(),
                    telefonoField.getValue(),
                    direccionField.getValue());
            crudService.guardar(c);
            refrescar.run();
            idField.setValue(texto(c.getIdContacto()));
            Notification.show("Contacto guardado con ID: " + c.getIdContacto());
        }));
        btnCrear.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button btnConsultar = new Button("Consultar", e -> ejecutar(() -> {
            Long id = idObligatorio(idField);
            if (id == null) return;
            Contacto c = crudService.buscarPorId(Contacto.class, id);
            if (c != null) {
                grid.asSingleSelect().setValue(c);
                Notification.show("Encontrado: " + c.getCorreo());
            } else {
                Notification.show("No encontrado");
            }
        }));

        Button btnActualizar = new Button("Actualizar", e -> ejecutar(() -> {
            Long id = idObligatorio(idField);
            if (id == null) return;
            Contacto c = crudService.buscarPorId(Contacto.class, id);
            if (c != null) {
                c.setCorreo(correoField.getValue());
                c.setTelefono(telefonoField.getValue());
                c.setDireccion(direccionField.getValue());
                crudService.actualizar(c);
                refrescar.run();
                Notification.show("Contacto actualizado");
            } else {
                Notification.show("ID no encontrado");
            }
        }));

        Button btnEliminar = new Button("Eliminar", e -> ejecutar(() -> {
            Long id = idObligatorio(idField);
            if (id == null) return;
            crudService.eliminar(Contacto.class, id);
            refrescar.run();
            limpiar(idField, correoField, telefonoField, direccionField);
            Notification.show("Contacto eliminado");
        }));
        btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button btnLimpiar = new Button("Limpiar", e -> {
            limpiar(idField, correoField, telefonoField, direccionField);
            grid.asSingleSelect().clear();
        });

        layout.add(form, crearAcciones(btnCrear, btnConsultar, btnActualizar, btnEliminar, btnLimpiar), grid);
        return layout;
    }

    private Component crearSeccionLibro() {
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(false);

        TextField idField = new TextField("ID Libro");
        TextField nombreField = new TextField("Nombre");
        TextField generoField = new TextField("Género");
        TextField seccionField = new TextField("Sección");
        TextField ejemplaresField = new TextField("Ejemplares");
        TextField anioField = new TextField("Año de publicación");

        FormLayout form = new FormLayout(idField, nombreField, generoField,
                seccionField, ejemplaresField, anioField);

        Grid<Libro> grid = new Grid<>(Libro.class, false);
        grid.addColumn(Libro::getIdLibro).setHeader("ID Libro").setAutoWidth(true);
        grid.addColumn(Libro::getNombre).setHeader("Nombre").setAutoWidth(true);
        grid.addColumn(Libro::getGenero).setHeader("Género").setAutoWidth(true);
        grid.addColumn(Libro::getSeccion).setHeader("Sección").setAutoWidth(true);
        grid.addColumn(Libro::getEjemplares).setHeader("Ejemplares").setAutoWidth(true);
        grid.addColumn(Libro::getAñoPublicacion).setHeader("Año de publicación").setAutoWidth(true);

        Runnable refrescar = () -> grid.setItems(crudService.listarTodos(Libro.class));
        refrescar.run();

        grid.asSingleSelect().addValueChangeListener(e -> {
            Libro l = e.getValue();
            if (l != null) {
                idField.setValue(texto(l.getIdLibro()));
                nombreField.setValue(texto(l.getNombre()));
                generoField.setValue(texto(l.getGenero()));
                seccionField.setValue(texto(l.getSeccion()));
                ejemplaresField.setValue(texto(l.getEjemplares()));
                anioField.setValue(texto(l.getAñoPublicacion()));
            }
        });

        Button btnCrear = new Button("Crear", e -> ejecutar(() -> {
            Libro l = new Libro(
                    nombreField.getValue(),
                    generoField.getValue(),
                    seccionField.getValue(),
                    parseInt(ejemplaresField.getValue()),
                    parseInt(anioField.getValue()));
            crudService.guardar(l);
            refrescar.run();
            idField.setValue(texto(l.getIdLibro()));
            Notification.show("Libro guardado con ID: " + l.getIdLibro());
        }));
        btnCrear.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button btnConsultar = new Button("Consultar", e -> ejecutar(() -> {
            Long id = idObligatorio(idField);
            if (id == null) return;
            Libro l = crudService.buscarPorId(Libro.class, id);
            if (l != null) {
                grid.asSingleSelect().setValue(l);
                Notification.show("Encontrado: " + l.getNombre());
            } else {
                Notification.show("No encontrado");
            }
        }));

        Button btnActualizar = new Button("Actualizar", e -> ejecutar(() -> {
            Long id = idObligatorio(idField);
            if (id == null) return;
            Libro l = crudService.buscarPorId(Libro.class, id);
            if (l != null) {
                l.setNombre(nombreField.getValue());
                l.setGenero(generoField.getValue());
                l.setSeccion(seccionField.getValue());
                l.setEjemplares(parseInt(ejemplaresField.getValue()));
                l.setAñoPublicacion(parseInt(anioField.getValue()));
                crudService.actualizar(l);
                refrescar.run();
                Notification.show("Libro actualizado");
            } else {
                Notification.show("ID no encontrado");
            }
        }));

        Button btnEliminar = new Button("Eliminar", e -> ejecutar(() -> {
            Long id = idObligatorio(idField);
            if (id == null) return;
            crudService.eliminar(Libro.class, id);
            refrescar.run();
            limpiar(idField, nombreField, generoField, seccionField, ejemplaresField, anioField);
            Notification.show("Libro eliminado");
        }));
        btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button btnLimpiar = new Button("Limpiar", e -> {
            limpiar(idField, nombreField, generoField, seccionField, ejemplaresField, anioField);
            grid.asSingleSelect().clear();
        });

        layout.add(form, crearAcciones(btnCrear, btnConsultar, btnActualizar, btnEliminar, btnLimpiar), grid);
        return layout;
    }

    private Component crearSeccionPrestamo() {
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(false);

        TextField idField = new TextField("ID Préstamo");
        TextField idUsuarioField = new TextField("ID Usuario");
        TextField idLibroField = new TextField("ID Libro");
        DatePicker fechaPrestamoField = new DatePicker("Fecha de préstamo");
        DatePicker fechaEntregaField = new DatePicker("Fecha de entrega");
        TextField cantidadField = new TextField("Cantidad de libros");

        FormLayout form = new FormLayout(idField, idUsuarioField, idLibroField,
                fechaPrestamoField, fechaEntregaField, cantidadField);

        Grid<Prestamo> grid = new Grid<>(Prestamo.class, false);
        grid.addColumn(Prestamo::getIdPrestamo).setHeader("ID Préstamo").setAutoWidth(true);
        grid.addColumn(Prestamo::getIdUsuario).setHeader("ID Usuario").setAutoWidth(true);
        grid.addColumn(Prestamo::getIdLibro).setHeader("ID Libro").setAutoWidth(true);
        grid.addColumn(Prestamo::getFechaPrestamo).setHeader("Fecha de préstamo").setAutoWidth(true);
        grid.addColumn(Prestamo::getFechaEntrega).setHeader("Fecha de entrega").setAutoWidth(true);
        grid.addColumn(Prestamo::getCantidadLibros).setHeader("Cantidad de libros").setAutoWidth(true);

        Runnable refrescar = () -> grid.setItems(crudService.listarTodos(Prestamo.class));
        refrescar.run();

        Runnable limpiarForm = () -> {
            limpiar(idField, idUsuarioField, idLibroField, cantidadField);
            fechaPrestamoField.clear();
            fechaEntregaField.clear();
        };

        grid.asSingleSelect().addValueChangeListener(e -> {
            Prestamo p = e.getValue();
            if (p != null) {
                idField.setValue(texto(p.getIdPrestamo()));
                idUsuarioField.setValue(texto(p.getIdUsuario()));
                idLibroField.setValue(texto(p.getIdLibro()));
                fechaPrestamoField.setValue(p.getFechaPrestamo());
                fechaEntregaField.setValue(p.getFechaEntrega());
                cantidadField.setValue(texto(p.getCantidadLibros()));
            }
        });

        Button btnCrear = new Button("Crear", e -> ejecutar(() -> {
            Prestamo p = new Prestamo(
                    fechaPrestamoField.getValue(),
                    fechaEntregaField.getValue(),
                    parseInt(cantidadField.getValue()));
            p.setIdUsuario(parseId(idUsuarioField));
            p.setIdLibro(parseId(idLibroField));
            crudService.guardar(p);
            refrescar.run();
            idField.setValue(texto(p.getIdPrestamo()));
            Notification.show("Préstamo guardado con ID: " + p.getIdPrestamo());
        }));
        btnCrear.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button btnConsultar = new Button("Consultar", e -> ejecutar(() -> {
            Long id = idObligatorio(idField);
            if (id == null) return;
            Prestamo p = crudService.buscarPorId(Prestamo.class, id);
            if (p != null) {
                grid.asSingleSelect().setValue(p);
                Notification.show("Préstamo encontrado: " + p.getIdPrestamo());
            } else {
                Notification.show("No encontrado");
            }
        }));

        Button btnActualizar = new Button("Actualizar", e -> ejecutar(() -> {
            Long id = idObligatorio(idField);
            if (id == null) return;
            Prestamo p = crudService.buscarPorId(Prestamo.class, id);
            if (p != null) {
                p.setIdUsuario(parseId(idUsuarioField));
                p.setIdLibro(parseId(idLibroField));
                p.setFechaPrestamo(fechaPrestamoField.getValue());
                p.setFechaEntrega(fechaEntregaField.getValue());
                p.setCantidadLibros(parseInt(cantidadField.getValue()));
                crudService.actualizar(p);
                refrescar.run();
                Notification.show("Préstamo actualizado");
            } else {
                Notification.show("ID no encontrado");
            }
        }));

        Button btnEliminar = new Button("Eliminar", e -> ejecutar(() -> {
            Long id = idObligatorio(idField);
            if (id == null) return;
            crudService.eliminar(Prestamo.class, id);
            refrescar.run();
            limpiarForm.run();
            Notification.show("Préstamo eliminado");
        }));
        btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button btnLimpiar = new Button("Limpiar", e -> {
            limpiarForm.run();
            grid.asSingleSelect().clear();
        });

        layout.add(form, crearAcciones(btnCrear, btnConsultar, btnActualizar, btnEliminar, btnLimpiar), grid);
        return layout;
    }

    private String texto(Object valor) {
        return valor == null ? "" : valor.toString();
    }

    private void ejecutar(Runnable accion) {
        try {
            accion.run();
        } catch (Exception ex) {
            Notification.show("Error: " + ex.getMessage());
        }
    }

    private Long idObligatorio(TextField field) {
        Long id = parseId(field);
        if (id == null) {
            Notification.show("Ingresa un ID válido");
        }
        return id;
    }

    private Long parseId(TextField field) {
        try {
            return Long.parseLong(field.getValue().trim());
        } catch (Exception e) {
            return null;
        }
    }

    private int parseInt(String val) {
        try {
            return Integer.parseInt(val.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    private void limpiar(TextField... campos) {
        for (TextField c : campos) {
            c.clear();
        }
    }

    private HorizontalLayout crearAcciones(Button... botones) {
        HorizontalLayout layout = new HorizontalLayout(botones);
        layout.getStyle().set("flex-wrap", "wrap");
        return layout;
    }
}