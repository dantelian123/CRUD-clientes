<div class="modal fade" id="agregarClienteModal">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-info text-white">
                <h5 class="modal-title">Agregar Cliente</h5>
                <button class="btn-close" data-bs-dismiss="modal">
                    <span></span>
                </button>
            </div>
            <form action="${pageContext.request.contextPath}/ServletControlador?accion=insertar" method="POST" class="was-validated">
                <div class="modal-body">
                    <div class="form-group mb-3">
                        <label for="nombre" class="form-label">Nombre</label>
                        <input type="text" name="nombre" class="form-control" required>
                    </div>
                    <div class="form-group mb-3">
                        <label for="apellido" class="form-label">Apellido</label>
                        <input type="text" name="apellido" class="form-control" required>
                    </div>
                    <div class="form-group mb-3">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" name="email" class="form-control" required>
                    </div>
                    <div class="form-group mb-3">
                        <label for="telefono" class="form-label">Telefono</label>
                        <input type="tel" name="telefono" class="form-control" required>
                    </div>
                    <div class="form-group mb-3">
                        <label for="saldo" class="form-label">Saldo</label>
                        <input type="number" name="saldo" class="form-control" required>
                    </div>
                </div>
                <div class="modal-footer mb-3">
                    <button class="btn btn-primary" type="submit">Guardar</button> 
                </div>
            </form>
        </div>
    </div>
    
</div>
