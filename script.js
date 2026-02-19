let zIdx = 1000;

// SISTEMA DE VENTANAS
function openWin(id) {
    const win = document.getElementById(id);
    win.dataset.opened = "true";
    win.classList.remove('closing', 'minimizing');
    win.style.display = 'flex';
    zIdx++; win.style.zIndex = zIdx;
    document.getElementById('start-menu').style.display = 'none';
    renderTabs();
}

function closeWin(id) {
    const win = document.getElementById(id);
    win.dataset.opened = "false";
    win.classList.add('closing');
    setTimeout(() => {
        win.style.display = 'none';
        win.classList.remove('closing');
        renderTabs();
    }, 150);
}

function minimizeWin(id) {
    const win = document.getElementById(id);
    win.classList.add('minimizing');
    setTimeout(() => {
        win.style.display = 'none';
        win.classList.remove('minimizing');
    }, 200);
}

function maximizeWin(id) { document.getElementById(id).classList.toggle('maximized'); }

// MENÚ INICIO
function toggleStart() {
    const menu = document.getElementById('start-menu');
    menu.style.display = (menu.style.display === 'flex') ? 'none' : 'flex';
}

// BARRA DE TAREAS (TABS)
function renderTabs() {
    const container = document.getElementById('tabs-container');
    container.innerHTML = ''; 
    document.querySelectorAll('.window').forEach(win => {
        if (win.dataset.opened === "true") {
            const title = win.querySelector('.title-caption span').innerText;
            const tab = document.createElement('div');
            tab.className = 'tab';
            tab.innerText = title.split(' - ')[0];
            tab.onclick = () => {
                if (win.style.display === 'none') {
                    win.style.display = 'flex';
                    win.classList.remove('minimizing');
                }
                zIdx++; win.style.zIndex = zIdx;
            };
            container.appendChild(tab);
        }
    });
}

// DRAG AND DROP
document.querySelectorAll('.title-bar').forEach(bar => {
    bar.onmousedown = (e) => {
        // Si la pantalla es pequeña, no arrastramos
        if (window.innerWidth <= 768) return; 

        let win = bar.parentElement;
        if (win.classList.contains('maximized')) return;
        zIdx++; win.style.zIndex = zIdx;
        let sX = e.clientX - win.offsetLeft, sY = e.clientY - win.offsetTop;
        const move = (e) => { win.style.left = (e.clientX - sX) + 'px'; win.style.top = (e.clientY - sY) + 'px'; };
        const stop = () => { window.removeEventListener('mousemove', move); };
        window.addEventListener('mousemove', move);
        window.addEventListener('mouseup', stop);
    };
});

// RELOJ DIGITAL XP
function updateClock() {
    const now = new Date();
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    document.getElementById('clock').innerText = `${hours}:${minutes}`;
}
updateClock();
setInterval(updateClock, 1000);

// CIERRE DE MENÚ AL HACER CLICK FUERA
document.addEventListener('click', (e) => {
    const menu = document.getElementById('start-menu');
    const btn = document.querySelector('.start-btn');
    if (menu.style.display === 'flex' && !menu.contains(e.target) && !btn.contains(e.target)) {
        menu.style.display = 'none';
    }
});