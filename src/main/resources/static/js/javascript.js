// ===============================
// CARGAR PRODUCTOS DESDE LA BD
// ===============================

async function cargarProductos() {
    const container = document.getElementById("productos-container");
    if (!container) return;

    try {
        const respuesta = await fetch('/api/productos');
        const productos = await respuesta.json();
        renderizarProductos(productos);
    } catch (error) {
        console.error("Error al cargar productos:", error);
        container.innerHTML = `<div style="grid-column: 1/-1; text-align: center; color: red;">
            <h3>Error al cargar productos. Por favor, intenta de nuevo más tarde.</h3>
        </div>`;
    }
}

function renderizarProductos(productos) {
    const container = document.getElementById("productos-container");
    container.innerHTML = "";

    if (productos.length === 0) {
        container.innerHTML = `<div style="grid-column: 1/-1; text-align: center;"><h3>No hay productos disponibles por ahora.</h3></div>`;
        return;
    }

    productos.forEach(p => {
        // Determinamos el color del punto de stock
        let dotClass = "";
        if (p.stock <= 0) dotClass = "out";
        else if (p.stock < 10) dotClass = "low";

        const card = document.createElement("div");
        card.className = "producto-card";
        card.dataset.id = p.idProducto;
        card.dataset.nombre = p.descripcion;
        card.dataset.precio = p.precio;
        card.dataset.emoji = getEmojiPorCategoria(p.categoria);
        card.dataset.cat = p.categoria || "otros";

        card.innerHTML = `
            <div class="producto-img">
                <img src="${p.imagenUrl || 'https://via.placeholder.com/400x300?text=' + p.descripcion}" alt="${p.descripcion}">
                <span class="producto-badge">${p.categoria || 'General'}</span>
            </div>
            <div class="producto-info">
                <div class="producto-nombre">${p.descripcion}</div>
                <div class="producto-descripcion">${getDescripcionesEjemplo(p.descripcion)}</div>
                <div class="producto-precio-row">
                    <span class="producto-precio">$${formatearPrecioColombia(p.precio)}</span>
                </div>
                <div class="producto-stock">
                    <span class="stock-dot ${dotClass}"></span> 
                    ${p.stock > 0 ? `En stock — ${p.stock} unidades` : '<span style="color:red; font-weight:bold;">Agotado</span>'}
                </div>
                <div class="producto-cantidad">
                    <label>Cantidad:</label>
                    <div class="cantidad-control">
                        <button type="button" class="cantidad-btn qty-minus">−</button>
                        <span class="cantidad-num">1</span>
                        <button type="button" class="cantidad-btn qty-plus">+</button>
                    </div>
                </div>
            </div>
            <div class="producto-actions">
                <button class="btn-agregar" ${p.stock <= 0 ? 'disabled style="background:#ccc; cursor:not-allowed;"' : ''}>
                    ${p.stock > 0 ? '🛒 Agregar al Carrito' : 'Sin Stock'}
                </button>
            </div>
        `;

        // Añadir eventos a los botones de cantidad
        const minus = card.querySelector(".qty-minus");
        const plus = card.querySelector(".qty-plus");
        const num = card.querySelector(".cantidad-num");

        minus.addEventListener("click", () => {
            let n = parseInt(num.innerText);
            if (n > 1) num.innerText = n - 1;
        });

        plus.addEventListener("click", () => {
            let n = parseInt(num.innerText);
            if (n < p.stock) num.innerText = n + 1;
            else mostrarNotificacion("Máximo stock alcanzado ⚠️");
        });

        // Evento agregar
        card.querySelector(".btn-agregar").addEventListener("click", () => {
            const prod = {
                id: p.idProducto,
                nombre: p.descripcion,
                precio: p.precio,
                cantidad: parseInt(num.innerText),
                emoji: card.dataset.emoji
            };
            agregarCarrito(prod);
            mostrarNotificacion(`${prod.nombre} agregado 🛒`);
        });

        container.appendChild(card);
    });
}

function getEmojiPorCategoria(cat) {
    const emojis = {
        'frutas': '🍎',
        'lacteos': '🥛',
        'carnes': '🥩',
        'panaderia': '🍞',
        'bebidas': '🧃',
        'limpieza': '🧹',
        'higiene': '🪥',
        'granos': '🍚'
    };
    return emojis[cat] || '📦';
}

function getDescripcionesEjemplo(nombre) {
    // Descripciones genéricas si no vienen de la DB
    return `Producto de alta calidad: ${nombre}. Frescura y garantía asegurada en nuestra tienda.`;
}

// ===============================
// INICIALIZACIÓN
// ===============================
document.addEventListener("DOMContentLoaded", () => {
    cargarProductos();
});

// ===============================
// FUNCIÓN PARA PRECIOS COLOMBIANOS
// ===============================
function formatearPrecioColombia(precio) {
    return Math.round(precio).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
}

let carrito = [];

function agregarCarrito(prod) {
    const existe = carrito.find(p => p.id === prod.id);
    if (existe) {
        existe.cantidad += prod.cantidad;
    } else {
        carrito.push(prod);
    }
    actualizarCarrito();
}

function eliminarDelCarrito(id) {
    const producto = carrito.find(p => p.id === id);
    if (producto) {
        carrito = carrito.filter(p => p.id !== id);
        mostrarNotificacion(`${producto.nombre} eliminado 🗑️`);
        actualizarCarrito();
    }
}

function actualizarCarrito() {
    const body = document.getElementById("carritoBody");
    const totalSpan = document.getElementById("carritoTotal");
    const count = document.getElementById("carritoCount");
    const countNav = document.getElementById("carritoCountNav");
    const flotante = document.getElementById("carritoFlotante");
    const flotanteTotal = document.getElementById("flotanteTotal");

    if (!body) return;

    body.innerHTML = "";
    let total = 0;

    if (carrito.length === 0) {
        body.innerHTML = `<div class="carrito-empty"><div class="empty-icon">🛒</div><p>Tu carrito está vacío</p></div>`;
        if(flotante) flotante.style.display = "none";
        count.innerText = "0";
        countNav.innerText = "0";
        totalSpan.innerText = "$0";
        return;
    }

    carrito.forEach(p => {
        const subtotal = p.precio * p.cantidad;
        total += subtotal;
        body.innerHTML += `
            <div style="display:flex;justify-content:space-between;align-items:center;margin-bottom:10px;padding:8px;background:#f8f9fa;border-radius:5px;">
                <span style="flex:1;">${p.emoji} ${p.nombre} x${p.cantidad}</span>
                <strong style="margin-right:10px;">$${formatearPrecioColombia(subtotal)}</strong>
                <button class="btn-eliminar" data-id="${p.id}" style="background:#ff4444;color:white;border:none;padding:5px 10px;border-radius:3px;cursor:pointer;">🗑️</button>
            </div>`;
    });

    totalSpan.innerText = `$${formatearPrecioColombia(total)}`;
    if(flotanteTotal) flotanteTotal.innerText = `$${formatearPrecioColombia(total)}`;
    count.innerText = carrito.length;
    countNav.innerText = carrito.length;
    if(flotante) flotante.style.display = "flex";
    
    document.querySelectorAll(".btn-eliminar").forEach(btn => {
        btn.addEventListener("click", () => eliminarDelCarrito(btn.dataset.id));
    });
}

// Eventos de Modal
const modalClose = document.getElementById("modalClose");
if (modalClose) {
    modalClose.addEventListener("click", () => document.getElementById("modalOverlay").style.display = "none");
}

const btnCarritoFlotante = document.getElementById("carritoFlotante");
if (btnCarritoFlotante) {
    btnCarritoFlotante.addEventListener("click", () => document.getElementById("modalOverlay").style.display = "flex");
}

// Finalizar Compra
const btnFinalizar = document.getElementById("btnFinalizarCompra");
if (btnFinalizar) {
    btnFinalizar.addEventListener("click", async () => {
        if (carrito.length === 0) {
            mostrarNotificacion("El carrito está vacío 😅");
            return;
        }

        let total = 0;
        carrito.forEach(p => total += (p.precio * p.cantidad));

        if (!confirm(`¿Finalizar compra por $${formatearPrecioColombia(total)}?`)) return;

        try {
            const respuesta = await fetch('/api/ventas/finalizar', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ carrito: carrito, total: total })
            });

            const datos = await respuesta.json();
            if (datos.success) {
                mostrarNotificacion("¡Compra exitosa! 🎉");
                carrito = [];
                actualizarCarrito();
                document.getElementById("modalOverlay").style.display = "none";
                cargarProductos(); // REFRESCAR STOCK
            } else {
                alert("Error: " + (datos.message || "No se pudo procesar"));
            }
        } catch (error) {
            console.error(error);
            alert("Error de conexión.");
        }
    });
}

// Buscador y Filtros
const searchInput = document.getElementById("searchProducts");
if (searchInput) {
    searchInput.addEventListener("input", e => {
        const texto = e.target.value.toLowerCase();
        document.querySelectorAll(".producto-card").forEach(card => {
            const nombre = card.dataset.nombre.toLowerCase();
            card.style.display = nombre.includes(texto) ? "block" : "none";
        });
    });
}

document.querySelectorAll(".filter-btn").forEach(btn => {
    btn.addEventListener("click", () => {
        document.querySelectorAll(".filter-btn").forEach(b => b.classList.remove("active"));
        btn.classList.add("active");
        const cat = btn.dataset.cat;
        document.querySelectorAll(".producto-card").forEach(card => {
            card.style.display = (cat === "todos" || card.dataset.cat === cat) ? "block" : "none";
        });
    });
});

function mostrarNotificacion(msg) {
    const notif = document.getElementById("notification");
    const texto = document.getElementById("notifText");
    if (!notif || !texto) return;
    texto.innerText = msg;
    notif.classList.add("show");
    setTimeout(() => notif.classList.remove("show"), 1800);
}

const btnVolver = document.getElementById("btnBackDash");
if (btnVolver) {
    btnVolver.addEventListener("click", () => {
        if(confirm("¿Seguro que quieres salir de la tienda?")) window.location.href = '/cliente';
    });
}
