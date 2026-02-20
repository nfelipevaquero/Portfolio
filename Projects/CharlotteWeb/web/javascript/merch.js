document.addEventListener("DOMContentLoaded", () => {
    const botonesAgregar = document.querySelectorAll(".agregar-carrito");
    const listaCarrito = document.getElementById("lista-carrito");
    const contadorCarrito = document.getElementById("contador-carrito");
    const carritoSeccion = document.getElementById("carrito");
    const botonVerCarrito = document.getElementById("ver-carrito");
    const botonCerrarCarrito = document.createElement("button");
    const botonFinalizarCompra = document.getElementById("comprar");
    botonCerrarCarrito.textContent = "← Volver";
    botonCerrarCarrito.id = "cerrar-carrito";
    carritoSeccion.prepend(botonCerrarCarrito);
    const botonVaciar = document.getElementById("vaciar-carrito");
    let carrito = JSON.parse(localStorage.getItem("carrito")) || [];

    // Mostrar carrito
    botonVerCarrito.addEventListener("click", () => {
        carritoSeccion.style.transform = "translateX(0px)";
    });

    // Ocultar carrito sin perder productos
    botonCerrarCarrito.addEventListener("click", () => {
        carritoSeccion.style.transform = "translateX(100%)";
    });

    botonesAgregar.forEach(boton => {
        boton.addEventListener("click", (event) => {
            const productoElemento = event.target.closest(".producto");
            if (productoElemento) {
                const nombre = productoElemento.getAttribute("data-nombre");
                const precio = parseFloat(productoElemento.getAttribute("data-precio"));
                agregarAlCarrito(nombre, precio);
            }
        });
    });

    function agregarAlCarrito(nombre, precio) {
        carrito.push({ nombre, precio });
        localStorage.setItem("carrito", JSON.stringify(carrito));
        actualizarCarrito();
    }

    function actualizarCarrito() {
        listaCarrito.innerHTML = "";
        carrito.forEach((producto, index) => {
            const item = document.createElement("li");
            item.innerHTML = `${producto.nombre} - $${producto.precio} <button class="eliminar" data-index="${index}">❌</button>`;
            listaCarrito.appendChild(item);
        });

        contadorCarrito.textContent = carrito.length;
        agregarEventosEliminar();
    }

    function agregarEventosEliminar() {
        document.querySelectorAll(".eliminar").forEach(boton => {
            boton.addEventListener("click", (event) => {
                const index = event.target.getAttribute("data-index");
                carrito.splice(index, 1);
                localStorage.setItem("carrito", JSON.stringify(carrito));
                actualizarCarrito();
            });
        });
    }
    
    // Finalizar compra - Guardar carrito y redirigir
    botonFinalizarCompra.addEventListener("click", () => {
        localStorage.setItem("carrito", JSON.stringify(carrito));
        window.location.href = "quacksonquestionari.html";
    });

    // Cargar el carrito al inicio
    actualizarCarrito();
    function actualizarCarrito() {
        listaCarrito.innerHTML = "";
        carrito.forEach((item) => {
            const li = document.createElement("li");
            li.textContent = `${item.nombre} - ${item.precio}€`;
            listaCarrito.appendChild(li);
        });
        contadorCarrito.textContent = carrito.length;
    }

    botonVaciar.addEventListener("click", () => {
        carrito.length = 0; // Vacía el array
        actualizarCarrito(); // Refresca la interfaz
    });
});
